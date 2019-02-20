package cn.nongph.jiamei.admin.qrcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 二维码服务类
 */
@Service
public class QRCodeService {
	static final int WHITE = 0xFFFFFFFF;

	static final int BLACK = 0xff000000;

	/**
	 * 生成QRCode二维码
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public byte[] encode(QRCodeParam zxing) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 设置纠错级别(L 7%~M 15%~Q 25%~H 30%),纠错级别越高存储的信息越少
		hints.put(EncodeHintType.ERROR_CORRECTION, zxing.getErrorCorrectionLevel());

		// 设置编码格式
		hints.put(EncodeHintType.CHARACTER_SET, zxing.getCharset());

		// 设置边缘空白
		hints.put(EncodeHintType.MARGIN, zxing.getMargin());

		BitMatrix bitMatrix = new MultiFormatWriter().encode( zxing.getContent(), 
				                                              BarcodeFormat.QR_CODE, 
				                                              zxing.getWidth(),
				                                              zxing.getHeight(), hints );
		BufferedImage bi = toBufferedImage(bitMatrix);
		BufferedImage logoimg = null;
		if( StringUtils.hasText( zxing.getLogoPath() ) ) {
			String file = this.getClass().getResource(zxing.getLogoPath()).getFile();
			logoimg = ImageIO.read(new File(file));// 实例化一个Image对象。
		} else if( StringUtils.hasText(zxing.getLogoText() ) ) {
			logoimg = crateLogoImage(zxing.getLogoText());
		}
		if( logoimg!=null ) {
			int width_4 = bitMatrix.getWidth() / 4;
			int width_8 = width_4 / 2;
			int height_4 = bitMatrix.getHeight() / 4;
			int height_8 = height_4 / 2;
			
			/* 返回由指定矩形区域定义的子图像 */
			BufferedImage bi2 = bi.getSubimage(width_4 + width_8, height_4 + height_8, width_4, height_4);
			/* 获取一个绘图工具笔 */
			Graphics2D g2 = bi2.createGraphics();
			/* 当前图片的宽与高 */
			int currentImgWidth = logoimg.getWidth();
			int currentImgHeight = logoimg.getHeight();
			/* 处理图片的宽与高 */
			int resultImgWidth = 0;
			int resultImgHeight = 0;
			if (currentImgWidth != width_4) {
				resultImgWidth = width_4;
			}
			if (currentImgHeight != width_4) {
				resultImgHeight = width_4;
			}
			
			// 圆角处理
			if (zxing.getLogoCornerRadius() > 0) {
				logoimg = ImageUtil.makeRoundedCorner(logoimg, zxing.getLogoCornerRadius());
			}
			/* 绘制LOGO */
			g2.drawImage(logoimg.getScaledInstance(resultImgWidth, resultImgHeight, Image.SCALE_SMOOTH), 0, 0, null);
			g2.dispose();
			bi.flush();
		}
		
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
			ImageIO.write(bi, zxing.getFormat(), out);
			return out.toByteArray();
		}
	}

	/**
	 * 解析二维码
	 * 
	 * @param path
	 *            图片的绝对路径
	 * @param charset
	 *            字符集
	 * @throws IOException
	 * @throws NotFoundException
	 */
	public Result decode(String path, String charset) throws IOException,
			NotFoundException {
		Assert.hasText(path, "[decode]文件路径不能为空");
		File file = new File(path);
		BufferedImage image = ImageIO.read(file);
		// 判断是否是图片
		if (image == null) {
			throw new IOException("Could not decode image: " + path);
		}

		// 解析二维码用到的辅助类
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		// 解码设置编码方式
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, charset);

		return new MultiFormatReader().decode(bitmap, hints);
	}

	/**
	 * 生成二维码内容
	 * 
	 * @param bitMatrix
	 * @return BufferedImage
	 */
	private BufferedImage toBufferedImage(BitMatrix bitMatrix) {
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	private BufferedImage crateLogoImage(String logoText){
		if( logoText.length()==2 ) 
			logoText = logoText.charAt(0) + "    "+ logoText.charAt(1);
		BufferedImage logoimg = new BufferedImage(85, 85, BufferedImage.TYPE_INT_RGB);
		for( int x=0; x<logoimg.getWidth(); x++ )
			for( int y=0; y<logoimg.getHeight(); y++)
				logoimg.setRGB(x, y, WHITE);
		Graphics2D lg2 = logoimg.createGraphics();
		lg2.setColor( Color.RED );
		Font font = lg2.getFont();
		lg2.setFont( font.deriveFont(Font.BOLD, 20) );
		lg2.drawString( logoText, 9, 22);
		lg2.drawString( "农品荟", 9, 49);
		lg2.setFont( font.deriveFont(Font.BOLD, 13) );
		lg2.drawString( "m.nongph.cn", 2, 70);
		lg2.dispose();
		return logoimg;
	}
}
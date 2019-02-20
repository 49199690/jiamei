package cn.nongph.jiamei.admin.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码（QRCODE）配置类
 */
public class QRCodeParam{
	/**
	 * 二维码内容
	 */
	private String content;
	/**
	 * 图片的宽度
	 */
	private int width = 430;
	/**
	 * 图片的高度
	 */
	private int height = 430;
	
	/**
	 * 生成图片的格式（后缀，例如: "png"）
	 */
	private String format = "png";
	
	/**
	 * logo圆角半径（0表示不作圆角处理）
	 */
	private int logoCornerRadius = 0;
	
	/**
	 * logo图地址
	 */
	private String logoPath;
	
	/**
	 * 文字logo
	 */
	private String logoText;
	
	/**
	 * 纠错级别
	 */
	private Object errorCorrectionLevel = ErrorCorrectionLevel.H;
	
	/**
	 * 编码格式
	 */
	private String charset = "UTF-8";
	
	/**
	 * 二维码边缘留白
	 */
	private int margin = 0;


	public int getLogoCornerRadius() {
		return logoCornerRadius;
	}

	public void setLogoCornerRadius(int logoCornerRadius) {
		this.logoCornerRadius = logoCornerRadius;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Object getErrorCorrectionLevel() {
		return errorCorrectionLevel;
	}

	public void setErrorCorrectionLevel(Object errorCorrectionLevel) {
		this.errorCorrectionLevel = errorCorrectionLevel;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoText() {
		return logoText;
	}

	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}
}
package cn.nongph.jiamei.core.utils;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESHandler {
	
	private static String KEY = "W9E5Q2";
    private static Logger logger  = LoggerFactory.getLogger(AESHandler.class);
    
	public static String encrypt(String strIn){
		try {
			return encrypt(KEY, strIn);
		} catch (Exception e) {
			logger.error("errer on aes encrypt " + strIn, e);
			return strIn;
		}
	}
	
	public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes());
        return Base64.getUrlEncoder().encodeToString(encrypted);
    }
	
	public static String decrypt(String strIn){
		try {
			return decrypt(KEY, strIn);
		} catch (Exception e) {
			logger.error("errer on aes decrypt " + strIn, e);
			return strIn;
		}
	}
	
    public static String decrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.getUrlDecoder().decode(strIn);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; //创建一个空的16位字节数组（默认值为0）

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

        return skeySpec;
    }
    
    public static void main(String[] args) {
    	System.out.println( encrypt("ba3d97443544cbbe5baed6f955f991fa") );
    }
}
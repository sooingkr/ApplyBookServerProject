package com.dayside.dbrs.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyUtil {
	
	private static SecretKeySpec secretKey;
	final static String myKey = "thisIsSecretKey";
	private static byte[] key;
	private static String iv = "489ndfkgldkfglkd";
	
	private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);
	
	public static void createKey() throws Exception {
		MessageDigest sha = null;
		
		try {
			
            sha = MessageDigest.getInstance("SHA-256");
            secretKey = new SecretKeySpec(Arrays.copyOf(sha.digest(myKey.getBytes("UTF-8")), 16), "AES");
			
		} catch (NoSuchAlgorithmException e) {
			logger.info("KeyUtil.createKey  NoSuchAlgorithmException:: " + e);
        } catch (UnsupportedEncodingException e) {
        	logger.info("KeyUtil.createKey UnsupportedEncodingException :: " + e);
        }
	}
	
	public static String encrypt(String str) throws Exception {
		
		try {
			
			KeyUtil.createKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
	            
			return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		
			 
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			logger.debug("키 암호화 오류");
		}
		
		return null;
	}

	public static String decrypt(String str) throws Exception {
		
		 try {
			 	KeyUtil.createKey();
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));
	            return new String(cipher.doFinal(Base64.getDecoder().decode(str)),"UTF-8");
	    } catch (Exception e) {
	    	logger.debug("키 복호화 실패");
	    }
		 
	    return null;
		
	}
}

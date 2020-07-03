package com.dayside.dbrs.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtil {

	private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

	private static byte[] stringToSha256(String str) {
		
		MessageDigest sha = null;
	    byte[] msg = null;
	    
	    try {
			msg = str.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-256");
			msg = sha.digest(msg);
			
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			logger.info("PasswordUtil.stringToSha256 :: " + e);
		}
	    
		return Base64.getEncoder().encode(msg);
	}
	
	public static String passwordEncrypt(String mbrId, String password) {
		
		String tmp = new String(stringToSha256( mbrId ),0,32) + password;
		byte[] tmpByte = null;
		
		tmpByte = stringToSha256(tmp);
		
		return Base64.getEncoder().encodeToString(tmpByte);
	}

}

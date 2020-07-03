package com.dayside.dbrs.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayside.dbrs.common.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

public class tokenUtil {

	 private static final Logger logger = LoggerFactory.getLogger(tokenUtil.class);
	 
	/*
	 * 토큰 암호화
	 */
	public static String encryptToekn(Token token) {
		try {
			String sha_mbrId = new String(tokenUtil.stringToSha256(token.getMbrId()),0,32);
			String password = token.getPassword();
			logger.debug("암호화된 토큰 : " + KeyUtil.encrypt(sha_mbrId+password));
			return KeyUtil.encrypt(sha_mbrId+password);
		} catch (Exception e) {
			logger.debug("토큰 암호화 실패");
		}
		return null;
	}
	
	/*
	 * 토큰 복호화
	 */
	public static Token decryptToken(String token) {
		try {
			return new ObjectMapper().readValue(KeyUtil.decrypt(token), Token.class);
		} catch (Exception e) {
			logger.debug("토큰 복호화 실패");
		}
		return null;
	}
	
	/*
	 * 토큰 인증
	 */
	public static boolean verifyToken(String token, String sessionToken) {
		boolean result = false;
//		boolean result = true;
		
		if(token.equals(sessionToken)) {
			return true;
		}
		
		return result;
	}
	
	private static byte[] stringToSha256(String token) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		byte[] msg = token.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		msg = sha.digest(msg);
		
		return msg;
	}
}

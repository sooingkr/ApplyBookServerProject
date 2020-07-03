/**
 *  SMCP version 1.0
 *
 *  Copyright ⓒ 2015 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 *
 * @author kt
 * @since 2015.07.15
 * @version 1.0
 * @see 
 * @Copyright © 2015 By KT corp. All rights reserved.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *  수정일               수정자                수정내용
 *  -------------        ----------       -------------------------
 *  2015.07.15           [성명]               최초생성            
 *
 *
 * </pre>
 */

package com.dayside.dbrs.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP Util
 */
public class HttpUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * <pre>
	 * executeGet
	 * 
	 * HTTP GET request
	 * </pre>
	 *
	 * @param reqUrl
	 */
	public static String executeGet(String reqUrl) {
		HttpURLConnection connection = null;
		StringBuffer text = null;
		String result = null;

		try {
			URL url = new URL(reqUrl);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/html; charset=ks_c_5601-1987");
			connection.setRequestProperty("Accept", "application/json, text/html");
			connection.setRequestProperty("Content-Encoding", "gzip");
			logger.debug("connection.getResponseCode() = " + connection.getResponseCode());

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				text = new StringBuffer();

				InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "MS949");
				BufferedReader reader = new BufferedReader(isr);
				String line;

				do {
					line = reader.readLine();
					if (StringUtils.isNotEmpty(line)) {
						text.append(line + "\n");						
					}
				} while (line != null);

				result = text.toString();
			}
		} catch (IOException e) {
			System.out.println("executeGet() IOException = " + e.toString());
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return result;
	}

	/**
	 * <pre>
	 * executePost
	 * 
	 * HTTP POST request
	 * </pre>
	 *
	 * @param reqUrl
	 */
	public static void executePost(String reqUrl) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(reqUrl);
			StringBuffer text = new StringBuffer();

			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
			writer.write("POST Body");
			writer.write("\r\n");
			writer.write("Http Server!!");
			writer.write("\r\n");
			writer.flush();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr);
					String line;

					do {
						line = reader.readLine();
						text.append(line + "\n");
					} while (line != null);
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
    }
	
	public static String executePostWithParam(String reqUrl, HashMap<String, String> params) {
		return executePostWithParamAndTimeOut(reqUrl, params, 0);
	}
	
	public static String executePostWithParamAndTimeOut(String reqUrl, HashMap<String, String> params, int timeout) {
		HttpURLConnection connection = null;
		String result = null;
		
		try {
			URL url = new URL(reqUrl);
			StringBuffer text = new StringBuffer();

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			//connection.setRequestProperty("Content-Type", "application/json");
			//connection.setRequestProperty("Accept", "application/json");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			if(timeout > 0 ) {
				connection.setConnectTimeout(timeout);
				connection.setReadTimeout(timeout);
			}
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
			writer.write(getQuery(params));
			writer.flush();
						
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr);
					String line;

					do {
						line = reader.readLine();
						text.append(line + "\n");
					} while (line != null);
					
					result = text.toString();
			}
		} catch (IOException e) {
			System.out.println((e));
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		return result;
	}
	
	private static String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		
		boolean first = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			
			result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
		}

		return result.toString();
	}

}

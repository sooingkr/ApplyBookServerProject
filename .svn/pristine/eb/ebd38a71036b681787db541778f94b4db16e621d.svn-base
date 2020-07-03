package com.dayside.dbrs.common.util;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	/**
	 * <pre>
	 * marshallingJson
	 * 
	 * Object to Json
	 * </pre>
	 *
	 * @param object
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String marshallingJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		
		String jsonText = om.writeValueAsString(object);

		return jsonText;
	}
	
	/**
	 * <pre>
	 * unmarshallingJson
	 * 
	 * Json String to Object
	 * </pre>
	 *
	 * @param jsonText
	 * @param valueType Object의 class Type
	 * @return
	 * @throws Exception
	 */
	public static <T> T unmarshallingJson(String jsonText, Class<T> valueType)
			throws Exception {
		
		if (StringUtils.isEmpty(jsonText) && "null".equals(jsonText)) {
			throw new JsonParseException("null string", null);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();

		return (T) objectMapper.readValue(jsonText, valueType);
	}
	
	/**
	 * 
	 * <pre>
	 * deserialization
	 * Json 문자열을 Object로 변환
	 * </pre>
	 *
	 * @param jsonData
	 * @param classOfT
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	static public <T> T deserialization(String jsonData, Class<T> classOfT) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		T response = null;
		
		response = objectMapper.readValue(jsonData, classOfT);
		
		return response;
	}
	
	/**
	 * 
	 * <pre>
	 * serialization
	 * Object를 json문자열로 변환 
	 * </pre>
	 *
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	static public String serialization(Object object) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(object);
		return jsonData;
	}
}

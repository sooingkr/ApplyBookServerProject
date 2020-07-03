package com.dayside.dbrs.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dayside.dbrs.book.controller.BookReqController;

@RestControllerAdvice(basePackages = {"com.dayside.dbrs"})
public class CommonController {

	private final Logger logger = LoggerFactory.getLogger(CommonController.class);

	
    @ExceptionHandler(value=Exception.class)
    public Map handelExeption(Exception e) {
    	
    	Map<String,Object> result = new HashMap<>();
    	
    	result.put("retCode", "F000001");
		result.put("retMsg", "실패");
		
		logger.info("CommonController :: " + e);
		
		return result;
    }
}

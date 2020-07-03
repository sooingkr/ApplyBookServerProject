package com.dayside.dbrs.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dayside.dbrs.book.controller.BookReqController;

@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		logger.info("welcome to home");
		return "index";
	}
}

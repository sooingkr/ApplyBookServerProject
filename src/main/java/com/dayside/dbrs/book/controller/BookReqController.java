package com.dayside.dbrs.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dayside.dbrs.book.model.Book;
import com.dayside.dbrs.book.model.BookReqSearchData;
import com.dayside.dbrs.book.model.BookRequestStatusInfo;
import com.dayside.dbrs.book.service.BookReqService;
import com.dayside.dbrs.common.code.DbrsResultCode;


@RestController
public class BookReqController {

	private final Logger logger = LoggerFactory.getLogger(BookReqController.class);

	@Resource
	private BookReqService bookReqService;
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/book/purchaseRequest",method = RequestMethod.POST, produces="application/json;charset=utf-8")
	public Map insertBookPurchaseRequestService(@RequestBody Book bookInfo, HttpServletRequest req) throws Exception {
		
    	Map<String,Object> resultMap = new HashMap<>();
    	Map<String,Object> result = new HashMap<>();
    	
    	int insertCnt = 0;
    	
    	try {
    		insertCnt = bookReqService.insertBookPurchaseRequestService(bookInfo,req);
    		
    		if(insertCnt < 1) {
        		result.put("retCode", DbrsResultCode.BOOK_INSERT_FAIL.getResultCodeStr());
    			result.put("retMsg", DbrsResultCode.BOOK_INSERT_FAIL.getResultDescStr());
    			result.put("result", result);
        	} else {
        		
        		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
        		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
        	}
    		
		} catch(Exception e) {
			logger.info("/book/purchaseRequest" + e);
			result.put("retCode", DbrsResultCode.BOOK_INSERT_FAIL.getResultCodeStr());
			result.put("retMsg", DbrsResultCode.BOOK_INSERT_FAIL.getResultDescStr());
			result.put("result", result);
		}
    	
		resultMap.put("result", result);
		
		return resultMap;
	}
    
    @SuppressWarnings({  "rawtypes" })
	@RequestMapping(value = "/books/purchaseRequest",method = RequestMethod.POST, produces="application/json;charset=utf-8")
	public Map selectRequestbooks(@RequestBody(required=false) BookReqSearchData searchInfo,HttpServletRequest req) throws Exception {
		
    	Map<String,Object> resultMap = new HashMap<>();
    	Map<String,Object> result = new HashMap<>();
    	
    	logger.debug("사원번호 : " + req.getAttribute("empNum"));
    	
		List<Book> books = bookReqService.selectRequestbooksService(searchInfo ,req);    
		
		resultMap.put("books",books);
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
		resultMap.put("result", result);
		
		return resultMap;
	}
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/book/purchaseRequestDetail/{ordNum}",method = RequestMethod.GET, produces="application/json;charset=utf-8")
	public Map selectPurchaseRequestDetail(@PathVariable(value="ordNum") String ordNum, HttpServletRequest req) throws Exception {
		
    	Map<String,Object> resultMap = new HashMap<>();
    	Map<String,Object> result = new HashMap<>();
    	
		Book reqBookInfo = bookReqService.selectPurchaseRequestDetail(ordNum,req);    
		
		resultMap.put("reqBookInfo",reqBookInfo);
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
		resultMap.put("result", result);
    	
		return resultMap;
	}
    
    @RequestMapping(value = "/book/updatePurchaseRequest",method = RequestMethod.POST, produces="application/json;charset=utf-8")
   	public Map updatePurchaseRequest(@RequestBody BookRequestStatusInfo bookRequestStatusInfo, HttpServletRequest req) throws Exception {
   		
       	Map<String,Object> resultMap = new HashMap<>();
       	Map<String,Object> result = new HashMap<>();
       	
       	boolean isSuccess = bookReqService.updatePurchaseRequest(bookRequestStatusInfo, req);    
   		
       	if(isSuccess) {
       		
       		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());       		
       		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());   
       		
       	} else {
       		result.put("retCode", DbrsResultCode.BOOK_INVALID_STATUS.getResultCodeStr());
       		result.put("retMsg", DbrsResultCode.BOOK_INVALID_STATUS.getResultDescStr());
       	}
       	
       	resultMap.put("result", result);
       	
   		return resultMap;
   	}
    
    @RequestMapping(value = "/book/getYes24BookInfo",method = RequestMethod.POST, produces="application/json;charset=ks_c_5601-1987")
   	public Map getYes24BookInfo(@RequestBody Book book, HttpServletRequest req) throws Exception {
   		
       	Map<String, Object> resultMap = new HashMap<>();
       	Map<String, Object> result = new HashMap<>();
       	String resp = null;
       	
       	try {
       		resp = bookReqService.getYes24BookInfo(book.getBookName(), book.getPage(), book.getPageSize());
       		
       		if(!StringUtils.isNotEmpty(resp)) {
       			resultMap.put("retCode", DbrsResultCode.YES24_CONNECT_ERROR.getResultCodeStr());
       			resultMap.put("data", DbrsResultCode.YES24_CONNECT_ERROR.getResultDescStr());
       			return resultMap;
       		}
       	} catch(Exception e) {
       		logger.info("/book/getYes24BookInfo" + e);
       		resultMap.put("retCode", DbrsResultCode.YES24_CONNECT_ERROR.getResultCodeStr());
   			resultMap.put("data", DbrsResultCode.YES24_CONNECT_ERROR.getResultDescStr());
   			return resultMap;
       	}
       	
   		resultMap.put("retCode", DbrsResultCode.OK.getResultCodeStr());       		
   		resultMap.put("retMsg", DbrsResultCode.OK.getResultDescStr());       		
       	resultMap.put("data", resp);
       	result.put("result", resultMap);
   		
   		return result;
   	}
    
    
    @RequestMapping(value = "/book/getRidiBookInfo",method = RequestMethod.POST, produces="application/json;charset=utf-8")
   	public Map getRidiBookInfo(@RequestBody Book book, HttpServletRequest req) throws Exception {
   		
       	Map<String, Object> resultMap = new HashMap<>();
       	Map<String, Object> result = new HashMap<>();
       	Map resp = null;
       	
       	try {
       		resp = bookReqService.getRidiBookInfo(book.getBookName(), book.getPage());
       		
       		if(resp == null || resp.isEmpty()) {
       			resultMap.put("retCode", DbrsResultCode.YES24_CONNECT_ERROR.getResultCodeStr());
       			resultMap.put("data", DbrsResultCode.YES24_CONNECT_ERROR.getResultDescStr());
       			return resultMap;
       		}
       	} catch(Exception e) {
       		logger.info("/book/getRidiBookInfo" + e);
       		resultMap.put("retCode", DbrsResultCode.YES24_CONNECT_ERROR.getResultCodeStr());
   			resultMap.put("data", DbrsResultCode.YES24_CONNECT_ERROR.getResultDescStr());
   			return resultMap;
       	}
       	
   		resultMap.put("retCode", DbrsResultCode.OK.getResultCodeStr());       		
   		resultMap.put("retMsg", DbrsResultCode.OK.getResultDescStr());       		
       	resultMap.put("data", resp);
       	result.put("result", resultMap);
   		
   		return result;
   	}
    
}

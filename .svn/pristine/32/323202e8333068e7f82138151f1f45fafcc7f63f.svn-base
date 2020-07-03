package com.dayside.dbrs.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dayside.dbrs.book.model.Book;
import com.dayside.dbrs.book.model.BookRequestStatusInfo;
import com.dayside.dbrs.common.code.DbrsResultCode;
import com.dayside.dbrs.common.util.GsonUtil;
import com.dayside.dbrs.common.util.HttpRequestWrapper;
import com.dayside.dbrs.common.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 필수 파라미터 유효성 검사
 *
 */
public class BookFilter implements Filter  {

	private static final Logger logger = LoggerFactory.getLogger(BookFilter.class);
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		// requestBody와 responseBody를 복사해오기 위한 httpServlet 객체 wrapper
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(req);
		
		String endPoint ="";
		String requestBody ="";
		String url =  requestWrapper.getRequestURI();
		
		// requestbody에서 내용 가져옴
		requestBody = requestWrapper.getRequestBody();
		
		endPoint = url.substring(url.lastIndexOf("/"));
		
		Map<String,Object> vaildMap = null;
		if(!endPoint.isEmpty()) {
			// 도서 구매 요청 파리미터 체크
			if(endPoint.equals("/purchaseRequest")) {
				// 요청json을 book 객체로 변경
				Book book = GsonUtil.deserialization(requestBody, Book.class);
				vaildMap = checkBookRequestParameters(book);
				
				// true , 즉 필수 파라미터 모두 존재 시 
				if(!(boolean)vaildMap.get("isVaild")) {
					resultResponse(vaildMap,res);
					
					return;
				}
			
			// 도서 구매요청 상태 변경 파라미터 체크
			} else if(endPoint.equals("/updatePurchaseRequest")) {
				BookRequestStatusInfo bookRequestStatusInfo = GsonUtil.deserialization(requestBody, BookRequestStatusInfo.class);
				vaildMap = checkBookStatusUpdateRequestParameter(bookRequestStatusInfo);
				
				if(!(boolean)vaildMap.get("isVaild")) {
					resultResponse(vaildMap,res);
					
					return;
				} else {
					vaildMap = checkBookStatusUpdateRequestParameterValidation(bookRequestStatusInfo,requestWrapper);
					
					if(!(boolean)vaildMap.get("isVaild")) {
						resultResponse(vaildMap,res);
						return;
					}
				}
			} else if(endPoint.equals("/getYes24BookInfo")) {
				String bookName = GsonUtil.deserialization(requestBody, String.class);
				
				Map<String, Object> validMap = new HashMap<String, Object>();
				validMap.put("isVaild", true);
				
				if(!(boolean)vaildMap.get("isVaild")) {
					resultResponse(vaildMap,res);
					return;
				} 
			}
			
			chain.doFilter(requestWrapper, res);
			
			return;
		} else {
			Map result = new HashMap<>();
			result.put("result","올바르지 않은 url입니다.");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.write(new ObjectMapper().writeValueAsString(result));
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void resultResponse(Map vaildMap, HttpServletResponse response) throws IOException {
		Map result = new HashMap<>();
		Map resultMap = new HashMap<>();
		
		resultMap.put("retCode",((DbrsResultCode)vaildMap.get("invalidCode")).getResultCodeStr() );
		resultMap.put("retMsg", ((DbrsResultCode)vaildMap.get("invalidCode")).getResultDescStr());
		result.put("result",resultMap);
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(new ObjectMapper().writeValueAsString(result));
		logger.info(JsonUtil.marshallingJson(result));
	}
	
	/**
	 * 권한체크
	 * @param bookRequestStatusInfo
	 * @param req
	 * @return
	 */
	private Map<String,Object> checkBookStatusUpdateRequestParameterValidation(BookRequestStatusInfo bookRequestStatusInfo, HttpRequestWrapper req) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isVaild", true);
		
		String updateStatus = bookRequestStatusInfo.getStatus();
		String adminYn = (String)req.getAttribute("adminYn");
		
			
		if( adminYn.equals("N") && !(updateStatus.equals("CANCLE") || updateStatus.equals("RECEIPT")) ) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_INVALID_ADMIN);
		 } 
		
		return validMap;
	}
	
	/**
	 * 파라미터 null or empty check
	 * @param bookRequestStatusInfo
	 * @return
	 */
	private Map<String,Object> checkBookStatusUpdateRequestParameter(BookRequestStatusInfo bookRequestStatusInfo) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isVaild", true);
		
		String ordNum = bookRequestStatusInfo.getOrdNum();
		if(StringUtils.isEmpty(ordNum)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_ORDNUM);
			return validMap;
		}
		
		String status = "";
		status = bookRequestStatusInfo.getStatus();
		if(status != null) status.toUpperCase();
		if(StringUtils.isEmpty(status) || (!status.equals("CANCLE") && !status.equals("REJECT") && !status.equals("APPROVAL") && !status.equals("RECEIPT"))) {
			validMap.put("isVaild", false);
			
			if(!StringUtils.isEmpty(status) && status.equals("REQUEST")) {
				validMap.put("invalidCode", DbrsResultCode.BOOK_INVALID_STATUS);			
			} else {
				validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_STATUS);		
			}

			return validMap;
		} 
		
		return validMap;
	}
	
	/**
	 * 파라미터 null or empty check
	 * @param endpoint
	 * @param book
	 * @param request
	 * @return
	 */
	private Map<String,Object> checkBookRequestParameters(Book book) {
		
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isVaild", true);
		
		String bookName = book.getBookName();
		if(StringUtils.isEmpty(bookName)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_BOOKNAME);
			return validMap;
		}
		
		String reqUrl = book.getReqUrl();
		if(StringUtils.isEmpty(reqUrl)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_REQURL);
			return validMap;
		}
		
		String reqType = book.getReqType();
		if(StringUtils.isEmpty(reqType)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_REQTYPE);
			return validMap;
		}
		
		String reqYearMonth = book.getReqYearMonth();
		if(StringUtils.isEmpty(reqYearMonth)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_REQYEARMONTH);
			return validMap;
		}
		
		int price = book.getPrice(); 
		if(price == 0) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_PRICE);
			return validMap;
		}
		
		String bookType = book.getBookType();
		if(StringUtils.isEmpty(bookType)) {
			validMap.put("isVaild", false);
			validMap.put("invalidCode", DbrsResultCode.BOOK_MISSING_REQUEST_BOOKTYPE);
			return validMap;
		}
		
		
		return validMap;
	}
	
	@Override
	public void destroy() {
	    // 주로 필터가 사용한 자원을 반납
	}
}

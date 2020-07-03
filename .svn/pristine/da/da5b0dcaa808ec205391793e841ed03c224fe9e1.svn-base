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

import com.dayside.dbrs.common.code.DbrsResultCode;
import com.dayside.dbrs.common.util.GsonUtil;
import com.dayside.dbrs.common.util.HttpRequestWrapper;
import com.dayside.dbrs.common.util.JsonUtil;
import com.dayside.dbrs.member.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MemberFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberFilter.class);
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업
    }
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
			if(endPoint.equals("/join")) {
				Member member = GsonUtil.deserialization(requestBody, Member.class);
				vaildMap = checkJoinRequestParameters(member);
				
				// true , 즉 필수 파라미터 모두 존재 시 
				if(!(boolean)vaildMap.get("isValid")) {
					resultResponse(vaildMap,res);
					return;
				}
			
			// 도서 구매요청 상태 변경 파라미터 체크
			} else if(endPoint.equals("/login")) {
				Member member = GsonUtil.deserialization(requestBody, Member.class);
				vaildMap = checkLoginRequestParameter(member);
				
				if(!(boolean)vaildMap.get("isValid")) {
					resultResponse(vaildMap,res);
					return;
				} 
				
			} else if(endPoint.equals("/searchUser")) {
				vaildMap = checkAdminYn(requestWrapper);
				
				if(!(boolean)vaildMap.get("isValid")) {
					resultResponse(vaildMap,res);
					return;
				}
				
			} else if(endPoint.equals("/approvalUser")) {
				vaildMap = checkAdminYn(requestWrapper);
				
				if(!(boolean)vaildMap.get("isValid")) {
					resultResponse(vaildMap,res);
					return;
				} else {
					//필수파라미터 유효성검사
					Member member = GsonUtil.deserialization(requestBody, Member.class);
					vaildMap = checkApprovalUserRequestParameters(member);
					
					if(!(boolean)vaildMap.get("isValid")) {
						resultResponse(vaildMap,res);
						return;
					} 
				}
			} else if(endPoint.equals("/updateUserPassword")) {
				Member member = GsonUtil.deserialization(requestBody, Member.class);
				vaildMap = checkUpdateUserRequestParams(member);
				
				if(!(boolean)vaildMap.get("isValid")) {
					resultResponse(vaildMap, res);
					return;
				}
			}
			
			chain.doFilter(requestWrapper, res);
		} else {
			vaildMap = new HashMap<>();
			vaildMap.put("invalidCode", DbrsResultCode.URL_MISSING_REQUEST);
			resultResponse(vaildMap,res);
		}
	}
	
	private Map<String, Object> checkUpdateUserRequestParams(Member member) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isValid", true);
		
		if(StringUtils.isEmpty(member.getOldPassword())) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_PASSWORD);
			return validMap;
		}
		
		if(StringUtils.isEmpty(member.getPassword())) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_PASSWORD);
			return validMap;
		}
	
		return validMap;
	}
	private Map<String,Object> checkApprovalUserRequestParameters(Member member) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isValid", true);
		
		String mbrId = member.getMbrId();
		if(StringUtils.isEmpty(mbrId)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_MBRID);
			return validMap;
		}
		
		String status = member.getStatus();
		if(StringUtils.isEmpty(status)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_STATUS);
			return validMap;
		}
		
		return validMap;
	} 
	
	private Map<String,Object> checkAdminYn(HttpRequestWrapper req) {
		Map<String, Object> vaildMap = new HashMap<String, Object>();
		vaildMap.put("isValid", true);
		
		String adminYn = (String) req.getAttribute("adminYn");
		
		if(adminYn.equals("N")) {
			vaildMap.put("isValid", false);
			vaildMap.put("invalidCode", DbrsResultCode.MEMBER_INVAILD_ADMIN_REQUEST);
		}
		
		return vaildMap;
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
	
	private Map<String,Object> checkLoginRequestParameter(Member member) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isValid", true);
		
		String mbrId = member.getMbrId();
		if(StringUtils.isEmpty(mbrId)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_MBRID);
			return validMap;
		}
		
		String password = member.getPassword();
		if(StringUtils.isEmpty(password)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_PASSWORD);
			return validMap;
		}
		
		return validMap;
	}
	
	
	private Map<String,Object> checkJoinRequestParameters(Member member) {
		Map<String, Object> validMap = new HashMap<String, Object>();
		validMap.put("isValid", true);
		
		String empNum = member.getEmpNum();
		if(StringUtils.isEmpty(empNum)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_EMPNUM);
			return validMap;
		}
		
		String mbrId = member.getMbrId();
		if(StringUtils.isEmpty(mbrId)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_MBRID);
			return validMap;
		}
		
		String mbrName = member.getMbrName();
		if(StringUtils.isEmpty(mbrName)) {
			validMap.put("isValid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_MBRNAME);
			return validMap;
		}
		
		String password = member.getPassword();
		if(StringUtils.isEmpty(password)) {
			validMap.put("isVaLid", false);
			validMap.put("invalidCode", DbrsResultCode.MEMBER_MISSING_REQUEST_PASSWORD);
			return validMap;
		}
		
		return validMap;
	}
	
	@Override
	public void destroy() {
	    // 주로 필터가 사용한 자원을 반납
	}
}

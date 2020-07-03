package com.dayside.dbrs.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayside.dbrs.common.model.Token;
import com.dayside.dbrs.common.util.tokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionFilter implements Filter  {
	
	private final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpreq = null;
		HttpServletResponse httprep = null;
		HttpSession session = null;
		Map result = new HashMap<>();
		Map resultInfo = new HashMap<>();
		
		try {
			if(request instanceof HttpServletRequest) {httpreq = (HttpServletRequest) request; session = httpreq.getSession();}
			if(response instanceof HttpServletResponse) {httprep = (HttpServletResponse) response;}
			
			if(session != null) {
				String sessionToken = (String) session.getAttribute("token");
				String headerToken = (String)httpreq.getHeader("token");
				if(sessionToken != null) { 
					
					if(tokenUtil.verifyToken(headerToken, sessionToken)) {
						Token mbrInfo = (Token)Optional.ofNullable(session.getAttribute("mbrInfo")).orElse(new Token());
						
						request.setAttribute("empNum", mbrInfo.getEmpNum());
						request.setAttribute("adminYn", mbrInfo.getAdminYn());
						
						chain.doFilter(request, response);
						return;
					} 
				}
			} 
			
			resultInfo.put("retCode", "F00001");
			resultInfo.put("retMsg", "토큰 인증 실패");
			
			result.put("result",resultInfo);
			
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = httprep.getWriter();
			out.write(new ObjectMapper().writeValueAsString(result));
		} catch(Exception e) {
			logger.debug("세션필터 :: " + e );
		}
	}
	
	@Override
	public void destroy() {
	    // 주로 필터가 사용한 자원을 반납
	}
	
	
}

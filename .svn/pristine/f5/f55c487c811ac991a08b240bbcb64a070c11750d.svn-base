package com.dayside.dbrs.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dayside.dbrs.common.filter.BookFilter;
import com.dayside.dbrs.common.filter.MemberFilter;
import com.dayside.dbrs.common.filter.SessionFilter;

@Configuration
public class ContextFilter {

	 @Bean
	 public FilterRegistrationBean < SessionFilter > filterRegistrationBean() {
		  FilterRegistrationBean < SessionFilter > registrationBean = new FilterRegistrationBean();
	
		  registrationBean.setFilter(new SessionFilter());
		  registrationBean.addUrlPatterns("/book/*");
		  registrationBean.addUrlPatterns("/books/*");
		  registrationBean.addUrlPatterns("/member/*");
		  registrationBean.setOrder(1); //set precedence
		  
		  return registrationBean;
	 }

	 @Bean
	 public FilterRegistrationBean bookFilter() {
		  FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	
		  registrationBean.setFilter(new BookFilter());
		  registrationBean.addUrlPatterns("/book/purchaseRequest/*");
		  registrationBean.addUrlPatterns("/book/updatePurchaseRequest/*");
		  registrationBean.setName("bookFilter");
		  registrationBean.setOrder(2); //set precedence
		  
		  return registrationBean;
	 }
	 
	 @Bean
	 public FilterRegistrationBean MemberFilter() {
		  FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	
		  registrationBean.setFilter(new MemberFilter());
		  registrationBean.addUrlPatterns("/member/*");;
		  registrationBean.setName("memberFilter");
		  registrationBean.setOrder(2); //set precedence
		  
		  return registrationBean;
	 }
	 
}

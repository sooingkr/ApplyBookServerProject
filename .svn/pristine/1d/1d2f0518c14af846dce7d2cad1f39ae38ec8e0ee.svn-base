package com.dayside.dbrs.common.util;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayside.dbrs.book.model.Book;

public class MailUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	 public static void mailSender(Book bookInfo, String adminYn) throws Exception {
		 
		String host = "smtp.mailplug.co.kr";
		
    	final String id = "book@dayside.co.kr";
    	final String password = "dayside123!@";
    	
    	String receiver ="support@dayside.co.kr";
    	String cc = ""; // 참조자
    	
    	String status = Optional.ofNullable(bookInfo.getStatus()).orElse(new String(""));
		 Properties props = System.getProperties();

    	// SMTP 서버 정보 설정
    	props.put("mail.smtp.host", host); 
    	props.put("mail.smtp.port", 465);
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.ssl.enable", "true"); 
    	props.put("mail.smtp.ssl.trust", host);	
	    	
		//Session 생성 
    	Session session = Session.getDefaultInstance(props, new Authenticator() { 
    		protected PasswordAuthentication getPasswordAuthentication() { 
    			return new PasswordAuthentication(id, password); } });
    	session.setDebug(true); //for debug

    	Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
    	mimeMessage.setFrom(new InternetAddress(id));

    	if(adminYn.contentEquals("Y")) {
    		receiver = bookInfo.getMbrId() + "@dayside.co.kr";
    	}
    	
    	mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
//    	mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));//수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음 
    
		 try {
			 	status = bookInfo.getStatus();
			 
		    	if(status.equalsIgnoreCase("REQUEST")) {
		    		 status = "요청";
		    	} else if(status.equalsIgnoreCase("CANCLE")) {
		    		status = "취소";
		    	} else if(status.equalsIgnoreCase("APPROVAL")) {
		    		status = "승인";
		    	} else if(status.equalsIgnoreCase("REJECT")) {
		    		status = "반려";
		    	} else if(status.equalsIgnoreCase("RECEIPT")) {
		    		return;
		    	}
		    		
		    	String subject = "[도서]'" + bookInfo.getMbrName() + "'님의 도서신청 '" + status + "' 되었습니다.";
		    	StringBuffer body = new StringBuffer();
		    	
		    
		    	body.append("<br>");
		    	body.append("<p>도서가 '" + status + "' 되었습니다.</p><br>") ;
		    	body.append("<b>책제목</b>  : " + bookInfo.getBookName() + "<br>" );
		    	body.append("<b>URL</b>   : <a href=' "+ bookInfo.getReqUrl() +"'> " + bookInfo.getReqUrl() + "</a>" + "<br>");
		    	body.append("<b>이름</b>   :  " + bookInfo.getMbrName() + "<br>");
		    	body.append("<b>배송지</b>   :  " + bookInfo.getAddress()  + "<br>");
		    	body.append("<b>가격</b>      :  " + bookInfo.getPrice() + "원 " + "<br>");
		    	if(bookInfo.getEtc() != null) {
		    		body.append("<b>비고</b> <blockquote style=\"border-left:10px solid #ccc;padding:10px\">" + bookInfo.getEtc().replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "<br>") + "</blockquote>");   		
		    	}
		    	
		    	
		    	body.append("<br><br><br>");
		    	body.append("<h4>@dayside <a href='https://book.dayside.org'>데이사이드 도서 신청</h4>" + " </h4>");
		    	
		    	
		    	System.out.println(body);
		    	
		    	mimeMessage.setSubject(subject); //제목셋팅 
		    	mimeMessage.setContent(body.toString(), "text/html; charset=UTF-8");
		    	
		 } catch(Exception e) {
			 
			 logger.info("MailUtil.mailSender : " + e);
			 
			 mimeMessage.setSubject("[도서]데이사이드 도서사이트 확인요망"); 
		     mimeMessage.setContent("메일로 도서 정보를 확인할 수 없습니다. 사이트에서 확인해주세요.", "text/html; charset=UTF-8");
			 
		 }
		 
		 Transport.send(mimeMessage); 

	    }
}

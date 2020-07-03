package com.dayside.dbrs.common.code;

public enum UserMsgConst {
	
	JOIN_DUPLICATE_USER_MSG						("JOIN_DUPLICATE_USER_MSG", "이미 가입된 ID가 존재합니다."),
	JOIN_DUPLICATE_ENUM_MSG 					("JOIN_DUPLICATE_ENUM_MSG", "중복된 사원번호 입니다."),
	
	MODIFY_MEMBER_NO_EXIST						("MODIFY_MEMBER_NO_EXIST", "사용자 정보가 없습니다."),
	MODIFY_MEMBER_PASSWORD_NOT_MATCHED			("MODIFY_MEMBER_PASSWORD_NOT_MATCHED", "사용자 비밀번호가 일치하지 않습니다."),
	
	LOGIN_NONAPPROVAL_REQUEST	("LOGIN_NONAPPROVAL_REQUEST",  "미승인 회원입니다. 관리자에게 요청해주세요."),
	LOGIN_NOT_MATCHED_REQUEST	("LOGIN_NOT_MATCHED_REQUEST",  "아이디 혹은 비밀번호를 확인해주세요.");
	
	private String type;
	private String userMsg;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}

	private UserMsgConst(String type, String userMsg) {
		this.type = type;
		this.userMsg = userMsg;
	}
}

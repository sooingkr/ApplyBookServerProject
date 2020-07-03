package com.dayside.dbrs.common.code;

public enum DbrsResultCode {

	OK									("S00000", "성공"),
	
	BOOK_MISSING_REQUEST				("F01001",  "필수 파라미터 누락 (정의된 오류코드외 필수파라미터 누락)"),
	URL_MISSING_REQUEST					("F01002",  "URL 오류"),
	
	//"03" 도서 구매 신청 오류
	BOOK_MISSING_REQUEST_ORDNUM			("F03001",  "ordNum 파라미터 누락"),
	BOOK_MISSING_REQUEST_EMPNUM			("F03002",  "empNum 파라미터 누락"),
	BOOK_MISSING_REQUEST_MBRNAME		("F03003",  "mbrName 파라미터 누락"),
	BOOK_MISSING_REQUEST_BOOKNAME		("F03004",  "bookName 파라미터 누락"),
	BOOK_MISSING_REQUEST_ADDRESS		("F03005",  "address 파라미터 누락"),
	BOOK_MISSING_REQUEST_REQTYPE	 	("F03006",  "reqType 파라미터 누락"),
	BOOK_MISSING_REQUEST_REQYEARMONTH	("F03007",  "reqYearMonth 파라미터 누락"),
	BOOK_MISSING_REQUEST_STATUS			("F03008",  "status 파라미터 누락"),
	BOOK_MISSING_REQUEST_REQURL			("F03009",  "reqUrl 파라미터 누락"),
	BOOK_MISSING_REQUEST_ISBN10			("F03010",  "ISBN10 파라미터 누락"),
	BOOK_MISSING_REQUEST_ISBN13			("F03011",  "ISBN13 파라미터 누락"),
	BOOK_MISSING_REQUEST_PRICE			("F03012",  "price 파라미터 누락"),
	BOOK_MISSING_REQUEST_REGDT 			("F03013",  "regDt 파라미터 누락"),
	
	
	BOOK_INVALID_STATUS			    	("F03014", "상태 오류"),
	BOOK_INVALID_ADMIN			    	("F03015", "권한 오류"),
	
	BOOK_MISSING_REQUEST_BOOKTYPE 		("F03016",  "bookType 파라미터 누락"),
	
	//"04" 회원 오류
	MEMBER_MISSING_REQUEST_EMPNUM		("F04001",  "empNum 파라미터 누락"),
	MEMBER_MISSING_REQUEST_MBRID		("F04002",  "mbrId 파라미터 누락"),
	MEMBER_MISSING_REQUEST_MBRNAME		("F04003",  "mbrName 파라미터 누락"),
	MEMBER_MISSING_REQUEST_PASSWORD		("F04004",  "password 파라미터 누락"),
	MEMBER_MISSING_REQUEST_STATUS		("F04005",  "status 파라미터 누락"),
	MEMBER_MISSING_REQUEST_REGDT		("F04006",  "regDt 파라미터 누락"),
	
	MEMBER_INVAILD_JOIN_REQUEST			("F04007",  "가입 ID 오류"),
	MEMBER_INVAILD_LOGIN_REQUEST		("F04008",  "로그인 정보 오류"),
	
	MEMBER_INVAILD_ADMIN_REQUEST		("F04009",  "관리자 인증 오류"),
	MEMBER_NONAPPROVAL_LOGIN_REQUEST	("F04010",  "미승인 회원 오류"),
	
	MEMBER_INVALID_UPDATE_REQUEST		("F04011", "사용자 정보 업데이트 오류"),
	MEMBER_NO_EXIST						("F04012", "사용자 정보가 없습니다."),
	MEMBER_PASSWORD_NOT_MATCHED			("F04013", "사용자 비밀번호가 일치하지 않습니다."),
	
	YES24_CONNECT_ERROR					("F04014", "yes24 연동에 실패했습니다."),
	MEMBER_INVAILD_JOIN_EMPNUM_REQUEST	("F04015",  "사원번호 중복"),
	BOOK_INSERT_FAIL					("F04016",  "도서 신청 실패")
	;
	
	
	private String resultCode;
	private String resultDesc;

	private DbrsResultCode(String resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}
	

	/**
	 * @return the resultCode
	 */
	public String getResultCodeStr() {
		return resultCode;
	}

	/**
	 * @return the resultDesc
	 */
	public String getResultDescStr() {
		return resultDesc;
	}
}

package com.dayside.dbrs.book.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BookReqDetailRequest {

	private String ordNum;
	
	public String getOrdNum() {
		return ordNum;
	}
	public void setOrdNum(String ordNum) {
		this.ordNum = ordNum;
	}
}

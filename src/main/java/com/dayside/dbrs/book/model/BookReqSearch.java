package com.dayside.dbrs.book.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BookReqSearch {
	
	BookReqSearchData searchInfo;

	public BookReqSearchData getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(BookReqSearchData searchInfo) {
		this.searchInfo = searchInfo;
	}
}

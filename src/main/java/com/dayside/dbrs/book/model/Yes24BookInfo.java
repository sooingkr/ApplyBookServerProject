package com.dayside.dbrs.book.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Yes24BookInfo {

	private int TotalCount;
	private int Count;
	private List<BookInfo> BookInfo;
	
	public int getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public List<BookInfo> getBookInfo() {
		return BookInfo;
	}
	public void setBookInfo(List<BookInfo> bookInfo) {
		BookInfo = bookInfo;
	}

	@Override
	public String toString() {
		return "Yes24BookInfo [TotalCount=" + TotalCount + ", Count=" + Count + ", BookInfo=" + BookInfo + "]";
	}
	
}

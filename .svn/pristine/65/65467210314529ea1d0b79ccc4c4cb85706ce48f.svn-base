package com.dayside.dbrs.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dayside.dbrs.book.model.Book;
import com.dayside.dbrs.book.model.BookReqDetailRequest;
import com.dayside.dbrs.book.model.BookReqSearchData;
import com.dayside.dbrs.book.model.BookRequestStatusInfo;

@Mapper
public interface BookReqMapper {

	int insertBookPurchaseRequestService(Book book)  throws Exception;
	
	List<Book> selectRequestbooksService(BookReqSearchData bookReqSearchData) throws Exception;

	Book selectPurchaseRequestDetail(BookReqDetailRequest param) throws Exception;

	String selectPurchaseRequestStatus(BookRequestStatusInfo bookRequestStatusInfo) throws Exception;
	
	void updatePurchaseRequest(BookRequestStatusInfo param) throws Exception;





}

package com.dayside.dbrs.book.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dayside.dbrs.book.mapper.BookReqMapper;
import com.dayside.dbrs.book.model.Book;
import com.dayside.dbrs.book.model.BookReqDetailRequest;
import com.dayside.dbrs.book.model.BookReqSearchData;
import com.dayside.dbrs.book.model.BookRequestStatusInfo;
import com.dayside.dbrs.book.model.RidiBookInfo;
import com.dayside.dbrs.common.util.HttpUtil;
import com.dayside.dbrs.common.util.JsonUtil;
import com.dayside.dbrs.common.util.MailUtil;

import oracle.net.aso.b;

@Service
public class BookReqService {
	
	private final Logger logger = LoggerFactory.getLogger(BookReqService.class);

	@Resource
	private BookReqMapper bookReqMapper;

	public int insertBookPurchaseRequestService(Book book, HttpServletRequest req) throws Exception {
		
		String ordNum =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) + ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
    	String empNum = (String) req.getAttribute("empNum");
    	
    	book.setOrdNum(ordNum);
    	book.setEmpNum(empNum);
    	
    	int count = bookReqMapper.insertBookPurchaseRequestService(book);
    	
    	if(count > 0) {
    		
    		BookReqDetailRequest bookReqDetailRequest = new BookReqDetailRequest();
    		bookReqDetailRequest.setOrdNum(book.getOrdNum());
    		Book reqBookInfo = bookReqMapper.selectPurchaseRequestDetail(bookReqDetailRequest); 
    		// send mail
    		MailUtil.mailSender(reqBookInfo,(String)req.getAttribute("adminYn"));
    	}
    	return count;
	}

	public List<Book> selectRequestbooksService(BookReqSearchData searchInfo, HttpServletRequest req) throws Exception {
		
		searchInfo.setLoginEmpNum((String)req.getAttribute("empNum"));
		searchInfo.setAdminYn((String)req.getAttribute("adminYn"));
		
		return bookReqMapper.selectRequestbooksService(searchInfo);
	}

	public Book selectPurchaseRequestDetail(String ordNum, HttpServletRequest req) throws Exception {
		
		BookReqDetailRequest bookReqDetailRequest = new BookReqDetailRequest();
		bookReqDetailRequest.setOrdNum(ordNum);
		
		return bookReqMapper.selectPurchaseRequestDetail(bookReqDetailRequest);
	}

	@Transactional
	public boolean updatePurchaseRequest(BookRequestStatusInfo bookRequestStatusInfo, HttpServletRequest req) throws Exception {
		
		String selectStatus = bookReqMapper.selectPurchaseRequestStatus(bookRequestStatusInfo);
		String updateStatus = bookRequestStatusInfo.getStatus();
		bookRequestStatusInfo.setEmpNum((String)req.getAttribute("empNum"));
		
		if( (selectStatus.equals("REQUEST") && !updateStatus.equals("RECEIPT")) || (selectStatus.equals("APPROVAL") && updateStatus.equals("RECEIPT")) ) {
			bookReqMapper.updatePurchaseRequest(bookRequestStatusInfo);	
			
			BookReqDetailRequest bookReqDetailRequest = new BookReqDetailRequest();
			bookReqDetailRequest.setOrdNum(bookRequestStatusInfo.getOrdNum());
			Book reqBookInfo = bookReqMapper.selectPurchaseRequestDetail(bookReqDetailRequest); 
       		
       		// send mail
       		MailUtil.mailSender(reqBookInfo,(String)req.getAttribute("adminYn"));
       		
			return true;
		} 
		
		return false;
	}

	@Transactional
	public String getYes24BookInfo(String bookTitle, int page, int pageSize) {
		System.out.println("bokkTitle : " + bookTitle + " , page : " + page + " pageSize : " + pageSize);
		return HttpUtil.executeGet("http://www.yes24.com/SearchCorner/Sniper/GetSniperSearch?Query=" + bookTitle + "&Parsing=false&Domain=0&Page=" + page + "&PageSize=" + pageSize);
	}
	
	@Transactional
	public Map getRidiBookInfo(String bookTitle, int page) {
		
		try {
			Document doc = Jsoup.connect("https://ridibooks.com/search/?q=" + bookTitle + "&page=" + page).get();
			
			if(doc != null) {
				
				List<RidiBookInfo> books = new ArrayList<>();
				Map<String,Object> map = new HashMap();
				
				String total = doc.select(".result_category_list_wrapper li:first-child .book_count").text().replaceAll("[\\)\\(]", "");
				map.put("totalCount", total);
				
				Elements list = doc.select("div.book_macro_110.book_macro_landscape");
				
				for(Element book : list) {
					RidiBookInfo bookInfo = new RidiBookInfo();
					
					bookInfo.setBookName(book.select(".title_text.js_highlight_helper").text().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					bookInfo.setImageUrl(book.select(".book_thumbnail > .thumbnail_image > img").attr("src"));
					bookInfo.setRidiPrice(book.select("p.buy_price_info > span.price > .museo_sans").text());
					bookInfo.setAuthor(book.select("a.js_author_detail_link.author_detail_link").text());
					bookInfo.setBookTypeBadge("");
					bookInfo.setDescription("");
					bookInfo.setCount_num(book.select(".book_metadata.book_count > .count_num").text());
					bookInfo.setEntire_purchase(book.select("entire_purchase_info.additional_info > .museo_sans").text());
					bookInfo.setSubTitle(book.select(".book_metadata.meta_sub_title").text());
					bookInfo.setSeriesComplete("");
					bookInfo.setReqUrl(book.select(".title_link.trackable").attr("href"));
					bookInfo.setPublisher(book.select(".book_metadata.publisher > a").text());
					
					books.add(bookInfo);
				}
				
				map.put("books",books);
				
				return map;
			}
			
		} catch (IOException e) {
			logger.info("BookReqService.getRidiBookInfo " + e);
		}
		
		
		return null;
	}
	
}

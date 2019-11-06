package com.scbtest.bookstore.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scbtest.bookstore.dao.BookDao;
import com.scbtest.bookstore.model.Book;
import com.scbtest.bookstore.model.properties.AppBookProperties;
import com.scbtest.bookstore.model.responses.GetBookResponse;

@Service
public class BookService {
	
	@Autowired
	private AppBookProperties appProperties;
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
	
	private BookDao bookDao = new BookDao() ;
	
	@SuppressWarnings("unlikely-arg-type")
	public List<GetBookResponse> getBooks(){
		
		try {
			
			LOGGER.info(String.format("Getting books from web APIs..."));
			
			BookArrayList bookList = new BookArrayList();
			
			Book[] allBooks = bookDao.getBooks(appProperties.getAllBookAPI());
			Book[] recommended = bookDao.getBooks(appProperties.getRecommendedBookAPI());
			
			if(recommended != null) {
				for(int iRecommended = 0; iRecommended < recommended.length; iRecommended++) {
					Book book = recommended[iRecommended];
					if(book != null) {
						if(!bookList.contains(book)){
							GetBookResponse getBookResponse = getBookResponseFromBook(book, true);
							bookList.add(getBookResponse);
						}
					}
				}
			}
			
			if(allBooks != null) {
				for(int iBook = 0; iBook < allBooks.length; iBook++) {
					Book book = allBooks[iBook];
					if(book != null) {
						if(!bookList.contains(book)) {
							GetBookResponse getBookResponse = getBookResponseFromBook(book, false);
							bookList.add(getBookResponse);
						}
					}
				}
			}
			
			bookList.sort(new BookComparator());
			
			LOGGER.info(String.format("Success getting books from web APIs."));
			
			return bookList ;
		} catch(Exception e) {
			
			LOGGER.error(String.format("Get books error, %s, %s", e.getMessage(), e.getStackTrace()));
			return null;
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public List<GetBookResponse> getBooksByBookIDs(int[] bookIDs){
		try {
			BookArrayList bookList = new BookArrayList() ;
			LOGGER.info(String.format("Getting books by book IDs: %s ...", bookIDs.toString()));
			Book[] books = bookDao.getBooks(appProperties.getAllBookAPI());
			if(books != null) {
				for(Book book : books) {
					if(book != null) {
						for(int bookId : bookIDs) {
							if(book.getId() == bookId && !bookList.contains(book)) {
								GetBookResponse bookResp = getBookResponseFromBook(book, false);
								bookList.add(bookResp);
								break;
							}
						}
						
						if(bookIDs.length == bookList.size()) {
							break;
						}
					}
				}
				
				LOGGER.info("Success Getting books by book IDs");
				return bookList;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			LOGGER.error(String.format("Error getting books by book IDs: %s, %s, %s", bookIDs.toString(), e.getMessage(), e.getStackTrace()));
			return null;
		}
		
	}
	
	private GetBookResponse getBookResponseFromBook(Book book, boolean isRecommended) {
		
		if(book != null) {
			GetBookResponse getBookResponse = new GetBookResponse();
			getBookResponse.setId(book.getId());
			getBookResponse.setIs_recommended(isRecommended);
			getBookResponse.setName(book.getBook_name());
			getBookResponse.setAuthor(book.getAuthor_name());
			getBookResponse.setPrice(book.getPrice());
			return getBookResponse;
		}else {
			return null;
		}
		
	}
	
	private class BookComparator implements Comparator<GetBookResponse>{

		@Override
		public int compare(GetBookResponse arg0, GetBookResponse arg1) {
			
			if(arg0.isIs_recommended() && !arg1.isIs_recommended()) {
				return -1;
			}else if(!arg0.isIs_recommended() && arg1.isIs_recommended()) {
				return 1;
			}else return arg0.getName().compareTo(arg1.getName());
		}
	}
	
	@SuppressWarnings("serial")
	private class BookArrayList extends ArrayList<GetBookResponse>{

		@Override
		public boolean contains(Object o) {
			
			int bookId = ((Book)o).getId();
			for(int i = 0; i<this.size(); i++) {
				if(this.get(i).getId() == bookId) {
					return true;
				}
			}
			
			return false;
		}
	}
}

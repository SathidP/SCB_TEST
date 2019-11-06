package com.scbtest.bookstore.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scbtest.bookstore.model.responses.GetBookResponse;
import com.scbtest.bookstore.service.BookService;

@RestController
@RequestMapping("/")
public class BookController {
	
	private BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getBooks(HttpSession session) {
	
		List<GetBookResponse> bookList = bookService.getBooks();
		
		if(bookList != null) {
			return new ResponseEntity<Object>(bookList, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
}

package com.scbtest.bookstore.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scbtest.bookstore.model.Order;
import com.scbtest.bookstore.model.User;
import com.scbtest.bookstore.model.requests.AddOrderRequest;
import com.scbtest.bookstore.model.responses.AddOrderResponse;
import com.scbtest.bookstore.model.responses.GetBookResponse;
import com.scbtest.bookstore.service.BookService;
import com.scbtest.bookstore.service.OrderService;

@RestController
@RequestMapping("/")
public class OrderController {

	private OrderService orderService;
	private BookService bookService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);	
	
	@Autowired
	public OrderController(OrderService orderService, BookService bookService) {
		this.orderService = orderService;
		this.bookService = bookService;
	}
	
	@PostMapping("/users/testorders")
	public ResponseEntity<Object> testOrder(@RequestBody AddOrderRequest addOrderRequest,  HttpSession session){
			
		
				try {
					float totalPrice = orderService.getPriceByOrderId(5);
						
						AddOrderResponse response = new AddOrderResponse();
						response.setPrice(totalPrice);
						
						return new ResponseEntity<Object>(response, HttpStatus.OK);
				}catch(Exception e) {
					LOGGER.error(String.format("%s, %s", e.getMessage(), e.getStackTrace()));
					return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
	}
	
	@PostMapping("/users/orders")
	public ResponseEntity<Object> addOrder(@RequestBody AddOrderRequest addOrderRequest,  HttpSession session){
		
		User user = (User) session.getAttribute(UserController.SESSION_USER_ATTR);
		
		if(user == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		
		if(addOrderRequest != null && addOrderRequest.getOrders() != null) {
			
			try {
				List<GetBookResponse> bookResponseList = bookService.getBooksByBookIDs(addOrderRequest.getOrders());
				if(bookResponseList == null || bookResponseList.size() == 0) {
					return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
				}else {
					for(int bookId : addOrderRequest.getOrders()) {
						Order order = new Order();
						order.setBook_id(bookId);
						order.setUser_id(user.getUser_id());
						float price = 0;
						//get book price
						for(GetBookResponse book : bookResponseList) {
							if(bookId == book.getId()) {
								price = book.getPrice();
								break;
							}
						}
						order.setPrice(price);
						orderList.add(order);
					}
					
					
					int createdOrderId = orderService.addOrder(orderList);
					float totalPrice = orderService.getPriceByOrderId(createdOrderId);
					
					AddOrderResponse response = new AddOrderResponse();
					response.setPrice(totalPrice);
					
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}catch(Exception e) {
				LOGGER.error(String.format("%s, %s", e.getMessage(), e.getStackTrace()));
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
}

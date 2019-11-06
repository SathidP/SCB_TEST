package com.scbtest.bookstore.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scbtest.bookstore.dao.OrderDao;
import com.scbtest.bookstore.model.Order;

@Service
public class OrderService {
	
	private OrderDao orderDao;
	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	public OrderService(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public int addOrder(List<Order> orderItems) {
		try {
			return orderDao.insertOrder(orderItems);
		}catch(Exception e) {
			LOGGER.error(String.format("Error adding order, %s, %s", e.getMessage(), e.getStackTrace()));
			return 0;
		}
	}
	
	public float getPriceByOrderId(int orderID) {
	
		
		try {
			return orderDao.getOrderByOrderId(orderID).get(0).floatValue();
		}catch(Exception e) {
			
			LOGGER.error(String.format("Error getting order price, %s, %s", e.getMessage(), e.getStackTrace()));
			return 0;
		}
	}
	
	public List<Integer> getBookIdListByUserId(int userID) {
		
		try {
			
			return orderDao.getBookIDsByUserId(userID);
		}catch(Exception e) {
			LOGGER.error(String.format("Error getting book ID list, %s, %s", e.getMessage(), e.getStackTrace()));
			return null;
		}
	}
	
	public boolean deleteOrdersByUserId(int userID) {
		try {
			List<Integer> orderIDs = orderDao.getOrderIDsByUserId(userID);
			for(int orderId : orderIDs) {
				try {
					orderDao.deleteOrderItem(orderId);
					orderDao.deleteOrder(orderId);
				} catch(Exception e) {
					LOGGER.error(String.format("Delete orders by user ID error, User ID : %d, Order ID : %d, %s, %s", userID, orderId, e.getMessage(), e.getStackTrace()));
					return false;
				}
			}
			
			return true;
		}catch(Exception e) {
			LOGGER.error(String.format("Delete orders by user ID error, User ID : %d, %s, %s", userID, e.getMessage(), e.getStackTrace()));
			return false;
		}
	}
}

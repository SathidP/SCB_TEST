package com.scbtest.bookstore.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scbtest.bookstore.model.Order;
import com.scbtest.bookstore.model.User;

public interface OrderDao extends CrudRepository<User, Integer> {

	@Modifying
	@Query(value = "call insert_order(:user_id, :order_date)", nativeQuery = true)
	@Transactional
	public List<Integer> insertOrder(@Param("user_id") int user_id, @Param("order_date") Date order_date);
	
	@Modifying
	@Query(value = "delete from ORDERS where order_id = :order_id", nativeQuery = true)
	@Transactional
	public int deleteOrder(@Param("order_id") int order_id);
	
	@Modifying
	@Query(value = "delete from ORDER_ITEMS where order_id = :order_id", nativeQuery = true)
	@Transactional
	public int deleteOrderItem(@Param("order_id") int order_id);
	
	
	@Modifying
	@Query(value = "insert into ORDER_ITEMS (order_id, book_id, price) "
			+ "VALUES (:order_id, :book_id, :price)", nativeQuery = true)
	@Transactional
	public int insertOrderItem(@Param("order_id") int order_id, @Param("book_id") int book_id, @Param("price") float price);

	@Query(value = "select sum(b.price)"
					+ " from ORDERS a, ORDER_ITEMS b"
					+ " where a.order_id = b.order_id and a.order_id = :order_id", nativeQuery = true)
	public List<Float> getOrderByOrderId(@Param("order_id") int order_id);
	
	@Query(value = "select b.book_id"
			+ " from ORDERS a, ORDER_ITEMS b"
			+ " where a.order_id = b.order_id and a.user_id = :user_id"
			+ " order by book_id", nativeQuery = true)
	public List<Integer> getBookIDsByUserId(@Param("user_id") int user_id);
	
	@Query(value = "select order_id"
			+ " from ORDERS"
			+ " where user_id = :user_id", nativeQuery = true)
	public List<Integer> getOrderIDsByUserId(@Param("user_id") int user_id);
	
	default int insertOrder(List<Order> orderItems) throws Exception {
		
		if(orderItems == null || orderItems.size() == 0) {
			throw new Exception("Order is empty");
		}
		
		Date order_date = new Date();
		Integer orderId = null;
		for(int i=0; i<orderItems.size(); i++) {
			Order orderItem = orderItems.get(i);
			if(i==0) {
				orderId = insertOrder(orderItem.getUser_id(), order_date ).get(0);
			}
			
			if(orderId == null) {
				throw new Exception("Error inserting order to DB");
			} else {
				try{
					insertOrderItem(orderId, orderItem.getBook_id(), orderItem.getPrice());
				}
				catch(Exception e) {
					deleteOrder(orderId);
					deleteOrderItem(orderId);
					throw e;
				}
				
			}
			
		}
		
		return orderId;
	}
	
}

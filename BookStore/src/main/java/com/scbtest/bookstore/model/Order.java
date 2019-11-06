package com.scbtest.bookstore.model;

import java.io.Serializable;

public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer order_id;
	private Integer user_id;
	private Integer book_id;
	private Float price;

	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getBook_id() {
		return book_id;
	}
	public void setBook_id(Integer book_id) {
		this.book_id = book_id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}

}

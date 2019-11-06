package com.scbtest.bookstore.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddOrderRequest {
	private int[] orders ;
	
	public AddOrderRequest(@JsonProperty("orders") int[] orders) {
		this.orders = orders;
	}

	public int[] getOrders() {
		return orders;
	}

	public void setOrders(int[] orders) {
		this.orders = orders;
	} 
}

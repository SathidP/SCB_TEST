package com.scbtest.bookstore.model.responses;

public class GetBookResponse {
	private int id;
	private String name;
	private String author;
	private float price;
	private boolean is_recommended;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isIs_recommended() {
		return is_recommended;
	}
	public void setIs_recommended(boolean is_recommended) {
		this.is_recommended = is_recommended;
	}
}

package com.scbtest.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
	
	private int id;
	private String book_name;
	private String author_name;
	private float price;
	private boolean is_recommended = false;

	/*
	public book(@JsonProperty int id, @JsonProperty String book_name, @JsonProperty String author_name, @JsonProperty float price ) {
		this.id = id;
		this.book_name = book_name;
		this.author_name = author_name;
		this.price = price;
	}*/
	
	public Book() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
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

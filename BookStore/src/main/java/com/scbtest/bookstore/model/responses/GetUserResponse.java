package com.scbtest.bookstore.model.responses;

import java.util.List;

public class GetUserResponse {
	private String name;
	private String surname;
	private String date_of_birth;
	private List<Integer> books;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public List<Integer> getBooks() {
		return books;
	}
	public void setBooks(List<Integer> books) {
		this.books = books;
	}
}

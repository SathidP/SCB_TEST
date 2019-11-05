package com.scbtest.bookstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private int user_id;
	private String name;
	private String surname;
	private String username;
	private Date date_of_birth;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int id) {
		this.user_id = id;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	/*public void setDateOfBirth(String date_of_birth) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date_of_birth =  format.parse(date_of_birth);
		}catch(Exception e) {
			this.date_of_birth = null;
		}
	}*/
	
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth =  date_of_birth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
}

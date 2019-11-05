package com.scbtest.bookstore.model.requests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scbtest.bookstore.service.UserService;

public class AddUserRequest {
	private String username;
	private String password;
	private String name;
	private String surname;
	private Date date_of_birth;
	
	private final Logger LOGGER = LoggerFactory.getLogger(AddUserRequest.class);
	
	public AddUserRequest(@JsonProperty("username") String username, @JsonProperty("password") String password,@JsonProperty("name") String name, @JsonProperty("surname") String surname, @JsonProperty("date_of_birth")  String date_of_birth) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date_of_birth = dateFormat.parse(date_of_birth);
		} catch(Exception e) {
			LOGGER.error(String.format("Error parsing date of birth '%s', %s", date_of_birth, e.getMessage()));
		}
	}
	
	/*public AddUserRequest(@JsonProperty("username") String username, @JsonProperty("date_of_birth") String date_of_birth) {
		this.username = username;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date_of_birth = dateFormat.parse(date_of_birth);
		} catch(Exception e) {
			
		}
	}*/

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
	
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
}

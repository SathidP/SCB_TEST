package com.scbtest.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scbtest.bookstore.dao.UserDao;
import com.scbtest.bookstore.model.AppUserProperties;
import com.scbtest.bookstore.model.User;
import com.scbtest.bookstore.model.requests.LoginRequest;

@Service
public class UserService {

	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	private final UserDao userDao;
	
	@Autowired
	private AppUserProperties appProperties;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public int addUser(User user, String password) {
		try {
			if(password == null) {
				password = appProperties.getDefaultPassword();
			}
			
			int result = userDao.addUser(user, password);
			LOGGER.info("Added user");
			return result;
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			return 0;
		}
	}
	
	public User getUser(LoginRequest loginRequest) {
		try {			
			return userDao.getUserByUsername(loginRequest.getUsername(), loginRequest.getPassword());
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}

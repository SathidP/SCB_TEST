package com.scbtest.bookstore.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scbtest.bookstore.model.User;
import com.scbtest.bookstore.model.requests.AddUserRequest;
import com.scbtest.bookstore.model.requests.LoginRequest;
import com.scbtest.bookstore.model.responses.GetUserResponse;
import com.scbtest.bookstore.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
	
	private final UserService userService;
	private final String SESSION_USER_ATTR = "user"; 
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest,  HttpSession session) {
		
		User user = userService.getUser(loginRequest);
		if(user != null) {
			session.setAttribute(SESSION_USER_ATTR, user); 
			return new ResponseEntity<Object>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUser(HttpSession session) {
		User user = (User) session.getAttribute(SESSION_USER_ATTR);
		
		if(user != null) {
			GetUserResponse response = new GetUserResponse();
			response.setName(user.getName());
			response.setSurname(user.getSurname());
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			response.setDate_of_birth(dateFormat.format(user.getDate_of_birth()));
			
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<Object> addUser(@RequestBody AddUserRequest addUserRequest) {
		if(addUserRequest.getUsername() != null)
		{
			User user = new User();
			user.setUsername(addUserRequest.getUsername());
			
			if(addUserRequest.getName() != null) {
				user.setName(addUserRequest.getName());
			}else {
				user.setName("");;
			}
			
			if(addUserRequest.getSurname() != null) {
				user.setSurname(addUserRequest.getSurname());
			}else {
				user.setSurname("");
			}
			
			if(addUserRequest.getDate_of_birth() != null) {
				user.setDate_of_birth(addUserRequest.getDate_of_birth());
			}else {
				user.setDate_of_birth(null);
			}
			
			userService.addUser(user, addUserRequest.getPassword());
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}

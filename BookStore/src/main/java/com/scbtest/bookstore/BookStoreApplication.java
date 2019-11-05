package com.scbtest.bookstore;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
public class BookStoreApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

}

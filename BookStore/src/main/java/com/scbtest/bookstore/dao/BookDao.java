package com.scbtest.bookstore.dao;

import java.net.URI;

import javax.net.ssl.SSLSocketFactory;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.scbtest.bookstore.model.Book;

public class BookDao {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookDao.class);
	
	public Book[] getBooks(String url){		
		
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    
			URI uri = new URI(url);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			ResponseEntity<Book[]> repo = restTemplate.getForEntity(uri, Book[].class);
			return repo.getBody();
		}
		catch(Exception e) {
			LOGGER.error(String.format("Retreiving books from API '%s' error, %s", url,  e.getMessage()));
			return null;
		}
	}
 }

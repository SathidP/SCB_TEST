package com.scbtest.bookstore.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="app.book")
@Component
public class AppBookProperties {
	
	private String allBookAPI;
	private String recommendedBookAPI;
	
	public String getAllBookAPI() {
		return allBookAPI;
	}
	public void setAllBookAPI(String getAllBookAPI) {
		this.allBookAPI = getAllBookAPI;
	}
	public String getRecommendedBookAPI() {
		return recommendedBookAPI;
	}
	public void setRecommendedBookAPI(String getRecommendedBookAPI) {
		this.recommendedBookAPI = getRecommendedBookAPI;
	}
}

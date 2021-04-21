package com.excilys.training.web;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class ListComputersParams {
	private int limitByPages = 10;
	private String nameSearched = "";
	
	public String getNameSearched() {
		return nameSearched;
	}

	public void setNameSearched(String nameSearched) {
		this.nameSearched = nameSearched;
	}

	public int getLimitByPages() {
		return limitByPages;
	}

	public void setLimitByPages(int limitByPages) {
		this.limitByPages = limitByPages;
	}
	
	
}

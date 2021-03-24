package com.excilys.training.cbd.controller;

import java.util.ArrayList;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.Page;

public class Pagination {
	
	public int limitByPage;
	public int numberOfPages = 0;
	public ArrayList<Page> pages = new ArrayList<Page>();

	public Pagination(int limitByPage, ArrayList<Computer> computers) {
		this.limitByPage = limitByPage;
		System.out.println(computers.size()/limitByPage);
		this.numberOfPages = computers.size()/limitByPage ;
		for(int i=0, j=1; i<computers.size(); i=i+limitByPage, j++) {
			if(numberOfPages == pages.size()) {
				pages.add( new Page(j, computers.subList(i, computers.size())));
			}else {
				pages.add( new Page(j, computers.subList(i, i+limitByPage)));
			}
			System.out.println(pages.size());
		}
		
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public Page nextPage(int currentPage) {
		return this.pages.get(currentPage--);
	}
	
	public Page precedentPage(int currentPage) {
		return this.pages.get(currentPage++);
	}
	
	public Page firstPage() {
		return this.pages.get(0);
	}
	
	public Page lastPage() {
		return this.pages.get(pages.size()-1);
	}
	
	public Page getPageIndex(int index) {
		return this.pages.get(index);
	}
	
	
}

package com.excilys.training.cbd.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private List<Computer> computers;
	public int pageNumber;
	
	public Page(int pageNumber, List<Computer> computers){
		this.computers = computers;
		this.pageNumber = pageNumber;
	}
	
	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String toString(){
		String pageDisplay = "";
		for(Computer computer : this.computers) {
			pageDisplay += " - id : "+ computer.getId() + " = Name : "+computer.getName()+"\n" ;
		}
			return pageDisplay;
	}

}

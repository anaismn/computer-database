package com.excilys.training.cbd.model;

public class Company {
	
	private String name;
	private Long id = null;
	
	public Company() {}
	
	public Company(String name) {
		this.name = name;
	}
	
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public Long getID() {
		return id;
	}
}

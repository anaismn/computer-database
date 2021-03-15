package com.excilys.training.cbd.model;

public class Company {
	
	private String name;
	private Long id = null;
	
	private Company(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}
	
	public Long getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return id+" - "+name;
	}
	
	//Builder
	public static class Builder {
		private String name;
		private Long id = null;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder setID(Long id) {
			this.id = id;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
	}
}

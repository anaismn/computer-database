package com.excilys.training.cbd.model;

import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name = "";
	private LocalDate introduced = null;
	private LocalDate discontinued = null;
	private Long company_id;
	
	private Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company_id = builder.company_id;
	}
	
	public Long getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}
	
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	public Long getCompanyID() {
		return company_id;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	
	@Override
	public String toString() {
		return "name : "+name+
				" - introduced : "+introduced+
				" - discontinued : "+discontinued+
				" company id : "+company_id;
	}
	
	//Builder
	public static class Builder {
		private Long id;
		private String name = "";
		private LocalDate introduced = null;
		private LocalDate discontinued = null;
		private Long company_id;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder setID(Long id) {
			this.id = id;
			return this;
		}

		public Builder setIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder setDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder setCompany_id(Long company_id) {
			this.company_id = company_id;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}

}
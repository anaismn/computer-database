package com.excilys.training.cbd.model;

public class Computer {

	private String name = "";
	private String introduced = "";
	private String discontinued = "";
	private Long company_id;
	
	private Computer(Builder builder) {
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company_id = builder.company_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public Long getCompanyID() {
		return company_id;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
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
		private String name = "";
		private String introduced = "";
		private String discontinued = "";
		private Long company_id;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder setDiscontinued(String discontinued) {
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
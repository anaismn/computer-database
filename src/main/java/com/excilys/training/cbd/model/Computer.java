package com.excilys.training.cbd.model;

import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name = "";
	private LocalDate introduced = null;
	private LocalDate discontinued = null;
	private Company company = null;

	private Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public Long getId() {
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

	public Company getCompany() {
		return company;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "name : " + name + " - introduced : " + introduced + " - discontinued : " + discontinued + " company : "
				+ company.getName();
	}

	// Builder
	public static class Builder {
		private Long id;
		private String name = "";
		private LocalDate introduced = null;
		private LocalDate discontinued = null;
		private Company company;

		public Builder(String name) {
			this.name = name;
		}

		public Builder setId(Long id) {
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

		public Builder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

}
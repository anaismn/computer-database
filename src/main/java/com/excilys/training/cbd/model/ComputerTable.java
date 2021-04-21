package com.excilys.training.cbd.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@Column(name = "company_id")
	private Long companyId;
	
	@ManyToOne
	@JoinColumn(name = "company_id", referencedColumnName = "id", insertable = false, updatable = false)
	private CompanyTable company;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	
	
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long company) {
		this.companyId = company;
	}
	public CompanyTable getCompany() {
		return company;
	}
	public void setCompany(CompanyTable company) {
		this.company = company;
	}
	
	
	
}

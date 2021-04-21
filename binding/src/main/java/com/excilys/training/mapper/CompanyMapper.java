package com.excilys.training.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.training.model.Company;
import com.excilys.training.model.CompanyTable;

@Component
public class CompanyMapper {

	public Company resultToCompany(String name, Long id) {
		return new Company.Builder(name).setId(id).build();
	}

	public Company resultToCompany(List<Object> subList) {
		return new Company.Builder((String) subList.get(1)).setId((Long) subList.get(0)).build();
	}

	public Company tableToCompany(CompanyTable companyTable) {
		return new Company.Builder(companyTable.getName()).setId(companyTable.getId()).build();
	}
	
}

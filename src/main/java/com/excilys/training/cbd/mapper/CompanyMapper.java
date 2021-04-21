package com.excilys.training.cbd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.CompanyTable;

@Component
public class CompanyMapper implements RowMapper<Company> {

	public Company resultToCompany(String name, Long id) {
		return new Company.Builder(name).setId(id).build();
	}

	public Company resultToCompany(List<Object> subList) {
		return new Company.Builder((String) subList.get(1)).setId((Long) subList.get(0)).build();
	}

	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Company.Builder(resultSet.getString("company.name")).setId(resultSet.getLong("company.id")).build();
	}

	public Company tableToCompany(CompanyTable companyTable) {
		return new Company.Builder(companyTable.getName()).setId(companyTable.getId()).build();
	}
	
}

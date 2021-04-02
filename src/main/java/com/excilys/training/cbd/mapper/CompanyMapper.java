package com.excilys.training.cbd.mapper;

import java.util.List;

import com.excilys.training.cbd.model.Company;

public class CompanyMapper {

	public static Company resultToCompany(String name, Long id) {
		return new Company.Builder(name).setId(id).build();
	}


	public static Company resultToCompany(List<Object> subList) {
		return new Company.Builder((String) subList.get(1)).setId((Long) subList.get(0)).build();
	}

	
}

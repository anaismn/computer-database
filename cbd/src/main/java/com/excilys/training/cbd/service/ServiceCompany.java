package com.excilys.training.cbd.service;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.CompanyDAO;

public class ServiceCompany {
	
	static CompanyDAO companyDao = new CompanyDAO();
	
	public static void getAllCompanies(){
		companyDao.getAllCompanies();
	}

	public static Company getOneCompany(String nameSearched){
		return companyDao.getOneCompany(nameSearched);
	}
}

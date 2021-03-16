package com.excilys.training.cbd.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.CompanyDAO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.mapper.CompanyMapper;

public class ServiceCompany {
	
	static CompanyDAO companyDao = new CompanyDAO();
	
	public static ArrayList<Company> getAllCompanies() throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		TreeMap<Long, String> result = companyDao.getAllCompanies();
		result.forEach((id, name) -> companies.add(CompanyMapper.resultToCompany(name, id)));
		return companies;
	}

	public static Company getOneCompany(String nameSearched) throws DAOException, SQLException{
		ArrayList<Company> companies = new ArrayList<Company>();
		TreeMap<Long, String> result= companyDao.getOneCompany(nameSearched) ;
		result.forEach((id, name) -> companies.add(CompanyMapper.resultToCompany(name, id)));
		return companies.get(0);
	}
}

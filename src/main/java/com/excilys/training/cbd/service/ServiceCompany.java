package com.excilys.training.cbd.service;

import java.util.ArrayList;
import java.util.TreeMap;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.CompanyDAO;
import com.excilys.training.cbd.persistence.DAOException;

public class ServiceCompany {

	static CompanyDAO companyDao = new CompanyDAO();

	public static ArrayList<Company> getAllCompanies() throws DAOException {
		ArrayList<Company> companies = new ArrayList<Company>();
		TreeMap<Long, String> result = companyDao.getAllCompanies();
		result.forEach((id, name) -> companies.add(CompanyMapper.resultToCompany(name, id)));
		return companies;
	}

	public static Company getOneCompany(String nameSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(nameSearched);
		Company company = CompanyMapper.resultToCompany(nameSearched, result.firstKey());
		return company;
	}

	public static Company getOneCompany(Long idSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(idSearched);
		Company company = CompanyMapper.resultToCompany(result.get(idSearched), idSearched);
		return company;
	}

	public static void deleteCompany(Long idSearched) throws DAOException {
		companyDao.deleteCompany(idSearched);
	}

}

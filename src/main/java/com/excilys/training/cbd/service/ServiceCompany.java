package com.excilys.training.cbd.service;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.CompanyDAO;
import com.excilys.training.cbd.persistence.DAOException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceCompany {

	@Autowired
	CompanyDAO companyDao;

	public ArrayList<Company> getAllCompanies() throws DAOException {
		ArrayList<Company> companies = new ArrayList<Company>();
		TreeMap<Long, String> result = companyDao.getAllCompanies();
		result.forEach((id, name) -> companies.add(CompanyMapper.resultToCompany(name, id)));
		return companies;
	}

	public Company getOneCompany(String nameSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(nameSearched);
		Company company = CompanyMapper.resultToCompany(nameSearched, result.firstKey());
		return company;
	}

	public Company getOneCompany(Long idSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(idSearched);
		Company company = CompanyMapper.resultToCompany(result.get(idSearched), idSearched);
		return company;
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		companyDao.deleteCompany(idSearched);
	}

}

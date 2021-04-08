package com.excilys.training.cbd.service;

import java.util.List;
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
	@Autowired
	CompanyMapper companyMapper;

	public List<Company> getAllCompanies() throws DAOException {
		return companyDao.getAllCompanies();
	}

	public Company getOneCompany(String nameSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(nameSearched);
		Company company = companyMapper.resultToCompany(nameSearched, result.firstKey());
		return company;
	}

	public Company getOneCompany(Long idSearched) throws DAOException {
		TreeMap<Long, String> result = companyDao.getOneCompany(idSearched);
		Company company = companyMapper.resultToCompany(result.get(idSearched), idSearched);
		return company;
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		companyDao.deleteCompany(idSearched);
	}

}

package com.excilys.training.cbd.service;

import java.util.List;

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

	public List<Company> getAllCompanies() throws DAOException {
		return companyDao.getAllCompanies();
	}

	public Company getOneCompany(Long idSearched) throws DAOException {
		return companyDao.getOneCompany(idSearched);
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		companyDao.deleteCompany(idSearched);
	}

}

package com.excilys.training.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.training.dao.CompanyDAO;
import com.excilys.training.dao.DAOException;
import com.excilys.training.mapper.CompanyMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.CompanyTable;
import com.excilys.training.model.ComputerDTO;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceCompany {

	@Autowired
	CompanyDAO companyDao;
	@Autowired
	CompanyMapper companyMapper;

	public List<Company> getAllCompanies() throws DAOException {
		List<CompanyTable> result = companyDao.getAllCompanies();
		List<Company> listCompanies = new ArrayList<Company>();
		for (CompanyTable companyTable : result) {
			listCompanies.add(companyMapper.tableToCompany(companyTable));
		}
		return listCompanies;
	}

	public Company getOneCompany(Long idSearched) throws DAOException {
		CompanyTable result = companyDao.getOneCompany(idSearched);
		return companyMapper.tableToCompany(result);
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		companyDao.deleteCompany(idSearched);
	}

}

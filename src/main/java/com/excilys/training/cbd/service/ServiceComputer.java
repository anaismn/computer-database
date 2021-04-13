package com.excilys.training.cbd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.ComputerDAO;
import com.excilys.training.cbd.persistence.DAOException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceComputer {

	private static final Long NO_COMPANY = 0L;

	@Autowired
	ComputerDAO computerDao;
	@Autowired
	ServiceCompany serviceCompany;
	@Autowired 
	ComputerMapper computerMapper;

	public int countComputers(String nameSearched) throws DAOException {
		return computerDao.countComputers(nameSearched);
	}

	public List<Computer> getAllComputers(String columnOrdering, int limit, int offset) throws DAOException {
		return computerDao.getAllComputers(columnOrdering, limit, offset);
	}

	public Computer getOneComputer(Long idSearched) throws DAOException {
		return computerDao.getOneComputer(idSearched);
	}

	public List<Computer> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset) {
		return computerDao.getComputersFiltered(nameSearched, columnOrdering, limit, offset);
	}

	public void setNewComputer(Computer newComputer) throws DAOException {
		Object[] informations = computerMapper.computerToResult(newComputer);
		computerDao.setNewComputer(informations);
	}

	public void deleteComputer(Long id) throws DAOException {
		computerDao.deleteComputer(id);
	}

	public void updateComputer(Computer updatedComputer, Long id) throws DAOException {
		computerDao.updateComputer(updatedComputer, id);
	}

}

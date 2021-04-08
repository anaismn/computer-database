package com.excilys.training.cbd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Company;
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
	CompanyMapper companyMapper;
	@Autowired
	ComputerMapper computerMapper;

	public int countComputers(String nameSearched) throws DAOException {
		return computerDao.countComputers(nameSearched);
	}

	public ArrayList<Computer> getAllComputers(String columnOrdering, int limit, int offset) throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getAllComputers(columnOrdering, limit, offset);
		for (int i = 0; i < result.size(); i = i + 7) {
			Company company = companyMapper.resultToCompany(result.subList(i + 5, i + 7));
			result.set(i + 4, company);

			computers.add(computerMapper.resultToComputer(result.subList(i, i + 5)));
		}
		return computers;
	}

	public void getOneComputer(Long idSearched) throws DAOException {
		computerDao.getOneComputer(idSearched);
	}

	public Computer getOneComputer(String nameSearched) throws DAOException {
		ArrayList<Object> result = computerDao.getOneComputer(nameSearched);
		List<Company> companies = serviceCompany.getAllCompanies();
		if (NO_COMPANY != result.get(4)) {
			for (Company company : companies) {
				if (company.getId() == result.get(4)) {
					result.set(4, company);
					break;
				}
			}
		}
		Computer computer = computerMapper.resultToComputer(result);
		return computer;
	}

	public ArrayList<Computer> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset)
			throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getComputersFiltered(nameSearched, columnOrdering, limit, offset);
		for (int i = 0; i < result.size(); i = i + 7) {
			Company company = companyMapper.resultToCompany(result.subList(i + 5, i + 7));
			result.set(i + 4, company);

			computers.add(computerMapper.resultToComputer(result.subList(i, i + 5)));
		}
		return computers;
	}

	public void setNewComputer(Computer newComputer) throws DAOException {
		ArrayList<Object> informations = computerMapper.computerToResult(newComputer);
		computerDao.setNewComputer(informations);
	}

	public void deleteComputer(Long id) throws DAOException {
		computerDao.deleteComputer(id);
	}

	public void deleteComputer(String name) throws DAOException {
		computerDao.deleteComputer(name);
	}

	public void updateComputer(String oldName, Computer updatedComputer) throws DAOException {
		ArrayList<Object> updatedInfo = computerMapper.computerToResult(updatedComputer);
		computerDao.updateComputer(oldName, updatedInfo);
	}

}

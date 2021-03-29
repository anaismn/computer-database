package com.excilys.training.cbd.service;

import java.util.ArrayList;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.ComputerDAO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.mapper.ComputerMapper;

public class ServiceComputer {
	
	private final static Long NO_COMPANY = 0L;
	
	static ComputerDAO computerDao = new ComputerDAO();
	
	public static ArrayList<Computer> getAllComputers() throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getAllComputers();
		ArrayList<Company> companies = ServiceCompany.getAllCompanies();
		for(int i=0; i<result.size(); i=i+5) {
			if(NO_COMPANY != result.get(i+4)) {
				for(Company company : companies) {
					if (company.getId() == result.get(i+4) ) {
						result.set(i+4, company); 
						break;
					}
				}
			}
			computers.add( ComputerMapper.resultToComputer(result.subList(i, i+5)) );
		}
		return computers;
	}
	
	public static void getOneComputer(Long idSearched) throws DAOException {
		computerDao.getOneComputer(idSearched);
	}
	
	public static Computer getOneComputer(String nameSearched) throws DAOException {
		ArrayList<Object> result = computerDao.getOneComputer(nameSearched);
		ArrayList<Company> companies = ServiceCompany.getAllCompanies();
		if(NO_COMPANY != result.get(4)) {
			for(Company company : companies) {
				if (company.getId() == result.get(4) ) {
					result.set(4, company); 
					break;
				}
			}
		}
		Computer computer  = ComputerMapper.resultToComputer(result) ;
		return computer;
	}
	
	public static ArrayList<Computer> getComputersFiltered(String nameSearched) throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getComputersFiltered(nameSearched);
		ArrayList<Company> companies = ServiceCompany.getAllCompanies();
		for(int i=0; i<result.size(); i=i+5) {
			if(NO_COMPANY != result.get(i+4)) {
				for(Company company : companies) {
					if (company.getId() == result.get(i+4) ) {
						result.set(i+4, company); 
						break;
					}
				}
			}
			computers.add( ComputerMapper.resultToComputer(result.subList(i, i+5)) );
		}
		return computers;
	}
	
	public static void setNewComputer(Computer newComputer) throws DAOException {
		ArrayList<Object> informations = ComputerMapper.computerToResult(newComputer);
		computerDao.setNewComputer(informations);
	}
	
	public static void deleteComputer(Long id) throws DAOException {
		computerDao.deleteComputer(id);
	}
	
	public static void deleteComputer(String name) throws DAOException {
		computerDao.deleteComputer(name);
	}
	
	public static void updateComputer(String oldName, Computer updatedComputer) throws DAOException {
		ArrayList<Object> updatedInfo = ComputerMapper.computerToResult(updatedComputer);
		computerDao.updateComputer(oldName,  updatedInfo);
	}

}

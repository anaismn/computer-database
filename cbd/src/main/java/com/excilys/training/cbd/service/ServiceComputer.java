package com.excilys.training.cbd.service;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.ComputerDAO;
import com.excilys.training.cbd.mapper.ComputerMapper;

public class ServiceComputer {
	
	static ComputerDAO computerDao = new ComputerDAO();
	
	public static void getAllComputers(){
		computerDao.getAllComputers();
	}
	
	public static void getOneComputer(Long idSearched) {
		computerDao.getOneComputer(idSearched);
	}
	
	public static Optional<Computer> getOneComputer(String nameSearched){
		ResultSet result = computerDao.getOneComputer(nameSearched).orElse(null);
		return ComputerMapper.resultToComputer(result);
	}
	
	public static void setNewComputer(Computer newComputer) {
		computerDao.setNewComputer(newComputer);
	}
	
	public static void deleteComputer(Long id) {
		computerDao.deleteComputer(id);
	}
	
	public static void deleteComputer(String name) {
		computerDao.deleteComputer(name);
	}
	
	public static void updateComputer(String oldName, Computer oldComputer, String newName, String introduced, String discontinued, Long companyID) {
		computerDao.updateComputer(oldName,  oldComputer, newName, introduced, discontinued, companyID);
	}

}

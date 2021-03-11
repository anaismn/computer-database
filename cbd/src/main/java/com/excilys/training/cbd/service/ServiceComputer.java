package com.excilys.training.cbd.service;

import java.text.ParseException;
import java.util.ArrayList;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.ComputerDAO;

public class ServiceComputer {
	
	static ComputerDAO computerDao = new ComputerDAO();
	
	public static void getAllComputers(){
		computerDao.getAllComputers();
	}
	
	public static void getOneComputer(Long idSearched) {
		computerDao.getOneComputer(idSearched);
	}
	
	public static Computer getOneComputer(String nameSearched){
		return computerDao.getOneComputer(nameSearched);
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

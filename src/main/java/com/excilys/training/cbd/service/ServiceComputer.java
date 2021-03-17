package com.excilys.training.cbd.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.ComputerDAO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.mapper.ComputerMapper;

public class ServiceComputer {
	
	static ComputerDAO computerDao = new ComputerDAO();
	
	public static ArrayList<Computer> getAllComputers() throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getAllComputers();
		for(int i=0; i<result.size(); i=i+2) {
			computers.add( ComputerMapper.resultToComputer(result.subList(i, i+2)) );
		}
		return computers;
	}
	
	public static void getOneComputer(Long idSearched) {
		computerDao.getOneComputer(idSearched);
	}
	
	public static Computer getOneComputer(String nameSearched) throws DAOException {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ArrayList<Object> result = computerDao.getOneComputer(nameSearched);
		for(int i=0; i<result.size(); i=i+5) {
			computers.add( ComputerMapper.resultToComputer(result.subList(i, i+5)) );
		}
		return computers.get(0);
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

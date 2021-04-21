package com.excilys.training.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.training.dao.ComputerDAO;
import com.excilys.training.dao.DAOException;
import com.excilys.training.mapper.ComputerMapper;
import com.excilys.training.model.Computer;
import com.excilys.training.model.ComputerDTO;
import com.excilys.training.model.ComputerTable;

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

	public long countComputers(String nameSearched) throws DAOException {
		return computerDao.countComputers(nameSearched);
	}

	public List<ComputerDTO> getAllComputers(String columnOrdering, int limit, int offset) throws DAOException {
		List<ComputerTable> result = computerDao.getAllComputers(columnOrdering, limit, offset);
		List<ComputerDTO> listComputerDTO = new ArrayList<ComputerDTO>();
		for (ComputerTable computerTable : result) {
			listComputerDTO.add(computerMapper.tableToDTO(computerTable));
		}
		return listComputerDTO;
	}

	public ComputerDTO getOneComputer(Long idSearched) throws DAOException {
		ComputerTable result = computerDao.getOneComputer(idSearched);
		return computerMapper.tableToDTO(result);
	}

	public List<ComputerDTO> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset) {
		List<ComputerTable> result = computerDao.getComputersFiltered(nameSearched, columnOrdering, limit, offset);
		List<ComputerDTO> listComputerDTO = new ArrayList<ComputerDTO>();
		for (ComputerTable computerTable : result) {
			listComputerDTO.add(computerMapper.tableToDTO(computerTable));
		}
		return listComputerDTO;
	}

	public void setNewComputer(Computer newComputer) throws DAOException {
		ComputerTable computerTable = computerMapper.computerToTable(newComputer);
		computerDao.setNewComputer(computerTable);
	}

	public void deleteComputer(Long id) throws DAOException {
		computerDao.deleteComputer(id);
	}

	public void updateComputer(Computer updatedComputer, Long id) throws DAOException {
		ComputerTable computerTable = computerMapper.computerToTable(updatedComputer);
		computerDao.updateComputer(computerTable, id);
	}

}

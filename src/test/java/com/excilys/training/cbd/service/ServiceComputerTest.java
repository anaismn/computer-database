package com.excilys.training.cbd.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.DAOException;

public class ServiceComputerTest {

	final int NUMBER_OF_COMPUTERS = 552;
	final String COMPUTER_NAME = "JUnitTEST";
	final LocalDate INTRODUCED ;
	
	
	@Test
	public void testGetAllComputers() throws DAOException, SQLException {
		ArrayList<Computer> computers = ServiceComputer.getAllComputers();
		assertEquals(NUMBER_OF_COMPUTERS, computers.size());
	}

	@Test
	public void testGetOneComputerString() throws SQLException {
		Computer computer = ServiceComputer.getOneComputer(COMPUTER_NAME);
	}

	@Test
	public void testSetNewComputer() {
		Computer computer = new Computer.Builder(COMPUTER_NAME)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany_id(1L)
				.build();
	        System.out.println(computer);
		ServiceComputer.setNewComputer(computer);
		
	}

	@Test
	public void testDeleteComputerString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateComputer() {
		fail("Not yet implemented");
	}

}

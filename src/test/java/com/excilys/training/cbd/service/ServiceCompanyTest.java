package com.excilys.training.cbd.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.DAOException;

public class ServiceCompanyTest {

	@Test
	public void testGetAllCompanies() throws SQLException {
		ArrayList<Company> companies = ServiceCompany.getAllCompanies();
		assertEquals(42,companies.size());
	}

	@Test
	public void testGetOneCompany() throws DAOException, SQLException {
		Company company = ServiceCompany.getOneCompany("Sony");
		long idFound = company.getID();
		assertEquals(17, idFound);
	}

}

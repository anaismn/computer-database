package com.excilys.training.cbd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.training.cbd.model.Computer;

public class ComputerMapper {
	
	public static Optional<Computer> resultToComputer(ResultSet result) {
		try {
			while ( result.next() ) {
				Long id = result.getLong("id");
				String name = result.getString("name");
				System.out.println("name : "+name);
				java.sql.Date introduced = null;
				java.sql.Date discontinued = null;
				if(result.getString("introduced") != null) {
					 introduced = result.getDate("introduced");
				}
				if(result.getString("discontinued") != null) {
					discontinued = result.getDate("discontinued");
				}
				Long company_id = result.getLong("company_id");
				
				Computer computer = new Computer( name, introduced, discontinued, company_id);
				return Optional.of(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}

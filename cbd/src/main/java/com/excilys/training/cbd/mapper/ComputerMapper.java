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
				String introduced = result.getString("introduced");
				String discontinued = result.getString("discontinued");
				Long company_id = result.getLong("company_id");
				
				Computer computer = new Computer.Builder(name)
						.setIntroduced(introduced)
						.setDiscontinued(discontinued)
						.setCompany_id(company_id)
						.build();
				return Optional.of(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	
}

package com.excilys.training.cbd.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.training.cbd.model.Computer;

public class ComputerMapper {
	
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static Computer resultToComputer(List<Object> list) {
		Computer computer;
		Long id = (Long) list.get(0);
		String name = (String) list.get(1);
		if(list.size()>2) {
			LocalDate introduced = (LocalDate) list.get(2);
			LocalDate discontinued = (LocalDate) list.get(3);
			Long company_id = (Long) list.get(4);
			computer = new Computer.Builder(name)
					.setID(id)
					.setIntroduced(introduced)
					.setDiscontinued(discontinued)
					.setCompany_id(company_id)
					.build();
		}else {
			computer = new Computer.Builder(name)
					.setID(id)
					.build();
		}
		
		
		return computer;
	}
	
	public static ArrayList<Object> computerToResult(Computer computer) {
		ArrayList<Object> informations = new ArrayList<Object>();
		informations.add(computer.getID());
		informations.add(computer.getName());
		informations.add(Date.valueOf(computer.getIntroduced()));
		informations.add(Date.valueOf(computer.getDiscontinued()));
		informations.add(computer.getCompanyID());
		return informations;
	}
	
}

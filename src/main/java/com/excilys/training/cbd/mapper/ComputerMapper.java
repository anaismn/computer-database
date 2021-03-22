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

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;

public class ComputerMapper {
	
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static Computer resultToComputer(List<Object> list) {
		Long id = (Long) list.get(0);
		String name = (String) list.get(1);
		
		Computer.Builder builder = new Computer.Builder(name)
				.setId(id);
		
		if(list.size()>2) {
			LocalDate introduced = (LocalDate) list.get(2);
			LocalDate discontinued = (LocalDate) list.get(3);
			builder = builder.setIntroduced(introduced)
					.setDiscontinued(discontinued);
			if(list.get(4).getClass().getName().equals("com.excilys.training.cbd.model.Company")) {
				Company company = (Company) list.get(4);
				builder = builder.setCompany(company);
			}
		}
		return builder.build();
	}
	
	public static ArrayList<Object> computerToResult(Computer computer) {
		ArrayList<Object> informations = new ArrayList<Object>();
		informations.add(computer.getId());
		informations.add(computer.getName());
		informations.add(Date.valueOf(computer.getIntroduced()));
		informations.add(Date.valueOf(computer.getDiscontinued()));
		informations.add(computer.getCompany().getId());
		return informations;
	}
	
	public static ComputerDTO computerToDTO(Computer computer) {
		String nameDTO = computer.getName();
		String introducedDTO = null!= computer.getIntroduced() ? computer.getIntroduced().format(dateFormat): "";
		String discontinuedDTO = null != computer.getDiscontinued() ? computer.getDiscontinued().format(dateFormat) : "" ; 
		String companyDTO = null != computer.getCompany() ? computer.getCompany().getName() : "" ; 
		ComputerDTO computerDTO = new ComputerDTO( nameDTO, introducedDTO , discontinuedDTO , companyDTO ) ;
		return computerDTO;
	}	
	
	public static Computer dtoToComputer(ComputerDTO computerDTO, Company company) {
		String name = computerDTO.getName();
		LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
		LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
		Computer computer = new Computer.Builder(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company)
				.build() ;
		return computer;
	}
	
	
}

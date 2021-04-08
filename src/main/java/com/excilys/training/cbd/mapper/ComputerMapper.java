package com.excilys.training.cbd.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;

@Component
public class ComputerMapper {

	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Computer resultToComputer(List<Object> list) {
		Long id = (Long) list.get(0);
		String name = (String) list.get(1);

		Computer.Builder builder = new Computer.Builder(name).setId(id);

		if (list.size() > 2) {
			LocalDate introduced = (LocalDate) list.get(2);
			LocalDate discontinued = (LocalDate) list.get(3);
			builder = builder.setIntroduced(introduced).setDiscontinued(discontinued);
			if (list.get(4).getClass().getName().equals("com.excilys.training.cbd.model.Company")) {
				Company company = (Company) list.get(4);
				builder = builder.setCompany(company);
			}
		}
		return builder.build();
	}

	public ArrayList<Object> computerToResult(Computer computer) {
		ArrayList<Object> informations = new ArrayList<Object>();
		informations.add(computer.getId());
		informations.add(computer.getName());
		informations.add(null != computer.getIntroduced() ? Date.valueOf(computer.getIntroduced()) : null);
		informations.add(null != computer.getDiscontinued() ? Date.valueOf(computer.getDiscontinued()) : null);
		informations.add(computer.getCompany().getId());
		return informations;
	}

	public ComputerDTO computerToDTO(Computer computer) {
		Long idDTO = computer.getId();
		String nameDTO = computer.getName();
		String introducedDTO = null != computer.getIntroduced() ? computer.getIntroduced().format(dateFormat) : "";
		String discontinuedDTO = null != computer.getDiscontinued() ? computer.getDiscontinued().format(dateFormat)
				: "";
		String companyDTO = null != computer.getCompany() ? computer.getCompany().getName() : "";
		ComputerDTO computerDTO = new ComputerDTO(idDTO, nameDTO, introducedDTO, discontinuedDTO, companyDTO);
		return computerDTO;
	}

	public Computer dtoToComputer(ComputerDTO computerDTO, Company company) {
		String name = computerDTO.getName();
		System.out.println(computerDTO.getDiscontinued());
		LocalDate introduced = "" != computerDTO.getIntroduced() ? LocalDate.parse(computerDTO.getIntroduced()) : null;
		LocalDate discontinued = "" != computerDTO.getDiscontinued() ? LocalDate.parse(computerDTO.getDiscontinued())
				: null;
		Computer computer = new Computer.Builder(name).setIntroduced(introduced).setDiscontinued(discontinued)
				.setCompany(company).build();
		return computer;
	}

}

package com.excilys.training.mapper;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.model.ComputerDTO;
import com.excilys.training.model.ComputerTable;

@Component
public class ComputerMapper {

	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Autowired
	CompanyMapper companyMapper;

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

	public Object[] computerToResult(Computer computer) {
		Object[] informations = new Object[] { computer.getName(),
				null != computer.getIntroduced() ? Date.valueOf(computer.getIntroduced()) : null,
				null != computer.getDiscontinued() ? Date.valueOf(computer.getDiscontinued()) : null,
				null != computer.getCompany() ? computer.getCompany().getId() : null, };
		System.out.println(informations.toString());
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

	public ComputerDTO tableToDTO(ComputerTable computerTable) {
		Long idDTO = computerTable.getId();
		String nameDTO = computerTable.getName();
		String introducedDTO = null != computerTable.getIntroduced() ? computerTable.getIntroduced().toString() : "";
		String discontinuedDTO = null != computerTable.getDiscontinued() ? computerTable.getDiscontinued().toString()
				: "";
		String companyDTO = null != computerTable.getCompanyId() ? computerTable.getCompany().getName() : "";
		return new ComputerDTO(idDTO, nameDTO, introducedDTO, discontinuedDTO, companyDTO);
	}

	public ComputerTable computerToTable(Computer computer) {
		ComputerTable computerTable = new ComputerTable();
		computerTable.setId(computer.getId());
		computerTable.setName(computer.getName());
		computerTable.setIntroduced(computer.getIntroduced());
		computerTable.setDiscontinued(computer.getDiscontinued());
		computerTable.setCompanyId((null != computer.getCompany() ? computer.getCompany().getId() : null));
		return computerTable;
	}

}

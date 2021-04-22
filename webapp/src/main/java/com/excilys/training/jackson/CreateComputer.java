package com.excilys.training.jackson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.training.dao.DAOException;
import com.excilys.training.mapper.ComputerMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.model.ComputerDTO;
import com.excilys.training.service.ServiceCompany;
import com.excilys.training.service.ServiceComputer;
import com.excilys.training.web.ListComputersParams;

@RestController
public class CreateComputer {

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	ListComputersParams listComputersParams;
	@Autowired
	private ListComputers listComputers;
	
	@GetMapping(value = "/createComputer")
	public List<Company> getAddComputer() {
		List<Company> companies = new ArrayList<>();
		try {
			companies = serviceCompany.getAllCompanies();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return companies;

	}

	@PostMapping(value = "/createComputer")
	public void postAddComputer(@RequestBody ComputerDTO computerDTO) {

		Company company = null;
		if (!"null".equals(computerDTO.getCompany())) {
			try {
				company = serviceCompany.getOneCompany(Long.parseLong(computerDTO.getCompany()));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Computer computer = computerMapper.dtoToComputer(computerDTO, company);

		try {
			serviceComputer.setNewComputer(computer);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}

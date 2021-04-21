package com.excilys.training.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.training.dao.DAOException;
import com.excilys.training.mapper.ComputerMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.model.ComputerDTO;
import com.excilys.training.service.ServiceCompany;
import com.excilys.training.service.ServiceComputer;

@Controller
public class ListComputersController {

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	ListComputersParams listComputersParams;

	@GetMapping(value = "/listComputers")
	public ModelAndView getListComputers(
			@RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(name = "limitByPages", required = false) Integer limitByPages,
			@RequestParam(name = "orderBy", required = false, defaultValue = "id") String orderBy,
			@RequestParam(name = "search", required = false) String nameSearched) {
		ModelAndView modelAndView = new ModelAndView("listComputers");

		if (null != limitByPages) {
			listComputersParams.setLimitByPages(limitByPages);
		}
		if (null != nameSearched) {
			listComputersParams.setNameSearched(nameSearched);

		}

		int offset = (pageNumber - 1) * listComputersParams.getLimitByPages();
		long count = 0;
			try {
				count = serviceComputer.countComputers(listComputersParams.getNameSearched());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		List<ComputerDTO> computersDTO = null;
		computersDTO = serviceComputer.getComputersFiltered(listComputersParams.getNameSearched(), orderBy,
				listComputersParams.getLimitByPages(), offset);
		
		modelAndView.addObject("computers", computersDTO);
		modelAndView.addObject("nameSearched", listComputersParams.getNameSearched());
		modelAndView.addObject("pageNumber", pageNumber);
		modelAndView.addObject("numberOfComputers", count);
		modelAndView.addObject("numberOfPages", (count - 1) / listComputersParams.getLimitByPages() + 1);
		modelAndView.addObject("limitByPages", listComputersParams.getLimitByPages());
		modelAndView.setViewName("listComputers");
		return modelAndView;
	}

	@PostMapping("/listComputers")
	public ModelAndView postListComputers(@RequestParam(name = "selection") String computerToDelete) {
		if (null != computerToDelete) {
			List<String> delete = Arrays.asList(computerToDelete.split(","));
			for (String computerId : delete) {
				try {
					serviceComputer.deleteComputer(Long.parseLong(computerId));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		}
		return getListComputers(1, null, "id", null);
	}

	@GetMapping(value = "/addComputer")
	public ModelAndView getAddComputer() {
		ModelAndView modelAndView = new ModelAndView("addComputer");
		List<Company> companies = new ArrayList<>();
		try {
			companies = serviceCompany.getAllCompanies();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		modelAndView.addObject("listCompanies", companies);
		return modelAndView;

	}

	@PostMapping(value = "/addComputer")
	public ModelAndView postAddComputer(@RequestParam(name = "computerName", required = true) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "company", required = false) String companyId) {

		Company company = null;
		if (!"null".equals(companyId)) {
			System.out.println(companyId);
			try {
				company = serviceCompany.getOneCompany(Long.parseLong(companyId));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ComputerDTO computerDTO = new ComputerDTO(null, name, introduced, discontinued, null);

		Computer computer = computerMapper.dtoToComputer(computerDTO, company);

		try {
			serviceComputer.setNewComputer(computer);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getListComputers(1, null, "id", null);
	}

	@GetMapping(value = "/editComputer")
	public ModelAndView getEditComputer(@RequestParam(name = "computer", required = true) Long id) {
		ModelAndView modelAndView = new ModelAndView("editComputer");
		List<Company> companies = null;
		ComputerDTO computerDTO = null;
		try {
			companies = serviceCompany.getAllCompanies();
			computerDTO =  serviceComputer.getOneComputer(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		modelAndView.addObject("listCompanies", companies);
		modelAndView.addObject("computer", computerDTO);
		return modelAndView;
	}
	
	@PostMapping(value = "/editComputer")
	public ModelAndView postEditComputer(@RequestParam(name = "computerId", required = true) Long id,
			@RequestParam(name = "computerName", required = true) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "companyId", required = false) String companyId) {

		Company company = null;
		if (!"null".equals(companyId)) {
			System.out.println(companyId);
			try {
				company = serviceCompany.getOneCompany(Long.parseLong(companyId));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ComputerDTO computerDTO = new ComputerDTO(id, name, introduced, discontinued, null);

		Computer computer = computerMapper.dtoToComputer(computerDTO, company);

		try {
			serviceComputer.updateComputer(computer, id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getListComputers(1, null, "id", null);
	}
	
	
}

package com.excilys.training.cbd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.service.ServiceComputer;

@Controller
@RequestMapping("/listComputers")
public class ListComputersController {

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	ComputerMapper computerMapper;
	
	 @RequestMapping(value = "/listComputers", method = RequestMethod.GET)
	    public ModelAndView listComputers() {
		 List<Computer> computers = serviceComputer.getComputersFiltered("", "id", 10, 0);
	        return new ModelAndView("listComputers", "computers", computers);
	    }
	
}

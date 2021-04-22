package com.excilys.training.jackson;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.training.dao.DAOException;
import com.excilys.training.mapper.ComputerMapper;
import com.excilys.training.model.ComputerDTO;
import com.excilys.training.service.ServiceComputer;
import com.excilys.training.web.ListComputersParams;

@RestController
public class ListComputers {

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	ComputerMapper computerMapper;
	@Autowired
	ListComputersParams listComputersParams;

	@GetMapping(value = "/allComputers")
	public List<ComputerDTO> getListComputers() {
		
		List<ComputerDTO> computersDTO = null;
		try {
			computersDTO = serviceComputer.getAllComputers("id", 10, 0);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return computersDTO;
	}
	
	
	@PostMapping("/allComputers")
	public void postListComputers(@RequestBody String computerToDelete) {
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
		 getListComputers();
	}
	
	
}

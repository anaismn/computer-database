package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceComputer;

public class ListComputers extends HttpServlet  {
	public static final String NBR_COMPUTER = "test";
	public static final String LIST_COMPUTERS = "listComputers";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		ArrayList<Computer> computers;
		ArrayList<Object> result = new ArrayList<>();
		ArrayList<ComputerDTO> computersDTO = new ArrayList<>();
		
		try {
			computers = ServiceComputer.getAllComputers();
			computers.forEach((computer) -> computersDTO.add(ComputerMapper.computerToDTO(computer)));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		request.setAttribute( LIST_COMPUTERS, computersDTO );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/listComputers.jsp" ).forward( request, response );
	}

}
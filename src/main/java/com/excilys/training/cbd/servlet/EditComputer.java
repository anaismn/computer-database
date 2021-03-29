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
import com.excilys.training.cbd.service.ServiceCompany;
import com.excilys.training.cbd.service.ServiceComputer;

public class EditComputer extends HttpServlet  {
	
	public static final String COMPUTER_TO_EDIT = "computer";
	public static final String LIST_COMPANIES = "listCompanies";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		ArrayList<Company> companies = new ArrayList<>();
		try {
			companies = ServiceCompany.getAllCompanies();
			
			System.out.println(request.getParameter(COMPUTER_TO_EDIT));
			Computer computer = ServiceComputer.getOneComputer(request.getParameter(COMPUTER_TO_EDIT));
			System.out.println(computer.getCompany());
			ComputerDTO computerDTO = ComputerMapper.computerToDTO(computer);
			System.out.println(computerDTO.getCompany());
			request.setAttribute( LIST_COMPANIES, companies );
			request.setAttribute( COMPUTER_TO_EDIT, computerDTO );
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/editComputer.jsp" ).forward( request, response );
	}
	
}

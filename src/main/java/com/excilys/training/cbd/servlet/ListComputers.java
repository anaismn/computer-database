package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.cbd.controller.Pagination;
import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceComputer;

public class ListComputers extends HttpServlet  {
	public static final String LIST_COMPUTERS = "listComputers";
	public static final String NUMBER_OF_COMPUTERS = "numberOfComputers";
	public static final String PARAMS_PAGE_NUMBER = "page";
	public static final String COMPUTERS_BY_PAGE = "limitByPages";
	public static final String PAGE_NUMBER = "pageNumber";
	public static int limitByPages = 10;
	public static int pageNumber = 1;
	ArrayList<Computer> computers;
	ArrayList<Object> result;
	ArrayList<ComputerDTO> computersDTO;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		computersDTO= new ArrayList<ComputerDTO>();
		try {
			computers = ServiceComputer.getAllComputers();
			computers.forEach((computer) -> computersDTO.add(ComputerMapper.computerToDTO(computer)));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(null !=request.getParameter(COMPUTERS_BY_PAGE)) {
			limitByPages = Integer.parseInt(request.getParameter(COMPUTERS_BY_PAGE));
		}
		
		Pagination pagination = new Pagination(limitByPages, computersDTO);
		System.out.println(request.getParameter(PARAMS_PAGE_NUMBER));
		if(null !=request.getParameter(PARAMS_PAGE_NUMBER)) {
			pageNumber = Integer.parseInt(request.getParameter(PARAMS_PAGE_NUMBER));
		}
		
		request.setAttribute(NUMBER_OF_COMPUTERS, computersDTO.size());
		request.setAttribute(PAGE_NUMBER, pageNumber);
		request.setAttribute( LIST_COMPUTERS, pagination.getPageIndex(pageNumber-1) );
		
		System.out.println("URI : "+request.getRequestURI());
		System.out.println(request.getQueryString());
		
		Enumeration parametres = request.getParameterNames();
	    System.out.println("Affichage des informations sur les paramètres de la requête");
	    while (parametres.hasMoreElements()) {
		    String nomParametre = (String) parametres.nextElement(); 
		    System.out.println("Le paramètre " + nomParametre  + " . " + request.getParameter(nomParametre));
	   }
	    
	    System.out.println(request.getParameter(COMPUTERS_BY_PAGE));
	    
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/listComputers.jsp" ).forward( request, response );
	}

}
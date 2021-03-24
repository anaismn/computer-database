package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

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
	public static final String LIST_COMPUTERS = "listComputers";
	public static int limitByPages = 50;
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
		
		request.setAttribute( LIST_COMPUTERS, computersDTO );
		
		System.out.println("URI : "+request.getRequestURI());
		System.out.println(request.getQueryString());
		
		Enumeration parametres = request.getParameterNames();
	    System.out.println("Affichage des informations sur les paramètres de la requête");
	    while (parametres.hasMoreElements()) {
		    String nomParametre = (String) parametres.nextElement(); 
		    System.out.println("Le paramètre " + nomParametre  + " . " + request.getParameter(nomParametre));
	   }
	    
	    System.out.println(request.getParameter("limitByPages"));
	    
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/listComputers.jsp" ).forward( request, response );
	}

}
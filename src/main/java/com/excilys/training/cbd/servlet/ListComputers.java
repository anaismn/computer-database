package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceComputer;

public class ListComputers extends HttpServlet  {
	public static final String NBR_COMPUTER = "test";
	public static final String LIST_COMPUTERS = "listComputers";
	
	ArrayList<Computer> computers;
	ArrayList<Object> result = new ArrayList<>();
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
		throws ServletException, IOException{
		
		try {
			computers = ServiceComputer.getAllComputers();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		computers.forEach((computer) -> System.out.println( " - id : "+ computer.getID() + " = Name : "+computer.getName())) ;
		request.setAttribute( NBR_COMPUTER, "grogro" );
		request.setAttribute( LIST_COMPUTERS, computers );
//		request.setAttribute(, response);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/listComputers.jsp" ).forward( request, response );
	}

}
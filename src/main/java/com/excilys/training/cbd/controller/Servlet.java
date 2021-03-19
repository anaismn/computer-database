package com.excilys.training.cbd.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
	public static final String NBR_COMPUTER = "test";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
		throws ServletException, IOException{
		
		request.setAttribute( NBR_COMPUTER, "grogro" );
		
		response.setContentType("text/html");
		response.setCharacterEncoding( "UTF-8" );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
	}

}

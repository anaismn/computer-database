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

public class AddComputer extends HttpServlet  {

	public static final String LIST_COMPANIES = "listCompanies";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		ArrayList<Company> companies = new ArrayList<>();
		try {
			companies = ServiceCompany.getAllCompanies();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.setAttribute( LIST_COMPANIES, companies );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/addComputer.jsp" ).forward( request, response );
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("computerName");
        System.out.println(name);
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String company_id = request.getParameter("company");
        System.out.println(company_id);
        Company company = null;
		try {
			company = ServiceCompany.getOneCompany(Long.parseLong(company_id));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ComputerDTO computerDTO = new ComputerDTO(name, introduced, discontinued, company.getName());
        
        Computer computer = ComputerMapper.dtoToComputer(computerDTO, company);
        try {
			ServiceComputer.setNewComputer(computer);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.sendRedirect("/cbd-maven/listComputers");
    }
	
}

package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceCompany;
import com.excilys.training.cbd.service.ServiceComputer;

//@Component
//@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {

	public static final String COMPUTER_TO_EDIT = "computer";
	public static final String LIST_COMPANIES = "listCompanies";

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	ServiceCompany serviceCompany;
	@Autowired 
	ComputerMapper computerMapper;

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = new ArrayList<>();
		try {
			companies = serviceCompany.getAllCompanies();

			Computer computer = serviceComputer.getOneComputer(Long.parseLong(request.getParameter(COMPUTER_TO_EDIT)));
			ComputerDTO computerDTO = computerMapper.computerToDTO(computer);
			request.setAttribute(LIST_COMPANIES, companies);
			request.setAttribute(COMPUTER_TO_EDIT, computerDTO);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/view/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("computerName");
		if (!"".equals(name.trim())) {
			String introduced = request.getParameter("introduced");
			String discontinued = request.getParameter("discontinued");
			String company_id = request.getParameter("companyId");
			Company company = null;
			System.out.println(company_id);
			if (!"".equals(company_id)) {
				System.out.println(null != company_id);
			}
			try {
				company = serviceCompany.getOneCompany(Long.parseLong(company_id));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ComputerDTO computerDTO = new ComputerDTO(null, name, introduced, discontinued, company.getName());

			Computer computer = computerMapper.dtoToComputer(computerDTO, company);

			try {
				serviceComputer.updateComputer(computer, Long.parseLong(request.getParameter("computerId")));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("/cbd-maven/listComputers");
		} else {
			doGet(request, response);
		}
	}

}

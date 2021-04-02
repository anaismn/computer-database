package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceComputer;

public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String LIST_COMPUTERS = "listComputers";
	public static final String NUMBER_OF_COMPUTERS = "numberOfComputers";
	public static final String PARAMS_PAGE_NUMBER = "page";
	public static final String COMPUTERS_BY_PAGE = "limitByPages";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String NUMBER_OF_PAGES = "numberOfPages";
	public static final String NAME_SEARCHED = "nameSearched";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String nameSearched = request.getParameter("search");
		String orderBy = null != request.getParameter("sort") ? request.getParameter("sort") : "id";

		int limitByPages = null != request.getParameter(COMPUTERS_BY_PAGE)
				? Integer.parseInt((String) request.getParameter(COMPUTERS_BY_PAGE))
				: 100;

		int pageNumber = null != request.getParameter(PARAMS_PAGE_NUMBER)
				? Integer.parseInt((String) request.getParameter(PARAMS_PAGE_NUMBER))
				: 0;
		ArrayList<Computer> computers = retrieveComputers(nameSearched, orderBy, limitByPages, pageNumber);
		try {
			displayComputers(request, session, computers, limitByPages, pageNumber);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		session.setAttribute(NAME_SEARCHED, nameSearched);

		this.getServletContext().getRequestDispatcher("/WEB-INF/view/listComputers.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] computerToDelete = request.getParameterValues("cb");
		if (null != computerToDelete) {
			for (String computerId : computerToDelete) {
				try {
					ServiceComputer.deleteComputer(Long.parseLong(computerId));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/view/listComputers.jsp").forward(request, response);
	}

	private ArrayList<Computer> retrieveComputers(String nameSearched, String orderBy, int limitByPages,
			int pageNumber) {
		ArrayList<Computer> computers = new ArrayList<>();
		try {
			if (null != nameSearched) {
				computers = ServiceComputer.getComputersFiltered(nameSearched, orderBy, limitByPages, pageNumber);
			} else {
				computers = ServiceComputer.getAllComputers(orderBy, limitByPages, pageNumber);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return computers;
	}

	private void displayComputers(HttpServletRequest request, HttpSession session, ArrayList<Computer> computers,
			int limitByPages, int pageNumber) throws DAOException {
		ArrayList<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();

		computers.forEach((computer) -> computersDTO.add(ComputerMapper.computerToDTO(computer)));

		session.setAttribute(NUMBER_OF_COMPUTERS, ServiceComputer.countComputers());
		session.setAttribute(NUMBER_OF_PAGES, ServiceComputer.countComputers() / limitByPages);
		session.setAttribute(PAGE_NUMBER, pageNumber);
		session.setAttribute(LIST_COMPUTERS, computersDTO);
	}

}
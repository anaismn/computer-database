package com.excilys.training.cbd.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;
import com.excilys.training.cbd.model.ComputerDTO;
import com.excilys.training.cbd.persistence.DAOException;
import com.excilys.training.cbd.service.ServiceComputer;

@Component
@WebServlet("/listComputers")
public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String LIST_COMPUTERS = "listComputers";
	public static final String NUMBER_OF_COMPUTERS = "numberOfComputers";
	public static final String PARAMS_PAGE_NUMBER = "page";
	public static final String COMPUTERS_BY_PAGE = "limitByPages";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String NUMBER_OF_PAGES = "numberOfPages";
	public static final String NAME_SEARCHED = "nameSearched";

	@Autowired
	private ServiceComputer serviceComputer;

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}

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
					serviceComputer.deleteComputer(Long.parseLong(computerId));
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
				computers = serviceComputer.getComputersFiltered(nameSearched, orderBy, limitByPages, pageNumber);
			} else {
				System.out.println(
						"orderBy : " + orderBy + " & limitByPages : " + limitByPages + " pageNumber : " + pageNumber);
				computers = serviceComputer.getAllComputers(orderBy, limitByPages, pageNumber);
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

		session.setAttribute(NUMBER_OF_COMPUTERS, serviceComputer.countComputers());
		session.setAttribute(NUMBER_OF_PAGES, serviceComputer.countComputers() / limitByPages);
		session.setAttribute(PAGE_NUMBER, pageNumber);
		session.setAttribute(LIST_COMPUTERS, computersDTO);
	}

}
package com.excilys.training.cbd.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ComputerDAO {
	private static final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM computer WHERE computer.name LIKE ? ";
	private static final String SELCT_ALL_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id ORDER BY computer.";
	private static final String SELCT_ONE_COMPUTER = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.id = ? ";
	private static final String FILTER_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.name LIKE ? ORDER BY computer.";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ? ";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";

//	@Autowired
//	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	ComputerDAO(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	public List<Computer> getAllComputers(String columnOrdering, int limit, int offset) {
		return jdbcTemplate.query(SELCT_ALL_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset + ";",
				new ComputerMapper());
	}

	public int countComputers(String nameSearched) {
		return jdbcTemplate.queryForObject(COUNT_COMPUTERS + ";", Integer.class, "%" + nameSearched + "%");
	}

	public Computer getOneComputer(Long id) {
		return (Computer) jdbcTemplate.queryForObject(SELCT_ONE_COMPUTER, new ComputerMapper(), id);
	}

	public List<Computer> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset) {
		return jdbcTemplate.query(FILTER_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset + ";",
				new ComputerMapper(), "%" + nameSearched + "%");
	}

	public void setNewComputer(Object[] informations) {
		jdbcTemplate.update(CREATE_COMPUTER, informations);
	}

	public void deleteComputer(Long id) {
		jdbcTemplate.update(DELETE_COMPUTER, id);
	}

	public void updateComputer(Computer updatedComputer, Long id) {
		jdbcTemplate.update(UPDATE_COMPUTER, 
				updatedComputer.getName(), 
				updatedComputer.getIntroduced(), 
				updatedComputer.getDiscontinued(), 
				updatedComputer.getCompany().getId(), 
				id);
	}
}

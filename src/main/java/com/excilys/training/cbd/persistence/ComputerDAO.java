package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private static final String SELCT_ONE_COMPUTER = "SELECT * FROM computer WHERE id = ? ";
	private static final String FILTER_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.name LIKE ? ORDER BY computer.";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ? ";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE name = ? ;";

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	ComputerDAO(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	
	public List<Computer> getAllComputers(String columnOrdering, int limit, int offset) {
		return jdbcTemplate.query(SELCT_ALL_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset + ";",
				new ComputerMapper());
	}

	public int countComputers(String nameSearched) {
		return jdbcTemplate.queryForObject(COUNT_COMPUTERS + ";",
				Integer.class, "%" + nameSearched + "%");
	}
	
	public Computer getOneComputer(Long id) {
		return (Computer) jdbcTemplate.queryForObject(SELCT_ONE_COMPUTER + ";",
				new ComputerMapper(), id);
	}

	public List<Computer> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset) {
		return jdbcTemplate.query(FILTER_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset + ";",
				new ComputerMapper(), "%" + nameSearched + "%");
	}

	
	public void setNewComputer(ArrayList<Object> informations) throws DAOException {
		int resultat = 0;
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(CREATE_COMPUTER);) {
			preStatement.setString(1, (String) informations.get(1));
			preStatement.setDate(2, (Date) informations.get(2));
			preStatement.setDate(3, (Date) informations.get(3));
			preStatement.setLong(4, (Long) informations.get(4));
			resultat = preStatement.executeUpdate();
			if (resultat == 1) {
				System.out.println("Ajout réussie! ");
			} else {
				System.out.println("Echec de l'ajout ");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void deleteComputer(Long id) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(DELETE_COMPUTER);) {

			preStatement.setLong(1, id);
			int resultat = preStatement.executeUpdate();

			if (resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : " + resultat);
			} else {
				System.out.println("Echec de la suppression ");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void deleteComputer(String name) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement("DELETE FROM computer WHERE name = ? ");) {
			preStatement.setString(1, name);
			int resultat = preStatement.executeUpdate();
			if (resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : " + resultat);
			} else {
				System.out.println("Echec de la suppression ");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void updateComputer(String oldName, ArrayList<Object> updatedInfo) throws DAOException {
		int resultat = 0;
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(UPDATE_COMPUTER);) {
			preStatement.setString(1, (String) updatedInfo.get(1));
			preStatement.setDate(2, (Date) updatedInfo.get(2));
			preStatement.setDate(3, (Date) updatedInfo.get(3));
			preStatement.setLong(4, (Long) updatedInfo.get(4));
			preStatement.setString(5, (String) oldName);

			resultat = preStatement.executeUpdate();
			if (resultat == 1) {
				System.out.println("Update réussie! ");
			} else {
				System.out.println("Echec de l'update ");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}

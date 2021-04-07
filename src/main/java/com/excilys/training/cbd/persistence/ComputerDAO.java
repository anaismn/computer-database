package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ComputerDAO {
	private static final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM computer WHERE computer.name LIKE ? ";
	private static final String SELCT_ALL_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id ORDER BY computer.";
	private static final String SELCT_ONE_COMPUTER = "SELECT * FROM computer WHERE name = ? ";
	private static final String FILTER_COMPUTERS = "SELECT * FROM computer LEFT JOIN company ON company.id = company_id WHERE computer.name LIKE ? ORDER BY computer.";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ? ";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE name = ? ;";

	@Autowired
	private DataSource dataSource;

	public int countComputers(String nameSearched) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(COUNT_COMPUTERS);) {

			preStatement.setString(1, "%" + nameSearched + "%");
			ResultSet resultat = preStatement.executeQuery();
			int count = 0;
			while (resultat.next()) {
				count = resultat.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			throw new DAOException(e);
			// logger.error(e.getMessage());
		}
	}

	public ArrayList<Object> getAllComputers(String columnOrdering, int limit, int offset) throws DAOException {
		try (Connection connexion = dataSource.getConnection(); Statement preStatement = connexion.createStatement();) {

			ResultSet resultat = preStatement
					.executeQuery(SELCT_ALL_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset + ";");
			return mapperResult(resultat);
		} catch (SQLException e) {
			e.printStackTrace();
			// logger.error(e.getMessage());
		}
		return null;
	}

	public ArrayList<Object> getOneComputer(Long id) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(SELCT_ONE_COMPUTER);) {

			Long searchID = id;
			preStatement.setLong(1, searchID);

			ResultSet resultat = preStatement.executeQuery();
			return mapperResult(resultat);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public ArrayList<Object> getOneComputer(String nameSearched) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(SELCT_ONE_COMPUTER);) {

			preStatement.setString(1, nameSearched);
			ResultSet resultat = preStatement.executeQuery();
			ArrayList<Object> result = new ArrayList<>();
			while (resultat.next()) {
				result.add(resultat.getLong("id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = null != resultat.getDate("introduced")
						? resultat.getDate("introduced").toLocalDate()
						: null;
				result.add(introduced);
				LocalDate discontinued = null != resultat.getDate("discontinued")
						? resultat.getDate("discontinued").toLocalDate()
						: null;
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public ArrayList<Object> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset)
			throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(
						FILTER_COMPUTERS + columnOrdering + " LIMIT " + limit + " OFFSET " + offset);) {
			preStatement.setString(1, "%" + nameSearched + "%");

			ResultSet resultat = preStatement.executeQuery();
			return mapperResult(resultat);

		} catch (SQLException e) {
			throw new DAOException(e);
		}
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

	public ArrayList<Object> mapperResult(ResultSet resultat) throws DAOException {
		ArrayList<Object> result = new ArrayList<>();
		try {
			while (resultat.next()) {
				result.add(resultat.getLong("computer.id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = null != resultat.getDate("introduced")
						? resultat.getDate("introduced").toLocalDate()
						: null;
				result.add(introduced);
				LocalDate discontinued = null != resultat.getDate("discontinued")
						? resultat.getDate("discontinued").toLocalDate()
						: null;
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
				result.add(resultat.getLong("company.id"));
				result.add(resultat.getString("company.name"));
			}
			return result;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}

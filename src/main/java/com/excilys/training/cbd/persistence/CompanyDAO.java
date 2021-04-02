package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

public class CompanyDAO {
	private static ConnectionManager connectionManager;
	private static final String SELECT_ALL_COMPANIES = "SELECT * FROM company;";
	private static final String SELECT_ONE_COMPANY_BY_NAME = "SELECT * FROM company WHERE name = ? ;";
	private static final String SELECT_ONE_COMPANY_BY_ID = "SELECT * FROM company WHERE id = ? ;";
	private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ? ;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ? ;";

	public CompanyDAO() {
		CompanyDAO.connectionManager = ConnectionManager.getInstance();
	}

	public TreeMap<Long, String> getAllCompanies() throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				Statement statement = connexion.createStatement();) {
			TreeMap<Long, String> companies = new TreeMap<Long, String>();
			ResultSet resultat = statement.executeQuery(SELECT_ALL_COMPANIES);

			while (resultat.next()) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				companies.put(id, name);
			}

			return companies;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public TreeMap<Long, String> getOneCompany(String nameSearched) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(SELECT_ONE_COMPANY_BY_NAME);) {
			preStatement.setString(1, nameSearched);

			return getResult(preStatement);

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public TreeMap<Long, String> getOneCompany(Long idSearched) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(SELECT_ONE_COMPANY_BY_ID);) {
			preStatement.setLong(1, idSearched);

			return getResult(preStatement);

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();) {
			try (PreparedStatement preStatement = connexion.prepareStatement(DELETE_COMPUTERS);
					PreparedStatement preStatement2 = connexion.prepareStatement(DELETE_COMPANY);) {
				connexion.setAutoCommit(false);
				preStatement.setLong(1, idSearched);
				ResultSet resultat = preStatement.executeQuery();
			} catch (SQLException e) {
				connexion.rollback();
				throw new DAOException(e);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private TreeMap<Long, String> getResult(PreparedStatement preStatement) {
		ResultSet resultat;
		TreeMap<Long, String> companies = new TreeMap<Long, String>();
		try {
			resultat = preStatement.executeQuery();

			while (resultat.next()) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				companies.put(id, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;

	}

}

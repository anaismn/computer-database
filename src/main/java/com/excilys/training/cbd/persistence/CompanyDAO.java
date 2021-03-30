package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

public class CompanyDAO {
	private static ConnectionManager connectionManager;

	public CompanyDAO() {
		CompanyDAO.connectionManager = ConnectionManager.getInstance();
	}

	public TreeMap<Long, String> getAllCompanies() throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				Statement statement = connexion.createStatement();) {
			TreeMap<Long, String> companies = new TreeMap<Long, String>();
			ResultSet resultat = statement.executeQuery("SELECT * FROM company;");

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
				PreparedStatement preStatement = connexion
						.prepareStatement("SELECT * FROM company WHERE name = ? ;");) {
			preStatement.setString(1, nameSearched);
			ResultSet resultat = preStatement.executeQuery();
			TreeMap<Long, String> companies = new TreeMap<Long, String>();

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

	public TreeMap<Long, String> getOneCompany(Long idSearched) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement("SELECT * FROM company WHERE id = ? ;");) {
			preStatement.setLong(1, idSearched);
			ResultSet resultat = preStatement.executeQuery();
			TreeMap<Long, String> companies = new TreeMap<Long, String>();

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

	public void deleteCompany(Long idSearched) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();) {
			try (PreparedStatement preStatement = connexion
					.prepareStatement("DELETE FROM computer WHERE company_id = ? ;");
					PreparedStatement preStatement2 = connexion
							.prepareStatement("DELETE FROM company WHERE id = ? ;");) {
				connexion.setAutoCommit(false);
				preStatement.setLong(1, idSearched);
				ResultSet resultat = preStatement.executeQuery();
				TreeMap<Long, String> companies = new TreeMap<Long, String>();

				while (resultat.next()) {
					Long id = resultat.getLong("id");
					String name = resultat.getString("name");
					companies.put(id, name);
				}

			} catch (SQLException e) {
				connexion.rollback();
				throw new DAOException(e);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}

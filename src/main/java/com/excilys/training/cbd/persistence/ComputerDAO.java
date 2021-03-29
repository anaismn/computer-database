package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;

public class ComputerDAO {
	private static ConnectionManager connectionManager;

	public ComputerDAO() {
		ComputerDAO.connectionManager = ConnectionManager.getInstance();
	}

	public ArrayList<Object> getAllComputers() throws DAOException {
		try(Connection connexion = connectionManager.getConnection();
				Statement statement = connexion.createStatement();
				) {
			ArrayList<Object> result = new ArrayList<>();
			ResultSet resultat = statement.executeQuery( "SELECT * FROM computer;" );
			while ( resultat.next() ) {
				result.add(resultat.getLong("id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = null != resultat.getDate("introduced") ? resultat.getDate("introduced").toLocalDate() : null;
				result.add(introduced);
				LocalDate discontinued = null!= resultat.getDate("discontinued")? resultat.getDate("discontinued").toLocalDate() : null;
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
			}
			return result;
		} catch ( SQLException e ) {
			throw new DAOException(e) ;
			//logger.error(e.getMessage());
		}
	}

	public ArrayList<Object> getOneComputer(Long id) throws DAOException {
		try(Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE id = ? ;" );
				) {
			Long searchID = id;
			ArrayList<Object> result = new ArrayList<>();
			preStatement.setLong( 1, searchID);
			ResultSet resultat = preStatement.executeQuery();
			
			while ( resultat.next() ) {
				result.add(resultat.getLong("id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = resultat.getDate("introduced").toLocalDate();
				result.add(introduced);
				LocalDate discontinued = resultat.getDate("discontinued").toLocalDate();
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
			}
			return result;
		} catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}

	public ArrayList<Object> getOneComputer(String nameSearched) throws DAOException {
		try(	Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE name = ? ;" );
				) {
			preStatement.setString( 1, nameSearched);
			ArrayList<Object> result = new ArrayList<>();
			ResultSet resultat = preStatement.executeQuery();
			
			while ( resultat.next() ) {
				result.add(resultat.getLong("id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = null != resultat.getDate("introduced") ? resultat.getDate("introduced").toLocalDate() : null;
				result.add(introduced);
				LocalDate discontinued = null!= resultat.getDate("discontinued")? resultat.getDate("discontinued").toLocalDate() : null;
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
			}
			return result;
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	public ArrayList<Object> getComputersFiltered(String nameSearched) throws DAOException {
		try(	Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE name LIKE  ? ;" );
				) {
			preStatement.setString( 1, "%" + nameSearched + "%");
			ArrayList<Object> result = new ArrayList<>();
			ResultSet resultat = preStatement.executeQuery();
			
			while ( resultat.next() ) {
				result.add(resultat.getLong("id"));
				result.add(resultat.getString("name"));
				LocalDate introduced = null != resultat.getDate("introduced") ? resultat.getDate("introduced").toLocalDate() : null;
				result.add(introduced);
				LocalDate discontinued = null!= resultat.getDate("discontinued")? resultat.getDate("discontinued").toLocalDate() : null;
				result.add(discontinued);
				result.add(resultat.getLong("company_id"));
			}
			return result;
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	public void setNewComputer(ArrayList<Object> informations) throws DAOException {
		int resultat = 0;
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "INSERT INTO computer "
						+ "(name, introduced, discontinued, company_id) "
						+ "VALUES(?, ?, ?, ?);" );
				) {
			preStatement.setString( 1, (String) informations.get(1));
			preStatement.setDate( 2, (Date) informations.get(2));
			preStatement.setDate( 3, (Date) informations.get(3));
			preStatement.setLong( 4, (Long) informations.get(4));
			resultat = preStatement.executeUpdate();
			if(resultat == 1) {
				System.out.println("Ajout réussie! ");
			}else {
				System.out.println("Echec de l'ajout ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	public void deleteComputer(Long id) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "DELETE FROM computer WHERE id = ? ");) {
			
			preStatement.setLong( 1, id);
			int resultat = preStatement.executeUpdate();
			
			if(resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : "+resultat);
			}else {
				System.out.println("Echec de la suppression ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	public void deleteComputer(String name) throws DAOException {
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( 
						"DELETE FROM computer WHERE name = ? ");) {
			preStatement.setString( 1, name);
			int resultat = preStatement.executeUpdate();
			if(resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : "+resultat);
			}else {
				System.out.println("Echec de la suppression ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	public void updateComputer(String oldName, ArrayList<Object> updatedInfo) throws DAOException {
		int resultat = 0;
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "UPDATE computer SET "
						+ "name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE name = ? ;" );
				) {
			preStatement.setString( 1, (String) updatedInfo.get(1));
			preStatement.setDate( 2, (Date) updatedInfo.get(2));
			preStatement.setDate( 3, (Date) updatedInfo.get(3));
			preStatement.setLong( 4, (Long) updatedInfo.get(4));
			preStatement.setString( 5, (String) oldName);
			
			resultat = preStatement.executeUpdate();
			if(resultat == 1) {
				System.out.println("Update réussie! ");
			}else {
				System.out.println("Echec de l'update ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
	
	
}

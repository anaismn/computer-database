package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.excilys.training.cbd.mapper.ComputerMapper;
import com.excilys.training.cbd.model.Computer;

public class ComputerDAO {
	private static ConnectionManager connectionManager;

	public ComputerDAO() {
		ComputerDAO.connectionManager = ConnectionManager.getInstance();
	}

	public void getAllComputers() throws DAOException{
		try(Connection connexion = connectionManager.getConnection();
				Statement statement = connexion.createStatement();
				) {
			ResultSet resultat = statement.executeQuery( "SELECT * FROM computer;" );
			System.out.println( "id  - name" );
			while ( resultat.next() ) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				System.out.println( id+" - "+name);
			}
		} catch ( SQLException e ) {
			System.out.println("Error while connecting! "+ e);
		}
	}

	public Optional<ResultSet> getOneComputer(Long id) {
		try(Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE id = ? ;" );
				) {
			Long searchID = id;
			preStatement.setLong( 1, searchID);
			ResultSet resultat = preStatement.executeQuery();
			return Optional.of(resultat);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}

	public Optional<ResultSet> getOneComputer(String nameSearched)  throws SQLException {
		try(	Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE name = ? ;" );
				) {
			preStatement.setString( 1, nameSearched);
			ResultSet resultat = preStatement.executeQuery();
			return Optional.of(resultat);
		}catch ( SQLException e ) {
			throw  e ;
		}
	}
	
	public void setNewComputer(Computer newComputer) {
		int resultat = 0;
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "INSERT INTO computer "
						+ "(name, introduced, discontinued, company_id) "
						+ "VALUES(?, ?, ?, ?);" );
				) {
			preStatement.setString( 1, newComputer.getName());
			preStatement.setString( 2, newComputer.getIntroduced());
			preStatement.setString( 3, newComputer.getDiscontinued());
			preStatement.setLong( 4, newComputer.getCompanyID());
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
	
	public void deleteComputer(Long id) {
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
	
	public void deleteComputer(String name) {
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
	
	public void updateComputer(String oldName, Computer oldComputer, String newName, String introduced, String discontinued, Long companyID) {
		int resultat = 0;
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "UPDATE computer SET "
						+ "name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE name = ? ;" );
				) {
			String updatedName = newName==null ? oldComputer.getName() : newName;
			String updatedIntroduced = introduced==null ? oldComputer.getIntroduced() : introduced;
			String updatedDiscontinued = discontinued==null ? oldComputer.getDiscontinued() : discontinued;
			Long updatedCompany = companyID==null ? oldComputer.getCompanyID() : companyID;
		
			preStatement.setString( 1, updatedName);
			preStatement.setString( 2, updatedIntroduced);
			preStatement.setString( 3, updatedDiscontinued);
			preStatement.setLong( 4, updatedCompany);
			preStatement.setString( 5, oldName);

			resultat = preStatement.executeUpdate();
			if(resultat == 1) {
				System.out.println("Update réussie! ");
			}else {
				System.out.println("Echec de l'update ");
			}
		}catch ( SQLException e ) {
			System.out.println("Error while connecting! "+ e);
		}
	}
	
	
}

package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.excilys.training.cbd.model.Computer;

public class ComputerDAO {
	private static DAOFactory daoFactory;
	private Connection connexion = null;

	public ComputerDAO() {
		ComputerDAO.daoFactory = DAOFactory.getInstance();
	}

	public void getAllComputers() throws DAOException{
		//Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		try {
			connexion = daoFactory.getConnection();

			/* Ici, nous placerons nos requêtes vers la BDD */
			System.out.println("Connection successful!");

			/* Création de l'objet gérant les requêtes */
			statement = connexion.createStatement();

			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery( "SELECT * FROM computer;" );

			/* Récupération des données du résultat de la requête de lecture */
			System.out.println( "id  - name" );
			while ( resultat.next() ) {
				int id = resultat.getInt("id");
				String name = resultat.getString("name");
				System.out.println( id+" - "+name);
			}

		} catch ( SQLException e ) {

			System.out.println("Error while connecting! "+ e);
		} finally {
			Close.fermeturesSilencieuses( resultat, statement, connexion );
		}	
	}

	public void getOneComputer(Long id) {
		PreparedStatement preStatement = null;
		ResultSet resultat = null;
		//Connection connexion = null;
		try {
			connexion = daoFactory.getConnection();

			//* Création de l'objet gérant les requêtes préparées */
			preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE id = ? ;" );

			Long searchID = id;
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setXXX() mises à disposition par l'objet PreparedStatement.
			 */
			preStatement.setLong( 1, searchID);

			/* Exécution de la requête */
			resultat = preStatement.executeQuery();
			while ( resultat.next() ) {
				Long foundID = resultat.getLong("id");
				String name = resultat.getString("name");
				
				java.sql.Date introduced = null;
				java.sql.Date discontinued = null;
				if(resultat.getString("introduced") != null) {
					 introduced = resultat.getDate("introduced");
				}
				if(resultat.getString("discontinued") != null) {
					discontinued = resultat.getDate("discontinued");
				}
				
				Long company_id = resultat.getLong("company_id");
				
				Computer computer = new Computer( name, introduced, discontinued, company_id);
				System.out.println( " id : "+foundID);
				System.out.println(computer.toString());
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		}finally {
			Close.fermeturesSilencieuses( resultat, preStatement, connexion );
		}
	}

	public Computer getOneComputer(String nameSearched) {
		PreparedStatement preStatement = null;
		ResultSet resultat = null;
		Connection connexion = null;
		Computer computer = null ;
		try {
			connexion = daoFactory.getConnection();
			preStatement = connexion.prepareStatement( "SELECT * FROM computer WHERE name = ? ;" );
			preStatement.setString( 1, nameSearched);

			resultat = preStatement.executeQuery();
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				System.out.println("name : "+name);
				java.sql.Date introduced = null;
				java.sql.Date discontinued = null;
				if(resultat.getString("introduced") != null) {
					 introduced = resultat.getDate("introduced");
				}
				
				if(resultat.getString("discontinued") != null) {
					discontinued = resultat.getDate("discontinued");
				}
				Long company_id = resultat.getLong("company_id");
				
				computer = new Computer( name, introduced, discontinued, company_id);
				System.out.println( " id : "+id);
				System.out.println(computer.toString());
			}
			
		}catch ( SQLException e ) {
			System.out.println("Erreur " + e.getMessage());
			//throw new DAOException( e );
		}finally {
			Close.fermeturesSilencieuses( resultat, preStatement, connexion );
		}
		return computer;
	}
	
	public void setNewComputer(Computer newComputer) {
		PreparedStatement preStatement = null;
		int resultat = 0;
		//Connection connexion = null;
		try {
			connexion = daoFactory.getConnection();

			//* Création de l'objet gérant les requêtes préparées */
			preStatement = connexion.prepareStatement( "INSERT INTO computer "
					+ "(name, introduced, discontinued, company_id) "
					+ "VALUES(?, ?, ?, ?);" );
			preStatement.setString( 1, newComputer.getName());
			
			preStatement.setString( 2, newComputer.getIntroduced());
			preStatement.setString( 3, newComputer.getDiscontinued());
			preStatement.setLong( 4, newComputer.getCompanyID());

			/* Exécution de la requête */
			resultat = preStatement.executeUpdate();
			if(resultat == 1) {
				System.out.println("Ajout réussie! ");
			}else {
				System.out.println("Echec de l'ajout ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}finally {
			Close.fermeturesSilencieuses( preStatement, connexion );
		}
	}
	
	public void deleteComputer(Long id) {
		PreparedStatement preStatement = null;
		int resultat = 0;
		//Connection connexion = null;
		try {
			connexion = daoFactory.getConnection();
			
			preStatement = connexion.prepareStatement( "DELETE FROM computer WHERE id = ? ");
			preStatement.setLong( 1, id);
			resultat = preStatement.executeUpdate();
			
			if(resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : "+resultat);
			}else {
				System.out.println("Echec de la suppression ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}finally {
			Close.fermeturesSilencieuses( preStatement, connexion );
		}
	}
	
	public void deleteComputer(String name) {
		PreparedStatement preStatement = null;
		int resultat = 0;
		//Connection connexion = null;
		try {
			connexion = daoFactory.getConnection();
			
			preStatement = connexion.prepareStatement( "DELETE FROM computer WHERE name = ? ");
			preStatement.setString( 1, name);
			resultat = preStatement.executeUpdate();
			
			if(resultat > 0) {
				System.out.println("Suppression réussie! - nombre de supression : "+resultat);
			}else {
				System.out.println("Echec de la suppression ");
			}
		}catch ( SQLException e ) {
			throw new DAOException( e );
		}finally {
			Close.fermeturesSilencieuses( preStatement, connexion );
		}
	}
	
	public void updateComputer(String oldName, Computer oldComputer, String newName, String introduced, String discontinued, Long companyID) {
		PreparedStatement preStatement = null;
		int resultat = 0;

		try {
			Connection connexion = daoFactory.getConnection();
			
			String updatedName = newName==null ? oldComputer.getName() : newName;
			String updatedIntroduced = introduced==null ? oldComputer.getIntroduced() : introduced;
			String updatedDiscontinued = discontinued==null ? oldComputer.getDiscontinued() : discontinued;
			Long updatedCompany = companyID==null ? oldComputer.getCompanyID() : companyID;
			
			preStatement = connexion.prepareStatement( "UPDATE computer SET "
					+ "name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE name = ? ;" );

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

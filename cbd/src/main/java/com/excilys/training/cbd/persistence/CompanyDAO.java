package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.training.cbd.model.Company;

public class CompanyDAO {
	private static DAOFactory daoFactory;
	private static Connection connexion = null;
	
	public CompanyDAO() {
		CompanyDAO.daoFactory = DAOFactory.getInstance();
	}

	public void getAllCompanies() throws DAOException{
		Statement statement = null;
		ResultSet resultat = null;
		try {
			connexion = daoFactory.getConnection();
			System.out.println("Connection successful!");
			statement = connexion.createStatement();

			resultat = statement.executeQuery( "SELECT * FROM company;" );

			/* Récupération des données du résultat de la requête de lecture */
			System.out.println( "id  - name" );
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				System.out.println( id+" - "+name);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			Close.fermeturesSilencieuses( resultat, statement, connexion );
		}	
	}
	
	public Company getOneCompany(String nameSearched) throws DAOException{
		PreparedStatement preStatement = null;
		ResultSet resultat = null;
		Company company = null;
		try {
			connexion = daoFactory.getConnection();
			preStatement = connexion.prepareStatement( "SELECT * FROM company WHERE name = ? ;" );
			preStatement.setString( 1, nameSearched);

			resultat = preStatement.executeQuery();

			/* Récupération des données du résultat de la requête de lecture */
			System.out.println( "id  - name" );
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				company = new Company(id, name);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			Close.fermeturesSilencieuses( resultat, preStatement, connexion );
		}	
		return company;
	}
}

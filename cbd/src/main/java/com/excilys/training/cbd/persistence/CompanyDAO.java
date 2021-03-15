package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Optional;

import com.excilys.training.cbd.mapper.CompanyMapper;

public class CompanyDAO {
	private static ConnectionManager connectionManager;

	public CompanyDAO() {
		CompanyDAO.connectionManager = ConnectionManager.getInstance();
	}

	public HashMap<String, Long> getAllCompanies() throws SQLException{
		try (Connection connexion = connectionManager.getConnection();
				Statement statement = connexion.createStatement();)
		{
			HashMap<String, Long> companies = new HashMap<String, Long>() ;
			ResultSet resultat = statement.executeQuery( "SELECT * FROM company;" );
			
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				companies.put(name, id);
			}
			
			return companies;
		} catch ( SQLException e ) {
			throw e;
		}	
	}

	public HashMap<String, Long> getOneCompany(String nameSearched) throws SQLException{
		try (Connection connexion = connectionManager.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM company WHERE name = ? ;" );
				){
			preStatement.setString( 1, nameSearched);
			ResultSet resultat = preStatement.executeQuery();
			HashMap<String, Long> companies = new HashMap<String, Long>() ;;
			
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				companies.put(name, id);
			}
			
			return companies;
		} catch ( SQLException e ) {
			throw e;
		}
	}
}

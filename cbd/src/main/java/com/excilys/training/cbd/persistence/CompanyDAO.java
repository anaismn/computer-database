package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.Computer;

public class CompanyDAO {
	private static DAOFactory daoFactory;

	public CompanyDAO() {
		CompanyDAO.daoFactory = DAOFactory.getInstance();
	}

	public void getAllCompanies() throws DAOException{
		try (Connection connexion = daoFactory.getConnection();
				Statement statement = connexion.createStatement();)
		{
			ResultSet resultat = statement.executeQuery( "SELECT * FROM company;" );
			while ( resultat.next() ) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				System.out.println( id+" - "+name);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		}	
	}

	public Optional<ResultSet> getOneCompany(String nameSearched) throws DAOException{
		try (Connection connexion = daoFactory.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement( "SELECT * FROM company WHERE name = ? ;" );
				){
			preStatement.setString( 1, nameSearched);
			ResultSet resultat = preStatement.executeQuery();
			return Optional.of(resultat);
		} catch ( SQLException e ) {
			throw new DAOException( e );
		}
	}
}

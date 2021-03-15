package com.excilys.training.cbd.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

	private static final String FICHIER_PROPERTIES       = "dao.properties";
	private static final String PROPERTY_URL             = "url";
	private static final String PROPERTY_DRIVER          = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "utilisateur";
	private static final String PROPERTY_MOT_DE_PASSE    = "motDePasse";

	private String              url;
	private String              username;
	private String              password;
	
	private static ConnectionManager instance;

	private ConnectionManager( String url, String username, String password ) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static ConnectionManager getInstance() throws DAOConfigurationException {
		if(instance == null) {
			Properties properties = new Properties();
			String url;
			String driver;
			String nomUtilisateur;
			String motDePasse;
	
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
	
			if ( fichierProperties == null ) {
				throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
			}
	
			try {
				properties.load( fichierProperties );
				url = properties.getProperty( PROPERTY_URL );
				driver = properties.getProperty( PROPERTY_DRIVER );
				nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
				motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
			} catch ( IOException e ) {
				throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
			}
	
			try {
				Class.forName( driver );
			} catch ( ClassNotFoundException e ) {
				throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
			}
	
			instance = new ConnectionManager( url, nomUtilisateur, motDePasse );
		}
		return instance;
	}

	 	Connection getConnection() throws SQLException {
		return DriverManager.getConnection( url, username, password );
	}

}

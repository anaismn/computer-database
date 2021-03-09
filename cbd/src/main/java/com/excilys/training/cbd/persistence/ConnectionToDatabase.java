package com.excilys.training.cbd.persistence;

import java.sql.*;

public class ConnectionToDatabase {

	private ConnectionToDatabase() {
		/* Chargement du driver JDBC pour MySQL */
		System.out.println("Loading of driver successful!");
		try {
		    Class.forName( "com.mysql.cj.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    /* Gérer les éventuelles erreurs ici. */
		}
		
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/computer-database-db";
		String utilisateur = "admincdb";
		String motDePasse = "qwerty1234";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		    
		    /* Ici, nous placerons nos requêtes vers la BDD */
		    System.out.println("Connection successful!");
		    
		    /* Création de l'objet gérant les requêtes */
		    Statement statement = connexion.createStatement();
		    
		    /* Exécution d'une requête de lecture */
		    ResultSet resultat = statement.executeQuery( "SELECT * FROM company;" );
		    
		    /* Récupération des données du résultat de la requête de lecture */
		    System.out.println( "id  - name" );
	        while ( resultat.next() ) {
	        	int id = resultat.getInt("id");
	        	String name = resultat.getString("name");
	        	
	        	System.out.println( id+" - "+name);
	        }

		} catch ( SQLException e ) {
		    /* Gérer les éventuelles erreurs ici */
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		        	System.out.println("...Disconnected");
		            connexion.close();
		        } catch ( SQLException ignore ) {
		            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		        }
		}
	}
	
	
	public static void main(String[] args) {
		ConnectionToDatabase co = new ConnectionToDatabase();

	}

}

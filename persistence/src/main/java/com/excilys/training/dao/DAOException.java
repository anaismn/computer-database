package com.excilys.training.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends SQLException {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOException.class);
	
	public DAOException( String message ) {
        logger.info(message);
    }

    public DAOException( String message, Throwable cause ) {
    	logger.info(message);
    	logger.error( cause.toString() );
    }

    public DAOException( Throwable cause ) {
    	logger.error( cause.toString() );
    } 
}
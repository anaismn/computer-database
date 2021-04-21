package com.excilys.training.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOConfigurationException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(DAOConfigurationException.class);
	
	public DAOConfigurationException( String message ) {
        logger.info(message);
    }

    public DAOConfigurationException( String message, Throwable cause ) {
    	logger.info(message);
    	logger.error( cause.toString() );
    }

    public DAOConfigurationException( Throwable cause ) {
    	logger.error( cause.toString() );
    }
}
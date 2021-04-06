package com.excilys.training.cbd.persistence;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({ "com.excilys.training.cbd.service", 
	"com.excilys.training.cbd.persistence", 
	"com.excilys.training.cbd.servlet" })
public class SpringWebConfig  implements WebApplicationInitializer {

	private static final String FICHIER_PROPERTIES = "/dao.properties";
	
	@Bean
	public DataSource getDataSource() throws SQLException {
		System.out.println(FICHIER_PROPERTIES);
		HikariConfig hikariConf = new HikariConfig(FICHIER_PROPERTIES);
		return new HikariDataSource(hikariConf);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	    context.register(SpringWebConfig.class);
	    context.setServletContext(servletContext);
		
	}
}
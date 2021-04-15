package com.excilys.training.cbd.persistence;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.training.cbd.service", "com.excilys.training.cbd.mapper",
	"com.excilys.training.cbd.persistence", "com.excilys.training.cbd.controller" })
public class HibernateConfig {

	private static final String FICHIER_PROPERTIES = "/dao.properties";
	
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        try {
			sessionFactory.setDataSource(getDataSource());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sessionFactory.setPackagesToScan(new String[]{"com.developerstack.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

	

	@Bean
	public DataSource getDataSource() throws SQLException {
		System.out.println(FICHIER_PROPERTIES);
		HikariConfig hikariConf = new HikariConfig(FICHIER_PROPERTIES);
		return new HikariDataSource(hikariConf);
	}

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }


}
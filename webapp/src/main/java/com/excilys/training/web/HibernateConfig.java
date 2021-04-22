package com.excilys.training.web;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({ "com.excilys.training.service", "com.excilys.training.mapper",
	"com.excilys.training.dao", "com.excilys.training.web",  "com.excilys.training.jackson",
	"com.excilys.training.model" })
public class HibernateConfig implements WebMvcConfigurer {

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
        sessionFactory.setPackagesToScan(new String[]{"com.excilys.training.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
	
	
	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
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
                //setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//                setProperty("hibernate.show_sql", "true");
            }
        };
    }

    
    
 // views 
 	@Bean
 	public ViewResolver viewResolver() {
 		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
 		resolver.setPrefix("/WEB-INF/view/");
 		resolver.setSuffix(".jsp");
 		return resolver;
 	}

 	@Override
 	public void addResourceHandlers(ResourceHandlerRegistry registry) {
 		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
 	}

}
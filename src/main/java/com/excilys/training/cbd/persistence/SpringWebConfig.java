package com.excilys.training.cbd.persistence;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.training.cbd.service", "com.excilys.training.cbd.mapper",
		"com.excilys.training.cbd.persistence", "com.excilys.training.cbd.servlet", 
		"com.excilys.training.cbd.controller"  })
public class SpringWebConfig implements WebMvcConfigurer {

	private static final String FICHIER_PROPERTIES = "/dao.properties";

	@Bean
	public DataSource getDataSource() throws SQLException {
		System.out.println(FICHIER_PROPERTIES);
		HikariConfig hikariConf = new HikariConfig(FICHIER_PROPERTIES);
		return new HikariDataSource(hikariConf);
	}

	public JdbcTemplate getJdbcTemplate(HikariDataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	 @Bean
	 public ViewResolver viewResolver() {
	  InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	  resolver.setPrefix("/WEB-INF/jsp/");
	  resolver.setSuffix(".jsp");
	  return resolver;
	 }
	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	     registry
	       .addResourceHandler("/resources/**")
	       .addResourceLocations("/resources/");	
	 }
	 
	 
//   @Override
//   public void onStartup(final ServletContext sc) throws ServletException {
//
//   		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//   		context.register(SpringWebConfig.class);
//   		context.setServletContext(sc);
//
//   		sc.addListener(new ContextLoaderListener(context));
//
//   		ServletRegistration.Dynamic appServlet = sc.addServlet("listComputers",
//   				new DispatcherServlet(new GenericWebApplicationContext()));
//   		appServlet.setLoadOnStartup(1);
//   		appServlet.addMapping("/");
//   }
}
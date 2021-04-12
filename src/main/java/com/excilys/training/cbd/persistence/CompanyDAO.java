package com.excilys.training.cbd.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.model.Company;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyDAO {
	private static final String SELECT_ALL_COMPANIES = "SELECT * FROM company;";
	private static final String SELECT_ONE_COMPANY_BY_NAME = "SELECT * FROM company WHERE name = ? ;";
	private static final String SELECT_ONE_COMPANY_BY_ID = "SELECT * FROM company WHERE id = ? ";
	private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ? ;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ? ;";

//	@Autowired
//	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	CompanyDAO(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	public List<Company> getAllCompanies() {
		return jdbcTemplate.query(SELECT_ALL_COMPANIES, new CompanyMapper());
	}

	public Company getOneCompany(Long idSearched) {
		return jdbcTemplate.queryForObject(
				SELECT_ONE_COMPANY_BY_ID, new CompanyMapper(), idSearched);
	}

	@Transactional
	public void deleteCompany(Long idSearched) {
		jdbcTemplate.update(DELETE_COMPUTERS, idSearched);
		jdbcTemplate.update(DELETE_COMPANY, idSearched);
	}

}

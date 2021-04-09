package com.excilys.training.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	CompanyDAO(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	public List<Company> getAllCompanies() {
		return jdbcTemplate.query(SELECT_ALL_COMPANIES, new CompanyMapper());
	}

	public TreeMap<Long, String> getOneCompany(String nameSearched) throws DAOException {
		try (Connection connexion = dataSource.getConnection();
				PreparedStatement preStatement = connexion.prepareStatement(SELECT_ONE_COMPANY_BY_NAME);) {
			preStatement.setString(1, nameSearched);

			return getResult(preStatement);

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Company getOneCompany(Long idSearched) {
		return jdbcTemplate.queryForObject(
				SELECT_ONE_COMPANY_BY_ID, new CompanyMapper(), idSearched);
	}

	public void deleteCompany(Long idSearched) throws DAOException {
		try (Connection connexion = dataSource.getConnection();) {
			try (PreparedStatement preStatement = connexion.prepareStatement(DELETE_COMPUTERS);
					PreparedStatement preStatement2 = connexion.prepareStatement(DELETE_COMPANY);) {
				connexion.setAutoCommit(false);
				preStatement.setLong(1, idSearched);
				ResultSet resultat = preStatement.executeQuery();
			} catch (SQLException e) {
				connexion.rollback();
				throw new DAOException(e);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	private TreeMap<Long, String> getResult(PreparedStatement preStatement) {
		ResultSet resultat;
		TreeMap<Long, String> companies = new TreeMap<Long, String>();
		try {
			resultat = preStatement.executeQuery();

			while (resultat.next()) {
				Long id = resultat.getLong("id");
				String name = resultat.getString("name");
				companies.put(id, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return companies;

	}

}

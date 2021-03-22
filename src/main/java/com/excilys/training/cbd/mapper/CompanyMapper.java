package com.excilys.training.cbd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.DAOException;

	public class CompanyMapper {

		public static Company resultToCompany(String name, Long id) {
			return new Company.Builder(name)
							.setId(id)
							.build();
		}
}

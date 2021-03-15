package com.excilys.training.cbd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.persistence.DAOException;

	public class CompanyMapper {
		
		public static Optional<Company> resultToCompany(ResultSet result) {
			try {
				while ( result.next() ) {
					Long id = result.getLong("id");
					String name = result.getString("name");
					 Company company = new Company.Builder(name)
							.setID(id)
							.build();
					 return Optional.of(company);
				}
			}catch ( SQLException e ) {
				throw new DAOException( e );
			}
			return Optional.empty();
		}
}

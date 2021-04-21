package com.excilys.training.cbd.persistence;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.training.cbd.mapper.CompanyMapper;
import com.excilys.training.cbd.model.Company;
import com.excilys.training.cbd.model.CompanyTable;
import com.excilys.training.cbd.model.QCompanyTable;
import com.excilys.training.cbd.model.QComputerTable;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyDAO {

	private EntityManager entityManager;

	CompanyDAO(DataSource dataSource, SessionFactory sessionFactory) {
		this.entityManager = sessionFactory.createEntityManager();
	}

	public List<CompanyTable> getAllCompanies() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyTable companyTable = QCompanyTable.companyTable;

		List<CompanyTable> companysTable = queryFactory.selectFrom(companyTable)
				.orderBy(companyTable.id.asc())
				.fetch();
		return companysTable;
	}

	public CompanyTable getOneCompany(Long idSearched) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyTable companyTable = QCompanyTable.companyTable;

		CompanyTable result = queryFactory.selectFrom(companyTable)
				.where(companyTable.id.eq(idSearched))
				.fetchOne();
		return result;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public void deleteCompany(Long id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		
		QComputerTable computerTable = QComputerTable.computerTable;
		queryFactory.delete(computerTable)
		.where(computerTable.companyId.eq(id))
		.execute();
		
		QCompanyTable companyTable = QCompanyTable.companyTable;
		queryFactory.delete(companyTable)
		.where(companyTable.id.eq(id))
		.execute();
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

}

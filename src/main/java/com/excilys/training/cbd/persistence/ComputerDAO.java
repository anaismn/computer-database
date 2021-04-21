package com.excilys.training.cbd.persistence;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.training.cbd.model.ComputerTable;
import com.excilys.training.cbd.model.QComputerTable;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ComputerDAO {

	private EntityManager entityManager;

	ComputerDAO(DataSource dataSource, SessionFactory sessionFactory) {
		this.entityManager = sessionFactory.createEntityManager();
	}

	public List<ComputerTable> getAllComputers(String columnOrdering, int limit, int offset) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerTable computerTable = QComputerTable.computerTable;

		List<ComputerTable> computersTable = queryFactory.selectFrom(computerTable).orderBy(computerTable.id.asc())
				.fetch();

		return computersTable;
	}

	public long countComputers(String nameSearched) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerTable computerTable = QComputerTable.computerTable;

		long numberOfComputers = queryFactory.selectFrom(computerTable).where(computerTable.name.contains(nameSearched))
				.fetchCount();

		return numberOfComputers;
	}

	public ComputerTable getOneComputer(Long id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerTable computerTable = QComputerTable.computerTable;

		ComputerTable computer = queryFactory.selectFrom(computerTable).where(computerTable.id.eq(id)).fetchOne();

		return computer;
	}

	public List<ComputerTable> getComputersFiltered(String nameSearched, String columnOrdering, int limit, int offset) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerTable computerTable = QComputerTable.computerTable;

		List<ComputerTable> computersTable = queryFactory.selectFrom(computerTable)
				.where(computerTable.name.contains(nameSearched)).limit(limit).offset(offset).fetch();

		return computersTable;
	}

	public void setNewComputer(ComputerTable computerTable) {
		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		entityManager.persist(computerTable);
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

	public void deleteComputer(Long id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		QComputerTable computerTable = QComputerTable.computerTable;

		queryFactory.delete(computerTable).where(computerTable.id.eq(id)).execute();
		entityManager.getTransaction().commit();
		entityManager.clear();
	}

	@Transactional(rollbackFor = { SQLException.class })
	public void updateComputer(ComputerTable updatedComputer, Long id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		QComputerTable computerTable = QComputerTable.computerTable;

		queryFactory.update(computerTable).where(computerTable.id.eq(id))
				.set(computerTable.name, updatedComputer.getName())
				.set(computerTable.introduced, updatedComputer.getIntroduced())
				.set(computerTable.discontinued, updatedComputer.getDiscontinued())
				.set(computerTable.companyId, updatedComputer.getCompanyId()).execute();
		entityManager.getTransaction().commit();
		entityManager.clear();
	}
}

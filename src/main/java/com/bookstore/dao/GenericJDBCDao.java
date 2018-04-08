package com.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.bookstore.exception.DataAccessException;
import com.bookstore.model.AbstractEntity;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * Abstract base class for generic CRUD operations on a entity for a specific
 * type.
 *
 * @author Shaheer
 */
public abstract class GenericJDBCDao<T extends AbstractEntity> implements GenericDao<T, Long> {

	private static final Logger logger = Logger.getLogger(GenericJDBCDao.class);

	private static final String PRIMARY_KEY_COLUMN = "id";

	protected abstract String getTableName();

	protected abstract String getTableColumns();

	protected abstract Object[] getEntityParameterValues(T entity);

	/**
	 * Map the current row of the given ResultSet to an entity.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped to an
	 *            entity.
	 * @return The mapped entity from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	protected abstract T getEntityFromResultSet(ResultSet resultSet) throws SQLException;

	@Override
	public final Optional<T> findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("The given id must not be null!.");
		}
		String query = this.getSelectQuery() + " WHERE id = ?";
		logger.debug(query);
		return find(query, id);
	}

	@Override
	public final List<T> findAll() {
		return this.findByCondition();
	}

	@Override
	public final T save(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity must not be null.");
		}
		if (entity.getId() == null) {
			return createEntity(entity);
		} else {
			return updateEntity(entity);
		}
	}

	@Override
	public final void delete(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity must not be null.");
		}
		this.deleteById(entity.getId());
	}

	@Override
	public final void deleteById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("The given id must not be null!.");
		}
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, this.getDeleteQuery(), false, id);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DataAccessException("Deleting entity failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public final long count() {
		String query = "SELECT COUNT(*) AS count FROM " + this.getTableName();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				return resultSet.getLong("count");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return 0;
	}

	protected final List<T> findByCondition(String... condition) {
		List<T> list = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(this.getSelectQuery(condition));
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				list.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return list;
	}

	protected final T findByConditionUnique(String... condition) {
		T entity = null;
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(this.getSelectQuery(condition));
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				entity = this.getEntityFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return entity;
	}

	/**
	 * Returns the entity from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * 
	 * @return The entity from the database matching the given SQL query with the
	 *         given values.
	 * @throws DataAccessException
	 *             If something fails at database level.
	 */
	private Optional<T> find(String sql, Object... values) {
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				return Optional.of(getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return Optional.empty();
	}

	private T createEntity(T entity) {
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, this.getInsertQuery(), true,
						this.getEntityParameterValues(entity));) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DataAccessException("Creating entity failed, no rows affected.");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entity.setId(generatedKeys.getLong(1));
				} else {
					throw new DataAccessException("Creating entity failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return entity;
	}

	private T updateEntity(T entity) {
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, this.getUpdateQuery(), false,
						this.getEntityParameterValues(entity));) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DataAccessException("Updating entity failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return entity;
	}

	protected final String getSelectQuery(String... condition) {
		String query = "SELECT " + PRIMARY_KEY_COLUMN + "," + this.getTableColumns() + " FROM " + this.getTableName();
		String whereClause = buildWhereClause(Arrays.asList(condition));
		return query + whereClause;
	}

	protected final String getInsertQuery() {
		String query = "INSERT INTO " + this.getTableName() + "(" + PRIMARY_KEY_COLUMN + "," + this.getTableColumns()
				+ ") values (null,";
		StringBuilder colSb = new StringBuilder();
		Arrays.asList(this.getTableColumns().split(",")).forEach(col -> colSb.append(" ?,"));
		colSb.setLength(colSb.length() - 1);
		colSb.append(")");
		return query + colSb.toString();
	}

	protected final String getUpdateQuery() {
		String query = "UPDATE " + this.getTableName() + " SET ";
		StringBuilder colSb = new StringBuilder();
		Arrays.asList(this.getTableColumns().split(",")).forEach(col -> colSb.append(col + "=? ,"));
		colSb.setLength(colSb.length() - 1);
		colSb.append(" WHERE " + PRIMARY_KEY_COLUMN + " = ?");
		return query + colSb.toString();
	}

	protected final String getDeleteQuery() {
		return "DELETE FROM " + this.getTableName() + " WHERE " + PRIMARY_KEY_COLUMN + "= ?";
	}

	private String buildWhereClause(Collection<String> conditions) {
		if (conditions == null || conditions.isEmpty()) {
			return "";
		}
		StringBuilder clauseSb = new StringBuilder();
		conditions.forEach(c -> clauseSb.append(c).append(" AND "));
		if (clauseSb.length() > 0) {
			clauseSb.insert(0, " WHERE ");
			clauseSb.setLength(clauseSb.length() - 4);
		}
		return clauseSb.toString();
	}
}
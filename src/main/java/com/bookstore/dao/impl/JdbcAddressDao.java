package com.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.exception.DataAccessException;
import com.bookstore.model.Address;
import com.bookstore.util.DAOUtil;
import com.bookstore.util.DBManager;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link AddressDao} interface.
 *
 * @author Shaheer
 */
public class JdbcAddressDao extends GenericJDBCDao<Address> implements AddressDao {

	private static final Logger logger = Logger.getLogger(JdbcAddressDao.class);

	private static final String SQL_ADDRESS_BOOK_INSERT = "INSERT INTO address_book (user_key, address_key) VALUES (?, ?)";

	@Override
	public void saveAddressBook(Long userId, Long addressId) {
		Object[] values = { userId, addressId };
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_ADDRESS_BOOK_INSERT, true,
						values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DataAccessException("Creating address book failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public List<Address> findAddressByUserId(Long userId) {
		String sql = "SELECT id, " + getTableColumns() + " FROM " + getTableName()
				+ " INNER JOIN address_book on address.id = address_book.address_key AND address_book.user_key = ?";
		logger.debug(sql);
		List<Address> addresses = new ArrayList<>();
		try (Connection connection = DBManager.getDBConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, userId);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				addresses.add(this.getEntityFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return addresses;
	}

	@Override
	protected String getTableName() {
		return "address";
	}

	@Override
	protected String getTableColumns() {
		return "first_name, last_name, street_1, street_2, city, state, postal_code";
	}

	@Override
	protected Object[] getEntityParameterValues(Address entity) {
		if (entity.getId() == null) {
			// save
			return new Object[] { entity.getFirstName(), entity.getLastName(), entity.getStreet1(), entity.getStreet2(),
					entity.getCity(), entity.getState(), entity.getPostalCode() };
		} else {
			// update
			return new Object[] { entity.getFirstName(), entity.getLastName(), entity.getStreet1(), entity.getStreet2(),
					entity.getCity(), entity.getState(), entity.getPostalCode(), entity.getId() };
		}
	}

	@Override
	protected Address getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		Address address = new Address();
		address.setId(resultSet.getLong("id"));
		address.setFirstName(resultSet.getString("first_name"));
		address.setLastName(resultSet.getString("last_name"));
		address.setStreet1(resultSet.getString("street_1"));
		address.setStreet2(resultSet.getString("street_2"));
		address.setCity(resultSet.getString("city"));
		address.setState(resultSet.getString("state"));
		address.setPostalCode(resultSet.getString("postal_code"));
		return address;
	}

}

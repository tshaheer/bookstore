package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.dao.UserDao;
import com.bookstore.model.User;

/**
 * This class represents a concrete JDBC implementation of the {@link UserDao} interface.
 *
 * @author Shaheer
 */
public class JdbcUserDao extends GenericJDBCDao<User> implements UserDao {

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return findByConditionUnique("email_address = '"+ email +"' and password = '"+ password +"'");
	}
	
	@Override
	public boolean existEmail(String email) {
		boolean exist = false;
		User user = findByConditionUnique("email_address = '"+ email +"'");
		if(user != null) {
			exist = true;
		}
		return exist;
	}

	@Override
	protected String getTableName() {
		return "user";
	}

	@Override
	protected String getTableColumns() {
		return "email_address, password";
	}

	@Override
	protected Object[] getEntityParameterValues(User entity) {
		return new Object[] { entity.getEmail(), entity.getPassword()};
	}

	@Override
	protected User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setEmail(resultSet.getString("email_address"));
		user.setPassword(resultSet.getString("password"));
		return user;
	}

}

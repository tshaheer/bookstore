package com.bookstore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.GenericJDBCDao;
import com.bookstore.model.CreditCard;

/**
 * This class represents a concrete JDBC implementation of the
 * {@link CreditCardDao} interface.
 *
 * @author Shaheer
 */
public class JdbcCreditCardDao extends GenericJDBCDao<CreditCard> implements CreditCardDao {

	@Override
	public CreditCard findOneByUserId(Long userId) {
		return findByConditionUnique("user_key = '"+ userId +"'");
	}

	@Override
	protected String getTableName() {
		return "credit_card";
	}

	@Override
	protected String getTableColumns() {
		return "card_type, card_number, card_ownername, card_expmonth, card_expyear, user_key";
	}

	@Override
	protected Object[] getEntityParameterValues(CreditCard entity) {
		if (entity.getId() == null) {
			// save
			return new Object[] { entity.getCardType(), entity.getCardNumber(),
					entity.getCardOwner(), entity.getExpMonth(), entity.getExpYear(), entity.getUser().getId() };
		} else {
			// update
			return new Object[] { entity.getCardType(), entity.getCardNumber(),
					entity.getCardOwner(), entity.getExpMonth(), entity.getExpYear(), entity.getUser().getId(),
					entity.getId() };
		}
	}

	@Override
	protected CreditCard getEntityFromResultSet(ResultSet resultSet) throws SQLException {
		CreditCard creditCard = new CreditCard();
		creditCard.setId(resultSet.getLong("id"));
		creditCard.setCardOwner(resultSet.getString("card_ownername"));
		creditCard.setCardType(resultSet.getString("card_type"));
		creditCard.setCardNumber(resultSet.getString("card_number"));
		creditCard.setExpMonth(resultSet.getInt("card_expmonth"));
		creditCard.setExpYear(resultSet.getInt("card_expyear"));
		return creditCard;
	}

}

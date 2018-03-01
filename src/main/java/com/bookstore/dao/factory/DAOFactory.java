package com.bookstore.dao.factory;

import com.bookstore.dao.AddressDao;
import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.CategoryDao;
import com.bookstore.dao.CreditCardDao;
import com.bookstore.dao.OrderDao;
import com.bookstore.dao.OrderItemDao;
import com.bookstore.dao.ProductDao;
import com.bookstore.dao.UserDao;

/**
 * This interface represents a Abstract DAO factory for a SQL data source. You can
 * use {@link #getDAOFactory(String)} to obtain a new instance for the given
 * data source connectivity type. The specific instance returned depends on the
 * name passed. You can obtain DAO's for the DAO factory instance using the DAO
 * getters.
 * 
 * Here is a basic use example:
 * 
 * <pre>
 * DAOFactory jdbcFactory = DAOFactory.getDAOFactory(DAOFactory.JDBC);
 * UserDAO userDAO = jdbcFactory.getUserDAO();
 * </pre>
 *
 * @author Shaheer
 */
public interface DAOFactory {
	
	// List of DAO types supported by the factory
	enum FactoryType {
		JDBC, HIBERNATE;
	}

	/**
	 * Returns a new DAOFactory instance for the given data source connectivity
	 * type.
	 * 
	 * @param name
	 *            The DAO type to return a new DAOFactory instance for.
	 * @return A new DAOFactory instance for the given DAO type.
	 */
	public static DAOFactory getDAOFactory(FactoryType whichFactory) {
		switch (whichFactory) {
		case JDBC:
			return new DAOFactoryJDBC();
		case HIBERNATE:
			throw new IllegalArgumentException("Hibernate DAOFactory type currently not supported.");
		default:
			throw new IllegalArgumentException("DAOFactory type not supported.");
		}
	}
	
	// Add your DAO interfaces here
    ProductDao getProductDao();
    
    CategoryDao getCategoryDao();
    
    AuthorDao getAuthorDao();
    
    UserDao getUserDao();
    
    AddressDao getAddressDao();
    
    CreditCardDao getCreditCardDao();
    
    OrderDao getOrderDao();
    
    OrderItemDao getOrderItemDao();

}

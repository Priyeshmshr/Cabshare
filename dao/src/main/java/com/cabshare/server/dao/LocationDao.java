/**
 * @author Priyesh Mishra
 * Created on 27-Dec-2015
 */
package com.cabshare.server.dao;

import java.sql.Connection;

import com.cabshare.server.properties.Properties;
/**
 * Performs CRUD operations on user location table.
 * This object will be used to update the location of the user in location table.
 * Yet to be implemented.
 *
 */
public class LocationDao implements Dao{

	private DaoFactory dataStore=null;
	private Connection conn = null;
	public LocationDao(){
		dataStore = DaoFactory.getDataStore(Properties.MySqlDB);
		conn = dataStore.getConnection();
	}
	@Override
	public int create(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object retrieve(String primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

}

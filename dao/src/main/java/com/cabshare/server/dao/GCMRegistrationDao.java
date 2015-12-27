/**
 * @author Priyesh Mishra
 * Created on 27-Dec-2015
 */
package com.cabshare.server.dao;

import java.sql.Connection;

import com.cabshare.server.properties.Properties;
/**
 * Perform CRUD operations on GCM Registration ID's table. 
 * This object will be used to retrieve registration id while sending downstream messages. 
 * Yet to be implemented.
 */
public class GCMRegistrationDao implements Dao{

	private DaoFactory dataStore=null;
	private Connection conn = null;
	
	public GCMRegistrationDao(){
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

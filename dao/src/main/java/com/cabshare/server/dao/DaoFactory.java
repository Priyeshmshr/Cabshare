/**
 * Created by Priyesh Mishra on 27-Dec-2015
 */

package com.cabshare.server.dao;

import java.sql.Connection;

 /** 
 *  Abstract Factory class to get instance of a database.
 *  This class hides the database details from the DAO objects.
 *  
 */
public abstract class DaoFactory {

	public abstract Dao createDAO(String dataTableName);
	public abstract Connection getConnection();
	public static DaoFactory getDataStore(String dataStoreName){
		switch(dataStoreName){
		case "MySql":
			return JdbcDaoFactory.getInstance();
		case "":
			return null;
		}
		return null;
	}
}

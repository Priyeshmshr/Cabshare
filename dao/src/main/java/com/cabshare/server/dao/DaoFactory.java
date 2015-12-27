/*
 * Created by Priyesh Mishra on 27-Dec-2015
 */

package com.cabshare.server.dao;

public abstract class DaoFactory {

	public abstract Dao createDAO(String dataTableName);
	
	public DaoFactory getInstance(String dataStoreName){
		switch(dataStoreName){
		case "MySql":
			return new JdbcDaoFactory();
		case "":
			return null;
		}
		return null;
	}
}

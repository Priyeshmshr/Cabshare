/*
 * Created by Priyesh Mishra on 27-Dec-2015
 */
package com.cabshare.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.cabshare.server.properties.Properties;

public class JdbcDaoFactory extends DaoFactory {

	static String user = null;
	static String password = null;
	private static Connection conn;
	// All these could constant Strings , or even better read from a file
	Logger logger = Logger.getLogger("SmackCcsClient");

	public Connection getConnection() {
		// TODO Auto-generated method stub
		if (user == null)
			user = "root";
		// user = "admincS4cVWT";
		// password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		if (password == null)
			password = "";
		// password = "dyuwJbupZX8N";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Properties.url, user, password);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public Dao createDAO(String dataTableName) {
		// TODO Auto-generated method stub
		switch(dataTableName){
		case "User":
			return new UserDao();
		case "Location":
			return new LocationDao();
		case "GCMRegistraion":
			return new GCMRegistrationDao();
		}
		return null;
	}
}

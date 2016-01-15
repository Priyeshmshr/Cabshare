/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.dao.dao;

import com.cabshare.server.properties.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JDBCConnection {

	static String user = null;
	static String password = null;
    private static Connection conn;
    // All these could constant Strings , or even better read from a file
    Logger logger = Logger.getLogger("SmackCcsClient");

	public static Connection getConnection() {
		//user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		if (user == null)
			user = "root";
			//user = "admincS4cVWT";
		//password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		if (password == null)
			password ="";
			//password = "dyuwJbupZX8N";
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
}

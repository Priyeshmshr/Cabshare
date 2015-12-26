/*
 * Created by Priyesh Mishra on 15-Dec-2015
 */
package com.cabshare.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cabshare.server.entities.User;
import com.cabshare.server.properties.Properties;
// Eclipse testing
public class UserAuthDAO implements UserAuthDAOInterface {

	public Connection conn;
	PreparedStatement psmnt = null;
	Logger logger = Logger.getLogger("SmackCcsClient");

	public UserAuthDAO() {
		conn = JDBCConnection.getConnection();
	}
	/*
	 * (non-Javadoc)
	 * @see com.cabshare.server.dao.UserAuthDAOInterface#login(java.lang.String, java.lang.String)
	 * 
	 * Login
	 */
	public String login(String username, String pwd) {
		// TODO Auto-generated method stub
		String passwd=null;
		try {
			psmnt = conn.prepareStatement("select pwd from "+Properties.DB_NAME+".csusers where uid=?");
			psmnt.setString(1, username);
			ResultSet rs = psmnt.executeQuery();
			while (rs.next()) {
				passwd = rs.getString("pwd");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.SEVERE," Login failed: "+ e.getMessage());
			return "Failure";
		}
		if(passwd==null)
			return "Username doesn't exist";
		else if(passwd.equals(pwd))
			return "success";
		else
			return "Wrong Password";
	}
	public String registration(User user) {
		// TODO Auto-generated method stub
		try{
			//psmnt = conn.prepareStatement(query);
		}
		catch(Exception e){
			
		}
		return null;
	}
}

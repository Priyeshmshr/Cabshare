/**
 * @author Priyesh Mishra
 * Created on 27-Dec-2015
 */
package com.cabshare.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.cabshare.server.entities.User;
import com.cabshare.server.properties.Properties;

/**
 * 
 * UserDao Class for CRUD operations on User table (csusers in our DB, might have to change the name)
 *
 */
public class UserDao implements Dao{

	private DaoFactory dataStore=null;
	private Connection conn = null;
	private PreparedStatement psmnt;
	public UserDao(){
		dataStore = DaoFactory.getDataStore(Properties.MySqlDB);
		conn = dataStore.getConnection();
	}
	/**
	 * Inserts an user's details in the user table(csusers in our DB)
	 * @param Object of the entity class which is mapped to the users table.
	 */
	@Override
	public int create(Object object) {
		// TODO Auto-generated method stub
		User user = (User)object;
		int rowCount = 0;
		try {
			psmnt = conn.prepareStatement("insert into " + Properties.DB_NAME + ".csusers(uid,pwd,fn,gndr,phn) values(?,?,?,?,?)");
			psmnt.setString(1, user.getUsername());
			psmnt.setString(2, user.getPassword());
			psmnt.setString(3, user.getFullName());
			psmnt.setString(4, user.getGender());
			psmnt.setString(5, user.getContact_no());
			rowCount = psmnt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * Retrieves the details of an users. 
	 * @param primary key of the users table
	 */
	@Override
	public Object retrieve(String primaryKey) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			psmnt = conn.prepareStatement("select * from " + Properties.DB_NAME + ".csusers where uid =?");
			psmnt.setString(1, primaryKey);
			ResultSet rs = psmnt.executeQuery();
			while(rs.next()){
				user = new User();
				user.setUsername(rs.getString("uid"));
				user.setContact_no(rs.getString("phn"));
				user.setPassword(rs.getString("pwd"));
				user.setGender(rs.getString("gndr"));
				user.setFullName(rs.getString("fn"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Updates an user's details in the user table(csusers in our DB)
	 * @param Object of the entity class which is mapped to the users table.
	 */
	@Override
	public boolean update(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Deletes an user's details from the user table(csusers in our DB)
	 * @param Object of the entity class which is mapped to the users table.
	 */
	@Override
	public boolean delete(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
}

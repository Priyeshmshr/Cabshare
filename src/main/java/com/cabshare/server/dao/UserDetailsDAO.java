/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */
package com.cabshare.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cabshare.server.entities.User;
import com.cabshare.server.properties.Properties;


public class UserDetailsDAO implements UserDetailsDAOInterface {

	public Connection conn;
	PreparedStatement psmnt= null;
	Logger logger = Logger.getLogger("SmackCcsClient");
	public UserDetailsDAO (){
		conn = JDBCConnection.getConnection();
	}
	public void Update(User user) throws SQLException {
		// TODO Auto-generated method stud
		// Code for uploading the registration id into the database
		try {
			psmnt = conn.prepareStatement("update "+Properties.DB_NAME+".sess set regid = ? where uid=?");
			psmnt.setString(1, user.getRegID());
			psmnt.setString(2, user.getId());

			int s = psmnt.executeUpdate();
			if (s > 0) {
				logger.log(Level.INFO, "Successful!!");
			} else {
				logger.log(Level.WARNING, "Unsuccessful!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.WARNING, "Exception: " + e.getMessage());
		} finally {
			psmnt.close();
		}
	}

	public Map<String, String> Location(User user) throws SQLException {
		// TODO Auto-generated method stub
		Map<String,String> tosend= new HashMap<String,String>();
		   int s=0;
		   try {
			psmnt = conn.prepareStatement("select uid from "+Properties.DB_NAME+".sess where regid = ?");
			psmnt.setString(1, user.getRegID());
			ResultSet rs = psmnt.executeQuery();
			String uid=null;
			while(rs.next()){
				uid = rs.getString("uid");
			}
			if(!uid.isEmpty()){
			psmnt = conn.prepareStatement("insert into "+Properties.DB_NAME+".coords(user_id, type ,startLat, startLon ,destLat,destLon) values(?,?,?,?,?,?) ");
			psmnt.setString(1, user.getId());
			psmnt.setString(2, user.getRequestType());
			psmnt.setDouble(3, Double.valueOf(user.getStartLat()));
			psmnt.setDouble(4, Double.valueOf(user.getStartLon()));
			psmnt.setDouble(5, Double.valueOf(user.getDestLat()));
			psmnt.setDouble(6, Double.valueOf(user.getDestLon()));
			s = psmnt.executeUpdate();
	        if(s>0){
		    	logger.log(Level.INFO,"Success");
		        tosend = getSuggestion(user);  	
			}
		    else{
		    	logger.log(Level.WARNING,"Error");
	            tosend.put("error", "Unsuccessful");
		    }
			}
			else{
				logger.log(Level.WARNING,"uid doesn't exist!!");
				tosend.put("error", "uid doesn't exist!!");
			}
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			   e.printStackTrace();
			   logger.log(Level.WARNING,"Exception::"+ e.getMessage());
			   tosend.put("error", e.getMessage());
		   }finally{
			   psmnt.close();
		   }
		   return tosend;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getSuggestion(User user) {
		// TODO Auto-generated method stub
		try {
			   JSONArray json = new JSONArray();
			   ResultSet rs = null;
			   Double SLonLen = Math.cos(Double.valueOf(user.getStartLat()))*111.325, DLonLen = Math.cos(Double.valueOf(user.getDestLat()))*111.325; //This many kilometres is 1 degree longitude 
			   SLonLen = SLonLen/60;//1 minute longitude is this many kilometres 
			   DLonLen = DLonLen/60;//1 minute longitude is this many kilometres
			   Double temp = 6/SLonLen; // minutes = 5-6 kilometres
			   SLonLen = temp/60; // minutes in decimal
			   temp = 6/DLonLen;
			   DLonLen = temp/60;//minutes in decimal
			   psmnt = conn.prepareStatement("select s.uid, s.regid, c.startLat, c.startLon, c.destLat, c.destLon from "+Properties.DB_NAME+".coords c , "+Properties.DB_NAME+".sess s where c.type = ? and c.startLat <= ?+0.05 and " +
			   		"c.startLon <= ?+? and c.destLat <= ? + 0.05 and c.destLon <= ? + ? and c.startLat >= ?-0.05 and " +
			   		"c.startLon >= ?-? and c.destLat >= ?- 0.05 and c.destLon >= ? - ? and s.uid = c.user_id"); //Query to get suggestions within 5-6 kilometres of range.
			   if(user.getRequestType()!=null && user.getRequestType().equals("requester"))
			      psmnt.setString(1,"sharer");
			   else if(user.getRequestType()!=null && user.getRequestType().equals("sharer"))
				  psmnt.setString(1, "requester");
			   
			   psmnt.setDouble(2, Double.valueOf(user.getStartLat()));
			   psmnt.setDouble(3, Double.valueOf(user.getStartLon()));
			   psmnt.setDouble(4, SLonLen);
			   psmnt.setDouble(5, Double.valueOf(user.getDestLat()));
			   psmnt.setDouble(6, Double.valueOf(user.getDestLon()));
			   psmnt.setDouble(7, DLonLen);
			   psmnt.setDouble(8,Double.valueOf(user.getStartLat()));
			   psmnt.setDouble(9, Double.valueOf(user.getStartLat()));
			   psmnt.setDouble(10, SLonLen);
			   psmnt.setDouble(11,Double.valueOf(user.getDestLat()));
			   psmnt.setDouble(12, Double.valueOf(user.getDestLon()));
			   psmnt.setDouble(13,DLonLen);
			   rs=psmnt.executeQuery(); 
			   while(rs.next()){
				   JSONObject jsonObj = new JSONObject();
				   jsonObj.put("user_id", rs.getString("uid"));
				   logger.log(Level.INFO,rs.getString("uid"));
				   jsonObj.put("regid", rs.getString("regid"));
				   jsonObj.put("startLat", String.valueOf(rs.getDouble("startLat")));
				   jsonObj.put("startLon", String.valueOf(rs.getDouble("startLon")));
				   jsonObj.put("destLat", String.valueOf(rs.getDouble("destLat")));
				   jsonObj.put("destLon", String.valueOf(rs.getDouble("destLon")));
				   json.add(jsonObj);
			   }
			   JSONObject suggestions = new JSONObject();
			   suggestions.put("list", json);
			   Map<String,String> data=new HashMap<String, String>();
			   data.put("data", suggestions.toJSONString());
			   data.put("Response_type", "userList");//New line, check if getting error.
			  // String tosend = suggestions.toJSONString();
			   return data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.WARNING,"Exception in getSuggestions()::"+ e.getMessage());
		}
		   return null;
	}
}

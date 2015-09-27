package GcmServer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUpdate {
	Connection conn;
	String url=null,user=null,password=null;
	Logger logger = Logger.getLogger("SmackCcsClient");
	public DbUpdate()throws IOException,SQLException
	{
		//url = "jdbc:mysql://db4free.net:3306/csdb";	
		url="jdbc:mysql://127.9.96.130:3306/csdb";
		user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		if(user == null)
		user = "adminx7Ikp6v";
		password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		if(password==null)
		password ="rzA3E3AtpM29";
		try {
			   Class.forName("com.mysql.jdbc.Driver");
			}
			catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			   }
		conn = DriverManager.getConnection(url, user, password);
	}
	
	public void Update(String regid,String userId)throws IOException, SQLException{
		PreparedStatement psmnt =null;
		//Code for uploading the registration id into the database
		 try {
			psmnt = conn.prepareStatement("update csdb.sess set regid = ? where uid=?");
			psmnt.setString(1,regid);
			psmnt.setString(2,userId);
			
			int s= psmnt.executeUpdate();
			if(s>0){
				//System.out.println("Successful");
				logger.log(Level.INFO,"Successful!!");
			}
			else{
				//System.out.println("Something is wrong!!");
				logger.log(Level.WARNING,"Unsuccessful!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.WARNING,"Exception: "+e.getMessage());
		}finally{
			//conn.close();
			psmnt.close();
		}
	}
   public Map<String,String> Location(String startLat,String startLon,String destLat,String destLon,String type,String userid,String regId) throws SQLException{
	   PreparedStatement ps =null;
	   Map<String,String> tosend= new HashMap<String,String>();
	   int s=0;
	   try {
		ps = conn.prepareStatement("select uid from csdb.sess where regid = ?");
		ps.setString(1, regId);
		ResultSet rs = ps.executeQuery();
		String uid=null;
		while(rs.next()){
			uid = rs.getString("uid");
		}
		if(!uid.isEmpty()){
		ps = conn.prepareStatement("insert into csdb.coords(user_id, type ,startLat, startLon ,destLat,destLon) values(?,?,?,?,?,?) ");
		ps.setDouble(3, Double.valueOf(startLat));
		ps.setDouble(4, Double.valueOf(startLon));
		ps.setDouble(5, Double.valueOf(destLat));
		ps.setDouble(6, Double.valueOf(destLon));
		ps.setString(2, type);
		ps.setString(1, userid);
		s = ps.executeUpdate();
        if(s>0){
	    	logger.log(Level.INFO,"Success");
	        tosend = getSuggestion(type,startLat,startLon,destLat,destLon);  	
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
		   ps.close();
	   }
	   return tosend;
    }
   @SuppressWarnings("unchecked")
public Map<String,String> getSuggestion(String ttype,String SLat,String SLon,String DLat,String DLon){
	   PreparedStatement ps = null;
	   try {
		   JSONArray json = new JSONArray();
		   ResultSet rs = null;
		   Double SLonLen = Math.cos(Double.valueOf(SLat))*111.325, DLonLen = Math.cos(Double.valueOf(DLat))*111.325; //This many kilometres is 1 degree longitude 
		   SLonLen = SLonLen/60;//1 minute longitude is this many kilometres 
		   DLonLen = DLonLen/60;//1 minute longitude is this many kilometres
		   Double temp = 6/SLonLen; // minutes = 5-6 kilometres
		   SLonLen = temp/60; // minutes in decimal
		   temp = 6/DLonLen;
		   DLonLen = temp/60;//minutes in decimal
		   ps = conn.prepareStatement("select s.uid, s.regid, c.startLat, c.startLon, c.destLat, c.destLon from csdb.coords c , csdb.sess s where c.type = ? and c.startLat <= ?+0.05 and " +
		   		"c.startLon <= ?+? and c.destLat <= ? + 0.05 and c.destLon <= ? + ? and c.startLat >= ?-0.05 and " +
		   		"c.startLon >= ?-? and c.destLat >= ?- 0.05 and c.destLon >= ? - ? and s.uid = c.user_id"); //Query to get suggestions within 5-6 kilometres of range.
		   if(ttype.equals("requester"))
		      ps.setString(1,"sharer");
		   else if(ttype.equals("sharer"))
			  ps.setString(1, "requester");
		   
		   ps.setDouble(2, Double.valueOf(SLat));
		   ps.setDouble(3, Double.valueOf(SLon));
		   ps.setDouble(4, SLonLen);
		   ps.setDouble(5, Double.valueOf(DLat));
		   ps.setDouble(6, Double.valueOf(DLon));
		   ps.setDouble(7, DLonLen);
		   ps.setDouble(8,Double.valueOf(SLat));
		   ps.setDouble(9, Double.valueOf(SLon));
		   ps.setDouble(10, SLonLen);
		   ps.setDouble(11,Double.valueOf(DLat));
		   ps.setDouble(12, Double.valueOf(DLon));
		   ps.setDouble(13,DLonLen);
		   rs=ps.executeQuery(); 
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

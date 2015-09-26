package com.cabshare.connection;

import java.sql.SQLException;
import java.util.Map;

public interface DAOUserDetailsInterface {

	public void Update(String regid,String userId) throws SQLException;
	public Map<String,String> Location(String startLat,String startLon,String destLat,String destLon,String type,String userid,String regId) throws SQLException;
	   //@SuppressWarnings("unchecked")
	public Map<String,String> getSuggestion(String ttype,String SLat,String SLon,String DLat,String DLon);
}

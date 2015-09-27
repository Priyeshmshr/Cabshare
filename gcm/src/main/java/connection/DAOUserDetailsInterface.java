package connection;

import java.sql.SQLException;
import java.util.Map;

public interface DAOUserDetailsInterface {

	void Update(String regid, String userId) throws SQLException;

	Map<String, String> Location(String startLat, String startLon, String destLat, String destLon, String type, String userid, String regId) throws SQLException;
	   //@SuppressWarnings("unchecked")
	   Map<String, String> getSuggestion(String ttype, String SLat, String SLon, String DLat, String DLon);
}

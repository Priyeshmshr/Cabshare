/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.dao;

import java.sql.SQLException;
import java.util.Map;
import com.cabshare.server.entities.User;

public interface UserDetailsInterface {

	public void Update(User user) throws SQLException;
	public Map<String,String> Location(User user) throws SQLException;
	   //@SuppressWarnings("unchecked")
	public Map<String,String> getSuggestion(User user);
}

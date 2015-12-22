/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.dao;

import com.cabshare.server.entities.User;

import java.sql.SQLException;
import java.util.Map;

/**
 * //TODO
 *
 * @deepak again have a chat with me about this  ,these are application layer stuff , we'll more layer of abstract doesnt really belong in a dao
 */

public interface UserDetailsDAOInterface {

    void Update(User user) throws SQLException;

    Map<String, String> Location(User user) throws SQLException;

    //@SuppressWarnings("unchecked")
    Map<String, String> getSuggestion(User user);
}

/*
 * Created by Priyesh Mishra on 15-Dec-2015
 */
package com.cabshare.server.dao;

import com.cabshare.server.entities.User;

public interface UserAuthDAOInterface {

	public String login(String username, String pwd);

	public String registration(User user);
}

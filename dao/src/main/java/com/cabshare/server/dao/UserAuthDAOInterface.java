/*
 * Created by Priyesh Mishra on 15-Dec-2015
 */
package com.cabshare.server.dao;

import com.cabshare.server.entities.User;


/**
 * @deepak :- not really the place for these functions (login , regsitration) in a DAO
 */
public interface UserAuthDAOInterface {

    String login(String username, String pwd);

    String registration(User user);
}

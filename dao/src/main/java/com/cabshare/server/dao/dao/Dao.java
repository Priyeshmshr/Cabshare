package com.cabshare.server.dao.dao;

import com.mysql.jdbc.Connection;

/**
 * Created by deepak on 3/1/16.
 */
public interface Dao {

    java.sql.Connection getConnection() throws ClassNotFoundException;
}

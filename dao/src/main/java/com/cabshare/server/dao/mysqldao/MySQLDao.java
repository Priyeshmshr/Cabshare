package com.cabshare.server.dao.mysqldao;

import com.cabshare.server.dao.dao.Dao;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by deepak on 3/1/16.
 */
public class MySQLDao implements Dao {
   public static java.sql.Connection connection;

   protected MySQLDao() throws ClassNotFoundException, SQLException {
       Class.forName("com.mysql.jdbc.Driver");
       connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newcabshare", "root", "sangeeta");
   }

    protected MySQLDao(String URL , String username , String password ) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newcabshare", "root", "sangeeta");
    }


    @Override
    public java.sql.Connection getConnection() throws ClassNotFoundException {
        return connection;
    }
}

package com.cabshare.server.dao.mysqldao.EntityDAO;

import com.cabshare.server.dao.mysqldao.MySQLDao;
import com.cabshare.server.entities.Interfaces.Users.UserProfile;
import com.sun.corba.se.spi.orbutil.fsm.Guard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by deepak on 3/1/16.
 */
public class UserDAO extends MySQLDao  {



    protected UserDAO() throws ClassNotFoundException, SQLException {
    }

    protected UserDAO(String URL, String username, String password) throws ClassNotFoundException, SQLException {
        super(URL, username, password);
    }

    public List<UserProfile> getUsersByID( String emailID)
    {

    }

    public void insertUserProfile( UserProfile userProfile)
    {

    }

    public void queryUserTable ( String query) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){

        }

    }


}

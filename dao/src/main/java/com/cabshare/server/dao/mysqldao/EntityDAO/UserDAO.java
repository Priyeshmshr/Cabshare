package com.cabshare.server.dao.mysqldao.EntityDAO;

import com.cabshare.server.dao.mysqldao.MySQLDao;
import com.cabshare.server.entities.EntityPOJO.UserProfilePOJO;
import com.cabshare.server.entities.Interfaces.Users.UserProfile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<UserProfilePOJO> getUserBy(String key, String value) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Select ?,?,?,?,? from User where ?=?");
        preparedStatement.setString(1, com.cabshare.server.entities.schema.UserProfile.email);
        preparedStatement.setString(2, com.cabshare.server.entities.schema.UserProfile.Date_Joined);
        preparedStatement.setString(3, com.cabshare.server.entities.schema.UserProfile.gender);
        preparedStatement.setString(4, com.cabshare.server.entities.schema.UserProfile.name);
        preparedStatement.setString(5, com.cabshare.server.entities.schema.UserProfile.phone_number);
        preparedStatement.setString(6, key);
        preparedStatement.setString(7, value);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<UserProfilePOJO> userProfilePOJOs = new ArrayList<UserProfilePOJO>();
        while (resultSet.next()) {
            UserProfilePOJO userProfile = new UserProfilePOJO();
            userProfile.setEmailID(resultSet.getString(com.cabshare.server.entities.schema.UserProfile.email));
            userProfile.setDate(resultSet.getDate(com.cabshare.server.entities.schema.UserProfile.Date_Joined));
            userProfile.setGender(resultSet.getString(com.cabshare.server.entities.schema.UserProfile.gender));
            userProfile.setPhoneNumber(resultSet.getString(com.cabshare.server.entities.schema.UserProfile.phone_number));
            userProfile.setName(resultSet.getString(com.cabshare.server.entities.schema.UserProfile.name));
            userProfilePOJOs.add(userProfile);
        }
        return userProfilePOJOs;
    }

    public void insertUserProfile(UserProfile userProfile) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into User values  ?,?,?,?,? from User where ?=?");
        preparedStatement.setString(1, com.cabshare.server.entities.schema.UserProfile.email);
        preparedStatement.setString(2, com.cabshare.server.entities.schema.UserProfile.Date_Joined);
        preparedStatement.setString(3, com.cabshare.server.entities.schema.UserProfile.gender);
        preparedStatement.setString(4, com.cabshare.server.entities.schema.UserProfile.name);
        preparedStatement.setString(5, com.cabshare.server.entities.schema.UserProfile.phone_number);
    }

    public void queryUserTable ( String query) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){

        }

    }


}

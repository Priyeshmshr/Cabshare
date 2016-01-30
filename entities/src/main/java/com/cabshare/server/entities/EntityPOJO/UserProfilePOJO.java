package com.cabshare.server.entities.EntityPOJO;

import com.cabshare.server.entities.Interfaces.Users.UserProfile;

import java.util.Date;

/**
 * Created by deepak on 3/1/16.
 */
public class UserProfilePOJO implements UserProfile {


    String emailID;
    Date date;
    String phoneNumber;
    String gender;
    String name;



    @Override
    public String getEmailID() {
        return this.emailID;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public String getName() {
        return this.name;
    }


    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }
}

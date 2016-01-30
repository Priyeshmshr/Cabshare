package com.cabshare.server.entities.Interfaces.Users;

import java.util.Date;

/**
 * Created by deepak on 3/1/16.
 */

public interface UserProfile {

    String getEmailID();
    Date getDate();
    String getPhoneNumber();
    String getGender();
    String getName();

}

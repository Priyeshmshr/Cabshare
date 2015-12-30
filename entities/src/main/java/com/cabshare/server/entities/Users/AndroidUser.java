package com.cabshare.server.entities.Users;

import com.cabshare.server.entities.Users.Person;

/**
 * Created by deepak on 30/12/15.
 */
public interface AndroidUser extends Person {

       String getGCMRegistrationID();
}

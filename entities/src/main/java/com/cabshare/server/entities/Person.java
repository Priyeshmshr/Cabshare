package com.cabshare.server.entities;


/**
 * @author deepak on 29/9/15.
 *         Base entity reflecting an entity that can take multiple roles ie CabRequester , Cabrider , logged in User etc
 */


public interface Person {

    String getName();

    String getEmail();
}

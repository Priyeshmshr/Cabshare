/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.properties;

/**
 * better to make a config.properties file , does openshift allow this ? storing a text file on it ?
 */

public class Properties {

	  public static final String userName = "1054444576671" + "@gcm.googleapis.com";
      public static final String password = "AIzaSyCgNKkvTFW5FzV5XVf5KYNtXls39hGtLiM";
      public static final String DB_NAME = "cabshare";
      //public static final String url = "jdbc:mysql://127.5.86.2:3306/"+DB_NAME;  //This is OpenShift's DB url.
      public static final String url = "jdbc:mysql://127.0.0.1:3306/"+DB_NAME;
	  public static final String GCM_SERVER = "gcm.googleapis.com";
	  public static final int GCM_PORT = 5235;
	  public static final String GCM_ELEMENT_NAME = "gcm";
	  public static final String GCM_NAMESPACE = "google:mobile:data";
	  public static final String MySqlDB = "MySql";
}

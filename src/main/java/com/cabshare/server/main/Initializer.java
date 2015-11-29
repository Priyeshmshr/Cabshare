/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 *   
 *   Starts the Backend server
 */

package com.cabshare.server.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.smack.XMPPException;
import com.cabshare.server.gcm.Server;

public class Initializer {
	public static void main(String [] args) throws IOException, SQLException {
		
	    /*//code to connect to port 8080
	    String repo = System.getenv("OPENSHIFT_REPO_DIR");
	    if(repo == null) {
	        repo = ".";
	    }
	    String ip = System.getenv("OPENSHIFT_DIY_IP");
	    if(ip == null) {
	        ip = "127.9.96.129";
	    }
	    String ports = System.getenv("OPENSHIFT_DIY_PORT");
	    if(ports == null) {
	        ports = "8080";
	    }
	    int port = Integer.decode(ports);
	    InetAddress isa;
		try {
			isa = InetAddress.getByName(ip);
			httpServer = HttpServer.create(new InetSocketAddress(isa, port), 10);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    httpServer.setExecutor(null);
	    httpServer.start();
	    //code over*/
	    
	    Server ccsClient = new Server();
	    try {
	      ccsClient.connect();
	      //db= new DbUpdate();
	    } catch (XMPPException e) {
	      e.printStackTrace();
	    }
	    
	    //Send a sample hello downstream message to a device.
	    String toRegId = "RegistrationIdOfTheTargetDevice";
	    String messageId = ccsClient.getRandomMessageId();
	    Map<String, String> payload = new HashMap<String, String>();
	    payload.put("Hello", "World");
	    payload.put("CCS", "Dummy Message");
	    payload.put("EmbeddedMessageId", messageId);
	    String collapseKey = "sample";
	    Long timeToLive = 10000L;
	    Boolean delayWhileIdle = true;
	    ccsClient.send(Server.createJsonMessage(toRegId, messageId, payload, collapseKey,
	        timeToLive, delayWhileIdle));
	  }
}


/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 */

package com.cabshare.server.gcm;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.xmlpull.v1.XmlPullParser;

import com.cabshare.server.dao.UserAuthDAO;
import com.cabshare.server.dao.UserAuthDAOInterface;
import com.cabshare.server.dao.UserDetailsDAO;
import com.cabshare.server.dao.UserDetailsDAOInterface;
import com.cabshare.server.entities.User;
import com.cabshare.server.properties.Properties;
/**
 * Sample Smack implementation of a client for GCM Cloud Connection Server.
 *
 * <p>For illustration purposes only.
 */
public class Server{

  Logger logger = Logger.getLogger("SmackCcsClient");
  static Random random = new Random();
  XMPPConnection connection;
  ConnectionConfiguration config;
  /**
   * XMPP Packet Extension for GCM Cloud Connection Server.
   */
  public Server() {
    // Add GcmPacketExtension
    ProviderManager.getInstance().addExtensionProvider(Properties.GCM_ELEMENT_NAME,
        Properties.GCM_NAMESPACE, new PacketExtensionProvider() {
    	
      public PacketExtension parseExtension(XmlPullParser parser)
          throws Exception {
        String json = parser.nextText();
        GcmPacketExtension packet = new GcmPacketExtension(json);
        return packet;
      }
    });
  }

  /**
   * Returns a random message id to uniquely identify a message.
   *
   * <p>Note:
   * This is generated by a pseudo random number generator for illustration purpose,
   * and is not guaranteed to be unique.
   *
   */
  public String getRandomMessageId() {
    return "m-" + Long.toString(random.nextLong());
  }

  /**
   * Sends a downstream GCM message.
   */
  public void send(String jsonRequest) {
    Packet request = new GcmPacketExtension(jsonRequest).toPacket();
    connection.sendPacket(request);
  }

  /**
   * Handles an upstream data message from a device application.
   *
   * <p>This sample echo server sends an echo message back to the device.
   * Subclasses should override this method to process an upstream message.
   */
  public void handleIncomingDataMessage(Map<String, Object> jsonObject) {
    
	User user = new User();
	UserDetailsDAOInterface daoUser = new UserDetailsDAO();
	UserAuthDAOInterface userAuth = new UserAuthDAO();
	
	String from = jsonObject.get("from").toString();
    // PackageName of the application that sent this message.
    String category = jsonObject.get("category").toString();
    // Use the packageName as the collapseKey in the echo packet
    String collapseKey = "echo:CollapseKey";
    @SuppressWarnings("unchecked")
	final Map<String, String> payload = (Map<String, String>) jsonObject.get("data");
    
    /*
     * Stores registration ID of the user. Registration id is an unique id of the device.
     */
    if(payload.get("my_action").equals("Update Registration ID")){
    	try {
    		user.setRegID(payload.get("regID"));
    		user.setId(payload.get("id"));
			daoUser.Update(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * Updates current location of the user and sends the list of other users requesting/sharing cab in 5km Radius.
     */
    else if(payload.get("my_action").equals("Update Location")){
		try {
			user.setRegID(from);
			user.setId(payload.get("id"));
			user.setStartLat(payload.get("startLat"));
			user.setStartLon(payload.get("startLon"));
			user.setDestLat(payload.get("destLat"));
			user.setDestLon(payload.get("destLon"));
			user.setRequestType(payload.get("requestType"));
			Map<String,String> list = daoUser.Location(user);
			if(!list.equals("error") && !list.equals(null)){
				String jsonList = createJsonMessage(from, getRandomMessageId(), list, collapseKey, null,false);
				logger.log(Level.INFO,"Suggestions made!!");
				send(jsonList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.WARNING, ":: "+e.getMessage());
		}
    }
    /*
     * Sends the request to the other user to share the cab.
     */
    else if(payload.get("my_action").equals("sendRequest")){
    	String to = payload.get("ToRegId");
    	String info = payload.get("UserInfo");
    	Map<String,String> req = new HashMap<String,String>();
    	req.put("req", info);
    	req.put("Response_type","request");
    	String toSend = createJsonMessage(to,getRandomMessageId(),req,collapseKey,null,false);
    	send(toSend);
    }
    /*
     * Logs in an user.
     */
    else if(payload.get("my_action").equals("login")){
    	String result=userAuth.login(payload.get("username"), payload.get("pwd"));
    	Map<String,String> info= new HashMap<String,String>();
    	info.put("result", result);
    	String toSend = createJsonMessage(from,getRandomMessageId(),info,collapseKey,null,false);
    	send(toSend);
    }
    /*
     * Registers an user.
     */
    else if(payload.get("my_action").equals("registration")){
    	
    }
    logger.log(Level.INFO, "hello");
    payload.put("ECHO", "Application: " + category);
    // Send an ECHO response back
   // String echo = createJsonMessage(from, getRandomMessageId(), payload, collapseKey, null, false);
    //send(echo);
  }
/**
   * Handles an ACK.
   *
   * <p>By default, it only logs a INFO message, but subclasses could override it to
   * properly handle ACKS.
   */
  public void handleAckReceipt(Map<String, Object> jsonObject) {
    String messageId = jsonObject.get("message_id").toString();
    String from = jsonObject.get("from").toString();
    logger.log(Level.INFO, "handleAckReceipt() from: " + from + ", messageId: " + messageId);
  }

  /**
   * Handles a NACK.
   *
   * <p>By default, it only logs a INFO message, but subclasses could override it to
   * properly handle NACKS.
   */
  public void handleNackReceipt(Map<String, Object> jsonObject) {
    String messageId = jsonObject.get("message_id").toString();
    String from = jsonObject.get("from").toString();
    logger.log(Level.INFO, "handleNackReceipt() from: " + from + ", messageId: " + messageId);
  }

  /**
   * Creates a JSON encoded GCM message.
   *
   * @param to RegistrationId of the target device (Required).
   * @param messageId Unique messageId for which CCS will send an "ack/nack" (Required).
   * @param payload Message content intended for the application. (Optional).
   * @param collapseKey GCM collapse_key parameter (Optional).
   * @param timeToLive GCM time_to_live parameter (Optional).
   * @param delayWhileIdle GCM delay_while_idle parameter (Optional).
   * @return JSON encoded GCM message.
   */
  public static String createJsonMessage(String to, String messageId, Map<String, String> payload,
      String collapseKey, Long timeToLive, Boolean delayWhileIdle) {
    Map<String, Object> message = new HashMap<String, Object>();
    message.put("to", to);
    if (collapseKey != null) {
      message.put("collapse_key", collapseKey);
    }
    if (timeToLive != null) {
      message.put("time_to_live", timeToLive);
    }
    if (delayWhileIdle != null && delayWhileIdle) {
      message.put("delay_while_idle", true);
    }
    message.put("message_id", messageId);
    message.put("data", payload);
    return JSONValue.toJSONString(message);
  }

  /**
   * Creates a JSON encoded ACK message for an upstream message received from an application.
   *
   * @param to RegistrationId of the device who sent the upstream message.
   * @param messageId messageId of the upstream message to be acknowledged to CCS.
   * @return JSON encoded ack.
   */
  public static String createJsonAck(String to, String messageId) {
    Map<String, Object> message = new HashMap<String, Object>();
    message.put("message_type", "ack");
    message.put("to", to);
    message.put("message_id", messageId);
    return JSONValue.toJSONString(message);
  }

  /**
   * Connects to GCM Cloud Connection Server using the supplied credentials.
   *
   * @throws XMPPException
   */
  public void connect() throws XMPPException {
    config = new ConnectionConfiguration(Properties.GCM_SERVER, Properties.GCM_PORT);
    config.setSecurityMode(SecurityMode.enabled);
    config.setReconnectionAllowed(true);
    config.setRosterLoadedAtLogin(false);
    config.setSendPresence(false);
    config.setSocketFactory(SSLSocketFactory.getDefault());

    // NOTE: Set to true to launch a window with information about packets sent and received
    config.setDebuggerEnabled(true);

    // -Dsmack.debugEnabled=true
    XMPPConnection.DEBUG_ENABLED = false;

    connection = new XMPPConnection(config);
    connection.connect();

    connection.addConnectionListener(new ConnectionListener() {

      public void reconnectionSuccessful() {
        logger.info("Reconnecting..");
      }

      public void reconnectionFailed(Exception e) {
        logger.log(Level.INFO, "Reconnection failed.. ", e);
      }

      public void reconnectingIn(int seconds) {
        logger.log(Level.INFO, "Reconnecting in %d secs", seconds);
      }

      public void connectionClosedOnError(Exception e) {
        logger.log(Level.INFO, "Connection closed on error.");
      }

      public void connectionClosed() {
        logger.info("Connection closed.");
      }
    });

    // Handle incoming packets
    connection.addPacketListener(new PacketListener() {

      public void processPacket(Packet packet) {
        logger.log(Level.INFO, "Received: " + packet.toXML());
        Message incomingMessage = (Message) packet;
        GcmPacketExtension gcmPacket =
            (GcmPacketExtension) incomingMessage.getExtension(Properties.GCM_NAMESPACE);
        String json = gcmPacket.getJson();
        try {
          @SuppressWarnings("unchecked")
          Map<String, Object> jsonObject =
              (Map<String, Object>) JSONValue.parseWithException(json);

          // present for "ack"/"nack", null otherwise
          Object messageType = jsonObject.get("message_type");

          if (messageType == null) {
            // Normal upstream data message
            handleIncomingDataMessage(jsonObject);

            // Send ACK to CCS
            String messageId = jsonObject.get("message_id").toString();
            String from = jsonObject.get("from").toString();
            String ack = createJsonAck(from, messageId);
            send(ack);
          } else if ("ack".equals(messageType.toString())) {
            // Process Ack
            handleAckReceipt(jsonObject);
          } else if ("nack".equals(messageType.toString())) {
            // Process Nack
            handleNackReceipt(jsonObject);
          } else {
            logger.log(Level.WARNING, "Unrecognized message type (%s)",
                messageType.toString());
          }
        } catch (ParseException e) {
          logger.log(Level.SEVERE, "Error parsing JSON " + json, e);
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Couldn't send echo.", e);
        }
      }
    }, new PacketTypeFilter(Message.class));


    // Log all outgoing packets
    connection.addPacketInterceptor(new PacketInterceptor() {
      public void interceptPacket(Packet packet) {
        logger.log(Level.INFO, "Sent: {0}",  packet.toXML());
      }
    }, new PacketTypeFilter(Message.class));

    connection.login(Properties.userName, Properties.password);
  }

}
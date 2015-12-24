/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 *   
 *   User entity.
 */

package com.cabshare.server.entities;

public class User {

	String startLat, startLon, destLat, destLon, requestType, id, regID;

	public String getStartLat() {
		return startLat;
	}

	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}

	public String getStartLon() {
		return startLon;
	}

	public void setStartLon(String startLon) {
		this.startLon = startLon;
	}

	public String getDestLat() {
		return destLat;
	}

	public void setDestLat(String destLat) {
		this.destLat = destLat;
	}

	public String getDestLon() {
		return destLon;
	}

	public void setDestLon(String destLon) {
		this.destLon = destLon;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegID() {
		return regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}
	
}

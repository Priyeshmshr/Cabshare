/**
 *   Created by Priyesh Mishra on 22-OCT-2015.
 *   
 *   User entity.
 */

package com.cabshare.server.entities;

public class User {

	private String password, username, fullName, gender, contact_no, startLat, startLon, destLat, destLon, requestType,
			regID;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String firstName) {
		this.fullName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

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

	public String getRegID() {
		return regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}

}

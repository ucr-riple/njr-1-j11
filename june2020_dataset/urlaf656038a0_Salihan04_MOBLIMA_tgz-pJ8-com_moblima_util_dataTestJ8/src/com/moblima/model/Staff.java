package com.moblima.model;

import java.io.Serializable;

public class Staff implements Serializable {
	private Integer staffID;
	private String username;
	private String password;
	
	public Staff(String username, String password) {
		this.staffID = -1;
		this.username = username;
		this.password = password;
	}
	
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	public int getStaffID() {
		return staffID;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}

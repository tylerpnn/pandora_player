package com.tylerpnn.ui.gui;

import com.google.gson.Gson;

public class UserInfo {
	
	private String username;
	private char[] password;
	private boolean pandoraOne;
	
	public UserInfo() {
		this("", new char[0], false);
	}
	
	public UserInfo(String u, char[] p, boolean po) {
		username = u;
		password = p;
		pandoraOne = po;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public boolean isPandoraOne() {
		return pandoraOne;
	}
	public void setPandoraOne(boolean pandoraOne) {
		this.pandoraOne = pandoraOne;
	}
	
	public String toString() {
		return new Gson().toJson(this);
	}
}

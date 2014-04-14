package com.tylerpnn.json.request;


public class UserLoginRequest extends JsonRequest {

	private String loginType, username, password, partnerAuthToken;
	private long syncTime;
	
	public String getPartnerAuthToken() {
		return partnerAuthToken;
	}
	public void setPartnerAuthToken(String partnerAuthToken) {
		this.partnerAuthToken = partnerAuthToken;
	}
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
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public long getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}

	
}

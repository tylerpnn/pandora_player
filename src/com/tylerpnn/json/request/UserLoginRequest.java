package com.tylerpnn.json.request;


public class UserLoginRequest extends JsonRequest {

	private String loginType, username, password, partnerAuthToken;
	private long syncTime;

	public void setPartnerAuthToken(String partnerAuthToken) {
		this.partnerAuthToken = partnerAuthToken;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

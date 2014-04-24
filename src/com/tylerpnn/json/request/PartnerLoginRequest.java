package com.tylerpnn.json.request;

public class PartnerLoginRequest extends JsonRequest {
	
	private String username, password, deviceModel, version;

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}

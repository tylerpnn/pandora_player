package com.tylerpnn.json.request;

public class StationListRequest extends JsonRequest {

	private String userAuthToken;
	private long syncTime;

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

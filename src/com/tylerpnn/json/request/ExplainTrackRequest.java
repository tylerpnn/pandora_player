package com.tylerpnn.json.request;

public class ExplainTrackRequest extends JsonRequest {

	private String trackToken, userAuthToken;
	private long syncTime;
	
	public void setTrackToken(String trackToken) {
		this.trackToken = trackToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

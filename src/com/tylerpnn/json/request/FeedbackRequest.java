package com.tylerpnn.json.request;

public class FeedbackRequest extends JsonRequest {

	private String trackToken, stationToken, userAuthToken;
	private boolean isPositive;
	private long syncTime;
	
	public void setTrackToken(String trackToken) {
		this.trackToken = trackToken;
	}
	public void setIsPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}
	public void setStationToken(String stationToken) {
		this.stationToken = stationToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

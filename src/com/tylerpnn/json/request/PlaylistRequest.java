package com.tylerpnn.json.request;

public class PlaylistRequest extends JsonRequest {

	private String userAuthToken, stationToken;	
	private long syncTime;

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public void setStationToken(String stationToken) {
		this.stationToken = stationToken;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

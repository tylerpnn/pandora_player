package com.tylerpnn.json.request;

public class PlaylistRequest extends JsonRequest {

	private String userAuthToken, stationToken;	
	private long syncTime;
	private boolean includeTrackLength;
	
	public void setIncludeTrackLength(boolean b) {
		this.includeTrackLength = true;
	}
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

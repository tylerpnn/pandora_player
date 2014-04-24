package com.tylerpnn.json.request;

public class SetQuickMixRequest extends JsonRequest {

	private String userAuthToken;
	private String[] quickMixStationIds;
	private long syncTime;
	
	public void setUserAuthToken(String s) {
		this.userAuthToken = s;
	}
	public void setQuickMixStationIds(String[] s) {
		this.quickMixStationIds = s;
	}
	public void setSyncTime(long t) {
		this.syncTime = t;
	}
}

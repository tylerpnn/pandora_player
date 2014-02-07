package pandora;

import json.response.StationListResponse.Result.Station;

public class UserSession {

	private String username;
	private String password;
	private String userAuthToken;
	private String partnerAuthToken;
	private String partnerId;
	private String userId;
	private long syncTime;
	private long startTime;
	private Station[] stations;
	
	public UserSession(String username, String password) {
		this.username = username;
		this.password = password;
		startTime = System.currentTimeMillis() / 1000;
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	
	public long calcSyncTime() {
		return this.syncTime + (System.currentTimeMillis()/1000 - this.startTime);
	}
	
	public void setPartnerAuthToken(String token) {
		this.partnerAuthToken = token;
	}
	
	public String getPartnerAuthToken() {
		return this.partnerAuthToken;
	}
	
	public void setUserAuthToken(String token) {
		this.userAuthToken = token;
	}
	
	public String getUserAuthToken() {
		return this.userAuthToken;
	}
	
	public String getAuthToken() {
		if(this.userAuthToken != null) {
			return this.userAuthToken;
		} else {
			return this.partnerAuthToken;
		}
	}
	
	public void setSyncTime(long s) {
		this.syncTime = s;
	}
	
	public long getSyncTime() {
		return this.syncTime;
	}
	
	public void setPartnerId(String id) {
		this.partnerId = id;
	}
	
	public String getPartnerId() {
		return this.partnerId;
	}
	
	public void setUserId(String id) {
		this.userId = id;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setStations(Station[] stations) {
		this.stations = stations;
	}
	
	public Station[] getStations() {
		return this.stations;
	}
	
	public String toString() {
		String user = "";
		user += "username: " + username;
		user += ", password: " + password;
		user += ", partnerAuthToken: " + partnerAuthToken;
		user += ", partnerId: " + partnerId;
		user += ", userAuthToken: " + userAuthToken;
		user += ", userId: " + userId;
		return user;
	}
	
}

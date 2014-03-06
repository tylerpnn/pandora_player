package pandora;

import java.util.Map;

import json.response.PlaylistResponse.Result.SongInfo;
import json.response.StationListResponse.Result.StationInfo;

public class UserSession {

	private String username;
	private String password;
	private String userAuthToken;
	private String partnerAuthToken;
	private String partnerId;
	private String userId;
	private long syncTime;
	private long startTime;
	private Map<String, StationInfo> stations;
	private SongInfo[] currentPlaylist;
	
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
	
	public void setStations(Map<String, StationInfo> stations) {
		this.stations = stations;
	}
	
	public Map<String, StationInfo> getStations() {
		return this.stations;
	}
	
	public void setCurrentPlaylist(SongInfo[] songs) {
		this.currentPlaylist = songs;
	}
	
	public SongInfo[] getCurrentPlaylist() {
		return this.currentPlaylist;
	}
	
	public StationInfo getStationInfoByName(String stationName) {
		return stations.get(stationName);
	}
	
	public StationInfo getStationInfoById(String stationId) {
		for(StationInfo si : stations.values()) {
			if(si.getStationId().equals(stationId))
				return si;
		}
		return null;
	}	
}

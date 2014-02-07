package json.request;

public class PlaylistRequest extends JSONRequest {

	private String userAuthToken, stationToken;
	private long syncTime;
	
	public String getUserAuthToken() {
		return userAuthToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public String getStationToken() {
		return stationToken;
	}
	public void setStationToken(String stationToken) {
		this.stationToken = stationToken;
	}
	public long getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

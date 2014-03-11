package json.request;

public class FeedbackRequest extends JsonRequest {

	private String trackToken, stationToken, userAuthToken;
	private boolean isPositive;
	private long syncTime;
	
	public String getTrackToken() {
		return trackToken;
	}
	public void setTrackToken(String trackToken) {
		this.trackToken = trackToken;
	}
	public boolean getIsPositive() {
		return isPositive;
	}
	public void setIsPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}
	public String getStationToken() {
		return stationToken;
	}
	public void setStationToken(String stationToken) {
		this.stationToken = stationToken;
	}
	public String getUserAuthToken() {
		return userAuthToken;
	}
	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}
	public long getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(long syncTime) {
		this.syncTime = syncTime;
	}
}

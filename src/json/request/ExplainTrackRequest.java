package json.request;

public class ExplainTrackRequest extends JSONRequest {

	private String trackToken, userAuthToken;
	private long syncTime;
	
	public String getTrackToken() {
		return trackToken;
	}
	public void setTrackToken(String trackToken) {
		this.trackToken = trackToken;
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

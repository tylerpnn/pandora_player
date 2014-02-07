package json.request;

public class StationListRequest extends JSONRequest {

	private String userAuthToken;
	private long syncTime;
	
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

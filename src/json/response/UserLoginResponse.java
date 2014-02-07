package json.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginResponse extends JSONResponse {
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {
		private String userId, userAuthToken;

		public String getUserAuthToken() {
			return userAuthToken;
		}

		public void setUserAuthToken(String userAuthToken) {
			this.userAuthToken = userAuthToken;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
	
	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result r) {
		result = r;
	}
	
	public String getUserId() {
		return result.getUserId();
	}
	
	public String getUserAuthToken() {
		return result.getUserAuthToken();
	}
}

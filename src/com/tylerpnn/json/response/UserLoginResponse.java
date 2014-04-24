package com.tylerpnn.json.response;

public class UserLoginResponse extends JsonResponse {
	
	public static class Result {
		private String userId, userAuthToken;

		public String getUserAuthToken() {
			return userAuthToken;
		}
		public String getUserId() {
			return userId;
		}
	}
	
	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public String getUserId() {
		return result.getUserId();
	}
	
	public String getUserAuthToken() {
		return result.getUserAuthToken();
	}
}

package com.tylerpnn.json.response;

public class PartnerLoginResponse extends JsonResponse {
	
	public static class Result {
		
		String syncTime, partnerId, partnerAuthToken;

		public String getSyncTime() {
			return syncTime;
		}
		public String getPartnerId() {
			return partnerId;
		}
		public String getPartnerAuthToken() {
			return partnerAuthToken;
		}
	}

	private Result result;

	public Result getResult() {
		return result;
	}

	public String getSyncTime() {
		return result.getSyncTime();
	}
	
	public String getPartnerId() {
		return result.getPartnerId();
	}
	
	public String getPartnerAuthToken() {
		return result.getPartnerAuthToken();
	}
}

package json.response;

public class PartnerLoginResponse extends JSONResponse {
	
	public static class Result {
		private String syncTime, partnerId, partnerAuthToken;

		public String getSyncTime() {
			return syncTime;
		}

		public void setSyncTime(String syncTime) {
			this.syncTime = syncTime;
		}

		public String getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public String getPartnerAuthToken() {
			return partnerAuthToken;
		}

		public void setPartnerAuthToken(String partnerAuthToken) {
			this.partnerAuthToken = partnerAuthToken;
		}

	}

	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
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

package json.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import json.response.StationListResponse.Result.StationInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationListResponse extends JSONResponse {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class StationInfo {
			private String stationName, stationId, stationToken, stationDetailUrl;
			private boolean isQuickMix;
			private String[] genres;
			
			public String getStationName() {
				return stationName;
			}
			public void setStationName(String stationName) {
				this.stationName = stationName;
			}
			public String getStationId() {
				return stationId;
			}
			public void setStationId(String stationId) {
				this.stationId = stationId;
			}
			public String getStationToken() {
				return stationToken;
			}
			public void setStationToken(String stationToken) {
				this.stationToken = stationToken;
			}
			public String getStationDetailUrl() {
				return stationDetailUrl;
			}
			public void setStationDetailUrl(String stationDetailUrl) {
				this.stationDetailUrl = stationDetailUrl;
			}
			public boolean isQuickMix() {
				return isQuickMix;
			}
			public void setQuickMix(boolean isQuickMix) {
				this.isQuickMix = isQuickMix;
			}
			public String[] getGenres() {
				return genres;
			}
			public void setGenres(String[] genres) {
				this.genres = genres;
			}			
		}
		
		private StationInfo[] stations;

		public StationInfo[] getStations() {
			return stations;
		}

		public void setStations(StationInfo[] stations) {
			this.stations = stations;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	public StationInfo[] getStations() {
		return result.getStations();
	}
}

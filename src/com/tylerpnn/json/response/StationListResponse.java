package com.tylerpnn.json.response;

import com.tylerpnn.json.response.StationListResponse.Result.StationInfo;

public class StationListResponse extends JsonResponse {

	public static class Result {
		
		public static class StationInfo {
			
			private String stationName, stationId, stationToken, stationDetailUrl;
			private boolean isQuickMix;
			private String[] genres, quickMixStationIds;
			
			public String getStationName() {
				return stationName;
			}
			public String getStationId() {
				return stationId;
			}
			public String getStationToken() {
				return stationToken;
			}
			public String getStationDetailUrl() {
				return stationDetailUrl;
			}
			public boolean isQuickMix() {
				return isQuickMix;
			}
			public String[] getGenres() {
				return genres;
			}
			public String[] getQuickMixStationIds() {
				return quickMixStationIds;
			}			
		}
		
		private StationInfo[] stations;

		public StationInfo[] getStations() {
			return stations;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}	
	public StationInfo[] getStations() {
		return result.getStations();
	}
}

package com.tylerpnn.pandora.api;

import java.util.HashMap;
import java.util.Map;

import com.tylerpnn.json.request.SetQuickMixRequest;
import com.tylerpnn.json.request.StationListRequest;
import com.tylerpnn.json.response.StationListResponse;
import com.tylerpnn.json.response.StationListResponse.Result.StationInfo;
import com.tylerpnn.pandora.Request;
import com.tylerpnn.pandora.RequestHandler;
import com.tylerpnn.pandora.UserSession;

public class User {

	public static void getStationList(UserSession user) {
		StationListRequest slreq = new StationListRequest();
		slreq.setSyncTime(user.calcSyncTime());
		slreq.setUserAuthToken(user.getUserAuthToken());
		Request req = new Request("user.getStationList", user, slreq, true);
		RequestHandler.sendRequest(req);
		StationListResponse slres = 
				new StationListResponse().loadFromJson(req.getResponse());
		
		Map<String, StationInfo> map = new HashMap<>();
		for(StationInfo s : slres.getStations()) {
			map.put(s.getStationName(), s);
		}
		user.setStations(map);
	}
	
	public static void setQuickMix(UserSession user, StationInfo[] stations) {
		SetQuickMixRequest sqreq = new SetQuickMixRequest();
		String[] stationIds = new String[stations.length];
		for(int i=0; i < stations.length; i++) {
			stationIds[i] = stations[i].getStationId();
		}
		sqreq.setQuickMixStationIds(stationIds);
		sqreq.setUserAuthToken(user.getUserAuthToken());
		sqreq.setSyncTime(user.calcSyncTime());
		
		Request req = new Request("user.setQuickMix", user, sqreq, true);
		RequestHandler.sendRequest(req);		
	}
}

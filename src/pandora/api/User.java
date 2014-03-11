package pandora.api;

import java.util.HashMap;
import java.util.Map;

import json.request.StationListRequest;
import json.response.JsonResponse;
import json.response.StationListResponse;
import json.response.StationListResponse.Result.StationInfo;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

public class User {

	public static void getStationList(UserSession user) {
		StationListRequest slreq = new StationListRequest();
		slreq.setSyncTime(user.calcSyncTime());
		slreq.setUserAuthToken(user.getUserAuthToken());
		Request req = new Request("user.getStationList", user, slreq, true);
		RequestHandler.sendRequest(req);
		StationListResponse slres = JsonResponse.loadFromJson(
				req.getResponse(), StationListResponse.class);
		
		Map<String, StationInfo> map = new HashMap<>();
		for(StationInfo s : slres.getStations()) {
			map.put(s.getStationName(), s);
		}
		user.setStations(map);
	}
}

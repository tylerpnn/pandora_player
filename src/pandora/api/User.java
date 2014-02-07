package pandora.api;

import java.io.IOException;

import json.request.StationListRequest;
import json.response.StationListResponse;
import pandora.ErrorHandler;
import pandora.ErrorHandler.PandoraServerException;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

	public static void getStationList(UserSession user) {
		StationListRequest slreq = new StationListRequest();
		slreq.setSyncTime(user.calcSyncTime());
		slreq.setUserAuthToken(user.getUserAuthToken());
		Request req = new Request("user.getStationList", user, slreq, true);
		RequestHandler.sendRequest(req);
		StationListResponse slres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			slres = mapper.readValue(req.getResponse(), StationListResponse.class);
			if(!slres.getStat().equalsIgnoreCase("ok")) {
				ErrorHandler.errorCheck(slres.getCode());
			}
		} catch(IOException | PandoraServerException e) {
			e.printStackTrace();
		}
		user.setStations(slres.getStations());
	}
}

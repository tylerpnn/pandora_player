package pandora.api;

import java.io.IOException;

import json.request.PlaylistRequest;
import json.response.PlaylistResponse;
import json.response.PlaylistResponse.Result.Song;
import pandora.ErrorHandler;
import pandora.ErrorHandler.PandoraServerException;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Station {

	public static Song[] getPlayList(UserSession user, int stationNumber) {
		json.response.StationListResponse.Result.Station station = user.getStations()[stationNumber];
		
		PlaylistRequest plreq = new PlaylistRequest();
		plreq.setStationToken(station.getStationToken());
		plreq.setSyncTime(user.calcSyncTime());
		plreq.setUserAuthToken(user.getUserAuthToken());
		Request req = new Request("station.getPlaylist", user, plreq, true);
		RequestHandler.sendRequest(req);
		
		PlaylistResponse plres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			plres = mapper.readValue(req.getResponse(), PlaylistResponse.class);
			if(!plres.getStat().equalsIgnoreCase("ok")) {
				ErrorHandler.errorCheck(plres.getCode());
			}
		} catch (IOException | PandoraServerException e) {
			e.printStackTrace();
		}
		return plres.getSongs();
	}
}

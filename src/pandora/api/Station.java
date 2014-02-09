package pandora.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.request.PlaylistRequest;
import json.response.PlaylistResponse;
import json.response.PlaylistResponse.Result.SongInfo;
import json.response.StationListResponse.Result.StationInfo;
import pandora.ErrorHandler;
import pandora.ErrorHandler.PandoraServerException;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Station {

	public static SongInfo[] getPlayList(UserSession user, StationInfo station) {
		
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
		List<SongInfo> songs = new ArrayList<>();
		for(SongInfo song : plres.getSongs()) {
			if(song.getSongIdentity() == null) continue;
			songs.add(song);
		}
		return songs.toArray(new SongInfo[songs.size()]);
	}
}

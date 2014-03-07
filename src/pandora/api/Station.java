package pandora.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.request.FeedbackRequest;
import json.request.PlaylistRequest;
import json.response.FeedbackResponse;
import json.response.PlaylistResponse;
import json.response.PlaylistResponse.Result.SongInfo;
import json.response.StationListResponse.Result.StationInfo;
import pandora.ErrorHandler;
import pandora.ErrorHandler.PandoraServerException;
import pandora.Request;
import pandora.RequestHandler;
import pandora.Song;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Station {

	public static Song[] getPlayList(UserSession user, StationInfo station) {		
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
			ErrorHandler.logJSON(req.getResponse());
			e.printStackTrace();
		}
		List<Song> songs = new ArrayList<>();
		for(SongInfo songInfo : plres.getSongs()) {
			if(songInfo.getSongIdentity() == null) continue;
			Song s = new Song(songInfo);
			s.setStationName(new String(station.getStationName()));
			songs.add(s);
		}
		return songs.toArray(new Song[songs.size()]);
	}
	
	public static void addFeedback(UserSession user, SongInfo song, boolean isPositive) {
		FeedbackRequest freq = new FeedbackRequest();
		freq.setTrackToken(song.getTrackToken());
		freq.setIsPositive(isPositive);
		freq.setStationToken(song.getStationId());
		freq.setUserAuthToken(user.getUserAuthToken());
		freq.setSyncTime(user.calcSyncTime());
		Request req = new Request("station.addFeedback", user, freq, true);
		RequestHandler.sendRequest(req);
		
		FeedbackResponse fres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			fres = mapper.readValue(req.getResponse(), FeedbackResponse.class);
			if(!fres.getStat().equalsIgnoreCase("ok")) {
				ErrorHandler.errorCheck(fres.getCode());
			}
		} catch(IOException | PandoraServerException e) {
			ErrorHandler.logJSON(req.getResponse());
			e.printStackTrace();
		}
	}
}
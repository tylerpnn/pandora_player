package com.tylerpnn.pandora.api;

import java.util.ArrayList;
import java.util.List;

import com.tylerpnn.json.request.FeedbackRequest;
import com.tylerpnn.json.request.PlaylistRequest;
import com.tylerpnn.json.response.PlaylistResponse;
import com.tylerpnn.json.response.PlaylistResponse.Result.SongInfo;
import com.tylerpnn.json.response.StationListResponse.Result.StationInfo;
import com.tylerpnn.pandora.Request;
import com.tylerpnn.pandora.RequestHandler;
import com.tylerpnn.pandora.Song;
import com.tylerpnn.pandora.UserSession;

public class Station {

	public static Song[] getPlayList(UserSession user, StationInfo station) {		
		PlaylistRequest plreq = new PlaylistRequest();
		plreq.setStationToken(station.getStationToken());
		plreq.setSyncTime(user.calcSyncTime());
		plreq.setUserAuthToken(user.getUserAuthToken());
		plreq.setIncludeTrackLength(true);
		Request req = new Request("station.getPlaylist", user, plreq, true);
		RequestHandler.sendRequest(req);
		PlaylistResponse plres = 
				new PlaylistResponse().loadFromJson(req.getResponse());
		List<Song> songs = new ArrayList<>();
		for(SongInfo songInfo : plres.getSongs()) {
			if(songInfo.getSongIdentity() == null) continue;
			songs.add(new Song(songInfo));
		}
		return songs.toArray(new Song[songs.size()]);
	}
	
	public static void addFeedback(UserSession user, Song song, boolean isPositive) {
		FeedbackRequest freq = new FeedbackRequest();
		freq.setTrackToken(song.getSongInfo().getTrackToken());
		freq.setIsPositive(isPositive);
		freq.setStationToken(song.getSongInfo().getStationId());
		freq.setUserAuthToken(user.getUserAuthToken());
		freq.setSyncTime(user.calcSyncTime());
		Request req = new Request("station.addFeedback", user, freq, true);
		RequestHandler.sendRequest(req);
		song.setSongRating(isPositive ? 1 : -1);
	}
}
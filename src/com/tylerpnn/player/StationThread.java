package com.tylerpnn.player;

import com.tylerpnn.json.response.StationListResponse.Result.StationInfo;
import com.tylerpnn.pandora.Application;
import com.tylerpnn.pandora.Song;
import com.tylerpnn.pandora.UserSession;
import com.tylerpnn.pandora.api.Station;



public class StationThread extends Thread {
	
	private Application app;
	private UserSession user;
	private StationInfo station;
	private boolean halt;
	
	private Song[] playlist;
	private Song currentSong;
	private int index;
	
	public StationThread(Application app, UserSession user, StationInfo station) {
		this.app = app;
		this.user = user;
		this.station = station;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while(!halt) {
			currentSong = getNextSong();
			if(currentSong == null) continue;
			currentSong.setPlaying(true);
			app.displaySong(currentSong);
			Player.decodeMp4(currentSong);
		}
	}
	
	private Song getNextSong() {
		if(playlist == null || index >= playlist.length) {
			playlist = Station.getPlayList(user, station);
			index = 0;
		}
		return playlist[index++];
	}
	
	public void halt() {
		this.halt = true;
		Player.stopPlaying();
	}
}

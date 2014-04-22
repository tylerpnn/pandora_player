package com.tylerpnn.pandora;

import java.util.Set;

import javax.swing.SwingUtilities;

import com.tylerpnn.pandora.api.Auth;
import com.tylerpnn.pandora.api.Station;
import com.tylerpnn.pandora.api.Track;
import com.tylerpnn.pandora.api.User;
import com.tylerpnn.player.Player;
import com.tylerpnn.ui.cli.CLI;

public class Application {
	
	private UserSession user;
	private UserInterface ui;
	private Player player;
	

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Application(args);
			}
		});
	}
	
	public Application(String[] args) {

		ui = new CLI(this);
		ui.start();
	}
	
	public static void exit() {
		System.exit(0);
	}

	public boolean login(String email, char[] pw, String proxy) {
		setProxy(proxy);
		user = new UserSession();
		Auth.partnerLogin(user);
		Auth.userLogin(user, email, pw);
		User.getStationList(user);
		player = new Player(this, user);
		if(user.getUserAuthToken() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void logout() {
		this.user = null;
		player.stop();
		player = null;
	}
	
	public void setProxy(String proxy) {
		if(proxy != null) {
			String[] params = proxy.split(":");
			if(params.length == 2) {
				System.setProperty("http.proxyHost", params[0]);
				System.setProperty("http.proxyPort", params[1]);
			}
		}
	}
	
	public void playStation(String stationName) {
		player.playStation(user.getStationInfoByName(stationName));
	}
	
	public void skipSong() {
		if(player != null)
			player.skip();
	}
	
	public void playToggle() {
		if(player != null)
			player.playToggle();
	}
	
	public void displaySong(Song song) {
		ui.displaySong(song);
	}
	
	public void setFeedback(Song song, int feedback) {
		Station.addFeedback(user, song.getSongInfo(), (feedback > 0));
		if(feedback < 0 && song.isPlaying())
			skipSong();
	}
	
	public String getExplanation(Song song) {
		return Track.explainTrack(user, song.getSongInfo());
	}
	
	public String getStationName(String stationId) {
		return user.getStationInfoById(stationId).getStationName();
	}
	
	public String[]	getStationList() {
		Set<String> stations = null;
		if(user != null && user.getStations() != null) {
			stations = user.getStations().keySet();
			return stations.toArray(new String[stations.size()]);
		} else {
			return new String[0];
		}
	}
}
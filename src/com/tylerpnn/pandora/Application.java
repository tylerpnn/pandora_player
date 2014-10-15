package com.tylerpnn.pandora;

import javafx.application.Platform;

import javax.swing.SwingUtilities;

import com.tylerpnn.json.response.StationListResponse.Result.StationInfo;
import com.tylerpnn.pandora.api.Auth;
import com.tylerpnn.pandora.api.Station;
import com.tylerpnn.pandora.api.Track;
import com.tylerpnn.pandora.api.User;
import com.tylerpnn.player.Player;
import com.tylerpnn.player.StationThread;
import com.tylerpnn.ui.cli.CLI;
import com.tylerpnn.ui.fx.Window;

public class Application {

	private UserSession user;
	private UserInterface ui;
	private StationThread stationThread;	


	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> new Application(args));
//		Platform.runLater(() -> new Application(args));
	}

	public Application(String[] args) {
		if(args.length > 0 && args[0].equals("-nogui")) {
			ui = new CLI(this);
		} else {
			ui = new Window(this);
		}
		Platform.runLater(() -> ui.start());
	}

	public static void exit() {
		System.exit(0);
	}

	public boolean login(String email, char[] pw, String proxy) {
		setProxy(proxy);
		user = new UserSession();
		Auth.partnerLogin(user);
		Auth.userLogin(user, email, pw);
		if(user.getUserAuthToken() == null) return false;
		User.getStationList(user);
		return true;
	}

	public void logout() {
		this.user = null;
		stopPlayer();
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
		this.stopPlayer();
		this.stationThread = new StationThread(this, user, user.getStationInfoByName(stationName));
		this.stationThread.start();
	}
	
	public synchronized void stopPlayer() {
		if(stationThread != null) {
			stationThread.halt();
			try {
				stationThread.join();
			} catch (InterruptedException e) {}
			stationThread = null;
		}
	}
	
	public void displaySong(Song song) {
		ui.displaySong(song);
	}

	public void setFeedback(Song song, int feedback) {
		Station.addFeedback(user, song, (feedback > 0));
		if(feedback < 0 && song.isPlaying())
			Player.skip();
	}

	public String getExplanation(Song song) {
		return Track.explainTrack(user, song.getSongInfo());
	}

	public String getStationName(String stationId) {
		return user.getStationInfoById(stationId).getStationName();
	}

	public StationInfo[] getStationList() {
		return user.getStations().values().toArray(new StationInfo[0]);
	}
}
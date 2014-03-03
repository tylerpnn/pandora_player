package ui;

import java.util.Set;

import javax.swing.SwingUtilities;

import pandora.Song;
import pandora.UserSession;
import pandora.api.Auth;
import pandora.api.Station;
import pandora.api.Track;
import pandora.api.User;
import player.Player;


public class Application {
	
	private UserSession user;
	private Player player;
	private static Frame gui;
	
	public static boolean muteAds = true;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Application();
			}
		});
	}
	
	public Application() {
		gui = new Frame(this);
	}
	
	public boolean login(String username, char[] password) {
		this.user = new UserSession(username, String.valueOf(password));
		Auth.partnerLogin(user);
		Auth.userLogin(user);
		User.getStationList(user);
		player = new Player(this, user);
		return true;
	}
	
	public void logout() {
		this.user = null;
		player.stop();
		player = null;
	}
	
	public void playStation(String stationName) {
		player.playStation(user.getStationInfo(stationName));
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
		gui.displaySong(song);
	}
	
	public void setFeedback(Song song, int feedback) {
		Station.addFeedback(user, song.getSongInfo(), (feedback > 0));
		if(feedback < 0 && song.isPlaying())
			skipSong();
	}
	
	public String getExplanation(Song song) {
		String res = "This track was selected because it features ";
		String[] traits = Track.explainTrack(user, song.getSongInfo());
		for(int i=0; i<traits.length-1; i++) {
			res += traits[i] + ", ";
			if(i % 2 == 0)
				res += "\n";
		}
		return res.substring(0, res.length()-3) + ".";
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
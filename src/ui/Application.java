package ui;

import java.util.Set;

import javax.swing.SwingUtilities;

import pandora.Configuration;
import pandora.Configuration.UserInfo;
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
	private Frame gui;
	
	private static Configuration config;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Application();
			}
		});
	}
	
	public Application() {
		config = Configuration.loadConfig();
		gui = new Frame(this);
	}
	
	public static void exit() {
		Configuration.writeConfig(config);
		System.exit(0);
	}
	
	public static Configuration getConfig() {
		return config;
	}

	public boolean login(UserInfo u) {
		user = new UserSession(u.getUsername(), 
				String.valueOf(u.getPassword()));
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
		config.setRememberUser(false);
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
		gui.displaySong(song);
	}
	
	public void setFeedback(Song song, int feedback) {
		Station.addFeedback(user, song.getSongInfo(), (feedback > 0));
		if(feedback < 0 && song.isPlaying())
			skipSong();
	}
	
	public String getExplanation(Song song) {
		String[] traits = Track.explainTrack(user, song.getSongInfo());
		String[] format = new String[traits.length];
		for(int i=0;i < format.length-1; i++) 
			format[i] = "%s, " + ((i%2==0) ? "\n" : "");
		format[format.length-1] = "and %s.";
		StringBuilder fmt = new StringBuilder("This track was selected because it features ");
		for(String s : format) fmt.append(s);
		return String.format(fmt.toString(), (Object[])traits);
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
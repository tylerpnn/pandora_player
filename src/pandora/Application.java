package pandora;

import java.util.Set;

import javax.swing.SwingUtilities;

import pandora.api.Auth;
import pandora.api.Station;
import pandora.api.Track;
import pandora.api.User;
import player.Player;
import ui.cli.CLI;
import ui.gui.Configuration;
import ui.gui.Frame;


public class Application {
	
	private UserSession user;
	private UserInterface ui;
	private Player player;
	
	private static Configuration config;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Application(args);
			}
		});
	}
	
	public Application(final String[] args) {
		config = Configuration.loadConfig();
		if(args.length > 0 && args[0].equals("-nogui")) {
			ui = new CLI(this);
		} else {
			ui = new Frame(this);
		}
	}
	
	public static void exit() {
		Configuration.writeConfig(config);
		System.exit(0);
	}
	
	public static Configuration getConfig() {
		return config;
	}

	public boolean login(UserInfo uInfo) {
		user = new UserSession();
		Auth.partnerLogin(user);
		Auth.userLogin(user, uInfo);
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
		ui.displaySong(song);
	}
	
	public void setFeedback(Song song, int feedback) {
		Station.addFeedback(user, song.getSongInfo(), (feedback > 0));
		if(feedback < 0 && song.isPlaying())
			skipSong();
	}
	
	public String getExplanation(Song song) {
		String[] traits = Track.explainTrack(user, song.getSongInfo());
		String[] format = new String[traits.length];
		for(int i=0; i < format.length-1; i++) 
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
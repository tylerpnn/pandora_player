package pandora;

import java.util.Set;

import javax.swing.SwingUtilities;

import pandora.api.Auth;
import pandora.api.User;
import player.Player;
import ui.Frame;


public class Application {
	
	private UserSession user;
	private Player player;
	private static Frame gui;

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
	
	public void displaySongs(Song[] playlist) {
		gui.displaySongs(playlist);
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
package pandora;

import java.awt.Font;
import java.util.Set;

import json.response.PlaylistResponse.Result.SongInfo;
import json.response.StationListResponse.Result.StationInfo;
import pandora.api.Auth;
import pandora.api.Station;
import pandora.api.User;
import player.Player;
import ui.Frame;


public class Application {
	
	private UserSession user;
	private Player player;
	private static Frame gui;

	public static void main(String[] args) {
		new Application();
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
	
	public void displaySongs(SongInfo[] playlist) {
		gui.displaySongs(playlist);
	}
	
	public String[]	getStationList() {
		Set<String> stations = null;
		if(user != null) {
			stations = user.getStations().keySet();
			return stations.toArray(new String[stations.size()]);
		} else {
			return new String[0];
		}
	}
}











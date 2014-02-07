package pandora;

import json.response.PlaylistResponse.Result.Song;
import pandora.api.Auth;
import pandora.api.Station;
import pandora.api.User;
import player.Player;


public class Application {
	
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";	

	public static void main(String[] args) {
		UserSession user = new UserSession(USERNAME, PASSWORD);
		Auth.partnerLogin(user);
		Auth.userLogin(user);
		User.getStationList(user);
		Song[] songs = Station.getPlayList(user, 0);
		for(Song song : songs) {
			if(song.getSongIdentity() == null) continue;
			System.out.printf("Song: %s | Artist: %s | Album: %s\n", song.getSongName(), song.getArtistName(), song.getAlbumName());
			System.out.println("url: " + song.getAudioUrlMap().getHighQuality().getAudioUrl() + "\n");
		}
		Player player = new Player();
		try {
			player.playSong(songs[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void nextSong() {
		
	}
}











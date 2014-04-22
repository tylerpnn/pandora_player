package com.tylerpnn.ui.cli;

import java.util.Arrays;

import com.tylerpnn.pandora.Application;
import com.tylerpnn.pandora.Song;
import com.tylerpnn.pandora.UserInterface;
import com.tylerpnn.player.Player;

public class CLI implements UserInterface {

	private Application app;
	private Console c;
	private Song currentSong;
	private Terminal term;
	
	private boolean pause;
	private String station;
	private float vol = .7f;
	
	public CLI(Application app) {
		if(!System.getProperty("os.name").equals("Linux")) {
			System.out.println("Console mode only supports Linux");
			System.exit(0);
		}
		this.app = app;
		c = new Console();
		term = new Terminal(this);
		pause = true;
	}
	
	@Override
	public void start() {
		login();
		new Thread(new Runnable() {
			@Override
			public void run() {
				term.listen();
			}
		}).start();
		
		while(true) {
			System.out.print("");
			if(!pause && currentSong != null && currentSong.isPlaying()) {
				c.printf("\r-> %d:%02d / %d:%02d",
				         (int)currentSong.getTime() / 60,
				         (int)currentSong.getTime() % 60,
				         (int)currentSong.getDuration() / 60,
				         (int)currentSong.getDuration() % 60);				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void login() {
		String email = c.readLine("%s", "Email: ");
		char[] pw = c.readPassword("%s", "Password: ");
		c.print("Login... ");
		if(app.login(email, pw, null)) {
			c.println("Ok");
		} else {
			c.println("Fail");
			Application.exit();
		}
		Arrays.fill(pw, ' ');
		pickStation();
	}
	
	public void pickStation() {
		pause = true;
		term.enable();
		String[] stations = app.getStationList();
		c.println("");
		for(int i=0; i<stations.length; i++) {
			c.printf("%d) %s%n", i, stations[i]);
		}
		int num = Integer.parseInt(c.readLine("%s", "Choose Station: "));
		c.println("\r");
		if(num < 0 || num > stations.length-1) {
			pickStation();
			return;
		}
		station = stations[num];
		app.playStation(station);
		pause = false;
		term.disable();
	}

	@Override
	public void displaySong(Song song) {
		this.currentSong = song;
		int rating = song.getSongInfo().getSongRating();		
		c.printf("\r>%s \"%s\" by %s on %s %s%n",
				(rating > 0) ? "+" : "",
				song.getSongInfo().getSongName(),
				song.getSongInfo().getArtistName(),
				song.getSongInfo().getAlbumName(),
				(station.equals("QuickMix")) ? 
						"@"+app.getStationName(
								song.getSongInfo().getStationId()) : "");
	}
	
	public void modVolume(float dV) {
		vol = Player.setVolume(vol + dV);
	}
	
	public void printHelp() {
		pause=true;
		c.println("\n\nHelp:\n" +
					"?\t Display this help menu\n" +
					"p\t Play/Pause\n" +
					"n\t Next song\n" + 
					"+\t Thumbs up\n" +
					"-\t Thumbs down\n" +
					"[\t Volume down\n" +
					"]\t Volume up\n");
		displaySong(currentSong);
		pause=false;
	}
	
	public void handleKey(char ch) {
		if(pause) return;
		switch(ch) {
		case 'n':
			app.skipSong();
			break;
		case 'p':
			app.playToggle();
			break;
		case 's':
			pickStation();
			break;
		case '+':
			app.setFeedback(currentSong, 1);
			break;
		case '-':
			app.setFeedback(currentSong, -1);
			break;
		case ']':
			modVolume(.1f);
			break;
		case '[':
			modVolume(-.1f);
			break;
		case '?':
			printHelp();
			break;
		default:
			break;
		}
	}
}

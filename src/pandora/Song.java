package pandora;

import json.response.PlaylistResponse.Result.SongInfo;
import ui.SongDisplay;

public class Song {

	private SongInfo songInfo;
	private boolean playing;
	private int duration;
	private int time;
	private SongDisplay display;
	private boolean isAd;
	
	public Song(SongInfo songInfo) {
		this.songInfo = songInfo;
	}
	
	public SongInfo getSongInfo() {
		return this.songInfo;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int t) {
		this.time = t;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(int t) {
		this.duration = t;
		this.setIsAd(t <= 30);
	}
	
	public void update(int time) {
		this.time = time;
		if(display != null)
			display.update();
	}
	
	public boolean isPlaying() {
		return this.playing;
	}
	
	public void setPlaying(boolean b) {
		this.playing = b;
		if(display != null)
			display.setPlaying(b);
	}
	
	public void setIsAd(boolean b) {
		this.isAd = b;
	}
	
	public boolean isAd() {
		return this.isAd;
	}
	
	public void setDisplay(SongDisplay display) {
		this.display = display;
	}
	
	public SongDisplay getDisplay() {
		return this.display;
	}
}

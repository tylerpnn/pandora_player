package pandora;

import json.response.PlaylistResponse.Result.SongInfo;

public class Song {

	private SongInfo songInfo;
	private boolean playing;
	private int duration;
	private int time;
	private Display display;
	private boolean isAd;
	private boolean isQuickMix;
	
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
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	public Display getDisplay() {
		return this.display;
	}
	
	public interface Display {
		
		public void update();
		public void setPlaying(boolean b);
	}
}

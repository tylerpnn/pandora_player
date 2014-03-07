package pandora;

import json.response.PlaylistResponse.Result.SongInfo;

public class Song {

	private SongInfo songInfo;
	private boolean playing;
	private double duration;
	private double time;
	private Display display;
	private boolean isAd;
	private String stationName;
	
	public Song(SongInfo songInfo) {
		this.songInfo = songInfo;
	}
	
	public SongInfo getSongInfo() {
		return this.songInfo;
	}
	
	public double getTime() {
		return this.time;
	}
	
	public void setTime(double t) {
		this.time = t;
	}
	
	public double getDuration() {
		return this.duration;
	}
	
	public void setDuration(double t) {
		this.duration = t;
		this.setIsAd(t <= 30f);
	}
	
	public void update(double time) {
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public interface Display {
		
		public void update();
		public void setPlaying(boolean b);
	}
}

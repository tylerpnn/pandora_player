package com.tylerpnn.pandora;

import com.tylerpnn.json.response.PlaylistResponse.Result.SongInfo;

public class Song {

	private SongInfo songInfo;
	private boolean playing;
	private double duration;
	private double time;
	private boolean isAd;
	private int songRating;
	
	public Song(SongInfo songInfo) {
		this.songInfo = songInfo;
		this.songRating = songInfo.getSongRating();
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
	
	public boolean isPlaying() {
		return this.playing;
	}
	
	public void setPlaying(boolean b) {
		this.playing = b;
	}
	
	public void setIsAd(boolean b) {
		this.isAd = b;
	}
	
	public boolean isAd() {
		return this.isAd;
	}

	public int getSongRating() {
		return songRating;
	}

	public void setSongRating(int songRating) {
		this.songRating = songRating;
	}
}

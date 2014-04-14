package com.tylerpnn.json.response;

public class FeedbackResponse extends JsonResponse {

	private String songName, artistName;
	private int feedbackId, isPositive;
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public int getIsPositive() {
		return isPositive;
	}
	public void setIsPositive(int isPositive) {
		this.isPositive = isPositive;
	}
}

package com.tylerpnn.json.response;

public class FeedbackResponse extends JsonResponse {

	private String songName, artistName;
	private int feedbackId, isPositive;
	
	public String getSongName() {
		return songName;
	}
	public String getArtistName() {
		return artistName;
	}
	public int getFeedbackId() {
		return feedbackId;
	}
	public int getIsPositive() {
		return isPositive;
	}
}

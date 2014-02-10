package ui;

import json.response.PlaylistResponse.Result.SongInfo;

public class Song {

	private String songIdentity, trackToken, 
		artistName, albumName, albumArtUrl, artistDetailUrl,
		albumIdentity, songName, albumDetailUrl, songDetailUrl;
	
	public Song(SongInfo song) {
		this.songIdentity = song.getSongIdentity();
		this.trackToken = song.getTrackToken();
		this.artistName = song.getArtistName();
		this.albumName = song.getAlbumName();
		this.albumArtUrl = song.getAlbumArtUrl();
		this.artistDetailUrl = song.getArtistDetailUrl();
		this.albumIdentity = song.getAlbumIdentity();
		this.songName = song.getSongName();
		this.albumDetailUrl = song.getAlbumDetailUrl();
		this.songDetailUrl = song.getSongDetailUrl();
	}
}

package json.response;

import java.util.HashMap;
import java.util.Map;

import json.response.PlaylistResponse.Result.SongInfo;

public class PlaylistResponse extends JSONResponse {

	public static class Result {
		
		public static class SongInfo {
			
			public static class AudioUrlMap {
				
				public static class AudioUrl {
					private String bitrate, encoding, audioUrl, protocol;

					public String getBitrate() {
						return bitrate;
					}

					public void setBitrate(String bitrate) {
						this.bitrate = bitrate;
					}

					public String getEncoding() {
						return encoding;
					}

					public void setEncoding(String encoding) {
						this.encoding = encoding;
					}

					public String getAudioUrl() {
						return audioUrl;
					}

					public void setAudioUrl(String audioUrl) {
						this.audioUrl = audioUrl;
					}

					public String getProtocol() {
						return protocol;
					}

					public void setProtocol(String protocol) {
						this.protocol = protocol;
					}					
				}
				
				private AudioUrl highQuality, mediumQuality, lowQuality;

				public AudioUrl getHighQuality() {
					return highQuality;
				}

				public void setHighQuality(AudioUrl highQuality) {
					this.highQuality = highQuality;
				}

				public AudioUrl getMediumQuality() {
					return mediumQuality;
				}

				public void setMediumQuality(AudioUrl mediumQuality) {
					this.mediumQuality = mediumQuality;
				}

				public AudioUrl getLowQuality() {
					return lowQuality;
				}

				public void setLowQuality(AudioUrl lowQuality) {
					this.lowQuality = lowQuality;
				}
				
				public Map<String, AudioUrl> map() {
					Map<String, AudioUrl> map = new HashMap<>();
					map.put("high", highQuality);
					map.put("medium", mediumQuality);
					map.put("low", lowQuality);
					return map;
				}
				
			}
			
			private String songIdentity, trackToken, stationId, 
				artistName, albumName, albumArtUrl, artistDetailUrl,
				albumIdentity, songName, albumDetailUrl, songDetailUrl;
			
			private AudioUrlMap audioUrlMap;
			private int songRating;

			public String getSongIdentity() {
				return songIdentity;
			}

			public void setSongIdentity(String songIdentity) {
				this.songIdentity = songIdentity;
			}

			public String getTrackToken() {
				return trackToken;
			}

			public void setTrackToken(String trackToken) {
				this.trackToken = trackToken;
			}

			public String getArtistName() {
				return artistName;
			}

			public void setArtistName(String artistName) {
				this.artistName = artistName;
			}

			public String getAlbumName() {
				return albumName;
			}

			public void setAlbumName(String albumName) {
				this.albumName = albumName;
			}

			public String getAlbumArtUrl() {
				return albumArtUrl;
			}

			public void setAlbumArtUrl(String albumArtUrl) {
				this.albumArtUrl = albumArtUrl;
			}

			public String getArtistDetailUrl() {
				return artistDetailUrl;
			}

			public void setArtistDetailUrl(String artistDetailUrl) {
				this.artistDetailUrl = artistDetailUrl;
			}

			public String getAlbumIdentity() {
				return albumIdentity;
			}

			public void setAlbumIdentity(String albumIdentity) {
				this.albumIdentity = albumIdentity;
			}

			public String getSongName() {
				return songName;
			}

			public void setSongName(String songName) {
				this.songName = songName;
			}

			public String getAlbumDetailUrl() {
				return albumDetailUrl;
			}

			public void setAlbumDetailUrl(String albumDetailUrl) {
				this.albumDetailUrl = albumDetailUrl;
			}

			public String getSongDetailUrl() {
				return songDetailUrl;
			}

			public void setSongDetailUrl(String songDetailUrl) {
				this.songDetailUrl = songDetailUrl;
			}

			public AudioUrlMap getAudioUrlMap() {
				return audioUrlMap;
			}

			public void setAudioUrlMap(AudioUrlMap audioUrlMap) {
				this.audioUrlMap = audioUrlMap;
			}

			public int getSongRating() {
				return songRating;
			}

			public void setSongRating(int songRating) {
				this.songRating = songRating;
			}

			public String getStationId() {
				return stationId;
			}

			public void setStationId(String stationId) {
				this.stationId = stationId;
			}
		}
		
		private SongInfo[] items;

		public SongInfo[] getItems() {
			return items;
		}

		public void setItems(SongInfo[] items) {
			this.items = items;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	public SongInfo[] getSongs() {
		return result.getItems();
	}
}

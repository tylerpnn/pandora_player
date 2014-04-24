package com.tylerpnn.json.response;

import java.util.HashMap;
import java.util.Map;

import com.tylerpnn.json.response.PlaylistResponse.Result.SongInfo;

public class PlaylistResponse extends JsonResponse {

	public static class Result {
		
		public static class SongInfo {
			
			public static class AudioUrlMap {
				
				public static class AudioUrl {
					private String bitrate, encoding, audioUrl, protocol;

					public String getBitrate() {
						return bitrate;
					}
					public String getEncoding() {
						return encoding;
					}
					public String getAudioUrl() {
						return audioUrl;
					}
					public String getProtocol() {
						return protocol;
					}					
				}
				
				private AudioUrl highQuality, mediumQuality, lowQuality;

				public AudioUrl getHighQuality() {
					return highQuality;
				}
				public AudioUrl getMediumQuality() {
					return mediumQuality;
				}
				public AudioUrl getLowQuality() {
					return lowQuality;
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
			private int songRating, trackLength;

			public String getSongIdentity() {
				return songIdentity;
			}
			public String getTrackToken() {
				return trackToken;
			}
			public String getArtistName() {
				return artistName;
			}
			public String getAlbumName() {
				return albumName;
			}
			public String getAlbumArtUrl() {
				return albumArtUrl;
			}
			public String getArtistDetailUrl() {
				return artistDetailUrl;
			}
			public String getAlbumIdentity() {
				return albumIdentity;
			}
			public String getSongName() {
				return songName;
			}
			public String getAlbumDetailUrl() {
				return albumDetailUrl;
			}
			public String getSongDetailUrl() {
				return songDetailUrl;
			}
			public AudioUrlMap getAudioUrlMap() {
				return audioUrlMap;
			}
			public int getSongRating() {
				return songRating;
			}
			public String getStationId() {
				return stationId;
			}
			public int getTrackLength() {
				return this.trackLength;
			}
		}
		
		private SongInfo[] items;

		public SongInfo[] getItems() {
			return items;
		}
	}
	
	private Result result;

	public Result getResult() {
		return result;
	}
	public SongInfo[] getSongs() {
		return result.getItems();
	}
}
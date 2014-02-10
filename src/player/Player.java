package player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import pandora.Application;
import pandora.UserSession;
import pandora.api.Station;

import json.response.PlaylistResponse.Result.SongInfo;
import json.response.StationListResponse.Result.StationInfo;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.Profile;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import net.sourceforge.jaad.mp4.api.Track;


public class Player {

	private UserSession user;
	private Application app;
	
	private PlayerThread playerThread;
	
	private StationInfo station;
	private SongInfo[] playlist;
	private int index;
	
	public Player(Application app, UserSession user) {
		this.app = app;
		this.user = user;
	}
	
	public void playStation(StationInfo station) {
		this.station = station;
		playlist = Station.getPlayList(user, station);
		app.displaySongs(playlist);
		index = 0;
		if(playerThread != null) {
			playerThread.interrupt();
			playerThread.stop();
			playerThread = null;
		}
		playerThread = new PlayerThread();
		playerThread.start();
	}
	
	public void skip() {
		playerThread.skip();
	}
	
	public void play() {
		if(playerThread.isPaused())
			playerThread.play();
	}
	
	public void pause() {
		if(!playerThread.isPaused())
			playerThread.pause();
	}
	
	public void playToggle() {
		if(playerThread.isPaused()) {
			playerThread.play();
		} else {
			playerThread.pause();
		}
	}
	
	private class PlayerThread extends Thread {
		
		private boolean playing = false;
		private boolean paused = false;
		private boolean skip = false;
		private byte[] currSong;
		
		public PlayerThread() {
			this.setDaemon(true);
		}
		
		public void run() {
			this.currSong = getNextSong();
			while(currSong != null && decodeMp4(currSong)) {
				currSong = getNextSong();
			}
		}
		
		private byte[] getNextSong() {
			if(index >= playlist.length) {
				playlist = Station.getPlayList(user, station);
				app.displaySongs(playlist);
				index = 0;
			}
			return getSongData(playlist[index++]);
		}
			
		private byte[] getSongData(SongInfo song) {
			System.out.println(song.getAudioUrlMap().getHighQuality().getAudioUrl());
			URL url;
			URLConnection con;
			DataInputStream dis;
			byte[] data = null;
			try {
				url = new URL(song.getAudioUrlMap().getHighQuality().getAudioUrl());
				con = url.openConnection();
				dis = new DataInputStream(con.getInputStream());
				data = new byte[con.getContentLength()];
				for(int i=0; i < data.length; i++) {
					data[i] = dis.readByte();
				}
				dis.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return data;
		}
		
		public void skip() {
			this.skip = true;
		}
		
		public boolean isPlaying() {
			return this.playing;
		}
		
		public boolean isPaused() {
			return this.paused;
		}
		
		public void pause() {
			this.paused = true;
		}
		
		public void play() {
			this.paused = false;
		}
		
		private boolean decodeMp4(byte[] songData) {
			playing = true;
			SourceDataLine dataLine = null;
			try {
				MP4Container cont = new MP4Container(new ByteArrayInputStream(songData));
				Movie movie = cont.getMovie();
				List<Track> tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
				AudioTrack track = (AudioTrack) tracks.get(0);
				AudioFormat audioFormat = new AudioFormat(track.getSampleRate()/2, track.getSampleSize(), track.getChannelCount(), true, true);
				dataLine = AudioSystem.getSourceDataLine(audioFormat);
				dataLine.open(audioFormat);
				dataLine.start();
				
				Decoder dec = new Decoder(track.getDecoderSpecificInfo());
				dec.getConfig().setProfile(Profile.AAC_LC);
				dec.getConfig().setSBREnabled(false);
				Frame frame;
				byte[] chunk;
				SampleBuffer buf = new SampleBuffer();
				while(!skip && track.hasMoreFrames()) {
					while(!skip && paused) { sleep(100L); }
					frame = track.readNextFrame();
					dec.decodeFrame(frame.getData(), buf);
					chunk = buf.getData();
					dataLine.write(chunk, 0, chunk.length);
				}
				skip = false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if(dataLine != null) {
					dataLine.stop();
					dataLine.close();
				}				
			}
			playing = false;
			return true;
		}
	}	
}









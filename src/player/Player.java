package player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import json.response.StationListResponse.Result.StationInfo;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.Profile;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import net.sourceforge.jaad.mp4.api.Track;
import pandora.Application;
import pandora.Song;
import pandora.UserSession;
import pandora.api.Station;


public class Player {

	private UserSession user;
	private Application app;
	public enum PlayerState { PLAYING, PAUSED, WAITING }
	
	private PlayerThread playerThread;
	public static PlayerState status;
	
	public Player(Application app, UserSession user) {
		this.app = app;
		this.user = user;
		status = PlayerState.WAITING;
	}
	
	public void playStation(StationInfo station) {
		if(playerThread != null) {
			playerThread.interrupt();
			playerThread.stop();
			playerThread = null;
		}
		playerThread = new PlayerThread(station);
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
		
		private boolean paused = false;
		private boolean skip = false;
		private StationInfo station;
		private Song[] playlist;
		private Song currSong;
		private int index;
		
		public PlayerThread(StationInfo station) {
			this.setDaemon(true);
			this.station = station;
			index = 0;
		}
		
		public void run() {
			this.currSong = getNextSong();
			while(currSong != null && decodeMp4(currSong)) {
				currSong = getNextSong();
			}
		}
		
		private Song getNextSong() {
			if(playlist == null || index >= playlist.length) {
				playlist = Station.getPlayList(user, station);
				app.displaySongs(playlist);
				index = 0;
			}
			return playlist[index++];
		}
			
		private byte[] getSongData(Song song) {
			System.out.println(song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl());
			URL url;
			URLConnection con;
			DataInputStream dis;
			byte[] data = null;
			try {
				url = new URL(song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl());
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
		
		public boolean isPaused() {
			return this.paused;
		}
		
		public void pause() {
			this.paused = true;
			status = PlayerState.PAUSED;
		}
		
		public void play() {
			this.paused = false;
			status = PlayerState.PLAYING;
		}
		
		private boolean decodeMp4(Song song) {
			song.setPlaying(true);
			byte[] songData = getSongData(song);
			SourceDataLine dataLine = null;
			try {
				MP4Container cont = new MP4Container(new ByteArrayInputStream(songData));
				Movie movie = cont.getMovie();
				song.setDuration((int)movie.getDuration());
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
					song.update((int)frame.getTime());
					dec.decodeFrame(frame.getData(), buf);
					chunk = buf.getData();
					dataLine.write(chunk, 0, chunk.length);
				}
				song.setPlaying(false);
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
			return true;
		}
	}	
}









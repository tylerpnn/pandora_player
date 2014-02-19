package player;

import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import json.response.StationListResponse.Result.StationInfo;
import net.sourceforge.jaad.spi.javasound.AACAudioFileReader;
import pandora.Application;
import pandora.Song;
import pandora.UserSession;
import pandora.api.Station;


public class Player {

	private UserSession user;
	private Application app;
	public enum PlayerState { PLAYING, PAUSED, WAITING }
	
	private PlayerThread playerThread;
	private static PlayerState status;
	private static SourceDataLine dataLine;
	private static float volume = 1f;
	
	public Player(Application app, UserSession user) {
		this.app = app;
		this.user = user;
		status = PlayerState.WAITING;
	}
	
	@SuppressWarnings("deprecation")
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
	
	public static PlayerState getStatus() {
		return status;
	}
	
	public static void setVolume(float level) {
		volume = level;
		if(dataLine != null) {
			FloatControl fc = null;
			if(dataLine.isControlSupported(FloatControl.Type.VOLUME)) {
				fc = (FloatControl) dataLine.getControl(FloatControl.Type.VOLUME);
				fc.setValue(level * fc.getMaximum());
			} else if(dataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				fc = (FloatControl) dataLine.getControl(FloatControl.Type.MASTER_GAIN);
				float gain = (float) ((level != 0) ? 10 * Math.log10(level) : -20f);
				fc.setValue(gain * Math.abs(fc.getMinimum() / 20f));
			}
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
				System.out.println(con.getContentLength());
				for(int i=0; i < data.length; i++) {
					data[i] = dis.readByte();
					System.out.printf("i: %d, b: %d\n", i, data[i]);
				}
				System.out.println("hi");
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
			dataLine = null;
			try {
				String url = song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl();
				System.out.print(song.getSongInfo().getSongName() + " ");
				System.out.println(url);
				AACAudioFileReader aafr = new AACAudioFileReader();
				AudioInputStream ais = aafr.getAudioInputStream(new URL(url));
				System.out.printf("%d\n", ais.getFrameLength());
				dataLine = AudioSystem.getSourceDataLine(ais.getFormat());
				dataLine.open();
				dataLine.start();
				byte[] buf = new byte[4096];
				int bytesRead = 0;
				while(!skip && bytesRead != -1) {
					while(!skip && paused) { sleep(100L); }
					bytesRead = ais.read(buf, 0, buf.length);
					if(bytesRead != -1)
						dataLine.write(buf, 0, buf.length);
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









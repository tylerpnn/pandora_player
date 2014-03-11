package player;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import json.response.StationListResponse.Result.StationInfo;
import net.sourceforge.jaad.aac.Decoder;
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
	private static PlayerState status;
	private static SourceDataLine dataLine;
	private static float volume = .75f;

	private Thread playerThread;
	private boolean paused = false;
	private boolean skip = false;
	private boolean stop = false;
	private StationInfo station;
	private Song[] playlist;
	private Song currSong;
	private int index;
	
	public Player(Application app, UserSession user) {
		this.app = app;
		this.user = user;
		status = PlayerState.WAITING;
	}
	
	public static PlayerState getStatus() {
		return status;
	}
	
	public synchronized void playStation(StationInfo station) {
		this.stop();
		this.station = station;
		playerThread = new Thread(new Runnable() {
			public void run() {
				currSong = getNextSong();
				while(!stop && currSong != null) {
					currSong = getNextSong();
					app.displaySong(currSong);
					decodeMp4(currSong);
				}
			}
		});
		playerThread.setDaemon(true);
		playerThread.start();
		status = PlayerState.PLAYING;
	}
	
	public synchronized void stop() {
		if(playerThread != null) {
			this.stop = true;
			try {
				playerThread.join();
			} catch (InterruptedException e) {}
			playerThread = null;
			this.stop = false;
		}
		station = null;
		playlist = null;
	}
	
	
	public synchronized void skip() {
		this.skip = true;
	}
	
	public synchronized boolean isPaused() {
		return this.paused;
	}
	
	public synchronized void pause() {
		this.paused = true;
		status = PlayerState.PAUSED;
	}
	
	public synchronized void play() {
		this.paused = false;
		status = PlayerState.PLAYING;
	}
	
	public void playToggle() {
		if(paused)
			play();
		else
			pause();
	}
	
	public synchronized static void setVolume(float level) {
		if(level < 0) level = 0;
		if(level > 1) level = 1;
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
	
	private Song getNextSong() {
		if(playlist == null || index >= playlist.length) {
			playlist = Station.getPlayList(user, station);
			index = 0;
		}
		return playlist[index++];
	}
	
	private void decodeMp4(Song song) {
		dataLine = null;
		float oldVol = volume;
		try {
			String audioURL = song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl();
			System.out.println(audioURL.substring(0, audioURL.indexOf(".mp4")+4));
			URL url = new URL(audioURL);
			MP4Container cont = new MP4Container(url.openStream());
			Movie movie = cont.getMovie();
			song.setDuration(movie.getDuration());
			song.setPlaying(true);
			List<Track> tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
			AudioTrack track = (AudioTrack) tracks.get(0);
			AudioFormat audioFormat = new AudioFormat(track.getSampleRate()/2, 
					track.getSampleSize(), 
					track.getChannelCount(), true, true);
			Decoder dec = new Decoder(track.getDecoderSpecificInfo());
			dec.getConfig().setSBREnabled(false);
			dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start();
			setVolume(song.isAd() && Application.getConfig().getMuteAds() ? 0f : volume);
			Frame frame;
			byte[] chunk;
			SampleBuffer buf = new SampleBuffer();
			while(!stop && !skip && track.hasMoreFrames()) {
				while(!skip && paused) { Thread.sleep(100l); }
				frame = track.readNextFrame();
				song.update(frame.getTime());
				dec.decodeFrame(frame.getData(), buf);
				chunk = buf.getData();
				dataLine.write(chunk, 0, chunk.length);
			}
			skip = false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			song.setPlaying(false);
			if(song.isAd())
				volume = oldVol;
			if(dataLine != null) {
				dataLine.stop();
				dataLine.close();
			}				
		}
	}
}
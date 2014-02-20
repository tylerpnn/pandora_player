package player;

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
	private static float volume = 1f;

	private Thread playerThread;
	private boolean paused = false;
	private boolean skip = false;
	private StationInfo station;
	private Song[] playlist;
	private Song currSong;
	private int index;
	
	public Player(Application app, UserSession user) {
		this.app = app;
		this.user = user;
		status = PlayerState.WAITING;
	}
	
	@SuppressWarnings("deprecation")
	public void playStation(StationInfo station) {
		this.station = station;
		if(playerThread != null) {
			playerThread.interrupt();
			playerThread.stop();
			playerThread = null;
		}
		playerThread = new Thread(new Runnable() {
			public void run() {
				currSong = getNextSong();
				while(currSong != null && decodeMp4(currSong)) {
					currSong = getNextSong();
				}
			}
		});
		playerThread.setDaemon(true);
		playerThread.start();
		status = PlayerState.PLAYING;
	}
	
	public static PlayerState getStatus() {
		return status;
	}
	
	public static void setVolume(float level) {
		if(level < 0 || level > 1) throw new IllegalArgumentException("Level must be between 0 and 1");
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
			app.displaySongs(playlist);
			index = 0;
		}
		return playlist[index++];
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
	
	public void playToggle() {
		if(paused)
			play();
		else
			pause();
	}
	
	private boolean decodeMp4(Song song) {
		song.setPlaying(true);
		dataLine = null;
		try {
			String url = song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl();
			System.out.print(song.getSongInfo().getSongName() + " ");
			System.out.println(url);
			MP4Container cont = new MP4Container(new URL(url).openStream());
			Movie movie = cont.getMovie();
			song.setDuration((int) movie.getDuration());
			List<Track> tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
			AudioTrack track = (AudioTrack) tracks.get(0);
			AudioFormat audioFormat = new AudioFormat(track.getSampleRate()/2, 
					track.getSampleSize(), 
					track.getChannelCount(), true, true);
			dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			setVolume(volume);
			dataLine.start();
			Decoder dec = new Decoder(track.getDecoderSpecificInfo());
			dec.getConfig().setSBREnabled(false);
			Frame frame;
			byte[] chunk;
			SampleBuffer buf = new SampleBuffer();
			while(!skip && track.hasMoreFrames()) {
				while(!skip && paused) { Thread.sleep(100L); }
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









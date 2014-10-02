package com.tylerpnn.player;

import java.net.URL;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import net.sourceforge.jaad.mp4.api.Track;

import com.tylerpnn.pandora.Song;


public class Player {
	
	public enum PlayerState { PLAYING, PAUSED, WAITING }
	private static PlayerState status = PlayerState.WAITING;
	private static SourceDataLine dataLine;
	private static float volume = .7f;

	private static boolean paused = false;
	private static boolean skip = false;
	private static boolean stop = false;
	
	private Player() {}
	
	public static PlayerState getStatus() {
		return status;
	}
	
	public static boolean isPaused() {
		return paused;
	}
	
	public static void skip() {
		if(status == PlayerState.PLAYING || status == PlayerState.PAUSED) {
			skip = true;
			paused = false;
		}
	}
	
	private static void pause() {
		paused = true;
		status = PlayerState.PAUSED;
	}
	
	private static void play() {
		paused = false;
		status = PlayerState.PLAYING;
	}
	
	public static void playToggle() {
		if(paused) play();
		else pause();
	}
	
	public static void stopPlaying() {
		stop = true;
	}
	
	public static float setVolume(float level) {
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
		return volume;
	}
	
	public static void decodeMp4(Song song) {
		if(status != PlayerState.WAITING) return;
		
		status = PlayerState.PLAYING;
		dataLine = null;

		float oldVol = volume;
		try {
			
			String audioURL = song.getSongInfo().getAudioUrlMap().getHighQuality().getAudioUrl();
			URL url = new URL(audioURL);
			MP4Container cont = new MP4Container(url.openStream());
			Movie movie = cont.getMovie();
			song.setDuration(movie.getDuration());
			List<Track> tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
			AudioTrack track = (AudioTrack) tracks.get(0);
			AudioFormat audioFormat = new AudioFormat(track.getSampleRate() / 2, 
					track.getSampleSize(), track.getChannelCount(), true, true);
			Decoder dec = new Decoder(track.getDecoderSpecificInfo());
			dec.getConfig().setSBREnabled(false);
			dataLine = AudioSystem.getSourceDataLine(audioFormat);
			dataLine.open(audioFormat);
			dataLine.start();
			setVolume(song.isAd() ? 0f : volume);
			Frame frame;
			byte[] chunk;
			SampleBuffer buf = new SampleBuffer();
			while(!stop && !skip && track.hasMoreFrames()) {
				while(!skip && paused) { Thread.sleep(100l); }
				frame = track.readNextFrame();
				song.setTime(frame.getTime());
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
			status = PlayerState.WAITING;
			stop = false;
		}
	}
}
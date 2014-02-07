package player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import json.response.PlaylistResponse.Result.Song;
import net.sourceforge.jaad.aac.AACException;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;
import net.sourceforge.jaad.mp4.api.Track;


public class Player {
	
	private boolean playing;

	public Player() {
		
	}
	
	public void playSong(Song song) {
		byte[] songData = getSongData(song);
		try {
			MusicThread thread = new MusicThread(songData);
			thread.setDaemon(true);
			thread.start();
			while(true){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public byte[] getSongData(Song song) {
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
	
	public boolean isPlaying() {
		return this.playing;
	}
	
	public class MusicThread extends Thread {
		
		public boolean paused = false;
		public byte[] song;
		
		public MusicThread(byte[] songData) {
			this.song = songData;
		}
		
		public void run() {
			try {
				decodeMP4(song);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void decodeMP4(byte[] songData) throws Exception {
			SourceDataLine line = null;
			byte[] b;
			try {
				//create container
				final MP4Container cont = new MP4Container(new ByteArrayInputStream(songData));
				final Movie movie = cont.getMovie();
				//find AAC track
				final List<Track> tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
				if(tracks.isEmpty()) throw new Exception("movie does not contain any AAC track");
				final AudioTrack track = (AudioTrack) tracks.get(0);
				//create audio format
				final AudioFormat aufmt = new AudioFormat(track.getSampleRate(), track.getSampleSize(), track.getChannelCount(), true, true);
				line = AudioSystem.getSourceDataLine(aufmt);
				line.open();
				line.start();
				//decode
				final Decoder dec = new Decoder(track.getDecoderSpecificInfo());
				Frame frame;
				final SampleBuffer buf = new SampleBuffer();
				while(track.hasMoreFrames()) {
					frame = track.readNextFrame();
					try {
						dec.decodeFrame(frame.getData(), buf);
						b = buf.getData();
						line.write(b, 0, b.length);
					}
					catch(AACException e) {
						e.printStackTrace();
					}
				}
			}
			finally {
				if(line!=null) {
					line.stop();
					line.close();
				}
			}
		} 
		
	}
}









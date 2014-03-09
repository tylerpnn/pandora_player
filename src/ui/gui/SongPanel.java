package ui.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pandora.Song;

public class SongPanel extends JPanel {

	private Frame frame;
	private SongDisplay selected;
	
	public SongPanel(Frame frame) {
		this.frame = frame;
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void addSong(Song song) {
		SongDisplay sd = new SongDisplay(this, song);
		song.setDisplay(sd);
		this.add(sd);
	}
	
	public void scroll(SongDisplay sd) {
		if(frame.getScrollBar().isVisible()) {
			sd.setPreferredSize(new Dimension(sd.getWidth() - frame.getScrollBar().getWidth(), sd.getHeight()));
			sd.setSize(sd.getPreferredSize());
			sd.setMaximumSize(sd.getPreferredSize());
		}
		if(sd.getLocation().y + sd.getHeight() >= this.getHeight()) {
			frame.getScrollBar().setValue(sd.getLocation().y);
		}
	}
	
	public void select(SongDisplay sd) {
		if(selected != null || selected == sd) {
			if(selected.getSong().isPlaying()) {
				selected.setBackground(new Color(220, 220, 220));
			} else {
				selected.setBackground(Color.white);
			}
		}
		if(selected != sd) {
			selected = sd;
			selected.setBackground(new Color(42, 161, 235));
		} else {
			selected = null;
		}
	}
	
	public String getStationName(String stationId) {
		return frame.getStationName(stationId);
	}
	
	public void setFeedback(Song song, int feedback) {
		frame.setFeedback(song, feedback);
	}
	
	public void explainTrack(Song song) {
		frame.explainTrack(song);
	}
}
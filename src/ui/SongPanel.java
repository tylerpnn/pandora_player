package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pandora.Song;

public class SongPanel extends JPanel {

	private Frame parent;
	private SongDisplay selected;
	
	public SongPanel(Frame parent) {
		this.parent = parent;
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		this.setSize(new Dimension(parent.getWidth(), 400));
	}
	
	public void addSongDisplay(SongDisplay sd) {		
		this.add(sd);
	}
	
	public void addSongs(Song[] playlist) {
		for(Song song : playlist) {
			SongDisplay sd = new SongDisplay(this, song);
			song.setDisplay(sd);
			addSongDisplay(sd);
		}
		this.validate();
	}
	
	public void scroll(SongDisplay sd) {
		if(sd.getLocation().y >= parent.getScrollBar().getValue() + sd.getHeight()*3) {
			parent.getScrollBar().setValue(sd.getLocation().y);
			repaint();
			validate();
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
}
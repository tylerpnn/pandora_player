package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import json.response.PlaylistResponse.Result.SongInfo;
import pandora.Song;

public class SongPanel extends JPanel {

	private Frame parent;
	private List<SongDisplay> elements;
	private SongDisplay selected;
	
	public SongPanel(Frame parent) {
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
	}
	
	public void addSongDisplay(SongDisplay sd) {		
		this.add(sd);
	}
	
	public void addSongs(Song[] playlist) {
		this.removeAll();
		elements = new ArrayList<>();
		for(Song song : playlist) {
			SongDisplay sd = new SongDisplay(this, song);
			song.setDisplay(sd);
			addSongDisplay(sd);
			elements.add(sd);
		}
		this.validate();
	}
	
	public void select(SongDisplay sd) {
		if(selected != null || selected == sd) {
			if(selected.getSong().isPlaying()) {
				selected.setBackground(new Color(220, 220, 220));
			} else {
				selected.setBackground(Color.white);
			}
		}
		selected = sd;
		selected.setBackground(new Color(42, 161, 235));
	}
}
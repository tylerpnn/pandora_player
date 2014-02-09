package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import json.response.PlaylistResponse.Result.SongInfo;

public class SongPanel extends JPanel {

	private Frame parent;
	private int elements;
	
	public SongPanel(Frame parent) {
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		elements = 0;
	}
	
	public void addSongDisplay(SongDisplay sd) {
		this.add(sd);
	}
	
	public void addSongs(SongInfo[] playlist) {
		this.removeAll();
		for(SongInfo song : playlist) {
			SongDisplay sd = new SongDisplay(this, song);
			addSongDisplay(sd);
			elements++;
		}
		this.validate();
	}
}
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

public class SongPanel extends JPanel implements MouseListener {

	private Frame parent;
	private List<SongDisplay> elements;
	private SongDisplay selected;
	
	public SongPanel(Frame parent) {
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		this.addMouseListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
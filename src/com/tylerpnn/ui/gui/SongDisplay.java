package com.tylerpnn.ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.tylerpnn.pandora.Song;

public class SongDisplay extends JPanel implements MouseListener {
	
	private SongPanel parent;
	private Song song;
	private BufferedImage albumArt;
	private ContextMenu contextMenu;
	
	public SongDisplay(SongPanel parent, Song song) {
		this.parent = parent;
		this.song = song;
		this.setPreferredSize(new Dimension(parent.getWidth(), 115));
		this.setSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.white);
		albumArt = getAlbumArt();
		contextMenu = new ContextMenu(this);
		this.addMouseListener(this);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JLabel s = new JLabel();
		String info="";
		if(song.isAd()) {
			info = "Advertisement";
		} else {
			info = String.format(
					"<html><font size=\"4\"><b>%s</b></font><br>"
					+ "by %s<br>"
					+ "on %s<br>"
					+ "@%s</html>",
					song.getSongInfo().getSongName(),
					song.getSongInfo().getArtistName(),
					song.getSongInfo().getAlbumName(),
					parent.getStationName(song.getSongInfo().getStationId()));
		}
		s.setText(info);
		s.setAlignmentY(TOP_ALIGNMENT);
		this.add(Box.createHorizontalStrut(getHeight() + 10));
		this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.add(s);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(albumArt != null) {
        	g.drawImage(albumArt, 0, 0, getHeight(), getHeight(), null);
        	g.drawLine(0, 0, 0, getHeight());
        	g.drawLine(getHeight(), 0, getHeight(), getHeight());
        	g.drawLine(0, getHeight()-1, getHeight(), getHeight()-1);
        }
        ((Graphics2D)g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(song.isPlaying()) {
        	String t = String.format("%d:%02d / %d:%02d", 
        			(int)song.getTime() / 60,
        			(int)song.getTime() % 60,
        			(int)song.getDuration() / 60,
        			(int)song.getDuration() % 60);
        	g.drawString(t, getWidth() - g.getFontMetrics().stringWidth(t) - 15, getHeight() - 23);
        	drawProgress(g);
        }
        if(song.getSongRating() != 0) {
        	g.setColor((song.getSongInfo().getSongRating() > 0) 
        			? Color.orange : new Color(187, 187, 242));
        	int[] xp = new int[] { 0, 0, 20 },
          		  yp = new int[] { getHeight(), getHeight()-20, getHeight() };
        	Polygon p = new Polygon( xp, yp, 3);
        	g.fillPolygon(p);
        }
    }
	
	private void drawProgress(Graphics g) {
		int w = getWidth() - getHeight() - 20;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getHeight() + 10, getHeight() - 15, w, 5);
		double progress = w * (song.getTime() / song.getDuration());
		g.setColor(Color.black);
		g.fillRect(getHeight() + 10, getHeight() - 15, (int)progress, 5);
	}
	
	public void update() {
		repaint();
	}
	
	public Song getSong() {
		return this.song;
	}
	
	public void setFeedback(int feedback) {
		parent.setFeedback(song, feedback);
		update();
	}
	
	public void explainTrack() {
		parent.explainTrack(song);
	}
	
	public BufferedImage getAlbumArt() {
		BufferedImage img = null;
		ClassLoader cl = this.getClass().getClassLoader();
		try {
			if(song.isAd()) {
				img = ImageIO.read(cl.getResourceAsStream("res/blank.png"));
			} else {
				String url = song.getSongInfo().getAlbumArtUrl();
				img = ImageIO.read(new URL(url));
			}
		} catch (IOException e) {
			try {	//sometimes even when a url is given for the album art, it wont work. Use blank art in this case.
				img = ImageIO.read(cl.getResourceAsStream("res/blank.png"));
			} catch (IOException e1) { 
				return null; 
			}
		}
		return img;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e) && !song.isAd()) {
			contextMenu.show(e.getComponent(), e.getPoint().x, e.getPoint().y);
		}
		parent.select(this);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}

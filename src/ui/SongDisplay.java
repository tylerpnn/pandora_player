package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pandora.Song;

public class SongDisplay extends JPanel implements MouseListener {
	
	private SongPanel parent;
	private Song song;
	private BufferedImage albumArt;
	private ContextMenu contextMenu;
	
	public SongDisplay(SongPanel parent, Song song) {
		this.setPreferredSize(new Dimension(parent.getWidth(), 104));
		this.setSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.parent = parent;
		this.song = song;
		this.setBackground(Color.white);
		if(song.isAd()) song.getSongInfo().setAlbumArtUrl("");
		try {
			String url = song.getSongInfo().getAlbumArtUrl();
			albumArt = ImageIO.read(new URL(url));			
		} catch (IOException e) {
			ClassLoader cl = this.getClass().getClassLoader();
			try {
				albumArt = ImageIO.read(
					cl.getResourceAsStream("res/blank.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.addMouseListener(this);
		contextMenu = new ContextMenu(song, this);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(albumArt != null) {
        	g.drawImage(albumArt, 0, 0, 104, 104, null);
        	g.drawLine(0, 0, 0, 104);
        	g.drawLine(104, 0, 104, 104);
        	g.drawLine(0, 103, 104, 103);
        }
        ((Graphics2D)g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("default", Font.BOLD, 14));       
        g.drawString((song.isAd()) ? 
        		"Advertisement" : song.getSongInfo().getSongName(), 114, 20);
        g.setFont(new Font("default", Font.PLAIN, 12));
        if(!song.isAd()) {
	        g.drawString("by " + song.getSongInfo().getArtistName(), 114, 35);
	        g.drawString("on " + song.getSongInfo().getAlbumName(), 114, 50);
        }
        if(song.isPlaying()) {
        	g.drawString(String.format("%d:%02d / %d:%02d", 
        			song.getTime() / 60,
        			song.getTime() % 60,
        			song.getDuration() / 60,
        			song.getDuration() % 60), 114, 90);
        }
        if(song.getSongInfo().getSongRating() != 0) {
        	g.setColor((song.getSongInfo().getSongRating() > 0) 
        			? Color.orange : new Color(187, 187, 242));
        	Polygon p = new Polygon( new int[] { getWidth()-30, getWidth(), getWidth() },
        							 new int[] { getHeight(), getHeight()-30, getHeight() },
        							 3);
        	g.fillPolygon(p);
        }
    }
	
	public void update() {
		repaint();
	}
	
	public void setPlaying(boolean b) {
		parent.scroll(this);
		if(b) {
			setBackground(new Color(220,220,220));
		} else {
			setBackground(Color.white);
		}
	}
	
	public Song getSong() {
		return this.song;
	}
	
	public void setFeedback(int feedback) {
		parent.setFeedback(song, feedback);
		song.getSongInfo().setSongRating(feedback);
		update();
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

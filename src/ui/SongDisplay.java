package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pandora.Song;

public class SongDisplay extends JPanel implements MouseListener {
	
	private SongPanel parent;
	private Song song;
	private BufferedImage albumArt;
	
	public SongDisplay(SongPanel parent, Song song) {
		this.setPreferredSize(new Dimension(parent.getWidth(), 100));
		this.setSize(this.getPreferredSize());
		this.parent = parent;
		this.song = song;
		this.setBackground(Color.white);
		try {
			byte[] img = getImageData();
			if(img != null) {
				albumArt = ImageIO.read(new ByteArrayInputStream(img));
			} else {
				ClassLoader c = this.getClass().getClassLoader();
				albumArt = ImageIO.read(c.getResourceAsStream("res/blank.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addMouseListener(this);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(albumArt != null) {
        	g.drawImage(albumArt, 2, 2, 97, 97, null);      
        }
       ((Graphics2D)g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawRect(1, 1, 98, 98);
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(song.getSongInfo().getSongName(), 110, 20);
        g.setFont(new Font("default", Font.PLAIN, 12));
        g.drawString("by " + song.getSongInfo().getArtistName(), 110, 35);
        g.drawString("on " + song.getSongInfo().getAlbumName(), 110, 50);
        if(song.isPlaying()) {
        	g.drawString(String.format("%d:%02d / %d:%02d", song.getTime() / 60,
        			song.getTime() % 60,
        			song.getDuration() / 60,
        			song.getDuration() % 60), 110, 90);
        }
    }
	
	public void update() {
		repaint();
	}
	
	public void setPlaying(boolean b) {
		if(b) {
			setBackground(new Color(220, 220, 220));
			parent.scroll(this);
		} else {
			setBackground(Color.white);
		}
	}
	
	public byte[] getImageData() {
		if(song.getSongInfo().getAlbumArtUrl().equals(""))
			return null;
		URL url;
		URLConnection con;
		DataInputStream dis;
		byte[] data = null;
		try {
			url = new URL(song.getSongInfo().getAlbumArtUrl());
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
	
	public Song getSong() {
		return this.song;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			//play song
			parent.select(this);
		} else {
			parent.select(this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}

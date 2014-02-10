package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import json.response.PlaylistResponse.Result.SongInfo;

public class SongDisplay extends JPanel {
	
	private JComponent parent;
	private SongInfo song;
	private BufferedImage albumArt;
	
	public SongDisplay(JComponent parent, SongInfo song) {
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
				albumArt = ImageIO.read(new File("blank.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
        g.drawString(song.getSongName(), 110, 20);
        g.setFont(new Font("default", Font.PLAIN, 12));
        g.drawString("by " + song.getArtistName(), 110, 35);
        g.drawString("on " + song.getAlbumName(), 110, 50);
    }
	
	public byte[] getImageData() {
		if(song.getAlbumArtUrl().equals(""))
			return null;
		URL url;
		URLConnection con;
		DataInputStream dis;
		byte[] data = null;
		try {
			url = new URL(song.getAlbumArtUrl());
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
}
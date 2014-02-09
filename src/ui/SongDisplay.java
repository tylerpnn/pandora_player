package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import json.response.PlaylistResponse.Result.SongInfo;

public class SongDisplay extends JPanel {
	
	private JComponent parent;
	private SongInfo song;
	private BufferedImage albumArt;
	
	public SongDisplay(JComponent parent, SongInfo song) {
		this.setPreferredSize(new Dimension(parent.getWidth(), 75));
		this.setSize(this.getPreferredSize());
		this.parent = parent;
		this.song = song;
		this.setBackground(Color.white);
		try {
			byte[] img = getImageData();
			if(img != null)
				albumArt = ImageIO.read(new ByteArrayInputStream(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(albumArt != null) {
        	g.drawImage(albumArt, 2, 2, 78, 78, null);      
        }
        g.drawRect(1, 1, 79, 79);
        g.drawString(song.getSongName(), 85, 15);
        g.drawString("by " + song.getArtistName(), 85, 30);
        g.drawString("on " + song.getAlbumName(), 85, 45);
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

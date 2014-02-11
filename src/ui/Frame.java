package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import pandora.Application;
import pandora.Song;

public class Frame extends JFrame {

	private JPanel panel;
	private Application app;
	private ToolBar bar;
	private SongPanel songPanel;
	
	public Frame(Application app) {
		super("Pandora Player");
		this.app = app;
				
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 400));
		this.setSize(this.getPreferredSize());
		Rectangle bounds = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int x = bounds.width/2 - this.getSize().width/2;
		int y = bounds.height/2 - this.getSize().height/2;
		this.setLocation(new Point(x, y));
		
		panel = new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		initComponents();		
		
		repaint();
		validate();
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	public void initComponents() {
		this.setJMenuBar(new MenuBar(this));
		bar = new ToolBar(this);
		songPanel = new SongPanel(this);
		panel.add(bar, BorderLayout.SOUTH);
		panel.add(new JScrollPane(songPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
	}
	
	public void displaySongs(Song[] playlist) {
		songPanel.addSongs(playlist);
		this.validate();
	}
	
	public void chooseStation(String stationName) {
		if(app != null) {
			app.playStation(stationName);
		}		
	}
	
	public void skipSong() {
		if(app != null)
			app.skipSong();
	}
	
	public void playToggle() {
		if(app != null)
			app.playToggle();
	}
	
	public void login(String username, char[] password) {
		if(app.login(username, password)) {
			bar.setStations(app.getStationList());
		}
		bar.setSelectedStation("QuickMix");
	}
	
	public String[] getStationList() {
		return app.getStationList();
	}
}

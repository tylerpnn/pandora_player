package com.tylerpnn.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import com.tylerpnn.pandora.Application;
import com.tylerpnn.pandora.Song;
import com.tylerpnn.pandora.UserInterface;
import com.tylerpnn.player.Player;
import com.tylerpnn.ui.gui.Configuration.Location;

public class Frame extends JFrame implements UserInterface, WindowListener {

	private JPanel panel;
	private Application app;
	private ToolBar bar;
	private SongPanel songPanel;
	private JScrollPane scrollPane;
	private MenuBar menuBar;
	
	private static Configuration config;
	
	public Frame(Application app) {
		super("Pandora Player");
		this.app = app;
		config = Configuration.loadConfig();
		try {
			UIManager.put("Slider.paintValue", false);
			String osname = System.getProperty("os.name");
			if(osname.equals("Linux")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		start();
	}
	
	public void start() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.getContentPane().add(panel);
		initComponents();

		Location loc = getConfig().getLoc();
		if(loc != null)
			this.setLocation(loc.getPoint());
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		this.setPreferredSize(new Dimension(500, 400));
		this.setResizable(false);
		this.setVisible(true);
		this.setFrameSize(getConfig().isCompact());
	}
	
	public static Configuration getConfig() {
		return config;
	}
	
	public void initComponents() {
		menuBar = new MenuBar(this);
		this.setJMenuBar(menuBar);
		bar = new ToolBar(this);
		panel.add(bar, BorderLayout.SOUTH);
		songPanel = new SongPanel(this);
		scrollPane = new JScrollPane(songPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);
	}
	
	public JScrollBar getScrollBar() {
		return scrollPane.getVerticalScrollBar();
	}
	
	public void displaySong(Song song) {
		songPanel.addSong(song);
		revalidate();
		repaint();
		if(song.isAd()) {
			this.setTitle("Advertisement");
		} else {
			this.setTitle(String.format("%s - %s", 
					song.getSongInfo().getSongName(), 
					song.getSongInfo().getArtistName()));
		}
	}
	
	public void chooseStation(String stationName) {
		if(songPanel != null) {
			songPanel.removeAll();
		}
		app.playStation(stationName);
		bar.buttonToggle(Player.getStatus());
	}
	
	public void skipSong() {
		app.skipSong();
	}
	
	public void playToggle() {
		app.playToggle();
	}
	
	public void login(final UserInfo uInfo, final String proxy) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				if(app.login(uInfo.getUsername(), uInfo.getPassword(), proxy)) {
					bar.setStations(app.getStationList());
					menuBar.loggedIn();
					bar.setSelectedStation("QuickMix");
				} else {
					getConfig().setRememberUser(false);
					getConfig().setUser(null);
				}
				return null;
			}			
		}.execute();
	}
	
	public void logout() {
		app.logout();
		songPanel.removeAll();
		repaint();
		bar.setStations(new String[0]);
		this.setTitle("Pandora Player");
		menuBar.loggedOut();
	}
	
	public void setFeedback(Song song, int feedback) {
		app.setFeedback(song, feedback);
	}
	
	public void explainTrack(Song song) {
		JOptionPane.showMessageDialog(this,
				app.getExplanation(song),
				song.getSongInfo().getSongName(),
				JOptionPane.DEFAULT_OPTION);
	}
	
	public void setFrameSize(boolean b) {
		getConfig().setCompact(b);
		if(b) {
			Insets i = this.getInsets();
			int height = i.top + i.bottom + bar.getHeight() + menuBar.getHeight();
			this.setSize(new Dimension(500, height));
		} else {
			this.setSize(new Dimension(500, 400));
		}
	}
	
	public String[] getStationList() {
		return app.getStationList();
	}
	
	public String getStationName(String stationId) {
		return app.getStationName(stationId);
	}
	
	public void setProxy(String proxy) {
		app.setProxy(proxy);
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
		repaint();
	}

	@Override
	public void windowClosing(WindowEvent we) {
		getConfig().setLoc(new Location(this.getLocation()));
		Configuration.writeConfig(config);
		Application.exit();
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}

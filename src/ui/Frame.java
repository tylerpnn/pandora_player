package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import pandora.Song;
import player.Player;

public class Frame extends JFrame implements WindowListener {

	private JPanel panel;
	private Application app;
	private ToolBar bar;
	private SongPanel songPanel;
	private JScrollPane scrollPane;
	private MenuBar menuBar;
	
	public Frame(Application app) {
		super("Pandora Player");
		this.app = app;
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Slider.paintValue", false);
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
		panel.setLayout(new BorderLayout());
		this.getContentPane().add(panel);
		initComponents();
		
		this.addWindowListener(this);
		this.setResizable(false);
		this.setVisible(true);	
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
	}
	
	public void skipSong() {
		app.skipSong();
	}
	
	public void playToggle() {
		app.playToggle();
	}
	
	public void login(String username, char[] password) {
		if(app.login(username, password)) {
			bar.setStations(app.getStationList());
			menuBar.loggedIn();
		}
		bar.setSelectedStation("QuickMix");
		bar.buttonToggle(Player.getStatus());
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
	
	public String[] getStationList() {
		return app.getStationList();
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		repaint();		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}

package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar implements ActionListener {
	
	private Frame frame;
	
	private JMenu optionsMenu;
	private JMenuItem login;
	private JCheckBoxMenuItem muteAds;
	private JCheckBoxMenuItem compact;
	private JMenuItem exit;
	
	private JMenu songMenu;
	private JMenuItem like;
	private JMenuItem dislike;
	private JMenuItem explain;
	
	private boolean isLoggedIn = false;
	
	public MenuBar(final Frame frame) {
		this.frame = frame;
		
		optionsMenu = new JMenu("Options");		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));		
		login = new JMenuItem("Log In");
		login.addActionListener(this);
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));		
		muteAds = new JCheckBoxMenuItem("Mute Ads", Application.getConfig().getMuteAds());
		muteAds.addActionListener(this);		
		compact = new JCheckBoxMenuItem("Compact mode", Application.getConfig().isCompact());
		compact.addActionListener(this);
		optionsMenu.add(login);
		optionsMenu.add(muteAds);
		optionsMenu.add(compact);
		optionsMenu.add(exit);
		
		songMenu = new JMenu("Song");
		like = new JMenuItem("Like");
		like.addActionListener(this);
		dislike = new JMenuItem("Dislike");
		dislike.addActionListener(this);
		explain = new JMenuItem("Explain track");
		explain.addActionListener(this);
		songMenu.add(like);
		songMenu.add(dislike);
		songMenu.add(explain);

		
		this.add(optionsMenu);
		this.add(songMenu);
	}
	
	public void loggedIn() {
		isLoggedIn = true;
		login.setText("Log Out");
		login.setAccelerator(null);
	}
	
	public void loggedOut() {
		isLoggedIn = false;
		login.setText("Log In");
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login) {
			if(!isLoggedIn) {
				if(Application.getConfig().getRememberUser() && Application.getConfig().getUser() != null) {
					frame.login(Application.getConfig().getUser());
				} else {
					new LoginDialog(frame);
				}
			} else {
				frame.logout();
			}
		}
		if(e.getSource() == exit) {
			Application.exit();
		}
		if(e.getSource() == muteAds) {
			Application.getConfig().setMuteAds(muteAds.getState());
		}
		if(e.getSource() == compact) {
			frame.setFrameSize(compact.getState());
		}
		if(e.getSource() == like) {

		}
	}
}

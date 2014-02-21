package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {
	
	private JMenuItem login;
	private ActionListener logoutListener;
	private ActionListener loginListener;
	private JMenu optionsMenu;
	
	public MenuBar(final Frame parent) {
		optionsMenu = new JMenu("Options");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setMnemonic(KeyEvent.VK_W);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		loginListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginDialog(parent);
			}
		};
		logoutListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.logout();
			}
		};
		
		login = new JMenuItem("Log In");
		login.addActionListener(loginListener);
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		
		optionsMenu.add(login);
		optionsMenu.add(exit);
		this.add(optionsMenu);
	}
	
	public void loggedIn() {
		login.setText("Log Out");
		login.removeActionListener(loginListener);
		login.addActionListener(logoutListener);
		login.setAccelerator(null);
	}
	
	public void loggedOut() {
		login.setText("Log In");
		login.removeActionListener(logoutListener);
		login.addActionListener(loginListener);
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	}
}

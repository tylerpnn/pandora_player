package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {

	private final Frame parent;
	
	public MenuBar(final Frame parent) {
		this.parent = parent;
		
		JMenu optionsMenu = new JMenu("Options");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setMnemonic(KeyEvent.VK_W);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		JMenuItem login = new JMenuItem("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					new LoginDialog(parent);
			}
		});
		login.setMnemonic(KeyEvent.VK_L);
		login.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		
		optionsMenu.add(login);
		optionsMenu.add(exit);
		this.add(optionsMenu);
	}
}

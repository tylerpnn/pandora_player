package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	private final Frame parent;
	
	public MenuBar(final Frame parent) {
		this.parent = parent;
		
		JMenu fileMenu = new JMenu("File");
		JMenu optionsMenu = new JMenu("Options");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem login = new JMenuItem("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					new LoginDialog(parent);
			}
		});
		fileMenu.add(exit);
		optionsMenu.add(login);
		this.add(fileMenu);
		this.add(optionsMenu);
	}
}

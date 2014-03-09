package ui.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pandora.Application;
import pandora.UserInfo;

public class LoginDialog extends JDialog implements ActionListener {

	private Frame frame;
	
	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton cancel;
	private JCheckBox pandoraOne;
	private JCheckBox rememberUser;
	
	public LoginDialog(Frame frame) {
		super(frame, "Login", true);
		this.frame = frame;
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(email());
		panel.add(password());
		panel.add(checkBoxes());
		panel.add(buttons());

		this.pack();
		this.setLocationRelativeTo(frame);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private JPanel email() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panel.setLayout(new GridLayout(1, 2, 0 ,5));
		panel.add(new JLabel("Email:"));
		username = new JTextField(15);
		panel.add(username);
		return panel;
	}
	
	private JPanel password() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panel.setLayout(new GridLayout(1, 2, 0, 5));
		panel.add(new JLabel("Password:"));
		password = new JPasswordField(15);
		panel.add(password);
		password.addActionListener(this);
		return panel;
	}
	
	private JPanel buttons() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		login = new JButton("Login");
		login.addActionListener(this);
		cancel = new JButton("Close");
		cancel.addActionListener(this);
		panel.add(login);
		panel.add(cancel);
		return panel;
	}
	
	private JPanel checkBoxes() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panel.setLayout(new GridLayout(1, 2, 0, 5));
		pandoraOne = new JCheckBox("Pandora One", false);
		rememberUser = new JCheckBox("Remember me", false);
		panel.add(pandoraOne);
		panel.add(rememberUser);
		return panel;
	}
	
	public void close() {
		this.dispose();
	}
	
	public void login(String username, char[] password, boolean pandoraOne) {
		UserInfo user = new UserInfo(username, password, pandoraOne);
		if(rememberUser.isSelected()) {
			Application.getConfig().setRememberUser(true);
			Application.getConfig().setUser(user);
		}
		frame.login(user);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		close();
		if(e.getSource() == this.login || e.getSource() == this.password) {
			login(username.getText(), password.getPassword(), pandoraOne.isSelected());
		}
	}
}
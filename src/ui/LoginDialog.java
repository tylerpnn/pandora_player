package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog implements ActionListener {

	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton cancel;
	private Frame parent;
	
	public LoginDialog(Frame parent) {
		super(parent, "Login", true);
		this.parent = parent;
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(panel);	
		panel.setLayout(new BorderLayout());
		
		panel.add(labelPanel(), BorderLayout.WEST);
		panel.add(fieldPanel(), BorderLayout.EAST);
		panel.add(buttonPanel(), BorderLayout.SOUTH);		
		
		this.setLocationRelativeTo(parent);
		this.setSize(new Dimension(220, 125));
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		login = new JButton("Login");
		cancel = new JButton("Cancel");
		login.addActionListener(this);
		cancel.addActionListener(this);
		panel.add(Box.createHorizontalGlue());
		panel.add(login);
		panel.add(Box.createHorizontalStrut(10));
		panel.add(cancel);
		panel.add(Box.createHorizontalGlue());
		return panel;
	}
	
	private JPanel fieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		username = new JTextField();
		username.setPreferredSize(new Dimension(150, 18));
		username.setMaximumSize(new Dimension(150, 18));		
		password = new JPasswordField();
		password.setMaximumSize(new Dimension(150, 18));
		password.setPreferredSize(new Dimension(150, 18));
		password.addActionListener(this);
		panel.add(username);
		panel.add(Box.createVerticalStrut(10));
		panel.add(password);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	private JPanel labelPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setAlignmentX(RIGHT_ALIGNMENT);
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(Box.createVerticalStrut(1));
		panel.add(emailLabel);
		panel.add(Box.createVerticalStrut(14));
		panel.add(passwordLabel);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public void close() {
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.login) {
			this.close();
			parent.login(username.getText(), password.getPassword());
		}
		if(e.getSource() == cancel) {
			this.close();
		}
		if(e.getSource() == this.password) {
			this.close();
			parent.login(username.getText(), password.getPassword());
		}
	}
}
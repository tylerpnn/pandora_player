package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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

		this.pack();
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private JPanel buttonPanel() {
		JPanel panel = new JPanel();
		login = new JButton("Login");
		cancel = new JButton("Cancel");
		login.addActionListener(this);
		cancel.addActionListener(this);
		panel.add(login);
		panel.add(cancel);
		return panel;
	}
	
	private JPanel fieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 0, 5));
		username = new JTextField(15);	
		password = new JPasswordField(15);
		password.addActionListener(this);
		panel.add(username);
		panel.add(password);
		return panel;
	}
	
	private JPanel labelPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 0, 5));
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setAlignmentX(RIGHT_ALIGNMENT);
		JLabel passwordLabel = new JLabel("Password:  ");
		passwordLabel.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(emailLabel, BorderLayout.NORTH);
		panel.add(passwordLabel, BorderLayout.SOUTH);
		return panel;
	}
	
	public void close() {
		this.dispose();
	}
	
	public void login(String username, char[] password) {
		parent.login(username, password);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		close();
		if(e.getSource() == this.login) {
			login(username.getText(), password.getPassword());
		}
		if(e.getSource() == this.password) {
			login(username.getText(), password.getPassword());
		}
	}
}
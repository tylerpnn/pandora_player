package com.tylerpnn.ui.fx;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.tylerpnn.pandora.Application;
import com.tylerpnn.pandora.Song;
import com.tylerpnn.pandora.UserInterface;

public class Window implements UserInterface {

	private Application app;
	private Stage stage;
	private StackPane root;
	
	public Window(Application app) {
		this.app = app;
		new JFXPanel();
	}

	@Override
	public void displaySong(Song song) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		stage = new Stage();
		stage.setTitle("Pandora Player");
		root = new StackPane();
		stage.setScene(new Scene(root, 500, 500));
		stage.show();
	}
	
	
}

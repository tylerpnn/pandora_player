package ui.cli;

import pandora.Application;
import pandora.Song;
import pandora.UserInterface;

public class CLI implements UserInterface {

	private Application app;
	
	public CLI(Application app) {
		this.app = app;
		System.out.println("hi");
		System.out.println("hi");
		System.out.println("hi");
		System.out.println("hi");
		System.out.println("hi");
		
	}
	
	public void print(String s) {
		System.out.println(s);
	}

	@Override
	public void displaySong(Song song) {
		
	}
}

package ui.cli;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import pandora.Application;
import pandora.Song;
import pandora.UserInterface;

public class CLI implements UserInterface {

	private Application app;
	private Console c;
	private Reader in;
	private Writer out;
	
	public CLI(Application app) {
		this.app = app;
		if((c = System.console()) == null) {
			throw new RuntimeException("Console not supported");
		}
		in = c.reader();
		out = c.writer();
	}
	
	public void print(String s) {
		try {
			out.write(s);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void displaySong(Song song) {
		
	}
}

package com.tylerpnn.ui.cli;

public class Console {

	private java.io.Console c;
	
	public Console() {
		if((c = System.console()) == null) {
			throw new RuntimeException("Console not supported");
		}
	}
	
	public void println(Object s) {
		c.printf("%s%n", s.toString());
	}
	
	public void printf(String fmt, Object ... args) {
		c.printf(fmt, args);
	}
	
	public void print(Object s) {
		c.printf("%s", s.toString());
	}
	
	public String readLine() {
		return c.readLine();
	}
	
	public String readLine(String fmt, Object ... args) {
		return c.readLine(fmt, args);
	}
	
	public char[] readPassword(String fmt, Object ... args) {
		return c.readPassword(fmt, args);
	}
}

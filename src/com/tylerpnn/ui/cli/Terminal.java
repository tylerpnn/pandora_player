package com.tylerpnn.ui.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

class Terminal {

	static {
		if(System.getProperty("os.name").equals("Linux")) {
			try {
				System.loadLibrary("Terminal");
			} catch(UnsatisfiedLinkError e) {
				loadLib();
			}
		}
	}
	
	private static void loadLib() {
		ClassLoader c = Terminal.class.getClassLoader();
		InputStream in = c.getResourceAsStream("libTerminal.so");
		File lib = new File(System.getProperty("java.io.tmpdir") + 
				File.separator + System.currentTimeMillis()
				+ "libTerminal.so");
		try {
			FileOutputStream out = new FileOutputStream(lib);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
			    out.write(buffer, 0, len);
			}
			out.flush();
			in.close();
			out.close();
			System.load(lib.toString());
			lib.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private CLI c;
	private boolean enabled;
	
	public Terminal(CLI c) {
		this.c = c;
		enabled = true;
		construct();
	}
	
	public void enable() {
		setEcho(1);
		setCanonical(1);
	}
	
	public void disable() {
		setEcho(0);
		setCanonical(0);
	}
	
	private native void construct();
	
	public native void setEcho(int e);
	public native void setCanonical(int e);
	public native void listen();
	
	private void fire_event(char ch) {
		c.handleKey(ch);
	}
}

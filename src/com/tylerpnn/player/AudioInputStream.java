package com.tylerpnn.player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AudioInputStream extends InputStream {
	
	protected int mark;
	protected int readPos;
	protected int bufferPos;
	private ArrayList<Byte> buf;
	
	private URL url;
	private Thread bufferThread;
	private boolean closed;
	
	public AudioInputStream(URL url) {
		this.url = url;
	}
	
	@Override
	public synchronized int read() throws IOException {
		return (readPos < bufferPos) ? (buf.get(readPos++) & 0xFF) : -1;
	}
	
	public synchronized int read(byte[] b, int offset, int len) {
		if (offset < 0 || len < 0 || len > b.length - offset) {
			throw new IndexOutOfBoundsException();
		}
		if(readPos < bufferPos) {
			return -1;
		}
		len = Math.min(len, readPos - bufferPos);
//		System.arraycopy(buf, readPos, b, offset, len);
		for(int i=readPos; i<readPos + len; i++) {
			b[i-readPos] = buf.get(i);
		}
		readPos += len;
		return len;
	}
	
	
	public synchronized int available() {
		return bufferPos - readPos;
	}
	
	public synchronized void mark(int markPos) {
		mark = readPos;
	}
	
	public boolean markSupported() {
		return true;
	}
	
	public synchronized void reset() {
		readPos = mark;
	}
	
	public void open() throws Exception {
		final URLConnection ucon = url.openConnection();
		buf = new ArrayList<>();
		readPos = bufferPos = mark = 0;
//		final DataInputStream dis = new DataInputStream(ucon.getInputStream());
		final InputStream in = ucon.getInputStream();
		bufferThread = new Thread(new Runnable() {
			public void run() {
				try {
					while(!closed) {
						buf.add((byte)in.read());
						bufferPos++;
						System.out.println(buf.get(bufferPos-1));
					}
					in.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		bufferThread.start();
	}	
	
	public void close() {
		closed = true;
		try {
			bufferThread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}

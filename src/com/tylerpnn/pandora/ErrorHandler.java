package com.tylerpnn.pandora;

import java.io.File;
import java.io.PrintWriter;

public class ErrorHandler {

	public static class PandoraServerException extends Exception {
		public PandoraServerException(String msg) {
			super(msg);
		}
	}
	
	public static void errorCheck(int errorCode) throws PandoraServerException {
		throw new PandoraServerException("Status: fail, Code: " + errorCode);
	}
	
	public static void logJSON(String json) {
		json = json.replace("{", "%n{%n");
		json = json.replace("}", "%n}%n");
		json = json.replace(",", ",%n");
		try {
			PrintWriter p = new PrintWriter(new File(System.currentTimeMillis()/1000 + ".json"));
			p.write(json, 0, json.length());
			p.close();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
}

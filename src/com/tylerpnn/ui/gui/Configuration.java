package com.tylerpnn.ui.gui;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerpnn.pandora.Crypt;

public final class Configuration {
	
	public static final String CONFIG_DIR = System.getProperty("user.home")
			+ File.separator + ".pandora";

	private boolean muteAds, 
					 rememberUser,
					 compact;
	private UserInfo user;
	private String proxy;
	private Location loc;

	private Configuration() {
		muteAds = true;
		rememberUser = compact = false;
	}
	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public void setMuteAds(boolean b) {
		muteAds = b;
	}
	
	public boolean getMuteAds() {
		return muteAds;
	}
	
	public void setRememberUser(boolean b) {
		rememberUser = b;
	}
	
	public boolean getRememberUser() {
		return rememberUser;
	}
	
	public boolean isCompact() {
		return compact;
	}

	public void setCompact(boolean compact) {
		this.compact = compact;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static void writeConfig(Configuration config) {
		if(config != null) {
			Crypt c = new Crypt(Crypt.DECRYPT_KEY, Crypt.DECRYPT_KEY);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(CONFIG_DIR));
				fos.write(c.encrypt(config.toString().getBytes()));
				fos.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Configuration loadConfig() {
		Configuration config = null;
		File cFile = new File(CONFIG_DIR);
		if(cFile.exists()) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get(CONFIG_DIR));
				byte[] decoded = new Crypt().decrypt(encoded);
				ObjectMapper mapper = new ObjectMapper();
				config = mapper.readValue(decoded, Configuration.class);
			} catch(Exception e) {
				e.printStackTrace();
				config = new Configuration();
			}
		} else {
			config = new Configuration();
		}
		return config;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public static class Location {
		public final int x;
		public final int y;
		
		@JsonCreator
		public Location(@JsonProperty("x") int x, @JsonProperty("y") int y) {
			this.x = x;
			this.y = y;
		}
		
		public Location(Point p) {
			this.x = p.x;
			this.y = p.y;
		}
		
		@JsonIgnore
		public Point getPoint() {
			return new Point(x, y);
		}
	}
}

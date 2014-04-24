package com.tylerpnn.json.response;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public abstract class JsonResponse {

	private String stat;
	private int code;

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static <T> T loadFromJson(String json, Class<T> type) {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return new Gson().fromJson(reader, type);
	}
}

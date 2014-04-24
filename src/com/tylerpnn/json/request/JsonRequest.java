package com.tylerpnn.json.request;

import com.google.gson.Gson;


public abstract class JsonRequest {

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}

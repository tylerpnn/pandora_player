package com.tylerpnn.json.response;

import com.google.gson.Gson;
import com.tylerpnn.pandora.ErrorHandler;
import com.tylerpnn.pandora.ErrorHandler.PandoraServerException;

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
	
	public <T extends JsonResponse> T loadFromJson(String json) {
		T t = null;
		try {
			t = (T) new Gson().fromJson(json, this.getClass());
			if(!t.getStat().equalsIgnoreCase("ok"))
				ErrorHandler.errorCheck(t.getCode());
		} catch(PandoraServerException pse) {
			return null;
		}
		return t;
//		return (T) new Gson().fromJson(json, this.getClass());
	}
}

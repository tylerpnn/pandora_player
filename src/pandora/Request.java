package pandora;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import json.request.JSONRequest;


public class Request {
	private String response;
	private String params;
	private String postData;
	private boolean encrypted;
	private Crypt crypt;
	
	
	public Request(String method, UserSession user, JSONRequest reqJson, boolean encrypted) {
		crypt = new Crypt();
		this.encrypted = encrypted;
		params = "method=" + method;
		if(user.getAuthToken() != null) {
			try {
				params += "&auth_token=" + URLEncoder.encode(user.getAuthToken(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(user.getPartnerId() != null) {
			params += "&partner_id=" + user.getPartnerId();
		}
		if(user.getUserId() != null) {
			params += "&user_id=" + user.getUserId();
		}
		
		String json = reqJson.toString();
		if(this.encrypted) {
			postData = crypt.byteToHex(crypt.encrypt(json.getBytes()));
		} else {
			postData = json;
		}
	}
	
	public String getParams() {
		return params;
	}
	
	public String getPostData() {
		return postData;
	}
	
	public void setPostData(String s) {
		this.postData = s;
	}
	
	public void setParams(String s) {
		this.params = s;
	}
	
	public void setRespose(String response) {
		this.response = response;
	}
	
	public boolean isEncrypted() {
		return this.encrypted;
	}
	
	public String getResponse() {
		return this.response;
	}
}

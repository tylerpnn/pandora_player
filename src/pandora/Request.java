package pandora;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import json.request.JsonRequest;


public class Request {
	
	private String response;
	private String params;
	private String postData;
	private boolean encrypted;
	private Crypt crypt;
	
	
	public Request(String method, UserSession user, JsonRequest jsonReq, boolean encrypted) {
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
		
		String json = jsonReq.toString();
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
	
	public void setResponse(String response) {
		this.response = response;
	}
	
	public boolean isEncrypted() {
		return this.encrypted;
	}
	
	public String getResponse() {
		return this.response;
	}
}

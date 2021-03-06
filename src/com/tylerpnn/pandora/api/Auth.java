package com.tylerpnn.pandora.api;

import com.tylerpnn.json.request.PartnerLoginRequest;
import com.tylerpnn.json.request.UserLoginRequest;
import com.tylerpnn.json.response.PartnerLoginResponse;
import com.tylerpnn.json.response.UserLoginResponse;
import com.tylerpnn.pandora.Crypt;
import com.tylerpnn.pandora.Request;
import com.tylerpnn.pandora.RequestHandler;
import com.tylerpnn.pandora.UserSession;

public class Auth {

	public static void partnerLogin(UserSession user) {
		PartnerLoginRequest plreq = new PartnerLoginRequest();
		plreq.setUsername("android");
		plreq.setPassword("AC7IBG09A3DTSYM4R41UJWL07VLN8JI7");
		plreq.setDeviceModel("android-generic");
		plreq.setVersion("5");
		Request req = new Request("auth.partnerLogin", user, plreq, false);
		RequestHandler.sendRequest(req);
		
//		PartnerLoginResponse plres = JsonResponse.loadFromJson(
//				req.getResponse(), PartnerLoginResponse.class);
		PartnerLoginResponse plres = 
				new PartnerLoginResponse().loadFromJson(req.getResponse());
		Crypt c = new Crypt();
		user.setPartnerAuthToken(plres.getPartnerAuthToken());
		user.setPartnerId(plres.getPartnerId());
		user.setSyncTime(Long.parseLong(c.decryptSyncTime(plres.getSyncTime())));
	}
	
	public static void userLogin(UserSession user, String email, char[] pw) {
		UserLoginRequest ulreq = new UserLoginRequest();
		ulreq.setLoginType("user");
		ulreq.setPartnerAuthToken(user.getPartnerAuthToken());
		ulreq.setUsername(email);
		ulreq.setPassword(String.valueOf(pw));
		ulreq.setSyncTime(user.calcSyncTime());
		Request req = new Request("auth.userLogin", user, ulreq, true);
		RequestHandler.sendRequest(req);
		ulreq.setPassword(null);
		UserLoginResponse ulres = 
				new UserLoginResponse().loadFromJson(req.getResponse());
		if(ulres != null) {
			user.setUserAuthToken(ulres.getUserAuthToken());
			user.setUserId(ulres.getUserId());
		}
	}
}

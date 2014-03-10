package pandora.api;

import json.request.PartnerLoginRequest;
import json.request.UserLoginRequest;
import json.response.JSONResponse;
import json.response.PartnerLoginResponse;
import json.response.UserLoginResponse;
import pandora.Crypt;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserInfo;
import pandora.UserSession;

public class Auth {

	public static void partnerLogin(UserSession user) {
		PartnerLoginRequest plreq = new PartnerLoginRequest();
		plreq.setUsername("android");
		plreq.setPassword("AC7IBG09A3DTSYM4R41UJWL07VLN8JI7");
		plreq.setDeviceModel("android-generic");
		plreq.setVersion("5");
		Request req = new Request("auth.partnerLogin", user, plreq, false);
		RequestHandler.sendRequest(req);
		
		PartnerLoginResponse plres = JSONResponse.loadFromJson(
				req.getResponse(), PartnerLoginResponse.class);
		Crypt c = new Crypt();
		user.setPartnerAuthToken(plres.getPartnerAuthToken());
		user.setPartnerId(plres.getPartnerId());
		user.setSyncTime(Long.parseLong(c.decryptSyncTime(plres.getSyncTime())));
	}
	
	public static void userLogin(UserSession user, UserInfo uInfo) {
		UserLoginRequest ulreq = new UserLoginRequest();
		ulreq.setLoginType("user");
		ulreq.setPartnerAuthToken(user.getPartnerAuthToken());
		ulreq.setUsername(uInfo.getUsername());
		ulreq.setPassword(String.valueOf(uInfo.getPassword()));
		ulreq.setSyncTime(user.calcSyncTime());
		Request req = new Request("auth.userLogin", user, ulreq, true);
		RequestHandler.sendRequest(req);
		
		UserLoginResponse ulres = JSONResponse.loadFromJson(
				req.getResponse(), UserLoginResponse.class);
		user.setUserAuthToken(ulres.getUserAuthToken());
		user.setUserId(ulres.getUserId());
	}
}

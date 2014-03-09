package pandora.api;

import java.io.IOException;

import json.request.PartnerLoginRequest;
import json.request.UserLoginRequest;
import json.response.PartnerLoginResponse;
import json.response.UserLoginResponse;
import pandora.Crypt;
import pandora.ErrorHandler;
import pandora.ErrorHandler.PandoraServerException;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserInfo;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Auth {

	public static void partnerLogin(UserSession user) {
		PartnerLoginRequest plreq = new PartnerLoginRequest();
		plreq.setUsername("android");
		plreq.setPassword("AC7IBG09A3DTSYM4R41UJWL07VLN8JI7");
		plreq.setDeviceModel("android-generic");
		plreq.setVersion("5");
		Request req = new Request("auth.partnerLogin", user, plreq, false);
		RequestHandler.sendRequest(req);
		
		PartnerLoginResponse plres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			plres = mapper.readValue(req.getResponse(), PartnerLoginResponse.class);
			if(!plres.getStat().equalsIgnoreCase("ok")) {
				ErrorHandler.errorCheck(plres.getCode());
			}
		} catch (IOException | PandoraServerException e) {
			ErrorHandler.logJSON(req.getResponse());
			e.printStackTrace();
		}
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
		
		UserLoginResponse ulres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			ulres = mapper.readValue(req.getResponse(), UserLoginResponse.class);
			if(!ulres.getStat().equalsIgnoreCase("ok")) {
				ErrorHandler.errorCheck(ulres.getCode());
			}
		} catch(IOException | PandoraServerException e) {
			ErrorHandler.logJSON(req.getResponse());
			e.printStackTrace();
		}
		user.setUserAuthToken(ulres.getUserAuthToken());
		user.setUserId(ulres.getUserId());
	}
}

package pandora.api;

import json.request.ExplainTrackRequest;
import json.response.ExplainTrackResponse;
import json.response.ExplainTrackResponse.Result.Explanation;
import json.response.PlaylistResponse.Result.SongInfo;
import pandora.ErrorHandler;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Track {

	public static String[] explainTrack(UserSession user, SongInfo song) {
		ExplainTrackRequest etreq = new ExplainTrackRequest();
		etreq.setTrackToken(song.getTrackToken());
		etreq.setUserAuthToken(user.getUserAuthToken());
		etreq.setSyncTime(user.calcSyncTime());
		Request req = new Request("track.explainTrack", user, etreq, true);
		RequestHandler.sendRequest(req);
		
		ExplainTrackResponse etres = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			etres = mapper.readValue(req.getResponse(), ExplainTrackResponse.class);
			if(!etres.getStat().equals("ok")) {
				ErrorHandler.errorCheck(etres.getCode());
			}
		} catch(Exception e) {
			ErrorHandler.logJSON(req.getResponse());
			e.printStackTrace();
		}
		Explanation[] explanations = etres.getResult().getExplanations();
		String[] s = new String[explanations.length];
		for(int i=0; i<explanations.length; i++) {
			s[i] = explanations[i].getFocusTraitName();
		}
		return s;
	}
}

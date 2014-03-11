package pandora.api;

import json.request.ExplainTrackRequest;
import json.response.ExplainTrackResponse;
import json.response.ExplainTrackResponse.Result.Explanation;
import json.response.JsonResponse;
import json.response.PlaylistResponse.Result.SongInfo;
import pandora.Request;
import pandora.RequestHandler;
import pandora.UserSession;

public class Track {

	public static String[] explainTrack(UserSession user, SongInfo song) {
		ExplainTrackRequest etreq = new ExplainTrackRequest();
		etreq.setTrackToken(song.getTrackToken());
		etreq.setUserAuthToken(user.getUserAuthToken());
		etreq.setSyncTime(user.calcSyncTime());
		Request req = new Request("track.explainTrack", user, etreq, true);
		RequestHandler.sendRequest(req);
		
		ExplainTrackResponse etres = JsonResponse.loadFromJson(
				req.getResponse(), ExplainTrackResponse.class);
		Explanation[] explanations = etres.getResult().getExplanations();
		String[] s = new String[explanations.length-1];
		for(int i=0; i<s.length; i++) {
			s[i] = explanations[i].getFocusTraitName();
		}
		return s;
	}
}

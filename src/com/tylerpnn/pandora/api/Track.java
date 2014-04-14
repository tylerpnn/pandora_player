package com.tylerpnn.pandora.api;

import com.tylerpnn.json.request.ExplainTrackRequest;
import com.tylerpnn.json.response.ExplainTrackResponse;
import com.tylerpnn.json.response.ExplainTrackResponse.Result.Explanation;
import com.tylerpnn.json.response.JsonResponse;
import com.tylerpnn.json.response.PlaylistResponse.Result.SongInfo;
import com.tylerpnn.pandora.Request;
import com.tylerpnn.pandora.RequestHandler;
import com.tylerpnn.pandora.UserSession;

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

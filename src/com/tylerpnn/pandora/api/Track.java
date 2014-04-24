package com.tylerpnn.pandora.api;

import com.tylerpnn.json.request.ExplainTrackRequest;
import com.tylerpnn.json.response.ExplainTrackResponse;
import com.tylerpnn.json.response.ExplainTrackResponse.Result.Explanation;
import com.tylerpnn.json.response.PlaylistResponse.Result.SongInfo;
import com.tylerpnn.pandora.Request;
import com.tylerpnn.pandora.RequestHandler;
import com.tylerpnn.pandora.UserSession;

public class Track {

	public static String explainTrack(UserSession user, SongInfo song) {
		ExplainTrackRequest etreq = new ExplainTrackRequest();
		etreq.setTrackToken(song.getTrackToken());
		etreq.setUserAuthToken(user.getUserAuthToken());
		etreq.setSyncTime(user.calcSyncTime());
		Request req = new Request("track.explainTrack", user, etreq, true);
		RequestHandler.sendRequest(req);
		
		ExplainTrackResponse etres = 
				new ExplainTrackResponse().loadFromJson(req.getResponse());
		
		Explanation[] explanations = etres.getResult().getExplanations();
		String[] traits = new String[explanations.length-1];
		for(int i=0; i<traits.length; i++) {
			traits[i] = explanations[i].getFocusTraitName();
		}
		String[] format = new String[traits.length];
		for(int i=0; i < format.length-1; i++) 
			format[i] = "%s, " + ((i%2==0) ? "\n" : "");
		format[format.length-1] = "and %s.";
		StringBuilder fmt = new StringBuilder("This track was selected because it features ");
		for(String s : format) fmt.append(s);
		return String.format(fmt.toString(), (Object[])traits);
	}
}

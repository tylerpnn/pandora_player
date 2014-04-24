package com.tylerpnn.pandora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RequestHandler {
	
	private static final String URL = "http://tuner.pandora.com/services/json/?";

	public static void sendRequest(Request req) {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(URL + req.getParams());
		post.addHeader("Content-Type", "text/plain;charset=UTF-8");
		try {
			StringEntity j = new StringEntity(req.getPostData());
			post.setEntity(j);
			CloseableHttpResponse response = httpClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for(String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			String result = builder.toString();
			req.setResponse(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
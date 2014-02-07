package pandora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RequestHandler {
	
	private static final String _url = "http://tuner.pandora.com/services/json/?";

	public static void sendRequest(Request req) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(_url + req.getParams());
		post.addHeader("Content-Type", "text/plain;charset=UTF-8");
		try {
			StringEntity j = new StringEntity(req.getPostData());
			post.setEntity(j);
			CloseableHttpResponse response = httpclient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for(String line = null; (line = reader.readLine()) != null;) {
				builder.append(line);
			}
			String result = builder.toString();
			req.setRespose(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
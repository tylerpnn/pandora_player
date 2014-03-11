package json.response;

import pandora.ErrorHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonResponse {

	private String stat;
	private int code;

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static <T extends JsonResponse> T loadFromJson(String json, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		T json_t = null;
		try {
			json_t = mapper.readValue(json, type);
		} catch (Exception e) {
			ErrorHandler.logJSON(json);
			e.printStackTrace();
		}
		return json_t;
	}
}

package org.amon;

import org.json.JSONException;
import org.json.JSONObject;

public class AmonStreamBuilder {

	private final JSONObject json = new JSONObject();

	public AmonStreamBuilder(AmonBuilder notice) throws JSONException {
		
	if(notice.isException()){
		json.put("exception_class", notice.getErrorMessage());
		json.put("backtrace", notice.getBacktrace());
		json.put("enviroment", notice.getEnvironmentType().toString());
		json.put("url", notice.getUrl());
		json.put("message", notice.getMessage());
		json.put("data", System.getenv());
	}else{
		json.put("message", notice.getMessage());
		if(notice.getMessageType() != null)
			json.put("level",notice.getMessageType().toString());
		}
		
	}

	public String toString() {
		return json.toString();
	}
}

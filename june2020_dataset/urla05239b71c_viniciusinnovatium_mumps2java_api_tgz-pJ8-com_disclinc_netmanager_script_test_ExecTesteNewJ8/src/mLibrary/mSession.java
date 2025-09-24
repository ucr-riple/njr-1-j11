package mLibrary;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class mSession {
	
	private String sessionId;
	private mData mDataSession = new mData();
	
	public mSession(Map<String, String[]> map) {
		populateParameter(map);
		setSessionId(UUID.randomUUID().toString());
	}

	public Object getSessionId(){
		return sessionId;
	}
	
	public void setSessionId(String sessionId){
		setData("sessionId", 1, sessionId);
		this.sessionId = sessionId;
	}
	
	public void populateParameter(Map<String, String[]> map){
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			for (int i = 0; i < result.getValue().length; i++) {
				mDataSession.subs(result.getKey(), i+1).set(result.getValue()[i]);				
			}
		}
	}	
	
	public mVar getData(Object... args){
		return new mVar(args, mDataSession);
	}
	
	public void setData(Object subs, Object idx, Object value){
		mDataSession.subs(subs, idx).set(value);			
	}

	public Object getLogin(Object object, Object object2, int i) {
		// TODO Auto-generated method stub
		return 1;
	}

	public Object getAppTimeout() {
		//TODO REVISAR TIMEOUT PROVISÓRIO;
		return 1;
	}		
}

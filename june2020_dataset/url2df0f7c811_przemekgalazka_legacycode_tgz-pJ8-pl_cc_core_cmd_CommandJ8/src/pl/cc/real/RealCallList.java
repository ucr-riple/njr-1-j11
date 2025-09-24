package pl.cc.real;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import pl.cc.events.real.RealCallListListener;
import pl.cc.events.real.RealCallListListenerList;
import pl.cc.real.comparator.CallComparatorByUniqueId;

public class RealCallList {
	private static final long serialVersionUID = 3945399966417608377L;
	static Logger log = Logger.getLogger(RealCallList.class);
	
	private RealCallListListenerList callListListenerList  = new RealCallListListenerList();
	private HashMap<String, RealCall> list = new HashMap<String, RealCall>();
	
	
	public void addCallListListener(RealCallListListener callListListener){
		this.callListListenerList.add(callListListener);
	}
	
	public void removeCallListListener(RealCallListListener callListListener){
		this.callListListenerList.remove(callListListener);
	}	
	
	public synchronized void add(RealCall call){
		if (!list.containsKey(call.getUniqueID())) {
			RealCall newCall = new RealCall(call.callerID, call.uniqueID, call.queue,call.crationTime);
			list.put(call.getUniqueID(), newCall);
			callListListenerList.onCallAdd(newCall);
		}
	}

	public synchronized void remove(RealCall call){
		log.debug("Remove CALL:"+call.toString());
		RealCall localCall = list.get(call.getUniqueID());
		if (localCall!=null) {
			list.remove(localCall.uniqueID);
			callListListenerList.onCallRemoved(localCall);
		}
	}
	
	public  synchronized RealCall getByUniqueID(String uniqueID){
		for (RealCall c : list.values()){
			if (c.uniqueID.equals(uniqueID)) return c;
		}
		return null;
	}

	
	public synchronized RealCall getByPosition(int position){
		if ((position<0)||(position>=list.size())) return null;
		RealCall [] l =  new RealCall[0];
		l = list.values().toArray(l);
		Arrays.sort(l, new CallComparatorByUniqueId());
		return (RealCall) l[position];
	}

	public synchronized boolean containsCall(RealCall call) {
		return list.containsKey(call.getUniqueID());
	}

	public synchronized Collection<RealCall> getValues() {
		return list.values();
	}

	public synchronized int size() {
		return list.size();
	}
	
}


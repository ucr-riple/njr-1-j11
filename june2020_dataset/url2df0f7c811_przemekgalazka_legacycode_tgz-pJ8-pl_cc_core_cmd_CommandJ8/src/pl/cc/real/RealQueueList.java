package pl.cc.real;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pl.cc.events.real.RealQueueListListener;
import pl.cc.events.real.RealQueueListListenerList;
import pl.cc.exceptions.CallNotFoundException;


public class RealQueueList  {
	private static final long serialVersionUID = 4718619661018043400L;
	public static final Logger log = Logger.getLogger(RealQueueList.class);

	private RealQueueListListenerList queueListListenerList = new RealQueueListListenerList();
	private  HashMap <String,RealQueue> list =  new HashMap <String,RealQueue>();
	
	
	public void addQueueListListener(RealQueueListListener queueListListener){
		this.queueListListenerList.add(queueListListener);
	}
	
	public void removeQueueListListener(RealQueueListListener queueListListener){
		this.queueListListenerList.remove(queueListListener);
	}
	
	public synchronized RealQueue get(String name){
		return (RealQueue) list.get(name);
	}

	public synchronized RealQueue get(RealQueue queue){
		RealQueue res = get(queue.getName());
		if (res==null) {
			return null;
		} else {
			return (RealQueue)res;
		}
	}
	
	public  synchronized boolean add(RealQueue realQueue){
		if (!list.containsKey(realQueue.getName())){
			log.debug("New queue: "+realQueue.toString());
			list.put(realQueue.getName(), realQueue);
			queueListListenerList.onQueueAdd(realQueue);
			return true;
		} else {
			get(realQueue.getName()).setWaitCount(realQueue.getWaitCount());
			return false;
		}
	}
	
	public  synchronized RealQueue remove(String name){
		Object res =  list.remove(name);
		if (res!=null){
			log.debug(res);
			queueListListenerList.onQueueRemoved((RealQueue)res);
			return (RealQueue) res;
		} else {
			log.debug("null");
			return null;
		}
	}
	

	public synchronized void clear(){
		list.clear();
		queueListListenerList.onClear();
	}
	
	public synchronized boolean addAgentToQueue(RealAgent agent, RealQueue queue){
		boolean res  = queue.addAgent(agent);
		//RealAgent res =  queue.getAgentList().put(agent.getId(), agent);
		log.debug("Agent "+agent+" added to: "+queue);
		return res;
	}


	public synchronized List <RealAgent> getAgentsByID(Integer agentID) {
		ArrayList <RealAgent> agentList = new ArrayList <RealAgent>();
		for (RealQueue queue : list.values()) { 
			RealAgent agent =  queue.getAgentByID(agentID);
			if (agent!=null){
				agentList.add(agent);
			}
		}
		return agentList;
	}

	public synchronized RealCall getCallByUniqueID(String uniqueID){
		for (RealQueue queue : list.values()) {
			RealCall c = queue.getCallList().getByUniqueID(uniqueID);
			if (c!=null) return c;
		}
		return null;
	}
	
	/**
	 * Sprawdzenie w której kolejce było to połączenie
	 * @param call
	 * @return
	 * @throws CallNotFoundException - jeśli nie znaleziono połączenia w żadnej z kolejeg
	 */
	public synchronized RealQueue findQueueForCall(RealCall call) throws CallNotFoundException{
		for(RealQueue queue : list.values()){
			if (queue.containsCall(call)) return queue; 
		}
		throw new CallNotFoundException(call);
	}

	public synchronized int size() {
		return list.size();
	}

	public synchronized Collection<RealQueue> getValues() {
		return list.values();
	}

	public synchronized boolean isPausedInAllQueues() {
		for (RealQueue queue : list.values()){
			if (! queue.isPaused()) return false;
		}
		return true;
	}

	public synchronized void remove(RealCall agentCall) {
		for (RealQueue queue : list.values()){
			queue.callsList.remove(agentCall);
		}
	}
	
	
	
}

package pl.cc.real;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import pl.cc.core.AgentStatusInQueue;
import pl.cc.events.real.RealAgentListListener;
import pl.cc.events.real.RealAgentListListenerList;
import pl.cc.real.comparator.AgentComparatorByName;

public class RealAgentList  {
	public static final Logger log = Logger.getLogger(RealAgentList.class);
	private RealAgentListListenerList queueAgentListenerList = new RealAgentListListenerList();

	private Map <Integer,RealAgent>list = new HashMap <Integer,RealAgent>();
	
	public RealAgentList() {
	}
	
	public RealAgentList(RealAgentList agentList) {
		for (RealAgent ra : agentList.getValues()){
			add(new RealAgent(ra));
		}
	}

	public synchronized Collection<RealAgent> getValues() {
			return list.values();
	}

	public void addAgenteListListener(RealAgentListListener realAgentListListener){
		this.queueAgentListenerList.add(realAgentListListener);
	}
	
	public void removeAgentListListener(RealAgentListListener realAgentListListener){
		this.queueAgentListenerList.remove(realAgentListListener);
	}
	
	public synchronized boolean add(RealAgent realAgent){
		if (!list.containsKey(realAgent.getId())){
			list.put(realAgent.getId(), realAgent);
			queueAgentListenerList.onAgentAdd(realAgent);
			return true;
		} else {
			// update ?!
			//get(realQueue.getName()).setWaitCount(realQueue.getWaitCount());
			return false;
		}
	}

	public synchronized RealAgent remove(RealAgent realAgent){
		return remove(realAgent.getId());
	}
	
	public synchronized RealAgent remove(Object key){
		Object res =  list.remove((Integer)key);
		if (res!=null){
			log.debug(res);
			queueAgentListenerList.onAgentRemoved((RealAgent)res);
			return (RealAgent) res;
		} else {
			log.debug("null");
			return null;
		}
	}

	public synchronized void clear(){
		list.clear();
		queueAgentListenerList.onClear();
	}
	
	public synchronized RealAgent get(RealAgent agent){
		return list.get(agent.getId());
	}
		
	public synchronized boolean addAgent(RealAgent agent){
		if (!list.containsKey(agent.getId())) {
			list.put(agent.getId(), agent);
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized int getPositionByName(RealAgent agent){
		return getPosition(agent, new AgentComparatorByName());
	}
	
	public synchronized int getPosition(RealAgent agent, Comparator<RealAgent> comparator){
		RealAgent [] l =  new RealAgent[0];
		l = list.values().toArray(l);
		Arrays.sort(l, comparator);
		for (int i =0 ;i<l.length; i++){
			RealAgent a = (RealAgent)l[i];
			if (a.getId().equals(agent.getId())) return i;
		}
		return 0;
	}
	
	public synchronized RealAgent getByPosition(int position, Comparator<RealAgent> comparator){
		if ((position<0)||(position>=list.size())) return null;
		RealAgent [] l = new RealAgent[0];
		l = list.values().toArray(l);
		Arrays.sort(l, comparator);
		return (RealAgent) l[position];
	}
	
	public synchronized RealAgent getByExtension(String extension){
		for (RealAgent a : list.values() ){
			if (a.getExtension().endsWith(extension)) return a;
		}
		return null;
	}

	public synchronized RealAgent get(Integer agentID) {
		return list.get(agentID);
	}

	public synchronized void onCallLeave(RealCall call, RealQueue queue) {
		for(RealAgent agent : list.values()){
			agent.onCallLeave(call, queue);
		}
	}

	public synchronized int getFreeAgentCount() {
		int count = 0;
		for(RealAgent realAgent :list.values()){
			if ((realAgent.connectedCall==null)&& (!realAgent.isPaused())&&(realAgent.status!=AgentStatusInQueue.IN_USE)){
				count++;
			}
		}
		return count;
	}

	public int getPausedAgentCount() {
		return getPausedAgentCount(-1);
	}
	
	public synchronized int getPausedAgentCount(int idDefault) {
		int count = 0;
		for(RealAgent realAgent :list.values()){
			if (realAgent.isPaused() && (idDefault==-1 || realAgent.getPauseType().getId()==idDefault)){
				count++;
			}
		}
		return count;
	}
	
	
	public synchronized int getTalkingAgentCount() {
		int count = 0;
		for(RealAgent realAgent :list.values()){
			if (realAgent.connectedCall!=null){
				count++;
			}
		}
		return count;
	}

	public synchronized boolean containsValue(RealAgent agent) {
		return list.containsValue(agent);
	}

	public synchronized RealAgent findAgentConnectedWithCall(RealCall call) {
		for (RealAgent realAgent : list.values()){
			if (call.equals(realAgent.getConnectedCall())){
				return realAgent;
			}
		}
		return null;
	}

	public synchronized int size() {
		return list.size();
	}


}

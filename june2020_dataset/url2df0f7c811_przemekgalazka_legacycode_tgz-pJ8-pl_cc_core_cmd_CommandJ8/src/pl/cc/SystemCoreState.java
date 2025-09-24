package pl.cc;

import java.util.Date;

import org.apache.log4j.Logger;

import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.PauseType;
import pl.cc.core.PauseTypeList;
import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.EventAgentAddToQueue;
import pl.cc.core.cmd.EventAgentComplete;
import pl.cc.core.cmd.EventAgentConnect;
import pl.cc.core.cmd.EventAgentHangup;
import pl.cc.core.cmd.EventAgentLogin;
import pl.cc.core.cmd.EventAgentLogout;
import pl.cc.core.cmd.EventAgentNewCall;
import pl.cc.core.cmd.EventAgentPause;
import pl.cc.core.cmd.EventAgentQueueMember;
import pl.cc.core.cmd.EventAgentRemoveFromQueue;
import pl.cc.core.cmd.EventAgentUnPause;
import pl.cc.core.cmd.EventQueueJoin;
import pl.cc.core.cmd.EventQueueLeave;
import pl.cc.core.cmd.InfoAgent;
import pl.cc.core.cmd.InfoPauseDefinition;
import pl.cc.core.cmd.InfoPrivilege;
import pl.cc.core.cmd.InfoQueue;
import pl.cc.core.cmd.dummy.Loggedout;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.exceptions.CallNotFoundException;
import pl.cc.exceptions.QueueNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealAgentList;
import pl.cc.real.RealCall;
import pl.cc.real.RealCallList;
import pl.cc.real.RealQueue;
import pl.cc.real.RealQueueList;

/**
 * Budujemy stan wnętrza systemu na podstawie komunikatów płynących z CCProxy
 * - Lista zalogowanych agentów
 * - Lista kolejek w systemie
 * 
 * @since 2009-05-21
 */
public class SystemCoreState {
	public static final Logger log = Logger.getLogger(SystemCoreState.class);

	private RealQueueList qList = new RealQueueList();
	private RealAgentList aList = new RealAgentList();
	private RealCallList callList = new RealCallList();
	
	/**
	 * Lista agentów do których mamy uprawnienia
	 */
	private RealAgentList privAgentList = new RealAgentList();
	/**
	 * Lista zdefiniowanych pauz w systemie. Lista spływa z CCProxy
	 */
	private PauseTypeList pauseTypeList = new PauseTypeList();
	
	public SystemCoreState(){
		log.info("======== Creating new instance SystemCoreState ========");
		// FIXME: TESTY - testy listenerów dla CallList
		/*
		RealCallListObserver observer = new RealCallListObserver("GLOBAL");
		callList.addCallListListener(observer);
		
		qList.addQueueListListener(new RealCallListObserver("BLUZECZKA"));
		*/
		// koniec testów
	}

	public SystemCoreState(PauseTypeList pauseTypeList){
		this();
		this.pauseTypeList = pauseTypeList; 
	}

	
	/**
	 * @return Lista agentów zalogowanych do systemu
	 */
	public RealAgentList getLoggedAgentList(){
		return aList;
	}
	/**
	 * @return Lista kolejek w systemie
	 */
	public RealQueueList getQueueList(){
		return qList;
	}

	/**
	 * @return Poł
	 */
	public RealCallList getCallList(){
		return callList;
	}

	/**
	 * @return Lista defninicji pauz używanych w systemie
	 */
	PauseTypeList getPauseTypeList() {
		return pauseTypeList;
	}
	/**
	 * @param agent Czy mamy uprawnienie do wybranego agenta
	 * @return true jeśli mamy uprawnienie
	 */
	public boolean hasPrivilegeForAgent(RealAgent agent){
		return privAgentList.containsValue(agent);
	}
	
	/**
	 * @param cmd - Komenda która napłynęła z CCProxy
	 * @param pt ProxyTasks - namiastka połączenia do CCProxy - czasem agenci sami inicjują jakieś akcje w CCProxy (np kliknięcię Spy)
	 * @return true - jeśli komenda została faktycznie przetworzona, false jeśli nie byliśmy nią zainteresowani
	 */
	public boolean process(Command cmd, ProxyTasks pt){
		log.debug("Process: "+cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
		
		if (cmd instanceof InfoAgent){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			InfoAgent e = (InfoAgent)cmd;
			try {
				addAgentToQueue(e.getQueue(), e.getAgent(), pt);
				RealQueue realQueue = findQueue(e.getQueue());
				realQueue.updateAgentCounts();
			} catch (QueueNotFoundException ex) {
				log.error(ex.getMessage());
			} 

		} else if (cmd instanceof EventAgentAddToQueue){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentAddToQueue e = (EventAgentAddToQueue)cmd;
			try {
				addAgentToQueue(e.getQueue(), new RealAgent(findAgent(e.getAgent())), pt);
				RealQueue realQueue = findQueue(e.getQueue());
				realQueue.updateAgentCounts();
			} catch (QueueNotFoundException ex) {
				log.error(ex.getMessage());
			} catch (AgentNotFoundException ex) {
				log.error(ex.getMessage());
			} 
		} else 	if (cmd instanceof InfoQueue){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			InfoQueue iq = (InfoQueue)cmd;
			RealQueue rq = new RealQueue(iq.getQueue());
			qList.add(rq);

		} else if (cmd instanceof Loggedout){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			qList.clear();
			aList.clear();
			
		} else if (cmd instanceof EventAgentLogin){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			RealAgent a = ((EventAgentLogin) cmd).getAgent();
			RealAgent ra = new RealAgent(a,pt);
			aList.add(ra);
		} else if (cmd instanceof EventAgentLogout){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			try {
				RealAgent a = findAgent(((EventAgentLogout) cmd).getAgent());
				
				aList.remove(a.getId());
				// Jeśli agent był skojażony z jakąś rozmową (dzwoni/rozmawia) usuń też info o tej rozmowie
				RealCall agentCall = a.getCall();
				if (agentCall!=null){
					// główna lista połączeń
					callList.remove(agentCall);
					// listy połączeń per kolejka
					qList.remove(agentCall);
				}
			} catch (AgentNotFoundException e) {
				e.getMessage();
			}	
		} else if (cmd instanceof EventAgentRemoveFromQueue){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentRemoveFromQueue e = (EventAgentRemoveFromQueue)cmd;
			try {
				removeAgentFromQueue(e.getQueue(), e.getAgent(), pt);
				RealQueue realQueue = findQueue(e.getQueue());
				realQueue.updateAgentCounts();
			} catch (QueueNotFoundException e1) {
				log.warn(e1.getMessage());
			}
		} else if (cmd instanceof EventAgentNewCall){
			// Agent otrzymuje nowe połączenie w ramach konkretnej kolejki ( RINGING ) 
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			
			EventAgentNewCall e = (EventAgentNewCall)cmd;
			try {
				RealAgent agent = findAgent(e.getAgent());
				RealAgent agentInQueue = findAgent(e.getAgent(), e.getQueue());
				RealCall call = e.getCall();
				log.debug("EventAgentNewCall GLOBAL LIST");
				agent.onAgentNewCall(call, e.getQueue());
				log.debug("EventAgentNewCall QUEUE: "+e.getQueue());
				agentInQueue.onAgentNewCall(call, e.getQueue());
			} catch (AgentNotFoundException e1) {
				log.error(e1.getMessage());
			} catch (QueueNotFoundException e2) {
				log.error(e2.getMessage());
			}

		} else if (cmd instanceof EventAgentConnect){
			// Agent rozpoczyna rozmowę w ramach konkretnej kolejki
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentConnect e = (EventAgentConnect) cmd;
			try {
				RealAgent agent = findAgent(e.getAgent());
				RealAgent agentInQueue = findAgent(e.getAgent(), e.getQueue());
				RealCall call = e.getCall();
				log.debug("EventAgentConnect GLOBAL LIST");
				agent.onAgentConnect(call, e.getQueue());
				log.debug("EventAgentConnect QUEUE: "+e.getQueue());
				agentInQueue.onAgentConnect(call, e.getQueue());
				RealQueue realQueue = findQueue(e.getQueue());
				realQueue.updateAgentCounts();
				
				// dodać połączenie do listy globalnej + listy połączeń w kolejce
				addCallToList(e.getCall(), e.getQueue());
				
				// Zdażenie połączenia agenta i połączenia w GLOBAL
				RealCall localCall = findCall(e.getCall());
				localCall.agentConnect(agent,realQueue);
				// Zdażenie połączenia agenta i połączenia w ramach połączeń kolejki
				RealCall localCallInQueue = findCall(e.getCall(), realQueue);
				localCallInQueue.agentConnect(agent,realQueue);

			} catch (AgentNotFoundException e1) {
				log.error(e1.getMessage());
			} catch (QueueNotFoundException e2) {
				log.error(e2.getMessage());
			} catch (CallNotFoundException e3) {
				log.error(e3.getMessage());
			}
			
		} else if (cmd instanceof EventAgentHangup){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentHangup e = (EventAgentHangup)cmd;
			callCompleded(e.getAgent(),e.getCall());

		} else if (cmd instanceof EventAgentComplete){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentComplete e = (EventAgentComplete)cmd;
			callCompleded(e.getAgent(),e.getCall());
			
		} else if (cmd instanceof EventQueueLeave){
			// połączenie wyszło z kolejki (zostało odebrane przez agenta lub przestało dzwonić)
			// przeglądnij wszystkich agentów w tej kolejce i daj im hangeup dla tej rozmowy
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventQueueLeave e = (EventQueueLeave)cmd;
			RealQueue queue = null;
			try {
				queue = findQueue(e.getQueue());
				queue.onQueueLeave(e.getCall());
				aList.onCallLeave(e.getCall(), queue);
			} catch (QueueNotFoundException e1) {
				log.warn(e1.getMessage());
			}

			// usuń połączenie z globalnej listy połączeń
			RealCall localCall = null; 
			try {
				localCall = findCall(((EventQueueLeave)cmd).getCall());
				callList.remove(localCall);
			} catch (CallNotFoundException e2) {
			}
			// usunąć je do listy połaczeń w kolejce
			if (queue != null && localCall !=null){
				queue.getCallList().remove(localCall);
			}
			
		} else if (cmd instanceof EventAgentPause){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			try {
				RealAgent agent = findAgent(((EventAgentPause) cmd).getAgent());
				String pauseType = ((EventAgentPause)cmd).getPauseType();
				PauseType pType = pauseTypeList.findByValue(pauseType);
                int pauseTime = ((EventAgentPause)cmd).getPauseTime();
                int timePoor = ((EventAgentPause)cmd).getTimePoor();
                int timeBad = ((EventAgentPause)cmd).getTimeBad();
				agent.setPaused(true, pType, pauseTime, timePoor, timeBad);
				Date since=((EventAgentPause)cmd).getSince();
				if (since!=null) agent.setLastStateChange(since);
				// Dystrybucja po wszyskich kolejkach
				RealQueueList queueList = findQueuesForAgent(agent);
				synchronized (queueList) {
					for (RealQueue queue : queueList.getValues()){
						try {
							RealAgent agentInQueue = findAgent(agent, queue);
							agentInQueue.setPaused(true, pType, pauseTime, timePoor, timeBad);
							RealQueue realQueue = findQueue(queue);
							realQueue.updateAgentCounts();
						} catch (QueueNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (AgentNotFoundException e) {
				log.error(e.getMessage());
			}
		
		} else if (cmd instanceof EventAgentUnPause){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			try {
				RealAgent agent = findAgent(((EventAgentUnPause) cmd).getAgent());
				agent.setPaused(false, null);
				// Dystrybucja po wszyskich kolejkach
				Date since=((EventAgentUnPause)cmd).getSince();
				if (since!=null) agent.setLastStateChange(since);
				RealQueueList queueList = findQueuesForAgent(agent);
				synchronized (queueList) {
					for (RealQueue queue : queueList.getValues()){
						try {
							RealAgent agentInQueue = findAgent(agent, queue);
							agentInQueue.setPaused(false, null);
							RealQueue realQueue = findQueue(queue);
							realQueue.updateAgentCounts();
						} catch (QueueNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (AgentNotFoundException e) {
				log.error(e.getMessage());
			}
		} else if (cmd instanceof EventAgentQueueMember){
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			EventAgentQueueMember e = (EventAgentQueueMember)cmd;
			try {
				/*
				 * FIXME: marek wysyła extension zamiast id
				 * Docelowo zdecydować sie czy przesyłamy id czy extension
				 * RealAgent ra = findAgent(e.getAgent(),e.getQueue());
				 */
				RealAgent ra;
				ra = (RealAgent) aList.getByExtension(e.getAgent().getId().toString());
				if (ra==null){
					log.debug("Nie znaleziono agenta (szukając po ext): "+e.getAgent().getId());
					ra = aList.get(e.getAgent());
				}
				if (ra == null){
					log.warn("Nie znaleziono agenta o id/exten: "+e.getAgent().getId());
					return false;
				}
				AgentStatusInQueue status = e.getStatus();

				try {
					// Zwyky agent nie będący supervisorem nie otrzymuje informacji o kolejkach
					// FIXME: docelowo powinien te informacje otrzymać
					ra = findAgent(ra, e.getQueue());
					log.debug("EventAgentQueueMember - TRANSLATED agentId: "+ra.getId());
					ra.onChangeStatusAsQueueMember(e.getQueue(), status);
					
				} catch (AgentNotFoundException e1) {
				} catch (QueueNotFoundException e2) {
				}
				// lista zalogowanych agentów 
				RealAgent rootAgent = findAgent(ra);
				
				rootAgent.onChangeStatusAsQueueMember(e.getQueue(), status);
				
				if (status==AgentStatusInQueue.NOT_INUSE){
					RealCall call = new RealCall("","123");
					rootAgent.onHangeup(call);
				}
				
				// ilość wolnych
				RealQueue realQueue = findQueue(e.getQueue());
				realQueue.updateAgentCounts();
			} catch (AgentNotFoundException e1) {
				log.error(e1.getMessage());
			} catch (QueueNotFoundException e2) {
				log.error(e2.getMessage());
			}
			
		} else if (cmd instanceof EventQueueJoin){
			// połączenie wpadło do kolejki
			log.debug(cmd.getClass().getSimpleName()+" | "+cmd.getOrginalLine());
			RealCall c = ((EventQueueJoin)cmd).getCall();
			RealQueue q = ((EventQueueJoin)cmd).getQueue();
			addCallToList(c, q);
			
		} else if (cmd instanceof InfoPrivilege){
			RealAgent agent = ((InfoPrivilege)cmd).getAgent();
			boolean hasPriv = ((InfoPrivilege)cmd).getPermission();
			if (hasPriv){
				privAgentList.add(agent);
			}
		} else if (cmd instanceof InfoPauseDefinition){
			PauseType pauseType = ((InfoPauseDefinition)cmd).getPauseType();
			pauseTypeList.add(pauseType);
		} else {	
			return false;
		}
		return true;
	}

	/**
	 * Wspólna obsługa zdażeń EventAgentComplete i EventAgentHangup
	 * @param agent2
	 * @param call2
	 */
	private void callCompleded(final RealAgent agent2, final RealCall call2 ){
		try {
			
			log.debug("Completing call: "+call2.toString());

			log.debug("Call list before: ");
			for (RealCall c : callList.getValues()){
					log.debug(c.toString());
					}
			
			
			RealAgent agent = findAgent(agent2);
			log.debug("EventAgentComplete GLOBAL LIST");
			agent.onHangeup(call2);
			
			RealQueueList queueList = findQueuesForAgent(agent2);
			log.debug("Agent "+agent.toString()+" znajduje się w kolejkach sztuk: "+queueList.size());
			// EventAgentHangup - dla każdej z kolejek w której jest ten agent 
			// ponieważ nie wiemy w jakiej kolejce 
			// poniewaďż˝ nie wiemy w jakiej kolejce zakoďż˝czyďż˝o siďż˝ poďż˝ďż˝czenie
			// EventAgentHangup - dla kaďż˝dej z kolejek w ktďż˝rej jest ten agent
			// poniewaďż˝ nie wiemy w jakiej kolejce zakoďż˝czyďż˝o siďż˝ poďż˝ďż˝czenie
			synchronized (queueList) {
				for (RealQueue queue : queueList.getValues()){
					
					log.debug("Call list before: "+queue.getName());
					for (RealCall c : queue.getCallList().getValues()){
							log.debug(c.toString());
							}

					
					RealAgent agentInQueue = findAgent(agent, queue);
					log.debug("EventAgentComplete QUEUE: "+queue);
					agentInQueue.onHangeup(call2);
					log.debug("Call list after: "+queue.getName());

					for (RealCall c : queue.getCallList().getValues()){
							log.debug(c.toString());
							}
					
					RealQueue realQueue = findQueue(queue);
					realQueue.updateAgentCounts();
				}
			}
		} catch (AgentNotFoundException e1) {
			log.error(e1.getMessage());
		} catch (QueueNotFoundException e2) {
			log.error(e2.getMessage());
		}
		
		// jesli któryś z agentów jest połączony (po agentConnect) z tym polączeniem wywołaj onHangeup
		try {
			log.debug("try 1");
			
			RealQueue q = qList.findQueueForCall(call2);
			RealCall localCall = findCall(call2);
			localCall.agentHangeup(agent2, q);
			log.debug("localCall.agentHangeup(agent2, q)");
			RealCall callInQueue = findCall(call2, q);
			callInQueue.agentHangeup(agent2, q);
			log.debug("callInQueue.agentHangeup(agent2, q)");
		} catch (CallNotFoundException e2) {
			e2.printStackTrace();
			log.warn(e2.getMessage());
		} catch (QueueNotFoundException e3) {
			log.warn(e3.getMessage());
		}
					
		// usunięcie połączenia z global listy i listy połączeń w kolejce
		try {
			log.debug("try 2");
			RealCall localCall = findCall(call2);
			callList.remove(localCall);
			RealQueue realQueue = qList.findQueueForCall(call2);
			realQueue.getCallList().remove(call2);

		} catch (CallNotFoundException e1) {
			//log.debug(e1);
		}
		log.debug("Call list after: ");
		for (RealCall c : callList.getValues()){
				log.debug(c.toString());
				}
	}
	
	/**
	 * Dodanie połączenia do:
	 * - globalnej listy połączeń
	 * - listy połączeń w kolejce
	 * @param call
	 * @param queue
	 */
	private void addCallToList(RealCall call, RealQueue queue){
		log.debug("addCallToList: "+call+" "+queue);
		try {
			RealQueue localQueue = findQueue(queue);
			call.setQueue(localQueue);
			// globalna lista połączeń
			callList.add(call);
			// lista połączeń per kolejka		
			localQueue.getCallList().add(call);
			callList.add(call);
		} catch (QueueNotFoundException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Dodanie agenta do kolejki
	 * @param queue 
	 * @param agent
	 * @return true jeśli dodano agenta, false jeśli agent jest już w kolejce
	 * @throws QueueNotFoundException nie znaleziono kolejki
	 */
	private boolean addAgentToQueue(RealQueue queue, RealAgent agent, ProxyTasks pt) throws QueueNotFoundException{
		RealQueue realQueue = findQueue(queue);
		RealAgent realAgent = new RealAgent(agent, pt);
		return realQueue.getAgentList().add(realAgent);
	}
	
	/**
	 * Usunięcie agenta z kolejki
	 * @param queue
	 * @param agent
	 * @return Instancja usuniętego agenta, bądź null jeśli nie znaleziono tego w tej kolejcy
	 * @throws QueueNotFoundException jeśli nie znaleziono kolejki
	 */
	private RealAgent removeAgentFromQueue(RealQueue queue, RealAgent agent, ProxyTasks pt) throws QueueNotFoundException{
		RealQueue realQueue = findQueue(queue);
		RealAgent realAgent = new RealAgent(agent, pt);
		return realQueue.getAgentList().remove(realAgent);
		
	}
	
	/**
	 * Znajdź agenta pośród zalogowanych
	 * @param agent agent do wyszukania
	 * @return instancja znalezionego agenta 
	 * @throws AgentNotFoundException - jeśli nie znaleziono agenta
	 */
	public RealAgent findAgent(RealAgent agent) throws AgentNotFoundException{
		RealAgent a = aList.get(agent);
		if (a==null){
			throw new AgentNotFoundException(agent);
		} else {
			return a;
		}
	}
	
	/**
	 * Znajdź agenta pośród zalogowanych
	 * @param id agenta do wyszukania
	 * @return instancja znalezionego agenta 
	 * @throws AgentNotFoundException - jeśli nie znaleziono agenta
	 */
	public RealAgent findAgent(Integer id) throws AgentNotFoundException{
		RealAgent agent = new RealAgent(id);
		return findAgent(agent);
	}
	
	private RealCall findCallInQueues(final RealCall call) throws CallNotFoundException {
		synchronized (qList){
			for(RealQueue queue : qList.getValues()){
				try {
					RealCall callInQueue = findCall(call, queue);
					if (callInQueue != null) return callInQueue;
				} catch (QueueNotFoundException e){
				}
			}
		}
		throw new CallNotFoundException(call);
	}
	
	private RealCall findCall(final RealCall call) throws CallNotFoundException {
		RealCall c= callList.getByUniqueID(call.getUniqueID());
		if (c==null){
			throw new CallNotFoundException(call);
		} else {
			return c;
		}
	}
	
	private RealCall findCall(final RealCall call, final RealQueue queue) throws CallNotFoundException, QueueNotFoundException {
		RealQueue q = findQueue(queue);
		if (q==null) throw new QueueNotFoundException(queue);
		RealCall c= q.getCallList().getByUniqueID(call.getUniqueID());
		if (c==null){
			throw new CallNotFoundException(call);
		} else {
			return c;
		}
	}
	
	/**
	 * Znajdź agenta pośród zalogowanych istniejących kolejej
	 * @param queue do wyszukania
	 * @return instancja znalezionej kolejki
	 * @throws QueueNotFoundException - jeśli nie znaleziono
	 */
	public RealQueue findQueue(RealQueue queue) throws QueueNotFoundException {
		RealQueue foundQueue = qList.get(queue);
		if (foundQueue==null){
			throw new QueueNotFoundException(queue);
		} else {
			return foundQueue;
		}
	}

	/**
	 * Znajdďż˝ agenta poďż˝rďż˝d istniejďż˝cej w systemie
	 * @param agent
	 * @param queue
	 * @return
	 * @throws AgentNotFoundException
	 * @throws QueueNotFoundException
	 */
	public RealAgent findAgent(RealAgent agent, RealQueue queue) throws AgentNotFoundException, QueueNotFoundException{
		RealQueue q = findQueue(queue);
		if (q==null) throw new QueueNotFoundException(queue);
		RealAgent a = (RealAgent) q.getAgentByID(agent.getId());
		if (a==null){
			throw new AgentNotFoundException(agent, queue);
		} else {
			return a;
		}		
	}

	
	public RealQueueList findQueuesForAgent(RealAgent agent){
		RealQueueList list = new RealQueueList();
		synchronized (qList) {
			for(RealQueue queue : qList.getValues()){
				RealAgent a = queue.getAgentByID(agent.getId());
				if (a!=null){
					list.add((RealQueue) queue);
				}
			}
		}
		return list;
	}
	/**
	 * Znajdź agenta połączonego z konkretną rozmową
	 * @param call 
	 * @return null jeśli nie znaleziono
	 */
	public RealAgent findAgentConnectedWithCall(RealCall call) {
		synchronized (qList) {
			for (RealQueue realQueue : qList.getValues()){
				try {
					RealCall realCall = findCall(call, realQueue);
					synchronized (aList) {
						for (RealAgent realAgent : aList.getValues()){
							if (realCall.equals(realAgent.getConnectedCall())){
								return realAgent;
							}
						}
					}
				} catch (CallNotFoundException e) {
				} catch (QueueNotFoundException e) {
				}
			}
			return null;
		}
	}

	/**
	 * Znalezienie definicji typu pauzy za pomocą jej wartości 
	 * @param pauseValue wartość typu: "auto, papierosek, administracyjna"
	 * @return defnicja jeśli odnaleziono lub null jeśli nie
	 */
	public PauseType findPauseTypeByValue(String pauseValue) {
		return pauseTypeList.findByValue(pauseValue);
	}

	public PauseType findAutoPauseDef() {
		return pauseTypeList.getAutoPauseDef();
	}
	
}

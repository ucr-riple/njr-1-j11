package ccproxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import pl.cc.SystemCoreState;
import pl.cc.core.AgentStatusInQueue;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;
import pl.cc.real.RealQueueList;
import pl.cc.utils.Utils;

public class AsteriskQueueList extends ArrayList<AsteriskQueue> {
	org.apache.log4j.Logger log = Logger.getLogger(AsteriskQueueList.class);
	private SystemCoreState coreState;
	
	public AsteriskQueueList (SystemCoreState coreState){
		this.coreState = coreState;
		// Sztuczna kolejka - IVR
		// FIXME: Określić w jakich warunkach ta kolejka ma być dodana
		AsteriskQueue ivr = new AsteriskQueue("IVR","0", "IVR");
		add(ivr);
	}
	
	@Override
	public boolean add(AsteriskQueue queue){
		boolean added = super.add(queue);
		if (added){
			log.debug("Adding new queue to coreState. Queue: "+queue.nazwa+" AsteriskQueueList: "+this+" size:"+size()+" coreState:"+coreState);
			RealQueue realQueue = new RealQueue(queue.nazwa, queue.ilosc);
			realQueue.setCustomType(queue.customType);
			ResultSet rs=null;
			if (CCproxy.p != null){
				rs = CCproxy.p.dbConn
					.query("select wrapup, wrapup_time_poor, wrapup_time_bad, talk_time_poor, talk_time_bad from t_queue where nazwa='" + queue.nazwa
							+ "'");
			try {
				if (rs.next()) {
					realQueue.setWrapup(rs.getInt("wrapup"));
                    realQueue.setWrapupTimePoor(rs.getInt("wrapup_time_poor"));
                    realQueue.setWrapupTimeBad(rs.getInt("wrapup_time_bad"));
                    realQueue.setTalkTimePoor(rs.getInt("talk_time_poor"));
                    realQueue.setTalkTimeBad(rs.getInt("talk_time_bad"));
				}
			} catch (SQLException e) {
			}
			}
			coreState.getQueueList().add(realQueue);
		}
		return added;
	}

    public boolean updateFromDb(){

       boolean isOk=true;
       ResultSet rs;
       for (int i=0; i<size();i++){
           AsteriskQueue queue = get(i);
                rs = CCproxy.p.dbConn
                        .query("select wrapup, wrapup_time_poor, wrapup_time_bad, talk_time_poor, talk_time_bad from t_queue where nazwa='" + queue.nazwa
                                + "'");
                try {
                    if (rs.next()) {
                        RealQueue realQueue = coreState.getQueueList().get(queue.nazwa);
                        realQueue.setWrapup(rs.getInt("wrapup"));
                        realQueue.setWrapupTimePoor(rs.getInt("wrapup_time_poor"));
                        realQueue.setWrapupTimeBad(rs.getInt("wrapup_time_bad"));
                        realQueue.setTalkTimePoor(rs.getInt("talk_time_poor"));
                        realQueue.setTalkTimeBad(rs.getInt("talk_time_bad"));
                    }
                } catch (SQLException e) {
                    isOk=false;
                }
            }

        return isOk;
    }


	public void updateQueue(String qname, String ilosc) {
		for (int i = 0; i < size(); i++) {
			if (get(i).nazwa.equals(qname)) {
				get(i).ilosc = ilosc;
				return;
			}
		}
		this.add(new AsteriskQueue(qname, ilosc));		
	}
	/**
	 * wylistowanie agentow
	 * 
	 * @param ac
	 */
	public void printAllLogedAgents(final AgentConnection ac) {
		log.info("printAllLogedAgents to agent: " + ac.agentNumber);
		int selfId = new Integer(ac.agentNumber);
		synchronized(coreState.getLoggedAgentList()){
			for (RealAgent realAgent : coreState.getLoggedAgentList().getValues()) {
				if (realAgent.getId().equals(selfId)){
					continue;
				}
				log.info("Sending login, pause info for: "+realAgent);
				String agentLogin = "EVENT [Agent], Action [Login], Agent ["
						+ realAgent.getId() + "], Exten ["
						+ realAgent.getExtension() + "]" + ", Name ["
						+ realAgent.getName() + "]";
				ac.out.println(agentLogin);
	
				if (realAgent.isPaused()) {
					String pauseType = ac.p.pauseTypeList.getDefault().getName();
					if (realAgent.getPauseType() != null) {
						pauseType = realAgent.getPauseType().getName();
					} else {
												
					}
					String agentPaused = "EVENT [Agent], Action [Pause], "
							+ "Agent [" + realAgent.getId() + "], Exten ["
							+ realAgent.getExtension() + "], Name ["
							+ realAgent.getName() + "], " + "Type [" + pauseType
							+"], " + "Since [" + Utils.formatDate( realAgent.getLastStateChange())
							+ "]";
					ac.out.println(agentPaused);
				} else {
					String agentUnPaused = "EVENT [Agent], Action [Unpause], "
							+ "Agent [" + realAgent.getId() + "], Exten ["
							+ realAgent.getExtension() + "], Name ["
							+ realAgent.getName()
							+"], " + "Since [" + Utils.formatDate( realAgent.getLastStateChange())
							+ "]";
					ac.out.println(agentUnPaused);
				}
				if (realAgent.getStatus()!=AgentStatusInQueue.NOT_INUSE) {
					RealQueueList agentQueues = coreState.findQueuesForAgent(realAgent);
					int paused;
					if (realAgent.isPaused()){
						paused=1;
					} else {
						paused=0;
					}
					for (RealQueue queue : agentQueues.getValues()){
						String queueStatus = "EVENT [Agent], Action [QueueMember], " +
								"Queue ["+queue.getName()+"], Agent ["+realAgent.getId()+"], Paused ["+paused+"], Penalty ["+0+"], " +
								"Status ["+realAgent.getStatus().getStatus()
								+"], " + "Since [" + Utils.formatDate( realAgent.getLastStateChange())
								+ "]";
						ac.out.println(queueStatus);
					}
				}
			}
		}
	}
	
	public void printAgentQueuesToAgent(AgentConnection ac) {
		String qname;
		String ilosc;
		showQueues();
			/*
			 * wylistowanie wszystkich kolejek z pamieci
			 */
			/*
			 * wylistowanie agentow w kolejkach
			 */
			for (int i = 0; i < size(); i++) {
				qname = get(i).nazwa;
				ilosc = get(i).ilosc;
				ac.out.println("+INFO Queue '" + qname + "', Count '" + ilosc
						+ "'");
				for (int j = 0; j < get(i).agenci.size(); j++) {
					ac.out.println("+INFO Queue '" + qname + "', Agent '"
							+ get(i).agenci.get(j).ac.agentNumber
							+ "', Exten '" + get(i).agenci.get(j).ac.agentExten
							+ "', Name: '" + get(i).agenci.get(j).ac.agentName
							+ "'");
				}
				
			}
			
			/* wylistowanie stanów agentów jak się da*/
			try {
				Thread.sleep(0);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		log.info("End listing queues");
	}

	public void printAllQueuesToAgent(AgentConnection ac) {
		String qname;
		String ilosc;
		showQueues();
		ResultSet rs;
		String predefined;
		int wrapup;
		
		for (int i = 0; i < size(); i++) {
			qname = get(i).nazwa;
			ilosc = get(i).ilosc;
			predefined = "false";
			wrapup=0;
			boolean pausedInQueue = ac.newConnected && ac.p.c.pauseWhenStartup;
				rs = ac.p.dbConn
						.query("select nazwa from v_agent_queue where numer='"
								+ ac.agentNumber + "' and nazwa='" + qname
								+ "'");
				try {
					if (rs.next()) {
						predefined = "true";
					}
				} catch (SQLException e) {
				}
				rs = ac.p.dbConn
						.query("select wrapup from t_queue where nazwa='" + qname
								+ "'");
				try {
					if (rs.next()) {
						wrapup=rs.getInt("wrapup");
					}
				} catch (SQLException e) {
				}

				String info ="+INFO Queue '" + qname + "', Count '" + ilosc
				+ "', Predefined '" + predefined + "', " +
					"Paused '"+pausedInQueue+"'" + ", Wrapup '" + wrapup + "'";
				AsteriskQueue queue = get(i);
				
				if (queue.customType!=null) {
					info+=", CustomType '"+queue.customType+"'";
				}
				
			ac.out.println(info);
		}

	}

	public void showQueues() {
		QueueMemberList ac;
		String agentString[];
		String qname;
		String ilosc;
		// log.info("Start listing queues");
		for (int i = 0; i < size(); i++) {
			qname = get(i).nazwa;
			ilosc = get(i).ilosc;
			log.info("Kolejka: " + qname + ", ilosc: " + ilosc);
			if ((ac = get(i).agenci) != null) {
				for (int j = 0; j < ac.size(); j++) {
					log.info("        W tej kolejce agent obiekt: "
							+ ac.get(j).ac.agentNumber);
				}
			}
		}
		// log.info("End listing queues");
	}

	public AsteriskQueue getQueueByName(String qname) {
		for (int i = 0; i < size(); i++) {
			if (get(i).nazwa.equals(qname)) {
				return get(i);
			}
		}
		return null;
	}

	public synchronized void updateQueueMember(Parametry p, String qname, String agentExten,
			String paused, String penalty) {

		AsteriskCmd cmd = new AsteriskCmd(p);
		AsteriskQueue a = getQueueByName(qname);
		AgentConnection ac;
		
		log.info("Zdarzenie w kolejce: " + qname + ", dla agenta: "
				+ agentExten);
		if (a != null) {
			log.info("Kolejka istnieje: " + qname);
			log.info("Sprawdzam, czy agent jest zalogowany przez ProxyClient. Agent: "
							+ agentExten);
			// znajduje referencje do agenta, ktory na numer agentNumber
			ac = p.agenci.getAgentConnectionByExten(agentExten);
			if (ac != null) {
				log.info("Agent istnieje w pamieci CCproxy. Agent: "
						+ agentExten);
				//if (paused.equals("1")) //to nie działa
					//ac.paused = true;
				p.log.info("Dodaje agenta do kolejki. Agent: " + agentExten
						+ ", kolejka: " + qname);
				/* jesli go nie ma kolejce dodajemy*/
				if(a.agenci.getQueueMemberByNumer(ac.agentNumber) == null)
					a.agenci.add(new QueueMember(ac, penalty));
			} else {
				p.log
						.info("Nie znalazlem obiektu agenta w ccproxy. Wylogowuje go z Asteriska. Agent: "
								+ agentExten + ", w kolejce: " + qname);
				GadajAsterisk.out.print(cmd.pause(0, "N/A", agentExten, ""));
				GadajAsterisk.out.print(cmd.removeFromQueue(0, "N/A",
						agentExten, qname));
				GadajAsterisk.out.flush();
			}
		} else {
			log.info("Nie znaleziono kolejki: " + qname);
		}
	}

	public synchronized void updateQueueMemberStatus(Parametry p, String qname, String agentExten,
			String paused, String status) {

		AsteriskQueue a = getQueueByName(qname);
		QueueMember qm;
		AgentConnection ac;
		
		log.info("Update statusu agenta w kolejce: " + qname + ", dla agenta: " + agentExten +", status: " + status + ", paused: " +paused);
		
		ac = p.agenci.getAgentConnectionByExten(agentExten);
		if(ac != null){
			qm=a.agenci.getQueueMemberByNumer(ac.agentNumber);
			if(qm != null){
				if(status != null)
					qm.status=status;
				if (paused.equals("1"))
					qm.paused=true;
				else
					qm.paused=false;
			if (p.c.pauseWhenUnavailable && !qm.paused && qm.status.equals("UNAVAILABLE")) //auto pauza na unavailable
			{
				p.log.info("Pausing unavailable agent");
				p.gadajAsterisk.sendMessage(ac, "EVENT [ProxyClient], Action [Pause], Value [true], Type [przerwa], Agent ["+ac.agentNumber+"], Exten ["+ac.agentExten+"]");
			}
			}else{
				log.info("Nie znaleziono agenta w kolejce: " + qname + ", agent: "+ agentExten);
			}
		}else{
			log.info("Nie znaleziono agenta:  " + agentExten); 
		}
	}

	public synchronized void removeQueueMember(Parametry p, String qname, String agentNumber) {

		AsteriskQueue a = getQueueByName(qname);
		AgentConnection ac;
		if (a != null) {

			// obsluga obiektów agentów
			log.info("Obsluga obiektow");
			if (a.agenci.size() > 0) {
				log.info(agentNumber);
				// znajduje referencje do agenta, ktory na numer agentNumber
				ac = p.agenci.getAgentConnectionByNumer(agentNumber);

				if (ac != null) {
					p.log.info("Usuwam obiekt agenta [" + ac.agentNumber
							+ "]z kolejki [" + a.nazwa + "]");

					a.agenci.remove(ac);
				}
			}

		} else {
			log.info("Nie ma kolejki: " + qname);
			// updateQueue(qname, "0");
		}

		// get(i).agenci.add(new AgentConnection())
	}

	public synchronized void removeAgentConnection(AgentConnection agentConnection) {
		QueueMemberList qm;

		for (int i = 0; i < size(); i++) {
			log.info("Sprawdzam kolejke: " + get(i).nazwa);
			if ((qm = get(i).agenci) != null) {
				for (int j = 0; j < qm.size(); j++) {
					log.info("         Agent w kolejcei: "
							+ qm.get(j).ac.agentNumber);
					if (qm.get(j).ac.agentNumber
							.equals(agentConnection.agentNumber)) {
						log.info("         Wylogowuje z kolejki: "
								+ qm.get(j).ac.agentNumber);
						agentConnection.p.gadajAsterisk.sendMessage(
								agentConnection, "removequeue:" + get(i).nazwa,
								false);
						qm.remove(j);
						break;
					}
				}
			}
		}
	}

}

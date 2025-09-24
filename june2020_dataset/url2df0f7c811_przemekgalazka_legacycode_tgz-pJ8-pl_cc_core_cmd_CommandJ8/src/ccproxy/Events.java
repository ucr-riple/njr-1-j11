package ccproxy;

import org.apache.log4j.Logger;

import pl.cc.core.PauseType;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.real.RealAgent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Events {
	org.apache.log4j.Logger log = Logger.getLogger("event");
	Parametry p;

	public Events(Parametry p) {
		this.p = p;
	}

	public void login(AgentConnection ac) {
		String eventMsg = new String("EVENT [Agent], Action [Login], Agent ["
				+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
				+ ", Name [" + ac.agentName + "]" + ", ID [" + ac.idConnection
				+ "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);
		if (ac.isNewConnected() && p.c.pauseWhenStartup){
			String defaultPause = p.pauseTypeList.getDefault().getName();
			String agentPaused = "EVENT [Agent], Action [Pause], " +
					"Agent ["+ac.agentNumber+"], Exten ["+ac.agentExten+"], Name ["+ac.agentName+"], " +
					"Type ["+defaultPause+"]";
			
			p.agenci.pushSuperMessage(agentPaused);	
		}
		
	}

	public void logout(AgentConnection ac) {
		String eventMsg = new String("EVENT [Agent], Action [Logout], Agent ["
				+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
				+ ", Name [" + ac.agentName + "]" + ", ID [" + ac.idConnection
				+ "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);
	}

	public void loginIncorrect(AgentConnection ac) {
		String eventMsg = new String(
				"EVENT [Agent], Action [Incorect login], Agent ["
						+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
						+ ", Name [" + ac.agentName + "]" + ", ID ["
						+ ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void removeQueueFailed(AgentConnection ac) {
		String eventMsg = new String(
				"EVENT [Agent], Action [Remove queue failed], Agent ["
						+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
						+ ", Name [" + ac.agentName + "]" + ", ID ["
						+ ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void pause(AgentConnection ac, String pauseType, int pauseTime, int timePoor, int timeBad) {
		String eventMsg = new String("EVENT [Agent], Action [Pause], Agent ["
				+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
				+ ", Name [" + ac.agentName + "]" + ", ID [" + ac.idConnection+ "]" +
						", Type ["+ pauseType +"], Time [" + pauseTime + "]");
        if (p.pauseTypeList.findById(PauseType.ID_AUTO).getName().equals(pauseType)){
            eventMsg = eventMsg + ", TimePoor ["+timePoor+"], TimeBad ["+timeBad+"]";
        }
		log.info(eventMsg);
        ac.paused=true;
        ResultSet rs;
        rs = p.dbConn.query("select agent_pause_ccproxy('" + ac.agentNumber +"','" + pauseType + "','" + ac.agentExten + "')");
        try {
            if (rs.next()) {
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		p.agenci.pushSuperMessage(eventMsg);
	
	}
	
	public void pause(AgentConnection ac) {
		pause(ac, "unknown", PauseType.PAUSE_FOREVER, 0, 0);
	}

	public void unpause(AgentConnection ac) {
		String eventMsg = new String("EVENT [Agent], Action [Unpause], Agent ["
				+ ac.agentNumber + "], Exten [" + ac.agentExten + "]"
				+ ", Name [" + ac.agentName + "]" + ", ID [" + ac.idConnection
				+ "]");
		log.info(eventMsg);
        ac.paused=false;
        ResultSet rs;
        rs = p.dbConn.query("select agent_pause_ccproxy('" + ac.agentNumber +"'," + "NULL" + ",'" + ac.agentExten +"')");
        try {
            if (rs.next()) {
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.agenci.pushSuperMessage(eventMsg);

	}

	public void addQueue(AgentConnection ac, String qname, String penalty) {
		String eventMsg = new String(
				"EVENT [Agent], Action [AddToQueue], Agent [" + ac.agentNumber
						+ "], Queue [" + qname + "], Exten [" + ac.agentExten
						+ "]" + ", Name [" + ac.agentName + "]" + ", Penalty ["
						+ penalty + "]" + ", ID [" + ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);
	}

	public void removeQueue(AgentConnection ac, String qname) {
		String eventMsg = new String(
				"EVENT [Agent], Action [RemoveFromQueue], Agent ["
						+ ac.agentNumber + "], Queue [" + qname + "], Exten ["
						+ ac.agentExten + "]" + ", Name [" + ac.agentName + "]"
						+ ", ID [" + ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void addQueueFailed(AgentConnection ac, String qname, String reason) {
		String eventMsg = new String(
				"EVENT [Agent], Action [AddToQueueFailed], Agent ["
						+ ac.agentNumber + "], Queue [" + qname + "], Exten ["
						+ ac.agentExten + "]" + "], Reason [" + reason + "]"
						+ ", Name [" + ac.agentName + "]" + ", ID ["
						+ ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void unknownResponse(AgentConnection ac, String asteriskResponse,
			String asteriskCommand) {
		String eventMsg = new String("EVENT [UnknownResponse], Agent ["
				+ ac.agentNumber + "], Command [" + asteriskCommand
				+ "], Response [" + asteriskResponse + "]" + ", Name ["
				+ ac.agentName + "]" + ", ID [" + ac.idConnection + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void joinORleave(String event, String qname, String count,
			String callerid, String uniqueid) {
		String eventMsg = new String("EVENT [Queue], Action [" + event
				+ "], Queue [" + qname + "], Count [" + count + "], Callerid ["
				+ callerid + "], Uniqueid [" + uniqueid + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void queueMemberStatus(String qname, String agentNumber,
			String paused, String penalty, String status) {
		String eventMsg = new String(
				"EVENT [Agent], Action [QueueMember], Queue [" + qname
						+ "], Agent [" + agentNumber + "], Paused [" + paused
						+ "], Penalty [" + penalty + "], Status [" + status
						+ "]");
		log.info(eventMsg);
		String statusinq=null;
		AgentConnection agent = p.agenci.getAgentConnectionByExten(agentNumber);
		if (agent != null)
		{
		try {
			statusinq=p.coreState.findAgent(new RealAgent(agent.agentNumber,null,null)).getStatus().getStatus();
		} catch (AgentNotFoundException e1) {
		}
		}
		if (!(status.equals(statusinq))){
			p.agenci.pushSuperMessage(eventMsg);
		}else {
			p.agenci.pushSuperMessage(eventMsg,true);
		}
		
	}

	public void newCall(String qname, String agentNumber, String exten,
			String callerid, String uniqueid) {
		String eventMsg = new String("EVENT [Agent], Action [NewCall], Agent ["
				+ agentNumber + "], Exten [" + exten + "], Queue [" + qname
				+ "], CallerID [" + callerid + "], Uniqueid [" + uniqueid + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void agentConnect(String qname, String agentNumber, String exten,
			String callerid, String holdtime, String uniqueid) {
		String eventMsg = new String(
				"EVENT [Agent], Action [AgentConnect], Agent [" + agentNumber
						+ "], Exten [" + exten + "], Queue [" + qname
						+ "], CallerID [" + callerid + "], Holdtime ["
						+ holdtime + "]" + ", Uniqueid [" + uniqueid + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void agentComplete(String qname, String agentNumber, String exten,
			String reason, String uniqueid) {
		String eventMsg = new String(
				"EVENT [Agent], Action [AgentComplete], Agent [" + agentNumber
						+ "], Exten [" + exten + "], Queue [" + qname
						+ "], Reason [" + reason + "], " +
						"Uniqueid ["+uniqueid+"]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void hangup(String agentNumber, String exten, String uniqueid) {
		String eventMsg = new String("EVENT [Agent], Action [Hangup], Agent ["
				+ agentNumber + "], Exten [" + exten + "], Uniqueid ["
				+ uniqueid + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}

	public void newSomeCall(String qname, String agentNumber, String callerid,
			String exten, String uniqueid) {
		String eventMsg = new String(
				"EVENT [Agent], Action [SomeNewCall], Agent [" + agentNumber
						+ "], Exten [" + exten + "], Queue [" + qname
						+ "], CallerID [" + callerid + "], Uniqueid ["
						+ uniqueid + "]");
		log.info(eventMsg);
		p.agenci.pushSuperMessage(eventMsg);

	}
}

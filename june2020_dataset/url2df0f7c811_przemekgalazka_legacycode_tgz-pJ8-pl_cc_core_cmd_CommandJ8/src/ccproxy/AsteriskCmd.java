package ccproxy;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class AsteriskCmd {
	org.apache.log4j.Logger log = Logger.getLogger(AsteriskCmd.class);
	org.apache.log4j.Logger loge = Logger.getLogger("event");

	Parametry p = null;

	public AsteriskCmd(Parametry p) {
		this.p = p;
	}

	public boolean login(String idConnection, String agentNumber,
			String agentExten) {
		ResultSet rs;

		log.info("LOGIN request from agent: " + agentNumber + ", extension: "
				+ agentExten);

		rs = p.dbConn.query("select agent_login('" + idConnection + "', '"
					+ agentNumber + "', '" + agentExten + "',"+(p.c.pauseWhenStartup?"true":"false")+")");
			try {
				if ((rs!=null) && rs.next()) {
					p.log.info("Pomyślnie zalogowano zdarzenie do bazy danych");
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				p.log.info("Bład operacji agent_login na bazie danych");
				return false;
			}
		return false;
	}

	public String saveAgentExten(String agentNumber, String agentExten) {
		log.info("DBSet [agentexten] agent: " + agentNumber + ", on exten: "
				+ agentExten);
		return new String("Action: DBPut\r\nFamily: agentexten\r\nKey: "
				+ agentExten + "\r\nVal: " + agentNumber + "\r\n\r\n");
	}

	public String delAgentExten(String agentExten) {
		log.info("DBSet [agentexten] empty for exten: " + agentExten);
		return new String("Action: DBPut\r\nFamily: agentexten\r\nKey: "
				+ agentExten + "\r\nVal: " + "\r\n\r\n");
	}

	public String saveAgentNumber(String agentNumber, String agentExten) {
		log.info("DBSet  [agentnumber] agent: " + agentNumber + ", on exten: "
				+ agentExten);
		return new String("Action: DBPut\r\nFamily: agentnumber\r\nKey: "
				+ agentNumber + "\r\nVal: " + agentExten + "\r\n\r\n");
	}

	public String delAgentNumber(String agentNumber) {
		log.info("DBSet [agentnumber] empty for agent: " + agentNumber);
		return new String("Action: DBPut\r\nFamily: agentnumber\r\nKey: "
				+ agentNumber + "\r\nVal: " + "\r\n\r\n");
	}

	public boolean logout(String idConnection, String agentNumber) {
		ResultSet rs;
		log.info("LOGOUT request from agent: " + agentNumber);
		
			rs = p.dbConn.query("select agent_logout('" + idConnection + "')");
			try {
				if (rs.next()) {
					p.log.info("Pomyślnie wylogowano zdarzenie z bazy danych");
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				p.log.info("Bład operacji agent_logout na bazie danych");
				return false;
			}
		return false;
	}

	public String pause(int acctionID, String agentNumber, String agentExten,
			String pauseType) {
		log.info("PAUSE request from agent: " + agentNumber + ", sip: "
				+ agentExten+", type: "+pauseType);
		return new String("Action: QueuePause\r\n" + "Interface: Sip/"
				+ agentExten + "\r\nPaused: 1\r\n" + "PauseType: " + pauseType
				+ "\r\nActionID: " + acctionID + "\r\n\r\n");
	}

	public String setPause(String agentNumber) {
		log.info("DBSet Set aget as paused for agent: " + agentNumber);
		return new String("Action: DBPut\r\nFamily: agentpause\r\nKey: "
				+ agentNumber + "\r\nVal: 1" + "\r\n\r\n");
	}

	public String setUnpause(String agentNumber) {
		log.info("DBSet Set aget as unpaused for agent: " + agentNumber);
		return new String("Action: DBPut\r\nFamily: agentpause\r\nKey: "
				+ agentNumber + "\r\nVal: 0" + "\r\n\r\n");
	}

	public String unpause(int acctionID, String agentNumber, String agentExten) {
		log.info("UNPAUSE request from agent: " + agentNumber + ", sip: "
				+ agentExten);
		return new String("Action: QueuePause\r\n" + "Interface: Sip/"
				+ agentExten + "\r\nPaused: 0\r\nActionID: " + acctionID
				+ "\r\n\r\n");
	}

	public String addToQueue(int acctionID, String agentNumber,
			String agentExten, String qname, String penalty, boolean paused) {
		String pause = "0";
		if (paused){
			pause="1";
		}
		log.info("ADDQUEUE request from agent: " + agentNumber + ", sip: "
				+ agentExten + " for queue: " + qname+", paused: "+paused);
		return new String("Action: QueueAdd\r\nInterface: Sip/" + agentExten
				+ "\r\nQueue: " + qname + "\r\nPenalty: " + penalty
				+ "\r\nPaused: "+pause+"\r\nActionID: " + acctionID + "\r\n\r\n");
	}

	public String removeFromQueue(int acctionID, String agentNumber,
			String agentExten, String qname) {
		log.info("REMOVEQUEUE request from agent: " + agentNumber + ", sip: "
				+ agentExten + " for queue: " + qname);
		return new String("Action: QueueRemove\r\nInterface: Sip/" + agentExten
				+ "\r\nQueue: " + qname + "\r\nActionID: " + acctionID
				+ "\r\n\r\n");
	}

	public String spy (int acctionID, String agentNumber, String agentExten, String spyAgentId) {
		String spyAgentExten = p.agenci.getAgentConnectionByNumer(spyAgentId).agentExten;
		log.info("SPY request FROM agent: " + agentNumber + ", exten: "+ agentExten+ " FOR agent: "+spyAgentId+", exten: "+spyAgentExten);
		
		String s = "Action: Originate\r\n" +
				"Channel: Sip/"+agentExten+"\r\n" +
				"Application: ChanSpy\r\n" +
				"Data: Sip/"+spyAgentExten+",q"+p.c.spyMode+"\r\n" +
				"CallerID: monitor "+spyAgentExten+"\r\n" +
				"Timeout: "+5000+"\r\n" +
				"Async: true\r\n" +
				"ActionID: " + acctionID + "\r\n\r\n";
		
		return s;
	}
}
package ccproxy;

import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.EventProxyClientPause;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.exceptions.QueueNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class MessageFromAsterisk {
  org.apache.log4j.Logger log = Logger.getLogger(MessageFromAsterisk.class);
  String eventMsg;
  String exten;

  String event = null;
  String response = null;
  Events e;
  boolean showQueueMember = true;
  HashMap<String, String> messageHash = new HashMap<String, String>();

  Parametry p;

  AgentResponseProcessor agentResponsProcessor;


  public MessageFromAsterisk(Parametry params) {
    this(params, new Events(params));
  }


  public MessageFromAsterisk(Parametry p, Events e) {
    this.p = p;
    this.e = e;
    this.agentResponsProcessor = new AgentResponseProcessor(p, e);
  }


  /**
   * @param line Linia otrzymana od Asteriska, ktora nalezy sparsowac.
   */
  public void parseLine(String line) {
    if (line.indexOf(": ") == -1) {
      log.info("Linia od asteriska bez dwukropka: " + line);
      return;
    }
    String key = line.substring(0, line.indexOf(": "));
    String param = line.substring(line.indexOf(": ") + 1);
    param = param.trim();
    if (key.equals("Event"))
      event = param;
    if (param != null)
      messageHash.put(key.toLowerCase(), param);
    else
      messageHash.put(key, "");
  }


  public void showEvent() {
    if (p.c.logAsteriskEvents) {
      Iterator<String> i = messageHash.values().iterator();
      Iterator<String> j = messageHash.keySet().iterator();

      p.log.info("--------Event begin");
      p.log.info(event);
      while (i.hasNext()) {
        p.log.info(j.next() + " -> " + i.next());
      }
      p.log.info("--------Event end");
    }
  }


  private String getHeader(String key) {
    String value = messageHash.get(key.toLowerCase());
    return value;
  }


  public void parseEvent(WiadomoscOdAsteriskaList wiadomosciOdAsteriska) {
    String globalMsg = null;
    String agentNumber;
    String asteriskResponse;
    AgentConnection agentConnection = null;
    String accID;
    boolean knownMsg = false;

		/*
         * Najpierw sprawdzam, czy nie ma odpowiedzi na coďż˝ co wysďż˝aďż˝ agent. W
		 * odpowiedzi zawsze musi byc actionid, bo zawsze je wysylam
		 */
    if ((accID = getHeader("actionid")) != null) {
      WiadomoscOdAsteriska w = new WiadomoscOdAsteriska();
      WiadomoscOdAgenta wAgent;

      w.numer = new Integer(accID).intValue();
      asteriskResponse = getHeader("message");
      if (asteriskResponse == null) {
        p.log.info("Mamy null - return");
        return;
      }
      if ((wAgent = p.gadajAsterisk.getWiadomoscByNumer(w.numer)) == null)
        return;
      agentConnection = wAgent.agent;

      knownMsg = agentResponsProcessor.processMessage(asteriskResponse, agentConnection, knownMsg, w);
      ;

      if (asteriskResponse.equals("No such agent")) {
        w.wiadomosc = "+ERR NO AGENT";
        e.loginIncorrect(agentConnection);
        knownMsg = true;
      }
      if (asteriskResponse.equals("Interface not found")) {
        w.wiadomosc = "+ERR PROBABLY NOT IN ANY QUEUE";
        e.removeQueueFailed(agentConnection);
        knownMsg = true;
      }
      if (asteriskResponse.equals("Interface paused successfully")) {

        Command c = wAgent.getCmd();
        if (c != null) {
          String pauseType = ((EventProxyClientPause) c).getPauseType();
          int pauseTime = ((EventProxyClientPause) c).getPauseTime();
          int timePoor = ((EventProxyClientPause) c).getTimePoor();
          int timeBad = ((EventProxyClientPause) c).getTimeBad();
          e.pause(agentConnection, pauseType, pauseTime, timePoor, timeBad);
          w.wiadomosc = "+OK PAUSED:'" + pauseType + "'";
        } else {
          e.pause(agentConnection);
          w.wiadomosc = "+OK PAUSED";
        }
        knownMsg = true;
      }
      if (asteriskResponse.equals("Interface unpaused successfully")) {
        w.wiadomosc = "+OK UNPAUSED";
        e.unpause(agentConnection);
        knownMsg = true;
      }
      // Queue 'bluzeczka', Action 'add'
      if (asteriskResponse.equals("Added interface to queue")) {
        String penalty = wAgent.agent.queuePenalty.get(wAgent.param);
        boolean pausedInQueue = (wAgent.agent.isNewConnected() && p.c.pauseWhenStartup);
        w.wiadomosc = "+INFO Queue '" + wAgent.param + "', Action 'add', Penalty '" + penalty + "', " +
            "Paused '" + pausedInQueue + "'";
        p.asteriskQueueList.updateQueueMember(p, wAgent.param,
            wAgent.agent.agentExten, "0", penalty);
        e.addQueue(agentConnection, wAgent.param, penalty);
        knownMsg = true;
      }
      if (asteriskResponse.equals("Removed interface from queue")) {
        w.wiadomosc = "+INFO Queue '" + wAgent.param
            + "', Action 'remove'";
        // w.wiadomosc = "+OK REMOVED FROM QUEUE: " + wAgent.param;
        e.removeQueue(agentConnection, wAgent.param);
        knownMsg = true;
        p.asteriskQueueList.removeQueueMember(p, wAgent.param,
            wAgent.agent.agentNumber);
      }
      if (asteriskResponse
          .equals("Unable to add interface: Already there")) {
        // w.wiadomosc = "+OK YOU ARE ALREADY IN QUEUE: " +
        // wAgent.param;
        String penalty = wAgent.agent.queuePenalty.get(wAgent.param);
        w.wiadomosc = "+INFO Queue '" + wAgent.param
            + "', Action 'add', Penalty '" + penalty + "'";
        p.asteriskQueueList.updateQueueMember(p, wAgent.param,
            wAgent.agent.agentExten, "0", penalty);
        e.addQueue(agentConnection, wAgent.param, penalty);
        e.addQueueFailed(agentConnection, wAgent.param,
            "Already in queue");
        knownMsg = true;
      }
      if (asteriskResponse
          .equals("Unable to remove interface: Not there")) {
        // w.wiadomosc = "+OK YOU ARE NOT IN QUEUE: " + wAgent.param;
        w.wiadomosc = "+INFO Queue '" + wAgent.param
            + "', Action 'remove'";
        e.removeQueueFailed(agentConnection);
        knownMsg = true;
      }
      if (knownMsg)
        wiadomosciOdAsteriska.add(w);
      else {
        w.wiadomosc = "+INFO Unknown response: " + asteriskResponse;
        wiadomosciOdAsteriska.add(w);
        e.unknownResponse(agentConnection, asteriskResponse,
            w.wiadomosc);
      }

      log.info("We have response for message id: "
          + getHeader("actionid") + ", response: '"
          + asteriskResponse + "'");
    }

    if (event == null)
      return;

    String qname, count, paused, callerid, reason, holdtime, uniqueid, bridgedUniqueid;
    qname = getHeader("queue");
    count = getHeader("count");
    paused = getHeader("paused");
    callerid = getHeader("callerid");
    reason = getHeader("reason");
    holdtime = getHeader("holdtime");
    uniqueid = getHeader("Uniqueid");
    bridgedUniqueid = getHeader("BridgedChannel");

    if (event.equals("Join") || event.equals("Leave")) {
      e.joinORleave(event, qname, count, callerid, uniqueid);
      globalMsg = new String("+INFO Queue '" + getHeader("queue")
          + "', Count '" + getHeader("Count") + "'");
      p.log.info(globalMsg);
      p.agenci.pushSuperMessage(globalMsg);
      p.asteriskQueueList.updateQueue(getHeader("queue"),
          getHeader("count"));

      // p.gadajAsterisk.sendAction("queuestatus");
      // TODO to trzeba chyba jakos inaczej obsluzyc, bo jak w trakcie
      // wylistowania agentow pojawia sie zdarzenia join lub leave, to
      // bedzie kicha
      showQueueMember = false;
      // GadajAsterisk.out.print("Action: queues\r\n\r\n");
      return;
			/* info o kolejkkach */
    }
    if (event.equals("QueueParams")) { // info o kolejce
      // e.queueParams(qname, max, calls, holdtime, completed, );
      globalMsg = new String("Queue: [" + getHeader("queue")
          + "], Calls [" + getHeader("calls") + "] max ["
          + getHeader("max") + "] holdtime [" + getHeader("holdtime")
          + "] completed [" + getHeader("completed")
          + "] abandoned [" + getHeader("abandoned")
          + "] servicelevel[" + getHeader("servicelevel") + "]");
      p.log.info(globalMsg);
      p.asteriskQueueList.updateQueue(getHeader("queue"),
          getHeader("calls"));
      return;
    }
		/* info o agentach w kolejce */
    if (event.equals("QueueMember")) {
      agentNumber = getHeader("location");
      String penalty = getHeader("penalty");
      String status = getStringStatus(getHeader("status"));
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);
      p.log.info(agentNumber);
      if (showQueueMember) {
        AgentConnection agent = p.agenci.getAgentConnectionByNumer(agentNumber);
        if (agent != null) p.log.info(agent.getState());

        e.queueMemberStatus(qname, agentNumber, paused, penalty,
            status);
        p.asteriskQueueList.updateQueueMember(p, getHeader("queue"),
            agentNumber, getHeader("Paused"), penalty);
      }
      return;
    }
		/* zmiany statusu agenta */
    if (event.equals("QueueMemberStatus") || event.equals("QueueMemberAdded")) {
      agentNumber = getHeader("location");
      String status = getStringStatus(getHeader("status"));
      paused = getHeader("paused");
      String penalty = getHeader("penalty");
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);

      e.queueMemberStatus(qname, agentNumber, paused, penalty, status);
      p.asteriskQueueList.updateQueueMemberStatus(p, qname, agentNumber, paused, status);
      // p.asteriskQueueList.updateQueueMember(p, getHeader("queue"),
      // agentNumber, getHeader("Paused"), penalty);

      return;
    }
		/* pauza */
    if (event.equals("QueueMemberPaused")) {
      agentNumber = getHeader("location");
      paused = getHeader("paused");
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);
      p.asteriskQueueList.updateQueueMemberStatus(p, qname, agentNumber, paused, null);
      return;
    }
		
		/* polaczenie przychodzi do agenta */
		/* AN compatible */
    if (event.equals("AgentCalled")) {
      agentNumber = getHeader("AgentCalled");
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);
      globalMsg = new String("+INFO New Call from '"
          + getHeader("callerid") + "', agent: '" + agentNumber
          + "', queue: '" + getHeader("queue") + "'");

			/*
			 * najpierw wyswietlimy event, ze dzwoni telefon agentowi. nawet
			 * jesli go nie ma w pamieci ccproxy
			 */
      e.newSomeCall(qname, agentNumber, callerid, getHeader("extension"),
          uniqueid);
      if ((agentConnection = p.agenci
          .getAgentConnectionByExten(agentNumber)) != null) {
        if ((exten = agentConnection.agentExten) != null) {
          e.newCall(qname, agentConnection.agentNumber, exten,
              callerid, uniqueid);
          p.agenci.pushMessage(globalMsg, agentConnection);
        }
      } else
        p.log.info("Nie znalezďż˝em agenta: " + agentNumber);
      p.log.info(globalMsg);

      return;
    }

		/* AN compatible */
    if (event.equals("AgentConnect")) {
      agentNumber = getHeader("member");
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);
      globalMsg = new String("+INFO Connected with Queue '"
          + getHeader("queue") + "', Agent '" + agentNumber + "'");

      RealQueue q = null;
      try {
        q = p.coreState.findQueue(new RealQueue(getHeader("queue"), 0));
      } catch (QueueNotFoundException e1) {
      }
      if (q != null) {
        globalMsg = globalMsg + " TalkTimePoor " + q.getTalkTimePoor() + ", TalkTimeBad " + q.getTalkTimeBad();
      }

      if ((agentConnection = p.agenci
          .getAgentConnectionByExten(agentNumber)) != null)
        if ((exten = agentConnection.agentExten) != null) {
          e.agentConnect(qname, agentConnection.agentNumber, exten,
              callerid, holdtime, uniqueid);
          p.agenci.pushMessage(globalMsg, agentConnection);
        }
      p.log.info(globalMsg);
    }

		/* AN compatible */
    if (event.equals("Hangup") /*|| event.equals("Unlink")*/) {
      agentNumber = getHeader("Channel");
      if (event.equals("Unlink")) {
        agentNumber = getHeader("Channel2");
      }
      if (agentNumber.substring(0, agentNumber.indexOf('/')).equals("SIP")) {
        agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1, agentNumber.indexOf('-'));
        globalMsg = new String("+INFO Hangup agent: '" + agentNumber + "'");
        if ((agentConnection = p.agenci.getAgentConnectionByExten(agentNumber)) != null) {
          if ((exten = agentConnection.agentExten) != null) {
            if (event.equals("Unlink")) {
              uniqueid = getHeader("Uniqueid1");
            }
            e.hangup(agentConnection.agentNumber, exten, uniqueid);
            p.agenci.pushMessage(globalMsg, agentConnection);
          }
        } else {
          log.warn("Agent " + agentNumber + " already loggedout");
        }
        p.log.info(globalMsg);
      }
    }
		/* AN compatible */
    if (event.equals("AgentComplete")) {
      agentNumber = getHeader("MemberName");
      agentNumber = agentNumber.substring(agentNumber.indexOf('/') + 1);
      globalMsg = new String("+INFO Call completed. Agent: "
          + agentNumber);

      p.log.info(globalMsg);

      if ((agentConnection = p.agenci
          .getAgentConnectionByExten(agentNumber)) != null)
        if ((exten = agentConnection.agentExten) != null) {
          if (p.c.pauseAfterConnection) {
            String autoPauseVal = p.pauseTypeList.getAutoPauseDef().getName();
            int pauseTime = 0;
            String callID = null;
            String queuename = null;
            RealQueue rq = null;
            try {
              try {
                RealCall call = p.coreState.findAgent(new RealAgent(agentConnection.agentNumber, null, null)).getConnectedCall();
                if (call != null) {
                  rq = p.coreState.findQueue(call.getQueue());
                  pauseTime = rq.getWrapup();
                  callID = call.getUniqueID();
                  queuename = call.getQueue().getName();
                }
              } catch (QueueNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
              //	pauseTime = p.coreState.findAgent(new RealAgent(agentConnection.agentNumber,null,null)).getConnectedCall().getQueue().getWrapup();
            } catch (AgentNotFoundException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }

            if (pauseTime != 0) {
              String line;
              if (p.c.requestCallTag) {
                ResultSet rs;
                String topicliststr = null, extrafieldsstr = null;
                rs = p.dbConn.query("SELECT * from get_topic_list('" + queuename + "')");
                try {
                  if (rs.next()) {
                    topicliststr = rs.getString("topiclist");
                    extrafieldsstr = rs.getString("extrafields");
                  }
                } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                if (!topicliststr.equals("")) {
                  line = "Event [CallTag], Callid [" + callID + "], Topiclist [" + topicliststr + "]";
                  if (!extrafieldsstr.equals("")) {
                    line = line + ", Extrafields [" + extrafieldsstr + "]";
                  }
                  p.agenci.pushMessage(line, agentConnection);
                }
              }
              line = "EVENT [ProxyClient], Action [Pause], Value [True], Type [" + autoPauseVal + "], Agent [" + agentConnection.agentNumber + "], Exten [" + exten + "], Time [" + pauseTime + "]";
              if (rq.getWrapupTimePoor() > 0) {
                line = line + ", TimePoor [" + rq.getWrapupTimePoor() + "], TimeBad [" + rq.getWrapupTimeBad() + "]";
              }
              p.gadajAsterisk.sendMessage(agentConnection, line);
            }

          }
          e.agentComplete(qname, agentConnection.agentNumber, exten,
              reason, uniqueid);
        }
    }

  }


  protected boolean agentLogout(String asteriskResponse, AgentConnection agentConnection,
      boolean knownMsg, WiadomoscOdAsteriska w) {
    return knownMsg;
  }


  public void send() {
    // TODO Auto-generated method stub

  }


  private String getStringStatus(String statusInt) {
    String status;
    if (statusInt == null || statusInt.equals(""))
      return "";
    int s = new Integer(statusInt).intValue();

    status = "BLAD";
    switch (s) {
      case 0:
        status = "UNKNOWN";
        break;
      case 1:
        status = "NOT_INUSE";
        break;
      case 2:
        status = "IN_USE";
        break;
      case 3:
        status = "BUSY";
        break;
      case 4:
        status = "INVALID";
        break;
      case 5:
        status = "UNAVAILABLE";
        break;
      case 6:
        status = "RINGING";
        break;
      case 7:
        status = "RINGINUSE";
        break;
      case 8:
        status = "ONHOLD";
        break;
    }
    return status;
  }
}

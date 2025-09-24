package ccproxy;

import pl.cc.SystemCoreState;
import pl.cc.core.PauseTypeList;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Parametry {

  PauseTypeList pauseTypeList;
  /**
   * Tutaj przechowywany jest aktalny stan systemu
   * Wykorzysywane do przesyłania listy aktualnie trwających połączeń dla nowo zalogowanych agentów
   */
  SystemCoreState coreState;

  AgentConnectionList agenci;
  AsteriskQueueList asteriskQueueList;


  DBConnection dbConn;
  GadajAsterisk gadajAsterisk;
  ReadConfig c;

  org.apache.log4j.Logger log = Logger.getLogger(Parametry.class);


  public Parametry() {
    pauseTypeList = new PauseTypeList();
    coreState = new SystemCoreState(pauseTypeList);
    agenci = new AgentConnectionList(coreState);
    asteriskQueueList = new AsteriskQueueList(coreState);
  }


  public Parametry(PauseTypeList pauseTypeList,
      SystemCoreState coreState,
      AgentConnectionList agenci, AsteriskQueueList asteriskQueueList) {
    this.pauseTypeList = pauseTypeList;
    this.coreState = coreState;
    this.agenci = agenci;
    this.asteriskQueueList = asteriskQueueList;
  }


  synchronized protected void removeAgentFromQueues(int numer) {
    AgentConnection a;
    ResultSet rs;
    String qname;
    if ((a = gadajAsterisk.getWiadomoscByNumer(numer).agent) != null) {

      rs = dbConn
          .query("SELECT nazwa from v_agent_queue where numer ='"
              + a.agentNumber + "'");
      try {
        while (rs.next()) {
          qname = new String(rs.getString("nazwa"));
          gadajAsterisk.sendMessage(a, "removequeue:" + qname,
              false);
          log.info("Automatyczne usuniecie agenta '"
              + a.agentNumber + "' z  kolejki:" + qname);
          // p.gadajAsterisk.out.print("Action: QueueAdd");
        }
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}

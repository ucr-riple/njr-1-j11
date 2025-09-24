package ccproxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Przemysław Gałązka
 * @since 25-07-2013
 */
public class AgentResponseProcessor {


  private Map<String, ProcessorAction> processorActionMap;


  public AgentResponseProcessor(final Parametry parametry,
      final Events events) {

    this.processorActionMap = new HashMap<String, ProcessorAction>();

    processorActionMap.put("Agent logged in", new ProcessorAction() {
      @Override
      public void process(WiadomoscOdAsteriska w, AgentConnection agentConnection) {
        w.wiadomosc = "+OK LOGGED IN";
        events.login(agentConnection);
      }
    });

    processorActionMap.put("Agent already logged in", new ProcessorAction() {
      @Override
      public void process(WiadomoscOdAsteriska w, AgentConnection agentConnection) {
        w.wiadomosc = "+OK AGENT ALREADY LOGGED IN";
        parametry.removeAgentFromQueues(w.numer);
        events.login(agentConnection);
      }
    });

    processorActionMap.put("Agent logged out", new ProcessorAction() {
      @Override
      public void process(WiadomoscOdAsteriska w, AgentConnection agentConnection) {
        w.wiadomosc = "+OK LOGGED OUT";
        events.logout(agentConnection);
      }
    });

  }


  protected boolean processMessage(String asteriskResponse, AgentConnection agentConnection,
      boolean knownMsg, WiadomoscOdAsteriska w) {

    ProcessorAction processorAction = processorActionMap.get(asteriskResponse);

    if (processorAction == null) {
      return knownMsg;
    } else {
      processorAction.process(w, agentConnection);
      return true;
    }
  }


  interface ProcessorAction {
    void process(WiadomoscOdAsteriska w, AgentConnection agentConnection);
  }

}

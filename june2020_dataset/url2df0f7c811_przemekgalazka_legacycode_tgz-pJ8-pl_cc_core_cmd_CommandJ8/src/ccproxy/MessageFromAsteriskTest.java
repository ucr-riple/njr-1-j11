package ccproxy;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Przemysław Gałązka
 * @since 25-07-2013
 */
public class MessageFromAsteriskTest {

  MessageFromAsterisk sut;
  Parametry parametry;
  Events events;

  AgentConnection agentConnection;
  WiadomoscOdAsteriska wiadomosc;


  @Before
  public void setUp() throws Exception {
    parametry = mock(Parametry.class);
    events = mock(Events.class);
    sut = new MessageFromAsterisk(parametry, events);

    agentConnection = mock(AgentConnection.class);
    wiadomosc = new WiadomoscOdAsteriska();
  }


  @Test
  public void shouldCharacteriseMessageProcessing() throws Exception {

    //-------------------- GIVEN -------------------------------------------------------------------


    //-------------------- WHEN --------------------------------------------------------------------
    sut.parseEvent(mock(WiadomoscOdAsteriskaList.class));

    //-------------------- THEN --------------------------------------------------------------------
  }


  @Test
  public void shouldProcessLogin() throws Exception {

    //-------------------- GIVEN -------------------------------------------------------------------
    String agentLoggedInResponse = "Agent logged in";
    boolean knownMsg = false;

    //-------------------- WHEN --------------------------------------------------------------------
    boolean actualResult = sut.agentResponsProcessor.processMessage(agentLoggedInResponse,
        agentConnection, knownMsg, wiadomosc);

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(wiadomosc.getWiadomosc()).isEqualTo("+OK LOGGED IN");
    verify(events).login(agentConnection);
    assertThat(actualResult).isEqualTo(true);
  }


  @Test
  public void shouldNotProcessAgentLoginIn() throws Exception {
    //-------------------- GIVEN -------------------------------------------------------------------
    String agentLoggedInResponse = "some response";
    boolean knownMsg = false;

    //-------------------- WHEN --------------------------------------------------------------------
    boolean actualResult = sut.agentResponsProcessor.processMessage(agentLoggedInResponse,
        agentConnection, knownMsg, wiadomosc);

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(actualResult).isEqualTo(knownMsg);

  }


  @Test
  public void shouldProcessAgentAlreadyLoggedIn() throws Exception {

    //-------------------- GIVEN -------------------------------------------------------------------
    String agentResponse = "Agent already logged in";
    boolean knownMsg = false;


    //-------------------- WHEN --------------------------------------------------------------------
    boolean actualResult = sut.agentResponsProcessor.processMessage(agentResponse, agentConnection,
        knownMsg, wiadomosc);

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(wiadomosc.getWiadomosc()).isEqualTo("+OK AGENT ALREADY LOGGED IN");
    assertThat(actualResult).isEqualTo(true);

    verify(events).login(agentConnection);
    verify(sut.p).removeAgentFromQueues(anyInt());

  }


  @Test
  public void shouldProcessAgentLogout() throws Exception {

    //-------------------- GIVEN -------------------------------------------------------------------
    String agentLoggedInResponse = "Agent logged out";
    boolean knownMsg = false;

    //-------------------- WHEN --------------------------------------------------------------------
    boolean actualResult = sut.agentResponsProcessor.processMessage(agentLoggedInResponse,
        agentConnection, knownMsg, wiadomosc);

    //-------------------- THEN --------------------------------------------------------------------
    assertThat(wiadomosc.getWiadomosc()).isEqualTo("+OK LOGGED OUT");
    verify(events).logout(agentConnection);
    assertThat(actualResult).isEqualTo(true);
  }

}

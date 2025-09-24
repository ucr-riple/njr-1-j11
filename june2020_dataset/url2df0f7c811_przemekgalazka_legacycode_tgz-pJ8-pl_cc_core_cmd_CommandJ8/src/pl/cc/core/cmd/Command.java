package pl.cc.core.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Reprezentacja informacji przychodzďż˝cej od CCProxy
 *
 * @since 2007-12-14
 */
public abstract class Command {
  protected static final Logger log = Logger.getLogger(Command.class);
  public static final int CMD_UNKNOWN = 0;
  public static final int CMD_INFO_QUEUE = 1;
  public static final int CMD_INFO_AGENT = 2;
  public static final int CMD_INFO_PRIVILEGE = 3;
  public static final int CMD_INFO_PAUSETYPE = 4;

  /**
   * Minimalna wersja klienta
   */
  public static final int CMD_INFO_VERSION = 3;
  public static final int CMD_INFO_TYPE = 4;

  public static final int CMD_EVENT_QUEUE_JOIN = 10;
  public static final int CMD_EVENT_QUEUE_LEAVE = 11;

  public static final int CMD_EVENT_AGENT_ADD_TO_QUEUE = 21;
  public static final int CMD_EVENT_AGENT_REMOVE_FROM_QUEUE = 22;
  public static final int CMD_EVENT_AGENT_NEW_CALL = 23;
  public static final int CMD_EVENT_AGENT_HANGUP = 24;
  public static final int CMD_EVENT_AGENT_CONNECT = 25;
  public static final int CMD_EVENT_AGENT_PAUSE = 26;
  public static final int CMD_EVENT_AGENT_UNPAUSE = 27;
  public static final int CMD_EVENT_AGENT_LOGIN = 28;
  public static final int CMD_EVENT_AGENT_LOGOUT = 29;
  public static final int CMD_EVENT_AGENT_QUEUE_MEMBER = 30;
  public static final int CMD_EVENT_AGENT_COMPLETED = 31;
  public static final int CMD_EVENT_AGENT_CALL_TIME_BAD = 32;

  public static final int CMD_WELCOME = 101;
  public static final int CMD_PONG = 102;
  public static final int CMD_OK_LOGGING_OK = 103;
  public static final int CMD_OK_LOGGING_OK_SUPERVISOR = 104;
  public static final int CMD_OK_LOGGING_ERROR = 105;
  public static final int CMD_OK_LOGGED_OUT = 106;

  public static final int CMD_QUEUE_ACTION_ADD = 201;
  public static final int CMD_QUEUE_ACTION_REMOVE = 202;

  public static final int CMD_OK_WELCOME = 301;
  public static final int CMD_OK_PAUSED = 302;

  public static final int CMD_ERR_CONNECTION_REPLACED = 401;

  public static final int CMD_EVENT_CALL_TAG = 501;
  public static final int CMD_EVENT_PROXYCLIENT_CALL_TAG = 502;


  /**
   * Zdarzenia płynące od CCProxyClient -> CCProxy
   */
  public static final int CMD_EVENT_PROXYCLIENT_PAUSE = 501;

  enum CmdType {INFO, EVENT, UNKNOWN}

  ;

  //static Matcher m;
  static Pattern p = Pattern.compile(".*");

  public static final String REGEX_CHARACTERS_INFO = "\\w\\s\\.,/:\\-ęóąśłżźćńĘÓĄŚŁŻŹĆŃ<>\\{\\}\\|\\(\\)";
  public static final String REGEX_CHARACTERS_EVENT = "^\\[\\]";


  public abstract int getType();


  private String orginalLine;
  private String connectionId;


  public Command(String orginalLine, String connectionId) {
    this.orginalLine = orginalLine;
    this.connectionId = connectionId;
  }


  protected Command(String orginalLine) {
    this.orginalLine = orginalLine;
  }


  protected static boolean equalsExtended(String line, String searching) {
    if (line == null) return false;
    String l2 = line.replaceAll("\\s", "").replaceAll("\\.", "");
    String s2 = searching.replaceAll("\\s", "").replaceAll("\\.", "");
    return l2.toLowerCase().equals(s2.toLowerCase());
  }


  private static String getValueINFO(String line, String name) {
    synchronized (p) {
      //+INFO Queue 'rekawek', Count '0'
      Matcher m = Pattern.compile(".*" + name + "[\\s,:]*'([" + REGEX_CHARACTERS_INFO + "]+)'.*", Pattern.CASE_INSENSITIVE).matcher(line);
      if ((m.find()) && (m.groupCount() == 1)) {
        return m.group(1);
      } else {
        return null;
      }
    }
  }


  private static String getValueEVENT(String line, String name) {
    synchronized (p) {
      // EVENT [Agent], Action [AddToQueue], Agent [313], Queue [nogawka], Exten [978], Name [Litewka Dorota]]
      Matcher m = Pattern.compile(".*" + name + "\\s*\\[([" + REGEX_CHARACTERS_EVENT + "]*)\\].*", Pattern.CASE_INSENSITIVE).matcher(line);
      if ((m.find()) && (m.groupCount() == 1)) {
        return m.group(1);
      } else {
        return null;
      }
    }
  }


  /**
   * Uďż˝ciďż˝lenie protected static String [] getValues (String line, String [] names) dla komend
   * +INFO Queue 'rekawek', Count '1'
   * Rozkďż˝ad linii na parametry.
   * Pobranie wartoďż˝ci konkretnych parametrďż˝w.
   * Jeďż˝li ktďż˝ryďż˝ z parametrďż˝w nie zostanie znaleziony - zwracam null.
   * Obsďż˝uguje linie typu:
   * +INFO Queue 'rekawek', Count '1'
   *
   * @param line  zawartoďż˝ďż˝ linii
   * @param names nazwy parametrďż˝w
   * @return tablica z wartoďż˝ci dla poszczegďż˝lnych parametrďż˝w
   */
  protected static String[] getValuesINFO(String line, String[] names, boolean[] required) {
    String values[] = new String[names.length];
    for (int i = 0; i < values.length; i++) {
      String val = null;
      val = getValueINFO(line, names[i]);
      if (val == null && required[i]) {
        return null;
      } else {
        values[i] = val;
      }
    }
    return values;
  }


  protected static String [] getValuesINFO(String line, String [] names){
    boolean [] required = new boolean [names.length];
    for (int i=0; i<required.length; i++){
      required[i] = true;
    }
    return getValuesINFO(line, names, required);
  }

  protected static boolean getValuesINFO(String line) {
    String[] names = {"version", "url"};
    boolean[] required = new boolean[names.length];
    for (int i = 0; i < required.length; i++) {
      required[i] = true;
    }
    return getValuesINFO(line, names, required) != null;
  }


  protected static String[] getValues(String line, String[] names, int numrequired) {
    boolean[] required = new boolean[names.length];
    for (int i = 0; i < numrequired; i++) {
      required[i] = true;
    }
    return getValues(line, names, required);
  }


  /**
   * Rozkďż˝ad linii na parametry.
   * Pobranie wartoďż˝ci konkretnych parametrďż˝w.
   * Jeďż˝li ktďż˝ryďż˝ z parametrďż˝w nie zostanie znaleziony - zwracam null.
   * Obsďż˝uguje linie typu:
   * EVENT [Agent], Action [SomeNewCall], Agent [301], Exten [222], Queue [rekawek], CallerID [],
   * Uniqueid [1202119281.30662]
   * +INFO Queue 'rekawek', Count '1'
   *
   * @param line  zawartoďż˝ďż˝ linii
   * @param names nazwy parametrďż˝w
   * @return tablica z wartoďż˝ci dla poszczegďż˝lnych parametrďż˝w
   */
  public static String[] getValues(String line, String[] names, boolean[] required) {
    String values[] = new String[names.length];
    CmdType cmdtype = CmdType.UNKNOWN;

    if (line.toUpperCase().startsWith("+INFO")) {
      cmdtype = CmdType.INFO;
    } else if (line.toUpperCase().startsWith("EVENT")) {
      cmdtype = CmdType.EVENT;
    }
    ;

    for (int i = 0; i < values.length; i++) {
      String val = null;
      if (cmdtype == CmdType.INFO) {
        val = getValueINFO(line, names[i]);
      } else if (cmdtype == CmdType.EVENT) {
        val = getValueEVENT(line, names[i]);
      }
      if (val == null && required[i]) {
        return null;
      } else {
        values[i] = val;
      }
    }
    return values;
  }


  public static String[] getValues(String line, String[] names) {
    boolean[] required = new boolean[names.length];
    for (int i = 0; i < required.length; i++) {
      required[i] = true;
    }
    return getValues(line, names, required);
  }


  public String serializeToString() {
    throw new UnsupportedOperationException();
  }


  public static Command factory(String line, String connectionId) {
        /* FIXME - proteza dla ID [...] EVENT */
    String l = line.replaceFirst("\\s*ID\\s*\\[\\d+\\.\\d+\\],\\s+", "");
    line = l;

    CommandFactory commandFactory = new CommandFactory();
    Command c;

    c = commandFactory.matchBy(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = commandFactory.matchBy(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = commandFactory.matchBy(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = CommandLoggedInSupervisor.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = CommandAuthError.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = CommandLoggedOut.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = CommandPong.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventQueueJoin.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventQueueLeave.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventAgentAddToQueue.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentQueueMember.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentRemoveFromQueue.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentNewCall.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentHangup.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentConnect.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentPause.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentUnPause.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentLogin.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = EventAgentLogout.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = InfoAgent.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = InfoPrivilege.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = InfoPauseDefinition.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = InfoQueue.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = InfoQueueActionAdd.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }
    c = InfoQueueActionRemove.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = OkWelcome.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    //	c = ErrSameAgentConnection.factoryInt(line);
    c = InfoType.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventProxyClientPause.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventAgentComplete.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventCallTag.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventProxyClientCallTag.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    c = EventAgentCallTimeBad.factoryInt(line);
    if (c != null) {
      c.connectionId = connectionId;
      return c;
    }

    CommandUnknown cu = new CommandUnknown(line, connectionId);

    return cu;
  }


  /**
   * Funkcja pomocnicza do testowania poprawnoďż˝ci parsowania linii z cmd.
   * Wypisuje liniďż˝ z cmd i Command.class
   *
   * @param cmd linia z cmd.
   */
  private static void testCommand(String cmd) {
    Command c = Command.factory(cmd, "test-123");
    log.info("Line: [" + cmd + "]");
    log.info("Command class: [" + c.getClass() + "]");
    //OkPaused p = (OkPaused)c;
    //log.info(p.getPauseType());
  }


  public static void main(String[] args) {
    String cmd;
    // cmd = "EVENT [Agent], Action [AgentConnect], Agent [315], Exten [855], Queue [fly], CallerID [], Holdtime [5], Uniqueid [1203680371.15557]";
    // cmd="EVENT [Agent], Action [Login], Agent [302], Exten [702], Name [Mijalska Katarzyna]";

    cmd = "EVENT [Agent], Action [AddToQueue], Agent [330], Queue [allegro], Exten [867], Name [Sieczka Magdalena], Penalty [0], ID [200810191212.330]";
    cmd = "EVENT [Agent], Action [Login], Agent [312], Exten [829], Name [Klimek Lłkasz]";  // ok
    cmd = "EVENT [Agent], Action [Login], Agent [312], Exten [829], Name [Klimek Ęukasz]"; // źłe - duże polskie litery
    cmd = "+INFO Type 'connection_replaced', Message 'Connection replaced',  Ip '127.0.0.1'";
    cmd = "+OK PAUSED:'default'";
    cmd = "EVENT [Agent], Action [NewCall], Agent [304], Exten [2001], Queue [bluzeczka], CallerID [17031574], Uniqueid[123], CreationTime [2009-07-03 12:56:11]";
    cmd = "EVENT [Agent], Action [AgentComplete], Agent [301], Exten [873], Queue [bluzeczka], Reason [caller], Uniqueid [1197554949.1659]";
    cmd = "+INFO Type 'license_error', Message 'Przekroczona licencja: 50<0'";
    cmd = "EVENT [ProxyClient], Action [Pause], Value [true], Type [przerwa], Agent [354], Exten [2230]";
    cmd = "Event [Agent], Action [CallTimeBad], Agent [694], Name [Kowalska Iwona], Time [0]";
    Command.testCommand(cmd);
  }


  public String getOrginalLine() {
    return orginalLine;
  }


  public String getConnectionId() {
    return connectionId;
  }


}

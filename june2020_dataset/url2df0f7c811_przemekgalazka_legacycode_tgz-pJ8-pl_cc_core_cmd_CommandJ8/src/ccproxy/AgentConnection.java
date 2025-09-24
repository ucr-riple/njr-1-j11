package ccproxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import pl.cc.SystemCoreState;
import pl.cc.core.NetPrintWriter;
import pl.cc.core.PauseType;
import pl.cc.core.cmd.InfoPauseDefinition;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.exceptions.QueueNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;
import pl.cc.utils.MD5Crypt;

public class AgentConnection extends Thread {
	org.apache.log4j.Logger logping = Logger.getLogger("ping");
	public static final Logger log = Logger.getLogger(AgentConnection.class);

	String ip;
	int port;

	String agentName;
	String agentExten = null;
	String agentNumber = null;
	String idConnection = null;

	boolean paused = false;
	boolean supervisor = false;
	boolean loggedout = false;
	boolean clearexit = false;
	boolean isSerwis = false; 

	AgentConnectionPing agentConnectionPing;

	SocketChannel sChannel;
	ChannelWriter out;
	ChannelReader in;
	Parametry p;
	Date last_ping = GregorianCalendar.getInstance().getTime();
	Date last_db_activity = GregorianCalendar.getInstance().getTime();
	HashMap<String, String> queuePenalty = new HashMap<String, String>();
	SimpleDateFormat dateFormat;
	SimpleDateFormat dateFormatsec;
	/**
	 * Czy agent nowo zalogowany. Używany na potrzeby automatycznego pauzowania w kolejkach po zalogowaniu
	 */
	boolean newConnected = true;
    private boolean offline=false;

    public AgentConnection(SocketChannel sChannel, Parametry param) {
		super();
		this.p = param;
		this.sChannel = sChannel;
		this.ip = sChannel.socket().getInetAddress().getHostAddress();
		this.port = sChannel.socket().getPort();
		this.idConnection = new Integer(Calendar.getInstance().get(
				Calendar.YEAR)).toString()
				+ new Integer(Calendar.getInstance().get(Calendar.MONTH))
						.toString()
				+ new Integer(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
						.toString()
				+ new Integer(Calendar.getInstance().get(Calendar.HOUR))
						.toString()
				+ new Integer(Calendar.getInstance().get(Calendar.MINUTE))
						.toString()
				+ new Integer(Calendar.getInstance().get(Calendar.SECOND))
						.toString();

		p.log.info("New connection from ip: " + ip + ":" + port);
	}

	public AgentConnection(String string, String string2) {
		// TODO Auto-generated constructor stub
		ip = string;
		agentNumber = string2;
	}

	/**
	 * Wylistowanie agentów do których mamy uprawnienia
	 * @param supervisorId - id agenta który rząda swoich uprawnień
	 * @param out - połączenie do agenta, który żąda listy uprawnień
 	 */
	private void printPrivilegesToAgent(int supervisorId, ChannelWriter out) {
		List<Integer> agents = loadPrivileges(supervisorId);
		for (Integer agentId : agents){
			out.println("+INFO Privilege 'grand', Agent '" + agentId.toString() + "'");
		}
	}
	/**
	 * Załadowanie listy uprawnień dla wybranego supervisora/kooordynatora
	 * @param supervisorId 
	 * @return lista ids dla agentów do których posiadamy uprawnienia
	 */
	private List<Integer> loadPrivileges(int supervisorId) {
		ArrayList<Integer> privSet = new ArrayList<Integer>();
		ResultSet rs = p.dbConn.query("select id_agent from v_supervisor_agent where id_supervisor='"+ supervisorId +"'");
		try {
			while (rs.next()) {
				privSet.add(rs.getInt("id_agent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p.log.debug("Agent id: "+supervisorId+" have privilege to: "+privSet.size()+" agents");
		return privSet;
	}

	/**
	 * Wylistowanie dostepnych w systemie pauz
	 * @param out
	 */
	private void printPauseTypesToAgent(ChannelWriter out) {
		for(PauseType pauseType : p.pauseTypeList){
			out.println(new InfoPauseDefinition(pauseType).serializeToString());
		}
	}

	/**
	 * Wysyłamy listę trwających połączeń dla nowo zalogowanego agenta
	 */
	public void printCallInProgress() {
		SystemCoreState cs = p.agenci.coreState;
		synchronized (cs.getQueueList()) {
			for (RealQueue queue : cs.getQueueList().getValues()){
				synchronized (queue.getCallList()) {
					for (RealCall call : queue.getCallList().getValues()){
						// Połączenie w kolejce
						//log.warn(">> Queue:" +queue+" call: " +call);
						String s = "EVENT [Queue], Action [Join], " +
								"Queue ["+queue.getName()+"], Count ["+queue.getWaitCount()+"], " +
								"Callerid ["+call.getCallerID()+"], Uniqueid ["+call.getUniqueID()+"], "+
								"CreationTime ["+call.getCrationTimeFormated()+"]";
						out.println(s);
					
						RealAgent agent = cs.findAgentConnectedWithCall(call);
						if (agent!=null){
							//Jeśli połączenie w kolejce sparowane z agentem
							log.warn(">> connected with: "+agent);
							// FIXME: Ponieważ klient może jeszcze nie mieć zalogowanego tego agenta to (dostanie to info póżniej)   
							// EVENT [Agent], Action [Login], Agent [304], Exten [2001], Name [Olsztyńska Antonina]
							String sAgentLogin = "EVENT [Agent], Action [Login], " +
									"Agent ["+agent.getId()+"], Exten ["+agent.getExtension()+"], Name ["+agent.getName()+"]";
							out.println(sAgentLogin);
							// FIXME:Agenta najprawdopodobniej nie ma także w kolejce 
							// +INFO Queue 'bluzeczka', Agent '304', Exten '2001', Name: 'Olsztyńska Antonina' (dostanie to info póżniej)
							String sAgentQueueMember = "+INFO Queue '"+queue.getName()+"', " +
									"Agent '"+agent.getId()+"', Exten '"+agent.getExtension()+"', Name: '"+agent.getName()+"'";
							out.println(sAgentQueueMember);
							
							//EVENT [Agent], Action [NewCall], Agent [313], Exten [877], Queue [nogawka], CallerID [978]
							String sNewCall ="EVENT [Agent], Action [NewCall], " +
									"Agent ["+agent.getId()+"], Exten ["+agent.getExtension()+"], " +
									"Queue ["+queue.getName()+"], CallerID ["+call.getCallerID()+"], Uniqueid ["+call.getUniqueID()+"],"+
									"CreationTime ["+call.getCrationTimeFormated()+"]";
							out.println(sNewCall);
							//EVENT [Agent], Action [AgentConnect], Agent [304], Exten [2001], Queue [bluzeczka], CallerID [76726470], Holdtime [0], Uniqueid [1246610416.627]
							String sAgentConnect ="EVENT [Agent], Action [AgentConnect], " +
									"Agent ["+agent.getId()+"], Exten ["+agent.getExtension()+"], " +
									"Queue ["+queue.getName()+"], " +
									"CallerID ["+call.getCallerID()+"], Holdtime [0], Uniqueid ["+call.getUniqueID()+"]," +
									"CreationTime ["+call.getCrationTimeFormated()+"]";
							out.println(sAgentConnect);
						}
					}
				}
			}
		}
	}
	@Override
	public void run() {
		runInternal();
		p.log.info("Thread died");
	}
	
	private void runInternal() {
		setThreadName();
		AgentConnection agentDel;
		Thread mainThread=null;
		try {
			Selector selector = Selector.open();
			out = new ChannelWriter(sChannel, selector);
			in = new ChannelReader(sChannel);
			//in = new BufferedReader(new InputStreamReader(sChannel
			//		.getInputStream()));
			String inputLine, outputLine;

			String date;
			
			GregorianCalendar gc = new GregorianCalendar();
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateFormatsec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date=dateFormat.format(gc.getTime());
			String check = new String(	Integer.toString(2008) + 
														"-" + Integer.toString(11) + 
														"-0" + Integer.toString(4) + 
														" 0" + Integer.toString(15));
			if(date.compareTo(check) > 0){
				this.isSerwis=false; // naleďż˝y zmieniďż˝ na true, jesli jest potrzeba wejďż˝cia w tryb serwisowy
			}
			Date licenseExpires=null;
			if (p.c.licenseExpires!=""){
				try {
					licenseExpires=dateFormat.parse(p.c.licenseExpires+" 00:00");
				} catch (ParseException e1) {
					e1.printStackTrace();
					licenseExpires=GregorianCalendar.getInstance().getTime();
				}
			}
			
			sChannel.configureBlocking(true);
			
			if (isSerwis || !agentAuthorization()) {
				out.println("+INFO Type 'other', Message 'Błędny identyfikator, hasło lub numer telefonu'");
				out.println("-ERR Authentication error");
				p.log.info("-ERR Authentication error");
				out.close();
				in.close();
				return;
			}

			sChannel.configureBlocking(false);
			sChannel.register(selector, SelectionKey.OP_READ);

			
			/* jesli to byl tylko dump struktury, to wychodzimy po cichu*/
			if(this.agentName.equals("CMD")){
				out.close();
				in.close();
				return;
			}
			idConnection += "." + agentNumber;

			Iterator<String> i = queuePenalty.keySet().iterator();
			while (i.hasNext()) {
				String key = i.next();
				p.log.info("key:" + key + ", value: " + queuePenalty.get(key));
			}

			if ((agentDel = p.agenci.getAgentConnectionByNumer(agentNumber)) != null) {
				p.log.info("-ERR Mam juz agenta " + agentNumber);
				agentDel.stopPingThread();
				agentDel.out.println("+INFO Type 'connection_replaced', Message 'Agent "+ agentNumber+" zalogował się powtórnie. Nr. tel:"+agentExten+" IP: "+ip+"',  Ip '" +agentDel.ip+"'");
				p.log.info("+INFO Type 'connection_replaced', Message 'Agent "+ agentNumber+" zalogował się powtórnie. Nr. tel:"+agentExten+" IP: "+ip+"',  Ip '" +agentDel.ip+"'");
				p.asteriskQueueList.removeAgentConnection(agentDel);
				
				p.agenci.remove(agentDel);
				agentDel.out.close();
			}
			if ((agentDel = p.agenci.getAgentConnectionByExten(agentExten)) != null) {
				p.log.info("-ERR Mam juz agenta na tym telefonie");
				out.println("+INFO Type 'connection_replaced', Message 'Na nr. tel: "+agentDel.agentExten+" zalogował się już agent "+ agentDel.agentNumber+" IP: "+agentDel.ip+"',  Ip '" +agentDel.ip+"'");
				p.log.info("+INFO Type 'connection_replaced', Message 'Na nr. tel: "+agentDel.agentExten+" zalogował się już agent "+ agentDel.agentNumber+" IP: "+agentDel.ip+"',  Ip '" +agentDel.ip+"'");
				out.close();
				in.close();
				return;
			}
			p.log.info("+INFO Licencja " + p.c.licensedAgents+", zalogowanych "+p.coreState.getLoggedAgentList().size());
			synchronized (p.agenci) {
			if (new Integer(p.c.licensedAgents).intValue() <= p.agenci.size()) {
				p.log.info("-ERR Przekroczona licencja");
				p.log.info("+INFO Type 'license_error', Message 'Brak wolnych licencji (dostępnych: " + p.c.licensedAgents+", w użyciu: "+p.coreState.getLoggedAgentList().size()+")'");
				out.println("+INFO Type 'license_error', Message 'Brak wolnych licencji (dostępnych: " + p.c.licensedAgents+", w użyciu: "+p.coreState.getLoggedAgentList().size()+")'");
				out.close();
				in.close();
				return;
			}			
			}

			if (supervisor) {
				out.println("+INFO Name '" + agentName + "', Extension '" + agentExten + "', AgentNumber '" + agentNumber + "', Type 'supervisor'");
			} else {
				out.println("+INFO Name '" + agentName + "', Extension '" + agentExten + "', AgentNumber '" + agentNumber + "', Type 'agent'");
			}


            if (offline){
                out.close();
                in.close();
                out=new NullChannelWriter();
            }

			//wylistowanie rodzaju pauz dostępnych w systemie
			printPauseTypesToAgent(out);

			synchronized (p.agenci) {
				p.agenci.add(this);
			}

            paused = p.c.pauseWhenStartup;

			p.gadajAsterisk.sendMessage(this, "login", false);
			
			p.gadajAsterisk.sendMessage(this, "getallqueues", false);
			
			p.gadajAsterisk.sendMessage(this, "getqueues");
				
			//wylistowanie uprawnień
			printPrivilegesToAgent(new Integer(agentNumber), out);
			
			
			// FIXME dorobic catch do readline

			last_ping = GregorianCalendar.getInstance().getTime();
			last_db_activity = GregorianCalendar.getInstance().getTime();
			mainThread = currentThread();
            if (offline){
             return;
            }

			agentConnectionPing = new AgentConnectionPing(this, mainThread);
			agentConnectionPing.start();

			
			while (true) {
				
				if (licenseExpires!=null && GregorianCalendar.getInstance().getTime().after(licenseExpires)) {
					p.c.licensedAgents="0";
					p.log.info("License Expired");
					out.close();
					in.close();
					return;
				}
							
				selector.select();
				Iterator selectedKeysIterator = selector.selectedKeys().iterator();

				while (selectedKeysIterator.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeysIterator.next();
					selectedKeysIterator.remove();
					if (!key.isValid())
						continue;

					if (key.isReadable()){
						p.log.trace("Readable...");
						
						while ((outputLine=in.readLine())!=null){
						if (outputLine!=null){
							if (outputLine.equals("exit") || outputLine.equals("logout")) {
								clearexit = true;
								break;
							}
							if (outputLine.equals("ping")) {
								logping.info("Ping fr ID " + idConnection + ", Agent: "
										+ agentNumber + ", exten: " + agentExten + ", IP: "
										+ ip + ":" + port);
								out.println("+OK pong");
								logping.info("Pong to ID " + idConnection + ", Agent: "
										+ agentNumber + ", exten: " + agentExten + ", IP: "
										+ ip + ":" + port);
								last_ping = GregorianCalendar.getInstance().getTime();
								continue;
							}
							p.gadajAsterisk.sendMessage(this, outputLine);
							
						}
						}}
					if (key.isWritable()){
						p.log.trace("Writable...");
						sChannel.register(selector, SelectionKey.OP_READ);
						out.println(null);
					}
						
					}
				
				
				if (isInterrupted())
					break;
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			cleanup(null, mainThread, true);
			return;
		} catch (IOException e) {
			System.out.println(agentName);
			if (this.agentName==null||this.agentName.equals("CMD")){
				out.close();
				in.close();
			} else {
				cleanup(null, mainThread, true);
			}
			if (!loggedout)
				log.error(e);
		}
	}

	private void stopPingThread() {
		log.info("Stopping ping thread");
        if (agentConnectionPing!=null) agentConnectionPing.stopThread();
		
	}

	private void cleanup(Thread pingThread, Thread mainThread,
			boolean raisedFromMainThread) {

		p.log.info("Prepare to clean after connection: Agent: " + agentNumber
				+ ", exten: " + agentExten + ", IP: " + ip + ":" + port);
		if (loggedout)
			return;
		loggedout = true;

		if (raisedFromMainThread) {
			p.log.info("Cleanup raised from main thread: Agent: " + agentNumber
					+ ", exten: " + agentExten + ", IP: " + ip + ":" + port);
			if (agentConnectionPing!=null) agentConnectionPing.stopThread();
		} else {
			p.log.info("Cleanup raised from ping thread: Agent: " + agentNumber
					+ ", exten: " + agentExten + ", IP: " + ip + ":" + port);
			mainThread.interrupt();
		}

		p.log.info("Try to close connection: Agent: " + agentNumber
				+ ", exten: " + agentExten + ", IP: " + ip + ":" + port);
		
		out.close();
		in.close();

		p.log.info("Agent connection closed: Agent: " + agentNumber
				+ ", exten: " + agentExten + ", IP: " + ip + ":" + port);
		//nie wysyłamy pauzy przed wylogowaniem 
		//p.gadajAsterisk.sendMessage(this, "pause"); 
		if (clearexit) {
			p.log.info("Clear exit received. Logout agent: Agent: "
					+ agentNumber + ", exten: " + agentExten + ", IP: " + ip
					+ ":" + port);
			p.gadajAsterisk.sendMessage(this, "logout");
		} else {
			p.log.info("Unclear exit received. Logout agentt: Agent: "
					+ agentNumber + ", exten: " + agentExten + ", IP: " + ip
					+ ":" + port);
			p.gadajAsterisk.sendMessage(this, "logout");
		}
		p.asteriskQueueList.removeAgentConnection(this);
		p.agenci.remove(this);
		AsteriskCmd cmd = new AsteriskCmd(p);

		GadajAsterisk.out.print(cmd.delAgentExten(agentExten));
		GadajAsterisk.out.print(cmd.delAgentNumber(agentNumber));

		try {
			in.close();
		} catch (Exception e) {
			p.log.info("Wywalono in: " + e.getMessage());
		}
		try {
			out.close();
		} catch (Exception e) {
			p.log.info("Wywalono out: " + e.getMessage());
		}
		System.out.println("Agent connection closed.");

		;

	}

	private void agentMakeQueuePenalty(String agentNumber) {
		String qname, penalty;
		ResultSet rs;
		
			rs = p.dbConn
					.query("select nazwa, penalty from v_agent_queue where numer='"
							+ agentNumber + "';");
			try {
				while (rs.next()) {
					qname = new String(rs.getString("nazwa"));
					penalty = new String(rs.getString("penalty"));
					p.log.info("Agent: " + agentNumber + ", Queue: " + qname
							+ ", penalty: " + penalty);
					this.queuePenalty.put(qname, penalty);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	private boolean agentAuthorization() throws IOException {
		String agentPassword = null;
		String line, sline[];
		ResultSet rs;
		out.println("+INFO Version '" + p.c.agentVersion + "', Url '"
				+ p.c.agentUpdateURL + "'");

			rs = p.dbConn
					.query("select nazwisko, numer, channel, supervisor from v_agent_proxy where ip_komputer='"
							+ ip + "';");
			try {
				if (rs.next()) {
					p.log.info("Polaczenie ze znanego ip: " + ip);
					agentName = new String(rs.getString("nazwisko"));
					agentExten = new String(rs.getString("channel"));
					agentNumber = new String(rs.getString("numer"));
					if (rs.getString("supervisor").equals("t"))
						supervisor = true;
					p.log.info(agentExten);
					p.log.info(agentName);
					p.log.info(agentNumber);
					agentMakeQueuePenalty(agentNumber);
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		out.println("Welcome. Please authorize.");
		// agent:300, password:111, exten:999
		p.log.debug(p.c.licensedAgents);

		if ((line = in.readLine()) == null)
			return false;

		/* zrzut struktur */
		
		if (ip.equals("127.0.0.1")) { //polaczenie lokalne
		
		if (line.equals("g")){
			this.agentName="CMD";
			dumpstruct();
			return true;
		}

		if (line.equals("w")){
			this.agentName="CMD";
			out.println(String.format("%d,%d,%d\n", p.coreState.getLoggedAgentList().size(),p.coreState.getLoggedAgentList().getFreeAgentCount(), p.coreState.getLoggedAgentList().getTalkingAgentCount()));
			return true;
		}
		if (line.equals("nw")){
			this.agentName="CMD";
			for (RealQueue q:p.coreState.getQueueList().getValues()){
					out.println(String.format("%s %d %d %d %d %d %d",q.getName(), q.getWaitCount(), q.getTalkingAgentCount(), 
							q.getFreeAgentCount(), q.getPausedAgentCount() ,q.getPausedAgentCount(PauseType.ID_DEFAULT) ,q.getAgentList().size()));
				}			
			return true;
		}
		if (line.equals("reload")){
			this.agentName="CMD";
			ReadConfig c = new ReadConfig(p, p.c.configFile);
			if (CCproxy.checkLicense(c.licensedAgents, c.licenseKey, c.licenseExpires)){
				p.c=c;
				log.info("Config reload OK");
				out.println("Config reload OK");
			} else
			{
				log.info("Config reload FAILED");
				out.println("Config reload FAILED");
			};
			return true;
		}
		if (line.equals("status")){
			this.agentName="CMD";
			out.println("Config file: " + p.c.configFile);
			out.println("Version: " + CCproxy.version);
			out.println("Licensed agents: " + p.c.licensedAgents);
			out.println("License expires: " + p.c.licenseExpires);
			
			return true;
		}
		if (line.startsWith("pause")){
			// pause agent '302' value 'true' type 'wrap-up'
			this.agentName="CMD";
			Matcher m = Pattern.compile("pause agent '([^']*)' value '([^']*)' type '([^']*)'.*").matcher(line);
			if (!m.matches()) return true;
			String agent = m.group(1);
			String exten;
			try {
				exten = p.coreState.findAgent(new RealAgent(agent,"","")).getExtension();
			} catch (AgentNotFoundException e) {
				out.println("ERR");
				return true;
			}
			String str="EVENT [ProxyClient], Action [Pause], Value ["+m.group(2)+"], Type ["+m.group(3)+"], Agent ["+agent+"], Exten ["+exten+"]";
			log.info(str);
			p.gadajAsterisk.sendMessage(this, str);
			out.println("OK");
			return true;
		}
		if (line.startsWith("allowed pausetypes agent")){
			//allowed pausetypes agent '304' values 'przerwa','zadanie służbowe'
			this.agentName="CMD";
			Matcher m = Pattern.compile("allowed pausetypes agent '([^']*)' values (.*)").matcher(line);
			if (!m.matches()) return true;
			String agent = m.group(1);
			String pausetypestr = m.group(2);
			RealAgent ra;
			try {
				ra = p.coreState.findAgent(new RealAgent(agent,"",""));
			} catch (AgentNotFoundException e) {
				out.println("ERR");
				return true;
			}
			ArrayList<String> pausetypes=null;
			if (! pausetypestr.equals("all")){
				pausetypes=new ArrayList<String>();
				if (! pausetypestr.equals("none")){
					Matcher ma=Pattern.compile("'([^']*)'").matcher(pausetypestr);
					while (ma.find()){
						pausetypes.add(ma.group(1));
					}
				}
			}	
			ra.setAllowedPauseTypes(pausetypes);
			out.println("OK");
			return true;
		}
		if (line.startsWith("allowed pausetypes queue")){
			//allowed pausetypes queue 'kolejka' values 'przerwa','zadanie służbowe'
			log.debug(line);
			this.agentName="CMD";
			Matcher m = Pattern.compile("allowed pausetypes queue '([^']*)' values (.*)").matcher(line);
			if (!m.matches()) return true;
			String queue = m.group(1);
			String pausetypestr = m.group(2);
			RealQueue rq=null;
			try {
				rq=p.coreState.findQueue(new RealQueue(queue, 0));
			} catch (QueueNotFoundException e) {
				out.println("ERR");
				return true;
			}
			ArrayList<String> pausetypes=null;
			if (! pausetypestr.equals("all")){
				pausetypes=new ArrayList<String>();
				if (! pausetypestr.equals("none")){
					Matcher ma=Pattern.compile("'([^']*)'").matcher(pausetypestr);
					while (ma.find()){
						pausetypes.add(ma.group(1));
					}
				}
			}
			rq.setAllowedPauseTypes(pausetypes);
			out.println("OK");
			return true;
		}
        if (line.startsWith("queue add")){
            log.debug(line);
            this.agentName="CMD";
            Matcher m = Pattern.compile("queue add name '([^']*)' agent ([^ ]*) penalty ([^ ]*)").matcher(line);
            if (!m.matches()) {
                out.println("ERR no match");
                return true;
            }
            String queuename=m.group(1);
            String agent = m.group(2);
            String penalty = m.group(3);
            AgentConnection ac = p.agenci.getAgentConnectionByNumer(agent);
            if (ac==null) {
                out.println("ERR");
                return true;
            }
            ac.queuePenalty.put(queuename, penalty);
            p.gadajAsterisk.sendMessage(ac,"addqueue:"+queuename);
            out.println("OK");
            return true;
        }
        if (line.startsWith("queue remove")){
                log.debug(line);
                this.agentName="CMD";
                Matcher m = Pattern.compile("queue remove name '([^']*)' agent ([^ ]*)").matcher(line);
                if (!m.matches()) {
                    out.println("ERR no match");
                    return true;
                }
                String queuename=m.group(1);
                String agent = m.group(2);
                AgentConnection ac = p.agenci.getAgentConnectionByNumer(agent);
                if (ac==null) {
                    out.println("ERR");
                    return true;
                }
                p.gadajAsterisk.sendMessage(ac,"removequeue:"+queuename);
                out.println("OK");
                return true;
            }


        if (line.startsWith("agent logout")){
            log.debug(line);
            this.agentName="CMD";
            Matcher m = Pattern.compile("agent logout agent ([^ ]*)").matcher(line);
            if (!m.matches()) {
                out.println("ERR no match");
                return true;
            }
            String agent = m.group(1);
            AgentConnection ac = p.agenci.getAgentConnectionByNumer(agent);
            if (ac==null) {
                out.println("ERR");
                return true;
            }
            p.log.info("Wylogowuję agenta " + agentNumber);
            ac.stopPingThread();
            ac.out.println("+INFO Type 'connection_replaced', Message 'Supervisor wylogował Cię z systemu'");
            p.log.info("+INFO Type 'connection_replaced', Message 'Supervisor wylogował Cię z systemu'");
            p.asteriskQueueList.removeAgentConnection(ac);
            p.agenci.remove(ac);
            ac.out.close();
            out.println("OK");
            return true;
        }

            if (line.startsWith("agent login")){
                log.debug(line);
                //this.agentName="CMD";
                Matcher m = Pattern.compile("agent login agent ([^ ]*) exten ([^ ]*)").matcher(line);
                if (!m.matches()) {
                    this.agentName = "CMD";
                    out.println("ERR no match");
                    return true;
                }
                agentNumber = m.group(1);
                agentExten = m.group(2);
                offline=true;
                out.println("OK");
            }


        if (line.startsWith("update queues")) {
            log.debug(line);
            this.agentName = "CMD";
            if (p.asteriskQueueList.updateFromDb()) {
                out.println("OK");
            } else {
                out.println("ERR");
            }
            return true;
        }

        if (line.startsWith("send raw")){
            log.debug(line);
            this.agentName="CMDWAIT";
            Matcher m = Pattern.compile("send raw (.*)").matcher(line);
            if (!m.matches()) {
                out.println("ERR no match");
                return true;
            }
            String raw_str=m.group(1);
            p.gadajAsterisk.sendMessage(this,"Event [Raw], "+raw_str);
        }

        }
        if (!offline){
		line = line.replaceAll("\\s", "");
		String arr[] = line.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].indexOf(':') > 0) {
				String param[] = arr[i].split(":",2);
				if (param.length == 2) {
					if (param[0].equals("agent")) {
						agentNumber = param[1];
						continue;
					}
					if (param[0].equals("password")) {
						agentPassword = param[1];
						continue;
					}
					if (param[0].equals("exten")) {
						agentExten = param[1];
						continue;
					}
					p.log.info("Unknown parametr: " + arr[i]);
				} else {
					p.log.error("Brak parametru dla: " + param[0]);
					return false;
				}
			}
		}
        }
		if ((agentNumber != null && agentPassword != null && agentExten != null) || offline) {
			p.log.info("+OK agent: " + agentNumber + ", exten:" + agentExten
					+ ", password: " + "*****");
		} else
			return false;

		try {
		if (!offline && agentExten.length()!=0 && !Pattern.matches("^[1-9][0-9]*$", agentExten)) { //exten nie-cyfrowy
			return false;
		}

		if (!Pattern.matches("^[0-9]*$", agentNumber)||offline){
			// autoryzacja po loginie 
			// return false;
			
			if (p.c.externAuthURL!=null){
				if (!externAuth(p.c.externAuthURL, agentNumber, agentPassword))
					return false;
			}

            String querystr="select va.nazwisko, va.numer, va.supervisor, tu.haslo as md5pass " +
                    "from v_agent va join t_user tu on va.id_user=tu.id_user where tu.login='"
                    + agentNumber + "'";
            if (offline){
                querystr="select va.nazwisko, va.numer, va.supervisor, tu.haslo as md5pass " +
                        "from v_agent va join t_user tu on va.id_user=tu.id_user where va.numer='"
                        + agentNumber + "'";
            }
			rs = p.dbConn
				.query(querystr);
			if ((rs != null) && rs.next()) {
				if (p.c.externAuthURL==null){
					String md5Pass = new String(rs.getString("md5pass"));
					if (!offline && !md5Pass.equals(MD5Crypt.crypt(agentPassword, md5Pass)))
						return false;
				} 
			} else return false;
		} else {
			//rs = p.dbConn
			//	.query("select nazwisko, numer, supervisor from v_agent where numer='"
			//			+ agentNumber + "' and haslo='" + agentPassword + "'");
			//if ((rs != null) && rs.next()) {
			//	
			//} else return false;
			return false; //nie ma juz autoryzacji "agentem"
		}
		
		{
				agentName = new String(rs.getString("nazwisko"));
				agentNumber = new String(rs.getString("numer"));
				if (rs.getString("supervisor").equals("t"))
					supervisor = true;
				
				if (agentExten.length()==0){
					rs = p.dbConn
					.query("select vus.numer from v_agent va join v_user_sip vus on va.id_user=vus.id_user where va.numer='"
							+ agentNumber + "'");
					if ((rs != null) && rs.next()){
						agentExten = new String(rs.getString("numer"));
					} else return false;
				}
				
				out.println("Auth success.");
				setThreadName();
				p.log.info("Database auth success.");
				agentMakeQueuePenalty(agentNumber);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("+OK autoryzacja w bazie danych i po niej juz.");

		return false;

	}

	private boolean externAuth(String url, String agentName, String password){
		boolean authorized=false;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.getOutputStream().write(("user="+agentName).getBytes("UTF-8"));
			con.getOutputStream().write("&".getBytes("UTF-8"));
			con.getOutputStream().write(("pass="+password).getBytes("UTF-8"));
			//con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null){
				if ("OK".equals(inputLine)) 
					authorized=true;
			}
			br.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authorized;
	}
	
	private boolean dumpstruct() {

		try {
			GadajAsterisk.agentListLock.lock();
			out.println("agenci: size:" + p.agenci.size());
			for (int i = 0; i < p.agenci.size(); i++) {
				out.println("\tagent: number: " + p.agenci.get(i).agentNumber
						+ ", exten: " + p.agenci.get(i).agentExten + ", name: "
						+ p.agenci.get(i).agentName + ", paused: "
						+ p.agenci.get(i).paused + ", idconnection: "
						+ p.agenci.get(i).idConnection + ", ip: "
						+ p.agenci.get(i).ip + ", port: "
						+ p.agenci.get(i).port + ", supervisor: "
						+ p.agenci.get(i).supervisor);
			}
		} finally {
			GadajAsterisk.agentListLock.unlock();
		}
			out.println("queuelist: size:" + p.asteriskQueueList.size());
			for (int i = 0; i < p.asteriskQueueList.size(); i++) {
				out.println("\tqueuelist: nazwa: "
						+ p.asteriskQueueList.get(i).nazwa + ", liczba: "
						+ p.asteriskQueueList.get(i).ilosc + ", agentow: "
						+ p.asteriskQueueList.get(i).agenci.size());
				for (int j = 0; j < p.asteriskQueueList.get(i).agenci.size(); j++) {
					out
							.println("\t\tagent: number: "
									+ p.asteriskQueueList.get(i).agenci.get(j).ac.agentNumber
									+ ", exten: "
									+ p.asteriskQueueList.get(i).agenci.get(j).ac.agentExten
									+ ", name: "
									+ p.asteriskQueueList.get(i).agenci.get(j).ac.agentName
									+ ", penalty: "
									+ p.asteriskQueueList.get(i).agenci.get(j).penalty
									+ ", paused: "
									+ p.asteriskQueueList.get(i).agenci.get(j).paused);
				}
			}
		synchronized (GadajAsterisk.wiadomosciOdAsteriska) {
			out.println("wiadomosci Asterisk: size:"
					+ p.gadajAsterisk.wiadomosciOdAsteriska.size());
			for (int i = 0; i < p.gadajAsterisk.wiadomosciOdAsteriska.size(); i++) {
				out
						.println("\tWiadomosci: wiadomosc: "
								+ p.gadajAsterisk.wiadomosciOdAsteriska.get(i).wiadomosc
								+ ", numer: "
								+ p.gadajAsterisk.wiadomosciOdAsteriska.get(i).wiadomosc
								+ ", wyslana: "
								+ p.gadajAsterisk.wiadomosciOdAsteriska.get(i).wyslana);
			}
		}
		try {
			GadajAsterisk.agentListLock.lock();
			out.println("Wiadomosci agent: size:"
					+ p.gadajAsterisk.wiadomosciOdAgenta.size());
			for (int i = 0; i < p.gadajAsterisk.wiadomosciOdAgenta.size(); i++) {
				out
						.println("\tWiadomosci: wiadomosc: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).wiadomosc
								+ ", numer: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).numer
								+ ", wyslana: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).wyslana
								+ ", Anumer: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).agent.agentNumber
								+ ", AExten: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).agent.agentExten
								+ ", AName: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).agent.agentName
								+ ", param: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).param
								+ ", time: "
								+ p.gadajAsterisk.wiadomosciOdAgenta.get(i).time.toString());
			}
		} finally {
				GadajAsterisk.agentListLock.unlock();
		};
		return false;
	}

	public boolean checkPing(Thread pingThread, Thread mainThread) {

		Date now = GregorianCalendar.getInstance().getTime();
		if ((now.getTime() - last_ping.getTime()) > 60000) {
			p.log.error("Koncze polaczenie z agentem: brak pinga");
			cleanup(pingThread, mainThread, false);
			return false;
		}
		if (p.c.monitorDbActivity && (now.getTime() - last_db_activity.getTime()) > 10000) {
			String datestr=p.dbConn.quickQuery("SELECT last_db_activity from v_agent_activity where numer="+agentNumber);

			Date dt=null;
			try {
				dt=dateFormatsec.parse(datestr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (dt!=null && dt.after(last_db_activity)) last_db_activity=dt;
			if ((now.getTime() - last_db_activity.getTime()) > 30000) {
				p.log.error("Koncze polaczenie z agentem: brak db activity od "+last_db_activity.getTime());
				((AgentConnection)mainThread).out.println("+INFO Type 'connection_replaced', Message 'Zamknięto przeglądarkę'" );
				p.log.info("+INFO Type 'connection_replaced', Message 'Zamknięto przeglądarkę'" );
				((AgentConnection)mainThread).out.close();
				((AgentConnection)mainThread).in.close();

				cleanup(pingThread, mainThread, true);
				//return false;
			}
		}
		return true;
	}

	public boolean isNewConnected() {
		return newConnected ;
	}

	public void setNewConnected(boolean newConnected) {
		this.newConnected = newConnected;
	}
	
	private void setThreadName(){
		setName("T-AgentConn:"+idConnection+"_"+agentNumber);
	}

	
}

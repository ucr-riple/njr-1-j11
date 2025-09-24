package ccproxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.NetPrintWriter;
import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.CommandUnknown;
import pl.cc.core.cmd.EventProxyClientCallTag;
import pl.cc.core.cmd.EventProxyClientPause;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealAgentList;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class GadajAsterisk extends Thread {
	static Logger logMessageFromAsterisk = Logger.getLogger("logMessageFromAsterisk");
	static Logger log = Logger.getLogger(GadajAsterisk.class);

	static String asteriskPort;
	static int numer = 0;
	static String asteriskIP;
	static String asteriskUser;
	static String asteriskPass;

	static Socket echoSocket = null;
	static PrintWriter out = null;
	static BufferedReader in = null;
	boolean asteriskReader = true;
	static boolean connected = true;

	static Parametry p;

	static WiadomoscOdAgentLlist wiadomosciOdAgenta = new WiadomoscOdAgentLlist();
	static WiadomoscOdAsteriskaList wiadomosciOdAsteriska = new WiadomoscOdAsteriskaList();

	static ReentrantLock agentListLock= new ReentrantLock(true);

	public GadajAsterisk() {
        asteriskReader=false;
	}

	public GadajAsterisk(String asteriskIP, String asteriskPort,
			String asteriskUser, String asteriskPass, Parametry parametry)
			throws Exception {
		super();
		this.p = parametry;
		this.asteriskPort = asteriskPort;
		this.asteriskIP = asteriskIP;
		this.asteriskUser = asteriskUser;
		this.asteriskPass = asteriskPass;

		try {
			if (echoSocket == null) {
				echoSocket = new Socket(asteriskIP, new Integer(asteriskPort)
						.intValue());
				out = new PrintWriter(echoSocket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(echoSocket
						.getInputStream()));

				// Autoryzacja do asteriska
				if (!authAsterisk())
					throw new Exception();
				// Listujemy kolejki wszystkie i zbieramy info
				sendAction("queuestatus");
			}
		} catch (UnknownHostException e) {
			System.err.println("Problem z połączeniem do Asteriska");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Problem z połączeniem do Asteriska");
			System.exit(1);
		}
	}

	public void close() {
		try {
			echoSocket.close();
			asteriskPort = null;
			numer = 0;
			asteriskIP = null;
			asteriskUser = null;
			asteriskPass = null;

			echoSocket = null;
			out = null;
			in = null;
			asteriskReader = true;
			connected = true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendAction(String action) {
		out.print("Action: " + action + "\r\n\r\n");
		out.flush();
	}

	private boolean authAsterisk() throws IOException {
		String line = in.readLine();
		System.out.println(line);

		out.print("Action: login\r\n");
		out.print("username: " + asteriskUser + "\r\n");
		out.print("secret: " + asteriskPass + "\r\n");
		out.print("\r\n");
		out.flush();
		line = in.readLine();
		System.out.println(line);
		if (line.equals("Response: Success")) {
			return true;
		}

		return false;
	}

	public void run() {
		setName("T-GadajAst");
		try {
			// sleep(500);
			sleep(0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		if (asteriskReader) {
			setName("T-GadajAstReader");
			MessageFromAsterisk message = new MessageFromAsterisk(p);
            boolean fullybooted=false;
            String line="";
            log.info("Waiting for Asterisk to fully boot...");
            while (!fullybooted||line.length() != 0){
                line = in.readLine();
                if (line.equals("Event: FullyBooted")) fullybooted=true;
            }
            log.info("Asterisk fully booted");
			while (true) {
				try {
					line = in.readLine();
					logMessageFromAsterisk.trace(line);
					// System.out.println(line);
					if (line != null) {

						// p.log.info("ok, line: "+ line);
						if (line == null)
							continue;
						if (line.length() == 0) {
							message.showEvent();
							message.parseEvent(wiadomosciOdAsteriska);
							// message.send();
							message = new MessageFromAsterisk(p);
							// p.asteriskQueueList.showQueues();
						
						} else
							message.parseLine(line);
					} else {
						connected = false;
						sleep(2000);// zeby nie bylo exceptionsow,bo musi sie
									// nowe polaczenie zestawić i w ogole
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			while (true) {
				try {
					if (!wyslijWiadomosci() & !wyslijOdpowiedz()) 
						sleep(100);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void sendMessage(AgentConnection agent, String outputLine) {
		sendMessage(agent, outputLine, true);
	}

	public void sendMessage(AgentConnection agent, String outputLine,
			boolean printConfirmation) {
		WiadomoscOdAgenta w;
		Date time = GregorianCalendar.getInstance().getTime();

		// obsługa kolumn z wieloma argumentami typu: EVENT [ProxyClient], Action [Pause], Value [True], Type [Default], Agent [313], Exten [874]
		Command c = Command.factory(outputLine, null);
		
		if  (! (c instanceof CommandUnknown)){
			p.log.info("We got NG command: "+c.getClass()+ " | "+c.getOrginalLine());
			if (c instanceof EventProxyClientPause || c instanceof EventProxyClientCallTag){
				if (c instanceof EventProxyClientPause)  //supervisor pauzować może nie siebie
					agent=p.agenci.getAgentConnectionByNumer(((EventProxyClientPause)c).getAgent().getId().toString()); 
				w = new WiadomoscOdAgenta(agent, numer++, time, c);
				try{
					agentListLock.lock();
					wiadomosciOdAgenta.add(w);
				} finally{
					agentListLock.unlock();
				}
			} else {
				p.log.warn("I can't process: "+c.getClass());
			}
			return;
		}
		
		// tu po staremu
		outputLine = outputLine.replaceAll("\\s", "");
		
		// komendy z argumentami
		if (outputLine.contains(":")) {
			p.log.info("+INFO : command '" + outputLine);
			String sline[] = outputLine.split(":");
			if (sline.length == 2) {
				w = new WiadomoscOdAgenta(agent, sline[0], sline[1], numer++);
				w.time = time;
				try{
					agentListLock.lock();
					wiadomosciOdAgenta.add(w);
				} finally{
					agentListLock.unlock();
				}
				if (printConfirmation)
					agent.out.println("+OK '" + outputLine + "'"); // FIXME
																	// dorobic
																	// sprawdzanie,
																	// czy jest
																	// taka
																	// wiadomosc
				return;
			} else {
  
				
			}
		}else{
		// komendy bezargumentowe
		if (Commands.COMMANDS.contains(outputLine)) {
			p.log.info("+INFO New command '" + outputLine + "' from agent: "
					+ agent.agentNumber + ", from IP: " + agent.ip + ":"
					+ agent.port + ", num: "+(numer+1));
			if (printConfirmation)
				agent.out.println("+OK '" + outputLine + "'");
			w = new WiadomoscOdAgenta(agent, outputLine, numer++);
			w.time = time;
			try{
				agentListLock.lock();
				wiadomosciOdAgenta.add(w);
			} finally{
				agentListLock.unlock();
			}
		} else {
			p.log.info("+INFO Unknown command '" + outputLine
					+ "' from agent: " + agent.agentNumber + ", from IP: "
					+ agent.ip);
			if (printConfirmation)
				agent.out.println("+ERR No command '" + outputLine + "'");
		}
		}
	}

	public boolean wyslijWiadomosci() {
		boolean sent;
		Date now = GregorianCalendar.getInstance().getTime();
		AsteriskCmd cmd = new AsteriskCmd(p);
		Events e = new Events(p);
		WiadomoscOdAsteriska wa = new WiadomoscOdAsteriska();
		/* synchinizacja */
		long t0=new Date().getTime();
		long t1;
		try{
			agentListLock.lock();
			t1=new Date().getTime();
			//p.log.info("SYN ms"+(t1-t0));
			int wSize=wiadomosciOdAgenta.size();
			if (wSize==0) return (false);
            sent = false;
            for (int i = 0; (i < wiadomosciOdAgenta.size() && i<20) ; i++) {
				WiadomoscOdAgenta w = wiadomosciOdAgenta.get(i);
				if (w.wyslana) {
					if (now.getTime() - w.time.getTime() > 30000) { //30 sekund uplywa...
						p.log.info("Usuwam przeterminowana wiadomosc: "
								+ w.wiadomosc);
						wiadomosciOdAgenta.remove(w);
						i--;
					}
					continue;
				}
				w.wyslana = true;

				// interpretacja nowych cmd NextGeneration ;)
				if (w.getCmd() != null){
					if (w.getCmd() instanceof EventProxyClientPause){
						EventProxyClientPause arg = (EventProxyClientPause)w.getCmd();
						String agentNumber = arg.getAgent().getId().toString();
						String agentExt = arg.getAgent().getExtension();
						RealAgent agent=null;
						try {
							agent=p.coreState.findAgent(arg.getAgent());
						} catch (AgentNotFoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						if (arg.isPause()){
							String pType=arg.getPauseType();
							//sprawdzamy czy może się pauzować
							//agent.get
							boolean pauseAllowed=true;
							for (RealQueue rq:p.coreState.findQueuesForAgent(agent).getValues()){
								pauseAllowed=pauseAllowed && rq.isAllowedPauseType(pType);
							}
							pauseAllowed=pauseAllowed && agent.isAllowedPauseType(pType);
							if (pauseAllowed){
								log.debug("allowed pause "+pType);
							} else {
								log.debug("DISallowed pause "+pType);								
								continue;
							}
							
							out.print(cmd.pause(w.numer, agentNumber, agentExt, arg.getPauseType()));
							out.print(cmd.setPause(agentNumber));
						} else {
							AgentStatusInQueue status=AgentStatusInQueue.UNKNOWN;
							try {
								log.debug(status=p.coreState.findAgent(arg.getAgent()).getStatus());
							} catch (AgentNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (!(p.c.pauseWhenUnavailable && status.equals(AgentStatusInQueue.UNKNOWN)))
							{
							w.agent.setNewConnected(false);
							out.print(cmd.unpause(w.numer, agentNumber, agentExt));
							out.print(cmd.setUnpause(agentNumber));
							}
							else
							{
								p.log.info("Ignoring unpause request from unavailable agent");
								wiadomosciOdAgenta.remove(w);
								i--;
							};	
						}
						out.flush();
						sent = true;
					}
					if (w.getCmd() instanceof EventProxyClientCallTag){
						EventProxyClientCallTag ect = (EventProxyClientCallTag) w.getCmd();
						String dbstr="SELECT tag_call('"+ect.getCallID()+"','" +ect.getGroup()+"','"+ect.getTopic()+"','"+ect.getComment()+"','"+ect.getExtraFields()+"')";
						p.log.info(dbstr);
						ResultSet rs;
						rs = p.dbConn.query(dbstr);
						try {
							if (rs.next()) {
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        wiadomosciOdAgenta.remove(w);
                        i--;
                        sent=true;
					}
					continue;
				}
                if (w.wiadomosc.equalsIgnoreCase("CallTimeBad")){
                    p.log.info("message: "+w.wiadomosc);
                    String secel="0";
                    try {
                        RealCall rc=p.coreState.findAgent(new RealAgent(w.agent.agentNumber,"","")).getConnectedCall();
                        if (rc!=null) secel=new Long(rc.getSecElapsed()).toString();
                    } catch (AgentNotFoundException e1) {
                    }
                    p.agenci.pushSupervisorMessage("Event [Agent], Action [CallTimeBad], Agent ["+w.agent.agentNumber+"], Name ["+w.agent.agentName+"], Time ["+secel+"]");
                    wiadomosciOdAgenta.remove(w);
                    i--;
                    sent = true;
                    break;
                }
				if (w.wiadomosc.equals("login")) {
					p.log.info("LOGIN request from agent: "
							+ w.agent.agentNumber + ", extension: "
							+ w.agent.agentExten);
					if (cmd.login(w.agent.idConnection, w.agent.agentNumber,
							w.agent.agentExten) == true) {
						/*
						 * usuwamy od razu, bo nie przyjdzie potwierdzenie
						 * nigdy, bo logujemy tylko do CCproxy
						 */
						wiadomosciOdAgenta.remove(w);
						i--;
						wa.wiadomosc = "+OK LOGGED IN";
						/*
						 * po poprawnym zalogowaniu, dodajemy agenta do
						 * odpowiednich kolejek na podstawie bazy danych ale
						 * tylko, jesli to nie jest supervisor
						 */
						long tt1=new Date().getTime();
						
						w.agent.out.println("+OK LOGGED IN");
						addAgentToQueues(w.agent);
						e.login(w.agent);
						long tt2=new Date().getTime();
						p.log.trace("SUB LEN ms"+(tt2-tt1));

					} else {
						// TODO jest źle, trzeba wywalić delikwenta z systemu
						// raczej
					}
					out.print(cmd.saveAgentExten(w.agent.agentNumber,
							w.agent.agentExten));
					out.print(cmd.saveAgentNumber(w.agent.agentNumber,
							w.agent.agentExten));
					//out.print(cmd.setUnpause(w.agent.agentNumber));
					out.flush();
					sent = true;
					break;
				}
				if (w.wiadomosc.equals("logout")) {
					long tt1=new Date().getTime();

					p.log.info("message: "+w.wiadomosc);
					out.print(cmd.pause(w.numer, w.agent.agentNumber,
							w.agent.agentExten, w.param));
					out.flush();
					cmd.logout(w.agent.idConnection, w.agent.agentNumber);
					wa.wiadomosc = "+OK LOGGED OUT";
					w.agent.out.println("+OK LOGGED OUT");
					e.logout(w.agent);
					sent = true;
					long tt2=new Date().getTime();
					p.log.trace("SUB LEN ms"+(tt2-tt1));
					break;


				}

				if (w.wiadomosc.equalsIgnoreCase("Spy")) {
					p.log.info("message: "+w.wiadomosc);
					out.print(cmd.spy(w.numer, w.agent.agentNumber,  w.agent.agentExten, w.param));
					out.flush();
					sent = true;
				}
				
				if (w.wiadomosc.equals("pause")) {
					p.log.info("message: "+w.wiadomosc);
					out.print(cmd.pause(w.numer, w.agent.agentNumber,
							w.agent.agentExten, w.param));
					out.print(cmd.setPause(w.agent.agentNumber));
					out.flush();
					sent = true;
				}
				if (w.wiadomosc.equals("unpause")) {
					p.log.info("message: "+w.wiadomosc);
					out.print(cmd.unpause(w.numer, w.agent.agentNumber,
							w.agent.agentExten));
					out.print(cmd.setUnpause(w.agent.agentNumber));
					out.flush();
					sent = true;
				}

				if (w.wiadomosc.equals("addqueue")) {
					p.log.info("message: "+w.wiadomosc);
					String penalty;
					boolean firstQueue = true;
					if ((penalty = w.agent.queuePenalty.get(w.param)) == null)
						penalty = "0";

					// w momencie wejscia w pierwsza kolejke, ustawiamy w bazie
					// AstDB agentpause na 0
					Iterator<AsteriskQueue> it = p.asteriskQueueList.iterator();
					while (it.hasNext()) {
						AsteriskQueue aq = it.next();
						if (aq.agenci.containsNumer(w.agent.agentNumber)) {
							firstQueue = false;
							break;
						}
					}
					if (firstQueue && ! p.c.pauseWhenStartup) {
						out.print(cmd.setUnpause(w.agent.agentNumber));
						out.flush();
					}
					boolean paused;
                    if (w.agent.isNewConnected()){
                        paused = p.c.pauseWhenStartup;
                       // System.out.println("paused1="+paused);
                    }
                    else {
                        paused = p.agenci.getAgentConnectionByNumer(w.agent.agentNumber).paused;
                        //System.out.println("paused="+paused);
                    }
					out.print(cmd.addToQueue(w.numer, w.agent.agentNumber, w.agent.agentExten, w.param, penalty, paused));
					out.flush();
					sent = true;
				}
				if (w.wiadomosc.equals("removequeue")) {
					p.log.info("message: "+w.wiadomosc);
					out.print(cmd.removeFromQueue(w.numer, w.agent.agentNumber,
							w.agent.agentExten, w.param));
					out.flush();
					sent = true;
				}
				/*
				 * Ten typ wiadomosci jest od razu do usuniecia, bo nie trzeba
				 * czekac az asterisk to przetworzy Wiadomosc jest wysylana
				 * przez CCproxy
				 */
				if (w.wiadomosc.equals("getqueues")) {
					long tt1=new Date().getTime();
					p.log.info("message: "+w.wiadomosc);
					p.asteriskQueueList.printAgentQueuesToAgent(w.agent);
					wiadomosciOdAgenta.remove(w);
					i--;
					// out.print(cmd.removeFromQueue(w.numer,
					// w.agent.agentNumber, w.param));
					// out.flush();
					sent = true;
					long tt2=new Date().getTime();
					p.log.trace("SUB LEN ms"+(tt2-tt1));
					break;
				}
				/*
				 * Ten typ wiadomosci jest od razu do usuniecia, bo nie trzeba
				 * czekac az asterisk to przetworzy Wiadomosc jest wysylana
				 * przez CCproxy
				 */
				/*
				 * To wywoływane po zalogowaniu jest niezależnie od tego czy jestes supervisorem czy agentem
				 */
				if (w.wiadomosc.equals("getallqueues")) {
					long tt1=new Date().getTime();
					p.log.info("message: "+w.wiadomosc);
					p.asteriskQueueList.printAllLogedAgents(w.agent);
					p.asteriskQueueList.printAllQueuesToAgent(w.agent);
					long tt2=new Date().getTime();
					p.log.trace("SUB LEN ms"+(tt2-tt1));
					 tt1=new Date().getTime();

					w.agent.printCallInProgress();
					wiadomosciOdAgenta.remove(w);
					i--;
					// out.flush();
					sent = true;
					 tt2=new Date().getTime();
					p.log.trace("SUB LEN ms"+(tt2-tt1));
					break;
				}

				p.log.info("Wyslano komende do asteriska: " + w.wiadomosc
						+ ", numer: " + w.numer);
				if (!sent) {
					p.log.info("Wyslano komende do asteriska: " + w.wiadomosc
							+ ", numer: " + w.numer);

					w.agent.out.println("+ERR No command '" + w.wiadomosc + "'");
					//out.flush();
                    sent=true;
				}
			}
		} finally {
			agentListLock.unlock();
		}
		long t2=new Date().getTime();
		if (t2-t1>100) {
			p.log.warn("LEN ms"+(t2-t1));
		} else {
			p.log.trace("LEN ms"+(t2-t1));
		}
		return sent;

	}

	public boolean wyslijOdpowiedz() {
		WiadomoscOdAgenta wAgent;
		synchronized(wiadomosciOdAsteriska){
		if (wiadomosciOdAsteriska.size()==0) return false;	
		for (int i = 0; i < wiadomosciOdAsteriska.size(); i++) {
			WiadomoscOdAsteriska wAsterisk = wiadomosciOdAsteriska.get(i);
			if ((wAgent = getWiadomoscByNumer(wAsterisk.numer)) != null) {
				wAgent.agent.out.println(wAsterisk.wiadomosc);
				/*
				 * Odesłaliśmy odpowiedź do agenta - możemy usunąć wiadomość od
				 * niego.
				 */
				try{
					agentListLock.lock();
					wiadomosciOdAgenta.remove(wAgent);
				} finally{
					agentListLock.unlock();
				}
			}
			wiadomosciOdAsteriska.remove(i);
			i--;
		}
		}
		return true;
	}

	public WiadomoscOdAgenta getWiadomoscByNumer(int numer) {
		long t0=new Date().getTime();
		long t1,t2;
		try{
			agentListLock.lock();
			t1=new Date().getTime();
			if (t1-t0>100) {
				p.log.warn("SYN ms"+(t1-t0));
			} else {
				p.log.trace("SYN ms"+(t1-t0));
			}
			int wSize=wiadomosciOdAgenta.size();
			for (int i = 0; i < wSize; i++) {
				if (wiadomosciOdAgenta.get(i).numer == numer){
					 t2=new Date().getTime();
					return wiadomosciOdAgenta.get(i);}
			}
		} finally
		{
			agentListLock.unlock();
		}

		return null;
	}

	synchronized private void addAgentToQueues(AgentConnection a) {
		// TODO Auto-generated method stub
		// AgentConnection a;
		ResultSet rs;
		String qname;
		
			rs = p.dbConn
					.query("SELECT nazwa from v_agent_queue where numer ='"
							+ a.agentNumber + "'");
		
		try {
			while (rs.next()) {
				qname = new String(rs.getString("nazwa"));
				p.gadajAsterisk.sendMessage(a, "addqueue:" + qname, false);
				p.log.info("Automatyczne dodanie agenta '" + a.agentNumber
						+ "' do  kolejki:" + qname);
				// p.gadajAsterisk.out.print("Action: QueeAdd");
				System.out.println(a.agentExten);
				a.out.println("+INFO Queue '" + qname + "', Count '0'");
				// a.out.println("+INFO Queue '"+ qname +"', Count '" +
				// p.asteriskQueueList.getQueueByName(qname).ilosc + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Commands extends ArrayList<String> {
	public static final Commands COMMANDS = new Commands();

	public Commands() {
		add("logout");
		add("login");
		add("pause");
		add("unpause");
		add("getqueues");
		add("getallqueues");
		add("spy");
        add("exit");
        add("calltimebad");
	}
}

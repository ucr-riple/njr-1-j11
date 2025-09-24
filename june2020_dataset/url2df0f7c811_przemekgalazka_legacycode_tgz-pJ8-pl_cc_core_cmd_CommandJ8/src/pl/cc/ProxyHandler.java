package pl.cc;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.log4j.Logger;

import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.LoginCredentials;
import pl.cc.core.NetPrintWriter;
import pl.cc.core.PauseType;
import pl.cc.core.PauseTypeList;
import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.ErrSameAgentConnection;
import pl.cc.core.cmd.EventAgentLogin;
import pl.cc.core.cmd.InfoType;
import pl.cc.core.cmd.InfoVersion;
import pl.cc.core.cmd.OkWelcome;
import pl.cc.events.real.RealAgentListener;
import pl.cc.exceptions.AgentNotFoundException;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

import javax.net.ssl.*;

import static pl.cc.Localization.getText;

/**
 * Klasa utrzymująca połączenie do CCProxy
 * - wysyłanie pingów
 * - przyjmowanie eventów od CCProxy i translacja ich na Command
 * - wysyłanie otrzymanych Commands do zainteresowanych listenerów
 * 
 * Klasa implementująca ProxyTasks - czyli zestaw poleceń, które może wykonać CCProxy
 *  - doSpyAgent
 *  - doPauseAgent
 * @since 2009-05-27
 */
public class ProxyHandler implements ProxyEventListener, ProxyTasks, RealAgentListener { 
	static final Logger log = Logger.getLogger(ProxyHandler.class);
	private static final int SLEEP_BEFORE_RECONNECT = 10;
	ProxyEventListenerList listenerList = new ProxyEventListenerList();

	protected OkWelcome selfInfo;
	protected String host;
	
	// Agent którym "jesteśmy podłączeni" do CCProxy
	protected RealAgent connectedAgent;
	
	protected RealAgentListener connectedAgentListener;
	
	private Ping ping = null;
	private Socket socket = null;
	protected NetPrintWriter out = null;
	private ReaderThread readerThread;
	private LoginCredentials credentials;
	PauseType lastPauseType = null;
	boolean lastPaused = false;


	private String applicationVersion;
	/**
	 * Czy wykonywać domyślnie automatyczny reconnect po rozłączeniu
	 */
	private boolean defaultReconnect;
	private boolean reconnect;
	private boolean reconnecting=false;

	// Jesteďż˝my w trakcie zamykania socketu do CCProxy
	private boolean closingSocket = false;

	
	/**
	 * Tutaj przechowywujemy stan wnďż˝trza systemu CCProxy.
	 * Budowany na podstawie zdaďż˝eďż˝ pďż˝ynďż˝cych z CCProxy
	 * FIXME: w chwili obecnej budowany jest tylko jeďż˝li jesteďż˝my zalogowani jako supervisor
	 */
	protected SystemCoreState systemState = null;
	
	protected int port;
    private boolean useSSL;
    private boolean ignoreCert;


    public ProxyHandler(String applicationVersion, boolean defaultReconnect, String host, int port, boolean useSSL, boolean ignoreCert){
        addEventListener(this);
        this.applicationVersion = applicationVersion;
        this.defaultReconnect = defaultReconnect;
        this.host=host;
        this.port=port;
        this.useSSL=useSSL;
        this.ignoreCert=ignoreCert;
    }

 	public void addEventListener(ProxyEventListener proxyEventListener) {
		listenerList.add(proxyEventListener);		
	}
	
	public boolean removeEventListener(ProxyEventListener proxyEventListener) {
		return listenerList.remove(proxyEventListener);
	}
	
	public SystemCoreState getSystemState() {
		return systemState;
	}
	/**
	 * @return podďż˝ďż˝czony, autoryzowany agent
	 */
	public RealAgent getConnectedAgent() {
		return connectedAgent;
	}
	
	private void disconnectAndCleanup(){
		log.debug("cleanupAfterDisconnect - started ...");
		if (connectedAgent!=null){
			lastPauseType=connectedAgent.getPauseType();
			lastPaused=connectedAgent.isPaused();
		}
		if (lastPauseType!=null && lastPauseType.isAuto()) lastPaused=false;
		systemState = null;
		connectedAgent = null;
		closeTCPSocket();
		
		if (ping != null){
			ping.stopThread();
			// listenerList.remove(ping);
			ping=null;
			log.debug("cleanupAfterDisconnect - ping stoped");
		}
	}
	
	/**
	 * Zamkniďż˝cie i posprzďż˝tanie po TCP Socket do poďż˝ďż˝czenia z CCProxy
	 */
	private void closeTCPSocket() {
		if (closingSocket || socket == null) {
			return;
		} else {
			// Zabezpiecznie przed dwukrotnym, rďż˝wnoczesnym wykonaniem
			closingSocket = true;
			log.info("Closing TCP socket ...");
			out.close();
			try {
				readerThread.closeInputStream();
			} catch (IOException e) {
				log.warn(e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				log.warn(e);
			}
			log.debug("Socket isClosed(): "+socket.isClosed());
			socket = null;
			log.info("Socket closed ...");
			closingSocket = false;
		}
	}
	
	
	public void doSendCallTag(String[] tag){
		String flatComment = tag[3].replaceAll("\\n", " ");
		String line="EVENT [ProxyClient], Action [CallTag], Callid["+tag[0]+"], Group ["+tag[1]+"], Topic ["+tag[2]+"], Comment ["+flatComment+"]";
		if (!tag[4].equals("")){
			line=line+", Extrafields ["+tag[4]+"]";
		}
		log.info("sending: "+line);	
		out.println(line);		
	}
	
	@Override
	public void onProblemWithPing() {
		log.info("onProblemWithPing");
		disconnectAndCleanup();
		if (reconnect){
			doReconnect();
		}
	}
	
	@Override
	public void onAuthorized(OkWelcome selfInfo) {
		this.selfInfo = selfInfo;
		connectedAgent = new RealAgent(selfInfo.getAgentNumber(), selfInfo.getName(), selfInfo.getExtension());

		log.info("Creating new instance SystemCoreState");
		systemState = new SystemCoreState();
	}

	@Override
	public void onAuthResponse(boolean authorized) {
		log.info("onAuthResponse: "+authorized);
		if (authorized) {
			ping = new Ping(listenerList, out);
			listenerList.add(ping);
			ping.start();
			if (reconnecting) doPauseAgent(connectedAgent, lastPaused, lastPauseType);
		} else {
			reconnect = false;
			disconnectAndCleanup();
		}
	}

	@Override
	public void onDisconnect() {
		log.info("onDisconnect");
		disconnectAndCleanup();
		if (reconnect){
			log.debug("Reconnecting after disconnect");
			doReconnect();
		}
	}

	@Override
	public void onEvent(Command command) {
		if (systemState!=null){
			try {
				systemState.process(command, this);
			} catch(Exception e){
			}
		} 
		if (command instanceof EventAgentLogin){
			RealAgent agent = ((EventAgentLogin)command).getAgent();
			try{
                int id = new Integer(selfInfo.getAgentNumber());
                if (agent.getId()==id) {
					try {
						connectedAgent = systemState.findAgent(agent);
						connectedAgent.addAgentListener(this);
						connectedAgent.addAgentListener(connectedAgentListener);
						log.info("Registered as listener for: "+connectedAgent);
					} catch (AgentNotFoundException e) {
						log.error(e);
					}
				}
			} catch (NumberFormatException e) {
			}
		}
	}

	@Override
	public void onInfoVersion(InfoVersion infoVersion) {
		log.debug("onInfoVersion: "+infoVersion);
		try {
			String requiredVersion = infoVersion.getVersion();
			if (requiredVersion.compareTo(applicationVersion)>0) {
				log.error("Incorrect application version. applicationVersion: "+applicationVersion+" requiredVersion: "+requiredVersion);
				listenerList.onInvalidVersion(applicationVersion, infoVersion);
			} else {
				log.info("Correct application version. applicationVersion: "+applicationVersion+" requiredVersion: "+requiredVersion);
			}
		} catch (NumberFormatException e){
			log.error(e);
		}
	}

	@Override
	public void onTryConnect(boolean successfully, String host) {
		log.debug("onTryConnect succesfully: "+successfully+" host: "+host);
		if (!successfully && reconnect) {
			doReconnect();
		}
	}

	@Override
	public void onPong(Date now) {
	}

	@Override
	public void onWelcome() {
		log.info("onWelcome");
		sendLoginCredentials();
	}

	protected boolean sendLoginCredentials(){
		if (credentials!=null&&credentials.areComplete()) {
			log.debug("Sending login credentials");
			String connParam = "agent:" + credentials.username + ", "+ "exten:" + credentials.exten + ", "+ "password:" + credentials.password;
			out.println(connParam);
			return true;
		} else {
			log.debug("Can't send login credentials. Credentials are incomplete");
			return false;
		}
	}
	
	@Override
	public void onInvalidVersion(String applicationVersion, InfoVersion infoVersion) {
		defaultReconnect = false;
		reconnect = false;
		disconnectAndCleanup();
	}
	
	@Override
	public void onStartReconnectProcedure(int sleepBeforeReconnect) {
				
	}

	public void onInfoType(InfoType cmd) {
		reconnect=reconnect&cmd.getReconnect();
	}
	
	@Override
	public void onConnectionReplaced(ErrSameAgentConnection connectionReplaced) {
		log.info("Connection replaced from ip: "+connectionReplaced.getIp());
		reconnect = false;
		disconnectAndCleanup();
	}
	
	/**
	 * Otrzymanie komendy unpause z CCProxy - (wyjďż˝cie z pauzy)
	 */
	protected void getCmdUnpaused() {
	}
	
	/**
	 * Otrzymanie komendy pause z CCProxy
     * @param pauseType - tryb pauzy
     * @param timePoor
     * @param timeBad
     */
	protected void getCmdPaused(PauseType pauseType, int pauseTime, int timePoor, int timeBad) {
	}

	
	public static void main (String [] args){
//		LoginCredentials agent = new LoginCredentials("301", "1234", "877");
//		LoginCredentials supervisor = new LoginCredentials("200", "1234", "877");
//		ProxyHandler sp = new ProxyHandler("12", true);
//		sp.doConnect("localhost", 5000, supervisor);
//
//		try {
//			Thread.sleep(5000);
//			sp.doDisconnect();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	
	}

	private void doReconnect(){
		log.debug("Reconecting. Going sleep for 10 sec ...");
		reconnecting=true;
		new ReconnectThread(this, SLEEP_BEFORE_RECONNECT).start();
	}



	/**
	 * - nawiďż˝zuje poďż˝ďż˝czenie po TCP
	 * - startuje wďż˝tek Ping
	 * @return true jeďż˝li udaďż˝o siďż˝ nawiďż˝zaďż˝ poďż˝ďż˝czenie
	 */
	@Override
	public void doConnect(LoginCredentials loginCredentials) {
        this.credentials = loginCredentials;
		if(socket!=null && socket.isConnected()){
			log.warn("Already connected");
			return;
		}
        SSLSocketFactory sf= getSSLSocketFactory(ignoreCert);
        String msg=host;
		try {
			reconnect = defaultReconnect;
            if (useSSL){
                socket =  sf.createSocket(host,port);
                ((SSLSocket)socket).startHandshake();
            } else {
                socket = new Socket(host, port);
            }
			out = new NetPrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
			readerThread = new ReaderThread(listenerList, in, new Integer((int)(Math.random()*10000)).toString());
			readerThread.start();
			listenerList.onTryConnect(true, host);

			return;
		} catch (UnknownHostException e) {
			log.error("doConnect. Host: " + host + " Port: " + port + " Exception: " + e.getMessage());
		} catch (SSLHandshakeException e){
            log.error("doConnect. Host: " + host + " Port: " + port + " Exception: " + e.getMessage());
            msg=msg+": "+getText("Błąd certyfikatu");
        } catch (IOException e) {
			log.error("doConnect. Host: "+host+" Port: "+port+" Exception: "+e.getMessage());
		}
        try {if (socket!=null)socket.close();socket=null;} catch (IOException ie){};
        listenerList.onTryConnect(false, msg);

	}

    private SSLSocketFactory getSSLSocketFactory(boolean ignoreCert) {
        if (!ignoreCert) return (SSLSocketFactory) SSLSocketFactory.getDefault();

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception ee) {
        }
        return null;
    }

	@Override
	public void doSpyAgent(RealAgent agent) {
		boolean hasPrrivilege = systemState.hasPrivilegeForAgent(agent);
		if (hasPrrivilege){
			String cmd ="Spy:"+agent.getId();
			log.info("Sending spy conmmand: ["+cmd+"]");
			out.println(cmd);
		} else {
			log.debug("Insufficient privilege level to spy agent: "+agent);
		}
	}

	@Override
	public void doDisconnect() {
		reconnect = false;
		closeTCPSocket();
	}
	
	@Override
	public void doPauseAgent(RealAgent agent, boolean pause, PauseType pauseType) {
		// sprawdzenie czy mamy wystarczajďż˝cy poziom uprawnieďż˝ do pauzowania innego agenta
		if (pause && pauseType.isAdministrative() && !systemState.hasPrivilegeForAgent(agent)){
			log.debug("Insufficient privilege level to administrative pause agent: "+agent);
			if (connectedAgent.equals(agent)){
				// Jeďż˝li pauzujemy sami siebie - nie sprawdzamy uprawnieďż˝ i zmieniamy pauzďż˝ na domyďż˝lnďż˝
				pauseType=systemState.getPauseTypeList().getDefault();
				log.debug("Self pause request. Pause type changed to: "+pauseType.getName());
			} else {
				return;
			}
		}
		
		if (!pause && !systemState.hasPrivilegeForAgent(agent) && !connectedAgent.equals(agent)){
			log.debug("Can't unpause agent: "+agent);
			return; 
		}

		
		String type;
		if (pause){
			 type = pauseType.getName();
		} else {
			type = "n/a";
		}
		String s = "EVENT [ProxyClient], Action [Pause], Value ["+ new Boolean(pause).toString() +"], Type ["+ type +"], Agent ["+ agent.getId() +"], Exten ["+ agent.getExtension()+"]";
		log.info("sending: "+s);
		out.println(s);
	}

	@Override
	public void doPauseAgentAdministrative(RealAgent realAgent, boolean pause) {
		PauseType admPause = getPauseTypeAdministrative();
		doPauseAgent(realAgent, pause, admPause);
	}

	/**
	 * @return Definicja domyďż˝lnej pauzy
	 */
	protected PauseType getPauseTypeDefault(){
		if (systemState!=null){
			return systemState.getPauseTypeList().getDefault();
		}  else {
			log.warn("Default pause not found");
			return null;
		}
	}

	/**
	 * @return Definicja domyďż˝lnej administracyjnej
	 */
	protected PauseType getPauseTypeAdministrative(){
		if (systemState!=null){
			return systemState.getPauseTypeList().findById(PauseType.ID_ADMINISTRATIVE);
		}  else {
			log.warn("Administrative pause not found");
			return null;
		}
	}
	/**
	 * @return Lista zdefiniowanych w systemie pauz
	 */
	public PauseTypeList getPauseTypeList() {
		if (systemState!=null){
			return systemState.getPauseTypeList();
		}  else {
			return null;
		}
	}

	public LoginCredentials getCredentials() {
		return credentials;
	}
	
	class ReconnectThread extends Thread {
		ProxyHandler proxyHandler;
		int sleepTime;
		
		public ReconnectThread(ProxyHandler proxyHandler, int sleepTime) {
			this.proxyHandler = proxyHandler;
			this.sleepTime = sleepTime;
		}

		public void run(){
			try {
				Thread.sleep(200);
				proxyHandler.listenerList.onStartReconnectProcedure(sleepTime);
				Thread.sleep(sleepTime*1000-200);
			} catch (InterruptedException e) {
			}
			proxyHandler.doConnect(getCredentials());
		}
	}

	@Override
	public void onAgentConnect(RealAgent realAgent, RealCall call,
			RealQueue queue) {
	}

	@Override
	public void onAgentNewCall(RealCall call, RealQueue queue) {
	}

	@Override
	public void onCallLeave(RealAgent realAgent, RealCall callLeave,
			RealQueue queue) {
	}

	@Override
	public void onChangeStatusAsQueueMember(RealAgent realAgent,
			RealQueue queue, AgentStatusInQueue status) {
	}

	@Override
	public void onHangeup(RealAgent realAgent, RealCall call) {
	}

	@Override
	/**
	 * Jesteďż˝my listenerem do agenta ktďż˝rym jesteďż˝my zalogowani
	 */
	public void onPause(RealAgent agent, boolean paused, PauseType pauseType, int pauseTime, int timePoor, int timeBad) {
		if (paused){
			getCmdPaused(pauseType, pauseTime, timePoor, timeBad);
		} else {
			getCmdUnpaused();
		}
	}
	
}



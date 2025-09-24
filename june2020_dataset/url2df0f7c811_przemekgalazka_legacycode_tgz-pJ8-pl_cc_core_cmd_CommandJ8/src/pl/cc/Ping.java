package pl.cc;

import java.util.Date;

import org.apache.log4j.Logger;

import pl.cc.core.NetPrintWriter;
import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.ErrSameAgentConnection;
import pl.cc.core.cmd.InfoType;
import pl.cc.core.cmd.InfoVersion;
import pl.cc.core.cmd.OkWelcome;
import pl.cc.utils.Utils;

/**
 * Wďż˝tek odpowiedzialny za podtrzymywanie komunikacji z agentem
 * @since 2007-06-19
 */
public class Ping extends Thread implements ProxyEventListener {
	static final Logger log = Logger.getLogger(Ping.class);
	boolean stop = false;

	ProxyEventListenerList proxyEventListenerList;
	/**
	 * Wďż˝tek sprawdzajďż˝cy pingi
	 */
	CheckPingThread checkPingThread = null;
	
	NetPrintWriter out;
	/**
	 * Czas ostatniego Ponga
	 */
	Date lastPong = Utils.getNow();

	/**
	 * Czas ostatniego Pinga
	 */
	Date lastPing;

	int noPongCount = 0;
	/**
	 * ile sekund pomiďż˝dzy kolejnymi pingami
	 */
	int interval = 5; 
	/**
	 * ile sekund czekamy na ponga
	 */
	int timeOut = 10;


	public Ping(ProxyEventListenerList proxyEventListenerList, NetPrintWriter out){
		super();
		this.proxyEventListenerList = proxyEventListenerList;
		this.out = out;
	}
	
	/**
	 * @param proxy obiekt proxy
	 * @param interval czas w sekundach pomiďż˝dzy kolejnymi pingami
	 * @param timeOut tyle sek moze upďż˝ynďż˝ďż˝ od wpďż˝yniďż˝cia ostatniego ponga
	 */
	public Ping(ProxyEventListenerList proxyEventListenerList, NetPrintWriter out, int interval, int timeOut){
		this(proxyEventListenerList, out);
		this.interval = interval;
		this.timeOut = timeOut;
	}
	
	private void checkPong(){
		if ((Utils.getNow().getTime()-lastPong.getTime())>(timeOut*1000)){
			log.error("Brak ďż˝ďż˝cznoďż˝ci z ProxyAgent");
			proxyEventListenerList.onProblemWithPing();
			stop = true;
		}
	}

	public void run(){
		setName("ping");
		log.info("Ping thread started");
		checkPingThread = new CheckPingThread();
		checkPingThread.start();
		while (!stop){
			try {
				lastPing = Utils.getNow();
				log.debug("Send PING: "+lastPing.toString());
				out.println("ping");
				sleep(interval*1000);
				if (stop) break;
			} catch (NullPointerException e1) {
				log.warn("Problem z ping-pong (NullPointerException)");
			} catch (InterruptedException e2) {
				log.warn("Problem z ping-pong (InterruptedException)");
			}
		}
		log.debug("Koniec działania wątku ping");
	}
	
	public void stopThread() {
		stop = true;
	}

	public void setLastPong(Date date){
		lastPong = date;
		log.debug("Recieve PONG: "+date.toString());
	}
	
	public Date getLastPong() {
		return lastPong;
	}

	@Override
	public void onAuthorized(OkWelcome selfInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAuthResponse(boolean authorized) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfoVersion(InfoVersion infoVersion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPong(Date now) {
		setLastPong(now);
	}

	@Override
	public void onProblemWithPing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTryConnect(boolean successfully, String host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWelcome() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInvalidVersion(String applicationVersion, InfoVersion infoVersion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartReconnectProcedure(int sleepBeforeReconnect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionReplaced(ErrSameAgentConnection connectionReplaced) {
		// TODO Auto-generated method stub
		
	}

	
	class CheckPingThread extends Thread {
			public void run(){
				setName("CheckPingThread");
				while (true){
					if (stop) return;
					checkPong();
					try {
						Thread.sleep(interval*1000);
					} catch (InterruptedException e) {
					}
				}
			}
	}


	@Override
	public void onInfoType(InfoType cmd) {
		// TODO Auto-generated method stub
		
	}
}

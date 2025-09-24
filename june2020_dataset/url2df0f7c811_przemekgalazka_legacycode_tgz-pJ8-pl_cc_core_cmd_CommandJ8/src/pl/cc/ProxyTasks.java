package pl.cc;

import pl.cc.core.LoginCredentials;
import pl.cc.core.PauseType;
import pl.cc.real.RealAgent;

/**
 * Zestaw poleceń które może wykonać CCProxy
 * 
 * FIXME: do poważnego odchudzenia
 * @since Oct 26, 2008
 */
public interface ProxyTasks {
	
	
	/*
	boolean doConnection();
	void setHost(String host);
	void setLoginCredentials(LoginCredentials loginCredentials);

	boolean sendLoginCredentials();

	boolean isTCPConnected();
	boolean startPingThread();
	
	// wyloguj mnie z CCProxy (ccproxy automatycznie zrywa takďż˝e poďż˝ďż˝czenie TCP)
	void doLogout();
	
	void addEventListener(ProxyEventListener proxyEventListener);
	boolean removeEventListener(ProxyEventListener proxyEventListener);
	
	void setAutoReconnect(boolean autoReconnect);
	public void spyAgent(RealAgent agent);
	
	public Date getLastPong();
*/
	
	public void doConnect(LoginCredentials loginCredentials);
	public void doDisconnect();
	
	/**
	 * Żądanie podsłuchu dla agenta
	 * @param agent - tego agenta chcemy podsłuchać
	 */
	public void doSpyAgent(RealAgent agent);
	
	
	public void doPauseAgent(RealAgent agent, boolean pause, PauseType pauseType);
	
	/**
	 * Wrzucenie agenta w tryb pauzy administracyjnej
	 * @param realAgent - agent którego chcemy zapauzować
	 * @param pause jeśli false - poprostu odpauzuj, true - pauza administracyjna
	 */
	public void doPauseAgentAdministrative(RealAgent realAgent, boolean pause);
	
}

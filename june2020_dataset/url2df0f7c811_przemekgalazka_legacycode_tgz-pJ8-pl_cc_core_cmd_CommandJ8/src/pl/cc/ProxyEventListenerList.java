package pl.cc;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.CommandAuthError;
import pl.cc.core.cmd.CommandLoggedIn;
import pl.cc.core.cmd.CommandWelcome;
import pl.cc.core.cmd.ErrSameAgentConnection;
import pl.cc.core.cmd.InfoType;
import pl.cc.core.cmd.InfoVersion;
import pl.cc.core.cmd.OkWelcome;

public class ProxyEventListenerList extends ArrayList<ProxyEventListener> implements ProxyEventListener {
	private static final long serialVersionUID = -6579712344976102456L;
	static final Logger log = Logger.getLogger(ProxyEventListenerList.class);

	private boolean locked = false;
	private ArrayList<PendingModificaton> pendingListModification = new ArrayList<PendingModificaton>();
	
	@Override
	public synchronized boolean add(ProxyEventListener listener){
		if (! locked) {
			return super.add(listener);
		} else {
			pendingListModification.add(new PendingModificaton(true, listener));
			return ! contains(listener);
		}
	}

	@Override
	public synchronized boolean remove(Object listener){
		if (! locked) {
			return super.remove(listener);
		} else {
			pendingListModification.add(new PendingModificaton(false, (ProxyEventListener)listener));
			return ! contains(listener);
		}

	}

	private void processPendingModification(){
		locked = false;
		for (PendingModificaton op : pendingListModification){
			ProxyEventListener listener = op.listener;
			if (op.toAdd){
				add(listener);
			} else {
				remove(listener);
			}
		}
		pendingListModification.clear();
	}
	
	/**
	 * TODO: czy ta logika powinna byďż˝ napewno tutaj
	 * Pewne komendy zamieniamy na wywoďż˝ania konkretnych metod
	 * @param cmd
	 * @return true jeďż˝li przetworzono. Wtedy nie naleďż˝y woďż˝aďż˝ onEvent
	 */
	private synchronized boolean preProcess(Command cmd){
		if (cmd instanceof CommandLoggedIn){
			log.debug("preProcessed "+cmd.getClass());
			onAuthResponse(true);
		} else if (cmd instanceof CommandAuthError){
			log.debug("preProcessed "+cmd.getClass());
			onAuthResponse(false);
		} else if (cmd instanceof InfoVersion){
			log.debug("preProcessed "+cmd.getClass());
			onInfoVersion((InfoVersion)cmd);
		} else if (cmd instanceof OkWelcome){
			log.debug("preProcessed "+cmd.getClass());
			onAuthorized((OkWelcome)cmd);
		} else if (cmd instanceof CommandWelcome){
			log.debug("preProcessed "+cmd.getClass());
			onWelcome();
		} else if (cmd instanceof ErrSameAgentConnection){
			log.debug("preProcessed "+cmd.getClass());
			onConnectionReplaced((ErrSameAgentConnection)cmd);			
		} else if (cmd instanceof InfoType){
			log.debug("preProcessed "+cmd.getClass());
			onInfoType((InfoType)cmd);			
		} else {
			processPendingModification();
			return false;
		}
		processPendingModification();
		return true;
	}

	public void onInfoType(InfoType cmd) {
		for (ProxyEventListener p : this) {
			p.onInfoType(cmd);
		}
	}

	public void onEvent(Command command) {
		locked = true;
		boolean preProcessed = preProcess(command);
		if (!preProcessed){
			for (ProxyEventListener p : this){
				p.onEvent(command);
			}
		}
	}

	
	public void onAuthResponse(boolean authorized) {
		for (ProxyEventListener p : this) {
			p.onAuthResponse(authorized);
		}
	}


	public void onInfoVersion(InfoVersion infoVersion) {
		for (ProxyEventListener p : this){
			p.onInfoVersion(infoVersion);
		}
	}

	public void onProblemWithPing() {
		for (ProxyEventListener p : this){
			p.onProblemWithPing();
		}
	}

	public void onTryConnect(boolean successfully, String host) {
		for (ProxyEventListener p : this){
			p.onTryConnect(successfully, host);
		}
	}

	public void onAuthorized(OkWelcome selfInfo) {
		for (ProxyEventListener p : this){
			p.onAuthorized(selfInfo);
		}
	}

	public void onDisconnect() {
		log.debug("pendingListModification.size: "+pendingListModification.size()+" listener.size: "+size());
		for (ProxyEventListener p : this){
			p.onDisconnect();
		}
	}
	
	@Override
	public void onPong(Date now) {
		for (ProxyEventListener p : this){
			p.onPong(now);
		}
	}

	@Override
	public void onWelcome() {
		for (ProxyEventListener p : this){
			p.onWelcome();
		}
	}
	@Override
	public void onInvalidVersion(String applicationVersion, InfoVersion infoVersion) {
		for (ProxyEventListener p : this){
			p.onInvalidVersion(applicationVersion, infoVersion);
		}
	}
	@Override
	public void onStartReconnectProcedure(int sleepBeforeReconnect) {
		for (ProxyEventListener p : this){
			p.onStartReconnectProcedure(sleepBeforeReconnect);
		}
	}
	@Override
	public void onConnectionReplaced(ErrSameAgentConnection connectionReplaced) {
		for (ProxyEventListener p : this){
			p.onConnectionReplaced(connectionReplaced);
		}
	}

}

class PendingModificaton {
	boolean toAdd;
	ProxyEventListener listener;
	
	public PendingModificaton(boolean toAdd, ProxyEventListener listener) {
		super();
		this.toAdd = toAdd;
		this.listener = listener;
	}
	
}
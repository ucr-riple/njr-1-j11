package pl.cc;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.CommandPong;
import pl.cc.utils.Utils;


/**
 * WÂątek obsÂługujÂący komunikaty przychodzÂące od  Proxy
 * 
 * @since 2007-07-06
 */
public class ReaderThread extends Thread {
	static final Logger log = Logger.getLogger(ReaderThread.class);
	private String connectionId;
	private ProxyEventListenerList listenerList;
	private BufferedReader in;
	
	public ReaderThread(ProxyEventListenerList listenerList, BufferedReader in, String connectionId){
		this.connectionId = connectionId;
		this.listenerList = listenerList;
		this.in = in;
		setName("reader");
	}

	protected void cmd(String s){
		Command c = Command.factory(s, connectionId);
		
		if (c instanceof CommandPong) {
			listenerList.onPong(Utils.getNow());
			return;
		}

		log.debug("READ FROM PROXY: ["+s+"] "+c.getClass());
		listenerList.onEvent(c);
		
	}
	
	public void run() {
		String s;
		try {
			while ((s = in.readLine()) != null) {
				cmd(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		listenerList.onDisconnect();
	}

	public void closeInputStream() throws IOException{
		in.close();
	}
	
}

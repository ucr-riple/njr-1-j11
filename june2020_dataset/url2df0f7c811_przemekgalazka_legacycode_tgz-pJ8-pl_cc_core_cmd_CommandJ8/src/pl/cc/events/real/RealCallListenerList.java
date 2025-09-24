package pl.cc.events.real;

import java.util.Vector;

import org.apache.log4j.Logger;

import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class RealCallListenerList extends Vector<RealCallListener> implements RealCallListener {
	public static final Logger log = Logger.getLogger(RealCallListenerList.class);

	private static final long serialVersionUID = -8802040613587607761L;

	@Override
	public void onAgentConnect(RealCall call, RealAgent agent, RealQueue queue) {
		for (RealCallListener listener : this){
			listener.onAgentConnect(call, agent, queue);
		}
	}
	
	@Override
	public void onAgentHangeup(RealCall call, RealAgent agent, RealQueue queue) {
		for (RealCallListener listener : this){
			listener.onAgentHangeup(call, agent, queue);
		}
	}	
	
	@Override
	public boolean add(RealCallListener listener){
		return super.add(listener); 
	}
	
	@Override
	public boolean remove(Object listener){
		return super.remove(listener);
	}


	

}

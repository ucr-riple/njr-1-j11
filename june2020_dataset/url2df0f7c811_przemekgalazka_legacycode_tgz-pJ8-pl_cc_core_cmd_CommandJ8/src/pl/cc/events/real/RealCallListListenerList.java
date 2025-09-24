package pl.cc.events.real;

import java.util.Vector;

import pl.cc.real.RealCall;


public class RealCallListListenerList extends Vector<RealCallListListener> implements RealCallListListener {
	private static final long serialVersionUID = -631347483422553089L;

	@Override
	public void onCallAdd(RealCall call) {
		for(RealCallListListener listener : this){
			listener.onCallAdd(call);
		}
		
	}

	@Override
	public void onCallRemoved(RealCall call) {
		for(RealCallListListener listener : this){
			listener.onCallRemoved(call);
		}
	}

}

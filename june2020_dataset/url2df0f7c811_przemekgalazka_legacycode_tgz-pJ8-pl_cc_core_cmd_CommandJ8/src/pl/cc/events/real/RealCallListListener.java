package pl.cc.events.real;

import pl.cc.real.RealCall;

public interface RealCallListListener {
	
	public void onCallAdd(RealCall call);
	public void onCallRemoved(RealCall call);
		
}

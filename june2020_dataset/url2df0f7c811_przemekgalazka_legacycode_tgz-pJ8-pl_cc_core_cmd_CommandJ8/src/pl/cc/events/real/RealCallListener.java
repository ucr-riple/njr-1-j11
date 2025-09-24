package pl.cc.events.real;

import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public interface RealCallListener {
	/**
	 * Rozmowa została połączona z agentem w ramach konretnej kolejki
	 * @param call
	 * @param agent
	 * @param queue
	 */
	public void onAgentConnect(RealCall call, RealAgent agent, RealQueue queue);

	/**
	 * Zakończenie połączenia z agentem w ramach konretnej kolejki
	 * @param call
	 * @param agent
	 * @param queue
	 */
	public void onAgentHangeup(RealCall call, RealAgent agent, RealQueue queue);
		
}

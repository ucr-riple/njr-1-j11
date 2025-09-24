package DeviceGraphics;

import Networking.Server;
import agent.Agent;
import agent.StandAgent;

/**
 * Graphics logic side of the inspection stand. Acts as one of the messengers between agent and client (managers).
 * @author Shalynn Ho
 *
 */
public class InspectionStandGraphics extends StandGraphics {
	
	public InspectionStandGraphics(Server s, Agent a) {
		super(s, a, 3);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

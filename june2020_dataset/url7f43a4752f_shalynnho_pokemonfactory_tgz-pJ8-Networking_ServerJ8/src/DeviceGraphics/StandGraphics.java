package DeviceGraphics;

import factory.KitConfig;
import factory.PartType;
import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.StandAgent;

/**
 * Represents the stand for the two kitting stands and the parent class for the inspection stand.
 * 
 * @author Shalynn Ho
 */
public class StandGraphics implements DeviceGraphics {
	
	public static final int RIGHT_X_LOC = 280;
	public static final int Y_OFFSET = 100;
	public static final int STAND_WIDTH = 80;
	
	protected Server server;
	protected StandAgent standAgent;
	protected int standID;
	protected Location location;
	
	// the kit on this stand
	protected KitGraphics kit;
	// false if there is a kit on the stand
	protected boolean isEmpty;
	
	/**
	 * 
	 * @param s - the server
	 * @param a - the stand agent
	 * @param id - stand ID - 0,1: kit stands; 2: inspection stand
	 */
	public StandGraphics(Server s, Agent a, int id) {
		server = s;
		standAgent = (StandAgent) a;
		standID = id;
		// set location of kit based on standID
		if (id % 2 == 0) {
			location = new Location((RIGHT_X_LOC - STAND_WIDTH/2), standID*Y_OFFSET + Y_OFFSET);
		} else {
			location = new Location(RIGHT_X_LOC, standID*Y_OFFSET + Y_OFFSET);
		}
		
		isEmpty = true;
	}
	
	/**
	 * Give kit to kit robot
	 * @param kg
	 * @return
	 */
	public void giveKit(KitGraphics kg) {
		if(!isEmpty) {
			kit = null;		// better way?
			isEmpty = true;
			server.sendData(new Request(Constants.STAND_GIVE_KIT_COMMAND, Constants.STAND_TARGET + standID, null));
		}
	}

	/**
	 * Kit robot places kit on stand
	 * @param kg - the kit
	 */
	public void receiveKit(KitGraphics kg) {
		if (isEmpty) {
			kit = kg;
			kit.setLocation(location);
			KitConfig config = kit.getKitConfig();
			isEmpty = false;
			server.sendData(new Request(Constants.STAND_RECEIVE_KIT_COMMAND, Constants.STAND_TARGET + standID, config));
		}
	}

	/**
	 * Kit receives part from parts robot
	 * Can only receive a part if there is a kit on the stand.
	 * @param part - the part
	 */
	public void receivePart(PartGraphics part) {
		if(!isEmpty) {
			kit.addPart(part);
			PartType type = part.getPartType();
			server.sendData(new Request(Constants.STAND_RECEIVE_PART_COMMAND, Constants.STAND_TARGET, type));
		}
	}
	
	/**
	 * Used to send DONE messages back to agent
	 * @param r - the request
	 */
	public void receiveData(Request r) {
		
				String cmd = r.getCommand();
				
				// TODO: double check with 201 that they don't need any done messages from stand
		
	}
	
}

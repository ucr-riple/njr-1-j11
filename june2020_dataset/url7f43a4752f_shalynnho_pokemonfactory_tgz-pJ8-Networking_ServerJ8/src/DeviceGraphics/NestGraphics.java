package DeviceGraphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import Utils.PartData;
import agent.Agent;
import agent.NestAgent;
import factory.PartType;

/**
 * This class represents the graphics logic for a nest.
 * @author Shalynn Ho, Harry Trieu
 */
public class NestGraphics implements GraphicsInterfaces.NestGraphics,
		DeviceGraphics {
	
	// max number of parts this Nest holds
	private static final int MAX_PARTS = 8;
	// y-coordinates of nest0
	private static final int NEST_Y = 45, NEST_Y_INCR = 75;
	private static final int BOTTOM_ROW_OFFSET = 23;

	// instructions to display graphics will be sent through the server
	private final Server server;
	// the ID of this Nest
	private int nestID;
	// the NestAgent
	private NestAgent nestAgent;
	// array of part locations in nest
	private ArrayList<Location> partLocs;
	// Location of upper left corner of this nest
	private final Location location;
	// dynamically stores the parts currently in the Nest
	private ArrayList<PartGraphics> partsInNest;
	// stores the parts in the nest and their quality (good/bad)
	private HashMap<PartGraphics, Boolean> partsInNestQuality;

	public NestGraphics(Server s, int nid, Agent agent) {
		server = s;
		nestID = nid;
		nestAgent = (NestAgent) agent;
		
		partsInNestQuality = new HashMap<PartGraphics, Boolean>();
		partsInNest = new ArrayList<PartGraphics>(MAX_PARTS);
		location = new Location(Constants.LANE_END_X - Constants.NEST_WIDTH, NEST_Y + nestID * NEST_Y_INCR);
		generatePartLocations();
	}

	/**
	 * Generates an array of Locations for the parts in the nest.
	 */
	private void generatePartLocations() {
		partLocs = new ArrayList<Location>(MAX_PARTS);
		for (int i = 0; i < MAX_PARTS; i++) {
			if (i % 2 == 0) { // top row
				partLocs.add(new Location((location.getX() + (i / 2)
						* Constants.PART_WIDTH), (location.getY() - Constants.PART_OFFSET)));
			} else { // bottom row
				partLocs.add(new Location((location.getX() + (i / 2)
						* Constants.PART_WIDTH),
						(location.getY() + BOTTOM_ROW_OFFSET - Constants.PART_OFFSET)));
			}
		}
	}
	
	/**
	 * Sets/updates the locations of the parts in the nest.
	 */
	private void setPartLocations() {
		// whichever is less
		int min = (MAX_PARTS < partsInNest.size()) ? MAX_PARTS : partsInNest.size();
		for (int i = 0; i < min; i++) {
			partsInNest.get(i).setLocation(partLocs.get(i));
		}
	}

	/**
	 * 
	 */
	@Override
	public void purge() {
		server.sendData(new Request(Constants.NEST_PURGE_COMMAND,
				Constants.NEST_TARGET + nestID, null));
	}

	/**
		 * @param
		 */
		@Override
		public void givePartToPartsRobot(PartGraphics pg) {
			// TODO: V2: get index of the part removed, if not 0
			server.sendData(new Request(Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND,
						Constants.NEST_TARGET + nestID, pg));
		}

	/**
	 * @param -
	 */
	@Override
	public void receivePart(PartGraphics pg) {
		partsInNest.add(pg);
		partsInNestQuality.put(pg, pg.getQuality());
		pg.setLocation(partLocs.get(partsInNest.size() - 1));
		PartType type = pg.getPartType();
		//System.out.println("NEST" + nestID + " RECEIVING PART " + partsInNest.size());
		server.sendData(new Request(Constants.NEST_RECEIVE_PART_COMMAND,
				Constants.NEST_TARGET + nestID, new PartData(pg.getPartType(),
						pg.getQuality())));
	}

	/**
	 * Receives message data from the Server
	 * @param r - the request to be parsed
	 */
	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.NEST_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX)) {
			nestAgent.msgReceivePartDone();
			
		} else if (req.getCommand().equals(
			Constants.NEST_GIVE_TO_PART_ROBOT_COMMAND + Constants.DONE_SUFFIX)) {
			partsInNestQuality.remove(partsInNest.get(0));
			partsInNest.remove(0);
			setPartLocations();
			nestAgent.msgGivePartToPartsRobotDone();
			
		} else if (req.getCommand().equals(
			Constants.NEST_PURGE_COMMAND + Constants.DONE_SUFFIX)) {
			partsInNestQuality.clear();
			partsInNest.clear();
			nestAgent.msgPurgingDone();
		} 
	}

	/**
	 * @return
	 */
	public boolean isFull() {
		return partsInNest.size() >= MAX_PARTS;
	}

	/**
	 * V2 ONLY
	 * @return
	 */
	public boolean allPartsBad() {
		// TODO: IMPLEMENT THIS METHOD FOR V2, IF CAMERA NEEDS IT
		return false;
	}

	/**
	 * @return location of this nest
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * @returns an array list of the parts in nest
	 */
	public ArrayList<PartGraphics> getPartsInNest() {
		return partsInNest;
	}

	/**
	 * V2 ONLY, no bad parts in V1.
	 * @returns a map of parts and their quality (good/bad)
	 */
	public Map<PartGraphics, Boolean> getQualityOfParts() {
		return partsInNestQuality;
	}

	public int getNestID() {
		return nestID;
	}

	public void setNestID(int nestID) {
		this.nestID = nestID;
	}

	public NestAgent getNestAgent() {
		return nestAgent;
	}

	public void setNestAgent(NestAgent nestAgent) {
		this.nestAgent = nestAgent;
	}

	public void setPartsInNest(ArrayList<PartGraphics> partsInNest) {
		this.partsInNest = partsInNest;
	}
}

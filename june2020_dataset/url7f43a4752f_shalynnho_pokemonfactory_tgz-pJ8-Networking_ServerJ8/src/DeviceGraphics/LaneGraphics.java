package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import Utils.PartData;
import agent.Agent;
import agent.LaneAgent;
import factory.PartType;

/**
 * This class contains the graphics logic for a lane.
 * 
 * @author Shalynn Ho
 * 
 */
public class LaneGraphics implements GraphicsInterfaces.LaneGraphics, DeviceGraphics {

	// max number of parts that can be on a Lane
	private static final int MAX_PARTS = Constants.LANE_LENGTH / (Constants.PART_WIDTH + Constants.PART_PADDING / 2);

	// start y-coord of the part
	private int endY;
	// instructions to display graphics will be sent through the server
	private final Server server;
	// the ID of this Lane
	private final int laneID;
	// the lane agent
	private final LaneAgent laneAgent;

	// dynamically stores Parts currently on Lane
	private final ArrayList<PartGraphics> partsOnLane;
	// true if Lane is on
	private boolean laneOn;
	// location of a part jam on the lane
	private Location jamLocation;
	// status of lane jam
	private boolean jammed;

	/**
	 * 
	 * @param s
	 *            - the Server
	 * @param id
	 *            - ID of this lane
	 * @param a
	 *            - the LaneAgent
	 */

	public LaneGraphics(Server s, int id, Agent a) {
		server = s;
		laneID = id;
		laneAgent = (LaneAgent) a;

		// initialize lane components
		partsOnLane = new ArrayList<PartGraphics>();
		laneOn = true;
		jammed = false;
	}

	/**
	 * 
	 * @return true if this lane is full
	 */
	public boolean isFull() {
		return partsOnLane.size() >= MAX_PARTS;
	}

	/**
	 * Called when part needs to be given to the nest associated with this lane. Basically, this lane doesn't care about
	 * that part anymore.
	 * 
	 * @param pg
	 *            - the part passed to the nest associated with this lane
	 */
	@Override
	public void givePartToNest(PartGraphics pg) {
		server.sendData(new Request(Constants.LANE_GIVE_PART_TO_NEST, Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Purges this lane of all parts
	 */
	@Override
	public void purge() {
		server.sendData(new Request(Constants.LANE_PURGE_COMMAND, Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Called when part is delivered to this lane
	 * 
	 * @param pg
	 *            - the part passed to this lane
	 */
	@Override
	public void receivePart(PartGraphics pg) {
		partsOnLane.add(pg);
		// System.out.println("	LANE"+laneID+" RECEIVING PART " +
		// partsOnLane.size());
		pg.setLocation(new Location(Constants.LANE_BEG_X, endY + Constants.PART_WIDTH / 2));
		PartType type = pg.getPartType();
		server.sendData(new Request(Constants.LANE_RECEIVE_PART_COMMAND, Constants.LANE_TARGET + laneID, new PartData(
				type, pg.getQuality())));
	}

	/**
	 * Sorts data and messages sent to this lane via the server Used to send DONE messages back to agent
	 * 
	 * @param r
	 *            - the request
	 */
	@Override
	public void receiveData(Request r) {
		String cmd = r.getCommand();

		if (cmd.equals(Constants.LANE_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX)) {
			PartGraphics p = partsOnLane.get(partsOnLane.size() - 1);
			p.setLocation(new Location(Constants.LANE_END_X, endY));
			laneAgent.msgReceivePartDone(p);

		} else if (cmd.equals(Constants.LANE_GIVE_PART_TO_NEST + Constants.DONE_SUFFIX)) {
			laneAgent.msgGivePartToNestDone(partsOnLane.get(0));
			partsOnLane.remove(0);
			// System.out.println("	LANE" + laneID + " GAVE PART TO NEST DONE, partsOnLane.size(): " +
			// partsOnLane.size());

		} else if (cmd.equals(Constants.LANE_PURGE_COMMAND + Constants.DONE_SUFFIX)) {
			partsOnLane.clear();
			laneAgent.msgPurgeDone();

		} else if (cmd.equals(Constants.LANE_SET_JAM_COMMAND)) {
			// System.out.println("LANE" + laneID + " SETTING JAM LOCATION");
			Location l = (Location) r.getData();
			setJamLocation(l);

		} else if (cmd.equals(Constants.LANE_SET_JAM_COMMAND + Constants.DONE_SUFFIX)) {
			jammed = false;
			server.displayMessage("Professor Oak: SNORLAX woke up!");
		} else if (cmd.equals(Constants.MSGBOX_DISPLAY_MSG)) {
			String s = (String) r.getData();
			server.displayMessage(s);
		}
	}

	/**
	 * Sets the jam location for a part on the lane
	 * 
	 * @param loc
	 *            - location of the lane jam
	 */
	public void setJamLocation(Location loc) {
		if (!jammed) {
			jammed = true;
			jamLocation = loc;
			server.sendData(new Request(Constants.LANE_SET_JAM_COMMAND, Constants.LANE_TARGET + laneID, jamLocation));
			server.displayMessage("Professor Oak: A wild SNORLAX appeared! Play the POKEFLUTE!");
			// System.out.println("LANE" + laneID + " JAM LOC SET x: " +
			// jamLocation.getX());
		}
	}

	/**
	 * Receives message from agent, sends to GraphicsDisplay to unjam the lane
	 */
	@Override
	public void unjam() {
		// jammed = false;
		server.sendData(new Request(Constants.LANE_UNJAM_COMMAND, Constants.LANE_TARGET + laneID, null));
	}

	/**
	 * Sets the vibration amplitude of this lane (how quickly parts vibrate down lane)
	 * 
	 * @param amp
	 *            - the amplitude
	 */
	/*
	 * public void setAmplitude(int amp) { amplitude = amp; server.sendData(new
	 * Request(Constants.LANE_SET_AMPLITUDE_COMMAND, Constants.LANE_TARGET + laneID, amp)); }
	 */

	/**
	 * Turns lane on or off
	 * 
	 * @param on
	 *            - on/off switch for this lane
	 */
	public void toggleSwitch(boolean on) {
		laneOn = on;
		server.sendData(new Request(Constants.LANE_TOGGLE_COMMAND, Constants.LANE_TARGET + laneID, laneOn));
	}
}

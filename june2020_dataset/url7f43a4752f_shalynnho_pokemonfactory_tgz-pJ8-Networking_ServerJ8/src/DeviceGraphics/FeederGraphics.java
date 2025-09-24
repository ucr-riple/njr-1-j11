package DeviceGraphics;

import java.util.Random;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.FeederAgent;
import factory.PartType;

/**
 * This class handles the logic for the feeder animation.
 * 
 * @author Harry Trieu
 * 
 */
public class FeederGraphics implements GraphicsInterfaces.FeederGraphics,
		DeviceGraphics {
	// a reference to the server
	private final Server server;
	// the feeder's unique ID
	private final int feederID;
	// a reference to the FeederAgent
	private final FeederAgent feederAgent;
	// true if the diverter is pointing to the top lane
	private boolean diverterTop;
	// reference to the current bin
	private BinGraphics binGraphics;
	// location of the feeder
	private final Location feederLocation;
	// reference to the partType in the current bin
	private PartType partType;

	/**
	 * This is the constructor.
	 * 
	 * @param id
	 *            the unique ID of the feeder (there will be 4 feeders so we
	 *            need to uniquely identify them)
	 * @param myServer
	 *            a reference to the Server
	 */
	public FeederGraphics(int id, Server myServer, Agent a) {
		feederID = id;

		// System.out.println("Feeder created! ID: " + feederID);

		server = myServer;
		feederAgent = (FeederAgent) a;
		feederLocation = new Location(Constants.FEEDER_LOC);
		feederLocation.incrementY(id * Constants.FEEDER_Y_STEP);

		// System.out.println("Feeder ID: " + feederID + " | X: " +
		// feederLocation.getX() + " | Y: " + feederLocation.getY());

		// diverter defaults to the top lane
		diverterTop = true;
	}

	/**
	 * This function receives a bin from agents.
	 * 
	 * @param bg
	 *            BinGraphics passed in by the Agent
	 */
	@Override
	public void receiveBin(BinGraphics bg) {
		binGraphics = bg;
		partType = bg.getPart().getPartType();

		server.sendData(new Request(Constants.FEEDER_RECEIVED_BIN_COMMAND,
				Constants.FEEDER_TARGET + feederID, partType));

		System.out.println("[FEEDER]: Received a bin.");
	}

	/**
	 * This function passes a part to agents who pass it on to the lane.
	 */
	@Override
	public PartGraphics createPartGraphics() {
		// TODO - need to implement this with agents and send appropriate done
		// messages

		System.out.println("[FEEDER]: Giving part to lane.");

		Random r = new Random();
		float randNum = r.nextFloat();

		PartGraphics partGraphics = new PartGraphics(partType);

		if (randNum < partType.getBadChance()
				|| randNum == partType.getBadChance()) {
			partGraphics.setQuality(false);
			return partGraphics;
		} else {
			return partGraphics;
		}
	}

	/**
	 * This function purges the bin.
	 * 
	 * @param bg
	 */
	@Override
	public void purgeBin(BinGraphics bg) {
		bg.setFull(false);
		server.sendData(new Request(Constants.FEEDER_PURGE_BIN_COMMAND,
				Constants.FEEDER_TARGET + feederID, null));

		System.out.println("[FEEDER]: Bin purging.");
	}

	/**
	 * This function flips the diverter.
	 */
	@Override
	public void flipDiverter() {
		diverterTop = !diverterTop;
		server.sendData(new Request(Constants.FEEDER_FLIP_DIVERTER_COMMAND,
				Constants.FEEDER_TARGET + feederID, null));

		System.out.println("[FEEDER]: Diverter flipping.");
	}

	/**
	 * Returns the location of the feeder.
	 */
	@Override
	public Location getLocation() {
		return feederLocation;
	}

	/**
	 * This function handles requests sent to the server
	 */
	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(
				Constants.FEEDER_RECEIVED_BIN_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgReceiveBinDone(binGraphics.getBin());
		} else if (req.getCommand().equals(
				Constants.FEEDER_PURGE_BIN_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgPurgeBinDone(binGraphics.getBin());
		} else if (req.getCommand().equals(
				Constants.FEEDER_FLIP_DIVERTER_COMMAND + Constants.DONE_SUFFIX)) {
			feederAgent.msgFlipDiverterDone();
		}
	}
}

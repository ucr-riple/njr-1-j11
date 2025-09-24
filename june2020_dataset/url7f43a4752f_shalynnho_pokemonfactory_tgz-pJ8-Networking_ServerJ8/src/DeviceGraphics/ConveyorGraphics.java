package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import agent.Agent;
import agent.ConveyorAgent;
import agent.StandAgent;

/**
 * Contains the logic for the Conveyor object
 * 
 * @author neetugeo
 */

public class ConveyorGraphics implements GraphicsInterfaces.ConveyorGraphics,
		DeviceGraphics {

	private final ArrayList<KitGraphics> kitsOnConveyor; // all kits on conveyor
	private final ArrayList<KitGraphics> kitsToLeave;
	private final Location location;
	private final Server server;
	private final int velocity;
	private final ConveyorAgent conveyorAgent;

	public ConveyorGraphics(Server s, Agent a) {
		location = new Location(0, 0);
		kitsOnConveyor = new ArrayList<KitGraphics>();
		kitsToLeave = new ArrayList<KitGraphics>();
		server = s;
		velocity = 10;
		conveyorAgent = (ConveyorAgent) a;

	}

	public void bringEmptyKit(KitGraphics kg) {
		System.out.println("Animating bringing empty kit");
		kitsOnConveyor.add(kg);
		server.sendData(new Request(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND,
				Constants.CONVEYOR_TARGET, null));
	}

	public void giveKitToKitRobot() {
		server.sendData(new Request(
				Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND,
				Constants.CONVEYOR_TARGET, null)); // temporary command name
													// until Kit Robot finalized
		kitsOnConveyor.remove(0);
	}

	/**
	 * send a completed kit off-screen
	 * 
	 * @param kit
	 *            - a kit must be received from KitRobot before sending it away
	 */
	public void receiveKit(KitGraphics kg) {
		kitsToLeave.add(kg);
		server.sendData(new Request(Constants.CONVEYOR_RECEIVE_KIT_COMMAND,
				Constants.CONVEYOR_TARGET, null));
	}

	@Override
	public void receiveData(Request r) {
		String target = r.getTarget();
		String command = r.getCommand();
		Object object = r.getData();

		if (target.equals(Constants.CONVEYOR_TARGET)) {
			if (command
					.equals(Constants.CONVEYOR_GIVE_KIT_TO_KIT_ROBOT_COMMAND)) {
				// parsing object to kit object
				System.out.println(this.toString()
						+ " received server command to giveKitToKitRobot.");
				giveKitToKitRobot();
			} else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)) {

			} else if (command
					.equals(Constants.CONVEYOR_CHANGE_VELOCITY_COMMAND)) {
				// need to somehow send an integer to change the velocity
			} else if (command
					.equals(Constants.CONVEYOR_SEND_ANIMATION_COMMAND)) {
				// still not quite sure how to implement this yet
			} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND)) {
				StandAgent stand = (StandAgent) server.agents
						.get(Constants.STAND_TARGET);
				stand.msgMakeKits(500); // TODO: Change
			} else if (command.equals(Constants.CONVEYOR_MAKE_NEW_KIT_COMMAND
					+ Constants.DONE_SUFFIX)) {
				System.out.println(this.toString() + " sending emptyKitDone");
				conveyorAgent.msgBringEmptyKitDone();
			} else if (command.equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND
					+ Constants.DONE_SUFFIX)) {
				conveyorAgent.msgReceiveKitDone();
			}
		}
	}

	@Override
	public void msgBringEmptyKit(KitGraphics kit) {
		bringEmptyKit(kit);
	}

	@Override
	public void msgGiveKitToKitRobot(KitGraphics kit) {
		giveKitToKitRobot();
		conveyorAgent.msgGiveKitToKitRobotDone();
	}

	@Override
	public void msgReceiveKit(KitGraphics kit) {
		receiveKit(kit);
	}

}

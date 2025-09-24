package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.ConveyorGraphics;
import agent.data.Kit;
import agent.interfaces.Conveyor;
import agent.interfaces.KitRobot;
import agent.test.mock.MockGraphics;
import factory.KitConfig;

/**
 * Conveyor brings empty kits into and takes completed (i.e. assembled and
 * inspected) kits out of the kitting cell. Interacts with the Factory Control
 * System (FCS) and the Kit Robot.
 * @author Daniel Paje
 */
public class ConveyorAgent extends Agent implements Conveyor {

	private final List<MyKit> kitsOnConveyor = Collections
			.synchronizedList(new ArrayList<MyKit>());
	private final List<MyKit> kitsOnOutboundConveyor = Collections
			.synchronizedList(new ArrayList<MyKit>());

	private KitConfig kitConfig;

	private MyKit incomingKit;
	private MyKit outgoingKit;
	private int numKitsToDeliver;
	private ConveyorState state;
	private boolean start;

	private enum ConveyorState {
		IDLE, TRANSFERRING_KIT
	};

	// Used to prevent animations from overlapping
	Semaphore animation = new Semaphore(0, true);

	// References to other agents
	private KitRobot kitrobot;
	private FCSAgent fcs;
	private ConveyorGraphics conveyorGraphics;
	private MockGraphics mockgraphics;

	// Name of the conveyor
	private final String name;

	/**
	 * Inner class encapsulates kit and adds states relevant to the conveyor
	 * @author dpaje
	 */
	private class MyKit {
		public Kit kit;
		public KitStatus KS;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.MOVING_IN;
		}
	}

	public enum KitStatus {
		MOVING_IN, ARRIVED_AT_PICKUP_LOCATION, AWAITING_PICKUP, PICKUP_REQUESTED, PICKED_UP, AWAITING_DELIVERY, MOVING_OUT, DELIVERED
	};

	/**
	 * Constructor for ConveyorAgent class
	 * @param name name of the conveyor
	 */
	public ConveyorAgent(String name) {
		super();

		this.name = name;
		this.numKitsToDeliver = 0;
		kitConfig = null;
		state = ConveyorState.IDLE;
		start = false;
	}

	/*
	 * Messages.
	 */

	@Override
	public void msgNeedKit() {
		print("Received msgNeedKit");
		// numKitsToDeliver++;
		start = true;
		stateChanged();
	}

	@Override
	public void msgNeedThisManyKits(int num) {
		this.numKitsToDeliver = num;
		start = true;
		stateChanged();
	}

	@Override
	public void msgGiveMeKit() {
		print("Received msgGiveMeKit with numkits left " + numKitsToDeliver);
		// synchronized (kitsOnConveyor) {
		print("Kits on conveyor size " + kitsOnConveyor.size());
		// for (MyKit mk : kitsOnConveyor) {
		// if (mk.KS != KitStatus.PICKED_UP) {
		if (kitsOnConveyor.size() > 0) {
			kitsOnConveyor.get(0).KS = KitStatus.PICKUP_REQUESTED;
			// print(kitsOnConveyor.get(0).toString() + " will be sent");
			// print(kitsOnConveyor.get(0).toString() + " status is "
			// + kitsOnConveyor.get(0).KS.toString());
		}
		stateChanged();
	}

	@Override
	public void msgTakeKitAway(Kit k) {
		print("Received msgTakeKitAway");
		print("Taking " + k.toString() + " away");
		MyKit mk = new MyKit(k);
		mk.KS = KitStatus.AWAITING_DELIVERY;
		kitsOnOutboundConveyor.add(mk);
		print(mk.toString() + " was added to outbound conveyor.");
		stateChanged();
	}

	@Override
	public void msgBringEmptyKitDone() {
		print("Received msgBringEmptyKitDone from graphics");
		animation.release();
		incomingKit.KS = KitStatus.ARRIVED_AT_PICKUP_LOCATION;
		stateChanged();
	}

	@Override
	public void msgGiveKitToKitRobotDone() {
		print("Received msgGiveKitToKitRobotDone from graphics");
		animation.release();
		stateChanged();
	}

	@Override
	public void msgReceiveKitDone() {
		print("Received msgReceiveKitDone from graphics");
		kitsOnOutboundConveyor.remove(0);
		// animation.release();
		stateChanged();
	}

	/*
	 * Scheduler
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */

	@Override
	public boolean pickAndExecuteAnAction() {

		if (start) {
			synchronized (kitsOnConveyor) {
				for (MyKit mk : kitsOnConveyor) {
					// Send the kit if it has reached the "stop" position on the
					// conveyor where the kit robot can pick it up and the kit
					// robot can pick it up.
					if (mk.KS == KitStatus.PICKUP_REQUESTED
							&& state != ConveyorState.TRANSFERRING_KIT) {
						state = ConveyorState.TRANSFERRING_KIT;
						mk.KS = KitStatus.PICKED_UP;
						// print("About to send kit");
						sendKit(mk);
						return true;
					}
				}
			}

			synchronized (kitsOnConveyor) {
				for (MyKit mk : kitsOnConveyor) {
					// Send the kit if it has reached the "stop" position on the
					// conveyor where the kit robot can pick it up.
					if (mk.KS == KitStatus.ARRIVED_AT_PICKUP_LOCATION) {
						mk.KS = KitStatus.AWAITING_PICKUP;
						kitrobot.msgKitReadyForPickup();
						// return true;
					}
				}
			}

			// Place kit onto conveyor and start moving it into the cell if
			// a new kit was requested by the kit robot
			if (numKitsToDeliver > 0) {
				numKitsToDeliver--;
				prepareKit();
				return true;
			}

			// print("Checking kits on outbound conveyor");
			synchronized (kitsOnOutboundConveyor) {
				for (MyKit mk : kitsOnOutboundConveyor) {
					// Place kit onto conveyor and start moving it out of
					// the cell if the kit robot has dropped off a completed kit
					if (mk.KS == KitStatus.AWAITING_DELIVERY) {
						print(mk.toString() + " is awaiting delivery");
						mk.KS = KitStatus.MOVING_OUT;
						deliverKit(mk);
						return true;
					}
				}
			}

			if (kitsOnConveyor.size() == 0) {
				kitrobot.msgNoKitsLeftOnConveyor();
				// start = false;
				// return true;
			} else {
				print("KitRobot needs to pick up kit");
				kitrobot.msgKitReadyForPickup();
				// print("There are " + kitsOnConveyor.size()
				// + " kits on my conveyor list");
				// return true;
			}

		}

		/*
		 * Tried all rules and found no actions to fire. Return false to the
		 * main loop of abstract base class Agent and wait.
		 */
		return false;
	}

	/*
	 * Actions
	 */

	/**
	 * Generate a new kit to move into the kitting cell.
	 */
	private void prepareKit() {
		print("Requesting new kit");
		Kit k = new Kit(kitConfig);
		incomingKit = new MyKit(k);
		// print("Got a permit");
		kitsOnConveyor.add(incomingKit);
		if (mockgraphics != null) {
			mockgraphics.msgBringEmptyKit(k.kitGraphics);
		}
		if (conveyorGraphics != null) {
			print("Asking conveyor graphics to animate a new kit");
			if (k.kitGraphics == null) {
				print("kitGraphics null");
			}
			conveyorGraphics.msgBringEmptyKit(k.kitGraphics);
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print("Got permit");
		stateChanged();
	}

	/**
	 * Send an empty kit to the kitrobot
	 * @param k the kit being sent.
	 */
	private void sendKit(MyKit mk) {
		print("Sending kit to kit robot");
		if (mockgraphics != null) {
			mockgraphics.msgGiveKitToKitRobot(mk.kit.kitGraphics);
		}
		if (conveyorGraphics != null) {
			conveyorGraphics.msgGiveKitToKitRobot(mk.kit.kitGraphics);
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kitrobot.msgHereIsKit(mk.kit);
		state = ConveyorState.IDLE;
		kitsOnConveyor.remove(mk);

		stateChanged();
	}

	/**
	 * Send a finished kit out of the cell.
	 * @param k the kit being delivered.
	 */
	private void deliverKit(MyKit mk) {
		print("Asking graphics to deliver the kit");
		if (mockgraphics != null) {
			mockgraphics.msgReceiveKit(mk.kit.kitGraphics);
		}
		if (conveyorGraphics != null) {
			conveyorGraphics.msgReceiveKit(mk.kit.kitGraphics);
		}

		// try {
		// animation.acquire();
		// } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the kitrobot.
	 * @param kr the kitrobot
	 */
	public void setKitRobot(KitRobot kr) {
		this.kitrobot = kr;
	}

	/**
	 * GUI Hack to set the reference to the FCS.
	 * @param fcs the FCS
	 */
	public void setFCS(FCSAgent fcs) {
		this.fcs = fcs;
	}

	/**
	 * GUI Hack to set the reference to this class' gui component
	 * @param gc the gui representation of conveyor
	 */
	@Override
	public void setGraphicalRepresentation(DeviceGraphics gc) {
		this.conveyorGraphics = (ConveyorGraphics) gc;
	}

	public MockGraphics getMockgraphics() {
		return mockgraphics;
	}

	public void setMockgraphics(MockGraphics mockgraphics) {
		this.mockgraphics = mockgraphics;
	}

	@Override
	public String getName() {
		return name;
	}

	public MyKit getIncomingKit() {
		return incomingKit;
	}

	public void setIncomingKit(MyKit incomingKit) {
		this.incomingKit = incomingKit;
	}

	public int getNumKitsToDeliver() {
		return numKitsToDeliver;
	}

	public void setNumKitsToDeliver(int numKitsToDeliver) {
		this.numKitsToDeliver = numKitsToDeliver;
	}

	public Semaphore getAnimation() {
		return animation;
	}

	public void setAnimation(Semaphore animation) {
		this.animation = animation;
	}

	public KitRobot getKitrobot() {
		return kitrobot;
	}

	public void setKitrobot(KitRobot kitrobot) {
		this.kitrobot = kitrobot;
	}

	public FCSAgent getFcs() {
		return fcs;
	}

	public ConveyorGraphics getConveyorGraphics() {
		return conveyorGraphics;
	}

	public List<MyKit> getKitsOnConveyor() {
		return kitsOnConveyor;
	}

	@Override
	public void msgHereIsKitConfiguration(KitConfig config) {
		this.kitConfig = config;
	}

}

package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.KitRobotGraphics;
import agent.data.Kit;
import agent.interfaces.Camera;
import agent.interfaces.Conveyor;
import agent.interfaces.KitRobot;
import agent.interfaces.Stand;
import agent.test.mock.MockGraphics;

/**
 * Kit Robot brings moves kits to and from the conveyor and arranges kits on the kitting stand. It is responsible for
 * moving the assembled kits on the stand into the inspection area for the Camera. Interacts with the stand, Conveyor
 * and Camera.
 * 
 * @author Daniel Paje
 */
public class KitRobotAgent extends Agent implements KitRobot {

	private final List<MyKit> myKits = Collections.synchronizedList(new ArrayList<MyKit>());

	// Tracks stand positions and whether or not they are open
	Map<Integer, Boolean> standPositions = Collections.synchronizedMap(new TreeMap<Integer, Boolean>());

	private boolean kitWaitingOnConveyor;
	private boolean kitRequested;
	private int numKitsToMake;
	private int numKitsRequested;

	// Used to prevent animations from overlapping
	Semaphore animation = new Semaphore(0, true);

	// References to other agents
	private Stand stand;
	private Conveyor conveyor;
	private Camera camera;
	private KitRobotGraphics kitrobotGraphics;
	private MockGraphics mockgraphics;

	private KitRobotState state;

	private enum KitRobotState {
		IDLE, HOLDING_KIT, NOT_HOLDING_KIT
	};

	private final String name;

	/**
	 * Inner class encapsulates kit and adds states relevant to the stand
	 * 
	 * @author dpaje
	 */
	public class MyKit {
		public Kit kit;
		public KitStatus KS;
		public int location;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.AWAITING_PICKUP;
		}
	}

	public enum KitStatus {
		AWAITING_PICKUP, REQUESTED, PICKED_UP, ON_STAND, MARKED_FOR_INSPECTION, AWAITING_INSPECTION, FAILED_INSPECTION, PASSED_INSPECTION, SHIPPED;
	};

	/**
	 * Constructor for KitRobotAgent class
	 * 
	 * @param name
	 *            name of the kitrobot
	 */
	public KitRobotAgent(String name) {
		super();

		this.name = name;
		kitWaitingOnConveyor = false;
		kitRequested = false;
		numKitsRequested = 0;
		numKitsToMake = 0;
		state = KitRobotState.IDLE;

		// Don't assume stand is empty
		standPositions.put(0, true);
		standPositions.put(1, false);
		standPositions.put(2, false);
	}

	/*
	 * Messages
	 */

	@Override
	public void msgNeedThisManyKits(int total) {
		print("Received msgNeedThisManyKits with amount " + total);
		conveyor.msgNeedThisManyKits(total);
		numKitsToMake = total;
		numKitsRequested = 0;
		kitRequested = false;
		stateChanged();
	}

	@Override
	public void msgKitReadyForPickup() {
		print("Received msgKitReadyForPickup");
		kitWaitingOnConveyor = true;
		stateChanged();
	}

	@Override
	public void msgNoKitsLeftOnConveyor() {
		print("Received msgNoKitsLeftOnConveyor");
		kitWaitingOnConveyor = false;
		// stateChanged();
	}

	@Override
	public void msgHereIsKit(Kit k) {
		print("Received msgHereIsKit");
		print("Adding kit " + k.toString());
		kitRequested = false;
		MyKit mk = new MyKit(k);
		myKits.add(mk);
		stateChanged();
	}

	@Override
	public void msgNeedKit(int standLocation) {
		print("Received msgNeedKit for stand location " + standLocation);
		standPositions.put(standLocation, true);
		stateChanged();
	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		print("Received msgMoveKitToInspectionArea");
		print(k.toString() + " should be moved.");
		synchronized (myKits) {
			// print("Acquiring in MKTIA");
			print("Mykits size: " + myKits.size());
			for (MyKit mk : myKits) {
				if (mk.kit.equals(k)) {
					mk.KS = KitStatus.MARKED_FOR_INSPECTION;
					print("Found kit to inspect");
					break;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgKitPassedInspection() {
		print("Received msgKitPassedInspection");
		synchronized (myKits) {
			// print("Acquiring in KPI");
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.AWAITING_INSPECTION) {
					mk.KS = KitStatus.PASSED_INSPECTION;
					break;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgKitFailedInspection() {
		print("Received msgKitFailedInspection");
		synchronized (myKits) {
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.AWAITING_INSPECTION) {
					mk.KS = KitStatus.FAILED_INSPECTION;
					break;
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		print("Received msgPlaceKitOnConveyorDone from graphics");
		animation.release();
		// stateChanged();
	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		print("Received msgPlaceKitInInspectionAreaDone from graphics");
		animation.release();
		// stateChanged();
	}

	@Override
	public void msgPlaceKitOnStandDone() {
		print("Received msgPlaceKitOnStandDone from graphics");
		animation.release();
		// stateChanged();
	}

	/*
	 * Scheduler
	 * 
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {

		// Kit needs to be shipped out of the kitting cell
		synchronized (myKits) {
			// print("Acquiring in scheduler");
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.PASSED_INSPECTION) {
					mk.KS = KitStatus.SHIPPED;
					numKitsToMake--;
					shipKit(mk);
					return true;
				}
			}
		}

		// Failed kits should be placed first
		synchronized (myKits) {
			// print("Acquiring in scheduler");
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.FAILED_INSPECTION) {
					mk.KS = KitStatus.PICKED_UP;
					state = KitRobotState.HOLDING_KIT;
					// Sets the old location of the kit to false so the kitrobot
					// can put it back there (or at another
					// position if necessary)
					standPositions.put(mk.location, true);
					standPositions.put(0, true);
					// TODO: This should ask the stand to place at the kit's
					// previous location.
					placeKitOnStand(mk);
					return true;
				}
			}
		}

		// Kit needs to be inspected
		synchronized (myKits) {
			// print("Acquiring in scheduler");
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.MARKED_FOR_INSPECTION && standPositions.get(0)) {
					mk.KS = KitStatus.AWAITING_INSPECTION;
					placeKitInInspectionArea(mk);
					return true;
				}
			}
		}

		// Pick up a kit from conveyor
		// AwaitingPickup is the default state for MyKit which is
		// created when conveyor sends hereIsKit
		synchronized (myKits) {
			// print("Acquiring in scheduler");
			for (MyKit mk : myKits) {
				if (mk.KS == KitStatus.AWAITING_PICKUP && (standPositions.get(1) || standPositions.get(2))) {
					mk.KS = KitStatus.PICKED_UP;
					state = KitRobotState.HOLDING_KIT;
					placeKitOnStand(mk);
					return true;
				}
			}
		}

		// We will always attempt to fill the stand, in case a kit fails
		// inspection. If the last kit is unneeded, we'll just put it on the
		// "bad" conveyor.
		if (kitWaitingOnConveyor && !kitRequested && state != KitRobotState.HOLDING_KIT
				&& (standPositions.get(1) || standPositions.get(2))) {
			kitRequested = true;
			conveyor.msgGiveMeKit();
			// return true;
		}

		/*
		 * Tried all rules and found no actions to fire. Return false to the main loop of abstract base class Agent and
		 * wait.
		 */
		return false;
	}

	/*
	 * Actions
	 */

	/**
	 * Takes a kit from the conveyor and place it on the stand.
	 */
	private void placeKitOnStand(MyKit mk) {
		print("Attempting to place kit");
		// Only need to check 1 and 2
		for (int loc = 1; loc < 3; loc++) {
			if (standPositions.get(loc) == true) {
				print("Found location.");
				standPositions.put(loc, false);
				mk.location = loc;
				mk.KS = KitStatus.ON_STAND;
				if (mockgraphics != null) {
					mockgraphics.msgPlaceKitOnStand(mk.kit.kitGraphics, loc);
				}
				if (kitrobotGraphics != null) {
					kitrobotGraphics.msgPlaceKitOnStand(mk.kit.kitGraphics, loc);
				}
				try {
					animation.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// print("Got permit");
				state = KitRobotState.NOT_HOLDING_KIT;
				stand.msgHereIsKit(mk.kit, loc);
				// print("Kit placed. Now asking stand to place kit");
				break;
			}
		}
		stateChanged();
	}

	/**
	 * Places an assembled kit on the stand into the inspection area (also on the stand).
	 * 
	 * @param k
	 *            the kit being placed.
	 */
	private void placeKitInInspectionArea(MyKit mk) {
		standPositions.put(0, false);
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitInInspectionArea(mk.kit.kitGraphics);
		}
		if (kitrobotGraphics != null) {
			if (mk.kit == null) {
				print("Inspection Kit is null");
			}
			if (mk.kit.kitGraphics == null) {
				print("Inspection KitGraphics null");
			}
			kitrobotGraphics.msgPlaceKitInInspectionArea(mk.kit.kitGraphics);
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// print("Got permit");

		camera.msgInspectKit(mk.kit);

		stand.msgMovedToInspectionArea(mk.kit, mk.location);
		stateChanged();
	}

	/**
	 * Places a completed kit on the conveyor for removal from the kitting cell.
	 * 
	 * @param k
	 *            the kit being shipped out of the kitting cell.
	 */
	private void shipKit(MyKit mk) {
		print("Removing " + mk.kit.toString());
		myKits.remove(mk);
		if (mockgraphics != null) {
			mockgraphics.msgPlaceKitOnConveyor();
		}
		if (kitrobotGraphics != null) {
			kitrobotGraphics.msgPlaceKitOnConveyor();
		}
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conveyor.msgTakeKitAway(mk.kit);
		stand.msgShippedKit();
		standPositions.put(0, true);
		stateChanged();
	}

	/**
	 * GUI Hack to set the reference to the conveyor.
	 * 
	 * @param co
	 *            the conveyor
	 */
	public void setConveyor(Conveyor co) {
		this.conveyor = co;
	}

	/**
	 * GUI Hack to set the reference to the camera.
	 * 
	 * @param ca
	 *            the camera
	 */
	public void setCamera(Camera ca) {
		this.camera = ca;
	}

	/**
	 * GUI Hack to set the reference to the stand.
	 * 
	 * @param st
	 *            the stand
	 */
	public void setStand(Stand st) {
		this.stand = st;
	}

	/**
	 * GUI Hack to set the reference to this class' gui component
	 * 
	 * @param gc
	 *            the gui representation of kit robot
	 */
	@Override
	public void setGraphicalRepresentation(DeviceGraphics gkr) {
		this.kitrobotGraphics = (KitRobotGraphics) gkr;
	}

	public MockGraphics getMockGraphics() {
		return mockgraphics;
	}

	public void setMockGraphics(MockGraphics mockgraphics) {
		this.mockgraphics = mockgraphics;
	}

	public Map<Integer, Boolean> getStandPositions() {
		return standPositions;
	}

	public void setStandPositions(Map<Integer, Boolean> standPositions) {
		this.standPositions = standPositions;
	}

	public List<MyKit> getMyKits() {
		return myKits;
	}

	@Override
	public String getName() {
		return name;
	}

}

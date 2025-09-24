package agent;

import java.util.ArrayList;

import DeviceGraphics.DeviceGraphics;
import agent.data.Kit;
import agent.interfaces.FCS;
import agent.interfaces.KitRobot;
import agent.interfaces.PartsRobot;
import agent.interfaces.Stand;

/**
 * Stand manages the kits that are placed on the kitting stand. Interacts with the Factory Control System (FCS), the
 * Parts Robot and the Kit Robot.
 * 
 * @author Daniel Paje
 */
public class StandAgent extends Agent implements Stand {

	// References to other agents
	private KitRobot kitrobot;
	private PartsRobot partsrobot;
	private FCS fcs;

	private final String name;

	private final ArrayList<MyKit> MyKits;

	private int numKitsToMake;
	private int numKitsMade;
	private int oldLocForKitBeingInspected;
	private boolean start;
	private StandStatus status;

	private enum StandStatus {
		IDLE, NEED_TO_INITIALIZE, KIT_REQUESTED, DONE
	};

	/**
	 * Inner class encapsulates kit and adds states relevant to the stand
	 * 
	 * @author dpaje
	 */
	private class MyKit {
		public Kit kit;
		public KitStatus KS;

		public MyKit(Kit k) {
			this.kit = k;
			this.KS = KitStatus.RECEIVED;
		}
	}

	public enum KitStatus {
		HOLDING, RECEIVED, PLACED_ON_STAND, ASSEMBLED, MARKED_FOR_INSPECTION, AWAITING_INSPECTION, INSPECTED, SHIPPED, DELIVERED;
	};

	/**
	 * Constructor for StandAgent class
	 * 
	 * @param name
	 *            name of the stand
	 */
	public StandAgent(String name) {
		super();

		this.name = name;
		numKitsToMake = 0;
		numKitsMade = 0;
		start = false;
		status = StandStatus.IDLE;

		MyKits = new ArrayList<MyKit>();
		MyKits.add(0, null);
		MyKits.add(1, null);
		MyKits.add(2, null);
	}

	/*
	 * Messages
	 */

	@Override
	public void msgMakeKits(int numKits) {
		print("Received msgMakeKits");
		numKitsToMake = numKits;
		numKitsMade = 0;
		status = StandStatus.NEED_TO_INITIALIZE;
		start = true;
		stateChanged();
	}

	@Override
	public void msgHereIsKit(Kit k, int destination) {
		print(k.toString());
		print("Received msgHereIsKit with destination " + destination);
		status = StandStatus.IDLE;
		if (MyKits.get(0) != null) {
			if (MyKits.get(0).kit == k) {
				print(k.toString() + " must have failed inspection");
				MyKits.set(0, null);
			}
		}
		MyKits.set(destination, new MyKit(k));
		stateChanged();
	}

	@Override
	public void msgKitAssembled(Kit k) {
		print("Received msgKitAssembled");
		for (MyKit mk : MyKits) {
			if (mk != null) {
				if (mk.KS != KitStatus.HOLDING) {
					if (mk.kit == k) {
						print(k.toString() + " was assembled.");
						mk.KS = KitStatus.ASSEMBLED;
						break;
					}
				}
			}
		}
		stateChanged();
	}

	@Override
	public void msgMovedToInspectionArea(Kit k, int oldLocation) {
		print("Received msgMovedToInspectionArea");
		if (MyKits.get(oldLocation) == null) {
			print("OMGNOOOO");
		}
		MyKits.set(0, MyKits.get(oldLocation));
		print(MyKits.get(0).kit.toString() + " is now in the inspection area");

		oldLocForKitBeingInspected = oldLocation;
		// MyKits.set(oldLocation, null);

		stateChanged();
	}

	@Override
	public void msgShippedKit() {
		print("Received msgShippedKit");
		numKitsMade++;
		MyKits.set(0, null);
		// MyKits.get(0).KS = KitStatus.SHIPPED;
		MyKits.set(oldLocForKitBeingInspected, null);
		print(numKitsToMake - numKitsMade + " kits left to make");
		fcs.msgShippedKit();
		stateChanged();
	}

	/*
	 * Scheduler
	 * 
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {

		if (MyKits.get(0) != null) {
			print(MyKits.get(0).kit.toString() + " is in the inspection area");
		}

		if (status == StandStatus.NEED_TO_INITIALIZE) {
			initialize();
			return true;
		}

		if (start) {
			if (numKitsMade == numKitsToMake) {
				finalizeOrder();
				return true;
			}

			for (MyKit mk : MyKits) {
				if (mk != null) {
					if (mk.KS != KitStatus.HOLDING) {
						// Received a kit from kit robot
						if (mk.KS == KitStatus.RECEIVED) {
							mk.KS = KitStatus.PLACED_ON_STAND;
							placeKit(mk);
							return true;
						}
					}
				}
			}

			for (MyKit mk : MyKits) {
				if (mk != null) {
					if (mk.KS != KitStatus.HOLDING) {
						// Kit needs to be inspected
						if (mk.KS == KitStatus.ASSEMBLED) {
							mk.KS = KitStatus.AWAITING_INSPECTION;
							requestInspection(mk);
							return true;
						}
					}
				}
			}

			// Attempt to request a new kit if necessary
			int loc = 0;
			int count = 0;
			for (int i = 0; i < 3; i++) {
				if (MyKits.get(i) == null) {
					count++;
				}
			}
			if (numKitsToMake > 0 && numKitsToMake > numKitsMade + 3 - count
					&& (MyKits.get(1) == null || MyKits.get(2) == null)) {
				print("NumKits(" + numKitsToMake + ") to make greater than numKitsMade(" + numKitsMade
						+ "). Stand positions empty count: " + count);
				if (MyKits.get(1) == null && MyKits.get(2) == null) {
					print("Neither position full");
					status = StandStatus.KIT_REQUESTED;
					requestKit(loc = 1);
					print("I'm requesting a new kit at position 1");
					return true;
				} else if (MyKits.get(1) == null && MyKits.get(2) != null || MyKits.get(2) == null
						&& MyKits.get(1) != null) {
					print("One position full, but need to make more than 1 kit.");
					status = StandStatus.KIT_REQUESTED;
					requestKit(loc = MyKits.get(1) == null ? 1 : 2);
					print("I'm requesting a new kit at position " + loc);
					return true;
				}
			}
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
	 * Tells the kitrobot how many kits it should expect to make.
	 */
	private void initialize() {
		print("Initializing KitRobot and Conveyor");
		status = StandStatus.IDLE;
		kitrobot.msgNeedThisManyKits(numKitsToMake);
		stateChanged();
	}

	/**
	 * Requests a kit from kit robot at the specified location.
	 * 
	 * @param index
	 *            the empty location on the stand where the new kit will be placed
	 */
	private void requestKit(int index) {
		status = StandStatus.IDLE;
		if (index == 0) {
			print("WTFISTHISSHIT");
		}
		MyKit mk = new MyKit(null);
		mk.KS = KitStatus.HOLDING;
		MyKits.set(index, mk);
		kitrobot.msgNeedKit(index);
		stateChanged();
	}

	/**
	 * Places a kit into the list of kits on the stand
	 * 
	 * @param k
	 *            the kit being placed
	 */
	private void placeKit(MyKit mk) {
		// kitRequested--;

		print("Kit ID is " + mk.kit.toString());
		// print(kitsOnStand.size() + " kits on stand");
		partsrobot.msgUseThisKit(mk.kit);

		stateChanged();

	}

	/**
	 * Requests inspection of an assembled kit.
	 * 
	 * @param k
	 *            the kit to be inspected.
	 */
	private void requestInspection(MyKit mk) {
		kitrobot.msgMoveKitToInspectionArea(mk.kit);
		stateChanged();
	}

	/**
	 * Updates the FCS when a batch of kits has been completed.
	 */
	private void finalizeOrder() {
		start = false;
		status = StandStatus.DONE;
		fcs.msgOrderFinished();
		System.out.println("\n====================");
		print("I FINISHED HURRAY");
		System.out.println("====================");
		// No need to call stateChanged() here as presumably the kitting cell is
		// idle (i.e., no queued orders)
	}

	/**
	 * GUI Hack to set the reference to the partsrobot.
	 * 
	 * @param pr
	 *            the partsrobot
	 */
	public void setPartsRobot(PartsRobot pr) {
		this.partsrobot = pr;
	}

	/**
	 * GUI Hack to set the reference to the kitrobot.
	 * 
	 * @param kr
	 *            the kitrobot
	 */
	public void setKitRobot(KitRobot kr) {
		this.kitrobot = kr;
	}

	/**
	 * GUI Hack to set the reference to the FCS.
	 * 
	 * @param fcs
	 *            the fcs
	 */
	public void setFCS(FCS fcs) {
		this.fcs = fcs;
	}

	@Override
	public String getName() {
		return name;
	}

	public KitRobot getKitrobot() {
		return kitrobot;
	}

	public void setKitrobot(KitRobot kitrobot) {
		this.kitrobot = kitrobot;
	}

	public PartsRobot getPartsrobot() {
		return partsrobot;
	}

	public FCS getFcs() {
		return fcs;
	}

	public void setFcs(FCS fcs) {
		this.fcs = fcs;
	}

	public int getNumKitsToMake() {
		return numKitsToMake;
	}

	public void setNumKitsToMake(int numKitsToMake) {
		this.numKitsToMake = numKitsToMake;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public ArrayList<MyKit> getMyKits() {
		return MyKits;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
	}
}

package agent.test.mock;

import agent.data.Kit;
import agent.interfaces.KitRobot;

/**
 * Mock kitrobot. Messages received simply add an entry to the mock agent's log.
 * 
 * @author Daniel Paje
 */
public class MockKitRobot extends MockAgent implements KitRobot {

	public EventLog log;

	public MockKitRobot(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgHereIsKit(Kit k) {
		log.add(new LoggedEvent("Received message msgHereIsKit"));

	}

	@Override
	public void msgNeedKit(int standLocation) {
		log.add(new LoggedEvent("Received message msgNeedKit"));

	}

	@Override
	public void msgMoveKitToInspectionArea(Kit k) {
		log.add(new LoggedEvent("Received message msgMoveKitToInspectionArea"));

	}

	@Override
	public void msgKitPassedInspection() {
		log.add(new LoggedEvent("Received message msgKitPassedInspection"));

	}

	@Override
	public void msgPlaceKitOnConveyorDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitInInspectionAreaDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnStandDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgNeedThisManyKits(int total) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgKitReadyForPickup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgNoKitsLeftOnConveyor() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgKitFailedInspection() {
		// TODO Auto-generated method stub

	}

}

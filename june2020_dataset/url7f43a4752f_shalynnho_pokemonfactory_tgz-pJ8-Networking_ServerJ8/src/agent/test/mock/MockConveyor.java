package agent.test.mock;

import agent.data.Kit;
import agent.interfaces.Conveyor;
import factory.KitConfig;

/**
 * Mock conveyor. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockConveyor extends MockAgent implements Conveyor {

	public EventLog log;

	public MockConveyor(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgNeedKit() {
		log.add(new LoggedEvent("Received message msgNeedKit"));
	}

	@Override
	public void msgTakeKitAway(Kit k) {
		log.add(new LoggedEvent("Received message msgTakeKitAway"));

	}

	@Override
	public void msgBringEmptyKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGiveKitToKitRobotDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceiveKitDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgHereIsKitConfiguration(KitConfig config) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received message msgHereIsKitConfiguration"));
	}

	@Override
	public void msgGiveMeKit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgNeedThisManyKits(int num) {
		// TODO Auto-generated method stub

	}

}

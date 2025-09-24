package agent.test.mock;

import agent.data.Kit;
import agent.interfaces.Stand;

/**
 * Mock stand. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockStand extends MockAgent implements Stand {

	public EventLog log;

	public MockStand(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgMakeKits(int numKits) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received message msgMakeKits with "+numKits+" kits ordered"));
	}

	@Override
	public void msgKitAssembled(Kit k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereIsKit(Kit k, int dest) {
		log.add(new LoggedEvent("Received message msgHereIsKit"));

	}

	@Override
	public void msgShippedKit() {
		log.add(new LoggedEvent("Received message msgShippedKit"));

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgMovedToInspectionArea(Kit k, int oldLocation) {
		log.add(new LoggedEvent("Received message msgMovedToInspectionArea"));
	}

}

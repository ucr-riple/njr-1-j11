package agent.test.mock;

import java.util.ArrayList;

import factory.Order;
import factory.PartType;
import agent.interfaces.FCS;

/**
 * Mock FCS. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockFCS extends MockAgent implements FCS {

	public EventLog log;

	public MockFCS(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgOrderFinished() {
		log.add(new LoggedEvent("Order finished"));
	}

	@Override
	public void msgAddKitsToQueue(Order o) {		
	}

	@Override
	public void msgStopMakingKit(Order o) {		
	}

	@Override
	public void msgStartProduction() {
	}

	@Override
	public void msgAddNewPartType(PartType part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBreakLane(int laneNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgShippedKit() {
		// TODO Auto-generated method stub
		
	}

}

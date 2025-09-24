package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.KitRobotGraphics;
import Networking.Request;

/**
 * Mock graphical representation for the kitrobot. Messages received simply add
 * an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockKitRobotGraphics extends MockAgent implements KitRobotGraphics, DeviceGraphics {

	public EventLog log;

	public MockKitRobotGraphics(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		log.add(new LoggedEvent("Received message msgPlaceKitOnStand"));

	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		log.add(new LoggedEvent("Received message msgPlaceKitInInspectionArea"));

	}

	@Override
	public void msgPlaceKitOnConveyor() {
		log.add(new LoggedEvent("Received message msgPlaceKitOnConveyor"));

	}

	@Override
	public void receiveData(Request req) {
		// does nothing. requirement of DeviceGraphics
	}

}

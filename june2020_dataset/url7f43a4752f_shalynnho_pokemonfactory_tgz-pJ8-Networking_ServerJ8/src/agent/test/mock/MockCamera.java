package agent.test.mock;

import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.NestGraphics;
import agent.data.Kit;
import agent.interfaces.Camera;
import agent.interfaces.Nest;

/**
 * Mock camera. Messages received simply add an entry to the mock agent's log.
 * @author Daniel Paje
 */
public class MockCamera extends MockAgent implements Camera {

	public EventLog log;

	public MockCamera(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
	}

	@Override
	public void msgInspectKit(Kit kit) {
		log.add(new LoggedEvent("Received message msgInspectKit"));

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgIAmFull(Nest nest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakePictureNestDone(NestGraphics nest, boolean d,
			NestGraphics nest2, boolean d2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgTakePictureKitDone(KitGraphics kit, boolean done) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgResetSelf() {
		// TODO Auto-generated method stub

	}

}

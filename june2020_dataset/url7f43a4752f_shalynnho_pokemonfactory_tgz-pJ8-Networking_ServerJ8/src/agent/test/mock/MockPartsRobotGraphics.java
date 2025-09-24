package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.KitGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.PartsRobotGraphics;
import Networking.Request;

public class MockPartsRobotGraphics extends MockAgent implements PartsRobotGraphics, DeviceGraphics{

	public EventLog log;
	public MockPartsRobotGraphics(String name, EventLog log) {
		super(name, log);
		this.log = super.getLog();
	}

	@Override
	public void receiveData(Request req) {

		log.add(new LoggedEvent("Received message msgreceiveData"));
		
	}

	/*@Override
	public void pickUpPart(PartGraphics part) {

		log.add(new LoggedEvent("Received message msgpickUpPart"));
		
	}

	@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit) {

		log.add(new LoggedEvent("Received message msgPartToKit"));
		
	}*/

	@Override
	public void dropPartFromArm(PartGraphics part, int arm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickUpPart(PartGraphics part, int arm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit, int arm) {
		// TODO Auto-generated method stub
		
	}

	

}

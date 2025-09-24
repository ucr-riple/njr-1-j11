package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.NestGraphics;
import Networking.Request;
import Utils.Location;
import agent.Agent;
import agent.NestAgent;

public class MockNestGraphics extends Agent implements DeviceGraphics, NestGraphics {

	NestAgent nest;
	
	@Override
	public void receivePart(PartGraphics part) {
		// TODO Auto-generated method stub
		nest.msgReceivePartDone();
	}

	@Override
	public void givePartToPartsRobot(PartGraphics part) {
		// TODO Auto-generated method stub
		nest.msgGivePartToPartsRobotDone();
	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub
		nest.msgPurgingDone();
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		// TODO Auto-generated method stub
		
	}

	public void setNestAgent(NestAgent nest) {
		this.nest = nest;
	}

	@Override
	public int getNestID() {
		return 0;
	}
}

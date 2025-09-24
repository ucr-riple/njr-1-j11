package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.LaneGraphics;
import Networking.Request;
import agent.Agent;
import agent.LaneAgent;

public class MockLaneGraphics extends Agent implements DeviceGraphics, LaneGraphics{

	LaneAgent lane;
	
	@Override
	public void receivePart(PartGraphics part) {
		// TODO Auto-generated method stub
		lane.msgReceivePartDone(part);
	}

	@Override
	public void givePartToNest(PartGraphics part) {
		// TODO Auto-generated method stub
		lane.msgGivePartToNestDone(part);
	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub
		lane.msgPurgeDone();
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
	
	public void setLaneAgent(LaneAgent lane) {
		this.lane = lane;
	}

	@Override
	public void unjam() {
		// TODO Auto-generated method stub
		
	}

}

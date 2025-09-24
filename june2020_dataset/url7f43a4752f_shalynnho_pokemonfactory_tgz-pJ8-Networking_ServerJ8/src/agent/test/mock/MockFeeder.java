package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import agent.GantryAgent;
import agent.LaneAgent;
import agent.data.Bin;
import agent.interfaces.Feeder;
import factory.PartType;

/**
 * Mock feeder.
 * @author Daniel Paje
 */
public class MockFeeder extends MockAgent implements Feeder {

	public EventLog log;

	public MockFeeder(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setGantry(GantryAgent gantry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLane(LaneAgent lane) {
		// TODO Auto-generated method stub

	}

	public void setGraphicalRepresentation(DeviceGraphics feeder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgINeedPart(PartType type, LaneAgent lane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereAreParts(PartType type, Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceiveBinDone(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPurgeBinDone(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgFlipDiverterDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgRemoveBinDone() {
		// TODO Auto-generated method stub

	}

}

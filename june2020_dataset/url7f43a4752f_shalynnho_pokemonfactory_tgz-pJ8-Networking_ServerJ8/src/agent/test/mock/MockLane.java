package agent.test.mock;

import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.PartGraphics;
import agent.data.Part;
import agent.interfaces.Lane;
import factory.PartType;

/**
 * Mock Lane
 * @author Daniel Paje
 */
public class MockLane extends MockAgent implements Lane {

	public EventLog log;

	public MockLane(String name) {
		super(name, new EventLog());
		this.log = super.getLog();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgINeedPart(PartType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgHereIsPart(Part p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setGraphicalRepresentation(DeviceGraphics lane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgReceivePartDone(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGivePartToNestDone(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPurgeParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPurgeDone() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgGiveMePart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgChangeAmplitude() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFixYourself() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBreakThis() {
		// TODO Auto-generated method stub
		
	}

}

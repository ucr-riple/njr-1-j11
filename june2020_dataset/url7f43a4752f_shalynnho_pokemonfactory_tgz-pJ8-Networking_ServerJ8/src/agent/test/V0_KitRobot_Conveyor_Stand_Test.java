package agent.test;

import agent.CameraAgent;
import agent.ConveyorAgent;
import agent.FCSAgent;
import agent.KitRobotAgent;
import agent.PartsRobotAgent;
import agent.StandAgent;
import agent.interfaces.PartsRobot;
import agent.test.mock.MockGraphics;

/**
 * Tests the kitting area (Kit robot, conveyor, stand). This is not a JUnit
 * test. Messages are manually sent from classes not part of this area of the
 * cell. These messages assume the other agents/graphics objects completed
 * successfully.
 * @author Daniel Paje
 */
public class V0_KitRobot_Conveyor_Stand_Test {
	static ConveyorAgent conveyor;
	static CameraAgent camera;
	static KitRobotAgent kitrobot;
	static StandAgent stand;
	static FCSAgent fcs;
	static MockGraphics mockgraphics;
	static PartsRobot partsrobot;

	public V0_KitRobot_Conveyor_Stand_Test() {

		conveyor = new ConveyorAgent("conveyor");
		camera = new CameraAgent("camera1");
		kitrobot = new KitRobotAgent("kitrobot");
		stand = new StandAgent("stand");
		fcs = new FCSAgent();
		partsrobot = new PartsRobotAgent("partsrobot");

		mockgraphics = new MockGraphics("mockgraphics");

		stand.setKitRobot(kitrobot);
		stand.setPartsRobot(partsrobot);
		stand.setFCS(fcs);

		kitrobot.setCamera(camera);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
		// kitrobot.setGraphicalRepresentation(mockgraphics.getKitrobotgraphics());
		kitrobot.setMockGraphics(mockgraphics);

		conveyor.setKitRobot(kitrobot);
		// conveyor.setGraphicalRepresentation(mockgraphics.getConveyorgraphics());
		conveyor.setMockgraphics(mockgraphics);

		mockgraphics.setConveyor(conveyor);
		mockgraphics.setKitrobot(kitrobot);

		conveyor.startThread();
		kitrobot.startThread();
		stand.startThread();
		mockgraphics.startThread();
	}

	public static ConveyorAgent getConveyor() {
		return conveyor;
	}

	public static void setConveyor(ConveyorAgent conveyor) {
		V0_KitRobot_Conveyor_Stand_Test.conveyor = conveyor;
	}

	public static KitRobotAgent getKitrobot() {
		return kitrobot;
	}

	public static void setKitrobot(KitRobotAgent kitrobot) {
		V0_KitRobot_Conveyor_Stand_Test.kitrobot = kitrobot;
	}

	public StandAgent getStand() {
		return stand;
	}

	public static void setStand(StandAgent stand) {
		V0_KitRobot_Conveyor_Stand_Test.stand = stand;
	}

	public static FCSAgent getFcs() {
		return fcs;
	}

	public static void setFcs(FCSAgent fcs) {
		V0_KitRobot_Conveyor_Stand_Test.fcs = fcs;
	}

	public static MockGraphics getMockgraphics() {
		return mockgraphics;
	}

	public static void setMockgraphics(MockGraphics mockgraphics) {
		V0_KitRobot_Conveyor_Stand_Test.mockgraphics = mockgraphics;
	}

	public static void main(String[] args) {
		V0_KitRobot_Conveyor_Stand_Test test = new V0_KitRobot_Conveyor_Stand_Test();
		System.out.println("=========================");
		System.out.println("  Starting kit area test");
		System.out.println("=========================");
		test.getStand().msgMakeKits(5);
	}

}
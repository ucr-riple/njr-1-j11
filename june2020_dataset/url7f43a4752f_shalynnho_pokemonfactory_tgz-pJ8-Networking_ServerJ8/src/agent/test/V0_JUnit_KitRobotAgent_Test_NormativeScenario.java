package agent.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import agent.KitRobotAgent;
import agent.KitRobotAgent.KitStatus;
import agent.data.Kit;
import agent.test.mock.MockAgent;
import agent.test.mock.MockCamera;
import agent.test.mock.MockConveyor;
import agent.test.mock.MockKitRobotGraphics;
import agent.test.mock.MockStand;

/**
 * This tests the normative scenario for the kitrobot in the kitting cell. The
 * UUT is the KitRobotAgent. The second tests for 100 kits in succession. Logs
 * for all agents can be printed at the end of each test if necessary.
 * @author Daniel Paje
 */
public class V0_JUnit_KitRobotAgent_Test_NormativeScenario extends TestCase {
	private static final int TESTKITCOUNT = 10;

	protected KitRobotAgent kitrobot;
	protected Date date;

	private final URL URL = V0_JUnit_KitRobotAgent_Test_NormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");

	@Override
	protected void setUp() {
		kitrobot = new KitRobotAgent("kitrobot");
		date = new Date();
	}

	@Override
	protected void tearDown() {
		kitrobot = null;
		date = null;
	}

	/**
	 * This tests the kitrobot's role in kit creation. The number of kits
	 * created is determined by TESTKITCOUNT. The other agents involved in this
	 * scenario are Conveyor, Camera and Stand. The graphics component of
	 * KitRobot is also involved. Note that this scenario exhaustively tests the
	 * messages sent/received and the data manipulation done by the KitRobot.
	 * @throws InterruptedException
	 */
	@Test
	public void testNormativeScenario() throws InterruptedException {
		MockConveyor conveyor = new MockConveyor("conveyor");
		MockCamera camera = new MockCamera("camera");
		MockStand stand = new MockStand("stand");
		MockKitRobotGraphics kitrobotGraphics = new MockKitRobotGraphics(
				"kitrobotGraphics1");

		kitrobot.setCamera(camera);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
		kitrobot.setGraphicalRepresentation(kitrobotGraphics);

		int standLogSize = 0;
		int cameraLogSize = 0;
		int conveyorLogSize = 0;
		int kitrobotGraphicsLogSize = 0;

		List<Kit> testKits = new ArrayList<Kit>();
		for (int i = 0; i < TESTKITCOUNT; i++) {
			testKits.add(new Kit("KIT" + i));
		}

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock Stand should have an empty event(s) log before the kitrobot's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 0, stand.log.size());

		assertEquals(
				"Mock Conveyor should have an empty event(s) log before the kitrobot's scheduler is called. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 0, conveyor.log.size());

		assertEquals(
				"Mock Camera should have an empty event(s) log before the kitrobot's scheduler is called. "
						+ "Instead, the camera's event(s) log reads: "
						+ camera.log.toString(), 0, camera.log.size());

		assertEquals(
				"Mock KitRobotGraphics should have an empty event(s) log before the kitrobot's scheduler is called. "
						+ "Instead, the kitrobotGraphics's event(s) log reads: "
						+ kitrobotGraphics.log.toString(), 0,
				kitrobotGraphics.log.size());

		for (int i = 0; i < TESTKITCOUNT; i++) {
			// Simulate a message to the kitrobot from stand. Pick between 1 and
			// 2. It really doesn't matter so do a math.random()
			final int standLoc = (int) (Math.random() * 2) + 1;

			kitrobot.msgNeedKit(standLoc);

			assertTrue("KitRobot should have set stand position " + standLoc
					+ " to 'true'", kitrobot.getStandPositions().get(standLoc));

			// Invoke the scheduler
			kitrobot.pickAndExecuteAnAction();

			// Scheduler should have fired requestKit()
			assertEquals("Conveyor should have " + (conveyorLogSize + 1)
					+ " event(s) in its log", conveyorLogSize + 1,
					conveyor.log.size());

			assertTrue(
					"Conveyor should have received a kit request. Last logged event(s): "
							+ conveyor.log.getLastLoggedEvent().getMessage(),
					conveyor.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgNeedKit"));

			// Simulate kit delivery. The conveyor messages KitRobot
			kitrobot.msgHereIsKit(testKits.get(i));

			// After receiving the message, the kitrobot should have set a stand
			// position to true (open)
			assertEquals(
					"KitRobot should have added a new MyKit object to its list of MyKits "
							+ kitrobot.getMyKits().size(), 1, kitrobot
							.getMyKits().size());

			kitrobot.pickAndExecuteAnAction();

			// Scheduler should have fired placeKitOnStand()
			assertEquals("KitRobotGraphics should have "
					+ (kitrobotGraphicsLogSize + 1) + " event(s) in its log",
					kitrobotGraphicsLogSize + 1, kitrobotGraphics.log.size());

			assertTrue(
					"KitRobotGraphics should have received a request to animate placing the kit on the stand. Last logged event(s): "
							+ kitrobotGraphics.log.getLastLoggedEvent()
									.getMessage(),
					kitrobotGraphics.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgPlaceKitOnStand"));

			assertFalse("KitRobot should have set stand position " + standLoc
					+ " back to 'false'",
					kitrobot.getStandPositions().get(standLoc));

			assertEquals(
					"KitRobot should have set first MyKit's KitStatus to OnStand",
					kitrobot.getMyKits().get(0).KS, KitStatus.ON_STAND);

			assertEquals("Stand should have " + (standLogSize + 1)
					+ " event(s) in its log", standLogSize + 1,
					stand.log.size());

			assertTrue(
					"Stand should have received a request to add the kit on the stand. Last logged event(s): "
							+ stand.log.getLastLoggedEvent().getMessage(),
					stand.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgHereIsKit"));

			// Simulate kit completion. The stand messages the KitRobot
			kitrobot.msgMoveKitToInspectionArea(testKits.get(i));

			assertEquals(
					"KitRobot should have set first MyKit's KitStatus to MarkedForInspection",
					kitrobot.getMyKits().get(0).KS,
					KitStatus.MARKED_FOR_INSPECTION);

			/*
			 * If the scheduler fires now, the code blocks if the previous
			 * animation isn't complete, so simulate completing the
			 * placeKitOnStand animation and releasing the animation permit. In
			 * this test case, we do this before invoking the scheduler as this
			 * test runs in a single thread (and will get stuck if kitrobot
			 * attempts to acquire a permit) whereas in the factory, the
			 * KitRobotGraphics will be running in another thread.
			 */
			kitrobot.msgPlaceKitOnStandDone();

			// Now it's safe to invoke the scheduler
			kitrobot.pickAndExecuteAnAction();

			// Scheduler should have fired placeKitInInspectionArea();

			assertEquals(
					"KitRobot should have set first MyKit's KitStatus to AwaitingInspection",
					kitrobot.getMyKits().get(0).KS,
					KitStatus.AWAITING_INSPECTION);

			assertEquals("KitRobotGraphics should have "
					+ (kitrobotGraphicsLogSize + 2) + " event(s) in its log",
					kitrobotGraphicsLogSize + 2, kitrobotGraphics.log.size());

			assertTrue(
					"KitRobotGraphics should have received a request to animate placing the kit in the inspection area. Last logged event(s): "
							+ kitrobotGraphics.log.getLastLoggedEvent()
									.getMessage(),
					kitrobotGraphics.log
							.getLastLoggedEvent()
							.getMessage()
							.equals("Received message msgPlaceKitInInspectionArea"));

			assertEquals("Camera should have " + (cameraLogSize + 1)
					+ " event(s) in its log", cameraLogSize + 1,
					camera.log.size());

			assertTrue(
					"Camera should have received a request to inspect the kit. Last logged event(s): "
							+ camera.log.getLastLoggedEvent().getMessage(),
					camera.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgInspectKit"));

			assertEquals("Stand should have " + (standLogSize + 3)
					+ " event(s) in its log", standLogSize + 2,
					stand.log.size());

			assertTrue(
					"Stand should have received a message stating a kit was placed into the inspection area. Last logged event(s): "
							+ conveyor.log.getLastLoggedEvent().getMessage(),
					stand.log
							.getLastLoggedEvent()
							.getMessage()
							.equals("Received message msgMovedToInspectionArea"));

			assertTrue(
					"Camera should have received a request to inspect the kit. Last logged event(s): "
							+ camera.log.getLastLoggedEvent().getMessage(),
					camera.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgInspectKit"));

			// Simulate a successful inspection. Camera messages the KitRobot
			kitrobot.msgKitPassedInspection();

			assertEquals(
					"KitRobot should have set first MyKit's KitStatus to Inspected",
					kitrobot.getMyKits().get(0).KS, KitStatus.PASSED_INSPECTION);

			// Again, release a permit before invoking the scheduler
			kitrobot.msgPlaceKitInInspectionAreaDone();

			kitrobot.pickAndExecuteAnAction();

			// Scheduler should have fired shipKit()

			assertEquals("KitRobotGraphics should have "
					+ (kitrobotGraphicsLogSize + 3) + " event(s) in its log",
					kitrobotGraphicsLogSize + 3, kitrobotGraphics.log.size());

			assertTrue(
					"KitRobotGraphics should have received a request to animate placing the kit on the conveyor. Last logged event(s): "
							+ kitrobotGraphics.log.getLastLoggedEvent()
									.getMessage(),
					kitrobotGraphics.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgPlaceKitOnConveyor"));

			assertEquals("Conveyor should have " + (conveyorLogSize + 2)
					+ " event(s) in its log", conveyorLogSize + 2,
					conveyor.log.size());

			assertTrue(
					"Conveyor should have received a request to take a kit away. Last logged event(s): "
							+ conveyor.log.getLastLoggedEvent().getMessage(),
					conveyor.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgTakeKitAway"));

			assertEquals("Stand should have " + (standLogSize + 3)
					+ " event(s) in its log", standLogSize + 3,
					stand.log.size());

			assertTrue(
					"Stand should have received a message stating a kit was shipped. Last logged event(s): "
							+ conveyor.log.getLastLoggedEvent().getMessage(),
					stand.log.getLastLoggedEvent().getMessage()
							.equals("Received message msgShippedKit"));

			assertEquals(
					"KitRobot should have removed a MyKit object from its list of MyKits ",
					0, kitrobot.getMyKits().size());

			// Success!

			// Again, release a permit before continuing the test
			kitrobot.msgPlaceKitOnConveyorDone();

			// Save the log sizes for the next iteration
			standLogSize = stand.log.size();
			conveyorLogSize = conveyor.log.size();
			cameraLogSize = camera.log.size();
			kitrobotGraphicsLogSize = kitrobotGraphics.log.size();
		}

		// Write logs to file. Don't do this for now.
		// List<MockAgent> MockAgents = new ArrayList<MockAgent>();
		// MockAgents.add(conveyor);
		// MockAgents.add(camera);
		// MockAgents.add(stand);
		// MockAgents.add(kitrobotGraphics);
		// generateLogFile("KitRobotAgent_Test_Normative",
		// this.getLogs(MockAgents));
	}

	/**
	 * This is a modified version of Sean Turner's helper function which prints
	 * out the logs from any number of MockAgents. This should help to assist in
	 * debugging.
	 * @return a string containing the logs from the mock agents.
	 */
	public String getLogs(List<MockAgent> MockAgents) {
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		sb.append("Found logs for " + MockAgents.size() + " Mock agent(s).");
		sb.append(newLine);

		for (MockAgent m : MockAgents) {
			sb.append(newLine);
			sb.append("-------" + m.getName() + " Log-------");
			sb.append(newLine);
			sb.append(m.getLog().toString());
			sb.append("-------End" + m.getName() + " Log-------");
			sb.append(newLine);
		}

		// System.out.println(sb.toString());

		return sb.toString();

	}

	/**
	 * Generates a log file of the messages received by MockAgents from the
	 * KitRobotAgent
	 * @param test the name of the test
	 * @param log string representation of a log to be printed to the file
	 */
	public void generateLogFile(String test, String log) {

		FileOutputStream fos;

		System.out.println("Message logs saved to " + FILEPATH + test + "("
				+ date.toString() + ").txt");

		try {
			fos = new FileOutputStream(FILEPATH + test + "(" + date.toString()
					+ ").txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(log);
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

}

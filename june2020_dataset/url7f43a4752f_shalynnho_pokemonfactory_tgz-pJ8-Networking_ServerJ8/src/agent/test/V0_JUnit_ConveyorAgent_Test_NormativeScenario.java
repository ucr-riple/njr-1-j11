package agent.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import agent.ConveyorAgent;
import agent.data.Kit;
import agent.test.mock.MockAgent;
import agent.test.mock.MockConveyorGraphics;
import agent.test.mock.MockKitRobot;

/**
 * This tests the normative scenario for the conveyor in the kitting cell. The
 * UUT is the ConveyorAgent. This tests the creation of a single kit.
 * @author Daniel Paje
 */
public class V0_JUnit_ConveyorAgent_Test_NormativeScenario extends TestCase {
	protected ConveyorAgent conveyor;
	protected Date date;

	private final URL URL = V0_JUnit_ConveyorAgent_Test_NormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");

	@Override
	protected void setUp() {
		conveyor = new ConveyorAgent("conveyor1");
		date = new Date();
	}

	@Override
	protected void tearDown() {
		conveyor = null;
		date = null;
	}

	/**
	 * This tests the conveyor's role in kit creation. The only other agent
	 * involved is KitRobot.
	 * @throws InterruptedException
	 */
	@Test
	public void testNormativeScenario() throws InterruptedException {
		MockKitRobot kitrobot = new MockKitRobot("kitrobot1");
		MockConveyorGraphics conveyorGraphics = new MockConveyorGraphics(
				"conveyorgraphics1");

		conveyor.setKitRobot(kitrobot);
		conveyor.setGraphicalRepresentation(conveyorGraphics);

		Kit kit = new Kit();

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock Conveyor Graphics should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ conveyorGraphics.log.toString(), 0,
				conveyorGraphics.log.size());

		assertEquals(
				"Mock Kit Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ kitrobot.log.toString(), 0, kitrobot.log.size());

		// Start the test
		conveyor.msgNeedKit();

		// Invoke the scheduler
		conveyor.pickAndExecuteAnAction();

		// Scheduler should have fired prepareKit()
		assertEquals("Conveyor should have added 1 MyKit object", 1, conveyor
				.getKitsOnConveyor().size());

		/*
		 * If the scheduler fires now, the code blocks if the previous animation
		 * isn't complete, so simulate completing the BringEmptyKit animation
		 * and releasing the animation permit. In this test case, we do this
		 * before invoking the scheduler as this test runs in a single thread
		 * (and will get stuck if Conveyor attempts to acquire a permit) whereas
		 * in the factory, the Conveyor will be running in another thread.
		 */
		conveyor.msgBringEmptyKitDone();

		assertEquals("ConveyorGraphics should have 1 event(s) in its log", 1,
				conveyorGraphics.log.size());

		assertTrue(
				"ConveyorGraphics should have received a request to animate moving the kit into the cell. Last logged event(s): "
						+ conveyorGraphics.log.getLastLoggedEvent()
								.getMessage(),
				conveyorGraphics.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgBringEmptyKit"));

		// Now it's safe to invoke the scheduler
		conveyor.pickAndExecuteAnAction();

		// Scheduler should have fired sendKit()
		assertEquals("Conveyor should have no kits left to deliver", 0,
				conveyor.getNumKitsToDeliver());

		assertEquals(
				"Conveyor should have 0 MyKit objects on its list of objects on the conveyor",
				0, conveyor.getKitsOnConveyor().size());

		// Release the animation semaphore permit
		conveyor.msgGiveKitToKitRobotDone();

		/*
		 * At this point the conveyor sleeps until the kit is completed and the
		 * kitrobot sends a message asking conveyor to take the kit out of the
		 * cell.
		 */
		conveyor.msgTakeKitAway(kit);

		assertEquals("Conveyor should have added 1 MyKit object", 1, conveyor
				.getKitsOnConveyor().size());

		conveyor.pickAndExecuteAnAction();

		// Scheduler should have fired deliverKit()
		assertEquals("ConveyorGraphics should have 2 event(s) in its log", 3,
				conveyorGraphics.log.size());

		assertTrue(
				"ConveyorGraphics should have received a request to animate moving the kit out of the cell. Last logged event(s): "
						+ conveyorGraphics.log.getLastLoggedEvent()
								.getMessage(),
				conveyorGraphics.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgReceiveKit"));

		conveyor.msgReceiveKitDone();

		// At this point the conveyor is done.
		assertEquals(
				"Conveyor should have added 0 MyKit objects left on the conveyor",
				0, conveyor.getKitsOnConveyor().size());

		// Write logs to file. Don't do this for now.
		// List<MockAgent> MockAgents = new ArrayList<MockAgent>();
		// MockAgents.add(conveyorGraphics);
		// MockAgents.add(kitrobot);
		// MockAgents.add(stand);
		// MockAgents.add(kitrobotGraphics);
		// generateLogFile("ConveyorAgent_Test_Normative",
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

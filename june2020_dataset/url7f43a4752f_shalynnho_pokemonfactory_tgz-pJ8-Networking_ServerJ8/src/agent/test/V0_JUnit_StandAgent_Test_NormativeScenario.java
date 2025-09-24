package agent.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import agent.StandAgent;
import agent.data.Kit;
import agent.test.mock.MockAgent;
import agent.test.mock.MockFCS;
import agent.test.mock.MockKitRobot;
import agent.test.mock.MockPartsRobot;

/**
 * This tests the normative scenario for the stand in the kitting cell. The UUT
 * is the StandAgent. This tests the creation of a single kit. Since the stand
 * is mainly responsible for manipulating lists for the kits, results of data
 * manipulation instead of messages are asserted. No longer useful in V1.
 * @author Daniel Paje
 */

@Deprecated
public class V0_JUnit_StandAgent_Test_NormativeScenario extends TestCase {
	protected StandAgent stand;
	protected Date date;

	private final URL URL = V0_JUnit_StandAgent_Test_NormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");

	@Override
	protected void setUp() {
		stand = new StandAgent("stand1");
		date = new Date();
	}

	@Override
	protected void tearDown() {
		stand = null;
		date = null;
	}

	/**
	 * This tests the stand's role in kit creation. The other agents involved in
	 * this scenario are PartsRobot, KitRobot and FCS.
	 */
	@Test
	public void testNormativeScenario() {
		MockPartsRobot partsrobot = new MockPartsRobot("partsrobot1");
		MockKitRobot kitrobot = new MockKitRobot("kitrobot1");
		MockFCS fcs = new MockFCS("fcs1");

		stand.setFCS(fcs);
		stand.setKitRobot(kitrobot);
		stand.setPartsRobot(partsrobot);

		Kit kit = new Kit();

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock FCS should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ fcs.log.toString(), 0, fcs.log.size());

		assertEquals(
				"Mock Kit Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ kitrobot.log.toString(), 0, fcs.log.size());

		assertEquals(
				"Mock Parts Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ partsrobot.log.toString(), 0, fcs.log.size());

		// Start the test
		stand.msgMakeKits(1);

		assertEquals("Stand should have updated the number of kits to make",
				stand.getNumKitsToMake(), 1);

		// Invoke the scheduler
		stand.pickAndExecuteAnAction();

		// Scheduler should have fired requestKit()
		assertTrue("Stand should have set position 1 on the stand", stand
				.getMyKits().get(1) != null);

		// Simulate kit reception. The kitrobot messages the stand
		stand.msgHereIsKit(kit, 1);

		assertEquals("Stand should have 1 MyKit object", stand.getMyKits()
				.size(), 1);

		stand.pickAndExecuteAnAction();

		// Simulate kit assembly completion. The partsrobot messages the stand
		stand.msgKitAssembled(kit);

		stand.pickAndExecuteAnAction();

		// Scheduler should have fired requestInspection()
		assertTrue(
				"KitRobot should have received a request to move the kit to the inspection area. Last logged event(s): "
						+ kitrobot.log.getLastLoggedEvent().getMessage(),
				kitrobot.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgMoveKitToInspectionArea"));

		// At this point the stand is not involved again until the kit robot has
		// shipped the kit.
		stand.msgShippedKit();

		stand.pickAndExecuteAnAction();

		assertTrue(
				"Stand should have set the reference to the kit in the inspection area to null",
				stand.getMyKits().get(0) == null);

		assertEquals(
				"Stand should have removed the MyKit object from its list of MyKits which should now have size 0",
				1, stand.getMyKits().size());

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

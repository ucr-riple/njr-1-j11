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

import factory.KitConfig;
import factory.Order;
import factory.PartType;

import agent.CameraAgent;
import agent.ConveyorAgent;
import agent.FCSAgent;
import agent.FeederAgent;
import agent.GantryAgent;
import agent.KitRobotAgent;
import agent.LaneAgent;
import agent.NestAgent;
import agent.PartsRobotAgent;
import agent.StandAgent;
import agent.test.mock.MockAgent;
import agent.test.mock.MockConveyor;
import agent.test.mock.MockGantry;
import agent.test.mock.MockGraphics;
import agent.test.mock.MockNest;
import agent.test.mock.MockPartsRobot;
import agent.test.mock.MockStand;

/**
 * This tests the FCSAgent, which acts as the go-between for the frontend and
 * backend.
 * @author Michael Gendotti
 */
public class V1_JUnit_FCS_Test_Normative extends TestCase {
	protected FCSAgent fcs;
	protected MockStand stand;
	protected MockPartsRobot partsRobot;
	protected MockGantry gantry;
	protected MockConveyor conveyor;
	protected ArrayList<MockNest> nests;
	protected Date date;

	private final URL URL = V0_JUnit_ConveyorAgent_Test_NormativeScenario.class
			.getResource(".");
	private final String FILEPATH = URL.toString().replace("file:", "");

	@Override
	protected void setUp() {
		fcs = new FCSAgent("FCS Agent");
		stand = new MockStand("Mock Stand");
		partsRobot = new MockPartsRobot("Mock PartsRobot");
		gantry = new MockGantry("Mock Gantry");
		conveyor = new MockConveyor("Mock Conveyor");
		nests = new ArrayList<MockNest>();
		for (int i = 0; i < 8; i++) {
			nests.add(new MockNest("Mock Nest " + i));
		}
		date = new Date();
	}

	@Override
	protected void tearDown() {
		fcs = null;
		stand = null;
		partsRobot = null;
		gantry = null;
		conveyor = null;
		nests = null;
		date = null;
	}

	/**
	 * This tests the FCS's role in kit creation. The test involves the gantry,
	 * stand, conveyor, nests, and parts robot as well.
	 * @throws InterruptedException
	 */
	@Test
	public void testNormativeScenario() throws InterruptedException {

		fcs.setConveyor(conveyor);
		fcs.setGantry(gantry);
		fcs.setPartsRobot(partsRobot);
		fcs.setStand(stand);
		for (int i = 0; i < 8; i++) {
			fcs.setNest(nests.get(i));
		}

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock Conveyor should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 0, conveyor.log.size());

		assertEquals(
				"Mock Gantry should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 0, gantry.log.size());

		assertEquals(
				"Mock Parts Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the part robot's event(s) log reads: "
						+ partsRobot.log.toString(), 0, partsRobot.log.size());

		assertEquals(
				"Mock Stand should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 0, stand.log.size());
		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have an empty event(s) log before the stand's scheduler is called. "
							+ "Instead, the nest's event(s) log reads: "
							+ nests.get(i).log.toString(), 0,
					nests.get(i).log.size());
		}

		// Start the test
		KitConfig partTypes = new KitConfig("KitConfig");
		partTypes.addItem(new PartType("A"),1);// 1
		partTypes.addItem(new PartType("B"),1);// 2
		partTypes.addItem(new PartType("C"),1);// 3
		partTypes.addItem(new PartType("D"),1);// 4
		partTypes.addItem(new PartType("E"),1);// 5
		partTypes.addItem(new PartType("F"),1);// 6
		partTypes.addItem(new PartType("G"),1);// 7
		partTypes.addItem(new PartType("H"),1);// 8
		fcs.msgStartProduction(); // This allows the fcs to run
		fcs.msgAddKitsToQueue(new Order(partTypes, 1));

		// FCS should have 1 order now
		assertEquals("FCS should have added 1 Order", 1, fcs.getOrders().size());

		// Invoke the scheduler
		fcs.pickAndExecuteAnAction();
		fcs.pickAndExecuteAnAction();

		// Scheduler should have fired placeOrder()
		assertEquals(
				"Mock Conveyor should have recieved a single message. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 1, conveyor.log.size());

		assertTrue(
				"Mock Conveyor should have received a message about the kit configuration. Last logged event(s): "
						+ conveyor.log.getLastLoggedEvent().getMessage(),
				conveyor.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Gantry should have recieved 8 bin config message. "
						+ "Instead, the stand's event(s) log reads: "
						+ gantry.log.toString(), 8, gantry.log.size());

		assertTrue(
				"Mock Gantry should have received a message about the bin configuration. Last logged event(s): "
						+ gantry.log.getLastLoggedEvent().getMessage(),
				gantry.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsBinConfig"));

		assertEquals(
				"Mock Parts Robot should have recieved a single message. "
						+ "Instead, the stand's event(s) log reads: "
						+ partsRobot.log.toString(), 1, partsRobot.log.size());

		assertTrue(
				"Mock Parts Robot should have received a message about the kit configuration. Last logged event(s): "
						+ partsRobot.log.getLastLoggedEvent().getMessage(),
				partsRobot.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Stand should have recieved a single message. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 1, stand.log.size());

		assertTrue(
				"Mock Stand should have received a message about the number of kits to make. Last logged event(s): "
						+ stand.log.getLastLoggedEvent().getMessage(),
				stand.log
						.getLastLoggedEvent()
						.getMessage()
						.equals("Received message msgMakeKits with 1 kits ordered"));

		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have recieved a single message. "
							+ "Instead, the nest's event(s) log reads: "
							+ nests.get(i).log.toString(), 1,
					nests.get(i).log.size());

			assertTrue(
					"Mock Nest should have received a message about its part type. Last logged event(s): "
							+ nests.get(i).log.getLastLoggedEvent()
									.getMessage(),
					nests.get(i).log.getLastLoggedEvent().getMessage()
							.equals("Received message msgHereIsPartType"));
		}

		// The FCS will receive this message when the kits are done being made
		// and remove the order from its order list
		fcs.msgOrderFinished();

		assertEquals("FCS should have no Orders", 0, fcs.getOrders().size());

	}

	/**
	 * This tests the FCS's ability to cancel an order. The test involves the
	 * gantry, stand, conveyor, nests, and parts robot as well.
	 * @throws InterruptedException
	 */
	@Test
	public void testCancelingKitScenario() throws InterruptedException {

		fcs.setConveyor(conveyor);
		fcs.setGantry(gantry);
		fcs.setPartsRobot(partsRobot);
		fcs.setStand(stand);
		for (int i = 0; i < 8; i++) {
			fcs.setNest(nests.get(i));
		}

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock Conveyor should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 0, conveyor.log.size());

		assertEquals(
				"Mock Gantry should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 0, gantry.log.size());

		assertEquals(
				"Mock Parts Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the part robot's event(s) log reads: "
						+ partsRobot.log.toString(), 0, partsRobot.log.size());

		assertEquals(
				"Mock Stand should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 0, stand.log.size());
		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have an empty event(s) log before the stand's scheduler is called. "
							+ "Instead, the nest's event(s) log reads: "
							+ nests.get(i).log.toString(), 0,
					nests.get(i).log.size());
		}

		// Start the test
		KitConfig partTypes = new KitConfig("KitConfig");
		partTypes.addItem(new PartType("A"),1);// 1
		partTypes.addItem(new PartType("B"),1);// 2
		partTypes.addItem(new PartType("C"),1);// 3
		partTypes.addItem(new PartType("D"),1);// 4
		partTypes.addItem(new PartType("E"),1);// 5
		partTypes.addItem(new PartType("F"),1);// 6
		partTypes.addItem(new PartType("G"),1);// 7
		partTypes.addItem(new PartType("H"),1);// 8
		KitConfig partTypes2 = new KitConfig("KitConfig");
		partTypes2.addItem(new PartType("A"),1);// 1
		partTypes2.addItem(new PartType("A"),1);// 2
		partTypes2.addItem(new PartType("A"),1);// 3
		partTypes2.addItem(new PartType("A"),1);// 4
		partTypes2.addItem(new PartType("A"),1);// 5
		partTypes2.addItem(new PartType("A"),1);// 6
		partTypes2.addItem(new PartType("A"),1);// 7
		partTypes2.addItem(new PartType("A"),1);// 8
		fcs.msgStartProduction(); // This allows the fcs to run
		Order temp = new Order(partTypes, 1);
		fcs.msgAddKitsToQueue(temp);
		fcs.msgAddKitsToQueue(new Order(partTypes2, 1));

		// FCS should have 2 orders now
		assertEquals("FCS should have added 2 Orders", 2, fcs.getOrders()
				.size());

		// tells the FCS to stop making the first kit ordered
		fcs.msgStopMakingKit(temp);

		// Invoke the scheduler
		fcs.pickAndExecuteAnAction();
		fcs.pickAndExecuteAnAction();

		// FCS should have 1 order now of the second part type list passed in
		assertEquals("FCS should have added 1 Order", 1, fcs.getOrders().size());

		assertEquals("FCS should have added 8 part types in its order", 8, fcs
				.getOrders().get(0).kitConfig.getConfig().keySet().size());

		// Order the second order
		fcs.pickAndExecuteAnAction();

		// Scheduler should have fired placeOrder()
		assertEquals(
				"Mock Conveyor should have recieved a single message. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 1, conveyor.log.size());

		assertTrue(
				"Mock Conveyor should have received a message about the kit configuration. Last logged event(s): "
						+ conveyor.log.getLastLoggedEvent().getMessage(),
				conveyor.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Gantry should have recieved 8 bin config message. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 8, gantry.log.size());

		assertTrue(
				"Mock Gantry should have received a message about the bin configuration. Last logged event(s): "
						+ gantry.log.getLastLoggedEvent().getMessage(),
				gantry.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsBinConfig"));

		assertEquals("Mock Parts Robot should have recieved a single message. "
				+ "Instead, the part robot's event(s) log reads: "
				+ partsRobot.log.toString(), 1, partsRobot.log.size());

		assertTrue(
				"Mock Parts Robot should have received a message about the kit configuration. Last logged event(s): "
						+ partsRobot.log.getLastLoggedEvent().getMessage(),
				partsRobot.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Stand should have recieved a single message. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 1, stand.log.size());

		assertTrue(
				"Mock Stand should have received a message about the number of kits to make. Last logged event(s): "
						+ stand.log.getLastLoggedEvent().getMessage(),
				stand.log
						.getLastLoggedEvent()
						.getMessage()
						.equals("Received message msgMakeKits with 1 kits ordered"));

		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have recieved a single message. "
							+ "Instead, the stand's event(s) log reads: "
							+ nests.get(i).log.toString(), 1,
					nests.get(i).log.size());

			assertTrue(
					"Mock Nest should have received a message about its part type. Last logged event(s): "
							+ nests.get(i).log.getLastLoggedEvent()
									.getMessage(),
					nests.get(i).log.getLastLoggedEvent().getMessage()
							.equals("Received message msgHereIsPartType"));
		}

		// The FCS will receive this message when the kits are done being made
		// and remove the order from its order list
		fcs.msgOrderFinished();

		assertEquals("FCS should have no Orders", 0, fcs.getOrders().size());

	}

	/**
	 * This tests the FCS's ability to cancel a kit. The test involves the
	 * gantry, stand, conveyor, nests, and parts robot as well.
	 * @throws InterruptedException
	 */
	@Test
	public void testMultipleKitScenario() throws InterruptedException {

		fcs.setConveyor(conveyor);
		fcs.setGantry(gantry);
		fcs.setPartsRobot(partsRobot);
		fcs.setStand(stand);
		for (int i = 0; i < 8; i++) {
			fcs.setNest(nests.get(i));
		}

		// Before we start, make sure the mocks have empty logs.

		assertEquals(
				"Mock Conveyor should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 0, conveyor.log.size());

		assertEquals(
				"Mock Gantry should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 0, gantry.log.size());

		assertEquals(
				"Mock Parts Robot should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the part robot's event(s) log reads: "
						+ partsRobot.log.toString(), 0, partsRobot.log.size());

		assertEquals(
				"Mock Stand should have an empty event(s) log before the stand's scheduler is called. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 0, stand.log.size());
		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have an empty event(s) log before the stand's scheduler is called. "
							+ "Instead, the nest's event(s) log reads: "
							+ nests.get(i).log.toString(), 0,
					nests.get(i).log.size());
		}

		// Start the test
		KitConfig partTypes = new KitConfig("KitConfig");
		partTypes.addItem(new PartType("A"),1);// 1
		partTypes.addItem(new PartType("B"),1);// 2
		partTypes.addItem(new PartType("C"),1);// 3
		partTypes.addItem(new PartType("D"),1);// 4
		partTypes.addItem(new PartType("E"),1);// 5
		partTypes.addItem(new PartType("F"),1);// 6
		partTypes.addItem(new PartType("G"),1);// 7
		partTypes.addItem(new PartType("H"),1);// 8
		KitConfig partTypes2 = new KitConfig("KitConfig");
		partTypes2.addItem(new PartType("A"),1);// 1
		partTypes2.addItem(new PartType("A"),1);// 2
		partTypes2.addItem(new PartType("A"),1);// 3
		partTypes2.addItem(new PartType("A"),1);// 4
		partTypes2.addItem(new PartType("A"),1);// 5
		partTypes2.addItem(new PartType("A"),1);// 6
		partTypes2.addItem(new PartType("A"),1);// 7
		partTypes2.addItem(new PartType("A"),1);// 8
		fcs.msgStartProduction(); // This allows the fcs to run
		fcs.msgAddKitsToQueue(new Order(partTypes, 1));
		fcs.msgAddKitsToQueue(new Order(partTypes2, 1));

		// FCS should have 2 orders now
		assertEquals("FCS should have added 2 Orders", 2, fcs.getOrders()
				.size());

		// Invoke the scheduler
		fcs.pickAndExecuteAnAction();
		fcs.pickAndExecuteAnAction();

		// Scheduler should have fired placeOrder()
		assertEquals(
				"Mock Conveyor should have recieved a single message. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 1, conveyor.log.size());

		assertTrue(
				"Mock Conveyor should have received a message about the kit configuration. Last logged event(s): "
						+ conveyor.log.getLastLoggedEvent().getMessage(),
				conveyor.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Gantry should have recieved 8 bin config message. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 8, gantry.log.size());

		assertTrue(
				"Mock Gantry should have received a message about the bin configuration. Last logged event(s): "
						+ gantry.log.getLastLoggedEvent().getMessage(),
				gantry.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsBinConfig"));

		assertEquals("Mock Parts Robot should have recieved a single message. "
				+ "Instead, the part robot's event(s) log reads: "
				+ partsRobot.log.toString(), 1, partsRobot.log.size());

		assertTrue(
				"Mock Parts Robot should have received a message about the kit configuration. Last logged event(s): "
						+ partsRobot.log.getLastLoggedEvent().getMessage(),
				partsRobot.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Stand should have recieved a single message. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 1, stand.log.size());

		assertTrue(
				"Mock Stand should have received a message about the number of kits to make. Last logged event(s): "
						+ stand.log.getLastLoggedEvent().getMessage(),
				stand.log
						.getLastLoggedEvent()
						.getMessage()
						.equals("Received message msgMakeKits with 1 kits ordered"));

		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have recieved a single message. "
							+ "Instead, the stand's event(s) log reads: "
							+ nests.get(i).log.toString(), 1,
					nests.get(i).log.size());

			assertTrue(
					"Mock Nest should have received a message about its part type. Last logged event(s): "
							+ nests.get(i).log.getLastLoggedEvent()
									.getMessage(),
					nests.get(i).log.getLastLoggedEvent().getMessage()
							.equals("Received message msgHereIsPartType"));
		}

		// The FCS will receive this message when the kits are done being made
		// and remove the order from its order list
		fcs.msgOrderFinished();

		// FCS should have 1 orders now
		assertEquals("FCS should have 1 Order", 1, fcs.getOrders().size());

		// Invoke the scheduler
		fcs.pickAndExecuteAnAction();

		// Scheduler should have fired placeOrder()
		assertEquals(
				"Mock Conveyor should have recieved two messages. "
						+ "Instead, the conveyor's event(s) log reads: "
						+ conveyor.log.toString(), 2, conveyor.log.size());

		assertTrue(
				"Mock Conveyor should have received a message about the kit configuration. Last logged event(s): "
						+ conveyor.log.getLastLoggedEvent().getMessage(),
				conveyor.log.getLastLoggedEvent().getMessage()
						.equals("Received message msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Gantry should have recieved 8 bin config message. "
						+ "Instead, the gantry's event(s) log reads: "
						+ gantry.log.toString(), 8, gantry.log.size());

		assertTrue(
				"Mock Gantry should have received a message about the bin configuration. Last logged event(s): "
						+ gantry.log.getLastLoggedEvent().getMessage(),
				gantry.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsBinConfig"));

		assertEquals("Mock Parts Robot should have recieved two messages. "
				+ "Instead, the part robot's event(s) log reads: "
				+ partsRobot.log.toString(), 2, partsRobot.log.size());

		assertTrue(
				"Mock Parts Robot should have received a message about the kit configuration. Last logged event(s): "
						+ partsRobot.log.getLastLoggedEvent().getMessage(),
				partsRobot.log.getLastLoggedEvent().getMessage()
						.equals("Recieved msgHereIsKitConfiguration"));

		assertEquals(
				"Mock Stand should have recieved two messages. "
						+ "Instead, the stand's event(s) log reads: "
						+ stand.log.toString(), 2, stand.log.size());

		assertTrue(
				"Mock Stand should have received a message about the number of kits to make. Last logged event(s): "
						+ stand.log.getLastLoggedEvent().getMessage(),
				stand.log
						.getLastLoggedEvent()
						.getMessage()
						.equals("Received message msgMakeKits with 1 kits ordered"));

		for (int i = 0; i < 8; i++) {
			assertEquals(
					"Mock Nest should have recieved two messages. "
							+ "Instead, the stand's event(s) log reads: "
							+ nests.get(i).log.toString(), 2,
					nests.get(i).log.size());

			assertTrue(
					"Mock Nest should have received a message about its part type. Last logged event(s): "
							+ nests.get(i).log.getLastLoggedEvent()
									.getMessage(),
					nests.get(i).log.getLastLoggedEvent().getMessage()
							.equals("Received message msgHereIsPartType"));
		}

		fcs.msgOrderFinished();

		assertEquals("FCS should have no Orders", 0, fcs.getOrders().size());

	}

	@Test
	public void testagentsWithoutGraphics() throws InterruptedException {
		GantryAgent gantry= new GantryAgent("Gantry Agent");
		ArrayList<FeederAgent> feeders = new ArrayList<FeederAgent>();
		for(int i=0;i<4;i++){
			feeders.add(new FeederAgent("Feeder Agent "+i));
		}
		ArrayList<LaneAgent> lanes = new ArrayList<LaneAgent>();
		for(int i=0;i<8;i++){
			lanes.add(new LaneAgent("Lane Agent "+i));
		}
		ArrayList<NestAgent> nests = new ArrayList<NestAgent>();
		for(int i=0;i<8;i++){
			nests.add(new NestAgent("Nest Agent "+i));
		}
		PartsRobotAgent partsRobot= new PartsRobotAgent("Parts Robot Agent");
		CameraAgent camera = new CameraAgent("Camera Agent");
		StandAgent stand = new StandAgent("Stand Agent");
		KitRobotAgent kitRobot = new KitRobotAgent("Kit Robot Agent");
		ConveyorAgent conveyor = new ConveyorAgent("Conveyor Agent");
		FCSAgent fcs = new FCSAgent("FCS Agent");
		
		for(int i=0;i<8;i++){
			feeders.get(i/2).setGantry(gantry);
			feeders.get(i/2).setLane(lanes.get(i));
			lanes.get(i).setFeeder(feeders.get(i/2));
			lanes.get(i).setNest(nests.get(i));
			nests.get(i).setLane(lanes.get(i));
			camera.setNest(nests.get(i));
			fcs.setNest(nests.get(i));
		}
		camera.setKitRobot(kitRobot);
		stand.setFCS(fcs);
		stand.setKitrobot(kitRobot);
		stand.setPartsRobot(partsRobot);
		kitRobot.setCamera(camera);
		kitRobot.setStand(stand);
		conveyor.setFCS(fcs);
		conveyor.setKitrobot(kitRobot);
		fcs.setConveyor(conveyor);
		fcs.setGantry(gantry);
		fcs.setPartsRobot(partsRobot);
		fcs.setStand(stand);
		
		MockGraphics mg= new MockGraphics("Mock Graphics");
		
		gantry.setGraphicalRepresentation(mg);
		for(int i=0;i<4;i++){
			feeders.get(i).setGraphicalRepresentation(mg);
		}
		for(int i=0;i<8;i++){
			lanes.get(i).setGraphicalRepresentation(mg);
		}
		for(int i=0;i<8;i++){
			nests.get(i).setGraphicalRepresentation(mg);
		}
		partsRobot.setGraphicalRepresentation(mg);
		camera.setGraphicalRepresentation(mg);
		stand.setGraphicalRepresentation(mg);
		kitRobot.setGraphicalRepresentation(mg);
		conveyor.setGraphicalRepresentation(mg);
		fcs.setGraphicalRepresentation(mg);
		
		KitConfig kg = new KitConfig("Kit config");
		kg.addItem(new PartType("A"), 1);
		kg.addItem(new PartType("B"), 1);
		kg.addItem(new PartType("C"), 1);
		kg.addItem(new PartType("D"), 1);
		kg.addItem(new PartType("E"), 1);
		kg.addItem(new PartType("F"), 1);
		kg.addItem(new PartType("G"), 1);
		kg.addItem(new PartType("H"), 1);
		
		gantry.startThread();
		for(int i=0;i<4;i++){
			feeders.get(i).startThread();
		}
		for(int i=0;i<8;i++){
			lanes.get(i).startThread();
		}
		for(int i=0;i<8;i++){
			nests.get(i).startThread();
		}
		mg.startThread();
		partsRobot.startThread();
		camera.startThread();
		stand.startThread();
		kitRobot.startThread();
		conveyor.startThread();
		fcs.startThread();
		
		fcs.msgAddKitsToQueue(new Order(kg,1));
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


package Networking;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import DeviceGraphics.CameraGraphics;
import DeviceGraphics.ConveyorGraphics;
import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.FeederGraphics;
import DeviceGraphics.GantryGraphics;
import DeviceGraphics.InspectionStandGraphics;
import DeviceGraphics.KitRobotGraphics;
import DeviceGraphics.LaneGraphics;
import DeviceGraphics.NestGraphics;
import DeviceGraphics.PartsRobotGraphics;
import DeviceGraphics.StandGraphics;
import Utils.ConsoleWriter;
import Utils.Constants;
import agent.Agent;
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
import factory.FCS;

/**
 * The Server is the "middleman" between Agents and the GUI clients. This is
 * where constructors of the different Agents will be called, as well as
 * establishing connections with the GUI clients.
 * @author Peter Zhang
 */
public class Server {
	private ServerSocket ss;
	private Socket s;

	// GUI client reader/writers
	private ClientReader factProdMngrReader;
	private AbstractWriter factProdMngrWriter = new DummyWriter();

	private ClientReader partsMngrReader;
	private AbstractWriter partsMngrWriter = new DummyWriter();

	private ClientReader kitMngrReader;
	private AbstractWriter kitMngrWriter = new DummyWriter();

	// Graphics only client reader/writers
	private ClientReader gantryRobotMngrReader;
	private AbstractWriter gantryRobotMngrWriter = new DummyWriter();

	private ClientReader kitAssemblyMngrReader;
	private AbstractWriter kitAssemblyMngrWriter = new DummyWriter();

	private ClientReader laneMngrReader;
	private AbstractWriter laneMngrWriter = new DummyWriter();

	// V0 Config (testing only - remove later)
	private ClientReader kitRobotMngrReader;
	private AbstractWriter kitRobotMngrWriter = new DummyWriter();

	private ClientReader partsRobotMngrReader;
	private AbstractWriter partsRobotMngrWriter = new DummyWriter();

	// See how many clients have connected
	private int numClients = 0;

	public volatile LinkedHashMap<String, DeviceGraphics> devices = new LinkedHashMap<String, DeviceGraphics>();
	public volatile LinkedHashMap<String, Agent> agents = new LinkedHashMap<String, Agent>();

	FCS fcs;
	FCSAgent fcsAgent;

	private final ConsoleWriter console;

	public Server() {
		// connecting to cloud debug panel
		console = new ConsoleWriter("Server");
		console.startConsole();
		console.startThread();

		fcsAgent = new FCSAgent(Constants.FCS_TARGET);
		fcs = new FCS(this, fcsAgent);

		initAgents();
		initDevices();
		connectAgentsWithEachOther();
		connectAgentsWithDevices();

		// add the save file method (at bottom) to the server
		addShutdownHook();

		initStreams();

		// will never run anything after init Streams
	}

	private void initStreams() {
		try {
			ss = new ServerSocket(Constants.SERVER_PORT);
		} catch (Exception e) {
			System.out.println("Server: cannot init server socket");
			e.printStackTrace();
			System.exit(0);
		}

		while (true) {
			try {
				s = ss.accept();
				identifyClient(s);
				System.out.println("Server: accepted client");
			} catch (Exception e) {
				System.out.println("Server: got an exception" + e.getMessage());
			}
		}
	}

	private void initAgents() {
		agents.put(Constants.GANTRY_ROBOT_TARGET, new GantryAgent(
				Constants.GANTRY_ROBOT_TARGET));
		agents.put(Constants.CAMERA_TARGET, new CameraAgent(
				Constants.CAMERA_TARGET));
		agents.put(Constants.CONVEYOR_TARGET, new ConveyorAgent(
				Constants.CONVEYOR_TARGET));
		agents.put(Constants.KIT_ROBOT_TARGET, new KitRobotAgent(
				Constants.KIT_ROBOT_TARGET));
		agents.put(Constants.PARTS_ROBOT_TARGET, new PartsRobotAgent(
				Constants.PARTS_ROBOT_TARGET));
		agents.put(Constants.STAND_TARGET, new StandAgent(
				Constants.STAND_TARGET));
		agents.put(Constants.FCS_TARGET, fcsAgent);

		for (int i = 0; i < Constants.FEEDER_COUNT; i++) {
			agents.put(Constants.FEEDER_TARGET + i, new FeederAgent(
					Constants.FEEDER_TARGET + i));
		}
		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			agents.put(Constants.LANE_TARGET + i, new LaneAgent(
					Constants.LANE_TARGET + i));
		}
		for (int i = 0; i < Constants.NEST_COUNT; i++) {
			agents.put(Constants.NEST_TARGET + i, new NestAgent(
					Constants.NEST_TARGET + i));
		}
	}

	private void initDevices() {
		devices.put(Constants.GANTRY_ROBOT_TARGET, new GantryGraphics(this,
				agents.get(Constants.GANTRY_ROBOT_TARGET)));
		devices.put(Constants.CAMERA_TARGET,
				new CameraGraphics(this, agents.get(Constants.CAMERA_TARGET)));
		devices.put(Constants.CONVEYOR_TARGET, new ConveyorGraphics(this,
				agents.get(Constants.CONVEYOR_TARGET)));
		devices.put(
				Constants.KIT_ROBOT_TARGET,
				new KitRobotGraphics(this, agents
						.get(Constants.KIT_ROBOT_TARGET), agents
						.get(Constants.STAND_TARGET)));
		devices.put(Constants.PARTS_ROBOT_TARGET, new PartsRobotGraphics(this,
				agents.get(Constants.PARTS_ROBOT_TARGET)));
		devices.put(Constants.STAND_TARGET + 0, new InspectionStandGraphics(
				this, agents.get(Constants.STAND_TARGET + 0)));

		for (int i = 0; i < Constants.FEEDER_COUNT; i++) {
			devices.put(Constants.FEEDER_TARGET + i, new FeederGraphics(i,
					this, agents.get(Constants.FEEDER_TARGET + i)));
		}
		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			devices.put(Constants.LANE_TARGET + i, new LaneGraphics(this, i,
					agents.get(Constants.LANE_TARGET + i)));
		}
		for (int i = 0; i < Constants.NEST_COUNT; i++) {
			devices.put(Constants.NEST_TARGET + i, new NestGraphics(this, i,
					agents.get(Constants.NEST_TARGET + i)));
		}
		for (int i = 1; i < Constants.STAND_COUNT; i++) {
			devices.put(Constants.STAND_TARGET + i, new StandGraphics(this,
					agents.get(Constants.STAND_TARGET + i), i));
		}
	}

	private void connectAgentsWithEachOther() {
		for (Agent a : agents.values()) {
			a.setConsoleWriter(console);
		}
		for (int i = 0; i < Constants.FEEDER_COUNT; i++) {
			((FeederAgent) agents.get(Constants.FEEDER_TARGET + i))
					.setLanes(
							(LaneAgent) agents.get(Constants.LANE_TARGET + i
									* 2),
							(LaneAgent) agents.get(Constants.LANE_TARGET
									+ (i * 2 + 1)));
			((FeederAgent) agents.get(Constants.FEEDER_TARGET + i))
					.setGantry((GantryAgent) agents
							.get(Constants.GANTRY_ROBOT_TARGET));
			((GantryAgent) agents.get(Constants.GANTRY_ROBOT_TARGET))
					.setFeeder((FeederAgent) agents.get(Constants.FEEDER_TARGET
							+ i));
		}

		for (int i = 0; i < Constants.LANE_COUNT; i++) {
			((LaneAgent) agents.get(Constants.LANE_TARGET + i))
					.setFeeder((FeederAgent) agents.get(Constants.FEEDER_TARGET
							+ i / 2));
			((LaneAgent) agents.get(Constants.LANE_TARGET + i))
					.setNest((NestAgent) agents.get(Constants.NEST_TARGET + i));
			((NestAgent) agents.get(Constants.NEST_TARGET + i))
					.setLane((LaneAgent) agents.get(Constants.LANE_TARGET + i));
			((NestAgent) agents.get(Constants.NEST_TARGET + i))
					.setCamera((CameraAgent) agents
							.get(Constants.CAMERA_TARGET));
			((CameraAgent) agents.get(Constants.CAMERA_TARGET))
					.setNest((NestAgent) agents.get(Constants.NEST_TARGET + i));
			((FCSAgent) agents.get(Constants.FCS_TARGET))
					.setNest((NestAgent) agents.get(Constants.NEST_TARGET + i));
		}

		KitRobotAgent kitrobot = (KitRobotAgent) agents
				.get(Constants.KIT_ROBOT_TARGET);
		PartsRobotAgent partsrobot = (PartsRobotAgent) agents
				.get(Constants.PARTS_ROBOT_TARGET);
		CameraAgent camera = (CameraAgent) agents.get(Constants.CAMERA_TARGET);
		ConveyorAgent conveyor = (ConveyorAgent) agents
				.get(Constants.CONVEYOR_TARGET);
		FCSAgent fcs = (FCSAgent) agents.get(Constants.FCS_TARGET);

		((StandAgent) agents.get(Constants.STAND_TARGET)).setKitrobot(kitrobot);
		((StandAgent) agents.get(Constants.STAND_TARGET)).setFCS(fcs);
		((StandAgent) agents.get(Constants.STAND_TARGET))
				.setPartsRobot(partsrobot);
		kitrobot.setStand((StandAgent) agents.get(Constants.STAND_TARGET));
		partsrobot.setStand((StandAgent) agents.get(Constants.STAND_TARGET));

		kitrobot.setCamera(camera);
		kitrobot.setConveyor(conveyor);
		conveyor.setKitrobot(kitrobot);
		conveyor.setFCS((FCSAgent) agents.get(Constants.FCS_TARGET));
		camera.setPartsRobot(partsrobot);
		camera.setKitRobot(kitrobot);

		((FCSAgent) agents.get(Constants.FCS_TARGET)).setConveyor(conveyor);
		((FCSAgent) agents.get(Constants.FCS_TARGET))
				.setGantry((GantryAgent) agents
						.get(Constants.GANTRY_ROBOT_TARGET));
		((FCSAgent) agents.get(Constants.FCS_TARGET))
				.setPartsRobot((PartsRobotAgent) agents
						.get(Constants.PARTS_ROBOT_TARGET));
		((FCSAgent) agents.get(Constants.FCS_TARGET))
				.setStand((StandAgent) agents.get(Constants.STAND_TARGET));
		((FCSAgent) agents.get(Constants.FCS_TARGET))
				.setCamera((CameraAgent) agents.get(Constants.CAMERA_TARGET));

	}

	private void connectAgentsWithDevices() {
		for (Entry<String, Agent> entry : agents.entrySet()) {
			Agent agent = entry.getValue();
			if (devices.containsKey(entry.getKey())) {
				DeviceGraphics device = devices.get(entry.getKey());
				agent.setGraphicalRepresentation(device);
			}
			agent.startThread();
		}
		fcsAgent.setFCS(fcs);
		fcsAgent.startThread();
	}

	/**
	 * Organize incoming streams according to the first message that we receive
	 */
	private void identifyClient(Socket s) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			// initial identity read
			Request req = (Request) ois.readObject();
			System.out.println("Server: Received client");

			if (req.getTarget().equals(Constants.SERVER_TARGET)
					&& req.getCommand().equals(Constants.IDENTIFY_COMMAND)) {
				String identity = (String) req.getData();
				System.out.println("Server: Received identity: " + identity);

				if (identity.equals(Constants.KIT_ROBOT_MNGR_CLIENT)) {
					kitRobotMngrWriter = new StreamWriter(oos);
					kitRobotMngrReader = new ClientReader(ois, this);
					new Thread(kitRobotMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.PARTS_ROBOT_MNGR_CLIENT)) {
					partsRobotMngrWriter = new StreamWriter(oos);
					partsRobotMngrReader = new ClientReader(ois, this);
					new Thread(partsRobotMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.LANE_MNGR_CLIENT)) {
					laneMngrWriter = new StreamWriter(oos);
					laneMngrReader = new ClientReader(ois, this);
					new Thread(laneMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.FACTORY_PROD_MNGR_CLIENT)) {
					factProdMngrWriter = new StreamWriter(oos);
					factProdMngrReader = new ClientReader(ois, this);
					new Thread(factProdMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.PARTS_MNGR_CLIENT)) {
					partsMngrWriter = new StreamWriter(oos);
					partsMngrReader = new ClientReader(ois, this);
					new Thread(partsMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.KIT_MNGR_CLIENT)) {
					kitMngrWriter = new StreamWriter(oos);
					kitMngrReader = new ClientReader(ois, this);
					new Thread(kitMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.KIT_ASSEMBLY_MNGR_CLIENT)) {
					kitAssemblyMngrWriter = new StreamWriter(oos);
					kitAssemblyMngrReader = new ClientReader(ois, this);
					new Thread(kitAssemblyMngrReader).start();
					numClients++;
				} else if (identity.equals(Constants.GANTRY_ROBOT_MNGR_CLIENT)) {
					gantryRobotMngrWriter = new StreamWriter(oos);
					gantryRobotMngrReader = new ClientReader(ois, this);
					new Thread(gantryRobotMngrReader).start();
					numClients++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayMessage(String s) {
		fcs.displayMessage(s);
	}

	public void receiveData(Request req) {
		String target = req.getTarget();

		if (target.equals(Constants.SERVER_TARGET)) {

		} else if (target.equals(Constants.FCS_TARGET)) {
			fcs.receiveData(req);
		} else {
			devices.get(target).receiveData(req);
		}
	}

	public void sendData(Request req) {
		String target = req.getTarget();
		System.out.println("Received: " + req);

		if (target.contains(Constants.CONVEYOR_TARGET)) {
			sendDataToConveyor(req);
		} else if (target.contains(Constants.KIT_ROBOT_TARGET)) {
			sendDataToKitRobot(req);
		} else if (target.contains(Constants.PARTS_ROBOT_TARGET)) {
			sendDataToPartsRobot(req);
		} else if (target.contains(Constants.NEST_TARGET)) {
			sendDataToNest(req);
		} else if (target.contains(Constants.CAMERA_TARGET)) {
			sendDataToCamera(req);
		} else if (target.contains(Constants.LANE_TARGET)) {
			sendDataToLane(req);
		} else if (target.contains(Constants.FEEDER_TARGET)) {
			sendDataToLane(req);
		} else if (target.contains(Constants.STAND_TARGET)) {
			sendDataToStand(req);
		} else if (target.contains(Constants.ALL_TARGET)) {
			sendDataToGUIManagers(req);
		} else if (target.contains(Constants.GANTRY_ROBOT_TARGET)) {
			sendDataToGantry(req);
		} else if (target.contains(Constants.MESSAGING_BOX_TARGET)) {
			sendDataToMessage(req);
		}
	}

	/**
	 * This method sends the request to all managers with a GUI.
	 * @param req
	 */
	private void sendDataToGUIManagers(Request req) {
		// TODO - Remove - Temp hack to run FPM without running other clients.
		if (factProdMngrWriter != null) {
			factProdMngrWriter.sendData(req);
		}

		if (kitMngrWriter != null) {
			kitMngrWriter.sendData(req);
		}

		if (partsMngrWriter != null) {
			partsMngrWriter.sendData(req);
		}
	}

	private void sendDataToConveyor(Request req) {
		factProdMngrWriter.sendData(req);
		kitRobotMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
	}

	private void sendDataToKitRobot(Request req) {
		factProdMngrWriter.sendData(req);
		kitRobotMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
	}

	private void sendDataToPartsRobot(Request req) {
		factProdMngrWriter.sendData(req);
		partsRobotMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
	}

	private void sendDataToStand(Request req) {
		factProdMngrWriter.sendData(req);
		kitRobotMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
	}
	private void sendDataToNest(Request req) {
		factProdMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
		laneMngrWriter.sendData(req);
	}

	private void sendDataToCamera(Request req) {
		factProdMngrWriter.sendData(req);
		partsRobotMngrWriter.sendData(req);
		kitAssemblyMngrWriter.sendData(req);
		laneMngrWriter.sendData(req);
	}

	private void sendDataToLane(Request req) {
		factProdMngrWriter.sendData(req);
		laneMngrWriter.sendData(req);
		gantryRobotMngrWriter.sendData(req);
	}

	private void sendDataToGantry(Request req) {
		factProdMngrWriter.sendData(req);
		laneMngrWriter.sendData(req);
		gantryRobotMngrWriter.sendData(req);
	}

	public void sendDataToMessage(Request req) {
		factProdMngrWriter.sendData(req);
	}

	private void addShutdownHook() {
		Thread hook = new Thread(new Runnable() {
			@Override
			public void run() {
				saveFCSData();
			}
		});
		Runtime.getRuntime().addShutdownHook(hook);
	}

	private void saveFCSData() {
		try {
			// save kit configs to KitConfigBackup.sav
			FileOutputStream saveKitConfigs = new FileOutputStream(
					"KitConfigBackup.sav");
			ObjectOutputStream outputKC = new ObjectOutputStream(saveKitConfigs);

			outputKC.writeObject(fcs.getKitConfigs());

			// save part types to PartTypesBackup.sav
			FileOutputStream savePartTypes = new FileOutputStream(
					"PartTypesBackup.sav");
			ObjectOutputStream outputPT = new ObjectOutputStream(savePartTypes);

			outputPT.writeObject(fcs.getPartTypes());

			// close both output streams... also closes both files
			outputKC.close();
			outputPT.close();

			System.out
					.println("[Server]: Kitconfigs/parts saved successfully.");
		} catch (Exception exc) {
			// print error info if error occurs
			exc.printStackTrace();
			System.out
					.println("[Server]: Error saving kitconfigs and parts to file.");
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
	}

}

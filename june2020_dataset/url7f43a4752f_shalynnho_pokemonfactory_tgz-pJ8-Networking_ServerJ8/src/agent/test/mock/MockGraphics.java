package agent.test.mock;

import java.util.Timer;

import DeviceGraphics.BinGraphics;
import DeviceGraphics.ConveyorGraphics;
import DeviceGraphics.DeviceGraphics;
import DeviceGraphics.KitGraphics;
import DeviceGraphics.KitRobotGraphics;
import DeviceGraphics.PartGraphics;
import GraphicsInterfaces.CameraGraphics;
import GraphicsInterfaces.FeederGraphics;
import GraphicsInterfaces.GantryGraphics;
import GraphicsInterfaces.LaneGraphics;
import GraphicsInterfaces.NestGraphics;
import GraphicsInterfaces.PartsRobotGraphics;
import Networking.Request;
import Utils.Location;
import agent.Agent;
import agent.CameraAgent;
import agent.ConveyorAgent;
import agent.FeederAgent;
import agent.GantryAgent;
import agent.KitRobotAgent;
import agent.LaneAgent;
import agent.NestAgent;
import agent.PartsRobotAgent;
import agent.data.Bin;
import agent.interfaces.Feeder;
import agent.interfaces.Gantry;
import agent.interfaces.Lane;
import agent.interfaces.Nest;

// import GraphicsInterfaces.DeviceGraphics;

;

/**
 * A mock of the graphics server
 * @author Daniel Paje
 */
public class MockGraphics extends Agent implements CameraGraphics,
		GraphicsInterfaces.ConveyorGraphics, FeederGraphics, GantryGraphics,
		GraphicsInterfaces.KitRobotGraphics, LaneGraphics, NestGraphics,
		PartsRobotGraphics, DeviceGraphics {

	Timer timer;
	String name;

	// Agents
	CameraAgent camera;
	ConveyorAgent conveyor;
	FeederAgent feeder;
	GantryAgent gantry;
	KitRobotAgent kitrobot;
	LaneAgent lane;
	NestAgent nest;
	PartsRobotAgent partsrobot;

	// Graphics
	KitRobotGraphics kitrobotgraphics;
	ConveyorGraphics conveyorgraphics;

	public MockGraphics(String name) {
		super();

		timer = new Timer();
		this.name = name;
		// Set server to null
		// conveyorgraphics = new DeviceGraphics.ConveyorGraphics(null);
		// kitrobotgraphics = new DeviceGraphics.KitRobotGraphics(null);

		/*
		 * camera = new CameraAgent("camera"); conveyor = new
		 * ConveyorAgent("conveyor"); feeder = new FeederAgent("feeder"); gantry
		 * = new GantryAgent("gantry"); kitrobot = new
		 * KitRobotAgent("kitrobot"); lane = new LaneAgent("lane"); nest = new
		 * NestAgent("nest"); // partsrobot = new PartsRobotAgent();
		 * camera.startThread(); conveyor.startThread(); feeder.startThread();
		 * gantry.startThread(); kitrobot.startThread(); lane.startThread();
		 * nest.startThread(); // partsrobot.startThread();
		 */

	}

	/*@Override
	public void pickUpPart(PartGraphics part) {

		print("PartsRobotGraphics received message msgpickupPart");

		print("PartsRobotGraphics sending message msgPickUpPartDone() to partsrobot");
		partsrobot.msgPickUpPartDone();

	}*/

	@Override
	public void givePartToPartsRobot(PartGraphics part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receivePart(PartGraphics part) {

	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgPlaceKitOnStand(KitGraphics kit, int location) {
		print("KitRobotGraphics received message msgPlaceKitOnStand");
		print("KitRobotGraphics sending message msgPlaceKitOnStandDone() to KitRobot");
		kitrobot.msgPlaceKitOnStandDone();

	}

	@Override
	public void msgPlaceKitInInspectionArea(KitGraphics kit) {
		print("KitRobotGraphics received message placeKitInInspectionArea");
		print("KitRobotGraphics sending message placeKitInInspectionAreaDone() to KitRobot");
		kitrobot.msgPlaceKitInInspectionAreaDone();
	}

	@Override
	public void msgPlaceKitOnConveyor() {
		print("KitRobotGraphics received message msgPlaceKitOnConveyor");
		print("KitRobotGraphics sending message placeKitOnConveyorDone() to KitRobot");
		kitrobot.msgPlaceKitOnConveyorDone();
	}

	@Override
	public void dropBin(Bin bin, FeederAgent feeder) {
		// TODO Auto-generated method stub
		gantry.msgDropBinDone(bin);
	}

	@Override
	public void removeBin(Bin bin) {
		// TODO Auto-generated method stub
		gantry.msgRemoveBinDone(bin);
	}

	@Override
	public void receiveBin(BinGraphics bin) {
		// TODO Auto-generated method stub
		gantry.msgReceiveBinDone(bin.getBin());
	}

	@Override
	public void purgeBin(BinGraphics bin) {
		// TODO Auto-generated method stub
		feeder.msgPurgeBinDone(bin.getBin());
	}

	@Override
	public void flipDiverter() {
		// TODO Auto-generated method stub
		feeder.msgFlipDiverterDone();
	}

	@Override
	public void msgBringEmptyKit(KitGraphics kit) {
		print("ConveyorGraphics received message msgBringEmptyKit");

		print("ConveyorGraphics sending message msgBringEmptyKitDone() to conveyor");
		conveyor.msgBringEmptyKitDone();

	}

	@Override
	public void msgGiveKitToKitRobot(KitGraphics kit) {
		print("ConveyorGraphics received message msgGiveKitToKitRobot");

		print("ConveyorGraphics sending message msgGiveKitToKitRobotDone() to conveyor");
		conveyor.msgGiveKitToKitRobotDone();
	}

	@Override
	public void msgReceiveKit(KitGraphics kit) {
		print("ConveyorGraphics received message msgReceiveKit");
		print("ConveyorGraphics sending message msgReceiveKitDone() to conveyor");
		conveyor.msgReceiveKitDone();
	}

	@Override
	public void takeKitPhoto(KitGraphics kit) {
		// TODO Auto-generated method stub
		camera.msgTakePictureKitDone(kit, true);
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Accessors and mutators
	 */

	public CameraAgent getCamera() {
		return camera;
	}

	public void setCamera(CameraAgent camera) {
		this.camera = camera;
	}

	public ConveyorAgent getConveyor() {
		return conveyor;
	}

	public void setConveyor(ConveyorAgent conveyor) {
		this.conveyor = conveyor;
	}

	public Feeder getFeeder() {
		return feeder;
	}

	public void setFeeder(FeederAgent feeder) {
		this.feeder = feeder;
	}

	public Gantry getGantry() {
		return gantry;
	}

	public void setGantry(GantryAgent gantry) {
		this.gantry = gantry;
	}

	public KitRobotAgent getKitrobot() {
		return kitrobot;
	}

	public void setKitrobot(KitRobotAgent kitrobot) {
		this.kitrobot = kitrobot;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(LaneAgent lane) {
		this.lane = lane;
	}

	public Nest getNest() {
		return nest;
	}

	public void setNest(NestAgent nest) {
		this.nest = nest;
	}

	public PartsRobotAgent getPartsrobot() {
		return partsrobot;
	}

	public void setPartsrobot(PartsRobotAgent partsrobot) {
		this.partsrobot = partsrobot;
	}

	public KitRobotGraphics getKitrobotgraphics() {
		return kitrobotgraphics;
	}

	public void setKitrobotgraphics(KitRobotGraphics kitrobotgraphics) {
		this.kitrobotgraphics = kitrobotgraphics;
	}

	public ConveyorGraphics getConveyorgraphics() {
		return conveyorgraphics;
	}

	public void setConveyorgraphics(ConveyorGraphics conveyorgraphics) {
		this.conveyorgraphics = conveyorgraphics;
	}

	@Override
	public String getName() {
		return name;
	}

	/*@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit) {

		print("PartsRobotGraphics received message msggivePartToKit");

		print("PartsrobotGraphics sending message msgGivePartToKitDone() to partsrobot");
		partsrobot.msgGivePartToKitDone();

	}*/

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void givePartToNest(PartGraphics part) {
		// TODO Auto-generated method stub
		lane.msgGivePartToNestDone(part);
	}

	@Override
	public void takeNestPhoto(NestGraphics nest1, NestGraphics nest2) {
		// TODO Auto-generated method stub
		camera.msgTakePictureNestDone(nest1, true, nest2, true);
	}

	@Override
	public void receiveBin(Bin newBin, FeederAgent feeder) {
		// TODO Auto-generated method stub
		gantry.msgReceiveBinDone(newBin);
	}

	@Override
	public void hereIsNewBin(Bin bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveData(Request req) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropPartFromArm(PartGraphics part, int arm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PartGraphics createPartGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unjam() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickUpPart(PartGraphics part, int arm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit, int arm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNestID() {
		return 0;
	}

}

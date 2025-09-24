package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import Utils.PartData;
import agent.Agent;
import agent.PartsRobotAgent;
import factory.PartType;

//import factory.data.Kit;

public class PartsRobotGraphics implements GraphicsInterfaces.PartsRobotGraphics, DeviceGraphics {

	Location initialLocation; // initial location of robot
	Location currentLocation; // current location of robot
	boolean arm1, arm2, arm3, arm4; // whether the arm is full, initialized empty
	ArrayList<PartGraphics> partArray; // an array of parts that allocates memory for 4 parts
	KitGraphics kit;
	int kitPosition;
	private final Server server;
	private final PartsRobotAgent partsRobotAgent;

	public PartsRobotGraphics(Server s, Agent a) {
		partsRobotAgent = (PartsRobotAgent) a;
		initialLocation = new Location(250, 450);
		currentLocation = initialLocation;
		kitPosition = 0;
		arm1 = false;
		arm2 = false;
		arm3 = false;
		arm4 = false;
		partArray = new ArrayList<PartGraphics>();
		server = s;
	}

	@Override
	public void pickUpPart(PartGraphics pg, int arm) {
		// TODO Auto-generated method stub
		partArray.add(pg);
		rotateArm();

		PartData pd = new PartData(pg.getLocation(), pg.getPartType(), arm);
		
		Location tempLoc = new Location(550, 100);
		// server.sendData(new Request(Constants.PARTS_ROBOT_PICKUP_COMMAND, Constants.PARTS_ROBOT_TARGET, tempLoc));
		server.sendData(new Request(Constants.PARTS_ROBOT_PICKUP_COMMAND, Constants.PARTS_ROBOT_TARGET, pd));
	}

	@Override
	public void givePartToKit(PartGraphics part, KitGraphics kit, int arm) {

		kit.addPart(part);
		int index = kit.partsSize()-1;
		Location partLocation;
		int yOffset = 20;
		int loweryOffset = 20;
		if(index !=2 || index !=3 ||index !=6  || index!=7 )
		{
			if(index<4)
			{
				partLocation = new Location (kit.getLocation().getX() - 29 + index%4*23, kit.getLocation().getY()-48 +yOffset);
			}
			else
			{
				partLocation = new Location (kit.getLocation().getX() - 29 + index%4*23, kit.getLocation().getY() -48 + 25+loweryOffset);
			}
		}
		else 
		{
			if(index<4)
			{
				partLocation = new Location (kit.getLocation().getX() - 29 + index%4*23 + 35, kit.getLocation().getY()-48 +yOffset);
			}
			else
			{
				partLocation = new Location (kit.getLocation().getX() - 29 + index%4*23 + 35, kit.getLocation().getY()-48 +25 +loweryOffset);
			}
		}
		
		
		PartData pd = new PartData(partLocation, arm);

		kitPosition = kit.getPosition();
		server.sendData(new Request(Constants.PARTS_ROBOT_GIVE_COMMAND, Constants.PARTS_ROBOT_TARGET, pd));

	}

	@Override
	public void dropPartFromArm(PartGraphics part, int arm) {
		PartData a = new PartData(part.getPartType(), arm);
		server.sendData(new Request(Constants.PARTS_ROBOT_DROP_COMMAND, Constants.PARTS_ROBOT_TARGET, a));

	}

	// rotates the arm
	public void rotateArm() {
		if (!isFullArm1()) {
			arm1 = true;
		} else if (!isFullArm2()) {
			arm2 = true;
		} else if (!isFullArm3()) {
			arm3 = true;
		} else if (!isFullArm4()) {
			arm4 = true;
		}
	}

	public void derotateArm() {
		if (isFullArm4()) {
			arm4 = false;
		} else if (isFullArm3()) {
			arm3 = false;
		} else if (isFullArm2()) {
			arm2 = false;
		} else if (isFullArm1()) {
			arm1 = false;
		}
	}

	// returns true if arm1 is full
	public boolean isFullArm1() {
		return arm1;
	}

	// returns true if arm2 is full
	public boolean isFullArm2() {
		return arm2;
	}

	// returns true if arm3 is full
	public boolean isFullArm3() {
		return arm3;
	}

	// returns true if arm4 is full
	public boolean isFullArm4() {
		return arm4;
	}

	// returns the current Location
	public Location getLocation() {
		return currentLocation;
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.PARTS_ROBOT_RECEIVE_PART_COMMAND + Constants.DONE_SUFFIX)) {
			partsRobotAgent.msgPickUpPartDone();
		} else if (req.getCommand().equals(Constants.PARTS_ROBOT_GIVE_COMMAND + Constants.DONE_SUFFIX)) {
			partsRobotAgent.msgGivePartToKitDone();
		} else if (req.getCommand().equals(Constants.PARTS_ROBOT_DROP_PART_COMMAND + Constants.DONE_SUFFIX)) {
			partsRobotAgent.msgDropPartFromArmDone();
			server.displayMessage("Professor Oak: Oops! A part was dropped!");
		} else if (req.getCommand().equals(Constants.KIT_UPDATE_PARTS_LIST_COMMAND + Constants.DONE_SUFFIX)) {
			PartData pd = null;
			PartType pT = (PartType) req.getData();
			for (PartGraphics p : partArray) {
				if (p.getPartType().equals(pT)) {
					partArray.remove(p);
					pd = new PartData(p.getPartType(), p.isInvisible());
					break;
				}
			}
			server.sendData(new Request(Constants.STAND_RECEIVE_PART_COMMAND, Constants.STAND_TARGET + kitPosition, pd));
		}
	}

}

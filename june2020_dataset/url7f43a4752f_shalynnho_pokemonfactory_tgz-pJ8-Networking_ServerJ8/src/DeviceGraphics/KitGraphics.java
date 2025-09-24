package DeviceGraphics;

import java.util.ArrayList;

import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.Location;
import factory.KitConfig;
import factory.PartType;

public class KitGraphics implements DeviceGraphics {

	private final ArrayList<PartGraphics> parts = new ArrayList<PartGraphics>();
	
	// private ArrayList<PartType> partTypes = new ArrayList<PartType>();
	// part types required to make kit
	private KitConfig kitConfig;
	private Location kitLocation;
	private int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	private boolean isFull; // Says whether or not the kit is full
	private final Server server;

	public KitGraphics(Server server) {
		this.server = server;
		isFull = false;
		kitLocation = new Location(0, 0);
		position = 0;
	}

	/*
	 * public KitGraphics(KitGraphics kg) { this.parts = kg.parts; this.kitConfig = kg.kitConfig; this.kitLocation =
	 * kg.kitLocation; this.isFull = kg.isFull; this.server = kg.server; }
	 */

	/**
	 * set the part types required to build kit
	 * 
	 * @param kitConfig
	 *            - parts required to build the kit
	 */
	public void setPartTypes(KitConfig config) {
		kitConfig = config;
	}

	public void addPart(PartGraphics newPart) {
		// TODO: add logic to determine status of kitConfig based on part added
		// or will this be handled somewhere else?
		if(parts.size()<8)
		{
			parts.add(newPart);

			if (parts.size() % 2 == 1) {
				newPart.setLocation(new Location(kitLocation.getX() + 5, kitLocation.getY() + 20 * (parts.size() - 1) / 2));
			} else {
				newPart.setLocation(new Location(kitLocation.getX() + 34, kitLocation.getY() + 20 * (parts.size() - 2) / 2));
			}
		}

	}

	public void setLocation(Location newLocation) {
		kitLocation = newLocation;
	}

	public Location getLocation() {
		return kitLocation;
	}

	public KitConfig getKitConfig() {
		return kitConfig;
	}

	// If true, set isFull boolean to true
	public void setFull(Boolean full) {
		isFull = full;
	}

	public Boolean getFull() {
		return isFull;
	}

	public void receivePart(PartGraphics part) {
		addPart(part);
		PartType type = part.getPartType();
		if (server != null) {
			server.sendData(new Request(Constants.KIT_UPDATE_PARTS_LIST_COMMAND, Constants.KIT_TARGET + position, type));
		}
	}

	@Override
	public void receiveData(Request req) {
		if (req.getCommand().equals("Testing")) {
			addPart(new PartGraphics(Constants.DEFAULT_PARTTYPES.get(0)));
		}
	}
	
	public int partsSize(){
		return parts.size();
	}

}

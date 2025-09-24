package factory;

import java.util.ArrayList;

import javax.sound.sampled.Clip;

import DeviceGraphics.GantryGraphics;
import Networking.Request;
import Networking.Server;
import Utils.Constants;
import Utils.ReadSaveData;
import agent.Agent;
import agent.FCSAgent;

/**
 * Factory Control System. Hub for GUI controls to the factory operations.
 * 
 * @author Peter Zhang
 */
public class FCS {
	private ArrayList<Order> queue = new ArrayList<Order>();
	private ArrayList<KitConfig> kitConfigs = (ArrayList<KitConfig>) Constants.DEFAULT_KITCONFIGS.clone();
	private ArrayList<PartType> partTypes = (ArrayList<PartType>) Constants.DEFAULT_PARTTYPES.clone();

	private final FCSAgent agent;
	private final Server server;
	// private ConsoleWriter console;

	private boolean productionStarted = false;

	private Clip completed;

	public FCS(Server server, Agent a) {
		agent = (FCSAgent) a;
		this.server = server;

		// read from save file
		ArrayList<PartType> updatedPartTypes = ReadSaveData.readPartType();
		if (updatedPartTypes != null) {
			partTypes = updatedPartTypes;
		}

		ArrayList<KitConfig> updatedKitConfigs = ReadSaveData.readKitConfig();
		if (updatedKitConfigs != null) {
			kitConfigs = updatedKitConfigs;
		}

	}

	public void updateParts() {
		server.sendData(new Request(Constants.FCS_UPDATE_PARTS, Constants.ALL_TARGET, partTypes));
	}

	public void updateKits() {
		server.sendData(new Request(Constants.FCS_UPDATE_KITS, Constants.ALL_TARGET, kitConfigs));
	}

	public void shippedKit() {
		server.sendData(new Request(Constants.FCS_SHIPPED_KIT, Constants.ALL_TARGET, null));

		if (queue.get(0) != null) {
			if (queue.get(0).getNumKits() > 0) {
				displayMessage("Professor Oak: Kit Completed!");
			}
		}
	}

	public void displayMessage(String s) {
		server.sendData(new Request(Constants.MSGBOX_DISPLAY_MSG, Constants.MESSAGING_BOX_TARGET, s));
	}

	/**
	 * Called to send clients the updated queue.
	 */
	public void updateQueue() {
		server.sendData(new Request(Constants.FCS_UPDATE_ORDERS, Constants.ALL_TARGET, queue));
	}

	/**
	 * Called only by FCSAgent to synchronize order queues from them.
	 * 
	 * @param q
	 *            updated order queue
	 */
	public void updateQueue(ArrayList<Order> q) {
		queue = q;
		updateQueue();
	}

	public boolean newPart(PartType pt) {
		for (PartType p : partTypes) {
			if (p.getName().equals(pt.getName())) {
				displayMessage("Professor Oak: Professor Oak: Part \"" + pt.getName() + "\" already exists!");
				return false;
			}
		}

		partTypes.add(pt);
		updateParts();
		agent.msgAddNewPartType(pt);

		displayMessage("Professor Oak: Part added - " + pt.getName());
		return true;
	}

	public void editPart(PartType pt) {
		// loops through ArrayList to find based on id, then replaces
		for (int i = 0; i < partTypes.size(); i++) {
			if (partTypes.get(i).equals(pt)) {
				partTypes.set(i, pt);
				updateParts();
				GantryGraphics gantry = (GantryGraphics) server.devices.get(Constants.GANTRY_ROBOT_TARGET);
				gantry.editBin(pt);
			}
		}

		displayMessage("Professor Oak: Part edited - " + pt.getName());
	}

	public void deletePart(PartType pt) {
		// loops through ArrayList to find based on id, then deletes
		for (int i = 0; i < partTypes.size(); i++) {
			if (partTypes.get(i).equals(pt)) {
				partTypes.remove(i);
				updateParts();
			}
		}

		displayMessage("Professor Oak: Part deleted - " + pt.getName());
		// TODO: add in agent call so to stop drawing bin
	}

	public boolean newKit(KitConfig kc) {
		for (KitConfig p : kitConfigs) {
			if (p.getName().equals(kc.getName())) {
				displayMessage("Professor Oak: Professor Oak: Kit \"" + kc.getName() + "\" already exists!");
				return false;
			}
		}
		kitConfigs.add(kc);
		updateKits();

		displayMessage("Professor Oak: Kit added - " + kc.getName());
		return true;
	}

	public void editKit(KitConfig kc) {
		// loops through ArrayList to find based on id, then replaces
		for (int i = 0; i < kitConfigs.size(); i++) {
			if (kitConfigs.get(i).equals(kc)) {
				kitConfigs.set(i, kc);
			}
		}
		updateKits();
		displayMessage("Professor Oak: Kit edited - " + kc.getName());
	}

	public void deleteKit(KitConfig kc) {
		// loops through ArrayList to find based on id, then deletes
		for (int i = 0; i < kitConfigs.size(); i++) {
			if (kitConfigs.get(i).equals(kc)) {
				kitConfigs.remove(i);
			}
		}
		updateKits();
		displayMessage("Professor Oak: Kit deleted - " + kc.getName());
	}

	public void addOrder(Order o) {
		if (o.getNumKits() > 0) {
			agent.msgAddKitsToQueue(o);

			// TODO: put "start production" button
			if (!productionStarted) {
				agent.msgStartProduction();
				productionStarted = true;
			}
		}
		displayMessage("Professor Oak: Ordered " + o.getConfig().getName() + " x" + o.getNumKits());
	}

	public void startProduction() {
		agent.msgStartProduction();
	}

	public void setDropChance(Float c) {
		// TODO Make a slider/button for this
		agent.msgSetPartsRobotDropChance(c);
		displayMessage("Professor Oak: Parts Robot's drop rate set to " + c * 100 + "%");
	}

	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.FCS_NEW_PART)) {
			PartType pt = (PartType) req.getData();
			newPart(pt);
		} else if (req.getCommand().equals(Constants.FCS_EDIT_PART)) {
			PartType pt = (PartType) req.getData();
			editPart(pt);
		} else if (req.getCommand().equals(Constants.FCS_DELETE_PART)) {
			PartType pt = (PartType) req.getData();
			deletePart(pt);
		} else if (req.getCommand().equals(Constants.FCS_NEW_KIT)) {
			KitConfig kc = (KitConfig) req.getData();
			newKit(kc);
		} else if (req.getCommand().equals(Constants.FCS_EDIT_KIT)) {
			KitConfig kc = (KitConfig) req.getData();
			editKit(kc);
		} else if (req.getCommand().equals(Constants.FCS_DELETE_KIT)) {
			KitConfig kc = (KitConfig) req.getData();
			deleteKit(kc);
		} else if (req.getCommand().equals(Constants.FCS_ADD_ORDER)) {
			Order o = (Order) req.getData();
			addOrder(o);
		} else if (req.getCommand().equals(Constants.FCS_START_PRODUCTION)) {
			startProduction();
		} else if (req.getCommand().equals(Constants.FCS_STOP_KIT)) {
			// agents aren't ready
		} else if (req.getCommand().equals(Constants.FCS_STOP_PRODUCTION)) {
			// agents aren't ready
		} else if (req.getCommand().equals(Constants.FCS_SET_DROP_CHANCE)) {
			Float chance = (Float) req.getData();
			setDropChance(chance);
		} else if (req.getCommand().equals(Constants.FCS_STOP_LANE)) {
			agent.msgBreakLane((Integer) req.getData());
			displayMessage("Professor Oak: The lane broke!");
		} else if (req.getClass().equals(Constants.MSGBOX_DISPLAY_MSG)) {
			String s = (String) req.getData();
			displayMessage(s);
		}
	}

	public ArrayList<KitConfig> getKitConfigs() {
		return kitConfigs;
	}

	public void setKitConfigs(ArrayList<KitConfig> kitConfigs) {
		this.kitConfigs = kitConfigs;
	}

	public ArrayList<PartType> getPartTypes() {
		return partTypes;
	}

	public void setPartTypes(ArrayList<PartType> partTypes) {
		this.partTypes = partTypes;
	}

}

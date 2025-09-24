package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import GraphicsInterfaces.GantryGraphics;
import agent.data.Bin;
import agent.data.Bin.BinStatus;
import agent.interfaces.Gantry;
import factory.PartType;

/**
 * Gantry delivers parts to the feeder
 * 
 * @author Michael Gendotti, Arjun Bhargava
 */
public class GantryAgent extends Agent implements Gantry {

	public List<Bin> binList = Collections.synchronizedList(new ArrayList<Bin>());
	public List<MyFeeder> feeders = Collections.synchronizedList(new ArrayList<MyFeeder>());

	private GantryGraphics gantryGraphics;

	private final String name;

	// private boolean waitForDrop = false;

	public class MyFeeder {
		public FeederAgent feeder;
		public PartType requestedType;
		public FeederStatus state;

		public MyFeeder(FeederAgent feeder) {
			this.feeder = feeder;
			state = FeederStatus.PENDING;
		}

		public MyFeeder(FeederAgent feeder, PartType type) {
			this.feeder = feeder;
			this.requestedType = type;
			state = FeederStatus.PENDING;
		}

		public FeederAgent getFeeder() {
			return feeder;
		}

		public PartType getRequestedType() {
			return requestedType;
		}

	}

	public enum FeederStatus {
		PENDING, REQUESTED_PARTS, BEING_MOVED_TO, FULL, PURGING
	};

	public Semaphore animation = new Semaphore(0, true);

	public GantryAgent(String name) {
		super();
		this.name = name;
		print("I'm working");
	}

	@Override
	public void msgHereIsBin(Bin bin) {
		print("Received msgHereIsBinConfig");
		binList.add(bin);
		stateChanged();
	}

	@Override
	public void msgINeedParts(PartType type, FeederAgent feeder) {
		print("Received msgINeedParts from " + feeder.toString());
		boolean temp = true;
		for (MyFeeder currentFeeder : feeders) {
			if (currentFeeder.getFeeder() == feeder) {
				// print("found feeder");
				currentFeeder.requestedType = type;
				currentFeeder.state = FeederStatus.REQUESTED_PARTS;
				temp = false;
				break;
			}
		}
		if (temp == true) {
			MyFeeder currentFeeder = new MyFeeder(feeder, type);
			feeders.add(currentFeeder);
		}
		stateChanged();
	}

	@Override
	public void msgRemoveBin(Bin bin) {
		print("Received msgRemoveBin");
		// synchronized(feeders){
		for (MyFeeder currentFeeder : feeders) {
			if (currentFeeder.state == FeederStatus.FULL && currentFeeder.requestedType.equals(bin.part.type)) {
				currentFeeder.state = FeederStatus.PURGING;
				break;
			}
		}
		// }
		bin.binState = BinStatus.DISCARDING;
		stateChanged();
	}

	@Override
	public void msgReceiveBinDone(Bin bin) {
		print("Received msgReceiveBinDone from graphics");
		bin.binState = BinStatus.OVER_FEEDER;
		animation.release();
		stateChanged();
	}

	@Override
	public void msgDropBinDone(Bin bin) {
		print("Received msgdropBinDone from graphics");
		bin.binState = BinStatus.EMPTY;
		animation.release();
		// waitForDrop = false;
		stateChanged();
	}

	@Override
	public void msgRemoveBinDone(Bin bin) {
		print("Received msgremoveBinDone from graphics");
		// binList.remove(bin);
		bin.binState = BinStatus.FULL;
		animation.release();
		stateChanged();
	}

	// SCHEDULER
	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(binList){
			for (Bin bin : binList) {
				if (bin.binState == BinStatus.PENDING) {
					addBinToGraphics(bin);
					return true;
				}
			}
		}
		for (MyFeeder currentFeeder : feeders) {
			if (currentFeeder.state == FeederStatus.REQUESTED_PARTS) {
				for (Bin bin : binList) {
					if (bin.part.type.equals(currentFeeder.getRequestedType()) && bin.binState == BinStatus.FULL) {
						moveToFeeder(bin, currentFeeder);
						return true;
					}
				}
			}
		}

		for (Bin bin : binList) {
			if (bin.binState == BinStatus.DISCARDING) {
				discardBin(bin);
				return true;
			}
		}
		return false;
	}

	// ACTIONS
	public void moveToFeeder(Bin bin, MyFeeder feeder) {
		print("Moving bin to over feeder " + feeder.feeder.toString());
		bin.binState = BinStatus.MOVING;
		feeder.state = FeederStatus.BEING_MOVED_TO;

		gantryGraphics.receiveBin(bin, feeder.getFeeder());
		// waitForDrop = true;
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fillFeeder(bin, feeder);

		stateChanged();
	}

	public void fillFeeder(Bin bin, MyFeeder feeder) {
		print("Placing bin in feeder and filling feeder " + feeder.feeder.toString());
		bin.binState = BinStatus.FILLING_FEEDER;
		feeder.state = FeederStatus.FULL;
		// waitForDrop = false;

		gantryGraphics.dropBin(bin, feeder.getFeeder());

		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		feeder.getFeeder().msgHereAreParts(bin.part.type, bin);

		stateChanged();
	}

	public void discardBin(Bin bin) {
		print("Discarding bin");
		bin.binState = BinStatus.FULL;

		gantryGraphics.removeBin(bin);

		try {
			animation.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (MyFeeder currentFeeder : feeders) {
			if (currentFeeder.state == FeederStatus.PURGING && currentFeeder.requestedType.equals(bin.part.type)) {
				currentFeeder.state = FeederStatus.PENDING;
				currentFeeder.feeder.msgRemoveBinDone();
			}
		}

		stateChanged();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		this.gantryGraphics = (GantryGraphics) dg;
	}

	public void addBinToGraphics(Bin bin) {
		print("added bin to graphics");
		if (gantryGraphics != null) {
			gantryGraphics.hereIsNewBin(bin);
		}
		bin.binState = BinStatus.FULL;
		stateChanged();
	}

	public void setFeeder(FeederAgent feeder) {
		feeders.add(new MyFeeder(feeder));
	}

}

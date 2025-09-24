package agent.interfaces;

import factory.PartType;
import agent.GantryAgent;
import agent.LaneAgent;
import agent.data.Bin;
import agent.data.Part;
import GraphicsInterfaces.FeederGraphics;

public interface Feeder {

	public abstract void msgINeedPart(PartType type, LaneAgent lane);

	public abstract void msgHereAreParts(PartType type, Bin bin);
	
	public abstract void msgRemoveBinDone();

	public abstract void msgReceiveBinDone(Bin bin);
	
	public abstract void msgPurgeBinDone(Bin bin);
	
	public abstract void msgFlipDiverterDone();

	public abstract boolean pickAndExecuteAnAction();

	//GETTERS AND SETTERS
	public abstract void setGantry(GantryAgent gantry);

	public abstract void setLane(LaneAgent lane);

}
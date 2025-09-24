package agent.interfaces;

import factory.PartType;
import agent.FeederAgent;
import agent.data.Bin;

public interface Gantry {

	public abstract void msgHereIsBin(Bin bin);

	public abstract void msgINeedParts(PartType type, FeederAgent feeder);
	
	public abstract void msgRemoveBin(Bin bin);

	public abstract void msgReceiveBinDone(Bin bin);

	public abstract void msgDropBinDone(Bin bin);

	public abstract void msgRemoveBinDone(Bin bin);

	public abstract boolean pickAndExecuteAnAction();

}
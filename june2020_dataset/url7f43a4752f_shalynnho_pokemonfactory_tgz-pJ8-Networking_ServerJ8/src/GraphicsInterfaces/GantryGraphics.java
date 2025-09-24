package GraphicsInterfaces;

import agent.FeederAgent;
import agent.data.Bin;

public interface GantryGraphics {

	public abstract void receiveBin(Bin newBin, FeederAgent feeder);
	public abstract void dropBin(Bin newBin, FeederAgent feeder);
	public abstract void removeBin(Bin newBin);
	public abstract void hereIsNewBin(Bin bin);
	
}

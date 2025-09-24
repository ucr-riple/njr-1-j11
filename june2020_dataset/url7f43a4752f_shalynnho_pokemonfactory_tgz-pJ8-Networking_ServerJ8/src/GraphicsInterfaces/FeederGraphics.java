package GraphicsInterfaces;

import DeviceGraphics.BinGraphics;
import DeviceGraphics.PartGraphics;
import Utils.Location;

public interface FeederGraphics {

	public abstract void receiveBin(BinGraphics bin);
	public abstract void purgeBin(BinGraphics bin);
	public abstract PartGraphics createPartGraphics();
	public abstract void flipDiverter();
	public abstract Location getLocation();
}

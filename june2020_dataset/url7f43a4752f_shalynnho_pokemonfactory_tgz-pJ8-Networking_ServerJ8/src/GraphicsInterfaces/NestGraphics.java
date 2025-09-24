package GraphicsInterfaces;

import DeviceGraphics.PartGraphics;
import Utils.Location;

public interface NestGraphics {

	public abstract void receivePart(PartGraphics part);
	public abstract void givePartToPartsRobot(PartGraphics part);
	public abstract void purge();
	public Location getLocation();

	public int getNestID();
	
}

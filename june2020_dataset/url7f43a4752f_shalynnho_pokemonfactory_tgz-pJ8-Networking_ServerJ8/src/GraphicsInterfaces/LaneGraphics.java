package GraphicsInterfaces;

import agent.data.Part;
import DeviceGraphics.PartGraphics;

public interface LaneGraphics {

	public abstract void receivePart(PartGraphics part);
	public abstract void givePartToNest(PartGraphics part);
	public abstract void unjam();
	public abstract void purge();
	
}

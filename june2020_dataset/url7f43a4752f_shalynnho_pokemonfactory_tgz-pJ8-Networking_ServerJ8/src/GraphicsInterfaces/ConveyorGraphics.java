package GraphicsInterfaces;

import DeviceGraphics.KitGraphics;

public interface ConveyorGraphics {

	public abstract void msgBringEmptyKit(KitGraphics kit);
	public abstract void msgGiveKitToKitRobot(KitGraphics kit);
	public abstract void msgReceiveKit(KitGraphics kit);
	
}

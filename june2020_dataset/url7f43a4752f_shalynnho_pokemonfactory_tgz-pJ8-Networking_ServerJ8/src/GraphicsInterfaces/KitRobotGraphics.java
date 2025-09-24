package GraphicsInterfaces;

import DeviceGraphics.KitGraphics;

public interface KitRobotGraphics {

	public abstract void msgPlaceKitOnStand(KitGraphics kit, int location);

	public abstract void msgPlaceKitInInspectionArea(KitGraphics kit);

	public abstract void msgPlaceKitOnConveyor();

}

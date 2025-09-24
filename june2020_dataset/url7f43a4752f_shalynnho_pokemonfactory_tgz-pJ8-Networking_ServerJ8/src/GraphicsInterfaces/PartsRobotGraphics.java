package GraphicsInterfaces;

import DeviceGraphics.KitGraphics;
import DeviceGraphics.PartGraphics;

public interface PartsRobotGraphics {

	public abstract void pickUpPart(PartGraphics part, int arm);

	public abstract void givePartToKit(PartGraphics part, KitGraphics kit, int arm);

	public abstract void dropPartFromArm(PartGraphics part, int arm);

}

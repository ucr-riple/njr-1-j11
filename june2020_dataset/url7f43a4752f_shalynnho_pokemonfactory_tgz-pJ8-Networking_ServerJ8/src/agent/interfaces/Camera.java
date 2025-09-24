package agent.interfaces;

import DeviceGraphics.KitGraphics;
import GraphicsInterfaces.NestGraphics;
import agent.data.Kit;

public interface Camera {

	public abstract void msgInspectKit(Kit kit);

	public abstract void msgIAmFull(Nest nest);
	
	public abstract void msgResetSelf();

	/**
	 * For v0, nests will never have bad parts.
	 * @param nest nest that was photographed.
	 */
	public abstract void msgTakePictureNestDone(NestGraphics nest, boolean d, NestGraphics nest2, boolean d2);

	public abstract void msgTakePictureKitDone(KitGraphics kit, boolean done);

	public abstract boolean pickAndExecuteAnAction();

}

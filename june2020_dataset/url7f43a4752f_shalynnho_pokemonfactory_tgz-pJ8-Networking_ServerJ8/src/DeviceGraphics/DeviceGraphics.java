package DeviceGraphics;

import Networking.Request;

/**
 * The abstract class that supports all other DeviceGraphics.
 * @author Peter Zhang
 */
public interface DeviceGraphics {
	public abstract void receiveData(Request req);
}

package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Location;
import Utils.StringUtil;

/**
 * An abstract class that every DeviceGraphicsDisplay extends from.
 * @author Peter Zhang
 */
public abstract class DeviceGraphicsDisplay implements Cloneable {

	Client client;
	Location location;

	/**
	 * Override this method to draw out your component.
	 */
	public abstract void draw(JComponent c, Graphics2D g);

	/**
	 * Override this method so to process any requests sent from Server. Will be
	 * called by Client as Requests arrive.
	 */
	public abstract void receiveData(Request req);

	public void setLocation(Location newLocation) {
		location = newLocation;
	}

	/**
	 * Print message. This should be used instead of System.out.println() as it
	 * will prepend the return value from DeviceGraphicsDisplay.getName().
	 */
	protected void print(String msg) {
		print(msg, null);
	}

	/** Print message with exception stack trace */
	protected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append("[Graphics]");
		sb.append(this.getName());
		sb.append(": ");
		sb.append(msg);
		sb.append("\n");
		if (e != null) {
			sb.append(StringUtil.stackTraceString(e));
		}
		System.out.print(sb.toString());
	}

	/**
	 * Returns toString()
	 * @return string representation of this DeviceGraphicsDisplay object
	 */
	public String getName() {
		return this.toString();
	}

	public void setExit(boolean b) {
		// TODO Auto-generated method stub
		
	}
}

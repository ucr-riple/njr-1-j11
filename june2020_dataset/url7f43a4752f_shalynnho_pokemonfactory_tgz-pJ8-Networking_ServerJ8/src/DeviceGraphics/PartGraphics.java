package DeviceGraphics;

import java.io.Serializable;

import Utils.Location;
import factory.PartType;

public class PartGraphics implements Serializable {
	private PartType partType;
	private Location partLocation;
	private boolean quality;
	private boolean isInvisible;

	public PartGraphics(PartType type) {
		partType = type;
		quality = true;
		isInvisible = false;
	}

	public PartGraphics(PartGraphics partGraphics) {
		this.partLocation = partGraphics.partLocation;
		this.partType = partGraphics.partType;
		isInvisible = false;
	}

	public void setLocation(Location newLocation) {
		partLocation = newLocation;
	}

	public boolean isInvisible() {
		return isInvisible;
	}

	public void setInvisible(boolean invis) {
		this.isInvisible = invis;
	}

	public Location getLocation() {
		return partLocation;
	}

	public PartType getPartType() {
		return partType;
	}

	public void setQuality(boolean qual) {
		quality = qual;
		// if (quality == false) {
		// partType = type;
		// }
	}

	public boolean getQuality() {
		return quality;
	}

	public void setPartType(PartType pt) {
		partType = pt;
	}
}

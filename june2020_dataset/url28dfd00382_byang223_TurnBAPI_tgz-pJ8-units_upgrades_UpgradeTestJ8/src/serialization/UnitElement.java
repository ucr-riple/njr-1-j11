package serialization;

/**
 * For serialization of a unit
 * @author tianyu shi
 * 
 */

import modes.EditMode.UnitOwner;
import units.Unit;

public class UnitElement implements java.io.Serializable {

	private Unit U;

	private UnitOwner currOwner;

	public Unit getUnit() {
		return this.U;
	}

	public void setUnit(Unit U) {
		this.U = U;
	}

	public UnitOwner getUnitOwner() {	
		return this.currOwner;
	}

	public void setUnitOwner(UnitOwner currOwner) {
		this.currOwner = currOwner;
	}

	public String toString() {
		return "UnitElement [U=" + U + ", currOwner=" + currOwner + "]";
	}
}

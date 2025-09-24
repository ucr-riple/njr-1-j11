package net.slreynolds.ds;



final public class Bar implements Comparable<Bar> {
	private final int ID;
		
	public Bar(int id) {
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}

	@Override
	public int hashCode() {
		// Return a smaller hashcode so that
		// we can observe collisions. Don't do this
		// in real life.
		return ID & 0xFFFF;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Bar)) {
			return false;
		}
		Bar other = (Bar) obj;
		
		return (ID == other.ID);
	}

	@Override
	public String toString() {
		return "Bar("+ID+")";
	}
	
	@Override
	public int compareTo(Bar other) {
		return this.ID - other.ID;
	}
}

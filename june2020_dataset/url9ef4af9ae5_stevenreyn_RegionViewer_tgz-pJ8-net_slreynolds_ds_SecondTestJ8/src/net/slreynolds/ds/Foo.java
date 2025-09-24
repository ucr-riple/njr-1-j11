
package net.slreynolds.ds;


public class Foo implements Comparable<Foo> {
	private final int ID;
	
	public Foo(int id) {
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
		if (!(obj instanceof Foo)) {
			return false;
		}
		Foo other = (Foo) obj;
		
		return (ID == other.ID);
	}

	@Override
	public String toString() {
		return "Foo("+ID+")";
	}

	@Override
	public int compareTo(Foo other) {
		return this.ID - other.ID;
	}
	
}

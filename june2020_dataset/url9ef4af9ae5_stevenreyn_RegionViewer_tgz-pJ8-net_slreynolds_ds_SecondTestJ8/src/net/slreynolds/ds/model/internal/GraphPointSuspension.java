package net.slreynolds.ds.model.internal;

public class GraphPointSuspension {
	
	final private int _nestingLevel;
	final private Object _o;
	
	
	public GraphPointSuspension(int nestingLevel,  Object o) {
		if (o == null) {
			throw new IllegalArgumentException("cannot suspend null object");
		}
		this._nestingLevel = nestingLevel;
		this._o = o;
	}

	public int getNestingLevel() {
		return _nestingLevel;
	}

	public Object getObject() {
		return _o;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_o == null) ? 0 : _o.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof GraphPointSuspension))
			return false;
		GraphPointSuspension other = (GraphPointSuspension) obj;
		/* they are equals if-and-only-if they suspend the same object */
		return (_o == other._o);
	}

}

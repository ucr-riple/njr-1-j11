package net.slreynolds.ds.model.internal;

public class EdgeSuspension {

	final private Object _from;
	final private Object _to;
	final private String _name;
	
	public EdgeSuspension(Object from, Object to, String name) {
		if (from == null) {
			throw new IllegalArgumentException("cannot suspend edge from null object");
		}
		if (to == null) {
			throw new IllegalArgumentException("cannot suspend edge to null object");
		}
		if (name == null) {
			throw new IllegalArgumentException("cannot suspend edge with null name");
		}
		this._from = from;
		this._to = to;
		this._name = name;
	}

	public Object getFrom() {
		return _from;
	}

	public Object getTo() {
		return _to;
	}

	public String getName() {
		return _name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_from == null) ? 0 : _from.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_to == null) ? 0 : _to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EdgeSuspension)) {
			return false;
		}
		EdgeSuspension other = (EdgeSuspension) obj;
		/* 
		 * To be equals two edge suspensions must satisfy the following
		 * 1. from objects must be identical
		 * 2. to objects must be identical
		 * 3. names must be equals
		 */
		if (!(_from == other._from)) {
			return false;
		}

		if (!(_to == other._to)) {
			return false;
		}
		
		if (!_name.equals(other._name)) {
			return false;
		}
		return true;
	}

	
}

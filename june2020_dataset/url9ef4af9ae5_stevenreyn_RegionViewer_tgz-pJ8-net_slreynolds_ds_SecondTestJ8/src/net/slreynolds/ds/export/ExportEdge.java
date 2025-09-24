package net.slreynolds.ds.export;

class ExportEdge {
	
	private final int _ID;
	private int _fromID;     // TODO should be final
	private final int _toID;
	private String _label;
	
	ExportEdge(int ID, int fromID, int toID, String label) {
		super();
		this._ID = ID;
		this._fromID = fromID;
		this._toID = toID;
		this._label = label;
	}
	
	int getID() {
		return _ID;
	}
	
	int getFromID() {
		return _fromID;
	}
	
	void setFromID(int id) {
		_fromID = id;
	}

	int getToID() {
		return _toID;
	}

	String getLabel() {
		return _label;
	}
	
	void setLabel(String l) {
		if (l == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		_label = l;
	}

	@Override
	public String toString() {
		return "JungEdge [_ID=" + _ID + ", _label=" + _label + ", from " + _fromID + " to " + _toID +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportEdge other = (ExportEdge) obj;
		if (_ID != other._ID)
			return false;
		return true;
	}
}
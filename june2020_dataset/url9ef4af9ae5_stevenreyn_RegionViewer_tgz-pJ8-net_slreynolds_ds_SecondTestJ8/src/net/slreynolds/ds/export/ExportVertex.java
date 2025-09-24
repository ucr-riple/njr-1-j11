package net.slreynolds.ds.export;




class ExportVertex {
	
	private final int _ID;
	private final String _label;
	private final VertexType _vertextType;
	private final int _generation;
	
	ExportVertex(int ID, String label, VertexType vertextType, int generation) {
		if (generation < 0) {
			throw new IllegalArgumentException("generation cannot be negative");
		}
		this._ID = ID;
		this._label = label;
		this._vertextType = vertextType;
		this._generation = generation;
	}
	
	int getID() {
		return _ID;
	}
	
	String getLabel() {
		return _label;
	}
	
	VertexType getVertexType() {
		return _vertextType;
	}
	
	int getGeneration() {
		return _generation;
	}

	@Override
	public String toString() {
		return "JungVertex [_ID=" + _ID + ", _label=" + _label
				+ ", _vertextType=" + _vertextType + "]";
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
		ExportVertex other = (ExportVertex) obj;
		if (_ID != other._ID)
			return false;
		return true;
	}
	
}
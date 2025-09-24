package net.slreynolds.ds.model;

public class NamedID {

		private final int _ID;
		


		NamedID(int ID) { // package access, only NamedIDGenerator should create these
			_ID = ID;
		}
		
		int asInt() {
			return _ID;
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
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof NamedID)) {
				return false;
			}
			NamedID other = (NamedID) obj;
			if (_ID != other._ID) {
				return false;
			}
			return true;
		}
}

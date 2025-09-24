package core;


public class Triple {

	private final Resource subject;
	private final Resource predicate;
	private final Resource object;
	
	public Triple(Resource subject, Resource predicate, Resource object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public Triple(String s, String p, String o) {
		this(Resource.of(s), Resource.of(p), Resource.of(o));
	}

	public Resource getSubject() {
		return subject;
	}

	public Resource getPredicate() {
		return predicate;
	}

	public Resource getObject() {
		return object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Triple other = (Triple) obj;
		if (object == null) {
			if (other.object != null) {
				return false;
			}
		} else if (!object.equals(other.object)) {
			return false;
		}
		if (predicate == null) {
			if (other.predicate != null) {
				return false;
			}
		} else if (!predicate.equals(other.predicate)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", subject, predicate, object);
	}
	
	public boolean equalsSubject(Resource s) {
		return subject.equals(s);
	}
	
	public boolean equalsPredicate(Resource p) {
		return predicate.equals(p);
	}
	
	public boolean equalsObject(Resource o) {
		return object.equals(o);
	}
		
}

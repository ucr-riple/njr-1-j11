package logic;

import core.Resource;

public class ResourceIssuerImpl implements ResourceIssuer {
	
	private static final String DEFAULT_PREFIX = "c";

	private final String prefix;
	private int count;

	public ResourceIssuerImpl(String prefix, int count) {
		super();
		this.prefix = prefix;
		this.count = count;
	}
	
	public ResourceIssuerImpl(String prefix) {
		this( prefix, 1 );
	}
	
	public ResourceIssuerImpl() {
		this( DEFAULT_PREFIX, 1 );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		ResourceIssuerImpl other = (ResourceIssuerImpl) obj;
		if (prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (!prefix.equals(other.prefix)) {
			return false;
		}
		return true;
	}

	@Override
	public Resource createFresh() {
		Resource fresh = Resource.of( prefix + count );
		
		count++;

		return fresh;
	}
	
}

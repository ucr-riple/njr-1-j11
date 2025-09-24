package logic;

import java.util.HashSet;
import java.util.Set;

import solver.PLSATSolver;
import core.Resource;
import core.Triple;

public class Atom implements Proposition {
	
	private final String name;

	public Atom(String name) {
		super();
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Atom other = (Atom) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public Set<Triple> toTriples() {
		return toTriples(new ResourceIssuerImpl(), PLSATSolver._1);
	}
	
	@Override
	public Set<Triple> toTriples(ResourceIssuer issuer, Resource previous) {
		Set<Triple> triples = new HashSet<>(1);
		
		triples.add( new Triple(previous, PLSATSolver.ATOM, Resource.of(name)) );
		
		return triples;
	}

	@Override
	public Proposition simplify() {
		return this;
	}

	@Override
	public Proposition normalize() {
		return this;
	}
	
}

package logic;

import java.util.HashSet;
import java.util.Set;

import solver.PLSATSolver;
import core.Resource;
import core.Triple;

public class Not implements Proposition {

	private final Proposition negatee;

	public Not(Proposition negatee) {
		super();
		this.negatee = negatee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((negatee == null) ? 0 : negatee.hashCode());
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
		Not other = (Not) obj;
		if (negatee == null) {
			if (other.negatee != null) {
				return false;
			}
		} else if (!negatee.equals(other.negatee)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format( "(not %s)", negatee );
	}
	
	@Override
	public Set<Triple> toTriples() {
		return toTriples(new ResourceIssuerImpl(), PLSATSolver._1);
	}

	@Override
	public Set<Triple> toTriples(ResourceIssuer issuer, Resource previous) {
		Set<Triple> triples = new HashSet<>();
		
		Resource fresh = issuer.createFresh(); 
		triples.add( new Triple(previous, PLSATSolver.NOT, fresh) );
		triples.addAll( negatee.toTriples(issuer, fresh) );
		
		return triples;
	}

	@Override
	public Proposition simplify() {
		Proposition n = negatee.simplify();
		
		if ( n instanceof Not ) {
			return ((Not) n).negatee;
		}
		
		return new Not(n);
	}

	@Override
	public Proposition normalize() {
		return new Not( negatee.normalize() );
	}
	

}

package logic;

import java.util.HashSet;
import java.util.Set;

import solver.PLSATSolver;
import core.Resource;
import core.Triple;

public class And implements Proposition {

	private final Proposition left;
	private final Proposition right;
	
	public And(Proposition left, Proposition right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		And other = (And) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format( "(and %s %s)", left, right );
	}
	
	@Override
	public Set<Triple> toTriples() {
		return toTriples(new ResourceIssuerImpl(), PLSATSolver._1);
	}
	
	@Override
	public Set<Triple> toTriples(ResourceIssuer issuer, Resource previous) {
		Set<Triple> triples = new HashSet<>();
		
		Resource thisLabel = issuer.createFresh();
		triples.add( new Triple(previous, PLSATSolver.AND, thisLabel) );
		
		Resource leftLabel  = issuer.createFresh();
		Resource rightLabel = issuer.createFresh();
		triples.add( new Triple(thisLabel, leftLabel, rightLabel) );
		triples.addAll( left.toTriples(issuer, leftLabel) );
		triples.addAll( right.toTriples(issuer, rightLabel) );
		
		return triples;
	}

	@Override
	public Proposition simplify() {
		Proposition l = left.simplify();
		Proposition r = right.simplify();
		
		if ( l.equals(r) ) {
			return l;
		}
		
		return new And(l, r);
	}

	@Override
	public Proposition normalize() {
		return new And( left.normalize(), right.normalize() );
	}
	
}

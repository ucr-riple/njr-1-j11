package query;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rule.Body;
import rule.Head;

import com.google.common.collect.Lists;

import core.Triple;

public class Query implements Head, Body {
	
	private final List<PrimitiveQuery> primitives;
	
	public Query(List<PrimitiveQuery> primitives) {
		super();

		this.primitives = primitives;
	}
	
	public Query(PrimitiveQuery... primitives) {
		this( Lists.newArrayList(primitives) );
	}
	
	private boolean hasTail() {
		// TODO: 実行時例外を投げる
		return primitives.size() >= 2;
	}
	
	private PrimitiveQuery head() {
		// TODO: 実行時例外を投げる
		return primitives.get(0);
	}
	
	private Query tail() {
		return new Query( primitives.subList(1, primitives.size()) );
	}
	
	private Query apply(Substitution substitution) {
		List<PrimitiveQuery> applied = new LinkedList<>();
		
		for ( final PrimitiveQuery primitive : primitives ) {
			applied.add( primitive.apply(substitution) );
		}

		return new Query(applied);
	}

	public Resolution solve(QueryTarget target) {
		return __solve( new Substitution(), target );
	}
	
	private Resolution __solve(Substitution previous, QueryTarget target) {
		Resolution current = this.apply(previous).head().solve(target);
		Resolution next    = current.concat(previous);
		
		if ( !hasTail() ) {
			return next;
		}

		Resolution answer = Resolution.createEmpty();
		for ( final Substitution s : next ) {
			answer.addAll( this.tail().__solve(s, target) );
		}
		
		return answer.isEmpty() ? Resolution.FAILURE : answer;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((primitives == null) ? 0 : primitives.hashCode());
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
		Query other = (Query) obj;
		if (primitives == null) {
			if (other.primitives != null) {
				return false;
			}
		} else if (!primitives.equals(other.primitives)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for ( final PrimitiveQuery p : primitives ) {
			sb.append(p);
		}

		return sb.toString();
	}

	@Override
	public Set<Triple> apply(Resolution r) {
		Set<Triple> result = new HashSet<>();
		
		for ( final Substitution s : r) {
			result.addAll( this.apply(s).toTriple() );
		}
		
		return result;
	}
	
	private Set<Triple> toTriple() {
		Set<Triple> triples = new HashSet<>();
		
		for ( final PrimitiveQuery p : primitives ) {
			triples.add(p.toTriple());
		}
		
		return triples;
	}

}

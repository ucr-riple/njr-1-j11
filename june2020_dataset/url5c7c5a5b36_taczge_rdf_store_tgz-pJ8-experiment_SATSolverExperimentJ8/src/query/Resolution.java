package query;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Joiner;

public class Resolution implements Iterable<Substitution> {

	public static final Resolution SUCCESS = new Resolution() {
		@Override
		public String toString() {
			return "Resolution.SUCCESS";
		}
	};

	public static final Resolution FAILURE = new Resolution() {
		@Override
		public String toString() {
			return "Resolution.FAILURE";
		}
		
		@Override
		public Resolution concat(Substitution substitution) {
			return this;
		}
	};
	
	private Set<Substitution> substitutions;
	
	// !!重要!! 空のリストのとき FAILURE を返す
	public static Resolution of(Set<Substitution> subs) {
		return subs.isEmpty() ? Resolution.FAILURE : new Resolution(subs);
	}
	
	public static Resolution createEmpty() {
		return new Resolution();
	}
	
	public static Resolution of(Substitution...subs) {
		return new Resolution(subs);
	}
	
	private Resolution(Set<Substitution> substitusions) {
		super();
		this.substitutions = substitusions;
	}
	
	private Resolution() {
		this( new HashSet<Substitution>() );
	}
	
	private Resolution(Substitution...subs) {
		this();

		for ( int i = 0; i < subs.length; i++ ) {
			substitutions.add( subs[i] );
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((substitutions == null) ? 0 : substitutions.hashCode());
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
		Resolution other = (Resolution) obj;
		if (substitutions == null) {
			if (other.substitutions != null) {
				return false;
			}
		} else if (!substitutions.equals(other.substitutions)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return Joiner.on(System.lineSeparator()).join(substitutions);
	}
	
	public boolean isEmpty() {
		return substitutions.isEmpty();
	}
	
	public int size() {
		return substitutions.size();
	}
	
	public void add(Substitution s) {
		substitutions.add(s);
	}
	
	public void addAll(Resolution other) {
		substitutions.addAll( other.substitutions );
	}
	
	public Resolution concat(Substitution resolution) {
		if ( this.isEmpty() ) {
			return new Resolution(resolution);
		}
		
		Set<Substitution> appended = new HashSet<>( this.size() );
		for ( final Substitution s : this ) {
			appended.add( s.concat(resolution) );
		}
		
		return new Resolution( appended );
	}
	
	public Resolution concat(Resolution other) {
		if ( other == Resolution.FAILURE ) {
			return Resolution.FAILURE;
		}
		
		if ( this.isEmpty() ) {
			return other;
		}
		
		if ( other.isEmpty() ) {
			return this;
		}
		
		Set<Substitution> appended = new HashSet<>( this.size() * other.size() );
		for ( final Substitution s1 : this ) {
			for ( final Substitution s2 : other ) {
				appended.add( s1.concat(s2) );
			}
		}
		
		return new Resolution( appended );
	}

	@Override
	public Iterator<Substitution> iterator() {
		return substitutions.iterator();
	}
	
}

package rule;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import core.Triple;

public class Rules {

	private final Set<Rule> rules;

	public Rules(Set<Rule> rules) {
		super();
		this.rules = rules;
	}
	
	public Rules(Rule...rules) {
		this( Sets.newHashSet(rules) );
	}
	
	public RuleTarget apply(RuleTarget target) {
		while ( true ) {
			Set<Triple> triples = applyOnce(target);

			if ( triples.isEmpty() ) {
				break;
			}
			
			target.addAll(triples);
		}
		
		return target;
	}
	
	private Set<Triple> applyOnce(RuleTarget target) {
		Set<Triple> triples = new HashSet<>();
		
		for ( final Rule r : rules) { 
			triples.addAll( r.apply(target) );
		}

		return triples;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
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
		Rules other = (Rules) obj;
		if (rules == null) {
			if (other.rules != null) {
				return false;
			}
		} else if (!rules.equals(other.rules)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return Joiner.on(System.lineSeparator()).join(rules);
	}
	
}

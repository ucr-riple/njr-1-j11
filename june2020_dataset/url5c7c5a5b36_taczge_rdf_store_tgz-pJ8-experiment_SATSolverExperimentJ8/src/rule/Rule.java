package rule;

import java.util.HashSet;
import java.util.Set;

import core.Triple;

public class Rule {
	
	private final Head head;
	private final Body body;

	public Rule(Head head, Body body) {
		super();
		
		// TODO: head <= body でないときのエラー処理
		this.head = head;
		this.body = body;
	}
	
	public Set<Triple> apply(RuleTarget target) {
		Set<Triple> triples = body.apply( head.solve(target) );

		return removeAll(triples, target); 
	}
	
	private Set<Triple> removeAll(Set<Triple> src, RuleTarget target) {
		Set<Triple> removed = new HashSet<>();
		
		for ( final Triple t : src) {
			if ( !target.contains(t) ) {
				removed.add(t);
			}
		}
		
		return removed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
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
		Rule other = (Rule) obj;
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		if (head == null) {
			if (other.head != null) {
				return false;
			}
		} else if (!head.equals(other.head)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return head + "=>" + body;
	}
	
}

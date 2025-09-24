package query;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;

import core.Resource;

public class Substitution {
	
	private Map<Variable, Resource> substitutions;

	public Substitution(Map<Variable, Resource> substitutions) {
		super();
		this.substitutions = substitutions;
	}
	
	public Substitution() {
		this( new HashMap<Variable, Resource>() );
	}
	
	public Substitution(Variable v, Resource c) {
		this();
		substitutions.put(v, c);		
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
		Substitution other = (Substitution) obj;
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
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(Joiner.on(",").withKeyValueSeparator("=>").join(substitutions));
		sb.append(")");
		
		return sb.toString();
	}

	public void put(Variable v, Resource c) {
		substitutions.put(v, c);
	}
	
	public boolean contains(Variable var) {
		return substitutions.containsKey(var);
	}
	
	public Resource getAssignedValue(Variable var) {
		return substitutions.get(var);
	}
	
	public int size() {
		return substitutions.size();
	}

	public Substitution concat(Substitution other) {
		Map<Variable,Resource> substitutions = new HashMap<>( this.size() + other.size() );
		
		substitutions.putAll(this.substitutions);
		substitutions.putAll(other.substitutions);
		
		return new Substitution(substitutions);
	}
	
}

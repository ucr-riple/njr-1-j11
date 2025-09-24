package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class XPX extends AbstractPrimitiveQuery<Variable,Resource,Variable> {

	public XPX(Variable s, Resource p, Variable o) {
		super(s, p, o);
	}

	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXPX(p) ) {
			substitutions.add( new Substitution(s, t.getSubject()) );
		}

		return Resolution.of(substitutions);
	}
	
}

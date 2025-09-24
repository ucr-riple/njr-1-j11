package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class SXX extends AbstractPrimitiveQuery<Resource,Variable,Variable> {

	public SXX(Resource s, Variable p, Variable o) {
		super(s, p, o);
	}

	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listSXX(s) ) {
			substitutions.add( new Substitution(p, t.getPredicate()) );
		}

		return Resolution.of(substitutions);
	}
	
}
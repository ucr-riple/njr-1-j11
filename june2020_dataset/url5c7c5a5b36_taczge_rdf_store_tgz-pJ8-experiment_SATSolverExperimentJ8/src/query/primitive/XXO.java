package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class XXO extends AbstractPrimitiveQuery<Variable,Variable,Resource> {
	
	public XXO(Variable s, Variable p, Resource o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXXO(o) ) {
			substitutions.add( new Substitution(s, t.getSubject()) );
		}

		return Resolution.of(substitutions);
	}

}
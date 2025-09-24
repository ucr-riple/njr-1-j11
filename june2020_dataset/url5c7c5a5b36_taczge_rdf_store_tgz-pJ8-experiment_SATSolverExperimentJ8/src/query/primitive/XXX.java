package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Triple;

public class XXX extends AbstractPrimitiveQuery<Variable,Variable,Variable> {

	public XXX(Variable s, Variable p, Variable o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXXX() ) {
			substitutions.add( new Substitution(s, t.getSubject()) );
		}

		return Resolution.of(substitutions);
	}

}
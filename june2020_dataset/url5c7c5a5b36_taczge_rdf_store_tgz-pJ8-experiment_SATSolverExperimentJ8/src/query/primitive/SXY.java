package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class SXY extends AbstractPrimitiveQuery<Resource,Variable,Variable> {

	public SXY(Resource s, Variable p, Variable o) {
		super(s, p, o);
	}

	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listSXY(s) ) {
			Substitution subs = new Substitution();
			subs.put(p, t.getPredicate());
			subs.put(o, t.getObject());

			substitutions.add(subs);
		}

		return Resolution.of(substitutions);
	}
	
}

package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class SXO extends AbstractPrimitiveQuery<Resource,Variable,Resource> {
	
	public SXO(Resource s, Variable p, Resource o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> res = new HashSet<>();

		for ( final Triple t : target.listSXO(s, o) ) {
			res.add( new Substitution(p, t.getPredicate()) ); 
		}

		return Resolution.of(res);
	}

}

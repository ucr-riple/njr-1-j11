package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class XPO extends AbstractPrimitiveQuery<Variable,Resource,Resource> {
	
	public XPO(Variable s, Resource p, Resource o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> res = new HashSet<>();

		for ( final Triple t : target.listXPO(p, o) ) {
			res.add( new Substitution(s, t.getSubject()) ); 
		}

		return Resolution.of(res);
	}

}

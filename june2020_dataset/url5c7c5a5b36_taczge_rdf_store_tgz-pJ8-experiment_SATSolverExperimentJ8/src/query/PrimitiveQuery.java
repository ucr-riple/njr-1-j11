package query;

import core.Triple;

public interface PrimitiveQuery {
	
	Resolution     solve(QueryTarget target);
	PrimitiveQuery apply(Substitution substitusion);
	Triple         toTriple();

}

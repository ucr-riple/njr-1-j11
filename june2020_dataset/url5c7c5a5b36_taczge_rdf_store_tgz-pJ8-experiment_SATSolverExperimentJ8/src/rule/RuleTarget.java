package rule;

import java.util.Collection;

import query.QueryTarget;
import core.Triple;

public interface RuleTarget extends QueryTarget {

	boolean contains(Triple t);
	void    addAll(Collection<Triple> ts);

}

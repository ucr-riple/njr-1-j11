package rule;

import java.util.Set;

import query.Resolution;
import core.Triple;

public interface Body {

	Set<Triple> apply(Resolution r);

}

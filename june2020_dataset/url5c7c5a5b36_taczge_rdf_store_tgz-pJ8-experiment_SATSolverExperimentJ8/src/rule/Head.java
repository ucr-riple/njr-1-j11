package rule;

import query.QueryTarget;
import query.Resolution;

public interface Head {

	Resolution solve(QueryTarget target);

}

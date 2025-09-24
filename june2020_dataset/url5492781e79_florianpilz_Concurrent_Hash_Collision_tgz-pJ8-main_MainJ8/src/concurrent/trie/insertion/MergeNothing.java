package concurrent.trie.insertion;

import cl.niclabs.skandium.muscles.Merge;

public class MergeNothing implements Merge<Object, Object> {

	@Override
	public Object merge(Object[] param) throws Exception {
		return new Object();
	}

}

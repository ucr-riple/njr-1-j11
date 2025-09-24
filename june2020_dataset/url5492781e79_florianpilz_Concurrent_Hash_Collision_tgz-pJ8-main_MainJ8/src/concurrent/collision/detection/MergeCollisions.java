package concurrent.collision.detection;

import cl.niclabs.skandium.muscles.Merge;
import data.structures.HashList;

public class MergeCollisions implements Merge<HashList, HashList> {

	@Override
	public HashList merge(HashList[] param) throws Exception {
		HashList result = new HashList();
		
		for (HashList list : param) {
			result.addAll(list);
		}
		
		return result;
	}

}

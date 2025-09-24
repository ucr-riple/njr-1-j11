package concurrent.hashing;

import cl.niclabs.skandium.muscles.Merge;
import data.structures.HashList;

public class MergeHashes implements Merge<HashList, HashList>{

	@Override
	public HashList merge(HashList[] param) throws Exception {
		HashList result = new HashList();
		
		for (HashList list : param) {
			result.addAll(list);
		}
		
		return result;
	}

}

package concurrent.trie.insertion;

import cl.niclabs.skandium.muscles.Split;
import data.structures.HashList;

public class SplitHashArray implements Split<HashList, HashList> {
	
	final int parts;
	
	public SplitHashArray(int parts) {
		this.parts = parts;
	}

	@Override
	public HashList[] split(HashList param) throws Exception {
		HashList[] result = new HashList[this.parts];
		for (int i = 0; i < this.parts; i++) {
			result[i] = new HashList();
		}
		
		int rest = (int) (this.parts - Math.ceil(param.size() / (double) this.parts) * this.parts + param.size());
		int contentLength = (int) (Math.ceil(param.size() / (double) this.parts) - 1);
		
		for (int i = 0; i < param.size() - rest; i++) {
			result[i / contentLength].add(param.get(i));
		}
		
		for (int i = 0; i < rest; i++) {
			result[i].add(param.get(i + param.size() - rest));
		}
		
		return result;
	}
}

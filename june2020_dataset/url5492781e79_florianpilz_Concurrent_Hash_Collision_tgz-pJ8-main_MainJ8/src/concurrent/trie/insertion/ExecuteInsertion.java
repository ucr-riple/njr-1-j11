package concurrent.trie.insertion;

import main.Main;
import cl.niclabs.skandium.muscles.Execute;
import data.structures.HashBitmapPair;
import data.structures.HashList;

public class ExecuteInsertion implements Execute<HashList, Object>{

	@Override
	public Object execute(HashList param) throws Exception {
		for (HashBitmapPair pair : param) {
			Main.hashmap.put(Main.byteToString(pair.getHash()), pair);
		}
		
		return new Object();
	}

}

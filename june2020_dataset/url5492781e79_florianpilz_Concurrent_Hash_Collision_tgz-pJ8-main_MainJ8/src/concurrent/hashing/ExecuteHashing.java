package concurrent.hashing;

import org.apache.commons.codec.digest.DigestUtils;

import cl.niclabs.skandium.muscles.Execute;
import data.structures.HashBitmapPair;
import data.structures.HashList;
import data.structures.SchemaInterface;

public class ExecuteHashing implements Execute<SchemaInterface, HashList> {
	
	final int power;
	
	public ExecuteHashing(int power) {
		this.power = power;
	}

	@Override
	public HashList execute(SchemaInterface param) throws Exception {
		HashList result = new HashList();
		
		for (Long bits : param) {
			final String s = param.getSentence(bits);
			byte[] bytes = new byte[this.power];
			byte[] digest = DigestUtils.sha256(s.getBytes("UTF8"));
			
			for (int i = 0; i < this.power; i++) {
				bytes[i] = digest[i];
			}
			
			result.add(new HashBitmapPair(bytes, bits));
		}

		return result;
	}

}

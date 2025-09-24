package concurrent.hashing;

import cl.niclabs.skandium.muscles.Split;
import data.structures.SchemaInterface;

public class SplitSchema implements Split<SchemaInterface, SchemaInterface> {
	
	final int parts;
	
	public SplitSchema(int parts) {
		this.parts = parts;
	}

	@Override
	public SchemaInterface[] split(SchemaInterface param) throws Exception {
		return param.split(this.parts);
	}

}

package vikram.demo.domain;

import org.bson.types.ObjectId;

public class Blog {

	public static final String ID = "_id";
	
	public ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
}

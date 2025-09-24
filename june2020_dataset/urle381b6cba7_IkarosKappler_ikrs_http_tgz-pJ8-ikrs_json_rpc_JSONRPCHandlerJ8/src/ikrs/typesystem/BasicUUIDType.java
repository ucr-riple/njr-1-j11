package ikrs.typesystem;

import java.util.UUID;

/**
 * @author Ikaros Kappler
 * @date 2012-04-23
 * @version 1.0.0
 **/


public class BasicUUIDType
    extends BasicTypeAdapter
    implements BasicType {
	
    private UUID uuid;
   
    public BasicUUIDType( UUID uuid ) {
	super( BasicType.TYPE_UUID );

	this.uuid = uuid;
    }

    public String getString() {

	return this.uuid.toString();

    }

    public UUID getUUID() {
	
	return this.uuid;
    }


    public String toString() {
	return this.uuid.toString();
    }

}
package agent.data;

/**
 * Deprecated. Use Utils.PartType instead. 
 * The master ArrayList is stored in FCS, and the 
 * initial ArrayList is stored in Constants.DEFAULT_PARTTYPES.
 */
@Deprecated
public enum PartType {
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	F("F"),
	G("G"),
	H("H");
	
	private final String name;       

    private PartType(String s) {
        name = s;
    }

    public String toString(){
       return name;
    }
}

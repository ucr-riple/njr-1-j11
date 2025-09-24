package hex;

public enum VaporEnum {
	NONE(0),
	SOURCE(1),
	SINK(2);	
	
	public final int id;
	
	VaporEnum(int i) {
		this.id = i;		
	}
	
	public static VaporEnum loadEnum(int i) {
		switch (i) {
		case 0: 
			return VaporEnum.NONE;			
		case 1:
			return VaporEnum.SOURCE;			
		case 2:
			return VaporEnum.SINK;			
		default:
			return VaporEnum.NONE;				
		}			
	}
	
}

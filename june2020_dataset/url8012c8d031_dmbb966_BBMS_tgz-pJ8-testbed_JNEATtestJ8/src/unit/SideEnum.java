package unit;

public enum SideEnum {
	
	// FRIENDLY, ENEMY, NEUTRAL
	FRIENDLY(0),
	ENEMY(1),
	NEUTRAL(2);
	
	// NOTE: Neutral side is not fully implemented.
	
	public final int id;	
	
	SideEnum(int i) {
		this.id = i;		
	}
}

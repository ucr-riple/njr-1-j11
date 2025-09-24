package Game;

public class History {
	protected byte actID;
	protected int objectID;
	protected int magnitude;
	
	//actIDs
	public static final int DISCOVERED = 0;
	public static final int CONQUERED = 1;
	public static final int PROPHETIZED = 2;
	public static final int HEALED = 3;
	public static final int UNITED = 4;
	public static final int FED = 5;
	public static final int OVERTHREW = 6;
	public static final int SLAYED = 7;
	public static final int PROSELYTIZED = 8;
	public static final int HADBUILT = 9;
	public static final int MASTERED = 10;
	public static final int DEFENDED = 11;
	public static final int GONEMAD = 12;
	public static final int PORTRAYED = 13;
	
	public byte getAct() {return actID;}
	public int getObject() {return objectID;}
	public int getMagnitude() {return magnitude;}
	public void addMagnitude() {magnitude++;}
	
	public String desc() {
		switch(actID) {
			case DISCOVERED: return "Visionary of ";
			case CONQUERED:
			case PROPHETIZED:
			case HEALED: return "Miraculously healed " + guyName(objectID);
			case UNITED:
			case FED: return "Selflessly provided sustenance for " + guyName(objectID);
			case OVERTHREW:
			case SLAYED: return "Slayer of " + guyName(objectID);
			case PROSELYTIZED:
			case HADBUILT:
			case MASTERED:
			case DEFENDED:
			case GONEMAD: return "Demented.";
			case PORTRAYED: return "Portrayed in " + magnitude + " arts in "; //+shirename
			default: return "";
		}
	}
	
	public String guyName(int id) {
		return AGPmain.TheRealm.getClan(id).getNomen();
	}
	public String jobName() {return "";}
	public String skillName() {return "";}
	
}

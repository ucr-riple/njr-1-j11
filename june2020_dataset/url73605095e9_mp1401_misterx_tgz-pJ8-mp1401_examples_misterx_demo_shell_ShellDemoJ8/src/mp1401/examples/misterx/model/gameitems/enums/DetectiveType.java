package mp1401.examples.misterx.model.gameitems.enums;

public enum DetectiveType {
	
	DEFAULT("<Unknown Detective>"),
	RED("Red"),
	GREEN("Green"),
	BLUE("Blue"),
	YELLOW("Yellow");
	
	private String typeAsString;
	
	private DetectiveType(String type) {
		this.typeAsString = type;
	}
	
	public String toString() {
		return typeAsString;
	}

}

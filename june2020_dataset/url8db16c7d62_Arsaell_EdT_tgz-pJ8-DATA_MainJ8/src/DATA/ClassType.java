package DATA;

import java.io.Serializable;

public class ClassType extends Constrainable implements Serializable {
	
	private String name, shortName;
	private Slot duration;
	
	public ClassType(String aName, String aShortName, Slot aDuration)	{
		
		this.name = aName;
		this.shortName = aShortName;
		this.duration = aDuration;
	}

	public String getName()	{
		return this.name;
	}
	
	public String getShortName()	{
		return this.shortName;
	}
	
	public Slot getDuration() {
		return duration;
	}

	public String toString()	{
		return  "[" + this.shortName + "] " + this.name;
	}
}

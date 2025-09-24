package DATA;

import java.awt.Point;
import java.io.Serializable;

public class Classroom extends Timeable implements Serializable{

	private Point coords;
	private ClassType type;
	private String name;
	private int effectif;
	public Classroom(ClassType aType, String aName, int aEff, Point aCoords)	{
	
		super();
		this.type = aType;
		this.name = aName;
		this.effectif = aEff;
		this.coords = aCoords;
	}
	
	public ClassType getType()	{
		return this.type;
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
	
 	public String toString()	{
		return (this.type.getShortName() + " " + this.name);
	}

	public String getName() {
		return this.name;
	}
	
	public Point getCoords()	{
		return this.coords;
	}
}

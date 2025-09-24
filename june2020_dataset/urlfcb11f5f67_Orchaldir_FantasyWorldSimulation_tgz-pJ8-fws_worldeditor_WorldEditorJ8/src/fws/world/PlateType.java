package fws.world;

import fws.utility.Color;

public class PlateType
{
	private String name_;
	private float elevation_;
	private Color color_;
	
	public PlateType(String name, float elevation, Color color)
	{
		name_ = name;
		setElevation(elevation);
		color_ = color;
	}

	public String getName()
	{
		return name_;
	}
	
	public float getElevation()
	{
		return elevation_;
	}

	public final void setElevation(float elevation)
	{
		elevation_ = Math.min(Math.max(elevation, 0.0f), 1.0f);
	}
	
	public Color getColor()
	{
		return color_;
	}
}

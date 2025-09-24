package fws.utility;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Color
{
	private float red_;
	private float green_;
	private float blue_;
	
	public Color(float red, float green, float blue)
	{
		setRed(red);
		setGreen(green);
		setBlue(blue);
	}

	public float getRed()
	{
		return red_;
	}

	public final void setRed(float red)
	{
		red_ = Math.min(Math.max(red, 0.0f), 1.0f);
	}

	public float getGreen()
	{
		return green_;
	}

	public final void setGreen(float green)
	{
		green_ = Math.min(Math.max(green, 0.0f), 1.0f);
	}

	public float getBlue()
	{
		return blue_;
	}

	public final void setBlue(float blue)
	{
		blue_ = Math.min(Math.max(blue, 0.0f), 1.0f);
	}
	
	public void apply()
	{
		glColor3f(red_, green_, blue_);
	}
}

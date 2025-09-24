package jatrace.gui.builders;

import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorBuilder extends JPanel
{
	
	private DoubleBuilder redField, greenField, blueField;
	private jatrace.Color color;
	
	public jatrace.Color build()
	{
		
		double red, green, blue;
		
		//get red value
		try
		{
			red = redField.getValue(256.0);
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			redField.requestFocus();
			return null;
		}
		
		//get green value
		try
		{
			green = greenField.getValue(256.0);
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			greenField.requestFocus();
			return null;
		}
		
		//get blue value
		try
		{
			blue = blueField.getValue(256.0);
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			blueField.requestFocus();
			return null;
		}
		
		return color.setRGB(red, green, blue);
		
	}
	
	public ColorBuilder()
	{
		super( new GridLayout(1,3,5,0) );
		
		setPreferredSize( new Dimension(0,25) );
		
		color = new jatrace.Color();
		
		buildFromColor(color);
		
		setOpaque(true);
	}
	
	protected void buildFromColor(jatrace.Color v)
	{
		
		if (blueField != null) remove(blueField);
		if (greenField != null) remove(greenField);
		if (redField != null) remove(redField);
		
		redField = new DoubleBuilder("r:",v.getRed());
		redField.setLocation(0,0);
		add(redField);

		greenField = new DoubleBuilder("g:",v.getGreen());
		greenField.setLocation(1,0);
		add(greenField);
		
		blueField = new DoubleBuilder("b:",v.getBlue());
		blueField.setLocation(2,0);
		add(blueField);
		
		revalidate();
		
	}
	
}

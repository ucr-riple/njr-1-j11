package jatrace.gui.builders;

import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VectorBuilder extends JPanel// implements FocusListener
{
	
	//private JTextField xField, yField, zField;
	private DoubleBuilder xField, yField, zField;
	private jatrace.Vector vector;
	
	public jatrace.Vector build()
	{
		
		double x,y,z;
		
		//get X value
		try
		{
			x = xField.getValue();
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			xField.requestFocus();
			return null;
		}
		
		//get Y value
		try
		{
			y = yField.getValue();
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			yField.requestFocus();
			return null;
		}
		
		//get Z value
		try
		{
			z = zField.getValue();
		}
		
		catch (NumberFormatException e)
		{
			System.out.println("couldn't read input");
			zField.requestFocus();
			return null;
		}
		
		return vector.setxyz(x,y,z);
		
	}
	
	public VectorBuilder()
	{
		super(new GridLayout(1,3,5,0) );
		
		setPreferredSize( new Dimension(0,25) );
		
		vector = new jatrace.Vector(0.0,0.0,0.0);
		
		buildFromVector(vector);
		
		setOpaque(true);
	}
	
	protected void buildFromVector(jatrace.Vector v)
	{
		
		if (zField != null) remove(zField);
		if (yField != null) remove(yField);
		if (xField != null) remove(xField);
		
		xField = new DoubleBuilder("x:",v.getX());
		xField.setLocation(0,0);
		add(xField);

		yField = new DoubleBuilder("y:",v.getY());
		yField.setLocation(1,0);
		add(yField);
		
		zField = new DoubleBuilder("z:",v.getZ());
		zField.setLocation(2,0);
		add(zField);
		
		revalidate();
		
	}
	
}

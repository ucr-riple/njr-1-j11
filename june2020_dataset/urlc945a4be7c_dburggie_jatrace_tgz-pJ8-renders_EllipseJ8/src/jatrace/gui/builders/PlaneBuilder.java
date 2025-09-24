package jatrace.gui.builders;


import jatrace.*;
import jatrace.gui.*;
import jatrace.bodies.Plane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlaneBuilder extends ScrollableGridPanel
{
	
	VectorBuilder position;
	VectorBuilder normal;
	ColorBuilder color;
	DoubleBuilder reflectivity;
	DoubleBuilder specularity;
	BooleanBuilder matte;
	
	Plane plane;
	
	public PlaneBuilder()
	{
		super();
		
		plane = new Plane();
		
		addLabel("Position:");
		position = new VectorBuilder();
		add(position);
		
		addLabel("Normal:");
		normal = new VectorBuilder();
		normal.buildFromVector(new jatrace.Vector(0.0,1.0,0.0));
		add(normal);
		
		addLabel("Color:");
		color = new ColorBuilder();
		add(color);
		
		reflectivity = new DoubleBuilder("Reflectivity:",0.3);
		add(reflectivity);
		
		specularity = new DoubleBuilder("Specularity:",10.0);
		add(specularity);
		
		matte = new BooleanBuilder("Matte:", false);
		add(matte);
		
		setOpaque(true);
		
	}
	
	public Plane build()
	{
		
		jatrace.Vector p = position.build();
		if (p == null) return null;
		
		jatrace.Vector n = normal.build();
		if (n == null) return null;
		
		jatrace.Color c = color.build();
		if (c == null) return null;
		
		double ref, spc;
		
		try {
			ref = reflectivity.getValue();
			spc = specularity.getValue();
		}
		
		catch (NumberFormatException e)
		{
			return null;
		}
		
		boolean m = matte.getTrueFalse();
		
		plane.setPosition(p);
		plane.setNormal(n);
		plane.setColor(c);
		plane.setReflectivity(ref);
		plane.setSpecularity(spc);
		plane.setMatte(m);
		
		return plane;
		
	}
	
	private void addLabel(String text)
	{
		
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setPreferredSize( new Dimension(0,25) );
		add(label);
		
	}
	
	
}

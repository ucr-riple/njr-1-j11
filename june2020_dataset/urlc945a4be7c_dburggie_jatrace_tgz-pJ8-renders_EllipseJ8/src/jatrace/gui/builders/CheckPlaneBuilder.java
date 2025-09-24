package jatrace.gui.builders;


import jatrace.*;
import jatrace.gui.*;
import jatrace.bodies.CheckPlane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckPlaneBuilder extends ScrollableGridPanel
{
	
	VectorBuilder position;
	VectorBuilder normal;
        VectorBuilder orientation;
	ColorBuilder color1, color2;
	DoubleBuilder reflectivity;
	DoubleBuilder specularity;
	BooleanBuilder matte;
	
	CheckPlane plane;
	
	public CheckPlaneBuilder()
	{
		super();
		
                jatrace.Vector p_pos = new jatrace.Vector(0.0,0.0,0.0),
                        p_nor = new jatrace.Vector(0.0,1.0,0.0),
                        p_ori = new jatrace.Vector(1.0,0.0,0.0);
                
                jatrace.Color p_color1 = new jatrace.Color(0.01,0.01,0.01),
                        p_color2 = new jatrace.Color(0.99,0.99,0.99);
                
		plane = new CheckPlane(p_pos, p_nor, p_ori);
                plane.setColor(p_color1, p_color2);
		
		addLabel("Position:");
                position = new VectorBuilder();
                position.buildFromVector(p_pos);
		add(position);
		
		addLabel("Normal:");
		normal = new VectorBuilder();
		normal.buildFromVector(p_nor);
		add(normal);
                
                addLabel("Orientation:");
                orientation = new VectorBuilder();
                orientation.buildFromVector(p_ori);
                add(orientation);
		
		addLabel("Color 1:");
		color1 = new ColorBuilder();
                color1.buildFromColor(p_color1);
                add(color1);
                
                addLabel("Color 2");
                color2 = new ColorBuilder();
                color2.buildFromColor(p_color2);
                add(color2);
		
		reflectivity = new DoubleBuilder("Reflectivity:",0.3);
		add(reflectivity);
		
		specularity = new DoubleBuilder("Specularity:",10.0);
		add(specularity);
		
		matte = new BooleanBuilder("Matte:", false);
		add(matte);
		
		setOpaque(true);
		
	}
	
	public CheckPlane build()
	{
		
		jatrace.Vector p = position.build();
		if (p == null) return null;
		
		jatrace.Vector n = normal.build();
		if (n == null) return null;
                
                jatrace.Vector o = orientation.build();
                if (o == null) return null;
		
		jatrace.Color c1 = color1.build();
		if (c1 == null) return null;
                
		jatrace.Color c2 = color2.build();
		if (c2 == null) return null;
                
                
		
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
                plane.setOrientation(o);
		plane.setColor(c1,c2);
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

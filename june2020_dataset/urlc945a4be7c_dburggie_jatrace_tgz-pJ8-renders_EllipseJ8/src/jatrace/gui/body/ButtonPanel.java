package jatrace.gui.body;

import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonPanel extends ScrollableGridPanel
{
	
	private static Dimension preferredSize = new Dimension(200,0);
	
	BodyButton head = null;
	
	public ButtonPanel(BodyButton b)
	{
		super();
		head = b;
		while (b != null)
		{
			add(b);
			b = b.getNext();
		}
	}
	
	public void add(BodyButton b)
	{
		//super.add(Box.createRigidArea( new Dimension(0,25) ) );
		super.add(b);
	}
	
}

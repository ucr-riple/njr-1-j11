package jatrace.gui.builders;

import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BooleanBuilder extends JPanel implements ItemListener
{
	
	//private JLabel label;
	private JCheckBox checkbox;
	private boolean selected;
	
	public BooleanBuilder(String descriptor, boolean defaultCheck)
	{
		//super(new GridLayout(1,2));
		super(new BorderLayout());
		
		setPreferredSize( new Dimension(0,25) );
		
		//label = new JLabel(descriptor, JLabel.CENTER);
		//add(label);
		
		checkbox = new JCheckBox(descriptor);
		checkbox.setSelected(defaultCheck);
		selected = defaultCheck;
		add(checkbox, BorderLayout.LINE_START);
	}
	
	public boolean getTrueFalse()
	{
		return selected;
	}
	
	public void setSelected(boolean b)
	{
		selected = b;
		checkbox.setSelected(b);
	}
	
	public void setText(String t)
	{
		//label.setText(t);
		checkbox.setText(t);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		if (e.getSource() == checkbox)
		{
			
			if (e.getStateChange() == ItemEvent.DESELECTED)
			{
				selected = false;
			}
			
			else
			{
				selected = true;
			}
			
			setSelected(selected);
			
		}//end if
	
	}//end itemStateChanged()
	
}

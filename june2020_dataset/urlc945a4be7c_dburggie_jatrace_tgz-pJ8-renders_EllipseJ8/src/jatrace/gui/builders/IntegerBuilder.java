package jatrace.gui.builders;

import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntegerBuilder extends JPanel implements FocusListener
{
	
	JLabel label;
	JTextField field;
	
	public IntegerBuilder(String name, int value)
	{
		//super(new GridLayout(1,2));
		super();
		setPreferredSize(new Dimension(0,25));
		
		label = new JLabel(name, JLabel.CENTER);
		add(label);
		
		field = new JTextField( Integer.toString(value), 5 );
		field.addFocusListener(this);
		add(field);
	}
	
	public int getValue()
			throws NumberFormatException
	{
		
		String input = field.getText();
		int i = 0;
		try 
		{
			i = Integer.decode(input);
		}
		
		catch (NumberFormatException e)
		{
			String l;
			l = "couldn't decode " + input + " as double, attempting as int";
			System.out.println(l);
			i = (int) Double.parseDouble(input);
		}
		
		return i;
		
	}
	
	public void setValue(int value)
	{
		field.setText( Integer.toString(value) );
	}
	
	public void requestFocus()
	{
		field.requestFocus();
	}
	
	// Focus Listener Interface Below
	
	public void focusGained(FocusEvent e)
	{
		Component c = e.getComponent();
		if (c instanceof JTextField)
		{
			((JTextField)c).selectAll();
		}
	}
	
	public void focusLost(FocusEvent e) { }
	
	
}

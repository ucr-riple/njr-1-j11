package jatrace.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyGridPanel extends JPanel
{
	
	public MyGridPanel()
	{
		super( new GridLayout(0,1,0,5) );
		setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
	}
	
}

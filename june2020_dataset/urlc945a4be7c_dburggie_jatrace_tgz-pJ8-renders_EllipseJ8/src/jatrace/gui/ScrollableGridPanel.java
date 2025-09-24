package jatrace.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScrollableGridPanel extends JPanel implements Scrollable
{
	
	private static Dimension preferredSize = new Dimension(200,0);
	
	public ScrollableGridPanel()
	{
		
		super( new GridLayout(0,1,0,5) );
		
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
	}
	
	@Override
	public boolean getScrollableTracksViewportHeight() { return false; }
	@Override
	public boolean getScrollableTracksViewportWidth() { return true; }
	@Override
	public Dimension getPreferredScrollableViewportSize()
	{
		return preferredSize;
	}
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, 
			int orientation, int direction)
	{
		return 30;
	}
	
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction)
	{
		return 30;
	}
}

package jatrace.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel
{
	
	private int xpos, ypos, width, height;
	
	public static MyPanel byHeightWidth(int w, int h)
	{
		return new MyPanel(0,0,w,h);
	}
	
	public MyPanel(int x, int y, int w, int h)
	{
		super();
		this.setLocation(x,y);
		this.setSize(w,h);
		this.setLayout(null);
		nextInChain = null; prevInChain = null;
	}
	
	@Override
	public void setLocation( int x, int y )
	{
		super.setLocation(x,y);
		xpos = x; ypos = y;
	}
	
	@Override
	public void setSize( int w, int h )
	{
		super.setSize(w,h);
		width = w; height = h;
	}
	
	public int getXposition() { return xpos; }
	public int getYposition() { return ypos; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	private MyPanel nextInChain;
	private MyPanel prevInChain;
	
	/** Get the next object in the chain. */
	public MyPanel nextLink()
	{
		return nextInChain;
	}
	
	/** Get the previous object in the chain. */
	public MyPanel prevLink()
	{
		return prevInChain;
	}
	
	/** Insert a new link before this one. */
	public void insertBefore(MyPanel obj)
	{
		if (prevInChain != obj)
		{
			prevInChain = obj;
			obj.insertAfter(this);
		}
	}
	
	/** Insert a new link after this one. */
	public void insertAfter(MyPanel obj)
	{
		if (nextInChain != obj)
		{
			nextInChain = obj;
			obj.insertBefore(this);
		}
	}
}

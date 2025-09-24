package fr.noxx90.jflam.model;

import java.awt.Color;

public interface Histogram
{
	public int getFrequency(int x, int y);
	public int getFrequencyMax();
	public Color getColor(int x, int y);
	
	public void putColor(int x, int y, Color c);
	
	public int getWidth();
	public int getHeight();
}

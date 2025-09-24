package fr.noxx90.jflam.model.impl;

import java.awt.Color;

import fr.noxx90.jflam.model.Histogram;

import static com.esotericsoftware.minlog.Log.*;

public class InMemoryHistogram implements Histogram
{
	protected int width;
	protected int height;
	protected int frequencyMax;
	protected int[][] frequencies;
	protected Color[][] colors;
	
	public InMemoryHistogram(int finalW, int finalH) {
		
		width = finalW;
		height = finalH;
		info("DefaultHistogram", "Init Histogram for width=" + finalW + ", height=" + finalH);
		
		frequencies = new int[width][height];
		colors = new Color[width][height];
	}
	
	public int getFrequency(int histogramX, int histogramY) {
		return frequencies[histogramX][histogramY];
	}
	
	public Color getColor(int histogramX, int histogramY) {
		Color c = colors[histogramX][histogramY];
		return c == null ? Color.black : c;
	}
	
	public synchronized void putColor(int x, int y, Color c) {
		frequencies[x][y]++;
		if(frequencies[x][y] > frequencyMax) {
			frequencyMax = frequencies[x][y];
		}
		colors[x][y] = c;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getFrequencyMax() {
		return frequencyMax;
	}
}

package fr.noxx90.jflam.renderer.impl;

import static com.esotericsoftware.minlog.Log.*;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.google.common.collect.RangeMap;

import fr.noxx90.jflam.model.Form;
import fr.noxx90.jflam.model.Histogram;

public class RendererTask implements Runnable
{
	protected RangeMap<Float, Form> rangeMap;
	protected float weightSum;
	protected float ratio;
	protected float marginTop;
	protected float marginLeft;
	protected Histogram histogram;
	protected int points;
	protected int width;
	protected int height;
	
	public RendererTask(RangeMap<Float, Form> rangeMap, float weightSum, float ratio, float marginTop, float marginLeft, Histogram histogram, int points, int width, int height) {
		this.rangeMap = rangeMap;
		this.weightSum = weightSum;
		this.ratio = ratio;
		this.marginTop = marginTop;
		this.marginLeft = marginLeft;
		this.histogram = histogram;
		this.points = points;
		this.width = width;
		this.height = height;
	}

	public void run() {
		for(int i = 0;i < points;i++) {
			Point2D.Float p = new Point2D.Float(rand(), rand());
			debug(i + "", p.x + ", " + p.y);
			for(int j = 0;j < 40;j++) {
				Form form = chooseFormByWeight();
				Color c = form.getColor();
				form.compute(p);
				debug(i + ", " + j, "----------------------------------------");
				debug(i + ", " + j, form.getName() + ": " + p.x + ", " + p.y);
				if(j >= 20) {
					int x = (int) (p.x * ratio + marginLeft);
					int y = (int) (-p.y * ratio + marginTop);
					
					if(x >= 0 && y >= 0 && x < width && y < height) {
						Color before = histogram.getColor(x, y);
						
						if(DEBUG) {
							debug(i + ", " + j, x + ", " + y + "  f=" + histogram.getFrequency(x, y));
							
							debug(i + ", " + j, printColor(before) + " + " + printColor(c));
						}
						
						Color after = blendColor(before, c);
						histogram.putColor(x, y, after);
						
						if(DEBUG) {
							debug(i + ", " + j, "==> f=" + histogram.getFrequency(x, y) + "  " + printColor(after));
						}
					}
				}
			}
		}
	}

	protected Form chooseFormByWeight() {
		float target = (float) (Math.random() * weightSum);
		Form chosenOne = rangeMap.get(target);
		
		if(chosenOne == null) {
			System.out.println(target);
		}
		
		return chosenOne;
	}
	
	protected Color blendColor(Color c1, Color c2) {
		if(c1 != null && c2 != null) {
			return new Color((c1.getRed() + c2.getRed()) / 2, (c1.getGreen() + c2.getGreen()) / 2, (c1.getBlue() + c2.getBlue()) / 2);			
		} else if(c1 != null) {
			return c1;
		} else if(c2 != null) {
			return c2;
		} else {
			return Color.black;
		}
	}
	
	protected static float rand() {
		return new Double(2 * Math.random() - 1).floatValue();
	}
	
	protected String printColor(Color c) {
		return "<" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ">";
	}
}

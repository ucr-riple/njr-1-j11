package fr.noxx90.jflam.renderer.impl;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Iterator;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Form;
import fr.noxx90.jflam.model.Histogram;
import fr.noxx90.jflam.model.impl.InMemoryHistogram;
import fr.noxx90.jflam.renderer.HistogramRenderer;

import static com.esotericsoftware.minlog.Log.*;

public class DefaultHistogramRenderer extends HistogramRenderer
{
	protected RangeMap<Float, Form> rangeMap;
	protected float weightSum;
	protected float ratio;
	protected float marginTop;
	protected float marginLeft;
	
	@Override
	public Histogram render(Flame flame, int width, int height, int quality, int sampling) {
		int progress = 0;
		
		info("DefaultHistogramRenderer", "first creating histogram with : width=" + width + ", height=" + height + ", quality=" + quality + ", sampling=" + sampling);
		initializeRangeMap(flame);
		
		Histogram histogram = new InMemoryHistogram(width, height);
		initializeProjection(histogram);
		
		int points = Math.round((float) quality / 100 * width * height);
		info("DefaultHistogramRenderer", "number of random points : " + points);
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
			
			int pg = i * 100 / points;
			if(pg > progress) {
				progress = pg;
				if(progress % 5 == 0) {
					info("DefaultHistogramRenderer", progress + "%");					
				}
			}
		}
		
		return histogram;
	}
	
	protected static float rand() {
		return new Double(2 * Math.random() - 1).floatValue();
	}
	
	protected Color randomColor() {
		return new Color((int) (Math.random()*256), (int) (Math.random()*256), (int) (Math.random()*256));
	}
	
	protected String printColor(Color c) {
		return "<" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ">";
	}
	
	protected void initializeRangeMap(Flame flame) {
		info("DefaultHistogramRenderer", "Initialize RangeMap for form selection");
		rangeMap = TreeRangeMap.create();
		float inf = 0, sup = 0;
		Iterator<Form> it = flame.getForms().values().iterator();
		while(it.hasNext()) {
			Form form = it.next();
			inf = sup;
			sup += form.getWeight();
			
			info("DefaultHistogramRenderer", "Found form with weight=" + form.getWeight());
			
			if(it.hasNext()) {
				rangeMap.put(Range.closedOpen(inf, sup), form);
				info("DefaultHistogramRenderer", "-> (" + inf + ", " + sup + ")");
			} else {
				rangeMap.put(Range.closed(inf, sup), form);
				info("DefaultHistogramRenderer", "-> (" + inf + ", " + sup + ")");
			}
		}
		
		weightSum = sup;
	}
	
	protected void initializeProjection(Histogram histogram) {
		info("DefaultHistogramRenderer", "Initialize projection parameters");
		float limit = Math.min(histogram.getWidth(), histogram.getHeight());
		ratio = limit / 2;
		marginTop = histogram.getHeight() / 2;
		marginLeft = histogram.getWidth() / 2;
		info("DefaultHistogramRenderer", "ratio=" + ratio + ", marginTop=" + marginTop + ", marginLeft=" + marginLeft);
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
}

package net.slreynolds.ds.export;

import java.awt.Color;
import java.util.List;



public abstract class AbstractExporter implements Exporter {

    private Color[] colors = new Color[] { new Color(252,167,144),
        	new Color(238,252,144),  new Color(211,144,252), new Color(144, 238, 252), 
        	new Color(144, 148, 252), new  Color(160,252, 144) };
    
    public void setColors(List<Color> cs) {
    	if (cs == null || cs.size() <= 0) {
    		throw new IllegalArgumentException("Colors list cannot be null or empty");
    	}
    	colors = cs.toArray(new Color[cs.size()]);
    }
	
    protected Color getColor(int generation) {
    	if (generation > colors.length) {
    		throw new IllegalStateException("Generation " + generation + " exceeds number of colors " + colors.length);
    	}
    	return colors[generation];
    }
    

}

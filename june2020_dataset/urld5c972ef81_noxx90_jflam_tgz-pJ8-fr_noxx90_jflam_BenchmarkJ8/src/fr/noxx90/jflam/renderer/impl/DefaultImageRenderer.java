package fr.noxx90.jflam.renderer.impl;

import static com.esotericsoftware.minlog.Log.debug;
import static com.esotericsoftware.minlog.Log.info;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.noxx90.jflam.model.Histogram;
import fr.noxx90.jflam.renderer.ImageRenderer;

public class DefaultImageRenderer extends ImageRenderer
{

	@Override
	public BufferedImage render(Histogram histogram) {
		info("DefaultImageRenderer", "now rendering image");
		int frequencyMax = histogram.getFrequencyMax();
		info("DefaultImageRenderer", "freqMax=" + frequencyMax);
		
		BufferedImage image = new BufferedImage(histogram.getWidth(), histogram.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		
		debug("DefaultImageRenderer", "------------ RENDERING ------------");
		for(int x = 0;x < histogram.getWidth();x++) {
			for(int y = 0;y < histogram.getHeight();y++) {
				
				Color before = histogram.getColor(x, y);
				float mult = histogram.getFrequency(x, y);
				mult /= frequencyMax;
				Color after = getFinalColor(before, mult);
				
				g.setColor(after);
				g.fillRect(x, y, 1, 1);
			}
		}
		
		/*
		try {
			ImageIO.write(image, "png", new File("out.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		return image;
	}
	
	protected Color getFinalColor(Color before, float mult) {
		
		float red = before.getRed(), green = before.getGreen(), blue = before.getBlue();
		red *= mult;
		green *= mult;
		blue *= mult;
		
		int r = Math.max(0, Math.min(255, Math.round(red)));
		int g = Math.max(0, Math.min(255, Math.round(green)));
		int b = Math.max(0, Math.min(255, Math.round(blue)));
		
		return new Color(r, g, b);
	}
}

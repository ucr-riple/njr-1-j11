package hex;

import java.awt.image.RGBImageFilter;

// From http://www.dmc.fmph.uniba.sk/public_html/doc/Java/ch25.htm
public class BrightnessFilter extends RGBImageFilter {
	int brightness;
	
	public BrightnessFilter(int b) {
		brightness = b;
		canFilterIndexColorModel = true;
	}	

	@Override
	public int filterRGB(int x, int y, int rgb) {
		// Get the individual colors
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = (rgb >> 0) & 0xff;
		
		// Calculate the brightness
		r += (brightness * r) / 100;
		g += (brightness * g) / 100;
		b += (brightness * b) / 100;
		
		// Check boundaries
		r = Math.min(Math.max(0, r), 255);
		g = Math.min(Math.max(0, g), 255);
		b = Math.min(Math.max(0, b), 255);
		
		return (rgb & 0xff000000) | (r << 16) | (g << 8) | (b << 0);
	}
}

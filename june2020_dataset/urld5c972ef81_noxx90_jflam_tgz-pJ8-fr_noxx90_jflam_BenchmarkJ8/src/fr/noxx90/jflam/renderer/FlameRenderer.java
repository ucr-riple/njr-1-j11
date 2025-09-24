package fr.noxx90.jflam.renderer;

import java.awt.image.BufferedImage;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Histogram;

import static com.esotericsoftware.minlog.Log.*;

public class FlameRenderer {
	
	protected HistogramRenderer histogramRenderer;
	protected ImageRenderer imageRenderer;

	public FlameRenderer(HistogramRenderer histogramRenderer, ImageRenderer imageRenderer) {
		this.histogramRenderer = histogramRenderer;
		this.imageRenderer = imageRenderer;
	}

	public HistogramRenderer getHistogramRenderer() {
		return histogramRenderer;
	}

	public ImageRenderer getImageRenderer() {
		return imageRenderer;
	}

	public BufferedImage render(Flame flame, int width, int height, int quality, int sampling) {
		info("FlameRenderer", "rendering flame with : width=" + width + ", height=" + height + ", quality=" + quality + ", sampling=" + sampling);
		Histogram histogram = histogramRenderer.render(flame, width, height, quality, sampling);
		return imageRenderer.render(histogram);
	}
}

package fr.noxx90.jflam.renderer.impl;

import static com.esotericsoftware.minlog.Log.info;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Histogram;
import fr.noxx90.jflam.model.impl.InMemoryHistogram;

public class ThreadPooledHistogramRenderer extends DefaultHistogramRenderer
{
	protected ThreadPoolExecutor executor;
	
	public ThreadPooledHistogramRenderer(int maxPoolSize) {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
		executor = new ThreadPoolExecutor(maxPoolSize, maxPoolSize, 5, TimeUnit.SECONDS, queue);
	}
	
	@Override
	public Histogram render(Flame flame, int width, int height, int quality, int sampling) {
		info("DefaultHistogramRenderer", "first creating histogram with : width=" + width + ", height=" + height + ", quality=" + quality + ", sampling=" + sampling);
		initializeRangeMap(flame);
		
		Histogram histogram = new InMemoryHistogram(width, height);
		initializeProjection(histogram);
		
		int points = Math.round((float) quality / 100 * width * height);
		info("DefaultHistogramRenderer", "number of random points : " + points);
		
		for(int i = 0;i < 100;i++) {
			RendererTask task = new RendererTask(rangeMap, weightSum, ratio, marginTop, marginLeft, histogram, points/100, width, height);
			executor.execute(task);
		}
		
		try {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return histogram;
	}
	
}

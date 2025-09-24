package fr.noxx90.jflam;

import java.awt.Color;
import java.awt.image.BufferedImage;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Form;
import fr.noxx90.jflam.renderer.FlameRenderer;
import fr.noxx90.jflam.renderer.HistogramRenderer;
import fr.noxx90.jflam.renderer.ImageRenderer;
import fr.noxx90.jflam.renderer.impl.DefaultHistogramRenderer;
import fr.noxx90.jflam.renderer.impl.DefaultImageRenderer;
import fr.noxx90.jflam.renderer.impl.ThreadPooledHistogramRenderer;

import static com.esotericsoftware.minlog.Log.*;

public class Benchmark
{
	protected int width;
	protected int height;
	protected int quality;
	protected int sampling;
	protected Flame flame;
	
	public Benchmark() {
		width = 5760;
		height = 3240;
		quality = 5;
		sampling = 1;
		
		flame = new Flame();
		
		Form f0 = new Form(0.5f, 0, 0, -0.8f, 0.5f, 0, 1, Color.blue);
		f0.add(Functions.LINEAR, 1);
		flame.add("F0", f0);
		
		Form f1 = new Form(0.5f, 0.4f, 0.5f, 0, 0.5f, 0, 1, Color.yellow);
		f1.add(Functions.LINEAR, 1);
		flame.add("F1", f1);
		
		Form f2 = new Form(0.5f, 0.2f, 0, 0, 0.5f, 0.5f, 1, Color.red);
		f2.add(Functions.LINEAR, 1);
		flame.add("F2", f2);
	}
	
	public void method1() {
		long a = System.currentTimeMillis();
		HistogramRenderer histogramRenderer = new DefaultHistogramRenderer();
		ImageRenderer imageRenderer = new DefaultImageRenderer();
		FlameRenderer flameRenderer = new FlameRenderer(histogramRenderer, imageRenderer);
		BufferedImage image = flame.render(flameRenderer, width, height, quality, sampling);
		long b = System.currentTimeMillis();
		
		if(checkResult(image)) {
			int time = (int) ((b-a)/1000);
			info("Benchmark", time + "s");
		}
	}
	
	public void method2() {
		long a = System.currentTimeMillis();
		HistogramRenderer histogramRenderer = new ThreadPooledHistogramRenderer(4);
		ImageRenderer imageRenderer = new DefaultImageRenderer();
		FlameRenderer flameRenderer = new FlameRenderer(histogramRenderer, imageRenderer);
		BufferedImage image = flame.render(flameRenderer, width, height, quality, sampling);
		long b = System.currentTimeMillis();
		
		if(checkResult(image)) {
			int time = (int) ((b-a)/1000);
			info("Benchmark", time + "s");
		}
	}
	
	public void method3() {
		long a = System.currentTimeMillis();
		HistogramRenderer histogramRenderer = new ThreadPooledHistogramRenderer(10);
		ImageRenderer imageRenderer = new DefaultImageRenderer();
		FlameRenderer flameRenderer = new FlameRenderer(histogramRenderer, imageRenderer);
		BufferedImage image = flame.render(flameRenderer, width, height, quality, sampling);
		long b = System.currentTimeMillis();
		
		if(checkResult(image)) {
			int time = (int) ((b-a)/1000);
			info("Benchmark", time + "s");
		}
	}
	
	protected boolean checkResult(BufferedImage image) {
		if(image.getWidth() != width || image.getHeight() != height) {
			return false;
		}
		
		for(int x = 0;x < width;x++) {
			for(int y = 0;y < height;y++) {
				if(!new Color(image.getRGB(x, y)).equals(Color.black)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		INFO();
		setLogger(new FlameLogger());
		Benchmark benchmark = new Benchmark();
		benchmark.method1();
		benchmark.method2();
		benchmark.method3();
	}
}

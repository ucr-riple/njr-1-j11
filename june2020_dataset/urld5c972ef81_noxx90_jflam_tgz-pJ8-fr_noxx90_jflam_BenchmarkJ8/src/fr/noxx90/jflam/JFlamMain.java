package fr.noxx90.jflam;

import static com.esotericsoftware.minlog.Log.INFO;
import static com.esotericsoftware.minlog.Log.setLogger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Form;
import fr.noxx90.jflam.model.Triangle;
import fr.noxx90.jflam.renderer.FlameRenderer;
import fr.noxx90.jflam.renderer.HistogramRenderer;
import fr.noxx90.jflam.renderer.ImageRenderer;
import fr.noxx90.jflam.renderer.impl.DefaultImageRenderer;
import fr.noxx90.jflam.renderer.impl.ThreadPooledHistogramRenderer;

public class JFlamMain
{
	public static void main(String[] args) throws InterruptedException {
		INFO();
		setLogger(new FlameLogger());
		Flame flame = new Flame();
		
		//Form f0 = new Form(0.5f, 0, 0, 0, 0.5f, 0, 1, Color.blue);
		Triangle t0 = new Triangle(0.76f, 0.03f, 0.14f, 0.73f, 0.07f, 0.3f);
		Form f0 = new Form(t0, 1, Color.blue);
		f0.add(Functions.LINEAR, 1);
		f0.add(Functions.SPHERICAL, 1);
		flame.add("F0", f0);
		
		//Form f1 = new Form(0.5f, 0, 0.5f, 0, 0.5f, 0, 1, Color.yellow);
		Triangle t1 = new Triangle(0.45f, 0.05f, -0.14f, 0.47f, -0.06f, -0.03f);
		Form f1 = new Form(t1, 1, Color.yellow);
		f1.add(Functions.LINEAR, 1);
		f1.add(Functions.SWIRL, 1);
		flame.add("F1", f1);
		
		//Form f2 = new Form(0.5f, 0, 0, 0, 0.5f, 0.5f, 1, Color.red);
		/*
		Triangle t2 = new Triangle(0.5f, 0, 0, 0.5f, 0, 0);
		Form f2 = new Form(t2, 1, Color.red);
		f2.add(Functions.LINEAR, 1);
		flame.add("F2", f2);
		*/
		HistogramRenderer histogramRenderer = new ThreadPooledHistogramRenderer(8);
		ImageRenderer imageRenderer = new DefaultImageRenderer();
		FlameRenderer flameRenderer = new FlameRenderer(histogramRenderer, imageRenderer);
		
		int width = 1800;
		int height = 1000;
		int quality = 50;
		int sampling = 1;
		
		final BufferedImage image = flame.render(flameRenderer, width, height, quality, sampling);
		
		JFrame frame = new JFrame("JFlam");
		
		@SuppressWarnings("serial")
		JPanel panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
		};
		
		frame.setContentPane(panel);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		panel.repaint();
	}
}

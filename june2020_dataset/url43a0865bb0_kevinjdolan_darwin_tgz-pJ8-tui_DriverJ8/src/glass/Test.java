package glass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import glass.drawing.Circle;
import glass.drawing.Picture;
import glass.drawing.Shape;
import glass.drawing.ShapeSet;
import glass.drawing.Triangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	public static void main(String[] args) {
		System.out.println("please.");
		
		ShapeSet shapes = new ShapeSet(new Color(255,255,255));
		
		Shape s1 = new Circle(new Point2D.Double(0.5,0.5), 0.1, new Color(255,0,0), 0.8);
		Shape s2 = new Circle(new Point2D.Double(0.6,0.5), 0.08, new Color(0,255,0), 0.8);
		Shape s3 = new Circle(new Point2D.Double(0.6,0.6), 0.08, new Color(100,0,200), 0.8);
		
		Shape s4 = new Triangle(new Point2D.Double(0.1,0.1), new Point2D.Double(0.9,0.9), 
				new Point2D.Double(0.5,0.2), new Color(200,0,100), 0.8);
		
		shapes.addShape(s1);
		shapes.addShape(s2);
		shapes.addShape(s3);
		shapes.addShape(s4);
		
		createAndShowGUI(shapes);
	}

	private static void createAndShowGUI(ShapeSet shapes) {
        //Create and set up the window.
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Picture picture = new Picture(shapes);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600,600));
        panel.setLayout(new BorderLayout());
        panel.add(picture, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
}

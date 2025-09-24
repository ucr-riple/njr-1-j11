package jatrace.gui;

import jatrace.gui.body.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JFrame
{
	
	public static void main(String [] args)
	{
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Gui();
            }
        });
	}
	
	private Gui()
	{
		super("jatrace");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setupContent();
		getContentPane().add(content);
		
		pack();
		setVisible(true);
		
		repaintAll();
	}
	
	
	
	
	JPanel content;
	JPanel footer;
	JPanel divider;
	private void setupContainers()
	{
		content = new JPanel(new BorderLayout());
		content.setPreferredSize(new Dimension( 600, 600 ));
		content.setOpaque(true);
		
		footer = new JPanel(new BorderLayout());
		footer.setPreferredSize(new Dimension(0,200));
		footer.setOpaque(true);
		
		divider = new JPanel(new GridLayout(1,2));
		divider.setPreferredSize(new Dimension(0,200));
		divider.setOpaque(true);
		
	}
	
	BodyPanel bodies;
	JPanel preview;
	JPanel skies;
	JPanel camera;
	JPanel render;
	private void setupContent()
	{
		
		setupContainers();
		
		bodies = new BodyPanel();
		content.add(bodies, BorderLayout.LINE_START);
		
		preview = new JPanel();
		preview.setPreferredSize(new Dimension(400,400));
		preview.add(content, BorderLayout.CENTER);
		
		skies = new JPanel();
		skies.setPreferredSize(new Dimension(200,200));
		footer.add(skies, BorderLayout.LINE_START);
		
		camera = new JPanel();
		camera.setPreferredSize(new Dimension(200,200));
		divider.add(camera);
		
		render = new JPanel();
		render.setPreferredSize(new Dimension(200,200));
		divider.add(render);
		
		footer.add(divider, BorderLayout.CENTER);
		content.add(footer, BorderLayout.PAGE_END);
		
	}
	
	private void repaintAll()
	{
		render.repaint();
		camera.repaint();
		divider.repaint();
		
		skies.repaint();
		footer.repaint();
		
		preview.repaint();
		bodies.repaint();
		content.repaint();
	}
	
	
}

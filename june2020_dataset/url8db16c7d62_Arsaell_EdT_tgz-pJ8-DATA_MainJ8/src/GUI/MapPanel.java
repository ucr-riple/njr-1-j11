package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DATA.Classroom;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class MapPanel extends JPanel {
	
	private ArrayList<Classroom> places;
	private Classroom hoveredClassroom;
	
	private JLabel name, type, eff;
	private JSplitPane splitPane;
	private JPanel panel; 
	
	public MapPanel(ArrayList<Classroom> places) {
		
		this.places = places;
		this.hoveredClassroom = null;
		
		
		BufferedImage img = null;
		try	{
			img = ImageIO.read(new File("img/Plan Campus.jpg"));
		} catch (Exception e)	{e.printStackTrace();}
		

		setLayout(new GridLayout(0, 1, 0, 0));
		
		this.splitPane = new JSplitPane();
		this.add(splitPane);
		
		this.panel = new JPanel();
		panel.setLayout(null);
		JLabel label = new JLabel(new ImageIcon(img));
		label.setBounds(0, 0, img.getWidth(), img.getHeight());
		this.panel.add(label);
		splitPane.setLeftComponent(panel);
		this.setPreferredSize(new Dimension(1050, 550));
		this.setMinimumSize(new Dimension(1050, 550));
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNom = new JLabel("Nom : ");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNom);
		
		name = new JLabel(" ");
		name.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(name);
		
		JLabel lblType = new JLabel("Type : ");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblType);
		
		type = new JLabel(" ");
		type.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(type);
		
		JLabel lblPlaces = new JLabel("Places : ");
		lblPlaces.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblPlaces);
		
		eff = new JLabel(" ");
		eff.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(eff);

		Graphics g = img.getGraphics();
		g.setColor(Color.RED);
		 
		for (final Classroom c : this.places)	{
			if (c.getCoords() != null)	{
				g.fillOval(c.getCoords().x - 34, c.getCoords().y - 34, 9, 9);
				JLabel lbl = new JLabel("");
				lbl.setBounds(c.getCoords().x - 34, c.getCoords().y - 34, 9, 9);
				lbl.addMouseListener(new MouseListener() {
					
					public void mouseReleased(MouseEvent e) {}
					
					public void mousePressed(MouseEvent e) {}
					
					public void mouseExited(MouseEvent e) {
						hoveredClassroom = null;
						updateHC();				
					}
					
					public void mouseEntered(MouseEvent e) {
						hoveredClassroom = c;
						updateHC();
					}
					
					public void mouseClicked(MouseEvent e) {}
				});
				
				//System.out.println(lbl + "\n" + lbl.getBounds());
				this.panel.add(lbl);
			}
		}
		
		this.splitPane.setDividerLocation(img.getWidth());
	}

	private void updateHC()	{
		if (this.hoveredClassroom != null)	{
			this.name.setText(this.hoveredClassroom.getName());
			this.type.setText(this.hoveredClassroom.getType().getShortName() + " (" + this.hoveredClassroom.getType().getName() + ")");
			this.eff.setText(((Integer)(this.hoveredClassroom.getEffectif())).toString());
		}
		else 	{
			this.name.setText("");
			this.type.setText("");
			this.eff.setText("");
		}
	}
}

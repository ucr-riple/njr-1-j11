package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EdTDayPanel extends JPanel {
	
	private JPanel empty;
	private JPanel monday;
	private JPanel tuesday;
	private JPanel wednesday;
	private JPanel thursday;
	private JPanel friday;

	
	/*private JLabel emptyLabel;
	private JLabel mondayLabel;
	private JLabel tuesdayLabel;
	private JLabel wednesdayLabel;
	private JLabel thursdayLabel;
	private JLabel fridayLabel;*/
	
	
	private int dayWidth;
	private int panelHeight;
	
	public EdTDayPanel() {
		
		dayWidth = 200;
		panelHeight = 30;
		
		FlowLayout flowLayout = (FlowLayout) this.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		this.setPreferredSize(new Dimension(1, panelHeight));
		
		empty = new JPanel();
		empty.setPreferredSize(new Dimension(52, panelHeight));
		empty.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		this.add(empty);
		
		monday = new JPanel();
		monday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//monday.setBackground(Color.yellow);
		monday.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.black));
		this.add(monday);

		tuesday = new JPanel();
		tuesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//tuesday.setBackground(Color.yellow);
		tuesday.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.black));
		this.add(tuesday);

		wednesday = new JPanel();
		wednesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//wednesday.setBackground(Color.yellow);
		wednesday.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.black));
		this.add(wednesday);

		thursday = new JPanel();
		thursday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//thursday.setBackground(Color.yellow);
		thursday.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.black));
		this.add(thursday);

		friday = new JPanel();
		friday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//friday.setBackground(Color.yellow);
		friday.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.black));
		this.add(friday);
		
		JLabel mondayLabel = new JLabel("Lundi");
		monday.add(mondayLabel);
		JLabel tuesdayLabel = new JLabel("Mardi");
		tuesday.add(tuesdayLabel);
		JLabel wednesdayLabel = new JLabel("Mercredi");
		wednesday.add(wednesdayLabel);
		JLabel thursdayLabel = new JLabel("Jeudi");
		thursday.add(thursdayLabel);
		JLabel fridayLabel = new JLabel("Vendredi");
		friday.add(fridayLabel);

		//System.out.println("EdTDayPanel");
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		super.paintComponent(g);
		
		g.drawLine(10, 20, 50, 20);
		
	}
}

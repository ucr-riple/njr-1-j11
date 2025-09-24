package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

public class EdTHourPanel extends JPanel {
	
	public EdTHourPanel() {
		
		FlowLayout flowLayout = (FlowLayout) this.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);

		this.setPreferredSize(new Dimension(51,600));
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		super.paintComponent(g);
		
		g.drawLine(37, 45, 50, 45);
		g.drawLine(37, 95, 50, 95);
		g.drawLine(37, 145, 50, 145);
		g.drawLine(37, 195, 50, 195);
		g.drawLine(37, 245, 50, 245);
		g.drawLine(37, 295, 50, 295);
		g.drawLine(37, 345, 50, 345);
		g.drawLine(37, 395, 50, 395);
		g.drawLine(37, 445, 50, 445);
		g.drawLine(37, 495, 50, 495);
		g.drawString("08h00", 8, 25);
		g.drawString("09h00", 8, 75);
		g.drawString("10h00", 8, 125);
		g.drawString("11h00", 8, 175);
		g.drawString("12h00", 8, 225);
		g.drawString("13h00", 8, 275);
		g.drawString("14h00", 8, 325);
		g.drawString("15h00", 8, 375);
		g.drawString("16h00", 8, 425);
		g.drawString("17h00", 8, 475);
		g.drawString("18h00", 8, 525);
		
	}
}

package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import DATA.*;

public class EdTContentPanel extends JPanel {
	
	private JPanel monday;
	private JPanel tuesday;
	private JPanel wednesday;
	private JPanel thursday;
	private JPanel friday;
	private JPanel[] days;
	
	/*private JLabel emptyLabel;
	private JLabel mondayLabel;
	private JLabel tuesdayLabel;
	private JLabel wednesdayLabel;
	private JLabel thursdayLabel;
	private JLabel fridayLabel;*/
	
	
	private int dayWidth;
	private int panelHeight;
	
	public EdTContentPanel(WeekTable wt) {
		
		dayWidth = 200;
		panelHeight = 550;
		
		FlowLayout flowLayout = (FlowLayout) this.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		this.setPreferredSize(new Dimension(1005, panelHeight));
		
		monday = new JPanel();
		monday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//monday.setBackground(Color.yellow);
		monday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		/*
		FlowLayout mflowLayout = (FlowLayout) monday.getLayout();
		mflowLayout.setVgap(3);
		mflowLayout.setHgap(3);
		mflowLayout.setAlignment(FlowLayout.LEFT);
		*/
		this.add(monday);

		tuesday = new JPanel();
		tuesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//tuesday.setBackground(Color.yellow);
		tuesday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(tuesday);

		wednesday = new JPanel();
		wednesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//wednesday.setBackground(Color.yellow);
		wednesday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(wednesday);

		thursday = new JPanel();
		thursday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//thursday.setBackground(Color.yellow);
		thursday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(thursday);

		friday = new JPanel();
		friday.setPreferredSize(new Dimension(dayWidth + 1, panelHeight));
		//friday.setBackground(Color.yellow);
		friday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(friday);
		
		days = new JPanel[]{monday, tuesday, wednesday, thursday, friday};
		
		fillEdT(wt);
	}
	
	public void fillEdT(WeekTable wt) {
		
		
		if (wt != null)
		for (Slot s : wt.getSlots())	{
			

				JPanel jpnl = new JPanel();
				jpnl.setLayout(new BoxLayout(jpnl, BoxLayout.Y_AXIS));
				
				jpnl.setPreferredSize(new Dimension(dayWidth - 7, (int)(s.getDuration().toMin() * panelHeight / 690) - 3));
				jpnl.setBackground(Color.LIGHT_GRAY);
				jpnl.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				
				if (s instanceof Lesson)	{
					
					jpnl.setBackground(Color.white);
					JLabel field = new JLabel();
					field.setHorizontalTextPosition(SwingConstants.CENTER);
					field.setText(((Lesson) s).getField().toString());
					jpnl.add(field);
					
					if (! (wt.getOwner() instanceof Teacher))	{
						JLabel teach = new JLabel(((Lesson) s).getTeacher().getName());
						teach.setHorizontalTextPosition(SwingConstants.RIGHT);
						jpnl.add(teach);
					}
					
					if (!(wt.getOwner() instanceof Group))	{
						JLabel group = new JLabel(((Lesson) s).getStudents().toString());
						group.setHorizontalTextPosition(SwingConstants.LEFT);
						jpnl.add(group);
					}
					
					if (!(wt.getOwner() instanceof Classroom))	{
						JLabel place = new JLabel(((Lesson) s).getPlace().toString());
						place.setHorizontalTextPosition(SwingConstants.CENTER);
						jpnl.add(place);
					}
				}	
				
				days[s.getBegin().getDay()].add(jpnl);
				
				if (s.getEnd().getHour() == 12)	{
					jpnl = new JPanel();
					jpnl.setPreferredSize(new Dimension(dayWidth - 7, 120 * panelHeight / 690));
					days[s.getBegin().getDay()].add(jpnl);
				}
				
		}
		/*
		for (int i = 0; i < days.length; i++) {
			if(i == 0) {
				JPanel empty = new JPanel();
				empty.setPreferredSize(new Dimension(dayWidth - 7, 17));
				days[i].add(empty);
				
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel.setBackground(Color.white);
				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				days[i].add(panel);

				JPanel panel2 = new JPanel();
				panel2.setPreferredSize(new Dimension(dayWidth - 7, 47));
				panel2.setBackground(Color.white);
				panel2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				days[i].add(panel2);
				
				JPanel panel3 = new JPanel();
				panel3.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel3.setBackground(Color.white);
				panel3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				days[i].add(panel3);
				
				JPanel panel4 = new JPanel();
				panel4.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel4.setBackground(Color.white);
				panel4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				days[i].add(panel4);
				JPanel panel5 = new JPanel();
				panel5.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel5.setBackground(Color.white);
				panel5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				days[i].add(panel5);
			}
		}*/
	}

}

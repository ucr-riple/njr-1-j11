package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import DATA.WeekTable;

public class EdTViewerPanel extends JPanel {

	private JPanel panel;
	
	private EdTHourPanel hourPanel;
	private EdTDayPanel dayPanel;
	private EdTContentPanel contentPanel;
	
	public EdTViewerPanel(WeekTable wt) {
		initialize(wt);
	}
	
	private void initialize(WeekTable wt) {
		this.setVisible(true);
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel classSelectorPanel = new JPanel();
		//classSelectorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.add(classSelectorPanel, BorderLayout.NORTH);
		
		JPanel timetableLayoutPanel = new JPanel();
		timetableLayoutPanel.setPreferredSize(new Dimension(1056,800));
		JScrollPane scrollPane = new JScrollPane(timetableLayoutPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane, BorderLayout.CENTER);
		timetableLayoutPanel.setLayout(new BorderLayout(0, 0));
		
		hourPanel = new EdTHourPanel();
		timetableLayoutPanel.add(hourPanel, BorderLayout.WEST);
		
		dayPanel = new EdTDayPanel();
		timetableLayoutPanel.add(dayPanel, BorderLayout.NORTH);
		
		contentPanel = new EdTContentPanel(wt);
		timetableLayoutPanel.add(contentPanel, BorderLayout.CENTER);
	}
}

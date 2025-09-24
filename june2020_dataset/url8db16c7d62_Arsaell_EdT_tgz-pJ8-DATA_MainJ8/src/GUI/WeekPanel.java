package GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import DATA.Slot;
import DATA.Time;
import DATA.WeekTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WeekPanel extends JPanel {

	private StartFrame container;
	private JTable table;
	private String[] columnNames = new String[]{"Hours", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	public WeekPanel(StartFrame aContainer) {

		super();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				container.ds.setDefaultWeek(updateWeekTable());
			}
		});
		this.container = aContainer;
		this.setBorder(new EmptyBorder(23, 23, 23, 23));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{new Integer(0), null, null, null, null, null, null, null},
				{new Integer(1), null, null, null, null, null, null, null},
				{new Integer(2), null, null, null, null, null, null, null},
				{new Integer(3), null, null, null, null, null, null, null},
				{new Integer(4), null, null, null, null, null, null, null},
				{new Integer(5), null, null, null, null, null, null, null},
				{new Integer(6), null, null, null, null, null, null, null},
				{new Integer(7), null, null, null, null, null, null, null},
				{new Integer(8), null, null, null, null, null, null, null},
				{new Integer(9), null, null, null, null, null, null, null},
				{new Integer(10), null, null, null, null, null, null, null},
				{new Integer(11), null, null, null, null, null, null, null},
				{new Integer(12), null, null, null, null, null, null, null},
				{new Integer(13), null, null, null, null, null, null, null},
				{new Integer(14), null, null, null, null, null, null, null},
				{new Integer(15), null, null, null, null, null, null, null},
				{new Integer(16), null, null, null, null, null, null, null},
				{new Integer(17), null, null, null, null, null, null, null},
				{new Integer(18), null, null, null, null, null, null, null},
				{new Integer(19), null, null, null, null, null, null, null},
				{new Integer(20), null, null, null, null, null, null, null},
				{new Integer(21), null, null, null, null, null, null, null},
				{new Integer(22), null, null, null, null, null, null, null},
				{new Integer(23), null, null, null, null, null, null, null},
			},
			columnNames
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		for (int i = 0 ; i < table.getColumnModel().getColumnCount() ; i++)	{

			table.getColumnModel().getColumn(i).setResizable(false);
			table.getColumnModel().getColumn(i).setMinWidth(65);
			table.getColumnModel().getColumn(i).setMaxWidth(100);
		}
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Veuillez lister les heures de cours de la semaine.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.NORTH);
		
		
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("WeekPanel.valider() : " + container.ds);
				container.ds.setDefaultWeek(updateWeekTable());
				
				container.allowTimeableTabs(true);
				//System.out.println("WeekPanel.valider() : " + container.ds);
			}
		});
		btnValider.setVerticalAlignment(SwingConstants.TOP);
		add(btnValider, BorderLayout.SOUTH);
		
	}

	private WeekTable updateWeekTable()	{

		ArrayList<Slot> temp = new ArrayList<Slot>();
		int begin = -1;
		
		for (int col = 1 ; col < table.getColumnCount() ; col++)	{
			for (int row = 0 ; row < table.getRowCount() ; row++)	{
				
				table.getValueAt(row, col);
				
				if (table.getValueAt(row, col) == null)
					table.setValueAt(false, row, col);

				if (row < table.getRowCount() - 1 && table.getValueAt(row + 1, col) == null)
					table.setValueAt(false, row + 1, col);
				
				if (begin == -1 && (Boolean)table.getValueAt(row, col))
					begin = row;
				
				if (row != begin && (Boolean)table.getValueAt(row, col) && (row == table.getRowCount() - 1 || !(Boolean)table.getValueAt(row + 1, col)))	{
					temp.add(new Slot(new Time((col - 1) * 10000 + begin * 100), new Time((col - 1) * 10000 + row * 100)));
					begin = -1;
				}
			}
		}
		WeekTable res = new WeekTable(temp, null);
		
		return res;
	}
}

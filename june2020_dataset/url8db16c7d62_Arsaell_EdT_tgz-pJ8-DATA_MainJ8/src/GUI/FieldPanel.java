package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DATA.ClassType;
import DATA.DataStore;
import DATA.Field;
import DATA.Slot;
import DATA.Time;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FieldPanel extends JPanel {
	
	private ArrayList<ClassType> classtypes;
	private ArrayList<Field> fields;
	
	private DataStore ds;
	
	private JTextField fieldName;
	private JTextField typeName;
	private JSpinner minHour;
	private JSpinner minMin;
	private JSpinner maxHour;
	private JSpinner maxMin;
	private JTextField roomType;
	private JList typesList;
	private JList fieldsList;
	private JButton addType;
	private JButton addField;
	private JComboBox fieldType;
	
	
	public FieldPanel(DataStore datastore) {

		super();
		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e)	{
				fields = ds.getFields();
				classtypes = ds.getTypes();
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				ds.setFields(fields);
				ds.setTypes(classtypes);
			}
		});
		
		this.ds = datastore;
		this.classtypes = ds.getTypes();
		this.fields = ds.getFields();
		
		this.setMinimumSize(new Dimension(450, 325));
		
		JLabel lblTypes = new JLabel("                            Types");
		
		JSplitPane splitPane_1 = new JSplitPane();
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);
		panel_1.setLayout(new GridLayout(5, 3, 10, 10));
		
		JLabel lblNom = new JLabel("Nom");
		panel_1.add(lblNom);
		
		typeName = new JTextField();
		typeName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//System.out.println("typeName");
				checkEnableButtons();
			}
		});
		panel_1.add(typeName);
		typeName.setColumns(10);
		
		JLabel lblType_1 = new JLabel("Type de Salle");
		panel_1.add(lblType_1);
		
		roomType = new JTextField();
		panel_1.add(roomType);
		roomType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//System.out.println("roomType");
				checkEnableButtons();
			}
		});
		
		
		JLabel lblTempsMinimum = new JLabel("Temps Minimum");
		panel_1.add(lblTempsMinimum);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		minHour = new JSpinner();
		minHour.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("minHour");
				checkEnableButtons();
			}
		});
		panel_2.add(minHour);
		
		
		JLabel lblHeures = new JLabel("heures");
		panel_2.add(lblHeures);
		
		minMin = new JSpinner();
		panel_2.add(minMin);
		minMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("minMin");
				checkEnableButtons();
			}
		});
		
		
		JLabel lblMinutes = new JLabel("minutes");
		panel_2.add(lblMinutes);
		
		JLabel lblTempsMaximum = new JLabel("Temps Maximum");
		panel_1.add(lblTempsMaximum);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		maxHour = new JSpinner();
		panel_3.add(maxHour);
		maxHour.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("maxHour");
				checkEnableButtons();
			}
		});
		
		
		JLabel lblHeures_1 = new JLabel("heures");
		panel_3.add(lblHeures_1);
		
		maxMin = new JSpinner();
		panel_3.add(maxMin);
		maxMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("maxMin");
				checkEnableButtons();
			}
		});
		
		
		JLabel lblMinutes_1 = new JLabel("minutes");
		panel_3.add(lblMinutes_1);
		
		JLabel label = new JLabel("");
		panel_1.add(label);
		
		addType = new JButton("Ajouter");
		addType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = typeName.getText();
				String shortName = roomType.getText();
				int hmin = (Integer) minHour.getValue();
				int mmin = (Integer) minMin.getValue();
				int hmax = (Integer) maxHour.getValue();
				int mmax = (Integer) maxMin.getValue();
				if (name.length() > 0 && shortName.length() > 0 && hmin + mmin > 0 && hmax + mmax > 0)
					try	{
						
						ClassType ct = new ClassType(name, shortName, new Slot(new Time(hmin * 100 + mmin), new Time(hmax * 100 + mmax)));
						classtypes.add(ct);
						
						typeName.setText("");
						roomType.setText("");
						minHour.setValue(0);
						minMin.setValue(0);
						maxHour.setValue(0);
						maxMin.setValue(0);
						
						typesList.setListData(classtypes.toArray());
						fieldType.addItem(ct);
						
					} catch (Exception e)	{
						e.printStackTrace();
						System.out.println("GUI.FieldPanel : tried to add an unavalid ClassType");
					}
				checkEnableButtons();
			}
		});
		panel_1.add(addType);
		
		JPanel panel_4 = new JPanel();
		splitPane_1.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		typesList = new JList(classtypes.toArray());
		panel_4.add(new JScrollPane(typesList), BorderLayout.NORTH);
//		
//		ListModel ctList = new javax.swing.DefaultListModel();
//		
//		list_1.setModel(ctList);
//		
		JButton rmType = new JButton("Enlever");
		rmType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] sel = typesList.getSelectedIndices();
				for (int i = sel.length - 1 ; i >= 0 ; i--)
					classtypes.remove(sel[i]);

				typesList.setListData(classtypes.toArray());
				
				fieldType.removeAllItems();
				for (ClassType ct : classtypes)
					fieldType.addItem(ct);
			}
		});
		panel_4.add(rmType, BorderLayout.SOUTH);
		
		JLabel lblMatires = new JLabel("                            Matières");
		
		JSplitPane splitPane = new JSplitPane();
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new GridLayout(3, 3, 10, 10));
		
		JLabel lblNewLabel = new JLabel("Nom");
		panel.add(lblNewLabel);
		
		fieldName = new JTextField();
		panel.add(fieldName);
		fieldName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				checkEnableButtons();
			}
		});
		
		
		JLabel lblType = new JLabel("Type");
		panel.add(lblType);
		
		fieldType = new JComboBox(classtypes.toArray());
		fieldType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				checkEnableButtons();
			}
		});
		panel.add(fieldType);
		
		JLabel label_3 = new JLabel("");
		panel.add(label_3);
		
		this.addField = new JButton("Ajouter");
		addField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Field f = new Field((ClassType)fieldType.getSelectedItem(), fieldName.getText());
				fields.add(f);
				fieldsList.setListData(fields.toArray());
			}
		});
		panel.add(addField);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(lblTypes);

		JPanel fieldsPanel = new JPanel();
		
		splitPane.setLeftComponent(fieldsPanel);
		
		fieldsList = new JList(this.fields.toArray());
		//addField = new JButton("Ajouter");
		
		
		JButton rmField = new JButton("Enlever");
		rmField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int [] sel = fieldsList.getSelectedIndices();
				
				for (int i = sel.length - 1 ; i >= 0 ; i--)
					fields.remove(sel[i]);
				
				fieldsList.setListData(fields.toArray());
			}
		});
		fieldsPanel.setLayout(new BorderLayout(0, 0));
		
		
		fieldsPanel.add(new JScrollPane(fieldsList), BorderLayout.NORTH);
		fieldsPanel.add(rmField, BorderLayout.SOUTH);
		
		JLabel label_1 = new JLabel("");
		add(label_1);
		add(splitPane_1);
		
		JLabel label_4 = new JLabel("");
		add(label_4);
		add(lblMatires);
		add(splitPane);
		
		JLabel label_5 = new JLabel("");
		add(label_5);
		this.checkEnableButtons();
		this.setVisible(true);
	}
	
	private void checkEnableButtons()	{
		
		boolean bool = this.typeName.getText().length() > 0 && this.roomType.getText().length() > 0 && ((Integer)this.minHour.getValue() + (Integer)this.minMin.getValue() > 0) && ((Integer)this.maxHour.getValue() + (Integer)this.maxMin.getValue() > 0);
		//System.out.print(bool);
		this.addType.setEnabled(bool);
		
		bool = this.fieldName.getText().length() > 0 && this.fieldType.getSelectedItem() != null;
		//System.out.print(bool);
		this.addField.setEnabled(bool);
	}
	
	public ArrayList<ClassType> getClassTypes()	{
		return this.classtypes;
	}
	
	public ArrayList<Field> getFields()	{
		return this.fields;
	}
}

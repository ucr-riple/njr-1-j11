package GUI;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import DATA.DataStore;
import DATA.Field;
import DATA.Group;
import DATA.Time;
import DATA.HashMap;

import javax.swing.JSplitPane;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.border.EmptyBorder;

public class GroupPanel extends JPanel {
	
	private Vector<Group> groups;
	private HashMap<Group, DefaultMutableTreeNode> nodes;
	private HashMap<Field, Time> classes;	
	
	private Group parentSelected;
	
	private JTree groupsTree;
	private JSpinner hours;
	private JSpinner minutes;
	private JTextField groupName;
	private JList fieldsList;
	private JList classesList;
	private JButton addGroup;
	private JButton addClass;
	private JComboBox<Group> groupsCBox;
	private JSplitPane splitPane;
	private DataStore ds;

	private void initializeTree() {
		groups = new Vector<Group>(ds.getGroups());
		groupsCBox = new JComboBox(groups);
		fieldsList.setListData(ds.getFields().toArray());
		classesList.setListData(classes.keySet().toArray());
		setUpTree();
		if (ds.getFields().size() == 0)
			fieldsList.setListData(new String[]{"Veuillez ajouter", "les matières", "dans l'onglet \"Fields\"."});
		if (classes.size() == 0)
			classesList.setListData(new String[]{"Veuillez sélectionner", "une matière", "dans la liste", "à droite."});
		
		splitPane.setDividerLocation(0.25);

	}
	
	public GroupPanel(DataStore dataStore) {
		
		super();
		this.ds = dataStore;
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				initializeTree();
			}
			
			public void componentHidden(ComponentEvent e)	{
				ds.setGroups(new ArrayList<Group>(groups));
			}
			
		});
		
		this.groups = new Vector<Group>(ds.getGroups());
		this.classes = new HashMap<Field, Time>();
		this.nodes = new HashMap<Group, DefaultMutableTreeNode>();
//		classes.put(new Field(new ClassType("", "", new Slot(new Time(), new Time())), ""), new Time());
		this.setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		this.groupsTree = new JTree(new DefaultMutableTreeNode("All"));
		//this.groupsTree.setRootVisible(false);
		scrollPane.setViewportView(groupsTree);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
		
		splitPane.setRightComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {50, 200};
		gbl_panel.rowHeights = new int[] {50, 50, 46, 100, 50, 30};
		gbl_panel.columnWeights = new double[]{1.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.fill = GridBagConstraints.VERTICAL;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panel.add(lblNom, gbc_lblNom);
		
		this.groupName = new JTextField();
		groupName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				checkAddGroupEnabled();
			}
			public void keyReleased(KeyEvent e) {
				checkAddGroupEnabled();
			}
		});
		
		GridBagConstraints gbc_groupName = new GridBagConstraints();
		gbc_groupName.insets = new Insets(0, 0, 5, 0);
		gbc_groupName.anchor = GridBagConstraints.WEST;
		gbc_groupName.ipadx = 99;
		gbc_groupName.gridx = 1;
		gbc_groupName.gridy = 0;
		panel.add(groupName, gbc_groupName);
		groupName.setColumns(10);
		
		JLabel lblParent = new JLabel("Parent");
		GridBagConstraints gbc_lblParent = new GridBagConstraints();
		gbc_lblParent.anchor = GridBagConstraints.EAST;
		gbc_lblParent.fill = GridBagConstraints.VERTICAL;
		gbc_lblParent.insets = new Insets(0, 0, 5, 5);
		gbc_lblParent.gridx = 0;
		gbc_lblParent.gridy = 1;
		panel.add(lblParent, gbc_lblParent);
		
		
		this.groupsCBox = new JComboBox(groups);
		groupsCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentSelected = (Group)((JComboBox)(e.getSource())).getSelectedItem();
				checkAddGroupEnabled();
			}
		});
		
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel.add(groupsCBox, gbc_comboBox);
		
		JLabel lblMatieres = new JLabel("Matières");
		GridBagConstraints gbc_lblMatieres = new GridBagConstraints();
		gbc_lblMatieres.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblMatieres.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatieres.gridx = 0;
		gbc_lblMatieres.gridy = 3;
		panel.add(lblMatieres, gbc_lblMatieres);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(1, 3, 10, 10));
		

		this.fieldsList = new JList(ds.getFields().toArray());
		
		panel_1.add(fieldsList);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 2, 5, 5));
		
		this.hours = new JSpinner();
		hours.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		
		this.hours.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_2.add(hours);
		
		JLabel lblHeures = new JLabel("heures");
		panel_2.add(lblHeures);
		
		this.minutes = new JSpinner();
		minutes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		
		
		this.minutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		panel_2.add(minutes);
		
		JLabel lblMinutes = new JLabel("minutes");
		panel_2.add(lblMinutes);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(UIManager.getColor("Panel.background"));
		separator.setForeground(UIManager.getColor("Panel.background"));
		panel_2.add(separator);
		
		this.addClass = new JButton(">>>");
		addClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Field f = (Field)fieldsList.getSelectedValue();
				Time t = new Time((Integer)hours.getValue() * 100 + (Integer)minutes.getValue());
				classes.put(f, t);
				hours.setValue(0);
				minutes.setValue(0);
				fieldsList.setSelectedIndex(-1);
				classesList.setListData(classes.keySet().toArray());
				
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		panel_2.add(addClass);
		
		this.classesList = new JList(this.classes.keySet().toArray());
		//this.classesList = new JList(new String[]{"1", "2", "3", "4"});
		panel_1.add(classesList);
		
		JLabel label_1 = new JLabel(" ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.BOTH;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 4;
		panel.add(label_1, gbc_label_1);
		
		this.addGroup = new JButton("Ajouter");
		
		this.addGroup.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				String name = groupName.getText();
				Group parent = parentSelected;
				Group g = new Group(name, 0).setClasses(classes).setParent(parent);
				groups.add(g);
				//System.out.println(name + " " + parent + " " + g + " " + g.classes + groups);
				classes = new HashMap<Field, Time>();
				groupName.setText("");
				
				//classesList = new JList<Field>();
				groupsCBox = new JComboBox(groups.toArray());
				
				setUpTree();
				
				checkAddGroupEnabled();
				checkAddClassEnabled();
			}
		});
		
		GridBagConstraints gbc_addGroup = new GridBagConstraints();
		gbc_addGroup.insets = new Insets(0, 0, 5, 0);
		gbc_addGroup.anchor = GridBagConstraints.EAST;
		gbc_addGroup.gridx = 1;
		gbc_addGroup.gridy = 4;
		panel.add(addGroup, gbc_addGroup);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{groupName, hours, minutes, addClass}));
		
		this.initializeTree();
		checkAddGroupEnabled();
		checkAddClassEnabled();
		
	}

	private void setUpTree()	{
		
		Vector<Group> temp = new Vector<Group>(this.groups);
		DefaultMutableTreeNode root = null;
		this.nodes = new HashMap<Group, DefaultMutableTreeNode>();
		
		while (temp.size() > 0)	{
			for (int i = 0 ; i < temp.size() ; i++)	{
				Group g = temp.get(i);
				if (g.getParent() == null)	{
					root = new DefaultMutableTreeNode(g);
					this.nodes.put(g, root);
					temp.remove(g);
					break;
				}
				boolean insertHere = false;
				Enumeration e = root.breadthFirstEnumeration();
				while (e.hasMoreElements())	{
					DefaultMutableTreeNode tn = (DefaultMutableTreeNode) e.nextElement();
					//System.out.println(g + " " + tn + " " + g.getParent());
					if (tn.getUserObject() == g.getParent())	{
						insertHere = true;
						DefaultMutableTreeNode res = new DefaultMutableTreeNode(g);
						tn.add(res);
						temp.remove(g);
						this.nodes.put(g, res);
						break;
					}
				}
				if (!insertHere)
					System.out.println("GroupPanel.setUpTree() : Group " + g + " didn't fit in tree. " + temp.size() + " " + root.getChildCount());
			}
		}
		
		this.groupsTree = new JTree(root);
		this.splitPane.setLeftComponent(this.groupsTree);
		
	}
	
	private void checkAddGroupEnabled()	{
		//System.out.println("Group");
		this.addGroup.setEnabled(this.groupName.getText().length() > 0 && this.classes.size() > 0 && (parentSelected != null || this.groups.size() == 0));
	}
	
	private void checkAddClassEnabled()	{
		//System.out.println("Class");
		this.addClass.setEnabled(ds.getFields().size() != 0 && this.fieldsList.getSelectedIndex() != -1 && ((Integer)this.hours.getValue() + (Integer)this.minutes.getValue() > 0));
	}

	public ArrayList<Group> getGroups() {
		return this.getGroups();
	}
}

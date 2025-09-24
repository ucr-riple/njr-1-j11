package manager.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import factory.KitConfig;
import factory.PartType;

/*
* Authorship: Aaron Harris and Matt Zecchini
*/

public class KitManagerPanel extends JPanel implements ActionListener {
	private manager.KitManager km;
	
	private ArrayList<KitConfig> kitConfigs;
	private ArrayList<PartType> partTypes;
	
	private JComboBox[] cbPart;
	private JComboBox cbKits;
	private Object[] partModel;
	private DefaultComboBoxModel kitModel;
	private JTextField tfName;
	private JPanel pnlButtons;
	private JButton btnAddKit;
	private JButton btnEditKit;
	private JButton btnDeleteKit;
	private JButton btnCreateKit;
	private JButton btnClrFields;
	private JButton btnSaveChg;
	private JButton btnCnclChg;
	
	private final PartType noPart = new PartType("No Part");
	private JPanel pnlParts;

	/**
	 * Create the panel.
	 */
	public KitManagerPanel(manager.KitManager k) {
		// store a reference to the KitManager to sent/receive items from client/server.
		km = k;
		
		setLayout(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel managerPanel = new JPanel();
		tabbedPane.addTab("Manage Kits", managerPanel);
		managerPanel.setLayout(new BorderLayout());
		
		//   This creates the panel at the top of the layout that lets the user select what kit to view/edit/delete
		JPanel pnlKitChooser = new JPanel();
		managerPanel.add(pnlKitChooser, BorderLayout.NORTH);
		
		// Creates a ComboBoxModel with all the KitConfigs, This populates the ComboBox at the top of the layout with the list of kitConfigs
		kitConfigs = Utils.Constants.DEFAULT_KITCONFIGS;
		kitModel = new DefaultComboBoxModel(kitConfigs.toArray());
		cbKits = new JComboBox(kitModel);
		cbKits.addActionListener(this);
		pnlKitChooser.add(cbKits);
		
		btnAddKit = new JButton("New Kit Arrangement");
		btnAddKit.addActionListener(this);
		pnlKitChooser.add(btnAddKit);
		
		// This panel is what allows us to combine the View/Add/Edit/Delete screens together
		// Each "screen" is instead a Panel of the buttons that that screen would have
		pnlButtons = new JPanel();
		managerPanel.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new CardLayout(0, 0));
		
		JPanel pnlView = new JPanel();
		pnlButtons.add(pnlView, "View");
		
		btnEditKit = new JButton("Edit Kit Arrangement");
		btnEditKit.addActionListener(this);
		pnlView.add(btnEditKit);
		
		btnDeleteKit = new JButton("Delete Kit Arrangement");
		btnDeleteKit.addActionListener(this);
		pnlView.add(btnDeleteKit);
		
		JPanel pnlEdit = new JPanel();
		pnlButtons.add(pnlEdit, "Edit");
		
		btnSaveChg = new JButton("Save Changes");
		btnSaveChg.addActionListener(this);
		pnlEdit.add(btnSaveChg);
		
		btnCnclChg = new JButton("Cancel Changes");
		btnCnclChg.addActionListener(this);
		pnlEdit.add(btnCnclChg);
		
		JPanel pnlAdd = new JPanel();
		pnlButtons.add(pnlAdd, "Add");
		
		btnCreateKit = new JButton("Create Kit Arrangement");
		btnCreateKit.addActionListener(this);
		pnlAdd.add(btnCreateKit);
		
		btnClrFields = new JButton("Clear Fields");
		btnClrFields.addActionListener(this);
		pnlAdd.add(btnClrFields);

		// The "Display" Panel is the central panel that displays the information about a certain kit
		JPanel pnlDisplay = new JPanel();
		pnlDisplay.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		managerPanel.add(pnlDisplay, BorderLayout.CENTER);
		pnlDisplay.setLayout(new BorderLayout());
		
		// The name field is separated on a separate panel so it remains centered in the frame and not at the mercy of the comboBox sizes
		JPanel pnlName = new JPanel();
		pnlDisplay.add(pnlName, BorderLayout.NORTH);
		
		JLabel lblKitName = new JLabel("Kit Name:");
		pnlName.add(lblKitName);
		
		tfName = new JTextField();
		pnlName.add(tfName);
		tfName.setColumns(10);
		
		pnlParts = new JPanel();
		pnlDisplay.add(pnlParts, BorderLayout.CENTER);
		GridBagLayout gbl_pnlParts = new GridBagLayout();
//      The below code aligns the GridBagLayout in the upper left corner of the panel; We don't want this
//		gbl_pnlParts.columnWidths = new int[]{0, 0, 0, 0, 0};
//		gbl_pnlParts.rowHeights = new int[]{0, 0};
//		gbl_pnlParts.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		gbl_pnlParts.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlParts.setLayout(gbl_pnlParts);

		// This loop adds the 8 Part labels (Part 1, Part 2, ... Part 8) to the panel iteratively
		for (int i = 0; i < 4; i++) {
			GridBagConstraints gbc_lblPart = new GridBagConstraints();
			JLabel lblPart = new JLabel("Part " + (i+1) + ":");
			if (i == 0) gbc_lblPart.anchor = GridBagConstraints.EAST;
			gbc_lblPart.insets = new Insets(0, 0, 5, 5);
			gbc_lblPart.gridx = 0;
			if (i == 3) gbc_lblPart.insets = new Insets(0,0,0,5);
			gbc_lblPart.gridy = i;
			pnlParts.add(lblPart, gbc_lblPart);

			lblPart = new JLabel("Part " + (i+5) + ":");
			gbc_lblPart.gridx = 2;
			gbc_lblPart.gridy = i;
			pnlParts.add(lblPart, gbc_lblPart);
		}

		// Array of comboBoxes is used to iteratively construct the comboboxes
		// This is used to make sure all comboBoxes are made the same way.
		cbPart = new JComboBox[8];
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		
		// First construct the ComboBoxModel for the PartTypes, then iterate through to add PartTypes
		partTypes = Utils.Constants.DEFAULT_PARTTYPES;
		partTypes.add(0,noPart);
		partModel = partTypes.toArray();
		
		for (int i = 0; i < 4; i++) {
			cbPart[i] = new JComboBox(partModel);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = i;
			pnlParts.add(cbPart[i], gbc_comboBox);
			
			cbPart[i+4] = new JComboBox(partModel);
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 3;
			gbc_comboBox.gridy = i;
			pnlParts.add(cbPart[i+4], gbc_comboBox);
		}
		
		disableFields();
		viewKit((KitConfig) cbKits.getSelectedItem());
		showButtons("View");
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnAddKit) {
			showButtons("Add");
			clearFields();
			enableFields();
		} else if (ae.getSource() == btnCreateKit) {
			KitConfig k = createKit();
			if (k != null) {
				km.addKit(k);
				viewKit(k);
				disableFields();
				cbKits.setSelectedIndex(cbKits.getModel().getSize()-1);
			}
		} else if (ae.getSource() == cbKits) {
			disableFields();
			viewKit((KitConfig) cbKits.getSelectedItem());
			showButtons("View");
		} else if (ae.getSource() ==  btnClrFields) {
			clearFields();
		} else if (ae.getSource() == btnEditKit) {
			enableFields();
			tfName.setEnabled(false);
			showButtons("Edit");
		} else if (ae.getSource() == btnDeleteKit) {
			int choice = JOptionPane.showConfirmDialog(null,
	        		"Are you sure you want to delete this part type?\nNote: the action cannot be undone.",
	                "Delete Part",
	                JOptionPane.YES_NO_OPTION);
	        if (choice == 0){
	        	deleteKit((KitConfig) cbKits.getSelectedItem());
	        }
		} else if (ae.getSource() == btnSaveChg) {
			KitConfig editedKit = createKit();
			if (editedKit != null) {
				disableFields();
				km.editKit(editedKit);
				viewKit(editedKit);
				showButtons("View");
				cbKits.setSelectedItem(editedKit);
			}
		} else if (ae.getSource() == btnCnclChg) {
			disableFields();
			viewKit((KitConfig) cbKits.getSelectedItem());
		}
	}
	
	public void viewKit(KitConfig kit) {
		tfName.setText(kit.getName());
		ArrayList<PartType> parts = new ArrayList<PartType>(kit.getParts());
		for (int i = 0; i < 8; i++) {
			if (i < parts.size()) {
				cbPart[i].setSelectedItem((Object) parts.get(i));
			} else {
				cbPart[i].setSelectedIndex(0);
			}
		}
	}
	
	public KitConfig createKit() {
		// for each of the comboboxes
		ArrayList<PartType> parts = new ArrayList<PartType>(8);
		for (int i = 0; i < 8; i++) {
			PartType p = (PartType) cbPart[i].getSelectedItem();
			if (!p.equals(noPart)) {
				parts.add(p);
			}
		}
		// validates 4-8 parts set
		if (parts.size() < 4) {
			JOptionPane.showMessageDialog(km,
	        		"You have not selected at least 4 parts for the kit arrangement",
	                "Not Enough Parts",
	                JOptionPane.ERROR_MESSAGE);
			return null;
		} else if (nameClear(tfName.getText())) {
			JOptionPane.showMessageDialog(km,
	        		"The Kit Name you have entered has already been taken",
	                "Name Taken",
	                JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			KitConfig newKit = new KitConfig(tfName.getText(), parts);
			return newKit;
		}
	}
	
	public void deleteKit(KitConfig deadKit) {
    	// changes the comboBox to look at the previous item in the list
		if (cbKits.getSelectedIndex() < 1) {
			cbKits.setSelectedIndex(0);
		} else {
			cbKits.setSelectedIndex(cbKits.getSelectedIndex()-1);
		}
    	viewKit((KitConfig) cbKits.getSelectedItem());
    	kitModel.removeElement(deadKit);
    	// send a message to fcs that the kit is now dead
    	km.deleteKit(deadKit);
	}
	
	public void updatePartComboModels() {
		// makes sure comboBoxModel for cbPart is up to date.
		for (int i = 0; i < 8; i++ ){
			cbPart[i].setModel(new DefaultComboBoxModel(partModel));
		}
	}

	public void clearFields() {
		tfName.setText("");
		// each of the comboBoxes set to first Item
		for (int i = 0; i < 8; i++) cbPart[i].setSelectedIndex(0);
	}
	
	public void enableFields() {
		tfName.setEnabled(true);
		for (int i = 0; i < 8; i++) {
			cbPart[i].setEnabled(true);
		}
	}
	
	public void disableFields() {
		tfName.setEnabled(false);
		for (int i = 0; i < 8; i++) {
			cbPart[i].setEnabled(false);
		}
	}

	public void updateKitConfig(ArrayList<KitConfig> kc)
	{
		kitConfigs = kc;
		kitModel = new DefaultComboBoxModel(kc.toArray());
		// sets a new Object Array that acts as the model for the comboboxes
		cbKits.setModel(kitModel);
	}
	
	public void updatePartTypes(ArrayList<PartType> pt){
		partTypes = pt;
		// sets a new Object Array that acts as the model for the comboboxes
		partTypes.add(0,noPart);
		partModel = pt.toArray();
		updatePartComboModels();
	}
	
	/*
	 * Iterates through the list of KitConfigs to determine whether 
	 * a Kit has already been named the same as an input string.
	 */
	public boolean nameClear(String s) {
		for (KitConfig k : kitConfigs) {
			if (k.getName().equals(s)) {
				return false;
			}
		}
		return true;
	}
	
	public void showButtons(String panel) {
		CardLayout cl = (CardLayout)(pnlButtons.getLayout());
        cl.show(pnlButtons, panel);
	}
}

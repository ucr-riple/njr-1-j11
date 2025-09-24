package GUI;

import DATA.DataStore;
import DATA.Field;
import DATA.Teacher;
import DATA.Time;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.JTabbedPane;

import java.awt.Insets;

import javax.swing.AbstractListModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class TeacherPanel extends JPanel implements ActionListener, KeyListener, ListSelectionListener{

	private ArrayList<Teacher> teachers;
	
	private JPanel infoPanel;
	private JPanel infosPanel;
	private JPanel disabledPanel;
	private JPanel fieldPanel;
	private JButton deleteTeacherBtn;
	private JTextField teacherLastNameField;
	private JTextField teacherFirstNameField;
	private JLabel teacherMailLabel;
	private JTable table;
	private JComboBox comboBox;
	private String[] comboData;
	private JList<Teacher> teacherList;
	
	private TeacherListModel teacherListModel;
	private Teacher selectedTeacher;
	
	private DataStore dataStore;

	/**
	 * Create the application.
	 * @param startFrame 
	 */
	public TeacherPanel(DataStore ds) {
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				teachers = dataStore.getTeachers();
				JTable table = getJTableField();
				fieldPanel.add(new JScrollPane(table), BorderLayout.CENTER);
				teacherListModel = new TeacherListModel(dataStore.getTeachers());
			}
			
			public void componentHidden(ComponentEvent e)	{
				dataStore.setTeachers(teachers);
			}
		});
		this.dataStore = ds;
		this.teachers = dataStore.getTeachers();
		this.teacherListModel = new TeacherListModel(this.dataStore.getTeachers());
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel teacherListPanel = new JPanel();
		teacherListPanel.setPreferredSize(new Dimension(200, 200));
		this.add(teacherListPanel, BorderLayout.WEST);
		GridBagLayout gbl_teacherListPanel = new GridBagLayout();
		gbl_teacherListPanel.columnWidths = new int[] {0, 1};
		gbl_teacherListPanel.rowHeights = new int[] {0, 0, 0, 1};
		gbl_teacherListPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_teacherListPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		teacherListPanel.setLayout(gbl_teacherListPanel);
		
		JLabel lblListeDesEnseignants = new JLabel("Liste des enseignants :");
		GridBagConstraints gbc_lblListeDesEnseignants = new GridBagConstraints();
		gbc_lblListeDesEnseignants.anchor = GridBagConstraints.WEST;
		gbc_lblListeDesEnseignants.insets = new Insets(0, 0, 5, 0);
		gbc_lblListeDesEnseignants.gridx = 0;
		gbc_lblListeDesEnseignants.gridy = 0;
		teacherListPanel.add(lblListeDesEnseignants, gbc_lblListeDesEnseignants);
		
		teacherList = new JList<Teacher>(this.teacherListModel);
		teacherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teacherListModel.sort();
		teacherList.addListSelectionListener(this);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		teacherListPanel.add(new JScrollPane(teacherList), gbc_list);
		
		JButton addTeacherBtn = new JButton("Ajouter un enseignant");
		addTeacherBtn.setActionCommand("addTeacher");
		addTeacherBtn.addActionListener(this);
		GridBagConstraints gbc_addTeacherBtn = new GridBagConstraints();
		gbc_addTeacherBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addTeacherBtn.gridx = 0;
		gbc_addTeacherBtn.gridy = 2;
		teacherListPanel.add(addTeacherBtn, gbc_addTeacherBtn);
		
		JTabbedPane teacherTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.add(teacherTabbedPane, BorderLayout.CENTER);
		
		infoPanel = new JPanel();
		teacherTabbedPane.addTab("Informations générales", null, infoPanel, null);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		disabledPanel = new JPanel();
		disabledPanel.setLayout(new BorderLayout(0,0));
		disabledPanel.setEnabled(true);
		infoPanel.add(disabledPanel, BorderLayout.CENTER);
		
		JLabel disabledLabel = new JLabel("Sélectionnez un enseignant, ou ajoutez-en un.");
		disabledLabel.setHorizontalAlignment(SwingConstants.CENTER);
		disabledPanel.add(disabledLabel, BorderLayout.CENTER);
		
		infosPanel = new JPanel();
		infosPanel.setEnabled(false);
		//infoPanel.add(infosPanel, BorderLayout.CENTER);
		GridBagLayout gbl_infosPanel = new GridBagLayout();
		gbl_infosPanel.columnWidths = new int[]{0, 0, 0};
		gbl_infosPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_infosPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_infosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		infosPanel.setLayout(gbl_infosPanel);
		
		JLabel lblNom = new JLabel("Nom :");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		infosPanel.add(lblNom, gbc_lblNom);
		
		teacherLastNameField = new JTextField();
		teacherLastNameField.addKeyListener(this);
		teacherLastNameField.addActionListener(this);
		GridBagConstraints gbc_teacherLastNameField = new GridBagConstraints();
		gbc_teacherLastNameField.insets = new Insets(0, 0, 5, 0);
		gbc_teacherLastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_teacherLastNameField.gridx = 1;
		gbc_teacherLastNameField.gridy = 0;
		infosPanel.add(teacherLastNameField, gbc_teacherLastNameField);
		teacherLastNameField.setColumns(10);
		
		JLabel lblPrnom = new JLabel("Prénom :");
		GridBagConstraints gbc_lblPrnom = new GridBagConstraints();
		gbc_lblPrnom.anchor = GridBagConstraints.EAST;
		gbc_lblPrnom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrnom.gridx = 0;
		gbc_lblPrnom.gridy = 1;
		infosPanel.add(lblPrnom, gbc_lblPrnom);
		
		teacherFirstNameField = new JTextField();
		teacherFirstNameField.addActionListener(this);
		teacherFirstNameField.addKeyListener(this);
		GridBagConstraints gbc_teacherFirstNameField = new GridBagConstraints();
		gbc_teacherFirstNameField.insets = new Insets(0, 0, 5, 0);
		gbc_teacherFirstNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_teacherFirstNameField.gridx = 1;
		gbc_teacherFirstNameField.gridy = 1;
		infosPanel.add(teacherFirstNameField, gbc_teacherFirstNameField);
		teacherFirstNameField.setColumns(10);
		
		JLabel lblAdresseEmail = new JLabel("Adresse email :");
		GridBagConstraints gbc_lblAdresseEmail = new GridBagConstraints();
		gbc_lblAdresseEmail.anchor = GridBagConstraints.EAST;
		gbc_lblAdresseEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblAdresseEmail.gridx = 0;
		gbc_lblAdresseEmail.gridy = 2;
		infosPanel.add(lblAdresseEmail, gbc_lblAdresseEmail);
		
		teacherMailLabel = new JLabel("");
		GridBagConstraints gbc_teacherMailLabel = new GridBagConstraints();
		gbc_teacherMailLabel.gridx = 1;
		gbc_teacherMailLabel.gridy = 2;
		infosPanel.add(teacherMailLabel, gbc_teacherMailLabel);
		
		JPanel panel_1 = new JPanel();
		infoPanel.add(panel_1, BorderLayout.SOUTH);
		
		deleteTeacherBtn = new JButton("Supprimer l'enseignant");
		deleteTeacherBtn.setVisible(false);
		panel_1.add(deleteTeacherBtn);
		
		this.fieldPanel = new JPanel();
		teacherTabbedPane.addTab("Matières enseignées", null, fieldPanel, null);
		fieldPanel.setLayout(new BorderLayout(0, 0));
		
		table = getJTableField();
		fieldPanel.add(new JScrollPane(table));
		
		JPanel panel = new JPanel();
		fieldPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton newFieldBtn = new JButton("Ajouter matière");
		newFieldBtn.addActionListener(new NewFieldListener());
		panel.add(newFieldBtn);
		
		JButton delFieldBtn = new JButton("Supprimer matière");
		delFieldBtn.addActionListener(new DelFieldListener());
		panel.add(delFieldBtn);
		
		/*
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		this.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		mnFichier.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnFichier);
		
		JMenuItem mntmImporterDesEnseignants = new JMenuItem("Importer des enseignants...");
		mntmImporterDesEnseignants.setBackground(Color.WHITE);
		mnFichier.add(mntmImporterDesEnseignants);
		
		JMenuItem mntmExporterDesEnseignants = new JMenuItem("Exporter la liste...");
		mntmExporterDesEnseignants.setBackground(Color.WHITE);
		mnFichier.add(mntmExporterDesEnseignants);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setBackground(Color.WHITE);
		mnFichier.add(mntmQuitter);
		*/
		//System.out.println("Test");
	}
	
	private JTable getJTableField() {
		
		// Nom des colonnes
		String[] columnNames = {"Matière enseignée"};
		
		// On s'occupe de la ComboBox
		ArrayList<Field> fields = dataStore.getFields();
		
		//System.out.println("tp : " + fields.size());
		
		comboData = new String[fields.size() + 1];
		comboData[0] = ""; // Première cellule vide
		for (int i = 0; i < fields.size(); i++) {
			comboData[i+1] = fields.get(i).toString();
		}
		comboBox = new JComboBox(comboData);
		//System.out.println("tp : " + comboBox.getItemCount());

		// On peuple le tableau avec des données vides
		Object[][] data = {{comboData[0]}};
		//System.out.println("tp : " + data[0].length);

		// On crée le tableau
		FieldTableModel model = new FieldTableModel(data, columnNames);
		JTable table = new JTable(model);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboBox));
		table.setGridColor(Color.LIGHT_GRAY);
		
		return table;
	}
	
	/** 
	 * Met à jour le tableau des matières en fonction de l'enseignant.
	 * @param teacher L'enseignant
	 */
	public void populateTableForTeacher(Teacher teacher) {
		ArrayList<Field> teacherFields = teacher.getFields();
		Object[][] data = new Object[teacherFields.size()][1];
		for (int i = 0; i < teacherFields.size(); i++) {
			data[i][0] = teacherFields.get(i).toString();
		}
		
		((FieldTableModel)table.getModel()).loadData(data);
	}
	
	class NewFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object[] data = new Object[]
		            {comboData[0]};
		         ((FieldTableModel)table.getModel()).addRow(data);
		    table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
		}
	}
	
	class DelFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			if(row >= 0)
				((FieldTableModel)table.getModel()).removeRow(row);
		}
	}
	
	public class TeacherListModel extends AbstractListModel<Teacher> {

		private ArrayList<Teacher> teachers;
		
		public TeacherListModel(ArrayList<Teacher> teachers) {
			this.teachers = teachers;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return this.teachers.size();
		}

		public Teacher getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.teachers.get(index);
		}
		
		public void sort() {
		    Collections.sort(teachers);
		    fireContentsChanged(this, 0, teachers.size());
		}

		public void addElement(Teacher newTeacher) {
			//System.out.println("TeacherPanel.addElement() : " + newTeacher);
			this.teachers.add(newTeacher);
			fireContentsChanged(this, 0, teachers.size());
		}
	}
	
	/**
	 * Méthode gérant la sélection d'un enseignant dans la liste.
	 */
	public void valueChanged(ListSelectionEvent e) {
		// Si un élément de la liste est sélectionné.
		if(!e.getValueIsAdjusting() && teacherList.getSelectedValuesList().size() > 0) {
			// On affiche les champs.
			//System.out.println("TeacherPanel.valueChanged() : " + e.getSource());
			infoPanel.remove(disabledPanel);
			infoPanel.add(infosPanel, BorderLayout.CENTER);
			// On y place les bonnes infos.
			selectedTeacher = teacherList.getSelectedValue();
			teacherFirstNameField.setText(selectedTeacher.getFirstName());
			teacherLastNameField.setText(selectedTeacher.getLastName());
			teacherMailLabel.setText(selectedTeacher.getMail());
			// On met à jour le tableau de matières
			this.populateTableForTeacher(selectedTeacher);
		} else {
			// Sinon, on affiche le message qui prie l'utilisateur de sélectionner un professeur.
			infoPanel.remove(infosPanel);
			infoPanel.add(disabledPanel, BorderLayout.CENTER);
		}
	}	
	
	class FieldTableModel extends AbstractTableModel {
	    private Object[][] data;
	    private String[] titles;
	    
	    public FieldTableModel(Object[][] data, String[] titles) {
	        super();
	        this.titles = titles;
	        this.data = data;
	    }
	 
	    public void loadData(Object[][] data) {
	    	this.data = data;
	    	this.fireTableDataChanged();
	    }
	    
	    public int getRowCount() {
	        return data.length;
	    }
	 
	    public int getColumnCount() {
	        return titles.length;
	    }
	 
	    public String getColumnName(int columnIndex) {
	        return titles[columnIndex];
	    }
	 
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        switch(columnIndex){
	            case 0:
	                return data[rowIndex][columnIndex].toString();
	            default:
	                return data[rowIndex][columnIndex]; //Ne devrait jamais arriver
	        }
	    }
	    
	    public void setValueAt(Object value, int row, int col) {
	        //On interdit la modification sur certaines colonnes !
	    	
	    	// Matière modifiée.
	    	if(col == 0 && !value.equals("")) {
	    		Field theField = null;
	    		// On récupère le nom de la matière, puis on retrouve le bon objet Field en fonction de son nom
	    		for (Field field : dataStore.getFields()) {
	    			if(field.toString().equals(value)) {
	    				theField = field;
	    			}
	    		}
	    		
	    		// On vérifie que la matière n'existe pas déjà
	    		int count = 0;
	    		for (int i = 0; i < selectedTeacher.getFields().size(); i++) {
	    			if(selectedTeacher.getFields().get(i).toString().equals(value) && i != row) {
	    				count++;
	    			}
	    		}
	    		
	    		// Si l'utilisateur met à jour une matière, alors on modifie le field dans l'ArrayList.
	    		if (count == 0) {
	    			if(row < selectedTeacher.getFields().size()) {
		    			selectedTeacher.updateFieldAt(row, theField);
		    		} else {
			    		selectedTeacher.addField(theField);
		    		}
	    			this.data[row][col] = value;
	    		} else {
	    			JOptionPane.showMessageDialog(null, "Cette matière est déjà attribuée pour cet enseignant !");
	    		}
	    	}
	     }
	    
	    public void removeRow(int position) {
	    	
	    	// Si la matière est renseignée, on la supprime de la liste des matières de l'enseignant.
			if(!table.getModel().getValueAt(position, 0).equals("")) {
				selectedTeacher.removeFieldAt(position);
			}
			
			// Puis on supprime la ligne dans le tableau
	    	int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
	        Object temp[][] = new Object[nbRow][nbCol];
	         
	        for(Object[] value : this.data){
	           if(indice != position){
	              temp[indice2++] = value;
	           }
	           System.out.println("Indice = " + indice);
	           indice++;
	        }
	        this.data = temp;
	        temp = null;
	        this.fireTableDataChanged();

	    }
	    //Retourne la classe de la donnée de la colonne
	    public Class getColumnClass(int col){
	    	return this.data[0][col].getClass();
	    }
	    
	    public boolean isCellEditable(int row, int col){
	    	return true;
	    }
	    
	    //Permet d'ajouter une ligne dans le tableau
	    public void addRow(Object[] data){
	       int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
	        
	       Object temp[][] = this.data;
	       this.data = new Object[nbRow+1][nbCol];
	        
	       for(Object[] value : temp)
	          this.data[indice++] = value;
	       
	       this.data[indice] = data;
	       temp = null;
	       //Cette méthode permet d'avertir le tableau que les données
	       //ont été modifiées, ce qui permet une mise à jour complète du tableau
	       this.fireTableDataChanged();
	    }
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Si le bouton "Ajouter enseignant est cliqué".
		if(command.matches("addTeacher")) {
			Teacher newTeacher = new Teacher(0, "", "", new Field[]{}, new Time((byte)1, (byte)1, (byte)1));
			((TeacherListModel) teacherList.getModel()).addElement(newTeacher);
			teacherList.setSelectedValue(newTeacher, true);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		// On met à jour l'enseignant et on repaint la JList
		selectedTeacher = teacherList.getSelectedValue();
		selectedTeacher.setFirstName(teacherFirstNameField.getText()).setLastName(teacherLastNameField.getText());
		teacherMailLabel.setText(selectedTeacher.getMail());
		teacherList.repaint();
	}
	
}



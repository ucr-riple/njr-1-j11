package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import DATA.Classroom;
import DATA.DataStore;
import DATA.Group;
import DATA.Teacher;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private DataStore dataStore;
	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnNew;
	private JButton btnTeachers;
	private AbstractButton btnClassrooms;
	private JButton btnGroups;
	private JButton btnFields;
	private JButton btnWeekTable;
	private JButton btnLancer;
	private JButton btnFillFrame;
	private JPanel listPanel;
	private JPanel listPanel2;
	private JPanel EdTPanel;
	
	public MainFrame(DataStore ds) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.dataStore = ds;
		
		contentPane = new ContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setResizable(true);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setMargin(new Insets(5, 5, 5, 5));
		toolBar.setBackground(new Color(230, 230, 230));
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		try {
			btnSave = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/disk.png"))));
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BufferedReader br;
					try {
						br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/dataStoreLocation.loc"));
						String fileName = "";
						String sCurrentLine = "";
						while ((sCurrentLine = br.readLine()) != null) {
							fileName = sCurrentLine;
						}
						File fichier =  new File(fileName) ;
						 // ouverture d'un flux sur un fichier
						ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;

						 // sérialization de l'objet
						oos.writeObject(dataStore);
				    	JOptionPane.showMessageDialog(null, "La sauvegarde a été réalisée avec succès.", "Sauvegarde réussie", JOptionPane.INFORMATION_MESSAGE);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnSave.setText("Enregistrer");
			
			btnOpen = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/folder.png"))));
			btnOpen.setText("Ouvrir");
			btnOpen.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
int n = JOptionPane.showConfirmDialog(null, "Voulez sauvegarder la session en cours ?", "Attention", JOptionPane.YES_NO_CANCEL_OPTION);
					
					switch(n)	{
					case JOptionPane.YES_OPTION :
						BufferedReader br;
						try {
							br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/dataStoreLocation.loc"));
							String fileName = "";
							String sCurrentLine = "";
							while ((sCurrentLine = br.readLine()) != null) {
								fileName = sCurrentLine;
							}
							File fichier =  new File(fileName) ;
							 // ouverture d'un flux sur un fichier
							ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;

							 // sérialization de l'objet
							oos.writeObject(dataStore);
					    	JOptionPane.showMessageDialog(null, "La sauvegarde a été réalisée avec succès.", "Sauvegarde réussie", JOptionPane.INFORMATION_MESSAGE);

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
						
						
					case JOptionPane.NO_OPTION :
						break;
						
					case JOptionPane.CANCEL_OPTION :
						return;
					}
					
					
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Emploi du Temps", "EdT"));
					int res = fc.showOpenDialog(null);
					File f = null;
					if (res == JFileChooser.APPROVE_OPTION)	{
						// On a le fichier.
						f = fc.getSelectedFile();
						// On sauvegarde sa localisation pour la prochaine ouverture du programme.
						File dataStoreLocation = new File(System.getProperty("user.dir") + "/dataStoreLocation.loc");
						BufferedWriter writer;
						try {
							writer = new BufferedWriter(new FileWriter(dataStoreLocation));
							writer.write (f.getAbsolutePath()); // On écrit le chemin du DataStore
							writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Impossible de sauvegarde la localisation du fichier de sauvegarde.", "Erreur de sauvegarde", JOptionPane.ERROR_MESSAGE);
						}

						// On essaie maintenant de charger le DataStore
						try
						{
							FileInputStream inputFileStream = new FileInputStream(f.getAbsolutePath());
							ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
							dataStore = (DataStore)objectInputStream.readObject();
							objectInputStream.close();
							inputFileStream.close();
						}
						catch(ClassNotFoundException e1)
						{
							JOptionPane.showMessageDialog(null, "Erreur. Le fichier de sauvegarde semble corrumpu. Veuillez réessayer ou recréer une nouvelle base de données.", "Fichier de sauvegarde corrompu", JOptionPane.ERROR_MESSAGE);
							new WelcomeFrame();
						}
						catch(IOException i)
						{
							JOptionPane.showMessageDialog(null, "Fichier de sauvegarde introuvable", "Le fichier de sauvegarde est introuvable ; merci de le localiser à nouveau.", JOptionPane.ERROR_MESSAGE);
							new WelcomeFrame();
						}

						new MainFrame(dataStore);
					}
					setVisible(false);
				}
			});
			
			btnNew = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/table_add.png"))));
			btnNew.setText("Nouveau");
			btnNew.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = JOptionPane.showConfirmDialog(null, "Voulez sauvegarder la session en cours ?", "Attention", JOptionPane.YES_NO_CANCEL_OPTION);
					
					switch(n)	{
					case JOptionPane.YES_OPTION :
						BufferedReader br;
						try {
							br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/dataStoreLocation.loc"));
							String fileName = "";
							String sCurrentLine = "";
							while ((sCurrentLine = br.readLine()) != null) {
								fileName = sCurrentLine;
							}
							File fichier =  new File(fileName) ;
							 // ouverture d'un flux sur un fichier
							ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;

							 // sérialization de l'objet
							oos.writeObject(dataStore);
					    	JOptionPane.showMessageDialog(null, "La sauvegarde a été réalisée avec succès.", "Sauvegarde réussie", JOptionPane.INFORMATION_MESSAGE);

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
						
						
					case JOptionPane.NO_OPTION :
						break;
						
					case JOptionPane.CANCEL_OPTION :
						return;
					}
					
					new StartFrame();
					setVisible(false);
				}
			});

			btnTeachers = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/user_red.png"))));
			btnTeachers.setText("Enseignants");
			btnTeachers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new TeacherPanel(dataStore));
					frame.setVisible(true);
				}
			});
			
			btnClassrooms = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/door_open.png"))));
			btnClassrooms.setText("Salles");
			btnClassrooms.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new ClassroomPanel(dataStore));
					frame.setVisible(true);
				}
			});

			btnGroups = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/group.png"))));
			btnGroups.setText("Groupes");
			btnGroups.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new GroupPanel(dataStore));
					frame.setVisible(true);
					frame.addFocusListener(new FocusListener() {
						
						@Override
						public void focusLost(FocusEvent e) {
							dataStore.setGroups(((GroupPanel)(frame.getContentPane().getComponent(0))).getGroups());
						}
						
						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			});
			
			btnFields = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/book_open.png"))));
			btnFields.setText("Matières");
			btnFields.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new FieldPanel(dataStore));
					frame.setVisible(true);
				}
			});
			
			btnWeekTable = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/table.png"))));
			btnWeekTable.setText("Emploi du temps");
			btnWeekTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(btnWeekTable.isSelected()) {
						EdTPanel.setVisible(false);
						listPanel.setVisible(false);
						listPanel2.setVisible(false);
						btnWeekTable.setSelected(false);

					} else {
						EdTPanel.setVisible(true);
						listPanel.setVisible(true);
						listPanel2.setVisible(true);
						listPanel2.repaint();
						btnWeekTable.setSelected(true);
						
					}
				}
				
			});
			
			btnFillFrame = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/book_open.png"))));
			btnFillFrame.setText("Construire l'emploi du temps");
			btnFillFrame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*JFrame frame = new 
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new FieldPanel(dataStore));
					frame.setVisible(true);*/
				}
			});
			


			btnLancer = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/accept.png"))));
			btnLancer.setText("Lancer");
			btnLancer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new FillFrame(dataStore);
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		toolBar.add(btnNew);
		toolBar.add(btnOpen);
		toolBar.add(btnSave);
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension size = new Dimension(
			    separator.getPreferredSize().width,
			    separator.getMaximumSize().height);
		separator.setMaximumSize(size);
		toolBar.add(separator);
		toolBar.add(btnTeachers);
		toolBar.add(btnClassrooms);
		toolBar.add(btnGroups);
		toolBar.add(btnFields);
//		JSeparator separator_1 = new JSeparator(JSeparator.VERTICAL);
//		separator_1.setMaximumSize(size);
		toolBar.add(separator);
		toolBar.add(btnWeekTable);
		toolBar.add(separator);
		toolBar.add(btnLancer);
		
		
		JPanel leftContainer = new JPanel();
		
		EdTPanel = new JPanel();
		listPanel = new ListPanel(dataStore, this.EdTPanel);
		leftContainer.add(listPanel);
		listPanel2 = new ListPanel2(dataStore, this.EdTPanel);
		leftContainer.add(listPanel2);
		FlowLayout fl_leftContainer = new FlowLayout(FlowLayout.LEFT, 5, 5);
		leftContainer.setLayout(fl_leftContainer);
		
		getContentPane().add(leftContainer, BorderLayout.WEST);
		listPanel.setVisible(false);
		listPanel2.setVisible(false);
		
		Dimension s = new Dimension(925, 480);
		this.setMinimumSize(s);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == this.mntmTeachers) {
//			TeacherPanel teacherWindow = new TeacherPanel(this, this.dataStore);
//			teacherWindow.getRootPane().setVisible(true);
//		}
		
	}
	
	class ContentPane extends JPanel {
		private Image img;

		public ContentPane() {
			this.img = new ImageIcon("img/edt.png").getImage();
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, this.getWidth() / 2 - this.img.getWidth(null) / 2, this.getHeight() / 2 - this.img.getHeight(null) / 2, null);
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
			this.teachers.add(newTeacher);
			fireContentsChanged(this, 0, teachers.size());
		}
	}
	
	public class ClassroomListModel extends AbstractListModel<Classroom> {

		private ArrayList<Classroom> classrooms;
		
		public ClassroomListModel(ArrayList<Classroom> classrooms) {
			this.classrooms = classrooms;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return this.classrooms.size();
		}

		public Classroom getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.classrooms.get(index);
		}

		public void addElement(Classroom newClassroom) {
			this.classrooms.add(newClassroom);
			fireContentsChanged(this, 0, classrooms.size());
		}
	}
	
	public class GroupListModel extends AbstractListModel<Group> {

		private ArrayList<Group> groups;
		
		public GroupListModel(ArrayList<Group> groups) {
			this.groups = groups;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return this.groups.size();
		}

		public Group getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.groups.get(index);
		}

		public void addElement(Group newGroup) {
			this.groups.add(newGroup);
			fireContentsChanged(this, 0, groups.size());
		}
	}

	class ListPanel extends JPanel implements ListSelectionListener {
		
		private ArrayList<Teacher> teachers;
		private JList<Teacher> teacherList;
		private TeacherListModel teacherListModel;
		private JPanel EdTPanel;
		private Teacher selectedTeacher;
		
		public ListPanel(DataStore dataStore, JPanel EdTPanel) {
			
			this.EdTPanel = EdTPanel;
			this.teachers = dataStore.getTeachers();
			this.teacherListModel = new TeacherListModel(dataStore.getTeachers());
			
			teacherList = new JList<Teacher>(this.teacherListModel);
			teacherList.setPreferredSize(new Dimension(180, 800));
			teacherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			teacherListModel.sort();
			teacherList.addListSelectionListener(this);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.insets = new Insets(0, 0, 5, 0);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 1;
			this.add(new JScrollPane(teacherList), BorderLayout.CENTER);
			
			this.setPreferredSize(new Dimension(200, 800));
		}

		public void valueChanged(ListSelectionEvent e) {
			// Si un élément de la liste est sélectionné.
			if(!e.getValueIsAdjusting() && teacherList.getSelectedValuesList().size() > 0) {
				// On affiche les champs.
				
				// On y place les bonnes infos.
				selectedTeacher = teacherList.getSelectedValue();
				//System.out.println(selectedTeacher);
				// On affiche l'emploi du temps du prof
				JFrame frame = new JFrame(selectedTeacher.getName());
				frame.setContentPane(new EdTViewerPanel(selectedTeacher.getWeekTable()));
				frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		}
	}
	class ListPanel2 extends JPanel implements ListSelectionListener {
		
		private ArrayList<Group> groups;
		private JList<Group> groupList;
		private GroupListModel groupListModel;
		private JPanel EdTPanel;
		private Group selectedGroup;
		
		public ListPanel2(DataStore dataStore, JPanel EdTPanel) {
			
			this.EdTPanel = EdTPanel;
			this.groups = dataStore.getGroups();
			this.groupListModel = new GroupListModel(dataStore.getGroups());
			
			groupList = new JList<Group>(this.groupListModel);
			groupList.setPreferredSize(new Dimension(180, 800));
			groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			groupList.addListSelectionListener(this);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.insets = new Insets(0, 0, 5, 0);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 1;
			this.add(new JScrollPane(groupList), BorderLayout.CENTER);
			
			this.setPreferredSize(new Dimension(200, 800));
		}

		public void valueChanged(ListSelectionEvent e) {
			// Si un élément de la liste est sélectionné.
			if(!e.getValueIsAdjusting() && groupList.getSelectedValuesList().size() > 0) {
				// On affiche les champs.
				
				// On y place les bonnes infos.
				selectedGroup = groupList.getSelectedValue();
				//System.out.println(selectedGroup);
				// On affiche l'emploi du temps du prof
				JFrame frame = new JFrame(selectedGroup.toString());
				frame.setContentPane(new EdTViewerPanel(selectedGroup.getWeekTable()));
				frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		}
	}
}

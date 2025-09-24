package GUI;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DATA.DataStore;
import DATA.Filler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;

public class StartFrame extends JFrame {

	protected DataStore ds;
	public FieldPanel fp;
	public WeekPanel wp;
	public TeacherPanel tp;
	public ClassroomPanel cp;
	public LinkPanel lp;
	public GroupPanel gp;
	public JTabbedPane tabbedPane;
	
	public StartFrame() {
		super();

		this.ds = new DataStore();
		//this.ds.addFixtures();
		
		this.fp = new FieldPanel(this.ds);
		this.wp = new WeekPanel(this);
		this.tp = null;
		this.cp = null;
		this.gp = null;
		this.lp = null;
		
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println("TabbedPane.stateChanged() : " + ds);
				//ds.addFixtures();
				allowLinksTab();
				//pack();
			}
		});
		
		tabbedPane.addTab("Week", wp);
		tabbedPane.addTab("Fields", fp);
		tabbedPane.addTab("Teachers", tp);
		tabbedPane.addTab("Classrooms", cp);
		tabbedPane.addTab("Groups", gp);
		tabbedPane.addTab("Links", lp);
		tabbedPane.setSelectedIndex(0);
		
		tabbedPane.setEnabledAt(5, false);
		
		JButton btLoad = new JButton("Charger");
		btLoad.setToolTipText("Charge l'exemple par défaut");
		
		btLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ds = new DataStore();
				ds.addFixtures();
				
				// On enregistre le dataStore
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
					oos.writeObject(ds);
					oos.close();
					
					setVisible(false);
					new MainFrame(ds);
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btTer = new JButton("Terminer");
		btTer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// On enregistre le dataStore
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
					oos.writeObject(ds);
					oos.close();
					
					setVisible(false);
					new MainFrame(ds);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		getContentPane().add(tabbedPane);
		getContentPane().add(btLoad);
		getContentPane().add(btTer);
		
		this.setBounds(100, 100, 750, 550);
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.allowTimeableTabs(false);
		this.allowLinksTab();
		
		
		this.setVisible(true);
		
	}
	
	public void allowTimeableTabs(Boolean bool) {
		
		this.tabbedPane.setEnabledAt(2, bool);
		this.tabbedPane.setEnabledAt(3, bool);
		this.tabbedPane.setEnabledAt(4, bool);
		
		if (!bool)	{
			this.tabbedPane.setToolTipTextAt(2, "Veuillez compléter l'onglet Week pour pouvoir éditer les professeurs.");
			this.tabbedPane.setToolTipTextAt(3, "Veuillez compléter l'onglet Week pour pouvoir éditer les classes.");
			this.tabbedPane.setToolTipTextAt(4, "Veuillez compléter l'onglet Week pour pouvoir éditer les groupes.");
		}
		
		else	{
			this.cp = new ClassroomPanel(this.ds);
			this.tp = new TeacherPanel(this.ds);
			this.gp = new GroupPanel(this.ds);
			this.tabbedPane.remove(2);
			//this.tabbedPane.setTabComponentAt(2, this.tp);
			//this.tabbedPane.add(this.tp, 2);
			this.tabbedPane.insertTab("Teachers", null, this.tp, "", 2);
			this.tabbedPane.remove(3);
			//this.tabbedPane.setTabComponentAt(3, this.cp);
			//this.tabbedPane.add(this.cp, 3);
			this.tabbedPane.insertTab("Classrooms", null, this.cp, "", 3);
			this.tabbedPane.remove(4);
			//this.tabbedPane.setTabComponentAt(4, this.gp);
			//this.tabbedPane.add(this.gp, 4);
			this.tabbedPane.insertTab("Groups", null, this.gp, "", 4);
			this.tabbedPane.setToolTipTextAt(2, null);
			this.tabbedPane.setToolTipTextAt(3, null);
			this.tabbedPane.setToolTipTextAt(4, null);
		}
	}
	
	private void allowLinksTab()	{
		
		//System.out.println("allowLinksTab()\t" + this.ds);// + "\n\t" + this.ds.getTeachers() + "\n\t" + this.ds.getGroups() + "\n\t" + this.ds.getFields() + "\n\t" + this.ds.getTypes() + "\n\t" + this.ds.getDefaultWeek());
		
		if (this.lp == null && this.isActive())
			if (this.ds.getTeachers().size() > 0 && this.ds.getGroups().size() > 0 && this.ds.getFields().size() > 0)	{
				
				//System.out.println("true");
				this.lp = new LinkPanel(this);
				this.tabbedPane.remove(5);
				this.tabbedPane.addTab("Links", this.lp);
				this.tabbedPane.setEnabledAt(5, true);
			}
	}
}

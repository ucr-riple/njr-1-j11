package GUI;

/**
 *	The GUI used to add units and enviroments to Editmode as well as change levels
 *  @author tianyu shi
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import modes.EditMode;
import modes.EditMode.Type;
import editmode.CommandAdd;

public class EditModeGUI {
	private CommandAdd addCommand;
	private EditMode editmode;
	private int level;
	private int totallevels;

	private JFrame f = new JFrame("ADD"); //create Frame

	private JMenuBar mb = new JMenuBar(); 
	private JMenu mnuAddUnit = new JMenu("Add Unit");
	private JMenu mnuAddEnviroment = new JMenu("Add Enviroment");
	private JMenu mnuAddFactory = new JMenu("Add Factory");
	private JMenu mnuLevel = new JMenu("Level");
	private JMenu mnuHelp = new JMenu("Help"); 
	private JMenuItem mnuExit = new JMenuItem("Exit");

	private JMenuItem mnuPika = new JMenuItem("Pikachu"); 
	private JMenuItem mnuBulba = new JMenuItem("Bulbasaur");
	private JMenuItem mnuChar = new JMenuItem("Charmander");
	private JMenuItem mnuHooh = new JMenuItem("Hooh");
	private JMenuItem mnuLugia = new JMenuItem("Lugia");
	private JMenuItem mnuMew = new JMenuItem("Mew");
	private JMenuItem mnuSnor = new JMenuItem("Snorlax");
	private JMenuItem mnuPikaF = new JMenuItem("Pikachu Factory Unit");
	private JMenuItem mnuElec = new JMenuItem("Electrode");
	private JMenuItem mnuHewner = new JMenuItem("Hewner");
	private JMenuItem mnuWee = new JMenuItem("Weezing");

	private JMenuItem mnuSnow = new JMenuItem("Snow");
	private JMenuItem mnuPoisonGas = new JMenuItem("PoisonGas"); 
	private JMenuItem mnuMountain = new JMenuItem("Mountain");

	private JMenuItem mnuGuide = new JMenuItem("Unit Guide");

	public EditModeGUI(EditMode editmode){
		this.editmode = editmode;
		f.setJMenuBar(mb);

		totallevels = editmode.getTotalLevels();

		for(int i = 1; i < totallevels+1; i++) {
			JMenuItem level = new JMenuItem(""+i);
			level.addActionListener(new ListenMenuLevel(""+i));
			mnuLevel.add(level);
		}

		mnuAddUnit.add(mnuPika);
		mnuAddUnit.add(mnuBulba);
		mnuAddUnit.add(mnuChar);
		mnuAddUnit.add(mnuHooh);
		mnuAddUnit.add(mnuLugia);
		mnuAddUnit.add(mnuMew);
		mnuAddUnit.add(mnuSnor);
		mnuAddUnit.add(mnuElec);
		mnuAddUnit.add(mnuHewner);
		mnuAddUnit.add(mnuWee);

		mnuAddFactory.add(mnuPikaF);

		mnuAddEnviroment.add(mnuSnow);
		mnuAddEnviroment.add(mnuPoisonGas);
		mnuAddEnviroment.add(mnuMountain);

		mnuHelp.add(mnuGuide);

		mb.add(mnuAddUnit);
		mb.add(mnuAddEnviroment);
		mb.add(mnuAddFactory);
		mb.add(mnuLevel);
		mb.add(mnuHelp);
		mb.add(mnuExit);

		f.addWindowListener((WindowListener) new ListenCloseWdw());

		mnuPika.addActionListener(new ListenMenuAddUnit("Pikachu"));
		mnuBulba.addActionListener(new ListenMenuAddUnit("Bulbasaur"));
		mnuChar.addActionListener(new ListenMenuAddUnit("Charmander"));
		mnuHooh.addActionListener(new ListenMenuAddUnit("Hooh"));
		mnuLugia.addActionListener(new ListenMenuAddUnit("Lugia"));
		mnuMew.addActionListener(new ListenMenuAddUnit("Mew"));
		mnuSnor.addActionListener(new ListenMenuAddUnit("Snorlax"));
		mnuBulba.addActionListener(new ListenMenuAddUnit("Bulbasaur"));
		mnuPikaF.addActionListener(new ListenMenuAddUnit("PikachuFactory"));
		mnuElec.addActionListener(new ListenMenuAddUnit("Electrode"));
		mnuHewner.addActionListener(new ListenMenuAddUnit("Hewner"));
		mnuWee.addActionListener(new ListenMenuAddUnit("Weezing"));

		mnuSnow.addActionListener(new ListenMenuAddEnviroment("Snow"));
		mnuPoisonGas.addActionListener(new ListenMenuAddEnviroment("PoisonGas"));
		mnuMountain.addActionListener(new ListenMenuAddEnviroment("Mountain"));

		mnuGuide.addActionListener(new ListenMenuGuide());
		mnuExit.addActionListener(new ListenMenuExit());

	}

	/**
	 * If a unit is clicked, it sets a key and type value in editmode that indicates the next type of unit to be created
	 * 
	 */

	public class ListenMenuAddUnit implements ActionListener{
		private String key;
		public ListenMenuAddUnit(String key){this.key = key;}
		public void actionPerformed(ActionEvent e) {
			editmode.setkey(key);
			editmode.setType(Type.UNIT);
		}
	}

	public class ListenMenuAddFactory implements ActionListener{
		private String key;
		public ListenMenuAddFactory(String key){this.key = key;}
		public void actionPerformed(ActionEvent e) {
			editmode.setkey(key);
			editmode.setType(Type.FACTORY);
		}
	}

	public class ListenMenuAddEnviroment implements ActionListener{
		private String key;
		public ListenMenuAddEnviroment(String key){this.key = key;}
		public void actionPerformed(ActionEvent e) {			
			editmode.setkey(key);
			editmode.setType(Type.ENVIRONMENT);
		}
	}
	
	public class ListenMenuLevel implements ActionListener {
		private String key;
		public ListenMenuLevel(String key){this.key = key;}
		public void actionPerformed(ActionEvent e) {
			editmode.changelevel(Integer.parseInt(key));
		}
	}

	public class ListenMenuGuide implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		}
	}

	public class ListenMenuExit implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			editmode.exit();
		}
	}

	public class ListenCloseWdw extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);         
		}
	}

	public CommandAdd getCommand() {
		return addCommand;
	}

	public void hideFrame() {
		f.hide();
	}

	public void launchFrame(){
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack(); 
		f.setVisible(true);
	}
}
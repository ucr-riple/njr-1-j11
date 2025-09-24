package gui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.*; 

import javax.swing.text.DefaultCaret;

import org.jfree.ui.RefineryUtilities;
import org.junit.runner.Result;


import Deprecated.newMomentum;
import Deprecated.orderObject;
import Deprecated.signalObject;
import Selecting_Algothrim.lecMS;
import Selecting_Algothrim.lecMSMomentum;
import Trading_Engine.MyAskList;
import Trading_Engine.MyBidList;
import Trading_Engine.ResultData;
import Trading_Engine.myDatabase;

public class Mainmenu extends JFrame{
	public static File csv = null;
	public static JTextArea console;
	public JComboBox choosestrat;		
	public static myDatabase myDB;
	private ResultDisplay myResult;
	public Mainmenu(myDatabase db, Result result) { 
		final JFrame frame = this;
		setTitle("Algorithmic Trading System"); 
		setSize(800,600);
		setLocationRelativeTo( null );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		myDB = db;
		Container con = this.getContentPane();
		JPanel pane = new JPanel();
		con.add(pane); 
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem loadcsv = new JMenuItem("Load CSV File");
		file.add(loadcsv);
		file.add(quit);
		JMenu help = new JMenu("Help");	
		JMenuItem howtouse = new JMenuItem("How To Use");
		help.add(howtouse);

		menubar.add(help);
		setJMenuBar(menubar);
		pane.add(consolePanel(result));

		howtouse.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event){
						JDialog help = new helpDialog(frame);
					}
				});


		loadcsv.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						console.append("Loading CSV file...\n");
						JFileChooser chooser = new JFileChooser();
						int returnVal = chooser.showOpenDialog(null);
						if(returnVal == JFileChooser.APPROVE_OPTION){
							csv = chooser.getSelectedFile();
							try {
								myResult = new ResultDisplay("Analysis for " + csv.getName(),myDB,myDB.insertAll(csv,"all_list"));
							} catch (Exception e) {
								console.append("Cannot insert csv to database\n");
							}
							myResult.setVisible(true);
							console.append("CSV loaded.\nPlease select a strategy.\n");
						}
					}
				});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				myDB.closeDatabase();
				System.exit(0);
			}
		});

		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				myDB.closeDatabase();
				System.exit(0);
			}
		});
		setVisible(true);	
	}

	class helpDialog extends JDialog {
		public helpDialog(JFrame frame) {
			super (frame, false);
			JDialog help = new JDialog(frame, false);
			JPanel panel = new JPanel();
			getContentPane().add(panel);
			setResizable(false);
			setLocationRelativeTo(frame);
			setSize(400,220);
			setTitle("Help");
			JTextArea text = new JTextArea ("To open a CSV file, click File - Load a CSV. To run a strategy, a CSV file musted be selected. " +
					"A new window will open including new buttons to select your strategy. The console area will display the list of returns based on the result time after running a strategy. Visual and table aid can be found in the following window.");
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setEditable(false);
			text.setMargin(new Insets(15,15,15,15));
			panel.add(text);
			text.setPreferredSize(new Dimension( 350,150));
			JButton ok = new JButton ("Close");
			panel.add(ok);
			
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					setVisible(false);
				}
			});
			setVisible(true);
		}
	}
	private JScrollPane consolePanel (Result result){
		console = new JTextArea("");
		console.setBorder(BorderFactory.createLineBorder(Color.black));
		console.setEditable(false);
		console.setMargin(new Insets(10,10,10,10));
		if(result.wasSuccessful()){
			console.append("All system checks passed.\n");
			console.append("Prototype 2 Loaded. Please load a CSV file.\n");
		}else{
			console.append("System checks not passed.\n");
			console.append("Please close this software and contact the developer regarding this issue.\n");
		}
		JScrollPane consoletext = new JScrollPane(console); 
		consoletext.setPreferredSize(new Dimension(600, 450));
		consoletext.setMaximumSize((new Dimension(600, 450)));
		consoletext.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		DefaultCaret caret = (DefaultCaret)console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		return consoletext;
	}
}
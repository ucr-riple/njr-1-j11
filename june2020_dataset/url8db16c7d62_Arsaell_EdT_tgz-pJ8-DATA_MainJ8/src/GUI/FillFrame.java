package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.Box;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

//import com.sun.xml.internal.txw2.Document;

import DATA.Constrainable;
import DATA.DataStore;
import DATA.Filler;
import DATA.HashMap;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class FillFrame extends JFrame {

	private DataStore ds;
	
	private Filler filler;
	private Boolean showOutput = false;
	
	private JPanel panel_1;
	private JTextArea txtrResultats;
	private JLabel lblConsole;
	private JButton btnLancer, btnQuitter;
	private ButtonGroup btnGroup;

	private JPanel pane;
	
	public FillFrame(DataStore DS) {
		
		this.ds = DS;
		this.setBounds(100, 100, 450, 300);
		this.pane = new JPanel();
		this.setContentPane(pane);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JLabel lblTraitementDesErreurs = new JLabel("Traitement des erreurs :");
		lblTraitementDesErreurs.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(lblTraitementDesErreurs);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		pane.add(panel);
		
		final JRadioButton rdbtnIgnorer = new JRadioButton("Ignorer");
		
		final JRadioButton rdbtnArrter = new JRadioButton("Arrêter");
		
		final JRadioButton rdbtnRsoudre = new JRadioButton("Résoudre");
		
		this.btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnArrter);
		btnGroup.add(rdbtnIgnorer);
		btnGroup.add(rdbtnRsoudre);
		btnGroup.setSelected(rdbtnRsoudre.getModel(), true);
		
		panel.add(rdbtnIgnorer);
		panel.add(rdbtnArrter);
		panel.add(rdbtnRsoudre);
		
		this.btnLancer = new JButton("Lancer");
		btnLancer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					int mode = rdbtnArrter.isSelected() ? Filler.ABORT : rdbtnIgnorer.isSelected() ? Filler.IGNORE : Filler.RETRY;
					txtrResultats.setText("");
					File log = new File("temp.log");
					PrintStream ps = new PrintStream(log);
					Filler fill = new Filler(new TextAreaPrintStream(txtrResultats, ps), ds);
					//fill.setMode(mode);
					System.out.println("FillFrame.startFilling()");
					fill.fill(fill.computeConstraints(true), mode);
					System.out.println("FillFrame.endFilling()");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
					pane.remove(btnLancer);
					pane.add(btnQuitter);
			}
		});
		pane.add(btnLancer);
		
		this.btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		
		this.panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_1.setAlignmentX((float)0.5);
		pane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		this.lblConsole = new JLabel(">>> Console :");
		this.lblConsole.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				toggleShowOutPut();
			}
		});
		lblConsole.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				toggleShowOutPut();
			}
		});
		lblConsole.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblConsole);
		this.txtrResultats = new JTextArea();
		txtrResultats.addNotify();
		txtrResultats.setText("Résultats :");
		panel_1.add(txtrResultats);
		rdbtnRsoudre.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			}
		});
		setVisible(true);
	}

	private void toggleShowOutPut() {
		
		this.showOutput = !this.showOutput;
		if (this.showOutput)	{
			this.panel_1.add(txtrResultats);
			this.lblConsole.setText("vvv Console :");
		}
		else	{
			this.panel_1.remove(txtrResultats);
			this.lblConsole.setText(">>> Console :");
		}
			
	}
	
	private class TextAreaPrintStream extends PrintStream {

	    //The JTextArea to wich the output stream will be redirected.
	    private JTextArea textArea;


	    /**
	     * Method TextAreaPrintStream
	     * The constructor of the class.
	     * @param the JTextArea to wich the output stream will be redirected.
	     * @param a standard output stream (needed by super method)
	     **/
	    public TextAreaPrintStream(JTextArea area, OutputStream out) {
		super(out);
		textArea = area;
	    }

	    /**
	     * Method println
	     * @param the String to be output in the JTextArea textArea (private
	     * attribute of the class).
	     * After having printed such a String, prints a new line.
	     **/
	    public void println(String string) {
		textArea.append(string+"\n");
	    }



	    /**
	     * Method print
	     * @param the String to be output in the JTextArea textArea (private
	     * attribute of the class).
	     **/
	    public void print(String string) {
		textArea.append(string);
	    }
	}
}

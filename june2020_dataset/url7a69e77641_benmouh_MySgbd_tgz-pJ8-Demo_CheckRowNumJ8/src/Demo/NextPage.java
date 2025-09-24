package Demo;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

class NextPage extends JFrame {
	
	NextPage(String st) {
		
		setLayout(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Welcome");
		JLabel lab = new JLabel("Welcome  " + st + " in MySgbd");
		JTextArea tAout = new JTextArea();
		JTextArea tAin = new JTextArea();
		TextAreaOutputStream taos = new TextAreaOutputStream(tAout, "Output");
		PrintStream ps = new PrintStream(taos);
		System.setOut(ps);
		System.setErr(ps);

		lab.setBounds(10, 10, 500, 20);
		tAout.setBounds(500, 100, 400, 400);
		tAin.setBounds(10, 100, 400, 400);
		add(lab);
		add(tAout);
		add(tAin);
		setSize(1024, 768);
		/******
		 * 
		 * Traitement
		 */
		
		
		
		/*
		 * Fin 
		 */
	}
}

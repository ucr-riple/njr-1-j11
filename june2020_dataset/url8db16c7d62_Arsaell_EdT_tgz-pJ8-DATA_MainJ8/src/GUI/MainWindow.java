package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import DATA.DataStore;

public class MainWindow {

	private JFrame frame;

	public MainWindow() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton jbtn = new JButton("Compiler");
		jbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataStore ds = new DataStore();
				ds.addFixtures();
				new FillFrame(ds);
			}
		});
		frame.add(jbtn);
		frame.setVisible(true);
	}

	public static void main(String[] args)	{
		new MainWindow();
	}
}

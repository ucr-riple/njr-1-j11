package testbed;

import javax.swing.*;

class CitiesPanel extends JPanel {

	public CitiesPanel() {
		JButton b1 = new JButton("Berlin");
		add(b1);
		JButton b2 = new JButton("London");
		add(b2);
		JButton b3 = new JButton("Rome");
		add(b3);
		JButton b4 = new JButton("Moscow");
		add(b4);
	}
}

class ColorsPanel extends JPanel {
	public ColorsPanel() {
		JCheckBox cb1 = new JCheckBox("Red");
		add(cb1);
		JCheckBox cb2 = new JCheckBox("White");
		add(cb2);
		JCheckBox cb3 = new JCheckBox("Blue");
		add(cb3);
	}
}

class FlavorsPanel extends JPanel {
	public FlavorsPanel() {
		JComboBox<String> jcb = new JComboBox<String>();
		jcb.addItem("Vanilla");
		jcb.addItem("Chocolate");
		jcb.addItem("Strawberry");
		add(jcb);
	}
}

public class SwingTabs {
	
	SwingTabs(){
		JFrame jf = new JFrame("Tabs");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(640, 480);
		
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Cities", new CitiesPanel());
		jtp.addTab("Colors",  new ColorsPanel());
		jtp.addTab("Flavors", new FlavorsPanel());
		
		jf.add(jtp);
		jf.setVisible(true);		
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingTabs();
			}
		});
	}
}



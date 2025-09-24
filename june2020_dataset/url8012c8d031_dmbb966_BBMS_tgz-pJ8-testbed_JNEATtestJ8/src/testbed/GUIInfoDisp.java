package testbed;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIInfoDisp extends JPanel{
	
	public GUIInfoDisp() {
		setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
					
		JLabel jl = new JLabel("Information panel here.");
		add(jl);
	}
}

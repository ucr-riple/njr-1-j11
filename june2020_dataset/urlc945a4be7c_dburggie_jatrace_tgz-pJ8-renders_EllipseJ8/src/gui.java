import jatrace.gui.*;
import jatrace.gui.body.*;

import javax.swing.*;

public class gui
{
	
	public static void main(String [] args)
	{
		JFrame frame = new JFrame("Gui Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new BodyPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
}

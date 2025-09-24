package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import AMath.Calc;
import Avatar.AbstractInputConsole;
import Game.AGPmain;

public class APopupQuery {

	private static final JFrame FRAME = new JFrame();

	public static void set(Component parent, String prompt, String defaultContent, final Calc.Listener listener) {
		Container c = FRAME.getContentPane();
		c.removeAll();
		String[] prompts = prompt.split(";");
		for(String p : prompts) {if (p.length() > 0) {c.add(new JLabel(p));}}
		@SuppressWarnings("serial")
		final AbstractInputConsole field = new AbstractInputConsole(defaultContent) {
			@Override
			public void doCommand(String command) {
				try {
					FRAME.setVisible(false);
					listener.call(getText());
					AGPmain.mainGUI.repaintEverything();
				} catch (Exception e) {
					setText("*ILLEGAL ARGUMENT*");
				}
			}
		};
		c.add(field);
		final JButton okButton = new JButton("OK");
		c.add(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				field.doCommand(field.getText().toLowerCase());
			}
		});
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		FRAME.setVisible(true);
		FRAME.pack();

	}
	JFrame getFrame() {return FRAME;}
	
}
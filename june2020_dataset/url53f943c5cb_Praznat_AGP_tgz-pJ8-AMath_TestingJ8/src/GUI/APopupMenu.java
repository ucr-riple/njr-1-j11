package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

import javax.swing.*;

import AMath.Calc;
import Game.AGPmain;

@SuppressWarnings("rawtypes")
public class APopupMenu {

	private static final APopupMenu INSTANCE = new APopupMenu();
	private static final JFrame FRAME = new JFrame();
	private static Calc.Function<Object, Object> actionFunction = new Calc.Function<Object, Object>() {
		@Override
		public Object apply(Object x) {return null;}
	};

	public static void set(Component parent, String prompt, Collection<? extends Object> choices, Calc.Listener listener,
			Calc.Transformer transformer) {
		Container c = FRAME.getContentPane();
		c.removeAll();
		ButtonGroup bg = new ButtonGroup();
		String[] prompts = prompt.split(";");
		for(String p : prompts) {if (p.length() > 0) {c.add(new JLabel(p));}}
		for (Object obj : choices) {
			AMenuItem ami = AMenuItem.createNew(INSTANCE, obj, listener, transformer);
			c.add(ami);
			bg.add(ami);
		}
		final JButton okButton = new JButton("OK");
		c.add(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {actionFunction.apply(null);}
		});
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		FRAME.setVisible(true);
		FRAME.pack();

		//		int x, y;
		//		if (parent != null && parent.getMousePosition() != null) {
		//			x = parent.getMousePosition().x-5;
		//			y = parent.getMousePosition().y-5;
		//		}	else {x = 0; y = 0;}
		//		show(parent, x, y);

	}
	void setFunction(Calc.Function<Object, Object> fn) {actionFunction = fn;}
	JFrame getFrame() {return FRAME;}
}

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
class AMenuItem extends JRadioButton {

	public AMenuItem(String s) {super(s);}

	public static AMenuItem createNew(final APopupMenu parent, final Object obj,
			final Calc.Listener listener, final Calc.Transformer transformer) {
		AMenuItem AMI = new AMenuItem(transformer != null ? transformer.transform(obj).toString() : obj.toString());
		AMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				parent.setFunction(new Calc.Function<Object, Object>() {
					public Object apply(Object event) {
						parent.getFrame().setVisible(false);
						listener.call(obj);
						AGPmain.mainGUI.repaintEverything();
						return null;
					};
				});
			}
		});
		return AMI;
	}

}
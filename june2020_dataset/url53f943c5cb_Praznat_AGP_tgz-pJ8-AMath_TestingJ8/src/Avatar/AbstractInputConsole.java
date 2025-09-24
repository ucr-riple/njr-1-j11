package Avatar;

import java.awt.event.*;

import javax.swing.JTextField;


@SuppressWarnings("serial")
public abstract class AbstractInputConsole extends JTextField {

	private final KeyListener keyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent arg0) {}
		@Override
		public void keyReleased(KeyEvent arg0) {}
		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				doCommand(getText().toLowerCase());
			}
		}
	};
	
	public AbstractInputConsole() {
		this.addKeyListener(keyListener);
	}
	public AbstractInputConsole(String defaultString) {
		super(defaultString);
		this.addKeyListener(keyListener);
	}
	
	@Override
	public boolean isFocusable() {return true;}
	
	public abstract void doCommand(String command);
	
}

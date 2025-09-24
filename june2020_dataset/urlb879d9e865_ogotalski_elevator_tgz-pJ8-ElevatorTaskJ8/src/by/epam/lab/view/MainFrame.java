package by.epam.lab.view;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ELEVATOR_TASK = "Elevator Task";
	private static final Dimension DEFAULT_SIZE = new Dimension(1100, 800);


	public MainFrame() {
		super();
		this.setTitle(ELEVATOR_TASK);
		this.setMinimumSize(DEFAULT_SIZE);
		this.setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	}

	public void addComponent(JComponent component) {

		getContentPane().add(component);
	}

}

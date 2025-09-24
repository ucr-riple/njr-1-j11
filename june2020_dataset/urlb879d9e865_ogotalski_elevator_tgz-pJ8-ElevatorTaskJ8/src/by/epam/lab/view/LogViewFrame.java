package by.epam.lab.view;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogViewFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String LOGFILE_NAME = "logfile.log";
	private static final Dimension DEFAULT_SIZE = new Dimension(1000, 800);

	public LogViewFrame() throws HeadlessException {
		super();
		this.setTitle("Elevator Task");
		this.setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JTextArea logArea = new JTextArea();
		FileReader reader;
		try {
			reader = new FileReader(LOGFILE_NAME);
			logArea.read(reader, LOGFILE_NAME);
			JScrollPane scrollPane = new JScrollPane(logArea);
			scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
			add(scrollPane);
			setVisible(true);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}

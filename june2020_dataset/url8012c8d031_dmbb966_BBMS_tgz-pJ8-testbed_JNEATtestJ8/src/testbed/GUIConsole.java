package testbed;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GUIConsole extends JPanel{
	JTextField textField;
	JTextArea textArea;
	
	public GUIConsole() {
		setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		textArea = new JTextArea(8, 80);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);
	}
	
	public void GCOutput(String str) {
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}

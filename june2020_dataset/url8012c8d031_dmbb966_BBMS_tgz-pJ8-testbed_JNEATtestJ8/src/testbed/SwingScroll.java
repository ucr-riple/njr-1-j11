package testbed;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class TextDemo extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	
	public TextDemo() {
		super(new GridBagLayout());
		
		textField = new JTextField(20);
		textField.addActionListener(this);
		
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// Add components to the panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;		
		c.fill = GridBagConstraints.HORIZONTAL;	// Makes the component wide enough to fill the frame horizontally
		add(textField, c);
		
		c.fill = GridBagConstraints.BOTH;		// Makes the component tall/wide enough to fill the frame
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String text = textField.getText();
		textArea.append(text + newline);
		textField.selectAll();
		
		// Make sure the new text is visible even if there was a selection in the text area
		textArea.setCaretPosition(textArea.getDocument().getLength());		
	}
}

public class SwingScroll {
	
	SwingScroll(){
		JFrame jf = new JFrame("Scroll Text Output Demo");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(new TextDemo());
		
		jf.pack();
		jf.setVisible(true);		
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingScroll();
			}
		});
	}
}



/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui;

import javax.swing.*;
import java.awt.*;

/**
 * A KlangTextArea is a JTextArea wrapped in a JScrollPane
 * @author jhallida
 *
 */
public class KlangTextArea extends JScrollPane {
	
	private JTextArea theTextArea;
	
	/**
	 * Initiate the KlangTextArea
	 * @param initialText initial text for the text area; may be null for empty text area
	 */
	public KlangTextArea(String initialText) {
		if (initialText != null) {
			theTextArea = new JTextArea(initialText);
		} else {
			theTextArea = new JTextArea();
		}
		this.setPreferredSize(new Dimension(400, 400));
		this.setViewportView(theTextArea);
	}
	
	/**
	 * Retrieval method for the JTextArea itself
	 * @return the JTextArea itself
	 */
	public JTextArea getTextArea() {
		return theTextArea;
	}
	
	/**
	 * Convenience method for setting JTextArea's text
	 * @param value the new text for the JTextArea
	 */
	public void setText(String value) {
		theTextArea.setText(value);
	}
	
	/**
	 * Convenience method for JTextArea's text retrieval
	 * @return the text in the JTextArea
	 */
	public String getText() {
		return theTextArea.getText();
	}

}

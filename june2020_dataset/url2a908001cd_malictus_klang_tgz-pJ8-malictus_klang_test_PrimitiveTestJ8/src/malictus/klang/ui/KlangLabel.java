/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui;

import java.awt.*;
import javax.swing.*;

/**
 * A KlangLabel is actually a JTextField, but it's disguised to look like a label. The reason is so that labels can be copy/pasted easily.
 * @author Jim Halliday
 */
public class KlangLabel extends JTextField {
	
	/**
	 * Initialize the KlangLabel
	 * @param lblText initial label text
	 */
	public KlangLabel(String lblText) {
		super(lblText);
		this.setBorder(null);
		this.setOpaque(false);
		this.setEditable(false); 
		FontMetrics fm = this.getFontMetrics(this.getFont());
    	int height = fm.getHeight();
		//this is needed to make box layout work properly
		this.setMaximumSize(new java.awt.Dimension(10000, height + 6));
		this.setPreferredSize(new java.awt.Dimension(0, height + 6));
		//have to call it here manually to force a possible resize
		this.setText(lblText);
	}
	
	/**
	 * Override of set text, so that long labels will look correct
	 * @param text new text for the object
	 */
	public void setText(String text) {
		super.setText(text);
		this.setCaretPosition(0);
		FontMetrics fm = this.getFontMetrics(this.getFont());
		int height = fm.getHeight();
		int width = fm.stringWidth(text) + 2;
		this.setPreferredSize( new Dimension(width + 2, height + 6));
	}
	
	/**
	 * Override of set font
	 * @param font new font for the object
	 */
	public void setFont(Font font) {
		super.setFont(font);
		String text = "";
		try {
			text = this.getText();
		} catch (Exception err) {
			text = "";
		}
		this.setCaretPosition(0);
		FontMetrics fm = this.getFontMetrics(this.getFont());
		int height = fm.getHeight();
		int width = fm.stringWidth(text) + 2;
		this.setPreferredSize( new Dimension(width + 2, height + 6));
	}

}

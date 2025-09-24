/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui;

import java.awt.*;
import javax.swing.*;
import malictus.klang.*;

/**
 * A KlangTextField extends a text field and adds a few formatting niceties
 * @author Jim Halliday
 */
public class KlangTextField extends JTextField {
	
	/**
	 * Initialize the KlangTextField
	 * @param text initial text
	 */
	public KlangTextField(String text) {
		super(text);
		this.setFont(KlangConstants.KLANGEDITOR_TEXTFIELD_FONT);
		FontMetrics fm = this.getFontMetrics(this.getFont());
    	int height = fm.getHeight();
		//this is needed to make box layout work properly
		this.setMaximumSize(new java.awt.Dimension(10000, height + 10));
	}
	
	/**
	 * Initialize the KlangTextField
	 * @param text initial text
	 * @param minimumWidth a minimum width for the text field, regardless of content
	 */
	public KlangTextField(String text, int minimumWidth) {
		super(text);
		this.setFont(KlangConstants.KLANGEDITOR_TEXTFIELD_FONT);
		FontMetrics fm = this.getFontMetrics(this.getFont());
    	int height = fm.getHeight();
		//this is needed to make box layout work properly
		this.setMaximumSize(new java.awt.Dimension(10000, height + 10));
		this.setPreferredSize(new java.awt.Dimension(minimumWidth, height + 10));
	}
	
	/**
	 * Initialize the KlangTextField
	 */
	public KlangTextField() {
		super();
		this.setFont(KlangConstants.KLANGEDITOR_TEXTFIELD_FONT);
		FontMetrics fm = this.getFontMetrics(this.getFont());
    	int height = fm.getHeight();
		//this is needed to make box layout work properly
		this.setMaximumSize(new java.awt.Dimension(10000, height + 10));
	}
	
	/**
	 * Override of set text, so that long text fields will look correct
	 * @param text new text for the object
	 */
	public void setText(String text) {
		super.setText(text);
		this.setCaretPosition(0);
	}

}

/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;

/**
 * The file chooser for Klang Editor.
 */
public class KlangEditorFileChooser extends JFileChooser {

	/**
	 * Init the KlangEditorFileChooser with supported file types displayed
	 */
	public KlangEditorFileChooser() {
		super();
		KlangEditorFileFilter ff = new KlangEditorFileFilter();
		this.setAcceptAllFileFilterUsed(true);
        this.addChoosableFileFilter(ff);
        this.setMultiSelectionEnabled(false);
	}
	
}

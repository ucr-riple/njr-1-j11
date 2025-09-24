/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.filechooser.*;
import java.io.File;
import malictus.klang.*;

/**
 * The KlangEditorFileFilter will automatically filter the appropriate file types
 */
public class KlangEditorFileFilter extends FileFilter {

	/**
	 * Constructor for KlangEditorFileFilter
	 */
	public KlangEditorFileFilter() {
		super();
	}

	/**
	 * Overwritten to accept only the files that are specified
	 *
	 * @param f The file that we are testing to see whether or not it is allowed
	 * @return true if file should be accepted, false otherwise
	 */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String filename = f.getName();
		int i = filename.lastIndexOf('.');
		String extension = "";
        if ((i > 0) &&  (i < filename.length() - 1)) {
            extension = filename.substring(i+1);
        }
        extension = extension.toLowerCase();
        String[] exts = KlangConstants.KLANGEDITOR_SUPPORTED_FILE_EXTENSIONS.split(",");
        int counter = 0;
        while (counter < exts.length) {
        	String cand = exts[counter];
        	if (cand.trim().toLowerCase().equals(extension)) {
        		return true;
        	}
        	counter = counter + 1;
        }
        return false;
    }

    /**
     * A text description of this file filter
     * @return a text description of this file filter
     */
    public String getDescription() {
        return KlangConstants.KLANGEDITOR_SUPPORTED_FILE_EXTENSIONS_DESC;
    }

}


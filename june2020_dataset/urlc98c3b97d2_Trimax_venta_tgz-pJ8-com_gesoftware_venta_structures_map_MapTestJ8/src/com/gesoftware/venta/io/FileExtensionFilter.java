package com.gesoftware.venta.io;

import java.io.File;

/***
 * File extension filter class
 ***/
public final class FileExtensionFilter extends javax.swing.filechooser.FileFilter {
    private final String m_Extension;
    private final String m_Description;

    /* *
     * METHOD: Extension filter class constructor
     *  PARAM: [IN] extension   - extension to filter
     *  PARAM: [IN] description - file format description
     * AUTHOR: Eliseev Dmitry
     * */
    public FileExtensionFilter(final String extension, final String description) {
        m_Description = description;
        m_Extension   = extension;
    } /* End of 'FileExtensionFilter::FileExtensionFilter' method */

    @Override
    public final boolean accept(final File f) {
        return f != null && (f.isDirectory() || f.getName().toLowerCase().endsWith(m_Extension));
    } /* End of 'FileExtensionFilter::accept' method */

    /* *
     * METHOD: Get's description
     * RETURN: File format description
     * AUTHOR: Eliseev Dmitry
     * */
    public final String getDescription() {
        return m_Description;
    } /* End of 'FileExtensionFilter::getDescription' method */
} /* End of 'FileExtensionFilter' class */
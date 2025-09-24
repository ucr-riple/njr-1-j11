package com.gesoftware.venta.io;

import java.io.*;

/**
 * File saver class
 **/
public final class FileSaver {
    private ObjectOutputStream m_ObjectOutputStream;
    private FileOutputStream m_FileOutputStream;

    /* *
     * METHOD: File saver class constructor
     *  PARAM: [IN] fileName - file name to save data
     * AUTHOR: Eliseev Dmitry
     * */
    public FileSaver(final String fileName) {
        try {
            m_FileOutputStream = new FileOutputStream(fileName);
            m_ObjectOutputStream = new ObjectOutputStream(m_FileOutputStream);
        } catch (final IOException ignored) {
            close();
        }
    } /* End of 'FileSaver::FileSaver' method */

    /* *
     * METHOD: Writes an object to file
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] object - object to write
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean writeObject(final Serializable object) {
        try {
            if (m_ObjectOutputStream == null)
                return false;

            m_ObjectOutputStream.writeObject(object);
            return true;
        } catch (final IOException ignored) {}

        return false;
    } /* End of 'FileSaver::writeObject' method */

    /* *
     * METHOD: Closes file
     * AUTHOR: Eliseev Dmitry
     * */
    private void close() {
        try {
            if (m_ObjectOutputStream != null)
                m_ObjectOutputStream.close();
        } catch (final IOException ignored) {}

        try {
            if (m_FileOutputStream != null)
                m_FileOutputStream.close();
        } catch (final IOException ignored) {}

        m_ObjectOutputStream = null;
        m_FileOutputStream = null;
    } /* End of 'FileSaver::FileSaver' method */

    @Override
    protected final void finalize() throws Throwable {
        close();

        super.finalize();
    } /* End of 'FileSaver::finalize' method */
} /* End of 'FileSaver' class */

package com.gesoftware.venta.io;

import java.io.*;

public final class FileLoader {
    private ObjectInputStream m_ObjectInputStream;
    private FileInputStream m_FileInputStream;

    /* *
     * METHOD: File loader class constructor
     *  PARAM: [IN] fileName - file name to load data from
     * AUTHOR: Eliseev Dmitry
     * */
    public FileLoader(final String fileName) {
        try {
            m_FileInputStream = new FileInputStream(fileName);
            m_ObjectInputStream = new ObjectInputStream(m_FileInputStream);
        } catch (final IOException ignored) {
            close();
        }
    } /* End of 'FileLoader::FileLoader' method */

    /* *
     * METHOD: Reads an object from opened file
     * RETURN: Read object if success, null otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    public final Object readObject() {
        try {
            if (m_ObjectInputStream != null)
                return m_ObjectInputStream.readObject();
        } catch (final ClassNotFoundException ignored) {
        } catch (final IOException ignored) {
        }

        return null;
    } /* End of 'FileLoader::readObject' method */

    /* *
     * METHOD: Closes file
     * AUTHOR: Eliseev Dmitry
     * */
    public void close() {
        try {
            if (m_ObjectInputStream != null)
                m_ObjectInputStream.close();
        } catch (final IOException ignored) {}

        try {
            if (m_FileInputStream != null)
                m_FileInputStream.close();
        } catch (final IOException ignored) {}

        m_ObjectInputStream = null;
        m_FileInputStream = null;
    } /* End of 'FileLoader::close' method */

    @Override
    protected final void finalize() throws Throwable {
        close();

        super.finalize();
    } /* End of 'FileLoader::finalize' method */
} /* End of 'FileLoader' class */
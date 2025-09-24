package com.gesoftware.venta.utility;

import com.gesoftware.venta.logging.LoggingUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * ApplicationUtility class definition
 **/
public final class ApplicationUtility {
    /** The lock_file. */
    private File m_LockFile = null;

    /** The lock. */
    private FileLock m_Lock = null;

    /** The lock_channel. */
    private FileChannel m_LockChannel = null;

    /** The lock_stream. */
    private FileOutputStream m_LockStream = null;

    /* *
     * METHOD: Application utility class constructor
     *  PARAM: [IN] key - application name
     * AUTHOR: Eliseev Dmitry
     * */
    private ApplicationUtility(final String key) throws Exception {
        String tmp_dir = System.getProperty("java.io.tmpdir");
        if (!tmp_dir.endsWith(System.getProperty("file.separator")))
            tmp_dir += System.getProperty("file.separator");

        /* Trying to lock file */
        try {
            m_LockFile = new File(tmp_dir + HashUtility.generateHash(key) + ".app_lock");
        } catch (Exception e) {
            LoggingUtility.core("AppLock.AppLock() file fail: " + e.getMessage());
        }

        /* MD5 acquire fail */
        if (m_LockFile == null)
            m_LockFile = new File(tmp_dir + key + ".app_lock");

        m_LockStream = new FileOutputStream(m_LockFile);

        /* Trying to create lock */
        String f_content = "Java AppLock Object\r\nLocked by key: " + key + "\r\n";
        m_LockStream.write(f_content.getBytes());
        m_LockChannel = m_LockStream.getChannel();
        m_Lock = m_LockChannel.tryLock();

        if (m_Lock == null)
            throw new Exception("Can't create Lock");
    } /* End of 'ApplicationUtility::ApplicationUtility' method */

    @SuppressWarnings("all")
    public void release() throws Throwable {
        if (m_Lock.isValid())
            m_Lock.release();


        if (m_LockStream != null)
            m_LockStream.close();

        if (m_LockChannel.isOpen())
            m_LockChannel.close();

        if (m_LockFile.exists())
            m_LockFile.delete();
    }  /* End of 'ApplicationUtility::release' method */

    @Override
    protected final void finalize() throws Throwable {
        release();
        super.finalize();
    }  /* End of 'ApplicationUtility::finalize' method */

    /* Utility instance */
    private static ApplicationUtility m_Instance;

    /* *
     * METHOD: Sets lock for application
     * RETURN: True if lock successfully set, False otherwise (it means, that already exists another instances)
     *  PARAM: [IN] key - application name
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean setLock(final String key) {
        if (m_Instance != null)
            return true;

        try {
            m_Instance = new ApplicationUtility(key);
        } catch (Exception e) {
            m_Instance = null;
            LoggingUtility.core("Fail to set AppLoc: " + e.getMessage());
            return false;
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ApplicationUtility.releaseLock();
            }
        });
        return true;
    }

    /* *
     * METHOD: Releases lock for application
     * AUTHOR: Eliseev Dmitry
     * */
    private static void releaseLock() {
        try {
            if (m_Instance == null)
                throw new NoSuchFieldException("Instance is null");

            m_Instance.release();
        } catch (Throwable e) {
            LoggingUtility.core("Fail to release AppLoc: " + e.getMessage());
        }
    }  /* End of 'ApplicationUtility::ApplicationUtility' method */
}  /* End of 'ApplicationUtility' class */

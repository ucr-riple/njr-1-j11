package com.gesoftware.venta.io;

import java.util.Collection;
import java.util.ArrayList;
import java.io.File;

/**
 * File manager class definition
 **/
public final class FileManager {
    /* *
     * METHOD: Gets files names collection by directory
     * RETURN: Collection of files names
     *  PARAM: [IN] directory   - directory to get files list
     *  PARAM: [IN] isRecursive - recursive directory walking
     *  PARAM: [IN] isFullPaths - return colletion with full paths
     * AUTHOR: Eliseev Dmitry
     * */
    public static Collection<String> getFilesList(final String directory, final boolean isRecursive, final boolean isFullPaths) {
        final Collection<String> fileNames = new ArrayList<String>();
        getFilesList(directory, fileNames, isRecursive, isFullPaths);

        /* That's it */
        return fileNames;
    } /* End if 'FileManager::getFilesList' method */

    /* *
     * METHOD: Gets files names collection by directory
     *  PARAM: [IN] directory   - directory to get files list
     *  PARAM: [IN] fileNames   - files names collection
     *  PARAM: [IN] isRecursive - recursive directory walking
     *  PARAM: [IN] isFullPaths - return colletion with full paths
     * AUTHOR: Eliseev Dmitry
     * */
    private static void getFilesList(final String directory, final Collection<String> fileNames, final boolean isRecursive, final boolean isFullPaths) {
        final File dir = new File(directory);

        /* Obtain files list */
        final File files[] = dir.listFiles();
        if (files == null)
            return;

        /* Filter files */
        for (final File file : files)
            if (file.isFile())
                fileNames.add(isFullPaths?file.getAbsolutePath():file.getName());
            else if (isRecursive)
                getFilesList(file.getAbsolutePath(), fileNames, isRecursive, isFullPaths);
    } /* End if 'FileManager::getFilesList' method */
} /* End of 'FileManager' class */

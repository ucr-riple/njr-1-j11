/*
 * SimpleSpeedTests.java
 * 
 * Copyright (c) 2010, Ralf Biedert All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package sandbox;

import static net.jcores.jre.CoreKeeper.$;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.jcores.jre.cores.CoreZipInputStream;

/**
 * @author rb
 *
 */
public class SimpleUnzip {

    static Random rnd = new Random();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        //doUnzip("/tmp/1.zip", "/tmp/unz");

        $("/tmp/uunz").file().delete();
        CoreZipInputStream zipstream = $("/tmp/torate.zip").file().input().zipstream();
        // zipstream.dir().print();
        System.out.println(zipstream.get("experiment.9.pageThailand.1282134351569.1282134351928.png"));
        System.out.println(zipstream.get("experiment.8.pageHonigbiene.1282131517351.1282131517788.png"));

        /*
         * experiment.9.pageThailand.1282134341053.1282134341397.png
        experiment.9.pageThailand.1282134345022.1282134345444.png
        experiment.9.pageThailand.1282134351569.1282134351928.png
        experiment.9.pageThailand.1282134358163.1282134358538.png
        experiment.9.pageThailand.1282134358694.1282134359179.png
        experiment.9.pageThailand.1282134363726.1282134364179.png
         */

        //$("/tmp/1.zip").file().streams().unzip("/tmp/uunz");
    }

    /**
     * @param inputZip
     * @param destinationDirectory
     * @throws IOException
     */
    public static void doUnzip(String inputZip, String destinationDirectory)
                                                                            throws IOException {
        final int BUFFER = 8 * 1024;
        final List<String> zipFiles = new ArrayList<String>();
        final File sourceZipFile = new File(inputZip);
        final File unzipDestinationDirectory = new File(destinationDirectory);

        unzipDestinationDirectory.mkdir();

        final ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
        final Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            final ZipEntry entry = zipFileEntries.nextElement();
            final String currentEntry = entry.getName();
            final File destFile = new File(unzipDestinationDirectory, currentEntry);

            if (currentEntry.endsWith(".zip")) {
                zipFiles.add(destFile.getAbsolutePath());
            }

            // grab file's parent directory structure
            final File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            try {
                // extract file if not a directory
                if (!entry.isDirectory()) {
                    final BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    final FileOutputStream fos = new FileOutputStream(destFile);
                    final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        zipFile.close();

        for (Iterator<String> iter = zipFiles.iterator(); iter.hasNext();) {
            String zipName = iter.next();
            doUnzip(zipName, destinationDirectory + File.separatorChar + zipName.substring(0, zipName.lastIndexOf(".zip")));
        }
    }
}

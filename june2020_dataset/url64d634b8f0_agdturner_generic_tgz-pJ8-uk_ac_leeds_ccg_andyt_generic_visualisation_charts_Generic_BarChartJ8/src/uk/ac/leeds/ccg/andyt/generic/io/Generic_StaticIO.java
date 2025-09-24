/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
//import java.nio.file.S

//TODO http://java.sun.com/docs/books/tutorial/essential/io/legacy.html#mapping
// http://java.sun.com/docs/books/tutorial/essential/io/fileio.html
/**
 * Class of static methods for helping with reading and writing (primarily)
 * to/from file.
 */
public class Generic_StaticIO {

    public static final String CLASSNAME = "Generic_StaticIO";

    /**
     * Creates a new instance of ObjectIO
     */
    public Generic_StaticIO() {
    }

    public static TreeSet<String> recursiveFileList(File file) {
        TreeSet<String> result = new TreeSet<String>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                TreeSet<String> subresult = recursiveFileList(file1);
                result.addAll(subresult);
            }
            return result;
        } else {
            result.add(file.toString());
            return result;
        }
    }

    public static TreeSet<String> recursiveFileList(File file, int depth) {
        TreeSet<String> result = new TreeSet<String>();
        if (depth != 0) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    TreeSet<String> subresult = recursiveFileList(f, depth - 1);
                    result.addAll(subresult);
                }
                return result;
            } else {
                result.add(file.toString());
                return result;
            }
        }
        result.add(file.toString());
        return result;
    }

    /**
     * Write object to file
     *
     * @param object
     * @param f
     */
    public static void writeObject(
            Object object,
            File f) {
        try {
            ObjectOutputStream oos;
            oos = getObjectOutputStream(f);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * Read Object from File
     *
     * @param f
     * @return
     */
    public static Object readObject(
            File f) {
        Object result = null;
        if (f.length() != 0) {
            try {
                ObjectInputStream ois;
                ois = getObjectInputStream(f);
                result = ois.readObject();
                ois.close();
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            } catch (ClassNotFoundException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.ClassNotFoundException);
            }
        }
        return result;
    }

    /**
     * @param input_File A File which is not a Directory to be copied
     * @param outputDirectory_File The Directory to copy to.
     */
    private static void copyFile(
            File input_File,
            File outputDirectory_File) {
        copyFile(
                input_File,
                outputDirectory_File,
                input_File.getName());
    }

    /**
     * @param fileToCopy A File which is not a Directory to be copied
     * @param directoryToCopyInto A File that is the Directory to copy to.
     * @param outputFileName The name for the file that will be created in the
     * outputDirectory_File
     */
    public static void copyFile(
            File fileToCopy,
            File directoryToCopyInto,
            String outputFileName) {
        // String osName = System.getProperty( "os.name" );
        // if ( osName.equalsIgnoreCase( "UNIX" ) ) {
        // Runtime.getRuntime().exec( "cp " + _InputFile.toString() + " " +
        // _Output_File.toString() );
        // } else {
        // }
        if (!fileToCopy.exists()) {
            System.err.println(
                    "!input_File.exists() in "
                    + Generic_StaticIO.class.getCanonicalName()
                    + ".copy(File(" + fileToCopy + "),File(" + directoryToCopyInto + "))");
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        if (!directoryToCopyInto.exists()) {
            directoryToCopyInto.mkdirs();
        }
        File output_File = new File(
                directoryToCopyInto, outputFileName);
        if (output_File.exists()) {
            System.out.println(
                    "Overwriting File " + output_File + " in "
                    + Generic_StaticIO.class.getCanonicalName()
                    + ".copy(File(" + fileToCopy + "),"
                    + "File(" + directoryToCopyInto + "))");
        } else {
            try {
                output_File.createNewFile();
            } catch (IOException e) {
                System.err.print(e.getMessage());
                System.err.println(
                        "Unable to createNewFile " + output_File + " in "
                        + Generic_StaticIO.class.getCanonicalName()
                        + ".copy(File(" + fileToCopy + "),"
                        + "File(" + directoryToCopyInto + "))");
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            }
        }
        try {
            BufferedInputStream bis;
            bis = getBufferedInputStream(fileToCopy);
            BufferedOutputStream bos;
            bos = getBufferedOutputStream(output_File);
            // bufferSize should be power of 2 (e.g. Math.pow(2, 12)), but nothing too big.
            int bufferSize = 2048;
            long numberOfArrayReads = fileToCopy.length() / bufferSize;
            long numberOfSingleReads = fileToCopy.length() - (numberOfArrayReads * bufferSize);
            byte[] b = new byte[bufferSize];
            for (int i = 0; i < numberOfArrayReads; i++) {
                bis.read(b);
                bos.write(b);
            }
            for (int i = 0; i < numberOfSingleReads; i++) {
                bos.write(bis.read());
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * @param f
     * @return <code>new BufferedInputStream(new FileInputStream(f)))</code>
     */
    public static BufferedInputStream getBufferedInputStream(File f) {
        BufferedInputStream result = null;
        FileInputStream fis;
        fis = getFileInputStream(f);
        result = new BufferedInputStream(fis);
        return result;
    }

    /**
     * @param f
     * @return <code>new FileInputStream(f)</code>
     */
    public static FileInputStream getFileInputStream(File f) {
        FileInputStream result = null;
        try {
            result = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.err.println("Trying to handle " + ex.getLocalizedMessage());
            System.err.println("Wait for 2 seconds then trying again to getFileInputStream(File " + f.toString() + ").");
            if (!f.exists()) {
                //ex.printStackTrace(System.err);
                Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                long wait = 2000L;
                try {
                    synchronized (f) {
                        f.wait(wait);
                    }
                } catch (InterruptedException ex2) {
                    Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex2);
                }
                return getFileInputStream(f, wait);
            }
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /**
     * @param f
     * @param wait
     * @return
     * <code>new BufferedReader(new InputStreamReader(new FileInputStream(file)))</code>
     */
    public static FileInputStream getFileInputStream(File f, long wait) {
        FileInputStream result = null;
        try {
            result = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            System.err.println("Trying to handle " + ex.getLocalizedMessage());
            System.err.println("Wait for " + wait + " miliseconds then trying "
                    + "again to getBufferedReader(File " + f.toString() + ", long).");
            if (!f.exists()) {
                //ex.printStackTrace(System.err);
                Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (f) {
                        f.wait(wait);
                    }
                } catch (InterruptedException ex2) {
                    Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex2);
                }
                return getFileInputStream(f, wait * 2);
            }
//        } catch (IOException e) {
//            e.printStackTrace(System.err);
//            Logger.getLogger(Generic_IO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /**
     * @param f
     * @return <code>new FileOutputStream(f)</code>
     */
    public static FileOutputStream getFileOutputStream(File f) {
        FileOutputStream result = null;
        try {
            result = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * @param f
     * @return <code>new BufferedInputStream(new FileInputStream(f)))</code>
     */
    public static BufferedOutputStream getBufferedOutputStream(File f) {
        BufferedOutputStream result = null;
        FileOutputStream fos;
        fos = getFileOutputStream(f);
        result = new BufferedOutputStream(fos);
        return result;
    }

    /**
     * @param f
     * @return <code>new ObjectInputStream(getBufferedInputStream(f)</code>
     */
    public static ObjectInputStream getObjectInputStream(File f) {
        ObjectInputStream result = null;
        BufferedInputStream bis;
        bis = getBufferedInputStream(f);
        try {
            result = new ObjectInputStream(bis);
        } catch (IOException ex) {
            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * @param f
     * @return <code>new ObjectOutputStream(getBufferedOutputStream(f)</code>
     */
    public static ObjectOutputStream getObjectOutputStream(File f) {
        ObjectOutputStream result = null;
        BufferedOutputStream bos;
        bos = getBufferedOutputStream(f);
        try {
            result = new ObjectOutputStream(bos);
        } catch (IOException ex) {
            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private static void copyDirectory(
            File directoryToCopy,
            File directoryToCopyInto) {
        try {
            if (!directoryToCopyInto.mkdir()) {
                directoryToCopyInto.mkdirs();
            }
            if (!directoryToCopy.isDirectory()) {
                throw new IOException(
                        "Expecting File " + directoryToCopy
                        + " To be a directory in "
                        + Generic_StaticIO.class.getName()
                        + ".copyDirectory(File,File)");
            }
            if (!directoryToCopyInto.isDirectory()) {
                throw new IOException(
                        "Expecting File " + directoryToCopyInto
                        + " To be a directory in "
                        + Generic_StaticIO.class.getName()
                        + ".copy(File,File)");
            }
            File copiedDirectory_File = new File(
                    directoryToCopyInto,
                    directoryToCopy.getName());
            copiedDirectory_File.mkdir();
            File[] a_DirectoryToCopy_File_Files
                    = directoryToCopy.listFiles();
            for (File f : a_DirectoryToCopy_File_Files) {
                if (f.isFile()) {
                    copyFile(f, copiedDirectory_File);
                } else {
                    copyDirectory(f, copiedDirectory_File);
                }
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    public static void copy(
            File fileOrDirectoryToCopy,
            File directoryToCopyInto) {
        try {
            if (!directoryToCopyInto.mkdir()) {
                directoryToCopyInto.mkdirs();
            }
            if (!directoryToCopyInto.isDirectory()) {
                throw new IOException(
                        "Expecting File " + directoryToCopyInto
                        + "To be a directory in "
                        + Generic_StaticIO.class.getName() + ".copy(File,File)");
            }
            if (fileOrDirectoryToCopy.isFile()) {
//                File outputFile = new File(
//                        a_DirectoryToCopyInto_File,
//                        a_FileOrDirectoryToCopy_File.getName());
//                copyFile(
//                        a_FileOrDirectoryToCopy_File,
//                        outputFile);
                copyFile(
                        fileOrDirectoryToCopy,
                        directoryToCopyInto);
            } else {
                copyDirectory(
                        fileOrDirectoryToCopy,
                        directoryToCopyInto);
//)
//                File[] a_DirectoryToCopy_File_Files =
//                        a_FileOrDirectoryToCopy_File.listFiles();
//                File a_DirectoryToCopyInto_1_File = new File(
//                        a_DirectoryToCopyInto_File,
//                        a_FileOrDirectoryToCopy_File.getName());
//                for (int i = 0; i < a_DirectoryToCopy_File_Files.length; i++) {
////                    copy(
////                        a_DirectoryToCopy_File_Files[i],
////                        a_DirectoryToCopyInto_1_File);
////                    }
//////                    File a_DirectoryToCopyInto_2_File = new File(
//////                            a_DirectoryToCopyInto_1_File,
//////                            a_DirectoryToCopy_File_Files[i].getName());
//////                    if (a_DirectoryToCopy_File_Files[i].isFile()) {
//////                        copyFile(
//////                                a_DirectoryToCopy_File_Files[i],
//////                                a_DirectoryToCopyInto_2_File);
//////                    } else {
//////                        if (!a_DirectoryToCopyInto_2_File.mkdir()) {
//////                            a_DirectoryToCopyInto_2_File.mkdirs();
//////                        }
//////                        copy(
//////                                a_DirectoryToCopy_File_Files[i],
//////                                a_DirectoryToCopyInto_2_File);
//////                    }
////                    File a_DirectoryToCopyInto_2_File = new File(
////                            a_DirectoryToCopyInto_1_File,
////                            a_DirectoryToCopy_File_Files[i].getName());
//                    if (a_DirectoryToCopy_File_Files[i].isFile()) {
//                        copyFile(
//                                a_DirectoryToCopy_File_Files[i],
//                                a_DirectoryToCopyInto_1_File);
//                    } else {
//                        if (!a_DirectoryToCopyInto_1_File.mkdir()) {
//                            a_DirectoryToCopyInto_1_File.mkdirs();
//                        }
//                        copy(
//                                a_DirectoryToCopy_File_Files[i],
//                                a_DirectoryToCopyInto_1_File);
//                    }
//                }
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
    }

    /**
     * @return true iff file exists and can be read
     * @param file
     */
    public static boolean fileExistsAndCanBeRead(File file) {
        if (file.exists()) {
            if (file.canRead()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to delete file from the file system. This does no block other
     * reads or writes to the files system while it is executing and it will
     * only succeed in deleting empty directories.
     *
     * @param file
     * @return long[] result where: result[0] is the number of directories
     * deleted; result[1] is the number of files deleted.
     */
    public static long[] delete(File file) {
        long[] result = new long[2];
        result[0] = 0L;
        result[1] = 0L;
        if (file.isDirectory()) {
            // Delete all the files it contains
            File[] files = file.listFiles();
            for (File file1 : files) {
                long[] deleted = delete(file1);
                result[0] += deleted[0];
                result[1] += deleted[1];
            }
            // Delete the directory itself
            file.delete();
            result[0]++;
        } else {
            file.delete();
            result[1]++;
        }
        return result;
    }

    /**
     * @return createTempFile(new File(System.getProperty("user.dir")));
     */
    public static File createTempFile() {
        return createTempFile(new File(System.getProperty("user.dir")));
    }

    /**
     * @param dir Directory in which temporary file is created.
     * @return Temporary file created in dir.
     */
    public static File createTempFile(File dir) {
        return createTempFile(dir, "", "");
    }

    /**
     * Returns a newly created temporary _InputFile.
     *
     * @param parentDirectory .
     * @param prefix If not 3 characters long, this will be padded with "x"
     * characters.
     * @param suffix If null the _InputFile is appended with ".tmp". Default
     * extension to nothing.
     * @return
     */
    public static File createTempFile(
            File parentDirectory,
            String prefix,
            String suffix) {
        File file = null;
        while (prefix.length() < 3) {
            prefix = prefix + "x";
        }
        boolean abstractFileCreated = false;
        do {
            try {
                file = File.createTempFile(
                        prefix + Long.toString(System.currentTimeMillis()),
                        suffix,
                        parentDirectory);
                abstractFileCreated = true;
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
                System.exit(Generic_ErrorAndExceptionHandler.IOException);
            }
        } while (!abstractFileCreated);
        file.deleteOnExit();
        return createNewFile(parentDirectory, file.getName());
    }

    /**
     * @return createNewFile(new File(System.getProperty("user.dir")));
     */
    public static File createNewFile() {
        // return createNewFile(
        // new File( System.getProperty( "java.io.tmpdir" ) ) );
        // return createNewFile(
        // new File( System.getProperty( "user.home" ) ) );
        return createNewFile(
                new File(System.getProperty("user.dir")));
    }

    /**
     * @return a File created by:      <code>
     * return createNewFile(
     *         parentDirectory,
     *         new String(""),
     *         new String(""));
     * </code>
     * @param parentDirectory Default extension prefix and suffix nothing.
     */
    public static File createNewFile(File parentDirectory) {
        return createNewFile(
                parentDirectory, "", "");
    }

    /**
     * @return a File.
     * @param parentDirectory
     * @param prefix
     * @param suffix
     */
    public static File createNewFile(
            File parentDirectory,
            String prefix,
            String suffix) {
        File file;
        do {
            file = new File(
                    parentDirectory,
                    prefix + Long.toString(System.currentTimeMillis()) + suffix);
        } while (file.exists());
        try {
            if ((prefix + suffix).equalsIgnoreCase("")) {
                file.mkdir();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        return file;
    }

    /**
     * Returns a newly created File which is a _InputFile if the filename. or a
     * directory.
     *
     * @param parentDirectory
     * @param filename
     * @return
     */
    public static File createNewFile(
            File parentDirectory,
            String filename) {
        File file = new File(parentDirectory, filename);
        try {
            if (filename.length() > 4) {
                if (filename.charAt(filename.length() - 4) != '.') {
                    file.mkdir();
                } else {
                    file.createNewFile();
                }
            } else {
                file.mkdir();
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        return file;
    }

    /**
     * @param file
     * @return
     * <code>new BufferedReader(new InputStreamReader(new FileInputStream(file)))</code>
     */
    public static BufferedReader getBufferedReader(File file) {
        BufferedReader result = null;
        result = new BufferedReader(
                new InputStreamReader(getFileInputStream(file)));
        return result;
    }

    /**
     * @param file The file to write to.
     * @param append If true an existing file will be appended otherwise it will
     * be overwritten.
     * @return
     */
    public static PrintWriter getPrintWriter(File file, boolean append) {
        PrintWriter result = null;
        try {
            result = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(file, append)));
        } catch (FileNotFoundException e) {
            System.err.println("Trying to handle " + e.getLocalizedMessage());
            System.err.println("Wait for 2 seconds then trying again to "
                    + "Generic_StaticIO.getPrintWriter(File, boolean).");
            if (!file.exists()) {
                e.printStackTrace(System.err);
                Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, e);
                // null will be returned...
            } else {
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (file) {
                        file.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
                }
                return getPrintWriter(file, append);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public static int getFileDepth(File f) {
        return getFileDepth(f.getAbsolutePath());
    }

    public static int getFileDepth(String absoluteFilePath) {
        int result;
        String[] s = absoluteFilePath.split("/");
        result = s.length;
        return result;
    }

    public static String getRelativeFilePath(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result += "../";
        }
        return result;
    }

    public static String getRelativeFilePath(
            int depth,
            File f) {
        return getRelativeFilePath(
                depth,
                f.getPath());
    }

    public static String getRelativeFilePath(
            int depth,
            String path) {
        String result = path;
        for (int i = 0; i < depth; i++) {
            result += "../";
        }
        return result;
    }

    /**
     * Skips to the next token of StreamTokenizer.TT_EOL type in
     * streamTokenizer.nextToken().
     *
     * @param streamTokenizer
     */
    public static void skipline(StreamTokenizer streamTokenizer) {
        int tokenType;
        try {
            tokenType = streamTokenizer.nextToken();
            while (tokenType != StreamTokenizer.TT_EOL) {
                tokenType = streamTokenizer.nextToken();
            }
        } catch (IOException ex) {
            Logger.getLogger(Generic_StaticIO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.wordChars( '0', '0' );</li>
     * <li>st.wordChars( '1', '1' );</li>
     * <li>st.wordChars( '2', '2' );</li>
     * <li>st.wordChars( '3', '3' );</li>
     * <li>st.wordChars( '4', '4' );</li>
     * <li>st.wordChars( '5', '5' );</li>
     * <li>st.wordChars( '6', '6' );</li>
     * <li>st.wordChars( '7', '7' );</li>
     * <li>st.wordChars( '8', '8' );</li>
     * <li>st.wordChars( '9', '9' );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set.
     */
    public static void setStreamTokenizerSyntaxNumbersAsWords1(
            StreamTokenizer st) {
        // st.wordChars( '0', '9' );
        st.wordChars('0', '0');
        st.wordChars('1', '1');
        st.wordChars('2', '2');
        st.wordChars('3', '3');
        st.wordChars('4', '4');
        st.wordChars('5', '5');
        st.wordChars('6', '6');
        st.wordChars('7', '7');
        st.wordChars('8', '8');
        st.wordChars('9', '9');
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set.
     */
    public static void setStreamTokenizerSyntax1(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('_', '_');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'e', 'e' );</li>
     * <li>st.wordChars( 'E', 'E' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax2(
            StreamTokenizer st) {
        st.resetSyntax();
        st.wordChars('"', '"');
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('e', 'e');
        st.wordChars('E', 'E');
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * <li>st.eolIsSignificant( true );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax3(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.wordChars('_', '_');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars( ',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.eolIsSignificant( true );</li> </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax4(
            StreamTokenizer st) {
        st.resetSyntax();
        st.wordChars(',', ',');
        st.wordChars('"', '"');
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars(',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( ':', ':' );</li>
     * <li>st.wordChars( '/', '/' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax5(
            StreamTokenizer st) {
        st.resetSyntax();
        // st.parseNumbers();
        st.wordChars(',', ',');
        //st.ordinaryChar(',');
        st.wordChars('"', '"');
        // st.whitespaceChars( '"', '"' );
        setStreamTokenizerSyntaxNumbersAsWords1(st);
        st.wordChars('.', '.');
        st.wordChars('-', '-');
        st.wordChars('+', '+');
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        // char[] tab = new char[1];
        // tab[0] = '\t';
        st.wordChars('\t', '\t');
        st.wordChars(' ', ' ');
        st.wordChars(':', ':');
        st.wordChars('/', '/');
        st.wordChars('_', '_');
        // st.ordinaryChar( ' ' );
        st.eolIsSignificant(true);
    }

    /**
     * Sets the syntax of st as follows:
     * @code {
     * <ul>
     * <li>st.resetSyntax();</li>
     * <li>st.wordChars(',', ',' );</li>
     * <li>st.wordChars( '"', '"' );</li>
     * <li>setStreamTokenizerSyntaxNumbersAsWords1(st);</li>
     * <li>st.wordChars( '.', '.' );</li>
     * <li>st.wordChars( '-', '-' );</li>
     * <li>st.wordChars( '+', '+' );</li>
     * <li>st.wordChars( 'a', 'z' );</li>
     * <li>st.wordChars( 'A', 'Z' );</li>
     * <li>st.wordChars( '\t', '\t' );</li>
     * <li>st.wordChars( ' ', ' ' );</li>
     * <li>st.wordChars( ':', ':' );</li>
     * <li>st.wordChars( '/', '/' );</li>
     * <li>st.wordChars( '_', '_' );</li>
     * </ul>
     * }
     *
     * @param st <code>StreamTokenizer</code> thats syntax is set
     */
    public static void setStreamTokenizerSyntax6(
            StreamTokenizer st) {
        setStreamTokenizerSyntax5(st);
        st.wordChars('&', '&');
        st.wordChars('(', '(');
        st.wordChars(')', ')');
        st.wordChars('?', '?');
        st.wordChars('\'', '\'');
        st.wordChars('*', '*');
        st.wordChars('\\', '\\');
        st.wordChars('/', '/');
        st.wordChars(';', ';');
        st.wordChars('%', '%');
        st.wordChars('"', '"');
        st.wordChars('£', '£');
        st.wordChars('|', '|');
        st.wordChars('@', '@');
        st.wordChars('=', '=');
        //st.wordChars('', '');
    }

    /**
     * @return File directory in which object with a_ID is (to be) stored within
     * directory0.
     * @param directory
     * @param a_ID The ID of the object to be stored
     * @param max_ID The maximum number of objects to be stored
     * @param range The number of objects stored per directory
     */
    public static File getObjectDirectory(
            File directory,
            long a_ID,
            long max_ID,
            long range) {
        long diff = range;
        while (max_ID / (double) diff > 1) {
            //while (max_ID / diff > 1) {
            diff *= range;
        }
        long min = 0;
        Object[] min_directory = getObjectDirectory(
                a_ID,
                min,
                diff,
                directory);
        diff /= range;
        while (diff > 1L) {
            min_directory = getObjectDirectory(
                    a_ID,
                    (Long) min_directory[1],
                    diff,
                    (File) min_directory[0]);
            diff /= range;
        }
        return (File) min_directory[0];
    }

    private static Object[] getObjectDirectory(
            long a_ID,
            long min,
            long diff,
            File parent_File) {
        Object[] result = new Object[2];
        long mint = min;
        long maxt = min + diff - 1;
        String directory_String;
        File directory = null;
        boolean found = false;
        while (!found) {
            if (a_ID >= mint && a_ID <= maxt) {
                found = true;
                directory_String = "" + mint + "_" + maxt;
                directory = new File(
                        parent_File,
                        directory_String);
            } else {
                mint += diff;
                maxt += diff;
            }
        }
        result[0] = directory;
        result[1] = mint;
        return result;
    }

    /**
     * Initialises a directory in directory and returns result.      <code>
     * File result;
     * long start = 0;
     * long end = range - 1;
     * result = new File(
     * directory,
     * "" + start + "_" + end + "/" + start);
     * result.mkdirs();
     * return result;</code>
     *
     * @param directory
     * @param range
     * @return
     */
    public static File initialiseArchive(
            File directory,
            long range) {
        File result;
        long start = 0;
        long end = range - 1;
        result = new File(
                directory,
                "" + start + "_" + end + "/" + start);
        result.mkdirs();
        return result;
    }

    /**
     * Initialises an Archive directory structure. The structure of the archive
     * is given by the range.
     *
     * @param directory
     * @param range
     * @param maxID
     * @throws IOException
     */
    public static void initialiseArchive(
            File directory,
            long range,
            long maxID)
            throws IOException {
        if (directory.exists()) {
            String[] list = directory.list();
            if (list != null) {
                if (list.length > 0) {
                    throw new IOException("Attempting to initialise Archive in non-empty directory");
                }
            }
        }
        initialiseArchive(
                directory,
                range);
        for (long l = 0L; l < maxID; l++) {
            addToArchive(
                    directory,
                    range);
        }
    }

    /**
     * Initialises an Archive directory structure and returns a TreeMap of the
     * Files in the Archive with numerical keys from 0 to maxID. The structure
     * of the archive is given by the range.
     *
     * @param directory
     * @param range
     * @param maxID
     * @return
     * @throws IOException
     */
    public static TreeMap<Long, File> initialiseArchiveReturnTreeMapLongFile(
            File directory,
            long range,
            long maxID)
            throws IOException {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        initialiseArchive(
                directory,
                range,
                maxID);
        return getArchiveLeafFiles_TreeMap(
                directory,
                "_",
                0L,
                maxID);
//        for (long l = 0L; l <= maxID; l++) {
//            File file = new File(
//                    getObjectDirectory(directory, l, maxID, range),
//                    "" + l);
//            result.put(l, file);
//        }
//        return result;
    }

    public static Long getArchiveHighestLeaf(
            File directory,
            String underscore) {
        Long result;
        File[] archiveFiles = directory.listFiles();
        if (archiveFiles == null) {
            return -1L;
        }
        if (archiveFiles.length == 0) {
            return -1L;
        } else {
            TreeMap<Long, File> numericalOrderFiles_TreeMap
                    = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
                            archiveFiles,
                            underscore);
            File last_File = numericalOrderFiles_TreeMap.lastEntry().getValue();
            if (last_File.getName().contains(underscore)) {
                return getArchiveHighestLeaf(
                        last_File,
                        underscore);
            } else {
                result = new Long(last_File.getName());
            }
        }
        return result;
    }

    public static long getArchiveRange(
            File directory,
            String underscore) {
        File highestLeaf_File = getArchiveHighestLeafFile(
                directory,
                underscore);
        String[] split = highestLeaf_File.getParentFile().getName().split(underscore);
        long min = new Long(split[0]);
        long max = new Long(split[1]);
        return max - min + 1;
    }

    /**
     * For returning all the leaf file elements in a branch of an archive as a
     * HashSet&lt;File&gt;. An archive has directories such as ./0_99 which
     * store leaves such as ./0_99/0 and ./0_99/10. The archive may have
     * considerable depth such that an archive leaf is stored for example in
     * ./0_999999/0_9999/0_99/0. The leaves are the directories at the end of
     * the tree branches that contain directories that do not have an underscore
     * in the filename
     *
     * @param directory
     * @param underscore
     * @return a HashSet&lt;File&gt; containing all files in the directory. The
     * directory is regarded as a directory that possibly contains other
     * directories in a branching tree with paths that are expected to end
     * eventually in one or more files. All such files are returned in the
     * result. The result is null if the directory is empty.
     */
    public static HashSet<File> getArchiveLeafFiles(
            File directory,
            String underscore) {
        HashSet<File> result = new HashSet<File>();
        File[] topLevelArchiveFiles = directory.listFiles();
        for (File topLevelArchiveFile : topLevelArchiveFiles) {
            HashSet<File> subresult = getArchiveLeafFiles0(topLevelArchiveFile, underscore);
            result.addAll(subresult);
        }
        return result;
    }

    private static HashSet<File> getArchiveLeafFiles0(
            File file,
            String underscore) {
        HashSet<File> result = new HashSet<File>();
        if (file.getName().contains(underscore)) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                HashSet<File> subresult = getArchiveLeafFiles0(file1, underscore);
                result.addAll(subresult);
            }
        } else {
            result.add(file);
        }
        return result;
    }

    /**
     * Potentially slow and could be speeded up by going through the file tree
     * branch by branch.
     *
     * @param directory dir.
     * @param underscore "_". {@code TreeMap<Long, File> result = new TreeMap<Long, File>();
     * File[] topLevelArchiveFiles = directory.listFiles();
     * for (File f : topLevelArchiveFiles) {
     * TreeMap<Long, File> subresult = getArchiveLeafFiles_TreeMap0(
     * f,
     * underscore);
     * result.putAll(subresult);
     * }
     * return result;
     * }
     * @
     * return result;
     */
//    public static TreeMap<Long,File> getArchiveLeafFiles_TreeMap(
//            File directory,
//            String underscore) {
//        TreeMap<Long,File> result = new TreeMap<Long,File>();
//        Long highestLeaf = getArchiveHighestLeaf(directory, underscore);
//        Long range = getArchiveRange(directory, underscore);
//        for (Long l = highestLeaf; l > -1; l--) {
//                File leaf = getObjectDirectory(
//                        directory,
//                        l,
//                        highestLeaf,
//                        range);
//                result.put(l,leaf);
//        }
//        return result;
//    }
    public static TreeMap<Long, File> getArchiveLeafFiles_TreeMap(
            File directory,
            String underscore) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        File[] topLevelArchiveFiles = directory.listFiles();
        for (File f : topLevelArchiveFiles) {
            TreeMap<Long, File> subresult = getArchiveLeafFiles_TreeMap0(
                    f,
                    underscore);
            result.putAll(subresult);
        }
        return result;
    }

    private static TreeMap<Long, File> getArchiveLeafFiles_TreeMap0(
            File file,
            String underscore) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        if (file.getName().contains(underscore)) {
            File[] files = file.listFiles();
            for (File f : files) {
                TreeMap<Long, File> subresult = getArchiveLeafFiles_TreeMap0(
                        f,
                        underscore);
                result.putAll(subresult);
            }
        } else {
            result.put(Long.valueOf(file.getName()), file);
        }
        return result;
    }

    public static TreeMap<Long, File> getArchiveLeafFiles_TreeMap(
            File directory,
            String underscore,
            long minID,
            long maxID) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        File[] topLevelArchiveFiles = directory.listFiles();
        for (File f : topLevelArchiveFiles) {
            TreeMap<Long, File> subresult = getArchiveLeafFiles_TreeMap0(
                    f,
                    underscore,
                    minID,
                    maxID);
            result.putAll(subresult);
        }
        return result;
    }

    private static TreeMap<Long, File> getArchiveLeafFiles_TreeMap0(
            File file,
            String underscore,
            long minID,
            long maxID) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        if (file.getName().contains(underscore)) {
            File[] files = file.listFiles();
            for (File f : files) {
                TreeMap<Long, File> subresult = getArchiveLeafFiles_TreeMap0(
                        f,
                        underscore,
                        minID,
                        maxID);
                result.putAll(subresult);
            }
        } else {
            long id = Long.valueOf(file.getName());
            if (id >= minID && id <= maxID) {
                result.put(Long.valueOf(file.getName()), file);
            }
        }
        return result;
    }

    /**
     *
     * @param directory
     * @param underscore
     * @return The highest numbered File
     */
    public static File getArchiveHighestLeafFile(
            File directory,
            String underscore) {
        File result;
        File[] archiveFiles = directory.listFiles();
        if (archiveFiles == null) {
            return null;
        }
        if (archiveFiles.length == 0) {
            return null;
        } else {
            TreeMap<Long, File> numericalOrderFiles_TreeMap
                    = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
                            archiveFiles,
                            underscore);
            File last_File = numericalOrderFiles_TreeMap.lastEntry().getValue();
            if (last_File.getName().contains(underscore)) {
                return getArchiveHighestLeafFile(
                        last_File,
                        underscore);
            } else {
                result = last_File;
            }
        }
        return result;
    }

    public static File growArchiveBase(
            File directory,
            long range) {
        String underscore = "_";
        File[] archiveFiles = directory.listFiles();
        TreeMap<Long, File> numericalOrderFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
                archiveFiles,
                underscore);
        File first_File = numericalOrderFiles_TreeMap.firstEntry().getValue();
        String directoryName0 = getFilename(
                directory,
                first_File);
        String[] split0 = directoryName0.split(underscore);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = new Long(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        File newTopOfArchive0 = new File(
                directory,
                "" + 0 + "_" + (newRange - 1L));
        File newTopOfArchive = new File(newTopOfArchive0.getPath());
        newTopOfArchive0.mkdir();
        for (File archiveFile : archiveFiles) {
            File newPath = new File(newTopOfArchive, archiveFile.getName());
            archiveFile.renameTo(newPath);
        }
        // Create new lower directories for next ID;
        Long next_ID = getArchiveHighestLeaf(
                directory,
                underscore);
        next_ID++;
        File newHighestLeaf_Directory = new File(
                getObjectDirectory(
                        newTopOfArchive, next_ID, next_ID, range),
                "" + next_ID);
        newHighestLeaf_Directory.mkdirs();
        return newTopOfArchive0;
    }

    public static File growArchiveBase(
            File directory,
            long range,
            long next_ID) {
        String underscore = "_";
        File[] archiveFiles = directory.listFiles();
        TreeMap<Long, File> numericalOrderFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
                archiveFiles,
                underscore);
        File first_File = numericalOrderFiles_TreeMap.firstEntry().getValue();
        String directoryName0 = getFilename(
                directory,
                first_File);
        String[] split0 = directoryName0.split(underscore);
        //long start0 = new Long(split0[0]).longValue();
        long end0 = new Long(split0[1]);
        long newRange = range * (end0 + 1L);
        // Create new top directory and move in existing files
        File newTopOfArchive0 = new File(
                directory,
                "" + 0 + "_" + (newRange - 1L));
        File newTopOfArchive = new File(newTopOfArchive0.getPath());
        newTopOfArchive0.mkdir();
        for (File archiveFile : archiveFiles) {
            File newPath = new File(newTopOfArchive, archiveFile.getName());
            archiveFile.renameTo(newPath);
        }
        File newHighestLeaf_Directory = new File(
                getObjectDirectory(
                        newTopOfArchive, next_ID, next_ID, range),
                "" + next_ID);
        newHighestLeaf_Directory.mkdirs();
        return newTopOfArchive0;
    }

    public static File addToArchive(
            File directory0,
            long range) {
        String underscore = "_";
        Long next_ID = getArchiveHighestLeaf(
                directory0,
                underscore);
        next_ID++;
        File newHighestLeaf_Directory = new File(
                getObjectDirectory(
                        directory0,
                        next_ID,
                        //next_ID + range - 2,
                        next_ID + 1,
                        range),
                "" + next_ID);
        String filename = getFilename(
                directory0,
                newHighestLeaf_Directory);
        File potentialNewTopDirectory = new File(
                directory0,
                filename);
        // Test range
        String[] splitnew = filename.split(underscore);
        long startnew = new Long(splitnew[0]);
        long endnew = new Long(splitnew[1]);
        long rangenew = endnew - startnew;
        HashMap<Integer, String> numericalAndNumericalUnderscoreFilenames_HashMap;
        numericalAndNumericalUnderscoreFilenames_HashMap = getNumericalAndNumericalUnderscoreFilenames_HashMap(
                directory0,
                underscore);
        Iterator<Integer> a_iterator = numericalAndNumericalUnderscoreFilenames_HashMap.keySet().iterator();
        int index;
        String filename0 = "";
        while (a_iterator.hasNext()) {
            index = a_iterator.next();
            filename0 = numericalAndNumericalUnderscoreFilenames_HashMap.get(index);
            //if (filename0 != null) {
            if (filename0.contains(underscore)) {
                break;
            }
            //}
        }
//        if (filename0 == null){
//            filename0 = filename;
//        }
        String[] splitold = filename0.split(underscore);
        long startold = Long.parseLong(splitold[0]);
        long endold = Long.parseLong(splitold[1]);
        long rangeold = endold - startold;
        if (rangenew > rangeold) {
            growArchiveBase(directory0, range);
        }
        newHighestLeaf_Directory.mkdirs();
        return newHighestLeaf_Directory;
    }

    public static File addToArchive(
            File directory0,
            long range,
            long next_ID) {
        String underscore = "_";
        File newHighestLeaf_Directory = new File(
                getObjectDirectory(
                        directory0,
                        next_ID,
                        //next_ID + range - 2,
                        next_ID + 1,
                        range),
                "" + next_ID);
        String filename = getFilename(
                directory0,
                newHighestLeaf_Directory);
        File potentialNewTopDirectory = new File(
                directory0,
                filename);
        // Test range
        String[] splitnew = filename.split(underscore);
        long startnew = new Long(splitnew[0]);
        long endnew = new Long(splitnew[1]);
        long rangenew = endnew - startnew;
        String[] archiveFiles = directory0.list();
        String[] splitold = archiveFiles[0].split(underscore);
        long startold = new Long(splitold[0]);
        long endold = new Long(splitold[1]);
        long rangeold = endold - startold;
        if (rangenew > rangeold) {
            growArchiveBase(directory0, range, next_ID);
        }
        newHighestLeaf_Directory.mkdirs();
        return newHighestLeaf_Directory;
    }
//
//        String[] directory0list = directory0.list();
//        boolean topDirectoryExists;
//        for (int i = 0; i < directory0list.length; i ++){
//            if (directory0list.equals(filename)){
//
//            }
//        TreeMap<Long, File> numericalOrderFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
//                archiveFiles,
//                underscore);
//
//
//
//
//        newHighestLeaf_Directory.mkdirs();
//
//
//
//        File last_File = numericalOrderFiles_TreeMap.lastEntry().getValue();
//
//        String filename2
//
//
//
//        boolean result = false;
//        String underscore = new String("_");
//        File[] list = directory.listFiles();
//        if (list != null) {
//            if (list.length > 0) {
//                if (list.length == range) {
//                    if (directory0 == directory) {
//                        growArchive(
//                                directory0,
//                                directory,
//                                range);
//                        return true;
//                    }
//                    boolean attemptResult = addToArchive(
//                            directory0,
//                            directory.getParentFile(),
//                            range);
////                    if (attemptResult) {
////                        return true;
////                    }
//                    return attemptResult;
//                }
//                TreeMap<Long, File> numericalOrderFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
//                        list,
//                        underscore);
//                File lastFile = numericalOrderFiles_TreeMap.lastEntry().getValue();
//                String filename = getFilename(
//                        directory,
//                        lastFile);
//                if (filename.contains(underscore)) {
//                    boolean attemptResult = addToArchive(
//                            directory0,
//                            //lastFile,
//                            lastFile.getParentFile(),
//                            range);
//                    if (attemptResult) {
//                        return true;
//                    }
//                    if (directory0 == directory) {
//                        growArchive(
//                                directory0,
//                                directory,
//                                range);
//                        return true;
//                    }
//                    File[] listParentFiles = lastFile.getParentFile().listFiles();
//                    TreeMap<Long, File> numericalOrderParentFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
//                            listParentFiles,
//                            underscore);
//                    Entry<Long, File> lastParentFile_Entry = numericalOrderParentFiles_TreeMap.lastEntry();
//                    Entry<Long, File> penultimateParentFile_Entry = numericalOrderParentFiles_TreeMap.higherEntry(lastParentFile_Entry.getKey());
//                    File penultimateFile = penultimateParentFile_Entry.getValue();
//                    String penultimateFilename = penultimateFile.getName();
//                    String[] split = penultimateFilename.split(underscore);
//                    long start0 = new Long(split[0]).longValue();
//                    long end0 = new Long(split[1]).longValue();
//                    long currentRange = end0 - start0 + 1;
//                    File newTopDirectory = new File(
//                            directory,
//                            "" + (start0 + currentRange) + "_"
//                            + (end0 + currentRange));
//                    newTopDirectory.mkdir();
//                    File lastParentFile = lastParentFile_Entry.getValue();
//                    File newPath = new File(
//                            newTopDirectory,
//                            lastParentFile.getName());
//                    lastParentFile.renameTo(newPath);
//                    //}
////                    while (currentRange > range) {
////                        currentRange /= range;
////                        newTopDirectory = new File(
////                                newTopDirectory,
////                                "" + start0 + "_" + (start0 + currentRange));
////                    }
////                    newTopDirectory = new File(
////                            newTopDirectory,
////                            "" + (start0 + currentRange));
////                    newTopDirectory.mkdirs();
//                    return true;
//                } else {
//                    if (list.length < range) {
//                        long fileID = new Long(filename);
//                        fileID++;
//                        File newTopDirectory = new File(
//                                directory,
//                                "" + fileID);
//                        newTopDirectory.mkdirs();
//                        return true;
//                    } else {
//                        growArchive(
//                                directory0,
//                                directory0,
//                                range);
////                    return addToArchive(
////                            directory.getParentFile(),
////                            range);
//                    }
//                }
//            } else {
//                if (directory == directory0) {
//                    int a = 0;
//                    a++;
//                }
//                String filename = getFilename(
//                        directory.getParentFile(),
//                        directory);
//                if (filename.contains(underscore)) {
//                    boolean attemptResult = addToArchive(
//                            directory0,
//                            directory.getParentFile(),
//                            range);
//                    if (attemptResult) {
//                        return result;
//                    }
//                    String[] split = filename.split(underscore);
//                    long start0 = new Long(split[0]).longValue();
//                    long end0 = new Long(split[1]).longValue();
//                    long currentRange = end0 - start0 + 1;
//                    File newTopDirectory = new File(
//                            directory,
//                            "" + (start0 + currentRange) + "_"
//                            + (end0 + currentRange));
//                    while (currentRange > range) {
//                        currentRange /= range;
//                        newTopDirectory = new File(
//                                newTopDirectory,
//                                "" + start0 + "_" + (start0 + currentRange));
//                    }
//                    newTopDirectory = new File(
//                            newTopDirectory,
//                            "" + (start0 + currentRange));
//                    newTopDirectory.mkdirs();
//                    return true;
//                }
//            }
//        }
//        return result;
//    }

//    public static File growArchive(
//            File directory0,
//            File directory,
//            long range) {
//        String underscore = new String("_");
//        File[] archiveFiles = directory.listFiles();
//        TreeMap<Long, File> numericalOrderFiles_TreeMap = getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
//                archiveFiles,
//                underscore);
//        File first_File = numericalOrderFiles_TreeMap.firstEntry().getValue();
//        String directoryName0 = getFilename(
//                directory,
//                first_File);
//        String[] split0 = directoryName0.split(underscore);
//        long start0 = new Long(split0[0]).longValue();
//        long end0 = new Long(split0[1]).longValue();
//        //if (archiveFiles.length > 1) {
//        if (archiveFiles.length == range) {
//            if (directory != directory0) {
//                return growArchive(
//                        directory0,
//                        directory.getParentFile(),
//                        range);
//            } else {
//                File newTopOfArchive = new File(
//                        directory,
//                        "" + start0 + "_" + ((range + 1) * end0));
//                newTopOfArchive.mkdir();
//                for (int i = 0; i < archiveFiles.length; i++) {
//                    File newPath = new File(
//                            newTopOfArchive,
//                            archiveFiles[i].getName());
//                    archiveFiles[i].renameTo(newPath);
//                }
//                long newRange0 = ((range + 1) * end0) - start0;
//                start0 += newRange0;
//                end0 += newRange0;
//                newTopOfArchive = new File(
//                        directory,
//                        "" + start0 + "_" + end0);
//                newTopOfArchive.mkdir();
//                long newRange = newRange0;
//                while (newRange < range) {
//                    newRange /= range;
//                    newTopOfArchive = new File(
//                            newTopOfArchive,
//                            "" + start0 + "_" + (start0 + newRange));
//                }
//                newTopOfArchive = new File(
//                        newTopOfArchive,
//                        "" + start0);
//                newTopOfArchive.mkdir();
//            }
//        } else {
//            File last_File = numericalOrderFiles_TreeMap.lastEntry().getValue();
//            String directoryNameNMinusOne = getFilename(
//                    directory,
//                    last_File);
//            String[] splitNMinusOne = directoryNameNMinusOne.split(underscore);
//            long startNMinusOne = new Long(splitNMinusOne[0]).longValue();
//            long endNMinusOne = new Long(splitNMinusOne[1]).longValue();
//            long newRangeNMinusOne = endNMinusOne - startNMinusOne + 1;
//            long startN = startNMinusOne + newRangeNMinusOne;
//            long endN = endNMinusOne + newRangeNMinusOne;
//            File newTopOfArchive = new File(
//                    directory,
//                    "" + startN + "_" + endN);
//            long newRange = newRangeNMinusOne;
//            while (newRange < range) {
//                newRange /= range;
//                newTopOfArchive = new File(
//                        newTopOfArchive,
//                        "" + startN + "_" + (startN + newRange));
//            }
//            newTopOfArchive = new File(
//                    newTopOfArchive,
//                    "" + startN);
//            newTopOfArchive.mkdirs();
//            return newTopOfArchive;
//        }
//        //}
//        return null;
//    }
//    public static File createArchive(
//            File directory,
//            long range) {
//        File[] archives = directory.listFiles();
//        File newTopOfArchiveDirectory;
//        if (archives != null) {
//            if (archives.length == range) {
//                String filename = getFilename(
//                        directory,
//                        archives[archives.length - 1]);
//                String[] split = filename.split("_");
//                long max_ID;
//                if (split.length == 1) {
//                    max_ID = new Long(split[0]);
//                } else {
//                    max_ID = new Long(filename.split("_")[1]);
//                }
//                max_ID += range;
//                long next_ID = max_ID - archives.length + 1;
//                if (directory.getParentFile().list().length < range - 1) {
//                    System.out.println("directory.getParentFile().list().length < range -1");
//                } else {
//                    System.out.println("directory.getParentFile().list().length !< range -1");
//                }
//                growArchive(directory, max_ID, range);
//                File objectParentDirectory = getObjectDirectory(
//                        directory.getParentFile(), next_ID, max_ID, range);
//                newTopOfArchiveDirectory = new File(
//                        objectParentDirectory,
//                        "" + next_ID);
//                //objectDirectory.mkdirs();
//                //newTopOfArchiveDirectory = new File(directory,
//                //        getFilename(directory, objectDirectory));
//                //directory = growArchive(directory.getParentFile(), max_ID, range);
//                //newTopOfArchiveDirectory = createArchive(directory, range);
//                // Where is new top of archive?
//                //newTopOfArchiveDirectory = getObjectDirectory(
//                //        directory, max_ID, max_ID, range);
//            } else {
//                if (archives.length == 0) {
////                    String filename = getFilename(
////                            directory,
////                            archives[archives.length - 1]);
////                    String[] split = filename.split("_");
////                    long a_ID;
////                    if (split.length == 1) {
////                        a_ID = new Long(split[0]);
////                    } else {
////                        a_ID = new Long(filename.split("_")[1]);
////                    }
//
//
//
//                    long start = archives.length * range;
//                    long end = start + range - 1;
//                    newTopOfArchiveDirectory = new File(
//                            directory, "" + start + "_" + end + "/" + start);
//                } else {
//                    String filename = getFilename(
//                            directory,
//                            archives[archives.length - 1]);
//                    String[] split = filename.split("_");
//                    long a_ID;
//                    if (split.length == 1) {
//                        a_ID = new Long(split[0]);
//                    } else {
//                        a_ID = new Long(filename.split("_")[1]);
//                    }
//                    a_ID += 1;
//                    newTopOfArchiveDirectory = new File(
//                            directory, "" + a_ID);
//                }
//            }
//        } else {
//            newTopOfArchiveDirectory = new File(directory.toString() + "/0_" + (range - 1) + "/0");
//        }
//        newTopOfArchiveDirectory.mkdirs();
//        // Attempt to slow things down as for some reason file system is getting
//        // messy...
//        System.out.println(
//                "new directory " + newTopOfArchiveDirectory
//                + " exists == " + newTopOfArchiveDirectory.exists());
////        for(int i = 0; i < 10000; i ++){
////            System.out.print(".");
////        }
//        return newTopOfArchiveDirectory;
//    }
    /**
     * @param directory
     * @param filename
     * @return The File with filename in directory or throws
     * FileNotFoundException if directory or the result does not exist.
     * @throws java.io.FileNotFoundException
     */
    public static File getFileThatExists(
            File directory,
            String filename) throws FileNotFoundException {
        String method = "getFile(File,String)";
        if (!directory.exists()) {
            throw new FileNotFoundException(
                    "File " + directory + " does not exist in "
                    + Generic_StaticIO.class.getName() + "." + method + ".");
        }
        File result = null;
        if (filename != null) {
            result = new File(directory, filename);
            if (!result.exists()) {
                throw new FileNotFoundException(
                        result + " does not exist in "
                        + Generic_StaticIO.class.getName() + "." + method + ".");
            }
        }
        return result;
    }

    /**
     * @param directory
     * @param a_File
     * @return The name of the file or directory in directory in the path of
     * a_File.
     */
    public static String getFilename(
            File directory,
            File a_File) {
        String result;
        int beginIndex = directory.getAbsolutePath().length() + 1;
        String fileSeparator = System.getProperty("file.separator");
        /*
         * A feature in Java means splitting strings with "\" does not work as
         * might be expected and the regexp needs changing to "\\\\"
         */
        String regexp = "\\";
        //System.out.println("regexp " + regexp);
        if (fileSeparator.equals(regexp)) {
            fileSeparator = "\\\\";
        }
        //System.out.println("fileSeparator " + fileSeparator);
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getAbsolutePath().substring(beginIndex)).split(System.getProperty("file.separator"))[0];
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("\\")[0];
        //String newTopOfArchiveDirectoryName = (objectDirectoryFile.getPath().substring(beginIndex)).split("/")[0];
        result = (a_File.getPath().substring(beginIndex)).split(fileSeparator)[0];
        //System.out.println("result " + result);
        return result;
    }

    /**
     * {@code getNumericalOrderFilesWithNumericalFilenames_TreeMap(
     *       File[] files) {
     *   TreeMap<Long, File> result = new TreeMap<Long, File>();
     *   for (int i = 0; i < files.length; i++) {
     *       result.put(
     *               new Long(files[i].getName()),
     *               files[i]);
     *   }
     *   return result;
     * }
     * }
     *
     * @param files
     * @return TreeMap<Long, File>
     */
    public static TreeMap<Long, File> getNumericalOrderFilesWithNumericalFilenames_TreeMap(
            File[] files) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        for (File file : files) {
            result.put(new Long(file.getName()), file);
        }
        return result;
    }

    /**
     * If needed to order also by a number after the underscore then a new
     * method is needed.
     *
     * @param files
     * @param underscore = "_"
     * @return TreeMap<Long, File>
     */
    public static TreeMap<Long, File> getNumericalOrderFilesWithNumericalUnderscoreFilenames_TreeMap(
            File[] files,
            String underscore) {
        TreeMap<Long, File> result = new TreeMap<Long, File>();
        String filename;
        String[] split;
        for (File file : files) {
            filename = file.getName();
            if (filename.contains(underscore)) {
                split = filename.split("_");
                if (split.length <= 2) {
                    long start;
                    try {
                        start = Long.parseLong(split[0]);
                        if (split.length == 2) {
                            Long.parseLong(split[1]);
                        }
                        result.put(start, file);
                    } catch (NumberFormatException a_NumberFormatException) {
                        // Ignore
                    }
                }
            } else {
                try {
                    long name = new Long(filename);
                    result.put(name, file);
                } catch (NumberFormatException a_NumberFormatException) {
                    // Ignore
                }
            }
        }
        return result;
    }

    //
    public static HashMap<Integer, String> getNumericalAndNumericalUnderscoreFilenames_HashMap(
            File directory,
            String underscore) {
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        int index = 0;
        String[] list = directory.list();
        int count = 0;
        String filename;
        String[] split;
        for (int i = 0; i < list.length; i++) {
            filename = list[i];
            if (filename.contains(underscore)) {
                split = filename.split("_");
                if (split.length <= 2) {
                    long start;
                    try {
                        start = new Long(split[0]).longValue();
                        if (split.length == 2) {
                            new Long(split[1]).longValue();
                        }
                        count++;
                        result.put(
                                index,
                                filename);
                        index++;
                    } catch (NumberFormatException a_NumberFormatException) {
                        // Ignore
                    }
                }
            } else {
                try {
                    long name = new Long(filename);
                    result.put(
                            index,
                            filename);
                    index++;
                } catch (NumberFormatException a_NumberFormatException) {
                    // Ignore
                }
            }
        }
        return result;
    }
    
    /**
     * Method to calculate the length of the file path.
     * The Windows 7 operating systems has a technical restriction of 260 
     * characters or less for file paths. So a file path that is greater than 
     * 250 characters is a worry especially if results are going to be zipped 
     * up and transferred to a Windows 7 machine.
     * @param f
     * @return 
     */
    public static int getFilePathLength(File f) {
        int result;
        String s;
        try {
            s = f.getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Generic_StaticIO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Returning absolute path as getCanonicalPath() resulted in IOException.");
            s = f.getAbsolutePath();
        }
        result = s.length();
        return result;
    }
    
    /**
     * Method to calculate the length of the file path.
     * The Windows 7 operating systems has a technical restriction of 260 
     * characters or less for file paths. So a file path that is greater than 
     * 250 characters is a worry especially if results are going to be zipped 
     * up and transferred to a Windows 7 machine.
     * @param f
     * @return 
     */
    public static int getFilePathLength(File f, File dir) {
        int fileFilePathLength;
        fileFilePathLength = getFilePathLength(f);
        int dirFilePathLength;
        dirFilePathLength = getFilePathLength(dir);
        return fileFilePathLength - dirFilePathLength;
    }
    
//    public static boolean isStandardFileName(File f){
//        return isStandardFileName(f.toString());
//    } 
}

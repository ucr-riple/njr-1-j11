/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
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
package uk.ac.leeds.ccg.andyt.generic.visualisation;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;
import uk.ac.leeds.ccg.andyt.generic.logging.Generic_Log;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Execution;

/**
 * For generic visualisation methods.
 */
public class Generic_Visualisation {

    /**
     * For loading a BufferedImage from a File
     *
     * @param aFile
     * @return
     */
    public static BufferedImage loadImage(File aFile) {
        BufferedImage aBufferedImage = null;
        try {
            try {
                aBufferedImage = ImageIO.read(aFile);
            } catch (IIOException e) {
                //} catch (FileNotFoundException e) {
                //} catch (IOException e) {
                //} catch (Exception e) {
                // This can happen because of too many open files.
                // Try waiting for a 2 seconds and then repeating...
                try {
                    synchronized (aFile) {
                        aFile.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                return loadImage(aFile);
            }
        } catch (IOException e) {
            Generic_Log.logger.log(
                    Generic_Log.Generic_DefaultLogLevel, //Level.ALL,
                    e.getMessage());
            e.printStackTrace(System.err);
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        return aBufferedImage;
    }

    /**
     * For saving a BufferedImage to a File
     *
     * @param bi
     * @param format
     * @param aFile
     */
    public static void saveImage(
            BufferedImage bi,
            String format,
            File aFile) {
//        try {
        System.out.println("<Generic_Visualisation.saveImage(BufferedImage,String,File)>");
        if (bi != null) {
            try {
                System.out.println("File " + aFile.toString());
                ImageIO.write(bi, format, aFile);
            } catch (IIOException e) {
                System.err.println("Trying to handle " + e.getLocalizedMessage());
                System.err.println("Wait for 2 seconds then trying again to saveImage.");
                //e.printStackTrace(System.err);
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (bi) {
                        bi.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                saveImage(
                        bi,
                        format,
                        aFile);
                //} catch (FileNotFoundException e) {
                //} catch (IOException e) {
            } catch (Exception e) {
                System.err.println("Trying to handle " + e.getLocalizedMessage());
                System.err.println("Wait for 2 seconds then trying again to saveImage.");
                //e.printStackTrace(System.err);
                // This can happen because of too many open files.
                // Try waiting for 2 seconds and then repeating...
                try {
                    synchronized (bi) {
                        bi.wait(2000L);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Generic_Visualisation.class.getName()).log(Level.SEVERE, null, ex);
                }
                saveImage(
                        bi,
                        format,
                        aFile);
            } finally {
                // There is nothing to go in here as IMAGEIO deals with the stream.
            }
        }
//        } catch (IOException e) {
//            Generic_Log.logger.log(
//                    Generic_Log.Generic_DefaultLogLevel, //Level.ALL,
//                    e.getMessage());
//            String methodName = "saveImage(BufferedImage,String,File)";
//            System.err.println(e.getMessage());
//            System.err.println("Generic_Visualisation." + methodName);
//            e.printStackTrace(System.err);
//            System.exit(Generic_ErrorAndExceptionHandler.IOException);
//        }
        System.out.println("</Generic_Visualisation.saveImage(BufferedImage,String,File)>");
    }

    /**
     * Saves image bi to file file in format after a timeInMilliseconds delay
     * which is intended to be long enough for all graphics to be drawn.
     *
     * @param executorService
     * @param obj
     * @param timeInMilliseconds
     * @param format
     * @param bi
     * @param aFile
     * @return
     */
//    public void saveImage(
//            long timeInMilliseconds,
//            String format,
//            File file) {
//        waitSychronized(timeInMilliseconds);
//        Generic_Visualisation.saveImage(bi, format, file);
//    }
//    /**
//     * Starts and runs save image in a new thread.
//     * @param obj
//     * @param bi
//     * @param timeInMilliseconds
//     * @param format
//     * @param aFile 
//     */
//    public static void saveImage(
//            Object obj,
//            BufferedImage bi,
//            long timeInMilliseconds,
//            String format,
//            File aFile) {
//        ImageSaver is = new ImageSaver(
//                obj,
//                bi,
//                timeInMilliseconds,
//                format,
//                aFile);
//        new Thread(is).start();
//        // Generic_Visualisation.saveImage(bi, format, file);
//    }
    public static Future saveImage(
            ExecutorService executorService,
            Object obj,
            BufferedImage bi,
            long timeInMilliseconds,
            String format,
            File aFile) {
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        Future result;
        ImageSaver is = new ImageSaver(
                obj,
                bi,
                timeInMilliseconds,
                format,
                aFile);
        result = executorService.submit(is);
        return result;
    }

    public static class ImageSaver implements Runnable {

        Object obj;
        BufferedImage bi;
        long timeInMilliseconds;
        String format;
        File file;

        public ImageSaver() {
        }

        public ImageSaver(
                Object obj,
                BufferedImage bi,
                long timeInMilliseconds,
                String format,
                File aFile) {
            this.obj = obj;
            this.bi = bi;
            this.timeInMilliseconds = timeInMilliseconds;
            this.format = format;
            this.file = aFile;
        }

        @Override
        public void run() {
            Generic_Execution.waitSychronized(obj, timeInMilliseconds);
            Generic_Visualisation.saveImage(bi, format, file);
        }
    }

//    /**
//     * For saving a BufferedImage to a File
//     * @param aBufferedImage
//     * @param format
//     * @param aFile 
//     */
//    public static void saveImage(
//            BufferedImage aBufferedImage,
//            String format,
//            File aFile) {
//        try {
//            ImageIO.write(aBufferedImage, format, aFile);
//        } catch (IOException aIOException) {
//            Generic_Log.logger.log(
//                    Generic_Log.Generic_DefaultLogLevel, //Level.ALL,
//                    aIOException.getMessage());
//            System.exit(Generic_ErrorAndExceptionHandler.IOException);
//        }
//    }
//    public static BufferedImage getBufferedImage(
//            Generic_Plot aGeneric_AgeGenderVisualisation) {
//        BufferedImage result;
//        Graphics2D g2 = aGeneric_AgeGenderVisualisation.g2;
//        int w = aGeneric_AgeGenderVisualisation.getWidth();
//	int h = aGeneric_AgeGenderVisualisation.getHeight();
//	result = (BufferedImage) (aGeneric_AgeGenderVisualisation.createImage(w,h));
//	result.createGraphics();
//	g2.drawImage(result, null, 0, 0);
//        return result;
//    }
    public static Object[] getHeadlessEnvironment() {
        Object[] result = new Object[2];
        System.setProperty("java.awt.headless", "true");
        Toolkit tk = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //print_headless_check(ge);
        result[0] = tk;
        result[1] = ge;
        return result;
    }

    public static void print_headless_check(GraphicsEnvironment ge) {
        boolean headless_check = ge.isHeadlessInstance();
        System.out.println("headless_check " + headless_check);
    }

    public static Font getDefaultFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }
}

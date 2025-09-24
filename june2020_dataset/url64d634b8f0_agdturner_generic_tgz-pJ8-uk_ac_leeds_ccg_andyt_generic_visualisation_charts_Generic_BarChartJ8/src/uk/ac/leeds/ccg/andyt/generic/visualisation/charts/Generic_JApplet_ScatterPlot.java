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
package uk.ac.leeds.ccg.andyt.generic.visualisation.charts;

import java.io.File;
import java.math.RoundingMode;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An implementation of
 * <code>Generic_JApplet_ScatterPlot<\code>
 *
 * If you run this class it will attempt to generate an Age by Gender
 * Population Line Chart Visualization of some default data and display it on
 * screen.
 */
public class Generic_JApplet_ScatterPlot extends Abstract_Generic_JApplet_Plot {

    public Generic_JApplet_ScatterPlot() {
    }

    public Generic_JApplet_ScatterPlot(
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot,
            int decimalPlacePrecisionForCalculations,
            int decimalPlacePrecisionForDisplay,
            RoundingMode roundingMode) {
        _Generic_Plot = new Generic_ScatterPlot(
                null,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                roundingMode);
    }

    public static void main(String[] args) {
        Generic_Visualisation.getHeadlessEnvironment();
        /*
         * Initialise title and File to write image to
         */
        String title;
        File file;
        String format = "PNG";
        if (args.length != 2) {
            System.out.println(
                    "Expected 2 args:"
                    + " args[0] title;"
                    + " args[1] File."
                    + " Recieved " + args.length + " args.");
            // Use defaults
            title = "Scatter Plot";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        /*
         * Initialise a container of showing on-screen
         */
        //Panel p = new Panel();
        JFrame jFrame = getJFrame(title);
        int dataWidth = 400;//250;
        int dataHeight = 657;
        String xAxisLabel = "Expected (X)";
        String yAxisLabel = "Observed (Y)";
        boolean drawOriginLinesOnPlot = true;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
        Generic_JApplet_ScatterPlot plot = new Generic_JApplet_ScatterPlot(
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                aRoundingMode);
        plot.setData(Generic_ScatterPlot.getDefaultData(true));
        plot.setStartAgeOfEndYearInterval(0);
        plot.run(jFrame);
    }

    /**
     * Draws the X axis returns the height
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @return 
     */
    @Override
    public int[] drawXAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        int[] result = ((Generic_ScatterPlot) _Generic_Plot).drawXAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        return result;
    }

    @Override
    public int[] drawYAxis(
            int interval, // ignored
            int textHeight,
            int startAgeOfEndYearInterval, // ignored
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        int[] result = ((Generic_ScatterPlot) _Generic_Plot).drawYAxis(
                interval,
                textHeight,
                startAgeOfEndYearInterval,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        return result;
    }

    @Override
    public void setOriginCol() {
        ((Generic_ScatterPlot) _Generic_Plot).setOriginCol();
//        setOriginCol(_Generic_Plot.coordinateToScreenCol(BigDecimal.ZERO));
//        System.out.println("originCol " + originCol);
//        
//        if (minX.compareTo(BigDecimal.ZERO) == 0) {
//            originCol = dataStartCol;
//            //originCol = dataStartCol - dataEndCol / 2;
//        } else {
//            if (cellWidth.compareTo(BigDecimal.ZERO) == 0) {
//                originCol = dataStartCol;
//            } else {
//                originCol = Generic_BigDecimal.divideRoundIfNecessary(
//                        minX,
//                        cellWidth,
//                        0,
//                        _RoundingMode).intValueExact()
//                        + dataStartCol;
//            }
//        }
    }
}

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

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.lang.Runnable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Execution;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Collections;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An implementation of
 * <code>Generic_Plot<\code>
 *
 * If you run this class it will attempt to generate an Bar Chart Visualization 
 * of some default data and write it out to file as a PNG.
 */
public class Generic_BarChart extends Abstract_Generic_BarChart {

    public Generic_BarChart() {
    }

    public Generic_BarChart(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot, //Ignored
            int barGap,
            int xIncrement,
            BigDecimal yMax, 
            BigDecimal yPin,
            BigDecimal yIncrement,
            int numberOfYAxisTicks,
            int decimalPlacePrecisionForCalculations,
            int decimalPlacePrecisionForDisplay,
            RoundingMode aRoundingMode) {
        setBarGap(barGap);
        setxIncrement(xIncrement);
        setyMax(yMax);
        setnumberOfYAxisTicks(numberOfYAxisTicks);
        setyPin(yPin);
        setyIncrement(yIncrement);
        init(
                executorService,
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
    }
    
    @Override
    public void drawData() {
        Object[] data;
        data = getData();
        setPaint(Color.DARK_GRAY);
        RoundingMode roundingMode = getRoundingMode();
        BigDecimal cellWidth = getCellWidth();
        
        Object[] intervalCountsAndLables;
        intervalCountsAndLables = (Object[]) data[0];
        TreeMap<Integer, Integer> counts;
        counts = (TreeMap<Integer, Integer>) intervalCountsAndLables[0];
        TreeMap<Integer, BigDecimal> centres;
        centres = (TreeMap<Integer, BigDecimal>) intervalCountsAndLables[2];
        
        
        BigDecimal[] minMaxBigDecimal;
        minMaxBigDecimal = (BigDecimal[]) data[1];
        BigDecimal minValue;
        minValue = minMaxBigDecimal[0];
        
        BigDecimal intervalWidth;
        intervalWidth = (BigDecimal) data[2];
        
        Iterator<Map.Entry<Integer, Integer>> ite;
        Map.Entry<Integer, Integer> entry;
        Integer interval;
        Integer count;
        int barGap = 1;
        int barWidth;
        barWidth = getBarWidth();
        // Draw bars
        ite = counts.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            interval = entry.getKey();
            count = entry.getValue();
            BigDecimal centre = centres.get(interval);
            int row = coordinateToScreenRow(
                    //new BigDecimal(count).multiply(cellHeight));
                    new BigDecimal(count));
            int barHeight = getDataEndRow() - row;
            if (barHeight == 0) {
                barHeight = 1;
                row -= 1;
            }
            int col = coordinateToScreenCol(
                    //minValue.add(new BigDecimal(interval).multiply(intervalWidth)))
                    //minValue.add(centre))
                    centre)
                    + barGap;
            setPaint(Color.DARK_GRAY);
            fillRect(
                    col,
                    row,
                    barWidth,
                    barHeight);
        }
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
            title = "Example Bar Chart";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        int dataWidth = 500;
        int dataHeight = 250;
        String xAxisLabel = "Population";
        String yAxisLabel = "Count of Areas";
        boolean drawOriginLinesOnPlot = true;
        int barGap = 1;
        int xIncrement = 1;
        int numberOfYAxisTicks = 11;
        BigDecimal yMax;
        yMax = null;
        BigDecimal yPin = BigDecimal.ZERO;
        BigDecimal yIncrement = BigDecimal.ONE;
        //int yAxisStartOfEndInterval = 60;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_BarChart chart = new Generic_BarChart(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                barGap,
                xIncrement,
                yMax,
                yPin,
                yIncrement,
                numberOfYAxisTicks,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                roundingMode);
        chart.setData(chart.getDefaultData());
        chart.run();
        Future future = chart.future;
        Generic_Execution.shutdownExecutorService(
                executorService, future, chart);
    }

    @Override
    public Dimension draw() {
        drawOutline();
        drawTitle(getTitle());
        drawAxes(2);
//        drawAxes(
//                getAgeInterval(),
//                getStartAgeOfEndYearInterval());
        drawData();
        Dimension newDim = new Dimension(getImageWidth(), getImageHeight());
        return newDim;
    }
    
    public void drawAxes(int interval) {
        int yAxisExtraWidthLeft;
//        int yAxisExtraHeightTop = 0;
//        int yAxisExtraHeightBottom = 0;
        int xAxisExtraWidthLeft;
        int xAxisExtraWidthRight;
        int xAxisExtraHeightBottom;
        int scaleTickLength = getDefaultScaleTickLength();
        int scaleTickAndTextSeparation = getDefaultScaleTickAndTextSeparation();
        int partTitleGap = getDefaultPartTitleGap();
        int textHeight = getTextHeight();
        //int seperationDistanceOfAxisAndData = textHeight;
        int seperationDistanceOfAxisAndData = 2;
        // Draw Y axis
        int[] yAxisDimensions = drawYAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        yAxisExtraWidthLeft = yAxisDimensions[0];

        int extraWidthLeft;
        extraWidthLeft = getExtraWidthLeft();
        int imageWidth;
        imageWidth = getImageWidth();
        int dataStartCol;
        dataStartCol = getDataStartCol();
        int dataEndCol;
        dataEndCol = getDataEndCol();
        int yAxisWidth;
 yAxisWidth = getyAxisWidth();
        if (yAxisExtraWidthLeft > extraWidthLeft) {
            int diff = yAxisExtraWidthLeft - extraWidthLeft;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = yAxisExtraWidthLeft;
            yAxisWidth += diff;
            //setExtraWidthLeft(yAxisExtraWidthLeft);
            setYAxisWidth(yAxisWidth);
        }
        //setYAxisWidth(yAxisExtraWidthLeft);
        setDataStartCol(dataStartCol);
        setDataEndCol(dataEndCol);
        // Draw X axis
        int[] xAxisDimensions = drawXAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        xAxisExtraWidthLeft = xAxisDimensions[0];
        xAxisExtraWidthRight = xAxisDimensions[1];
        xAxisExtraHeightBottom = xAxisDimensions[2];
        if (xAxisExtraWidthLeft > extraWidthLeft) {
            int diff = xAxisExtraWidthLeft - extraWidthLeft;
            imageWidth += diff;
            dataStartCol += diff;
            dataEndCol += diff;
            extraWidthLeft = xAxisExtraWidthLeft;
            yAxisWidth += diff;
            setYAxisWidth(yAxisWidth);
//            setOriginCol();
        }
        setDataStartCol(dataStartCol);
        setDataEndCol(dataEndCol);
        int extraWidthRight;
        extraWidthRight = getExtraWidthRight();
        if (xAxisExtraWidthRight > extraWidthRight) {
            imageWidth += xAxisExtraWidthRight - extraWidthRight;
            extraWidthRight = xAxisExtraWidthRight;
            setExtraWidthRight(extraWidthRight);
        }
        setImageWidth(imageWidth);
        int xAxisHeight;
        xAxisHeight = getxAxisHeight();
        xAxisHeight += xAxisExtraHeightBottom;
        setxAxisHeight(xAxisHeight);
        int extraHeightBottom;
        extraHeightBottom = getExtraHeightBottom();
        int imageHeight;
        imageHeight = getImageHeight();
        if (xAxisExtraHeightBottom > extraHeightBottom) {
            int diff = xAxisExtraHeightBottom - extraHeightBottom;
            imageHeight += diff;
            setImageHeight(imageHeight);
            setExtraHeightBottom(xAxisExtraHeightBottom);
        }
    }
    
    @Override
    public Object[] getDefaultData() {
        Object[] result;
        result = new Object[3];
        BigDecimal intervalWidth;
        intervalWidth = new BigDecimal(getxIncrement());
        TreeMap<String, BigDecimal> map;
        map = new TreeMap<String, BigDecimal>();
        map.put("A", new BigDecimal(0.0d));
        map.put("B", new BigDecimal(1.0d));
        map.put("C", new BigDecimal(2.0d));
        map.put("D", new BigDecimal(3.0d));
        map.put("E", new BigDecimal(4.0d));
        map.put("F", new BigDecimal(5.0d));
        map.put("G", new BigDecimal(6.0d));
        map.put("H", new BigDecimal(7.0d));
        map.put("I", new BigDecimal(8.0d));
        map.put("J", new BigDecimal(9.0d));
        map.put("K", new BigDecimal(10.0d));
        map.put("L", new BigDecimal(11.0d));
        map.put("M", new BigDecimal(12.0d));
        map.put("N", new BigDecimal(13.0d));
        map.put("O", new BigDecimal(14.0d));
        map.put("P", new BigDecimal(15.0d));
        map.put("Q", new BigDecimal(16.0d));
        map.put("R", new BigDecimal(2.0d));
        map.put("S", new BigDecimal(4.0d));
        map.put("T", new BigDecimal(6.0d));
        map.put("U", new BigDecimal(8.0d));
        map.put("V", new BigDecimal(10.0d));
        map.put("W", new BigDecimal(12.0d));
        map.put("X", new BigDecimal(14.0d));
        map.put("Y", new BigDecimal(16.0d));
        map.put("Z", new BigDecimal(4.0d));
        BigDecimal[] minMaxBigDecimal;
        minMaxBigDecimal = Generic_Collections.getMinMaxBigDecimal(map);
        Object[] intervalCountsLabelsMins;
        BigDecimal min = minMaxBigDecimal[0];
        //BigDecimal max = minMaxBigDEcimal[1];
        
        MathContext mc;
        mc = new MathContext(getDecimalPlacePrecisionForCalculations(), getRoundingMode());

        intervalCountsLabelsMins = Generic_Collections.getIntervalCountsLabelsMins(
                min, intervalWidth, map, mc);
        result[0] = intervalCountsLabelsMins;
        result[1] = minMaxBigDecimal;
        result[2] = intervalWidth;
        return result;
    }
    
    @Override
    public void drawTitle(String title) {
        super.drawTitle(title);
        int barHeight = Generic_BigDecimal.divideRoundIfNecessary(
                    BigDecimal.valueOf(getAgeInterval()),
                    getCellHeight(),
                    0,
                    getRoundingMode()).intValue();
        setExtraHeightTop(getExtraHeightTop() + barHeight);
    }
}

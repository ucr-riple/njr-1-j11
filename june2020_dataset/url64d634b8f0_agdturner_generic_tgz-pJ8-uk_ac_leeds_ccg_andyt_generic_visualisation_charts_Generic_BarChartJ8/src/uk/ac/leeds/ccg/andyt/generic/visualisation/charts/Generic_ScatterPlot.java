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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import uk.ac.leeds.ccg.andyt.generic.data.Generic_XYNumericalData;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * A class for creating Scatter Plot images.
 *
 * If you run this class it will attempt to generate an Age by Gender Population
 * Line Chart Visualization of some default data and display it on screen.
 */
public class Generic_ScatterPlot extends Generic_Plot {

    public Generic_ScatterPlot() {
    }

    public Generic_ScatterPlot(
            ExecutorService executorService,
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
            RoundingMode aRoundingMode) {
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
        int dataWidth = 400;//250;
        int dataHeight = 657;
        String xAxisLabel = "Expected";
        String yAxisLabel = "Observed";
        boolean drawOriginLinesOnPlot = true;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_ScatterPlot plot = new Generic_ScatterPlot(
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
        plot.setData(plot.getDefaultData());
        plot.setStartAgeOfEndYearInterval(0); // To avoid null pointer
        plot.run();
    }

    @Override
    public void drawData() {
        drawPoints(Color.DARK_GRAY, getData());
    }

    /**
     * Draws the X axis returns the height
     *
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
        int[] result = new int[3];
        int significantDigits = getSignificantDigits();
        int xAxisExtraWidthLeft = 0;
        int xAxisExtraWidthRight = 0;
        //int seperationDistanceOfAxisAndData = partTitleGap;
        //int seperationDistanceOfAxisAndData = partTitleGap * 2;
        //int seperationDistanceOfAxisAndData = textHeight;
        int xAxisExtraHeightBottom = scaleTickLength + scaleTickAndTextSeparation + seperationDistanceOfAxisAndData;
//                    row + scaleTickLength + (textHeight / 3));;
        setPaint(Color.LIGHT_GRAY);
        int originRow = getOriginRow();
        int dataStartRow = getDataStartRow();
        int dataStartCol = getDataStartCol();
        int dataEndRow = getDataEndRow();
        int dataEndCol = getDataEndCol();
        // Draw X axis below the data
        setPaint(Color.GRAY);
        int row = dataEndRow + seperationDistanceOfAxisAndData;
        Line2D ab = new Line2D.Double(
                dataStartCol,
                row,
                dataEndCol,
                row);
        draw(ab);
        /*
         * Draw X axis ticks and labels below the X axis
         */
        int increment = textHeight;
        int dataWidth = getDataWidth();
        while (((dataWidth * textHeight) + 4) / increment > dataWidth) {
            increment += textHeight;
        }
        int xAxisMaxLabelHeight = 0;
        String text_String;
        int textWidth;
        double angle;
        // From the origin right
        int startCol = getOriginCol();
        //int startCol = getDataStartCol();
        RoundingMode roundingMode = getRoundingMode();
        for (int col = startCol; col <= getDataEndCol(); col += increment) {
            if (col >= dataStartCol) {
                ab = new Line2D.Double(
                        col,
                        row,
                        col,
                        row + scaleTickLength);
                draw(ab);
                BigDecimal x = imageColToXCoordinate(col);
                if (x.compareTo(BigDecimal.ZERO) == 0 || col == startCol) {
                    text_String = "0";
                } else {
                //text_String = "" + x.stripTrailingZeros().toPlainString();
                    //text_String = "" + x.round(mc).stripTrailingZeros().toString();
                    //text_String = "" + x.stripTrailingZeros().toString();
                    text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
                            x,
                            Generic_BigDecimal.getDecimalPlacePrecision(x, significantDigits),
                            roundingMode).toString();
                }
                textWidth = getTextWidth(text_String);
                xAxisMaxLabelHeight = Math.max(xAxisMaxLabelHeight, textWidth);
                angle = Math.PI / 2;
                writeText(
                        text_String,
                        angle,
                        col - (textHeight / 3),
                        row + scaleTickAndTextSeparation + scaleTickLength);
//                    row + scaleTickLength + (textHeight / 3));
            }
        }
        // From the origin left
        for (int col = startCol; col >= dataStartCol; col -= increment) {
            if (col >= dataStartCol) {
                ab = new Line2D.Double(
                        col,
                        row,
                        col,
                        row + scaleTickLength);
                draw(ab);
                BigDecimal x = imageColToXCoordinate(col);
                if (x.compareTo(BigDecimal.ZERO) == 0 || col == startCol) {
                    text_String = "0";
                } else {
                //text_String = "" + x.stripTrailingZeros().toPlainString();
                    //text_String = "" + x.round(mc).stripTrailingZeros().toString();
                    //text_String = "" + x.stripTrailingZeros().toString();
                    text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
                            x,
                            Generic_BigDecimal.getDecimalPlacePrecision(
                                    x,
                                    significantDigits),
                            roundingMode).toString();
                }
                textWidth = getTextWidth(text_String);
                xAxisMaxLabelHeight = Math.max(xAxisMaxLabelHeight, textWidth);
                angle = Math.PI / 2;
                writeText(
                        text_String,
                        angle,
                        col - (textHeight / 3),
                        row + scaleTickAndTextSeparation + scaleTickLength);
//                    row + scaleTickLength + (textHeight / 3));
            }
        }
        xAxisExtraHeightBottom += xAxisMaxLabelHeight;
        //xAxisExtraHeightBottom += scaleTickAndTextSeparation + scaleTickLength + seperationDistanceOfAxisAndData;
        xAxisExtraWidthRight += textHeight * 2;
        xAxisExtraWidthLeft += textHeight / 2;
        // Add the X axis label
        setPaint(Color.BLACK);
        text_String = getxAxisLabel();
        textWidth = getTextWidth(text_String);
        xAxisExtraHeightBottom += partTitleGap;
        // Calculate if the xAxisLabel will require the imageWidth to increase.
        // If the xAxisLable is wider than the XAxis it might be best to split 
        // it and write it on multiple lines.  
        int currentWidth = xAxisExtraWidthLeft + dataWidth + xAxisExtraWidthRight;
        int endxAxisLabelPostion = dataStartCol + (dataWidth / 2) + (textWidth / 2);
        if (endxAxisLabelPostion > currentWidth) {
            xAxisExtraWidthRight += endxAxisLabelPostion - currentWidth;
        }
        drawString(
                text_String,
                dataStartCol + (dataWidth / 2) - (textWidth / 2),
                row + xAxisExtraHeightBottom);
        // Draw line on origin
        if (isDrawOriginLinesOnPlot()) {
            if (originRow <= dataEndRow && originRow >= dataStartRow) {
                setPaint(Color.LIGHT_GRAY);
                ab = new Line2D.Double(
                        dataStartCol,
                        originRow,
                        dataEndCol,
                        originRow);
                draw(ab);
            }
        }
        if (!isAddLegend()) {
            xAxisExtraHeightBottom += (2 * textHeight);
        }
        result[0] = xAxisExtraWidthLeft;
        result[1] = xAxisExtraWidthRight;
        result[2] = xAxisExtraHeightBottom;
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
        int[] result = new int[1];
        int yAxisExtraWidthLeft = scaleTickLength + scaleTickAndTextSeparation
                + seperationDistanceOfAxisAndData;
        Line2D ab;
        int text_Height = getTextHeight();
        String text_String;
        int text_Width;
        int row;
        int increment;
        int significantDigits = getSignificantDigits();
        RoundingMode roundingMode = getRoundingMode();
        int originRow = getOriginRow();
        int originCol = getOriginCol();
        int dataStartRow = getDataStartRow();
        int dataEndRow = getDataEndRow();
        int dataStartCol = getDataStartCol();
        int dataEndCol = getDataEndCol();
        int dataHeight = getDataHeight();
        // Draw Y axis to left of data
        //setOriginCol();
        setPaint(Color.GRAY);
        int col = dataStartCol - seperationDistanceOfAxisAndData;
        ab = new Line2D.Double(
                col,
                dataEndRow,
                col,
                dataStartRow);
        draw(ab);
        /*
         * Draw Y axis ticks and labels to left of Y axis
         */
        increment = text_Height;
        while (((dataHeight * text_Height) + 4) / increment > dataHeight) {
            increment += text_Height;
        }
        int yAxisMaxLabelWidth = 0;
        // From the origin up
        for (row = originRow; row >= dataStartRow; row -= increment) {
            if (row <= dataEndRow) {
                ab = new Line2D.Double(
                        col,
                        row,
                        col - scaleTickLength,
                        row);
                draw(ab);
                BigDecimal y = imageRowToYCoordinate(row);
                if (y.compareTo(BigDecimal.ZERO) == 0 || row == originRow) {
                    text_String = "0";
                } else {
                    //text_String = "" + y.stripTrailingZeros().toPlainString();
                    //text_String = "" + y.round(mc).stripTrailingZeros().toString();
                    text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
                            y,
                            Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
                            roundingMode).toString();
                }
                text_Width = getTextWidth(text_String);
                yAxisMaxLabelWidth = Math.max(yAxisMaxLabelWidth, text_Width);
                drawString(
                        text_String,
                        col - scaleTickAndTextSeparation - scaleTickLength - text_Width,
                        row + (textHeight / 3));
            }
        }
        // From the origin down
        for (row = originRow; row <= dataEndRow; row += increment) {
            if (row <= dataEndRow) {
                ab = new Line2D.Double(
                        col,
                        row,
                        col - scaleTickLength,
                        row);
                draw(ab);
                BigDecimal y = imageRowToYCoordinate(row);
                if (y.compareTo(BigDecimal.ZERO) == 0 || row == originRow) {
                    text_String = "0";
                } else {
                    //text_String = "" + y.stripTrailingZeros().toPlainString();
                    //text_String = "" + y.round(mc).stripTrailingZeros().toString();
                    text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
                            y,
                            Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
                            roundingMode).toString();
                }
                text_Width = getTextWidth(text_String);
                yAxisMaxLabelWidth = Math.max(yAxisMaxLabelWidth, text_Width);
                drawString(
                        text_String,
                        col - scaleTickAndTextSeparation - scaleTickLength - text_Width,
                        row + (textHeight / 3));
            }
        }
        yAxisExtraWidthLeft += scaleTickLength + scaleTickAndTextSeparation + yAxisMaxLabelWidth;
        // Add the Y axis label
        setPaint(Color.BLACK);
        text_String = getyAxisLabel();
        text_Width = getTextWidth(text_String);
        yAxisExtraWidthLeft += (textHeight * 2) + partTitleGap;
        double angle = 3.0d * Math.PI / 2.0d;
        writeText(
                text_String,
                angle,
                3 * textHeight / 2,
                getDataMiddleRow() + (text_Width / 2));
        // Draw line on origin
        if (isDrawOriginLinesOnPlot()) {
            if (originCol <= dataEndCol && originCol >= dataStartCol) {
                setPaint(Color.LIGHT_GRAY);
                ab = new Line2D.Double(
                        originCol,
                        dataStartRow,
                        originCol,
                        dataEndRow);
                draw(ab);
            }
        }
        result[0] = yAxisExtraWidthLeft;
        return result;
    }

//    /**
//     * Draws the X axis
//     * returns the height
//     */
//    @Override
//    public int[] drawXAxis(
//            int textHeight,
//            int scaleTickLength,
//            int scaleTickAndTextSeparation,
//            int partTitleGap) {
//        int[] result = new int[3];
//        // Draw X axis
//        int xAxisExtraWidthLeft = 0;
//        int xAxisExtraWidthRight = 0;
//        int xAxisExtraHeightBottom = scaleTickLength + scaleTickAndTextSeparation + textHeight;
//    
//         // Draw X axis scale below the data
//        setPaint(Color.GRAY);
//        int row = dataEndRow + textHeight;
//        Line2D ab = new Line2D.Double(
//                dataStartCol,
//                row,
//                dataEndCol,
//                row);
//        draw(ab);
//        int increment = textHeight;
//        while (((dataWidth * textHeight) + 4) / increment > dataWidth) {
//            increment += textHeight;
//        }
//        String text_String;
//        int textWidth;
//        double angle;
//        int theXaxisMaxLabelHeight = 0;
//        // From the origin right
//        for (int col = this.originCol; col <= this.dataEndCol; col += increment) {
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col,
//                    row + scaleTickLength);
//            draw(ab);
//            BigDecimal x = imageColToXCoordinate(col);
//            if (x.compareTo(BigDecimal.ZERO) == 0 || col == this.originCol) {
//                text_String = "0";
//            } else {
//                //text_String = "" + x.stripTrailingZeros().toPlainString();
//                //text_String = "" + x.round(mc).stripTrailingZeros().toString();
//                //text_String = "" + x.stripTrailingZeros().toString();
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        x,
//                        Generic_BigDecimal.getDecimalPlacePrecision(x, significantDigits),
//                        _RoundingMode).toString();
//            }
//            textWidth = getTextWidth(text_String);
//            theXaxisMaxLabelHeight = Math.max(theXaxisMaxLabelHeight, textWidth);
//            angle = Math.PI / 2;
//            writeText(
//                    text_String,
//                    angle,
//                    col - (textHeight / 3),
//                    row + scaleTickLength + (textHeight / 3));
//        }
//        // From the origin left
//        for (int col = this.originCol; col >= this.dataStartCol; col -= increment) {
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col,
//                    row + scaleTickLength);
//            draw(ab);
//            BigDecimal x = imageColToXCoordinate(col);
//            if (x.compareTo(BigDecimal.ZERO) == 0 || col == this.originCol) {
//                text_String = "0";
//            } else {
//                //text_String = "" + x.stripTrailingZeros().toPlainString();
//                //text_String = "" + x.round(mc).stripTrailingZeros().toString();
//                //text_String = "" + x.stripTrailingZeros().toString();
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        x,
//                        Generic_BigDecimal.getDecimalPlacePrecision(x, significantDigits),
//                        _RoundingMode).toString();
//            }
//            textWidth = getTextWidth(text_String);
//            theXaxisMaxLabelHeight = Math.max(theXaxisMaxLabelHeight, textWidth);
//            angle = Math.PI / 2;
//            writeText(
//                    text_String,
//                    angle,
//                    col - (textHeight / 3),
//                    row + scaleTickLength + (textHeight / 3));
//        }
//        // Add the X axis label
//        setPaint(Color.BLACK);
//        text_String = "X";
//        textWidth = getTextWidth(text_String);
//        drawString(
//                text_String,
//                dataStartCol + (dataWidth / 2),
//                row + theXaxisMaxLabelHeight + scaleTickLength + ((3 * textHeight) / 2) + 4);
//        
//        
//        result[0] = xAxisExtraWidthLeft;
//        result[1] = xAxisExtraWidthRight;
//        result[2] = xAxisExtraHeightBottom;
//        
//        return result;
//    
//    }
//    
//    @Override
//    public int[] drawYAxis(
//            int interval,
//            int textHeight,
//            int startAgeOfEndYearInterval,
//            int scaleTickLength,
//            int scaleTickAndTextSeparation,
//            int partTitleGap) {
//        int[] result = new int[1];
//        int yAxisExtraWidthLeft = 0;
//        
//        // Draw Y axis
//        //setOriginCol();
//        setPaint(Color.GRAY);
//        Line2D ab = new Line2D.Double(
//                originCol,
//                dataStartRow,
//                originCol,
//                dataEndRow);
//        draw(ab);
//        // Draw Y axis scale to the left side
//        setPaint(Color.GRAY);
//        int col = dataStartCol;
//        ab = new Line2D.Double(
//                col,
//                dataEndRow,
//                col,
//                dataStartRow);
//        draw(ab);
//        int increment = textHeight;
//        while (((dataHeight * textHeight) + 4) / increment > dataHeight) {
//            increment += textHeight;
//        }
//        int maxTickTextWidth = 0;
//        String text_String;
//        int textWidth;
//        // From the origin up
//        for (int row = this.originRow; row >= this.dataStartRow; row -= increment) {
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col - scaleTickLength,
//                    row);
//            draw(ab);
//            BigDecimal y = imageRowToYCoordinate(row);
//            if (y.compareTo(BigDecimal.ZERO) == 0 || row == this.originRow) {
//                text_String = "0";
//            } else {
//                //text_String = "" + y.stripTrailingZeros().toPlainString();
//                //text_String = "" + y.round(mc).stripTrailingZeros().toString();
//                //text_String = "" + y.stripTrailingZeros().toString();
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        y,
//                        Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
//                        _RoundingMode).toString();
//            }
//             textWidth = getTextWidth(text_String);
//            drawString(
//                    text_String,
//                    col - 3 - scaleTickLength - textWidth,
//                    row + (textHeight / 3));
//            maxTickTextWidth = Math.max(maxTickTextWidth, textWidth);
//        }
//       // From the origin down
//        for (int row = this.originRow; row <= this.dataEndRow; row += increment) {
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col - scaleTickLength,
//                    row);
//            draw(ab);
//            BigDecimal y = imageRowToYCoordinate(row);
//            if (y.compareTo(BigDecimal.ZERO) == 0 || row == this.originRow) {
//                text_String = "0";
//            } else {
//                //text_String = "" + y.stripTrailingZeros().toPlainString();
//                    //text_String = "" + y.round(mc).stripTrailingZeros().toString();
//                //text_String = "" + y.stripTrailingZeros().toString();
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        y,
//                        Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
//                        _RoundingMode).toString();
//            }
//             textWidth = getTextWidth(text_String);
//            drawString(
//                    text_String,
//                    col - 3 - scaleTickLength - textWidth,
//                    row + (textHeight / 3));
//         maxTickTextWidth = Math.max(maxTickTextWidth, textWidth);
//        }
//         yAxisExtraWidthLeft += scaleTickLength + scaleTickAndTextSeparation + maxTickTextWidth;
//        // Y axis label
//        setPaint(Color.BLACK);
//        text_String = "Y";
//        textWidth = getTextWidth(text_String);
//        double angle = 3.0d * Math.PI / 2.0d;
//        col = 3 * textHeight / 2;
//        writeText(
//                text_String,
//                angle,
//                col,
//                dataMiddleRow + (textWidth / 2));
//        result[0] = yAxisExtraWidthLeft;
//        return result;
//    
//    }
    protected void drawPoints(
            Color color,
            Object[] data) {
        if (data != null) {
            ArrayList<Generic_XYNumericalData> theGeneric_XYNumericalData;
            theGeneric_XYNumericalData = (ArrayList<Generic_XYNumericalData>) data[0];
            Iterator<Generic_XYNumericalData> ite = theGeneric_XYNumericalData.iterator();
            Generic_XYNumericalData aGeneric_XYNumericalData;
            setPaint(color);
            Point2D aPoint2D;
            while (ite.hasNext()) {
                aGeneric_XYNumericalData = ite.next();
                aPoint2D = coordinateToScreen(
                        aGeneric_XYNumericalData.getX(),
                        aGeneric_XYNumericalData.getY());
                draw(aPoint2D);
            }
        }
    }

    @Override
    public void setOriginCol() {
        setOriginCol(coordinateToScreenCol(BigDecimal.ZERO));
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

    @Override
    public Object[] getDefaultData() {
        return getDefaultData(true);
    }

    public static Object[] getDefaultData(boolean ignore) {
        Random random = new Random(0);
        Object[] result = new Object[5];
//        BigDecimal maxx = BigDecimal.valueOf(Double.NEGATIVE_INFINITY);
//        BigDecimal minx = BigDecimal.valueOf(Double.POSITIVE_INFINITY);
//        BigDecimal maxy = BigDecimal.valueOf(Double.NEGATIVE_INFINITY);
//        BigDecimal miny = BigDecimal.valueOf(Double.POSITIVE_INFINITY);
        BigDecimal maxx = BigDecimal.valueOf(Double.MIN_VALUE);
        BigDecimal minx = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal maxy = BigDecimal.valueOf(Double.MIN_VALUE);
        BigDecimal miny = BigDecimal.valueOf(Double.MAX_VALUE);
        ArrayList<Generic_XYNumericalData> theGeneric_XYNumericalData = new ArrayList<Generic_XYNumericalData>();
//        for (int i = -100; i < 328; i++) {         
//            for (int j = -100; j < 0; j++) {
//        for (int i = -100; i < 100; i++) {
//            for (int j = -100; j < 100; j++) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
//        for (int i = -15; i < 10; i++) {
//            for (int j = -9; j < 12; j++) {
                double random_0 = random.nextDouble();
                BigDecimal x = BigDecimal.valueOf((i + random.nextDouble()) * random_0);
                BigDecimal y = BigDecimal.valueOf(((j + i) / 2) * random_0);
                //BigDecimal y = BigDecimal.valueOf((j + i) * random_0);
                maxx = maxx.max(x);
                minx = minx.min(x);
                maxy = maxy.max(y);
                miny = miny.min(y);
                Generic_XYNumericalData point = new Generic_XYNumericalData(
                        x,
                        y);
                theGeneric_XYNumericalData.add(point);
            }
        }
        result[0] = theGeneric_XYNumericalData;
        result[1] = maxx;
        result[2] = minx;
        result[3] = maxy;
        result[4] = miny;
        return result;
    }
}

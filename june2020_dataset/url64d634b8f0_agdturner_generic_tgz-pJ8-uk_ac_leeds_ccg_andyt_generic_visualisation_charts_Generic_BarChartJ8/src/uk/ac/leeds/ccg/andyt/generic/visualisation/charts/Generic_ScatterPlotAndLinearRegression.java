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
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.math.stat.regression.SimpleRegression;
import uk.ac.leeds.ccg.andyt.generic.data.Generic_XYNumericalData;
import uk.ac.leeds.ccg.andyt.generic.visualisation.Generic_Visualisation;

/**
 * An implementation of
 * <code>Generic_JApplet_ScatterPlotAndLinearRegression<\code>
 *
 * If you run this class it will attempt to generate an Age by Gender
 * Population Line Chart Visualization of some default data and display it on
 * screen.
 */
public class Generic_ScatterPlotAndLinearRegression extends Generic_ScatterPlot {

    public Generic_ScatterPlotAndLinearRegression() {
    }

    public Generic_ScatterPlotAndLinearRegression(
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
            title = "Scatter Plot And Linear Regression";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        int dataWidth = 256;//250;
        int dataHeight = 256;
        String xAxisLabel = "Expected (X)";
        String yAxisLabel = "Observed (Y)";
        boolean drawOriginLinesOnPlot = false;//true;
        int decimalPlacePrecisionForCalculations = 100;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_ScatterPlotAndLinearRegression plot = new Generic_ScatterPlotAndLinearRegression(
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
        plot.run();
    }

    @Override
    public void drawData() {
        double[][] dataAsDoubleArray = getDataAsDoubleArray(
                (ArrayList<Generic_XYNumericalData>) getData()[0]);
        drawYEqualsXLineData(dataAsDoubleArray);
        /*
         * regressionParameters[0] is the y axis intercept;
         * regressionParameters[1] is the change in y relative to x (gradient or
         * slope); regressionParameters[2] is the rank correlation coefficient
         * (RSquare); regressionParameters[3] is data[0].length.
         */
        double[] regressionParameters = getSimpleRegressionParameters(
                dataAsDoubleArray);
        drawRegressionLine(
                regressionParameters,
                dataAsDoubleArray);
        drawPoints(
                Color.DARK_GRAY,
                getData());
        //if (addLegend) {
        drawLegend(
                regressionParameters);
        //}
    }

    @Override
    public Object[] getDefaultData() {
        return new Generic_ScatterPlot().getDefaultData();
    }

    @Override
    public Dimension draw() {
        drawOutline();
        drawTitle(getTitle());
        //System.out.println("dataStartCol " + dataStartCol);
        drawAxes(0, 0);
        drawPoints(
                Color.DARK_GRAY,
                getData());
        Object[] data = getData();
        if (data == null) {
            data = getDefaultData();
        }
        double[][] dataAsDoubleArray = getDataAsDoubleArray(
                (ArrayList<Generic_XYNumericalData>) data[0]);
        drawYEqualsXLineData(dataAsDoubleArray);
        /*
         * regressionParameters[0] is the y axis intercept;
         * regressionParameters[1] is the change in y relative to x (gradient or
         * slope); regressionParameters[2] is the rank correlation coefficient
         * (RSquare); regressionParameters[3] is data[0].length.
         */
        double[] regressionParameters = getSimpleRegressionParameters(
                dataAsDoubleArray);
        drawRegressionLine(
                regressionParameters,
                dataAsDoubleArray);
        //if (addLegend) {
        drawLegend(
                regressionParameters);
        Dimension newDim = new Dimension(getImageWidth(), getImageHeight());
        return newDim;
    }

//    /**
//     * Draws the X axis returns the height
//     */
//    @Override
//    public int[] drawXAxis(
//            int textHeight,
//            int scaleTickLength,
//            int scaleTickAndTextSeparation,
//            int partTitleGap) {
//        int[] result = new int[3];
//
//        int xAxisExtraWidthLeft = 0;
//        int xAxisExtraWidthRight = 0;
//        //int seperationDistanceOfAxisAndData = partTitleGap;
//        //int seperationDistanceOfAxisAndData = partTitleGap * 2;
//        int seperationDistanceOfAxisAndData = textHeight;
//        int xAxisExtraHeightBottom = scaleTickLength + scaleTickAndTextSeparation + seperationDistanceOfAxisAndData;
////                    row + scaleTickLength + (textHeight / 3));;
//
//        setPaint(Color.LIGHT_GRAY);
//        Line2D ab = new Line2D.Double(
//                dataStartCol,
//                originRow,
//                dataEndCol,
//                originRow);
//        draw(ab);
//        // Draw X axis scale below the data
//        setPaint(Color.GRAY);
//        //int row = dataEndRow - text_Height;
//        //int row = dataEndRow - partTitleGap;
//        int row = dataEndRow + seperationDistanceOfAxisAndData;
//        ab = new Line2D.Double(
//                dataStartCol,
//                row,
//                dataEndCol,
//                row);
//        draw(ab);
//        int increment = textHeight;
//        while (((dataWidth * textHeight) + 4) / increment > dataWidth) {
//            increment += textHeight;
//        }
//        int xAxisMaxLabelHeight = 0;
//        String text_String;
//        int textWidth;
//        double angle;
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
//            xAxisMaxLabelHeight = Math.max(xAxisMaxLabelHeight, textWidth);
//            angle = Math.PI / 2;
//            writeText(
//                    text_String,
//                    angle,
//                    col - (textHeight / 3),
//                    row + scaleTickAndTextSeparation + scaleTickLength);
////                    row + scaleTickLength + (textHeight / 3));
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
//            xAxisMaxLabelHeight = Math.max(xAxisMaxLabelHeight, textWidth);
//            angle = Math.PI / 2;
//            writeText(
//                    text_String,
//                    angle,
//                    col - (textHeight / 3),
//                    row + scaleTickAndTextSeparation + scaleTickLength);
////                    row + scaleTickLength + (textHeight / 3));
//        }
//        xAxisExtraHeightBottom += xAxisMaxLabelHeight;
//        //xAxisExtraHeightBottom += scaleTickAndTextSeparation + scaleTickLength + seperationDistanceOfAxisAndData;
//        xAxisExtraWidthRight += textHeight * 2;
//        xAxisExtraWidthLeft += textHeight / 2;
//
//        // Add the X axis label
//        setPaint(Color.BLACK);
//        text_String = xAxisLabel;
//        //textWidth = getTextWidth(text_String);
//
//        xAxisExtraHeightBottom += partTitleGap;
//
//        drawString(
//                text_String,
//                dataStartCol + (dataWidth / 2),
//                row + xAxisExtraHeightBottom);
//
//        if (!addLegend) {
//            xAxisExtraHeightBottom += textHeight;
//        }
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
//            int interval, // ignored
//            int textHeight,
//            int startAgeOfEndYearInterval, // ignored
//            int scaleTickLength,
//            int scaleTickAndTextSeparation,
//            int partTitleGap) {
//        int[] result = new int[1];
//        //int seperationDistanceOfAxisAndData = partTitleGap * 2;
//        int seperationDistanceOfAxisAndData = textHeight;
//        int yAxisExtraWidthLeft = scaleTickLength + scaleTickAndTextSeparation + seperationDistanceOfAxisAndData;
//
//        Line2D ab;
//        int text_Height = getTextHeight();
//        String text_String;
//        int text_Width;
//        int row;
//        int increment;
//
//        // Draw Y axis
//        //setOriginCol();
//        setPaint(Color.GRAY);
//        ab = new Line2D.Double(
//                originCol,
//                dataStartRow,
//                originCol,
//                dataEndRow);
//        draw(ab);
//
//        // Draw Y axis labels
//        setPaint(Color.GRAY);
//        //int col = dataStartCol - text_Height;
//        //int col = dataStartCol - partTitleGap;
//        int col = dataStartCol - seperationDistanceOfAxisAndData;
//        ab = new Line2D.Double(
//                col,
//                dataEndRow,
//                col,
//                dataStartRow);
//        draw(ab);
//        increment = text_Height;
//        while (((dataHeight * text_Height) + 4) / increment > dataHeight) {
//            increment += text_Height;
//        }
//        int yAxisMaxLabelWidth = 0;
//        // From the origin up
//        for (row = this.originRow; row >= this.dataStartRow; row -= increment) {
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
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        y,
//                        Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
//                        _RoundingMode).toString();
//            }
//            text_Width = getTextWidth(text_String);
//            yAxisMaxLabelWidth = Math.max(yAxisMaxLabelWidth, text_Width);
//            drawString(
//                    text_String,
//                    col - scaleTickAndTextSeparation - scaleTickLength - text_Width,
//                    row + (textHeight / 3));
//        }
//        // From the origin down
//        for (row = this.originRow; row <= this.dataEndRow; row += increment) {
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
//                text_String = "" + Generic_BigDecimal.roundStrippingTrailingZeros(
//                        y,
//                        Generic_BigDecimal.getDecimalPlacePrecision(y, significantDigits),
//                        _RoundingMode).toString();
//            }
//            text_Width = getTextWidth(text_String);
//            yAxisMaxLabelWidth = Math.max(yAxisMaxLabelWidth, text_Width);
//            drawString(
//                    text_String,
//                    col - scaleTickAndTextSeparation - scaleTickLength - text_Width,
//                    row + (textHeight / 3));
//        }
//        yAxisExtraWidthLeft += scaleTickLength + scaleTickAndTextSeparation + yAxisMaxLabelWidth;
//
//        // Add the Y axis label
//        setPaint(Color.BLACK);
//        text_String = yAxisLabel;
//        text_Width = getTextWidth(text_String);
//        yAxisExtraWidthLeft += (textHeight * 2) + partTitleGap;
//        double angle = 3.0d * Math.PI / 2.0d;
//        writeText(
//                text_String,
//                angle,
//                3 * textHeight / 2,
//                dataMiddleRow + (text_Width / 2));
//        // Draw Y axis
//        setPaint(Color.LIGHT_GRAY);
//        ab = new Line2D.Double(
//                originCol,
//                dataStartRow,
//                originCol,
//                dataEndRow);
//
//        draw(ab);
//
//
//        result[0] = yAxisExtraWidthLeft;
//        return result;
//
//    }
//
//    @Override
//    public void setOriginCol() {
//        originCol = coordinateToScreenCol(BigDecimal.ZERO);
//        System.out.println("originCol " + originCol);
////        if (minX.compareTo(BigDecimal.ZERO) == 0) {
////            originCol = dataStartCol;
////            //originCol = dataStartCol - dataEndCol / 2;
////        } else {
////            if (cellWidth.compareTo(BigDecimal.ZERO) == 0) {
////                originCol = dataStartCol;
////            } else {
////                originCol = Generic_BigDecimal.divideRoundIfNecessary(
////                        minX,
////                        cellWidth,
////                        0,
////                        _RoundingMode).intValueExact()
////                        + dataStartCol;
////            }
////        }
//    }
    /**
     * @param regressionParameters regressionParameters[0] is the y axis
     * intercept; regressionParameters[1] is the change in y relative to x
     * (gradient or slope); regressionParameters[2] is the rank correlation
     * coefficient (RSquare); regressionParameters[3] is data[0].length.
     */
    protected void drawLegend(
            double[] regressionParameters) {
//        int[] result = new int[3];

        int newLegendWidth = 0;
        int newLegendHeight = 0;
//        int legendExtraWidthLeft = 0;
//        int legendExtraWidthRight = 0;
        int textHeight = getTextHeight();
        int legendExtraHeightBottom = textHeight;

        int legendStartRow = getDataEndRow() + getxAxisHeight();
//        int legendStartRow = this.dataEndRow + this.xAxisHeight / 2;
        int symbolRow;
        int row;
        int symbolCol;
        int col;
        int symbolWidth = 10;
        // Legend Title
        setPaint(Color.DARK_GRAY);
        int legendItemWidth = 0;

        String text = "Legend";
        int textWidth = getTextWidth(text);
        newLegendHeight += textHeight;
        row = legendStartRow + newLegendHeight;
        //col = dataStartCol - yAxisWidth;
        col = textHeight;
        legendItemWidth += textWidth;
        newLegendWidth = Math.max(newLegendWidth, legendItemWidth);
        drawString(
                text,
                col,
                row);
        Point2D.Double point = new Point2D.Double();

        // Point marker
        legendItemWidth = 0;
        newLegendHeight += textHeight;
        symbolRow = legendStartRow + newLegendHeight;
        legendItemWidth += symbolWidth;
        symbolCol = col + symbolWidth / 2;
        point.setLocation(
                symbolCol,
                symbolRow);
        setPaint(Color.DARK_GRAY);
        draw(point);
        row += ((3 * textHeight) / 2) - 2;
        newLegendHeight += (textHeight / 2) - 2;
        col += symbolWidth + 4;
        setPaint(Color.GRAY);
        text = "Data Point";
        textWidth = getTextWidth(text);
        legendItemWidth += textWidth;
        newLegendWidth = Math.max(newLegendWidth, legendItemWidth);
        drawString(
                text,
                col,
                row);
        // Y = X line
        setPaint(Color.LIGHT_GRAY);
        //int itemSymbolWidth = (symbolCol + symbolWidth / 2) - (symbolCol - symbolWidth / 2);
        //legendItemWidth = itemSymbolWidth;        
        //legendItemWidth = symbolWidth + 4;
        symbolRow += textHeight;
        draw(new Line2D.Double(
                symbolCol - symbolWidth / 2,
                (symbolRow + textHeight / 2) - 2,
                symbolCol + symbolWidth / 2,
                (symbolRow - textHeight / 2) + 2));
        setPaint(Color.GRAY);
        row += textHeight;
        text = "Y = X";
        textWidth = getTextWidth(text);
        drawString(
                text,
                col,
                row);
        legendItemWidth = textWidth;
        newLegendWidth = Math.max(newLegendWidth, legendItemWidth);
        newLegendHeight += textHeight + 4;

        // Regression line
        setPaint(Color.DARK_GRAY);
        legendItemWidth = symbolWidth + 4;
        symbolRow += textHeight;
        row += textHeight;
        draw(new Line2D.Double(
                symbolCol - symbolWidth / 2,
                (symbolRow + textHeight / 2) - 2,
                symbolCol + symbolWidth / 2,
                (symbolRow - textHeight / 2) + 2));
        setPaint(Color.GRAY);
        // Y = mX + c
        // generalise m
        int scale = 4;
        BigDecimal m;
        if (Double.isNaN(regressionParameters[1])) {
            m = BigDecimal.ZERO;
        } else {
            m = BigDecimal.valueOf(regressionParameters[1]);
        }
        RoundingMode roundingMode = getRoundingMode();
        m = m.setScale(scale, roundingMode);
        m = m.stripTrailingZeros();
//        m = Generic_BigDecimal.roundStrippingTrailingZeros(
//                m, 
//                decimalPlacePrecision,
//                _RoundingMode);
        BigDecimal c;
        if (Double.isNaN(regressionParameters[0])) {
            c = BigDecimal.ZERO;
        } else {
            c = BigDecimal.valueOf(regressionParameters[0]);
        }
        c = c.setScale(scale, roundingMode);
        c = c.stripTrailingZeros();
        BigDecimal rsquare;
        if (Double.isNaN(regressionParameters[2])) {
            rsquare = BigDecimal.ZERO;
        } else {
            rsquare = BigDecimal.valueOf(regressionParameters[2]);
        }
        rsquare = rsquare.setScale(3, roundingMode);
        rsquare = rsquare.stripTrailingZeros();
        String equation;
        if (c.compareTo(BigDecimal.ZERO) != -1) {
            equation = "Y = (" + m + " * X) + " + c + "";
        } else {
            equation = "Y = (" + m + " * X) - " + c.negate() + "";
        }
        drawString(
                equation,
                col,
                row);
        textWidth = getTextWidth(equation);
        legendItemWidth += textWidth;
        newLegendWidth = Math.max(newLegendWidth, legendItemWidth);
        newLegendHeight += textHeight;

        // Rsquare component
        String rsquare_String = "RSquare = " + rsquare;
        textWidth = getTextWidth(rsquare_String);
        legendItemWidth = textWidth;
        row += textHeight;
        drawString(
                rsquare_String,
                col,
                row);
        //setLegendHeight(row - legendStartRow);
        newLegendWidth = Math.max(newLegendWidth, legendItemWidth);
        newLegendHeight += (2 * textHeight);

        int imageWidth = getImageWidth();
        int dataWidth = getDataWidth();
        int extraWidthLeft = getExtraWidthLeft();
        if (newLegendWidth > getLegendWidth()) {
            //int diff = newLegendWidth - legendWidth;
            if (newLegendWidth > imageWidth) {
                setImageWidth(newLegendWidth);
                setExtraWidthRight(newLegendWidth - extraWidthLeft - dataWidth);
            }
            setLegendWidth(newLegendWidth);
        }
        int extraHeightBottom = getExtraHeightBottom();
        if (newLegendHeight > getLegendHeight()) {
            //int diff = newLegendHeight - legendHeight;
            //int heightForLegend = legendStartRow - dataStartRow + newLegendHeight;
            int newExtraHeightBottom = newLegendHeight + getxAxisHeight();
            if (newExtraHeightBottom > extraHeightBottom) {
                int diff2 = newExtraHeightBottom - extraHeightBottom;
                setExtraHeightBottom(newExtraHeightBottom);
                setImageHeight(getImageHeight() + diff2);
            }
            setLegendHeight(newLegendHeight);
        }
//        result[0] = legendExtraWidthLeft;
//        result[1] = legendExtraWidthRight;
//        result[2] = legendExtraHeightBottom;

//        return result;

    }

    protected double[][] getDataAsDoubleArray(ArrayList<Generic_XYNumericalData> theGeneric_XYNumericalData) {
        double[][] result = new double[2][theGeneric_XYNumericalData.size()];
        Iterator<Generic_XYNumericalData> ite = theGeneric_XYNumericalData.iterator();
        Generic_XYNumericalData aGeneric_XYNumericalData;
        /*
         * data[0][] are the y values data[1][] are the x values
         */
        int n = 0;
        while (ite.hasNext()) {
            aGeneric_XYNumericalData = ite.next();
            result[0][n] = aGeneric_XYNumericalData.getY().doubleValue();
            result[1][n] = aGeneric_XYNumericalData.getX().doubleValue();
            n++;
        }
        return result;
    }

    protected void drawYEqualsXLineData(double[][] dataAsDoubleArray) {
        double[][] yEqualsXLineData = getYEqualsXLineData(
                dataAsDoubleArray);
        setPaint(Color.LIGHT_GRAY);
        draw(new Line2D.Double(
                coordinateToScreenCol(BigDecimal.valueOf(yEqualsXLineData[1][0])),
                coordinateToScreenRow(BigDecimal.valueOf(yEqualsXLineData[0][0])),
                coordinateToScreenCol(BigDecimal.valueOf(yEqualsXLineData[1][1])),
                coordinateToScreenRow(BigDecimal.valueOf(yEqualsXLineData[0][1]))));
    }

    protected void drawRegressionLine(
            double[] regressionParameters,
            double[][] dataAsDoubleArray) {
        double[][] regressionLineXYLineData = getXYLineData(
                dataAsDoubleArray,
                regressionParameters);
        setPaint(Color.BLACK);
        draw(new Line2D.Double(
                coordinateToScreenCol(BigDecimal.valueOf(regressionLineXYLineData[1][0])),
                coordinateToScreenRow(BigDecimal.valueOf(regressionLineXYLineData[0][0])),
                coordinateToScreenCol(BigDecimal.valueOf(regressionLineXYLineData[1][1])),
                coordinateToScreenRow(BigDecimal.valueOf(regressionLineXYLineData[0][1]))));
//                coordinateToScreenCol(BigDecimal.valueOf(regressionLineXYLineData[0][1])),
//                coordinateToScreenRow(BigDecimal.valueOf(regressionLineXYLineData[1][0])),
//                coordinateToScreenCol(BigDecimal.valueOf(regressionLineXYLineData[0][0])),
//                coordinateToScreenRow(BigDecimal.valueOf(regressionLineXYLineData[1][1]))));
    }

    /**
     * @param data double[2][] where: data[0][] are the y values data[1][] are
     * the x values
     * @return double[] result where: <ul> <li>result[0] is the y axis
     * intercept;</li> <li>result[1] is the change in y relative to x (gradient
     * or slope);</li> <li>result[2] is the rank correlation coefficient
     * (RSquare);</li> <li>result[3] is data[0].length.</li> </ul>
     */
    public static double[] getSimpleRegressionParameters(double[][] data) {
        double[] result = new double[4];
        // org.apache.commons.math.stat.regression.SimpleRegression;
        SimpleRegression a_SimpleRegression = new SimpleRegression();
        //System.out.println("data.length " + data[0].length);
        for (int i = 0; i < data[0].length; i++) {
            a_SimpleRegression.addData(data[1][i], data[0][i]);
            //aSimpleRegression.addData(data[0][i], data[1][i]);
        }
        result[0] = a_SimpleRegression.getIntercept();
        result[1] = a_SimpleRegression.getSlope();
        result[2] = a_SimpleRegression.getRSquare();
        result[3] = data[0].length;
        return result;
    }

    /**
     *
     * @param data
     * @param lineParameters
     * @return
     */
    public static double[][] getXYLineData(
            double[][] data,
            double[] lineParameters) {
        double[][] result = new double[2][2];
        double miny = Double.MAX_VALUE;
        double maxy = Double.MIN_VALUE;
        double minx = Double.MAX_VALUE;
        double maxx = Double.MIN_VALUE;
        for (int j = 0; j < data[0].length; j++) {
            minx = Math.min(minx, data[0][j]);
            maxx = Math.max(maxx, data[0][j]);
            miny = Math.min(miny, data[1][j]);
            maxy = Math.max(maxy, data[1][j]);
        }
        result[0][0] = minx;
        result[0][1] = maxx;
        result[1][0] = miny;
        result[1][1] = maxy;
//        System.out.println("miny " + minx);
//        System.out.println("maxy " + maxx);
//        System.out.println("minx " + miny);
//        System.out.println("maxx " + maxy);
        double m = lineParameters[1];
        double c = lineParameters[0];
        // y = (m * x) + c
        // x = (y - c) / m
        // minyx stores the y at minx
        double minyx;
        // maxyx stores the y at maxx 
        double maxyx;
        if (m != 0) {
            minyx = (minx - c) / m;
            maxyx = (maxx - c) / m;
        } else {
            minyx = miny;
            maxyx = maxy;
        }
        // minxy stores the x at miny
        double minxy = (m * miny) + c;
        // maxxy stores the x at maxy
        double maxxy = (m * maxy) + c;


        if (maxxy < maxx) {
            result[0][1] = maxxy;
        } else {
            result[1][1] = maxyx;
        }
        if (minxy > minx) {
            result[0][0] = minxy;
        } else {
            result[1][0] = minyx;
        }

        if (maxyx < maxy) {
            result[1][1] = maxyx;
        } else {
            result[0][1] = maxxy;
        }

        if (minyx > miny) {
            result[1][0] = minyx;
        } else {
            result[0][0] = minxy;
        }



        if (Double.isNaN(result[1][0])) {
            if (Double.isNaN(result[0][0])) {
                result[1][0] = 0;
                result[0][0] = 0;
            } else {
                result[1][0] = result[0][0];
                //result[1][0] = 0;
            }
        }
        if (Double.isNaN(result[1][1])) {
            if (Double.isNaN(result[0][1])) {
                result[1][1] = 0;
                result[0][1] = 0;
            } else {
                result[1][1] = result[0][1];
                //result[1][1] = 0;
            }
        }

        System.out.println("Line Segment");
        System.out.println(
                "(minx,miny) ("
                + result[1][0] + ","
                + result[0][0] + ")");
        System.out.println(
                "(maxx,maxy) ("
                + result[1][1] + ","
                + result[0][1] + ")");
        return result;
    }

    /**
     *
     *
     * @param data
     * @return 
     */
    public static double[][] getYEqualsXLineData(
            double[][] data) {
        double[][] lineChartData = new double[2][2];
        // minx is the minimum x value in data[1]
        double minx = Double.MAX_VALUE;
        // maxx is the maximum x value in data[1]
        double maxx = Double.MIN_VALUE;
        // miny is the minimum y value in data[0]
        double miny = Double.MAX_VALUE;
        // maxy is the maximum y value in data[1]
        double maxy = Double.MIN_VALUE;
        for (int j = 0; j < data[0].length; j++) {
            miny = Math.min(miny, data[0][j]);
            maxy = Math.max(maxy, data[0][j]);
            minx = Math.min(minx, data[1][j]);
            maxx = Math.max(maxx, data[1][j]);
        }
        lineChartData[0][0] = miny;
        lineChartData[0][1] = maxy;
        lineChartData[1][0] = minx;
        lineChartData[1][1] = maxx;
        //System.out.println("miny " + miny);
        if (maxy == Double.MIN_VALUE) {
            maxy = miny;
        }
        //System.out.println("maxy " + maxy);
        //System.out.println("minx " + minx);
        if (maxx == Double.MIN_VALUE) {
            maxx = minx;
        }
        //System.out.println("maxx " + maxx);
        if (maxx < maxy) {
            lineChartData[0][1] = maxx;
        } else {
            lineChartData[1][1] = maxy;
        }
        if (minx > miny) {
            lineChartData[0][0] = minx;
        } else {
            lineChartData[1][0] = miny;
        }
        return lineChartData;
    }

    /**
     *
     * @param simpleRegressionParameters are as per the output returned from
     * getSimpleRegressionParameters(double[][])
     * @return String[] result where; result[0] is of the form "Y = m * X + c"
     * where m and c are numbers result[1]
     */
    public static String[] getSimpleRegressionParametersStrings(double[] simpleRegressionParameters) {
        String[] result = new String[3];
        result[0] = "Y = " + simpleRegressionParameters[1] + " * X + " + simpleRegressionParameters[0];
        result[1] = "RSquare " + simpleRegressionParameters[2];
        result[2] = "n " + simpleRegressionParameters[3];
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
        return result;
    }
}

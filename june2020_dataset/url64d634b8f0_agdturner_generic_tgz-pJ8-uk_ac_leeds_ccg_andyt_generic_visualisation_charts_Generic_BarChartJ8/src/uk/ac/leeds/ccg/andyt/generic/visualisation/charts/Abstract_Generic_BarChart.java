/**
 * Copyright 2015 Andy Turner, The University of Leeds, UK
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
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.utilities.Generic_Collections;

/**
 * An abstract class for creating Age by Gender Population visualisations and
 * possibly rendering them in a lightweight component as suited to headless
 * rendering.
 */
public abstract class Abstract_Generic_BarChart extends Generic_Plot {

    private int xAxisIncrement;
    private int numberOfYAxisTicks;
    private BigDecimal yMax;
    private BigDecimal yPin;
    private BigDecimal yAxisIncrement;
    private int barWidth;
    private int barGap;

    protected final void init(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawAxesOnPlot,
            int ageInterval,
            Integer startAgeOfEndYearInterval,
            int decimalPlacePrecisionForCalculations,
            int significantDigits,
            RoundingMode aRoundingMode) {
        setAgeInterval(ageInterval);
        setStartAgeOfEndYearInterval(startAgeOfEndYearInterval);
        super.init(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawAxesOnPlot,
                decimalPlacePrecisionForCalculations,
                significantDigits,
                aRoundingMode);
    }

    @Override
    public void initialiseParameters(Object[] data) {
        Object[] intervalCountsAndLabels;
        intervalCountsAndLabels = (Object[]) data[0];

        TreeMap<Integer, Integer> map;
        map = (TreeMap<Integer, Integer>) intervalCountsAndLabels[0];

        BigDecimal[] minmMaxBigDecimal = (BigDecimal[]) data[1];
        BigDecimal minX = minmMaxBigDecimal[0];
        setMinX(minX);
        BigDecimal intervalWidth = (BigDecimal) data[2];
        BigDecimal maxX;
        maxX = new BigDecimal(map.lastKey()).multiply(intervalWidth);
        setMaxX(maxX);
        int[] minMax = Generic_Collections.getMinMaxInteger(map);
        BigDecimal minY = BigDecimal.ZERO;
        setMinY(minY);
        BigDecimal maxY = BigDecimal.valueOf(minMax[1]);
        BigDecimal yMax = getyMax();
        if (yMax == null) {
            setMaxY(maxY);
        } else {
            if (maxY.compareTo(yMax) == 1) {
                setMaxY(maxY);
            } else {
                setMaxY(yMax);
            }
        }
        setCellHeight();
        setCellWidth();
        setOriginRow();
        setOriginCol();

        RoundingMode roundingMode = getRoundingMode();
        BigDecimal cellWidth = getCellWidth();
        if (cellWidth.compareTo(BigDecimal.ZERO) == 0) {
            barWidth = 1;
        } else {
            barWidth = Generic_BigDecimal.divideRoundIfNecessary(
                    intervalWidth,
                    cellWidth,
                    0,
                    roundingMode).intValue() - (2 * barGap);
        }
        if (barWidth < 1) {
            barWidth = 1;
        }
        
        int xIncrement;
        xIncrement = getxIncrement();
        if (xIncrement == 0) {
            xIncrement = 1;
            setxIncrement(xIncrement);
        }
    }

    @Override
    public void setOriginCol() {
        setOriginCol(getDataStartCol());
        //setOriginCol((getDataStartCol() + getDataEndCol()) / 2);
//        if (minX.compareTo(BigDecimal.ZERO) == 0) {
//            originCol = dataStartCol;
//            //originCol = dataStartCol - dataEndCol / 2;
//        } else {
//            if (cellWidth.compareTo(BigDecimal.ZERO) == 0) {
//                originCol = dataStartCol;
//            } else {
//                originCol = Generic_BigDecimal.divideRoundIfNecessary(
//                    BigDecimal.ZERO.subtract(minX),
//                    cellWidth,
//                    0,
//                    _RoundingMode).intValueExact()
//                    + dataStartCol;
//            }
//        }
    }

    /**
     * Draws the Y axis.
     *
     * @param pin This is a value that should appear on the Yaxis
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @param scaleTickAndTextSeparation
     * @return an int[] result for setting display parameters where: result[0] =
     * yAxisExtraWidthLeft;
     * @TODO Better handle case when yAxisLabel has a text width wider than
     * image is high
     */
    //@Override
    public int[] drawYAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        int[] result = new int[1];
        MathContext mc;
        mc = new MathContext(
                getDecimalPlacePrecisionForCalculations(),
                RoundingMode.HALF_UP);

        BigDecimal yMax;
        yMax = getyMax();

        BigDecimal rowValue;
        BigDecimal minY;
        minY = getMinY();
        BigDecimal maxY;
        maxY = getMaxY();

        BigDecimal pin;
        BigDecimal yIncrement;
        pin = getyPin();
        yIncrement = getyIncrement();

        if (pin != null) {
            // Initialise rowValue the lowest value in the
            int pinCompareToMinY;
            pinCompareToMinY = pin.compareTo(minY);
            if (pinCompareToMinY != 0) {
                if (pinCompareToMinY == 1) {
                    int pinCompareToMaxY;
                    pinCompareToMaxY = pin.compareTo(maxY);
                    if (pinCompareToMaxY != 1) {
                        rowValue = pin;
                        while (rowValue.compareTo(minY) != 1) {
                            rowValue = rowValue.subtract(yIncrement);
                        }
                    } else {
                        throw new UnsupportedOperationException(this.getClass().getName() + ".drawYAxis(int, BigDecimal, BigDecimal, int, int, int, int)");
                    }
                } else {
                    rowValue = pin;
                    while (rowValue.compareTo(minY) == -1) {
                        rowValue = rowValue.add(yIncrement);
                    }
                }
            } else {
                rowValue = minY;
            }
        } else {
            rowValue = minY;
        }

        int numberOfTicks;
        if (yIncrement != null) {
            if (rowValue != null) {
                numberOfTicks = ((maxY.subtract(rowValue)).divide(yIncrement, mc)).intValue();
            } else {
                numberOfTicks = ((maxY.subtract(minY)).divide(yIncrement, mc)).intValue();
            }
        } else {
            numberOfTicks = getnumberOfYAxisTicks();
            if (rowValue != null) {
                yIncrement = (maxY.subtract(rowValue)).divide(new BigDecimal(numberOfTicks), mc);
            } else {
                yIncrement = (maxY.subtract(minY)).divide(new BigDecimal(numberOfTicks), mc);
            }
        }

        int yAxisExtraWidthLeft = scaleTickLength + scaleTickAndTextSeparation
                + seperationDistanceOfAxisAndData;
        int col = getDataStartCol() - seperationDistanceOfAxisAndData;
        int dataStartRow = getDataStartRow();
        int dataEndRow = getDataEndRow();
        Line2D ab;
        // Draw Y axis scale to the left side
        setPaint(Color.GRAY);
        ab = new Line2D.Double(
                col,
                dataEndRow,
                col,
                dataStartRow);
        draw(ab);
        setPaint(Color.GRAY);
        String text_String;
        int textWidth;
        int maxTickTextWidth = 0;
        boolean first = true;
        int row0 = coordinateToScreenRow(rowValue);
        int previousRow = row0;
        for (int i = 0; i < numberOfTicks; i++) {
            int row = coordinateToScreenRow(rowValue);
            setPaint(Color.GRAY);
            ab = new Line2D.Double(
                    col,
                    row,
                    col - scaleTickLength,
                    row);
            draw(ab);
            if (first || (previousRow - row) > textHeight) {
                text_String = "" + rowValue;
                textWidth = getTextWidth(text_String);
                drawString(
                        text_String,
                        col - scaleTickAndTextSeparation - scaleTickLength - textWidth,
                        //row);
                        row + (textHeight / 3));
                maxTickTextWidth = Math.max(maxTickTextWidth, textWidth);
                previousRow = row;
                first = false;
            }
            rowValue = rowValue.add(yIncrement);
        }
        // <drawEndOfYAxisTick>
        int row = coordinateToScreenRow(maxY);
        setPaint(Color.GRAY);
        ab = new Line2D.Double(
                col,
                row,
                col - scaleTickLength,
                row);
        draw(ab);
        if ((previousRow - row) > textHeight) {
            text_String = "" + rowValue;;
            textWidth = getTextWidth(text_String);
            drawString(
                    text_String,
                    col - scaleTickAndTextSeparation - scaleTickLength - textWidth,
                    //row);
                    row + (textHeight / 3));
            maxTickTextWidth = Math.max(maxTickTextWidth, textWidth);
        }
        // </drawEndOfYAxisTick>
        yAxisExtraWidthLeft += maxTickTextWidth;
        // Y axis label
        setPaint(Color.BLACK);
        String yAxisLabel = getyAxisLabel();
        textWidth = getTextWidth(yAxisLabel);
        double angle = 3.0d * Math.PI / 2.0d;
        col = 3 * textHeight / 2;
        writeText(
                yAxisLabel,
                angle,
                col,
                getDataMiddleRow() + (textWidth / 2));
        yAxisExtraWidthLeft += (textHeight * 2) + partTitleGap;
        result[0] = yAxisExtraWidthLeft;
        return result;
    }

    /**
     * Draw the X axis.
     *
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @param scaleTickAndTextSeparation
     * @return an int[] result for setting display parameters where: result[0] =
     * xAxisExtraWidthLeft; result[1] = xAxisExtraWidthRight; result[2] =
     * xAxisExtraHeightBottom.
     */
    @Override
    public int[] drawXAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
//        MathContext mc;
//        mc = new MathContext(getDecimalPlacePrecisionForCalculations(), getRoundingMode());               
        Object[] data = getData();
        Object[] intervalCountsLabelsMins;
        intervalCountsLabelsMins = (Object[]) data[0];
        TreeMap<Integer, Integer> counts;
        counts = (TreeMap<Integer, Integer>) intervalCountsLabelsMins[0];
        TreeMap<Integer, String> labels;
        labels = (TreeMap<Integer, String>) intervalCountsLabelsMins[1];
        TreeMap<Integer, BigDecimal> mins;
        mins = (TreeMap<Integer, BigDecimal>) intervalCountsLabelsMins[2];
//        int xIncrement;
//        xIncrement = getxIncrement();
//        if (xIncrement == 0) {
//            xIncrement = 1;
//            setxIncrement(xIncrement);
//        }
        int xAxisTickIncrement = getxIncrement();
        int dataStartCol = getDataStartCol();        
        int xIncrementWidth = coordinateToScreenCol(
                BigDecimal.valueOf(xAxisTickIncrement)) - dataStartCol;
        int[] result = new int[3];
        int xAxisExtraWidthLeft = 0;
        int extraAxisLength;
        //extraAxisLength = xIncrementWidth + (barGap * 2) + (barWidth / 2);
        extraAxisLength = xIncrementWidth + (barGap * 2);
        int xAxisExtraWidthRight = extraAxisLength;
        int xAxisExtraHeightBottom = seperationDistanceOfAxisAndData
                + scaleTickLength + scaleTickAndTextSeparation;
        int dataEndRow = getDataEndRow();
        int dataEndCol = getDataEndCol();
        Line2D ab;
        setPaint(Color.GRAY);
        int row = dataEndRow + seperationDistanceOfAxisAndData;
        // draw XAxis Line
        ab = new Line2D.Double(
                dataStartCol,
                row,
                dataEndCol + extraAxisLength,
                row);
        draw(ab);
        // Add ticks and labels
        int textRow = row + scaleTickLength + scaleTickAndTextSeparation;
        String text_String;
        int textWidth;
        int maxWidth = 0;
        double angle = 3.0d * Math.PI / 2.0d;
        int colCenterer = getBarGap() + getBarWidth() / 2;
        int col = getDataStartCol() + colCenterer;
        int previousCol = col;
        boolean first = true;
        Iterator<Integer> ite2;
        ite2 = counts.keySet().iterator();
        while (ite2.hasNext()) {
            Integer value = ite2.next();
            String label = labels.get(value);
            BigDecimal min = mins.get(value);
            col = coordinateToScreenCol(min) + colCenterer;
            //col = value * xAxisTickIncrement + col0;
            //System.out.println("" + value + ", " + count + ", \"" + label +  "\"");        
            ab = new Line2D.Double(
                    col,
                    row,
                    col,
                    row + scaleTickLength);
            draw(ab);
            if (first || (col - previousCol) > textHeight) {
                text_String = label;
                textWidth = getTextWidth(text_String);
                writeText(
                        text_String,
                        angle,
                        col + (textHeight / 3),
                        textRow + textWidth);

                maxWidth = Math.max(maxWidth, textWidth);
                previousCol = col;
                first = false;
            }
        }
        xAxisExtraWidthLeft += textHeight;
        xAxisExtraHeightBottom += maxWidth;
        xAxisExtraWidthRight += textHeight / 2;
        textRow += maxWidth + partTitleGap + textHeight;
        xAxisExtraHeightBottom += partTitleGap + textHeight;
        setPaint(Color.BLACK);
        text_String = getxAxisLabel();
        textWidth = getTextWidth(text_String);
        drawString(
                text_String,
                (dataEndCol - dataStartCol) / 2 + dataStartCol - textWidth / 2,
                textRow);
        int endOfAxisLabelCol = (dataEndCol - dataStartCol) / 2 + dataStartCol + textWidth / 2 + 1; // Add one to cover rounding issues.
        if (endOfAxisLabelCol > dataEndCol + xAxisExtraWidthRight) {
            int diff = endOfAxisLabelCol - (dataEndCol + xAxisExtraWidthRight);
            xAxisExtraWidthRight += diff;
        }
        xAxisExtraHeightBottom += textHeight + 2;
        result[0] = xAxisExtraWidthLeft;
        result[1] = xAxisExtraWidthRight;
        result[2] = xAxisExtraHeightBottom;
        return result;
    }

//    /**
//     * Draw the X axis.
//     *
//     * @param seperationDistanceOfAxisAndData
//     * @param partTitleGap
//     * @param scaleTickAndTextSeparation
//     * @return an int[] result for setting display parameters where: result[0] =
//     * xAxisExtraWidthLeft; result[1] = xAxisExtraWidthRight; result[2] =
//     * xAxisExtraHeightBottom.
//     */
//    @Override
//    public int[] drawXAxis(
//            int textHeight,
//            int scaleTickLength,
//            int scaleTickAndTextSeparation,
//            int partTitleGap,
//            int seperationDistanceOfAxisAndData) {
//
//        int xAxisTickIncrement = getxIncrement();
//        int xIncrementWidth = coordinateToScreenCol(BigDecimal.valueOf(xAxisTickIncrement)) - getDataStartCol();
//
//        int[] result = new int[3];
//        int xAxisExtraWidthLeft = 0;
//        int xAxisExtraWidthRight = xIncrementWidth;
//        int xAxisExtraHeightBottom = seperationDistanceOfAxisAndData
//                + scaleTickLength + scaleTickAndTextSeparation;
//        int dataStartCol = getDataStartCol();
//        //int originRow = getOriginRow();
//        int dataEndRow = getDataEndRow();
//        int dataEndCol = getDataEndCol();
////        setDataEndCol(dataEndCol);
////        setDataWidth(getDataWidth() + xIncrementWidth);
////        setImageWidth(getImageWidth() + xIncrementWidth);
//        BigDecimal maxX = getMaxX();
//        int significantDigits = getSignificantDigits();
//        Line2D ab;
//        setPaint(Color.GRAY);
//        int row = dataEndRow + seperationDistanceOfAxisAndData;
//        // draw XAxis Line
//        ab = new Line2D.Double(
//                dataStartCol,
//                row,
//                dataEndCol + xIncrementWidth,
//                row);
//        draw(ab);
//        // Add ticks and labels
//        int textRow = row + scaleTickLength + scaleTickAndTextSeparation;
//        String text_String;
//        int textWidth;
//        MathContext mc = new MathContext(
//                significantDigits, RoundingMode.CEILING);
//        //int mini = getMinY().intValue();
//        //int maxi = maxX.round(mc).intValueExact();
//        
//        BigDecimal minX;
//        minX = getMinX();
//        int iterations;
//        iterations = maxX.subtract(minX).divide(BigDecimal.valueOf(xAxisTickIncrement), mc).intValue();
//        int maxWidth = 0;
//        double angle = 3.0d * Math.PI / 2.0d;
//        
//        BigDecimal x;
//        x = minX;
//        
//        BigDecimal xIncrement;
//        xIncrement = BigDecimal.valueOf(xAxisTickIncrement);
//        int col0;
//        int col = getDataStartCol() + getBarGap() + getBarWidth() / 2;
//        col0 = col;
////        for (int i = mini; i <= maxi + xAxisTickIncrement; i += xAxisTickIncrement) {
//        for (int i = 0; i <= iterations; i ++) {
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col,
//                    row + scaleTickLength);
//            draw(ab);
//            if (i == 0 || (col - col0) > textHeight) {
//                text_String = "" + x;
//
//                textWidth = getTextWidth(text_String);
//
////            int textRow0 = textRow + textWidth;
//                writeText(
//                        text_String,
//                        angle,
//                        col + (textHeight / 3), //col - (textWidth / 2),
//                        textRow + textWidth);
//
//                maxWidth = Math.max(maxWidth, textWidth);
//                col0 = col;
//            }
////            drawString(
////                    text_String,
////                    col - (textWidth / 2),
////                    textRow);
//            if (i == 0) {
//                // Add to imageWidth as this label sticks out
//                //xAxisExtraWidthLeft += (textWidth / 2) + textHeight;
//                xAxisExtraWidthLeft += textHeight;
//            }
//            x = x.add(xIncrement);
//            col += coordinateToScreenCol(x);
//
//            
//
//        }
//        xAxisExtraHeightBottom += maxWidth;
////        // Check to see if plot needs to grow
////        if (xAxisExtraWidthLeft > xAxisExtraWidthLeft) {
////            int diff = xAxisExtraWidthLeft - dataStartCol;
////            imageWidth += diff;
////            dataStartCol += diff;
////            dataEndCol += diff;
////            xAxisExtraWidthLeft = xAxisExtraWidthLeft;
////            setOriginCol();
////        }
//
//        // Add to imageWidth as this label sticks out
////        xAxisExtraWidthRight += (textWidth / 2) + textHeight;
//        xAxisExtraWidthRight += textHeight / 2;
////        if (xAxisExtraWidthRight > xAxisExtraWidthRight) {
////            imageWidth += axesExtraWidthRight - xAxisExtraWidthRight;
////            xAxisExtraWidthRight = axesExtraWidthRight;
////        }
//        // Add axis labels
////        textRow += textHeight + partTitleGap;
//        textRow += maxWidth + partTitleGap + textHeight;
////        xAxisExtraHeightBottom += textHeight + partTitleGap;
//        xAxisExtraHeightBottom += partTitleGap + textHeight;
//        setPaint(Color.BLACK);
//        text_String = getxAxisLabel();
////        text_String = "Population";
//        textWidth = getTextWidth(text_String);
//        drawString(
//                text_String,
//                (dataEndCol - dataStartCol) / 2 - (textWidth / 2),
//                //dataEndCol + xAxisExtraHeightBottom + 2,
//                textRow);
//        xAxisExtraHeightBottom += textHeight + 2;
//        result[0] = xAxisExtraWidthLeft;
//        result[1] = xAxisExtraWidthRight;
//        result[2] = xAxisExtraHeightBottom;
//        return result;
//    }
    public int getxIncrement() {
        return xAxisIncrement;
    }

    public void setxIncrement(int xIncrement) {
        this.xAxisIncrement = xIncrement;
    }

    public int getnumberOfYAxisTicks() {
        return numberOfYAxisTicks;
    }

    public void setnumberOfYAxisTicks(int numberOfYAxisTicks) {
        this.numberOfYAxisTicks = numberOfYAxisTicks;
    }

    /**
     * @return the yPin
     */
    public BigDecimal getyPin() {
        return yPin;
    }

    /**
     * @param yPin the yPin to set
     */
    public void setyPin(BigDecimal yPin) {
        this.yPin = yPin;
    }

    /**
     * @return the yAxisIncrement
     */
    public BigDecimal getyIncrement() {
        return yAxisIncrement;
    }

    /**
     * @param yIncrement the yAxisIncrement to set
     */
    public void setyIncrement(BigDecimal yIncrement) {
        this.yAxisIncrement = yIncrement;
    }

    /**
     * @return the barWidth
     */
    public int getBarWidth() {
        return barWidth;
    }

    /**
     * @param barWidth the barWidth to set
     */
    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
    }

    /**
     * @return the barGap
     */
    public int getBarGap() {
        return barGap;
    }

    /**
     * @param barGap the barGap to set
     */
    public void setBarGap(int barGap) {
        this.barGap = barGap;
    }

    /**
     * @return the yMax
     */
    public BigDecimal getyMax() {
        return yMax;
    }

    /**
     * @param yMax the yMax to set
     */
    public void setyMax(BigDecimal yMax) {
        this.yMax = yMax;
    }

}

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
import java.util.ArrayList;
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
public abstract class Abstract_Generic_LineGraph extends Generic_Plot {

    private BigDecimal yMax;
    private BigDecimal yPin;
    private BigDecimal yIncrement;
    private int numberOfYAxisTicks;

    private TreeMap<BigDecimal, String> xAxisLabels;
    private BigDecimal xMax;
    private BigDecimal xPin;
    private BigDecimal xIncrement;
    private int numberOfXAxisTicks;

    private Color[] colours;
    private ArrayList<String> labels;

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
        BigDecimal minY;
        BigDecimal maxY;
        BigDecimal minX;
        BigDecimal maxX;
        minY = (BigDecimal) data[1];
        maxY = (BigDecimal) data[2];
        minX = (BigDecimal) data[3];
        maxX = (BigDecimal) data[4];
        ArrayList<String> labels;
        labels = (ArrayList<String>) data[5];
        TreeMap<BigDecimal, String> xAxisLabels;
        xAxisLabels = (TreeMap<BigDecimal, String>) data[6];
        setMinY(minY);
        setMaxY(maxY);
        setMinX(minX);
        setMaxX(maxX);
        setLabels(labels);
        setxAxisLabels(xAxisLabels);
        setCellHeight();
        setCellWidth();
        setOriginRow();
        setOriginCol();
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
//        int[] result;
//        result = new int[1];
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
                        throw new UnsupportedOperationException(this.getClass().getName() + ".drawYAxis(int, int, int, int, int)");
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
            numberOfTicks = getNumberOfYAxisTicks();
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
                text_String = "" + Generic_BigDecimal.roundIfNecessary(rowValue, 2, RoundingMode.HALF_UP);
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
            text_String = "" + Generic_BigDecimal.roundIfNecessary(rowValue, 2, RoundingMode.HALF_UP);
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
        int[] result;
        result = new int[1];
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
        int[] result = new int[3];
        Object[] data = getData();

        MathContext mc;
        mc = new MathContext(
                getDecimalPlacePrecisionForCalculations(),
                RoundingMode.HALF_UP);

        BigDecimal xMax;
        xMax = getxMax();

        BigDecimal colValue;
        BigDecimal minX;
        minX = getMinX();
        BigDecimal maxX;
        maxX = getMaxX();

        BigDecimal pin;
        BigDecimal xIncrement;
        pin = getxPin();
        xIncrement = getxIncrement();

        if (pin != null) {
            // Initialise colValue
            int pinCompareToMinX;
            pinCompareToMinX = pin.compareTo(minX);
            if (pinCompareToMinX != 0) {
                if (pinCompareToMinX == 1) {
                    int pinCompareToMaxX;
                    pinCompareToMaxX = pin.compareTo(maxX);
                    if (pinCompareToMaxX != 1) {
                        colValue = pin;
                        while (colValue.compareTo(minX) != 1) {
                            colValue = colValue.subtract(xIncrement);
                        }
                    } else {
                        throw new UnsupportedOperationException(this.getClass().getName() + ".drawXAxis(int, int, int, int, int)");
                    }
                } else {
                    colValue = pin;
                    while (colValue.compareTo(minX) == -1) {
                        colValue = colValue.add(xIncrement);
                    }
                }
            } else {
                colValue = minX;
            }
        } else {
            colValue = minX;
        }

        int numberOfTicks;
        if (getxIncrement() != null) {
            if (colValue != null) {
                numberOfTicks = ((maxX.subtract(colValue)).divide(xIncrement, mc)).intValue();
            } else {
                numberOfTicks = ((maxX.subtract(minX)).divide(xIncrement, mc)).intValue();
            }
        } else {
            numberOfTicks = getNumberOfYAxisTicks();
            if (colValue != null) {
                xIncrement = (maxX.subtract(colValue)).divide(new BigDecimal(numberOfTicks), mc);
            } else {
                xIncrement = (maxX.subtract(minX)).divide(new BigDecimal(numberOfTicks), mc);
            }
        }

        int dataStartCol = getDataStartCol();
        int xAxisExtraWidthLeft = 0;
        int extraAxisLength;
        extraAxisLength = 0;
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
        //int colCenterer = getBarGap() + getBarWidth() / 2;
        int col = getDataStartCol();
        int previousCol = col;

        TreeMap<BigDecimal, String> xAxisLabels;
        xAxisLabels = getxAxisLabels();
        if (xAxisLabels != null) {
            boolean first = true;
            Iterator<BigDecimal> ite;
            ite = xAxisLabels.keySet().iterator();
            while (ite.hasNext()) {
                BigDecimal x;
                x = ite.next();
                String label = xAxisLabels.get(x);
                col = coordinateToScreenCol(x);
                if (col >= dataStartCol) {
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
            }
        } else {
            boolean first = true;
            BigDecimal x = minX;
            int i = 0;
            while (x.compareTo(maxX) != 1) {
//        for (int i = 0; i < numberOfTicks; i ++) {
                //String label = labels.get(value);
                x = minX.add(BigDecimal.valueOf(i));
                col = coordinateToScreenCol(x.multiply(xIncrement));
                if (col >= dataStartCol) {
                    ab = new Line2D.Double(
                            col,
                            row,
                            col,
                            row + scaleTickLength);
                    draw(ab);
                    if (first || (col - previousCol) > textHeight) {
                        text_String = x.toPlainString();
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
                i++;
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
     * @return the yIncrement
     */
    public BigDecimal getyIncrement() {
        return yIncrement;
    }

    /**
     * @param yIncrement the yIncrement to set
     */
    public void setyIncrement(BigDecimal yIncrement) {
        this.yIncrement = yIncrement;
    }

    /**
     * @return the numberOfYAxisTicks
     */
    public int getNumberOfYAxisTicks() {
        return numberOfYAxisTicks;
    }

    /**
     * @param numberOfYAxisTicks the numberOfYAxisTicks to set
     */
    public void setNumberOfYAxisTicks(int numberOfYAxisTicks) {
        this.numberOfYAxisTicks = numberOfYAxisTicks;
    }

    /**
     * @return the xMax
     */
    public BigDecimal getxMax() {
        return xMax;
    }

    /**
     * @param xMax the xMax to set
     */
    public void setxMax(BigDecimal xMax) {
        this.xMax = xMax;
    }

    /**
     * @return the xPin
     */
    public BigDecimal getxPin() {
        return xPin;
    }

    /**
     * @param xPin the xPin to set
     */
    public void setxPin(BigDecimal xPin) {
        this.xPin = xPin;
    }

    /**
     * @return the xIncrement
     */
    public BigDecimal getxIncrement() {
        return xIncrement;
    }

    /**
     * @param xIncrement the xIncrement to set
     */
    public void setxIncrement(BigDecimal xIncrement) {
        this.xIncrement = xIncrement;
    }

    /**
     * @return the numberOfXAxisTicks
     */
    public int getNumberOfXAxisTicks() {
        return numberOfXAxisTicks;
    }

    /**
     * @param numberOfXAxisTicks the numberOfXAxisTicks to set
     */
    public void setNumberOfXAxisTicks(int numberOfXAxisTicks) {
        this.numberOfXAxisTicks = numberOfXAxisTicks;
    }

    /**
     * @return the colours
     */
    public Color[] getColours() {
        if (colours == null) {
            initColours();
        }
        return colours;
    }

    /**
     * @param colours the colours to set
     */
    public void setColours(Color[] colours) {
        this.colours = colours;
    }

    public void initColours() {
        colours = new Color[11];
        //        ColorBrewer brewer = ColorBrewer.instance();
//        //String[] paletteNames = brewer.getPaletteNames(0, nClasses);
//        String[] paletteNames = brewer.getPaletteNames();
//        for (int i = 0; i < paletteNames.length; i++) {
//            System.out.println(paletteNames[i]);
//        }

        colours[0] = Color.BLACK;
        colours[1] = Color.BLUE;
        colours[2] = Color.CYAN;
        colours[3] = Color.GREEN;
        colours[4] = Color.MAGENTA;
        colours[5] = Color.ORANGE;
        colours[6] = Color.PINK;
        colours[7] = Color.RED;
        colours[8] = Color.YELLOW;
        colours[9] = Color.DARK_GRAY;
        colours[10] = Color.LIGHT_GRAY;
    }

    /**
     * @return the labels
     */
    public ArrayList<String> getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    /**
     * @return the xAxisLabels
     */
    public TreeMap<BigDecimal, String> getxAxisLabels() {
        return xAxisLabels;
    }

    /**
     * @param xAxisLabels the xAxisLabels to set
     */
    public void setxAxisLabels(TreeMap<BigDecimal, String> xAxisLabels) {
        this.xAxisLabels = xAxisLabels;
    }

}

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
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;

/**
 * An abstract class for creating Age by Gender Population visualisations and
 * possibly rendering them in a lightweight component as suited to headless
 * rendering.
 */
public abstract class Abstract_Generic_AgeGenderPlot extends Generic_Plot {

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
        BigDecimal maxX = new BigDecimal(((BigDecimal) data[2]).toString());
        setMaxX(maxX);
        setMinX(maxX.negate());
        BigDecimal maxY = BigDecimal.valueOf(getStartAgeOfEndYearInterval() + getAgeInterval());
        setMaxY(maxY);
        setMinY(BigDecimal.ZERO);
        setCellHeight();
        setCellWidth();
        setOriginRow();
        setOriginCol();
    }

    @Override
    public void setOriginCol() {
//        originCol = dataStartCol;
        setOriginCol((getDataStartCol() + getDataEndCol()) / 2);
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
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @param scaleTickAndTextSeparation
     * @return an int[] result for setting display parameters where: result[0] =
     * yAxisExtraWidthLeft;
     * @TODO Better handle case when yAxisLabel has a text width wider than
     * image is high
     */
    @Override
    public int[] drawYAxis(
            int interval,
            int textHeight,
            int startAgeOfEndYearInterval,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        int[] result = new int[1];
        int yAxisExtraWidthLeft = 0;
        int originCol = getOriginCol();
        int dataStartRow = getDataStartRow();
        int dataEndRow = getDataEndRow();
        Line2D ab;
        // Draw origin
        if (isDrawOriginLinesOnPlot()) {
            setPaint(Color.LIGHT_GRAY);
            ab = new Line2D.Double(
                    originCol,
                    dataStartRow,
                    originCol,
                    dataEndRow);
            draw(ab);
        }
//        // Draw Y axis scale to the left side
//        setPaint(Color.GRAY);
//        int col = getDataStartCol();
//        ab = new Line2D.Double(
//                col,
//                dataEndRow,
//                col,
//                dataStartRow);
//        draw(ab);
        setPaint(Color.GRAY);
        BigDecimal cellHeight = getCellHeight();
        int barHeight;
        if (cellHeight.compareTo(BigDecimal.ZERO) == 0) {
            barHeight = 1;
        } else {
            barHeight = Generic_BigDecimal.divideRoundIfNecessary(
                    BigDecimal.valueOf(interval),
                    getCellHeight(),
                    0,
                    getRoundingMode()).intValue();
        }
        int barHeightdiv2 = barHeight / 2;

        int increment = interval;
        int dataHeight = getDataHeight();
        while (((startAgeOfEndYearInterval * textHeight) + 4) / increment > dataHeight) {
            increment += interval;
        }
        String text;
        int maxTickTextWidth = 0;
        int col = getDataStartCol();
        int miny_int = getMinY().intValue();
        //for (int i = miny_int; i <= startAgeOfEndYearInterval; i += increment) {
        for (int i = miny_int; i <= getMaxY().intValue(); i += increment) {

            // int row = coordinateToScreenRow(BigDecimal.valueOf(i));
            int row = coordinateToScreenRow(BigDecimal.valueOf(i)) - barHeightdiv2;
            //int row = coordinateToScreenRow(BigDecimal.valueOf(i)) - barHeight;

            setPaint(Color.GRAY);
//            ab = new Line2D.Double(
//                    col,
//                    row,
//                    col - scaleTickLength,
//                    row);
//            draw(ab);
            //text = "" + i + " - " + (i + increment);
            text = "" + i;
            int textWidth = getTextWidth(text);
            drawString(
                    text,
                    col - scaleTickAndTextSeparation - scaleTickLength - textWidth,
                    //row);
                    row + (textHeight / 3));
            maxTickTextWidth = Math.max(maxTickTextWidth, textWidth);
        }
        yAxisExtraWidthLeft += scaleTickLength + scaleTickAndTextSeparation + maxTickTextWidth;
        // Y axis label
        setPaint(Color.BLACK);
        String yAxisLabel = getyAxisLabel();
        int textWidth = getTextWidth(yAxisLabel);
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
        int[] result = new int[3];
        int xAxisExtraWidthLeft = 0;
        int xAxisExtraWidthRight = 0;
        int xAxisExtraHeightBottom = seperationDistanceOfAxisAndData +
                scaleTickLength + scaleTickAndTextSeparation + textHeight;
        int dataStartCol = getDataStartCol();
        //int originRow = getOriginRow();
        int dataEndRow = getDataEndRow();
        int dataEndCol = getDataEndCol();
        int originCol = getOriginCol();
        BigDecimal maxX = getMaxX();
        int significantDigits = getSignificantDigits();
        RoundingMode roundingMode = getRoundingMode();
        Line2D ab;
        setPaint(Color.GRAY);
        int row = dataEndRow + seperationDistanceOfAxisAndData;
        // draw XAxis Line
        ab = new Line2D.Double(
                dataStartCol,
                row,
                dataEndCol,
                row);
        draw(ab);
        // Add ticks and labels
        // origin tick and label
        int textRow = row + scaleTickLength + scaleTickAndTextSeparation + textHeight;
        String text_String = "0";
        int textWidth = getTextWidth(text_String);
        ab = new Line2D.Double(
                originCol,
                row,
                originCol,
                row + scaleTickLength);
        draw(ab);
        drawString(
                text_String,
                originCol - (textWidth / 2),
                textRow);
        // Left end scale tick and label
        int decimalPlacePrecisionForDisplay =
                Generic_BigDecimal.getDecimalPlacePrecision(
                maxX, significantDigits);
        if (maxX != null) {
            text_String = Generic_BigDecimal.roundIfNecessary(
                    maxX,
                    decimalPlacePrecisionForDisplay,
                    roundingMode).toPlainString();
            textWidth = getTextWidth(text_String);
        }
        ab = new Line2D.Double(
                dataStartCol,
                row,
                dataStartCol,
                row + scaleTickLength);
        draw(ab);
        drawString(
                text_String,
                dataStartCol - (textWidth / 2),
                textRow);
        // Add to imageWidth as this label sticks out
        xAxisExtraWidthLeft += (textWidth / 2) + textHeight;
//        // Check to see if plot needs to grow
//        if (xAxisExtraWidthLeft > xAxisExtraWidthLeft) {
//            int diff = xAxisExtraWidthLeft - dataStartCol;
//            imageWidth += diff;
//            dataStartCol += diff;
//            dataEndCol += diff;
//            xAxisExtraWidthLeft = xAxisExtraWidthLeft;
//            setOriginCol();
//        }
        // Right end scale tick and label
        //text_String = maxX.toBigInteger().toString();
        text_String = Generic_BigDecimal.roundIfNecessary(
                getMaxX(),
                decimalPlacePrecisionForDisplay,
                roundingMode).toPlainString();
        textWidth = getTextWidth(text_String);
        ab = new Line2D.Double(
                dataEndCol,
                row,
                dataEndCol,
                row + scaleTickLength);
        draw(ab);
        drawString(
                text_String,
                dataEndCol - (textWidth / 2),
                textRow);
        // Add to imageWidth as this label sticks out
        xAxisExtraWidthRight += (textWidth / 2) + textHeight;
//        if (xAxisExtraWidthRight > xAxisExtraWidthRight) {
//            imageWidth += axesExtraWidthRight - xAxisExtraWidthRight;
//            xAxisExtraWidthRight = axesExtraWidthRight;
//        }
        // Add axis labels
        setPaint(Color.DARK_GRAY);
        textRow += textHeight + partTitleGap;
        text_String = "Male";
        textWidth = getTextWidth(text_String);
        xAxisExtraHeightBottom += textHeight + partTitleGap;
        drawString(
                text_String,
                ((dataStartCol + originCol) / 2) - (textWidth / 2),
                textRow);
        setPaint(Color.DARK_GRAY);
        text_String = "Female";
        textWidth = getTextWidth(text_String);
        drawString(
                text_String,
                ((dataEndCol + originCol) / 2) - (textWidth / 2),
                textRow);
        textRow += textHeight + partTitleGap;
        xAxisExtraHeightBottom += textHeight + partTitleGap;
        setPaint(Color.BLACK);
        text_String = getxAxisLabel();
//        text_String = "Population";
        textWidth = getTextWidth(text_String);
        drawString(
                text_String,
                originCol - (textWidth / 2),
                textRow);
        xAxisExtraHeightBottom += textHeight;
        result[0] = xAxisExtraWidthLeft;
        result[1] = xAxisExtraWidthRight;
        result[2] = xAxisExtraHeightBottom;
        return result;
    }
}

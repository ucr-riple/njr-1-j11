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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An abstract class for creating Age by Gender Population visualisations and
 * displaying them in a JFrame/heavyweight component.
 */
public abstract class Abstract_Generic_JApplet_AgeGenderPlot extends Abstract_Generic_JApplet_Plot {

    protected final void init(
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            int ageInterval,
            int startAgeOfEndYearInterval,
            int decimalPlacePrecisionForCalculations,
            int significantDigits,
            RoundingMode roundingMode) {
        setTitle(title);
        setDataWidth(dataWidth);
        setDataHeight(dataHeight);
        setxAxisLabel(xAxisLabel);
        setyAxisLabel(yAxisLabel);
        setAgeInterval(ageInterval);
        setStartAgeOfEndYearInterval(startAgeOfEndYearInterval);
        setDecimalPlacePrecisionForCalculations(decimalPlacePrecisionForCalculations);
        setSignificantDigits(significantDigits);
        setRoundingMode(roundingMode);
        setAgeInterval(ageInterval);
        setStartAgeOfEndYearInterval(startAgeOfEndYearInterval);
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
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @return 
     * @see <code>Abstract_Generic_AgeGenderPlot.drawYaxis(int,int,int,int,int,int)</code>.
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
        int[] result;
        result = ((Abstract_Generic_AgeGenderPlot) _Generic_Plot).drawYAxis(
                interval,
                textHeight,
                startAgeOfEndYearInterval,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        return result;
    }

    /**
     * @param seperationDistanceOfAxisAndData
     * @param partTitleGap
     * @return 
     * @see <code>Abstract_Generic_AgeGenderPlot.drawXaxis(int,int,int,int)</code>.
     */
    @Override
    public int[] drawXAxis(
            int textHeight,
            int scaleTickLength,
            int scaleTickAndTextSeparation,
            int partTitleGap,
            int seperationDistanceOfAxisAndData) {
        int[] result;
        result = ((Abstract_Generic_AgeGenderPlot) _Generic_Plot).drawXAxis(
                textHeight,
                scaleTickLength,
                scaleTickAndTextSeparation,
                partTitleGap,
                seperationDistanceOfAxisAndData);
        return result;
    }
}

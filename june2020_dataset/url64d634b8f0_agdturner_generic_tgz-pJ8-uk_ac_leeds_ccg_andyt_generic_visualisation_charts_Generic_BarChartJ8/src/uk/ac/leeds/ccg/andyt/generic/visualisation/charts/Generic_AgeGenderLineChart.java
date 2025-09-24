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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.math.statistics.Generic_Statistics;

/**
 * An implementation of
 * <code>Generic_JApplet_AgeGenderLineChart<\code>
 *
 * If you run this class it will attempt to generate an Age by Gender
 * Population Line Chart Visualization of some default data and display it on
 * screen.
 */
public class Generic_AgeGenderLineChart extends Abstract_Generic_AgeGenderPlot {

    public Generic_AgeGenderLineChart() {
    }

    public Generic_AgeGenderLineChart(
            ExecutorService executorService,
            File file,
            String format,
            String title,
            int dataWidth,
            int dataHeight,
            String xAxisLabel,
            String yAxisLabel,
            boolean drawOriginLinesOnPlot,
            int ageInterval,
            int startAgeOfEndYearInterval,
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
                ageInterval,
                startAgeOfEndYearInterval,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                aRoundingMode);
    }

    public static void main(String[] args) {
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
            title = "Age Gender Population Line Chart";
            System.out.println("Use default title: " + title);
            file = new File(
                    new File(System.getProperty("user.dir")),
                    title.replace(" ", "_") + "." + format);
            System.out.println("Use default File: " + file.toString());
        } else {
            title = args[0];
            file = new File(args[1]);
        }
        int dataWidth = 1000;//250;
        int dataHeight = 500;
        String xAxisLabel = "Population";
        String yAxisLabel = "Age";
        boolean drawOriginLinesOnPlot = true;
        int ageInterval = 1;
        int startAgeOfEndYearInterval = 90;//95;
        int decimalPlacePrecisionForCalculations = 10;
        int decimalPlacePrecisionForDisplay = 3;
        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Generic_AgeGenderLineChart plot = new Generic_AgeGenderLineChart(
                executorService,
                file,
                format,
                title,
                dataWidth,
                dataHeight,
                xAxisLabel,
                yAxisLabel,
                drawOriginLinesOnPlot,
                ageInterval,
                startAgeOfEndYearInterval,
                decimalPlacePrecisionForCalculations,
                decimalPlacePrecisionForDisplay,
                aRoundingMode);
        plot.setData(plot.getDefaultData());
        plot.run();
    }

    @Override
    public void drawData() {
        //drawLineChartUsingMeanAndStandardDeviation();
        drawLineChartUsingMinQ1MedianQ3Max();
    }

    public void drawLineChartUsingMeanAndStandardDeviation() {
        Object[] data = getData();
        int ageInterval = getAgeInterval();
        Line2D abLine2D;
        TreeMap<Integer, Object[]> femaleSummaryStatisticsData = (TreeMap<Integer, Object[]>) data[0];
        TreeMap<Integer, Object[]> maleSummaryStatisticsData = (TreeMap<Integer, Object[]>) data[1];
        Iterator<Map.Entry<Integer, Object[]>> ite;
        Map.Entry<Integer, Object[]> entry;
        Integer age;
        Object[] stats;
        BigDecimal[] firstOrderStats;
        BigDecimal[] secondOrderStats;
        BigDecimal mean;
        BigDecimal meanAddStdDev;
        BigDecimal meanSubtractStdDev;
        boolean firstPoint;
        firstPoint = true;
        /*
         * Draw Female Lines
         */
        int lastMeanPointCol = 0;
        int lastMeanAddStdDevPointCol = 0;
        int lastMeanSubtractStdDevPointCol = 0;
        int lastPointRow = 0;
        ite = femaleSummaryStatisticsData.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            stats = entry.getValue();
            firstOrderStats = (BigDecimal[]) stats[0];
            secondOrderStats = (BigDecimal[]) stats[1];
            mean = firstOrderStats[1];
            meanAddStdDev = mean.add(secondOrderStats[5]);
            meanSubtractStdDev = mean.subtract(secondOrderStats[5]);
            int meanPointCol = coordinateToScreenCol(mean);
            int pointRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age - ageInterval / 2));
            int meanAddStdDevPointCol = coordinateToScreenCol(
                    meanAddStdDev);
            int meanSubtractStdDevPointCol = coordinateToScreenCol(
                    meanSubtractStdDev);
            if (firstPoint) {
                lastMeanPointCol = meanPointCol;
                lastMeanAddStdDevPointCol = meanAddStdDevPointCol;
                lastMeanSubtractStdDevPointCol = meanSubtractStdDevPointCol;
                lastPointRow = pointRow;
                firstPoint = false;
            }
            // Draw median add StdDev line
            abLine2D = new Line2D.Double(
                    lastMeanAddStdDevPointCol,
                    lastPointRow,
                    meanAddStdDevPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw median subtract StdDev line
            abLine2D = new Line2D.Double(
                    lastMeanSubtractStdDevPointCol,
                    lastPointRow,
                    meanSubtractStdDevPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw median line
            abLine2D = new Line2D.Double(
                    lastMeanPointCol, lastPointRow, meanPointCol, pointRow);
            setPaint(Color.DARK_GRAY);
            draw(abLine2D);
            // Set parameters
            lastMeanPointCol = meanPointCol;
            lastMeanAddStdDevPointCol = meanAddStdDevPointCol;
            lastMeanSubtractStdDevPointCol = meanSubtractStdDevPointCol;
            lastPointRow = pointRow;
        }
        /*
         * Draw Male Lines
         */
        firstPoint = true;
        ite = maleSummaryStatisticsData.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            stats = entry.getValue();
            firstOrderStats = (BigDecimal[]) stats[0];
            secondOrderStats = (BigDecimal[]) stats[1];
            mean = firstOrderStats[1];
            meanAddStdDev = mean.add(secondOrderStats[5]);
            meanSubtractStdDev = mean.subtract(secondOrderStats[5]);
            int meanPointCol = coordinateToScreenCol(mean.negate());
            int pointRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age - ageInterval / 2));
            int meanAddStdDevPointCol = coordinateToScreenCol(
                    meanAddStdDev.negate());
            int meanSubtractStdDevPointCol = coordinateToScreenCol(
                    meanSubtractStdDev.negate());
            if (firstPoint) {
                lastMeanPointCol = meanPointCol;
                lastMeanAddStdDevPointCol = meanAddStdDevPointCol;
                lastMeanSubtractStdDevPointCol = meanSubtractStdDevPointCol;
                lastPointRow = pointRow;
                firstPoint = false;
            }
            // Draw median add StdDev line
            abLine2D = new Line2D.Double(
                    lastMeanAddStdDevPointCol,
                    lastPointRow,
                    meanAddStdDevPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw median subtract StdDev line
            abLine2D = new Line2D.Double(
                    lastMeanSubtractStdDevPointCol,
                    lastPointRow,
                    meanSubtractStdDevPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw median line
            abLine2D = new Line2D.Double(
                    lastMeanPointCol, lastPointRow, meanPointCol, pointRow);
            setPaint(Color.DARK_GRAY);
            draw(abLine2D);
            // Set parameters
            lastMeanPointCol = meanPointCol;
            lastMeanAddStdDevPointCol = meanAddStdDevPointCol;
            lastMeanSubtractStdDevPointCol = meanSubtractStdDevPointCol;
            lastPointRow = pointRow;
        }
    }

    public void drawLineChartUsingMinQ1MedianQ3Max() {
        Object[] data = getData();
        int ageInterval = getAgeInterval();
        Line2D abLine2D;
        TreeMap<Integer, Object[]> femaleSummaryStatisticsData = (TreeMap<Integer, Object[]>) data[0];
        TreeMap<Integer, Object[]> maleSummaryStatisticsData = (TreeMap<Integer, Object[]>) data[1];
        Iterator<Map.Entry<Integer, Object[]>> ite;
        Map.Entry<Integer, Object[]> entry;
        Integer age;
        Object[] stats;
        BigDecimal[] firstOrderStats;
        BigDecimal[] secondOrderStats;
        BigDecimal min;
        BigDecimal q1;
        BigDecimal median;
        BigDecimal q3;
        BigDecimal max;
        boolean firstPoint;
        firstPoint = true;
        /*
         * Draw Female Lines
         */
        int last_minPointCol = 0;
        int last_q1PointCol = 0;
        int last_medianPointCol = 0;
        int last_q3PointCol = 0;
        int last_maxPointCol = 0;
        int lastPointRow = 0;
        ite = femaleSummaryStatisticsData.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            stats = entry.getValue();
            /* 
             * firstOrderStats[0] = sum;
             * firstOrderStats[1] = mean;
             * firstOrderStats[2] = median;
             * firstOrderStats[3] = q1;
             * firstOrderStats[4] = q3;
             * firstOrderStats[5] = mode;
             * firstOrderStats[6] = min;
             * firstOrderStats[7] = max;
             * firstOrderStats[8] = numberOfDifferentValues;
             * firstOrderStats[9] = numberOfDifferentValuesInMode;
             * firstOrderStats[10] = numberOfSameValuesInAnyPartOfMode;
             */
            firstOrderStats = (BigDecimal[]) stats[0];
            /*
             * secondOrderStats[0] = moment1 = sum of the (differences from the median)
             * secondOrderStats[1] = moment2 = sum of the (differences from the median squared)
             * secondOrderStats[2] = moment3 = sum of the (differences from the median cubed)
             * secondOrderStats[3] = moment4 = sum of the (differences from the median squared squared)
             * secondOrderStats[4] = variance = (sum of the (differences from the median))/n
             * secondOrderStats[5] = standard deviation (hacked)  
             */
            secondOrderStats = (BigDecimal[]) stats[1];
            min = firstOrderStats[6];
                    q1 = firstOrderStats[3];
            median = firstOrderStats[2];
            q3 = firstOrderStats[4];
            max = firstOrderStats[7];
            int minPointCol = coordinateToScreenCol(min);
            int q1PointCol = coordinateToScreenCol(q1);
            int medianPointCol = coordinateToScreenCol(median);
            int q3PointCol = coordinateToScreenCol(q3);
            int maxPointCol = coordinateToScreenCol(max);
            int pointRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age - ageInterval / 2));
            if (firstPoint) {
                last_minPointCol = minPointCol;
                last_q1PointCol = q1PointCol;
                last_medianPointCol = medianPointCol;
                last_q3PointCol = q3PointCol;
                last_maxPointCol = maxPointCol;
                lastPointRow = pointRow;
                firstPoint = false;
            }
            // Draw min line
            abLine2D = new Line2D.Double(
                    last_minPointCol,
                    lastPointRow,
                    minPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw max line
            abLine2D = new Line2D.Double(
                    last_maxPointCol,
                    lastPointRow,
                    maxPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw q1 line
            abLine2D = new Line2D.Double(
                    last_q1PointCol,
                    lastPointRow,
                    q1PointCol,
                    pointRow);
            setPaint(Color.GRAY);
            draw(abLine2D);
            // Draw q3 line
            abLine2D = new Line2D.Double(
                    last_q3PointCol,
                    lastPointRow,
                    q3PointCol,
                    pointRow);
            setPaint(Color.GRAY);
            draw(abLine2D);
            // Draw median line
            abLine2D = new Line2D.Double(
                    last_medianPointCol,
                    lastPointRow,
                    medianPointCol,
                    pointRow);
            setPaint(Color.DARK_GRAY);
            draw(abLine2D);
            // Set parameters
            last_minPointCol = minPointCol;
            last_q1PointCol = q1PointCol;
            last_medianPointCol = medianPointCol;
            last_q3PointCol = q3PointCol;
            last_maxPointCol = maxPointCol;
            lastPointRow = pointRow;
        }
        /*
         * Draw Male Lines
         */
        firstPoint = true;
        ite = maleSummaryStatisticsData.entrySet().iterator();
        while (ite.hasNext()) {
            entry = ite.next();
            age = entry.getKey();
            stats = entry.getValue();
            /* 
             * firstOrderStats[0] = sum;
             * firstOrderStats[1] = mean;
             * firstOrderStats[2] = median;
             * firstOrderStats[3] = q1;
             * firstOrderStats[4] = q3;
             * firstOrderStats[5] = mode;
             * firstOrderStats[6] = min;
             * firstOrderStats[7] = max;
             * firstOrderStats[8] = numberOfDifferentValues;
             * firstOrderStats[9] = numberOfDifferentValuesInMode;
             * firstOrderStats[10] = numberOfSameValuesInAnyPartOfMode;
             */
            firstOrderStats = (BigDecimal[]) stats[0];
            /*
             * secondOrderStats[0] = moment1 = sum of the (differences from the median)
             * secondOrderStats[1] = moment2 = sum of the (differences from the median squared)
             * secondOrderStats[2] = moment3 = sum of the (differences from the median cubed)
             * secondOrderStats[3] = moment4 = sum of the (differences from the median squared squared)
             * secondOrderStats[4] = variance = (sum of the (differences from the median))/n
             * secondOrderStats[5] = standard deviation (hacked)  
             */
            secondOrderStats = (BigDecimal[]) stats[1];
            min = firstOrderStats[6];
                    q1 = firstOrderStats[3];
            median = firstOrderStats[2];
            q3 = firstOrderStats[4];
            max = firstOrderStats[7];
            int minPointCol = coordinateToScreenCol(min.negate());
            int q1PointCol = coordinateToScreenCol(q1.negate());
            int medianPointCol = coordinateToScreenCol(median.negate());
            int q3PointCol = coordinateToScreenCol(q3.negate());
            int maxPointCol = coordinateToScreenCol(max.negate());
            int pointRow = coordinateToScreenRow(
                    BigDecimal.valueOf(age - ageInterval / 2));
            if (firstPoint) {
                last_minPointCol = minPointCol;
                last_q1PointCol = q1PointCol;
                last_medianPointCol = medianPointCol;
                last_q3PointCol = q3PointCol;
                last_maxPointCol = maxPointCol;
                lastPointRow = pointRow;
                firstPoint = false;
            }
            // Draw min line
            abLine2D = new Line2D.Double(
                    last_minPointCol,
                    lastPointRow,
                    minPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw max line
            abLine2D = new Line2D.Double(
                    last_maxPointCol,
                    lastPointRow,
                    maxPointCol,
                    pointRow);
            setPaint(Color.LIGHT_GRAY);
            draw(abLine2D);
            // Draw q1 line
            abLine2D = new Line2D.Double(
                    last_q1PointCol,
                    lastPointRow,
                    q1PointCol,
                    pointRow);
            setPaint(Color.GRAY);
            draw(abLine2D);
            // Draw q3 line
            abLine2D = new Line2D.Double(
                    last_q3PointCol,
                    lastPointRow,
                    q3PointCol,
                    pointRow);
            setPaint(Color.GRAY);
            draw(abLine2D);
            // Draw median line
            abLine2D = new Line2D.Double(
                    last_medianPointCol,
                    lastPointRow,
                    medianPointCol,
                    pointRow);
            setPaint(Color.DARK_GRAY);
            draw(abLine2D);
            // Set parameters
            last_minPointCol = minPointCol;
            last_q1PointCol = q1PointCol;
            last_medianPointCol = medianPointCol;
            last_q3PointCol = q3PointCol;
            last_maxPointCol = maxPointCol;
            lastPointRow = pointRow;
        }
    }

    /**
     * Override this method to use other data
     *
     * @param femalePopAge0
     * @param malePopAge0
     * @return
     */
    private static Object[] getPopulationData(
            int femalePopAge0,
            int malePopAge0) {
        Object[] result = new Object[2];
        TreeMap<Integer, BigDecimal> femaleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        TreeMap<Integer, BigDecimal> maleAgeInYearsPopulationCount_TreeMap = new TreeMap<Integer, BigDecimal>();
        BigDecimal population_BigDecimal;
        BigDecimal change_BigDecimal;
        int age;
        population_BigDecimal = new BigDecimal("" + femalePopAge0);
        change_BigDecimal = new BigDecimal("0.94");
        for (age = 0; age < 5; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.95");
        for (age = 5; age < 10; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.96");
        for (age = 10; age < 15; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.97");
        for (age = 15; age < 20; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.99");
        for (age = 20; age < 60; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.97");
        for (age = 60; age < 80; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.75");
        for (age = 80; age < 100; age++) {
            femaleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        population_BigDecimal = new BigDecimal("" + malePopAge0);
        change_BigDecimal = new BigDecimal("0.93");
        for (age = 0; age < 5; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.94");
        for (age = 5; age < 10; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.95");
        for (age = 10; age < 15; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.96");
        for (age = 15; age < 20; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.98");
        for (age = 20; age < 60; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.7");
        for (age = 60; age < 70; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        change_BigDecimal = new BigDecimal("0.5");
        for (age = 70; age < 100; age++) {
            maleAgeInYearsPopulationCount_TreeMap.put(
                    age, population_BigDecimal);
            population_BigDecimal = population_BigDecimal.multiply(change_BigDecimal);
        }
        Integer maxAge = 99;
        BigDecimal maxCount = new BigDecimal("" + Math.max(femalePopAge0, malePopAge0));
        result[0] = femaleAgeInYearsPopulationCount_TreeMap;
        result[1] = maleAgeInYearsPopulationCount_TreeMap;
        //result[2] = maxAge;
        //result[3] = maxCount;
        return result;
    }

    @Override
    public Object[] getDefaultData() {
        int ageInterval = 1;
        int startAgeOfEndYearInterval = 90;//95;
        int decimalPlacePrecisionForCalculations = 10;
//        int ageInterval = getAgeInterval();
//        int startAgeOfEndYearInterval = getStartAgeOfEndYearInterval();
//        int decimalPlacePrecisionForCalculations = getDecimalPlacePrecisionForCalculations();
        RoundingMode roundingMode = getRoundingMode();
        return getDefaultData(
                ageInterval,
                startAgeOfEndYearInterval,
                decimalPlacePrecisionForCalculations,
                roundingMode);
    }

    /**
     * Returns a sample dataset.
     *
     * @param roundingMode
     * @param startAgeOfEndYearInterval
     * @param decimalPlacePrecisionForCalculations
     * @return The dataset.
     */
    public static Object[] getDefaultData(
            int ageInterval,
            int startAgeOfEndYearInterval,
            int decimalPlacePrecisionForCalculations,
            RoundingMode roundingMode) {
        Object[] result = new Object[5];
        TreeMap<Integer, Object[]> femaleSummaryStatistics = new TreeMap<Integer, Object[]>();
        TreeMap<Integer, Object[]> maleSummaryStatistics = new TreeMap<Integer, Object[]>();
        Object[] data10000 = getPopulationData(10000, 10000);
        TreeMap<Integer, BigDecimal> female10000 = (TreeMap<Integer, BigDecimal>) data10000[0];
        TreeMap<Integer, BigDecimal> male10000 = (TreeMap<Integer, BigDecimal>) data10000[1];
        Object[] data9000 = getPopulationData(9000, 9000);
        TreeMap<Integer, BigDecimal> female9000 = (TreeMap<Integer, BigDecimal>) data9000[0];
        TreeMap<Integer, BigDecimal> male9000 = (TreeMap<Integer, BigDecimal>) data9000[1];
        Object[] data9900 = getPopulationData(9900, 9900);
        TreeMap<Integer, BigDecimal> female9900 = (TreeMap<Integer, BigDecimal>) data9900[0];
        TreeMap<Integer, BigDecimal> male9900 = (TreeMap<Integer, BigDecimal>) data9900[1];
        Object[] data9950 = getPopulationData(9950, 9950);
        TreeMap<Integer, BigDecimal> female9950 = (TreeMap<Integer, BigDecimal>) data9950[0];
        TreeMap<Integer, BigDecimal> male9950 = (TreeMap<Integer, BigDecimal>) data9950[1];
        Object[] data9800 = getPopulationData(9800, 9800);
        TreeMap<Integer, BigDecimal> female9800 = (TreeMap<Integer, BigDecimal>) data9800[0];
        TreeMap<Integer, BigDecimal> male9800 = (TreeMap<Integer, BigDecimal>) data9800[1];
        Iterator<Integer> iterator;
        Integer age = 0;
        BigDecimal pop10000;
        BigDecimal pop9000;
        BigDecimal pop9900;
        BigDecimal pop9950;
        BigDecimal pop9800;
        BigDecimal maxValue;
        maxValue = BigDecimal.ZERO;
        iterator = ((TreeMap<Integer, BigDecimal>) data10000[0]).keySet().iterator();
        ArrayList<BigDecimal> values = null;
        while (iterator.hasNext()) {
            pop10000 = BigDecimal.ZERO;
            pop9000 = BigDecimal.ZERO;
            pop9900 = BigDecimal.ZERO;
            pop9950 = BigDecimal.ZERO;
            pop9800 = BigDecimal.ZERO;
            for (int interval = 0; interval < ageInterval; interval++) {
                if (iterator.hasNext()) {
                    age = iterator.next();
                    if (age >= startAgeOfEndYearInterval) {
                        pop10000 = values.get(0).add(female10000.get(age));
                        pop9000 = values.get(1).add(female9000.get(age));
                        pop9900 = values.get(2).add(female9900.get(age));
                        pop9950 = values.get(3).add(female9950.get(age));
                        pop9800 = values.get(4).add(female9800.get(age));
                    } else {
                        pop10000 = pop10000.add(female10000.get(age));
                        pop9000 = pop9000.add(female9000.get(age));
                        pop9900 = pop9900.add(female9900.get(age));
                        pop9950 = pop9950.add(female9950.get(age));
                        pop9800 = pop9800.add(female9800.get(age));
                    }
                }
            }
            if (age < startAgeOfEndYearInterval) {
                values = new ArrayList<BigDecimal>();
            }
            values.add(pop10000);
            values.add(pop9000);
            values.add(pop9900);
            values.add(pop9950);
            values.add(pop9800);
            Object[] summaryStatistics_1 = Generic_Statistics.getSummaryStatistics_1(
                    values,
                    decimalPlacePrecisionForCalculations,
                    roundingMode);
            System.out.println(
                    "Female age " + age);
            // Set maxValue to be the maximum of the median added to the standard 
            // deviation
            BigDecimal[] firstOrderStatistics = (BigDecimal[]) summaryStatistics_1[0];
            BigDecimal[] secondOrderStatistics = (BigDecimal[]) summaryStatistics_1[1];
            maxValue = maxValue.max(
                    firstOrderStatistics[1].add(secondOrderStatistics[5]));
            if (age < startAgeOfEndYearInterval) {
                femaleSummaryStatistics.put(age, summaryStatistics_1);
            } else {
                femaleSummaryStatistics.put(
                        startAgeOfEndYearInterval + ageInterval,
                        summaryStatistics_1);
            }
        }
//maxX = maxValue;
//maxValue = BigDecimal.ZERO;
        iterator = ((TreeMap<Integer, BigDecimal>) data10000[1]).keySet().iterator();
        while (iterator.hasNext()) {
            pop10000 = BigDecimal.ZERO;
            pop9000 = BigDecimal.ZERO;
            pop9900 = BigDecimal.ZERO;
            pop9950 = BigDecimal.ZERO;
            pop9800 = BigDecimal.ZERO;
            for (int interval = 0; interval < ageInterval; interval++) {
                if (iterator.hasNext()) {
                    age = iterator.next();
                    if (age >= startAgeOfEndYearInterval) {
                        pop10000 = values.get(0).add(male10000.get(age));
                        pop9000 = values.get(1).add(male9000.get(age));
                        pop9900 = values.get(2).add(male9900.get(age));
                        pop9950 = values.get(3).add(male9950.get(age));
                        pop9800 = values.get(4).add(male9800.get(age));
                    } else {
                        pop10000 = pop10000.add(male10000.get(age));
                        pop9000 = pop9000.add(male9000.get(age));
                        pop9900 = pop9900.add(male9900.get(age));
                        pop9950 = pop9950.add(male9950.get(age));
                        pop9800 = pop9800.add(male9800.get(age));
                    }
                }
            }
            if (age < startAgeOfEndYearInterval) {
                values = new ArrayList<BigDecimal>();
            }
            values.add(pop10000);
            values.add(pop9000);
            values.add(pop9900);
            values.add(pop9950);
            values.add(pop9800);
            System.out.println("Male age " + age);
            Object[] summaryStatistics_1 = Generic_Statistics.getSummaryStatistics_1(
                    values,
                    decimalPlacePrecisionForCalculations,
                    roundingMode);
            // Set maxValue to be the maximum of the median added to the standard 
            // deviation
            BigDecimal[] firstOrderStatistics = (BigDecimal[]) summaryStatistics_1[0];
            BigDecimal[] secondOrderStatistics = (BigDecimal[]) summaryStatistics_1[1];
            maxValue = maxValue.max(
                    firstOrderStatistics[1].add(secondOrderStatistics[5]));
            if (age < startAgeOfEndYearInterval) {
                maleSummaryStatistics.put(age, summaryStatistics_1);
            } else {
                maleSummaryStatistics.put(
                        startAgeOfEndYearInterval + ageInterval,
                        summaryStatistics_1);
            }
        }
        result[0] = femaleSummaryStatistics;
        result[1] = maleSummaryStatistics;
        //minX = maxValue.negate();
        result[2] = maxValue;
        result[3] = BigDecimal.valueOf(100);
        result[4] = BigDecimal.ZERO;
        return result;
    }

    /**
     * Returns a sample dataset.
     *
     * @param decimalPlacePrecisionForCalculations
     * @param roundingMode
     * @return The dataset.
     */
    public static Object[] getData(
            int decimalPlacePrecisionForCalculations,
            RoundingMode roundingMode) {
        Object[] result = new Object[2];
        TreeMap<Integer, BigDecimal[]> femaleBoxPlotStatistics = new TreeMap<Integer, BigDecimal[]>();
        TreeMap<Integer, BigDecimal[]> maleBoxPlotStatistics = new TreeMap<Integer, BigDecimal[]>();
        Object[] data10000 = getPopulationData(10000, 10000);
        TreeMap<Integer, BigDecimal> female10000 = (TreeMap<Integer, BigDecimal>) data10000[0];
        TreeMap<Integer, BigDecimal> male10000 = (TreeMap<Integer, BigDecimal>) data10000[1];
        Object[] data9000 = getPopulationData(9000, 9000);
        TreeMap<Integer, BigDecimal> female9000 = (TreeMap<Integer, BigDecimal>) data9000[0];
        TreeMap<Integer, BigDecimal> male9000 = (TreeMap<Integer, BigDecimal>) data9000[1];
        Object[] data9900 = getPopulationData(9900, 9900);
        TreeMap<Integer, BigDecimal> female9900 = (TreeMap<Integer, BigDecimal>) data9900[0];
        TreeMap<Integer, BigDecimal> male9900 = (TreeMap<Integer, BigDecimal>) data9900[1];
        Object[] data9950 = getPopulationData(9950, 9950);
        TreeMap<Integer, BigDecimal> female9950 = (TreeMap<Integer, BigDecimal>) data9950[0];
        TreeMap<Integer, BigDecimal> male9950 = (TreeMap<Integer, BigDecimal>) data9950[1];
        Object[] data9800 = getPopulationData(9800, 9800);
        TreeMap<Integer, BigDecimal> female9800 = (TreeMap<Integer, BigDecimal>) data9800[0];
        TreeMap<Integer, BigDecimal> male9800 = (TreeMap<Integer, BigDecimal>) data9800[1];
        Iterator<Integer> iterator;
        Integer age;
        BigDecimal pop;
        BigDecimal maxValue;
        maxValue = BigDecimal.ZERO;
        iterator = ((TreeMap<Integer, BigDecimal>) data10000[0]).keySet().iterator();
        while (iterator.hasNext()) {
            age = iterator.next();
            pop = female10000.get(age);
            ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = female9000.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = female9900.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = female9950.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = female9800.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            BigDecimal[] boxPlotStatistics =
                    Generic_Statistics.getSummaryStatistics_0(
                    values,
                    decimalPlacePrecisionForCalculations,
                    roundingMode);
            femaleBoxPlotStatistics.put(age, boxPlotStatistics);
        }
        maxValue = BigDecimal.ZERO;
        iterator = ((TreeMap<Integer, BigDecimal>) data10000[0]).keySet().iterator();
        while (iterator.hasNext()) {
            age = iterator.next();
            pop = male10000.get(age);
            ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = male9000.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = male9900.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = male9950.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            pop = male9800.get(age);
            maxValue = Generic_BigDecimal.max(maxValue, pop);
            values.add(pop);
            BigDecimal[] boxPlotStatistics = Generic_Statistics.getSummaryStatistics_0(
                    values,
                    decimalPlacePrecisionForCalculations,
                    roundingMode);
            maleBoxPlotStatistics.put(age, boxPlotStatistics);
        }
        result[0] = femaleBoxPlotStatistics;
        result[1] = maleBoxPlotStatistics;
        return result;
    }
}

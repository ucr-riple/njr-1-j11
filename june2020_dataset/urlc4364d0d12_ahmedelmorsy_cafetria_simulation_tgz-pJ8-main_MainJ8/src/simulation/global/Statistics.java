package simulation.global;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import simulation.logging.ConsoleLogger;
import simulation.logging.EventsLogger;
import simulation.logging.FileLogger;
import simulation.queue.Customer;

public class Statistics {

    private static HashMap<String, ArrayList<QueueInfo>> queuesInfoMap;
    private static ArrayList<QueueInfo> systemInfoMap;
    private static HashMap<String, ArrayList<Delay>> delaysInfoMap;
    private static HashMap<Customer, Integer> custDelay;
    private static HashMap<Customer, Delay> tempDelays;

    // private static int timeAvgNumInQueue = -1;
    private static HashMap<String, Double> timeAvgNumInQueue;
    private static HashMap<String, Integer> maxNumInQueue;
    private static HashMap<String, Double> avgDelayInQueue;
    private static HashMap<String, Integer> maxDelayInQueue;

    public static EventsLogger console = new ConsoleLogger();
    public static EventsLogger trace = new FileLogger(new File("trial0"));
    

    private static XYSeries queueLengthDataset = new XYSeries("First");

    static {
        reset();
    }
    
    public static void reset() {
        queueLengthDataset.add(0, 0);

        systemInfoMap = new ArrayList<Statistics.QueueInfo>();
        QueueInfo tempInfo = new QueueInfo();
        tempInfo.from = 0;
        tempInfo.count = 0;
        systemInfoMap.add(tempInfo);
        queuesInfoMap = new HashMap<String, ArrayList<QueueInfo>>();
        ArrayList<QueueInfo> temp = new ArrayList<Statistics.QueueInfo>();
        tempInfo = new QueueInfo();
        tempInfo.from = 0;
        tempInfo.count = 0;
        temp.add(tempInfo);
        queuesInfoMap.put(Const.HOTFOOD_SERVER, temp);
        temp = new ArrayList<Statistics.QueueInfo>();
        tempInfo = new QueueInfo();
        tempInfo.from = 0;
        tempInfo.count = 0;
        temp.add(tempInfo);
        queuesInfoMap.put(Const.SANDWICH_SERVER, temp);
        // queuesInfoMap.put(Const.DRINKS_SERVER, new
        // ArrayList<Statistics.QueueInfo>());
        temp = new ArrayList<Statistics.QueueInfo>();
        tempInfo = new QueueInfo();
        tempInfo.from = 0;
        tempInfo.count = 0;
        temp.add(tempInfo);
        queuesInfoMap.put(Const.CASHIER_SERVER, temp);

        delaysInfoMap = new HashMap<String, ArrayList<Delay>>();
        ArrayList<Delay> list = new ArrayList<Statistics.Delay>();
        delaysInfoMap.put(Const.HOTFOOD_SERVER, list);
        list = new ArrayList<Statistics.Delay>();
        delaysInfoMap.put(Const.SANDWICH_SERVER, list);
        list = new ArrayList<Statistics.Delay>();
        delaysInfoMap.put(Const.DRINKS_SERVER, list);
        list = new ArrayList<Statistics.Delay>();
        delaysInfoMap.put(Const.CASHIER_SERVER, list);

        tempDelays = new HashMap<Customer, Statistics.Delay>();

        timeAvgNumInQueue = new HashMap<String, Double>();
        maxNumInQueue = new HashMap<String, Integer>();
        avgDelayInQueue = new HashMap<String, Double>();
        maxDelayInQueue = new HashMap<String, Integer>();
        custDelay = new HashMap<Customer, Integer>();
    }

    public static void drawQueueLen() {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart Demo 6", // chart title
                "X", // x axis label
                "Y", // y axis label
                new XYSeriesCollection(queueLengthDataset), // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
                );
        chart.setBackgroundPaint(Color.white);

        // final StandardLegend legend = (StandardLegend) chart.getLegend();
        // legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        // plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        ApplicationFrame demo = new ApplicationFrame("chart");
        demo.setContentPane(chartPanel);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    public static void UpdateQueueLength(int len, String queueType) {
        ArrayList<QueueInfo> temp = queuesInfoMap.get(queueType);
        QueueInfo info = temp.get(temp.size() - 1);
        if (queueType == Const.HOTFOOD_SERVER) {
            queueLengthDataset.add(info.from, info.count);
            queueLengthDataset.add(info.from, len);
        }
        if (info.from == SimulationClk.clock) {
            info.count = len;
        } else {
            info.to = SimulationClk.clock;
            info = new QueueInfo();
            info.from = SimulationClk.clock;
            info.count = len;
            temp.add(info);
        }
    }

    public static void CustomersEnteredSystem(int num) {
        QueueInfo temp = systemInfoMap.get(systemInfoMap.size() - 1);
        temp.to = SimulationClk.clock;
        QueueInfo info = new QueueInfo();
        info.from = SimulationClk.clock;
        info.count = temp.count + num;
        systemInfoMap.add(info);
    }
    
    public static void CustomersQuitSystem(int num) {
        QueueInfo temp = systemInfoMap.get(systemInfoMap.size() - 1);
        temp.to = SimulationClk.clock;
        QueueInfo info = new QueueInfo();
        info.from = SimulationClk.clock;
        info.count = temp.count - num;
        systemInfoMap.add(info);
    }
    
    public static double getTimeAvgNumInQueue(String qType) {
        if (timeAvgNumInQueue.containsKey(qType))
            return timeAvgNumInQueue.get(qType);
        ArrayList<QueueInfo> list = queuesInfoMap.get(qType);
        int max = 0;
        int timeAvg = 0;
        int maxTime = 0;
        Iterator<QueueInfo> it = list.iterator();
        while (it.hasNext()) {
            QueueInfo info = it.next();
            if (info.to > info.from) {
                if (info.count > max)
                    max = info.count;
                if (info.to > maxTime)
                    maxTime = info.to;
                timeAvg += info.count * (info.to - info.from);
            }
        }
        maxNumInQueue.put(qType, max);
        if (maxTime > 0)
            timeAvgNumInQueue.put(qType, (double) timeAvg / maxTime);
        else
            timeAvgNumInQueue.put(qType, 0.0);
        return timeAvgNumInQueue.get(qType);
    }

    public static int getMaxNumInQueue(String qType) {
        if (maxNumInQueue.containsKey(qType))
            return maxNumInQueue.get(qType);
        ArrayList<QueueInfo> list = queuesInfoMap.get(qType);
        int max = 0;
        int timeAvg = 0;
        int maxTime = 0;
        Iterator<QueueInfo> it = list.iterator();
        while (it.hasNext()) {
            QueueInfo info = it.next();
            if (info.to > info.from) {
                if (info.count > max)
                    max = info.count;
                if (info.to > maxTime)
                    maxTime = info.to;
                timeAvg += info.count * (info.to - info.from);
            }
        }
        maxNumInQueue.put(qType, max);
        if (maxTime > 0)
            timeAvgNumInQueue.put(qType, (double) timeAvg / maxTime);
        else
            timeAvgNumInQueue.put(qType, 0.0);
        return maxNumInQueue.get(qType);
    }

    public static double getTimeAvgNumInSystem() {
        int timeAvg = 0;
        int maxTime = 0;
        Iterator<QueueInfo> it = systemInfoMap.iterator();
        while (it.hasNext()) {
            QueueInfo info = it.next();
            if (info.to > maxTime)
                maxTime = info.to;
            timeAvg += info.count * (info.to - info.from);
        }
        if (maxTime > 0)
            return (double) timeAvg / maxTime;
        else
            return 0.0;
    }

    public static int getMaxNumInSystem() {
        int max = 0;
        Iterator<QueueInfo> it = systemInfoMap.iterator();
        while (it.hasNext()) {
            QueueInfo info = it.next();
            if (info.count > max)
                max = info.count;
        }
        return max;
    }

    // /////////////////// Delays ///////////////////////////////////
    public static void CustomerEnteredQueue(Customer c, String qType) {
        Delay d = new Delay();
        d.from = SimulationClk.clock;
        d.c = c;
        tempDelays.put(c, d);
    }

    public static void CustomerQuitQueue(Customer c, String qType) {
        Delay d = tempDelays.get(c);
        d.to = SimulationClk.clock;
        ArrayList<Delay> list = delaysInfoMap.get(qType);
        list.add(d);
        int delay = d.to - d.from;
        if (custDelay.containsKey(c))
            custDelay.put(c, custDelay.get(c) + delay);
        else
            custDelay.put(c, delay);
    }

    public static double getAvgDelayInQueue(String qType) {
        if (avgDelayInQueue.containsKey(qType))
            return avgDelayInQueue.get(qType);
        ArrayList<Delay> list = delaysInfoMap.get(qType);
        int max = 0;
        int average = 0;
        Iterator<Delay> it = list.iterator();
        while (it.hasNext()) {
            Delay info = it.next();
            int delay = info.to - info.from;
            if (delay > max)
                max = delay;
            average += delay;
        }
        maxDelayInQueue.put(qType, max);
        if (list.size() > 0)
            avgDelayInQueue.put(qType, (double) average / list.size());
        else
            avgDelayInQueue.put(qType, 0.0);
        return avgDelayInQueue.get(qType);
    }

    public static int getMaxDelayInQueue(String qType) {
        if (maxDelayInQueue.containsKey(qType))
            return maxDelayInQueue.get(qType);
        ArrayList<Delay> list = delaysInfoMap.get(qType);
        int max = 0;
        int average = 0;
        Iterator<Delay> it = list.iterator();
        while (it.hasNext()) {
            Delay info = it.next();
            int delay = info.to - info.from;
            if (delay > max)
                max = delay;
            average += delay;
        }
        maxDelayInQueue.put(qType, max);
        if (list.size() > 0)
            avgDelayInQueue.put(qType, (double) average / list.size());
        else
            avgDelayInQueue.put(qType, 0.0);
        return maxNumInQueue.get(qType);
    }

    public static double getOverallAvgDelay() {
        Iterator<Customer> it = custDelay.keySet().iterator();
        int sum = 0;
        int count = 0;
        while (it.hasNext()) {
            Customer next = it.next();
            sum += custDelay.get(next);
            count++;
        }
        return (double) sum / count;
    }

    public static double getAvgDelayForCust(int custType) {
        Iterator<Customer> it = custDelay.keySet().iterator();
        int sum = 0;
        int count = 0;
        while (it.hasNext()) {
            Customer next = it.next();
            if (next.getType() == custType) {
                sum += custDelay.get(next);
                count++;
            }
        }
        return (double) sum / count;
    }

    public static double getMaxDelayForCust(int custType) {
        Iterator<Customer> it = custDelay.keySet().iterator();
        int max = 0;
        while (it.hasNext()) {
            Customer next = it.next();
            if (next.getType() == custType) {
                int delay = custDelay.get(next);
                if (delay > max)
                    max = delay;
            }
        }
        return max;
    }

    private static class Delay {
        public Customer c;
        public int from;
        public int to;
    }

    private static class QueueInfo {
        public int from;
        public int to;
        public int count;
    }
}

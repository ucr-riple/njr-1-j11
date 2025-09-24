package com.alibaba;

import org.math.plot.*;

import javax.swing.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: sweeliao@gmail.com
 * Date: 14-3-21
 * Time: 上午10:50
 */
public class DataPlot {
    public static void main(String[] args) {
        DataRead reader = new DataRead();
        reader.read();
        if (!reader.read()) {
            System.out.println("read file error");
            return;
        }
        Map<String, Map<String, Map<Integer, HashSet<Integer>>>> table = reader.getData();
        reader.close();

        double[] x = new double[table.size()];
        double[] y = new double[table.size()];
        double[] z = new double[table.size()];
        Plot2DPanel plot = new Plot2DPanel();

        int num = 0;
        for (String user: table.keySet()) {
            long click = 0, buy = 0, save = 0;
            for (String brand: table.get(user).keySet()) {
                Set<Integer> ii = table.get(user).get(brand).get(1);
                if (ii.contains(0)) click++;
                else if (ii.contains(1)) buy++;
                else if (ii.contains(2)) save++;
            }
            x[num] = click;
            y[num] = buy;
            z[num++] = save;
        }
        //plot.addScatterPlot("plot about relationship between users' behavior of click and buy", x, y);
        plot.addScatterPlot("plot about relationship between user's behavior of save and buy", z, y);
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}

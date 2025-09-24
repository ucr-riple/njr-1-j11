package main;

import common.Params;
import lunar.IProblem;
import strategy.IStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

public class StatsVisualiser extends JComponent {

    final int MARGIN = 20;
    final int SCALE_WIDTH = 30;
    final int V_LINES = 6;

    int runIndex;
    List<Double> results;
    double bestResult = 0;

    public StatsVisualiser() {
        runIndex = 0;
        results = new ArrayList<Double>();
    }

    public void addResult(double result) {
        results.add(result);
    }

    public void clearResults() {
        results = new ArrayList<Double>();
    }

    public void setRunIndex(int runIndex) {
        this.runIndex = runIndex;
    }

    private void drawResults(Graphics2D g) {
        int width = (int)(getSize().getWidth());
        int height = (int)(getSize().getHeight());

        int lineSpacing = (height - (MARGIN*2))/V_LINES;
        int barWidth = (width - (MARGIN*2 + SCALE_WIDTH)) / results.size();

        // get range
        double minRange = Double.MAX_VALUE;
        double maxRange = -Double.MAX_VALUE;
        for(double value : results) {
            if(value < minRange) minRange = value;
            if(value > maxRange) maxRange = value;
        }

        // get scale
        double scale = maxRange - minRange;

        // draw lines
        g.setColor(Color.DARK_GRAY);
        for(int i=0; i<V_LINES; i++) {
            g.drawLine(MARGIN, MARGIN + (i*lineSpacing), width - MARGIN, MARGIN + (i*lineSpacing));
        }

        // draw bars
        for(int i=0; i<results.size(); i++) {
            if(i%2 == 0) {
                g.setColor(Color.DARK_GRAY);
            } else {
                g.setColor(Color.GRAY);
            }
            int barHeight = (int)(((results.get(i) - minRange) / scale) * (height - (MARGIN*2)));

            g.fillRect(MARGIN + SCALE_WIDTH + (i * barWidth),
                    height - (MARGIN + barHeight),
                    barWidth,
                    barHeight);
        }

        // print scale
        g.setColor(Color.white);
        Font font = new Font("sans serif", Font.BOLD, 10);
        g.setFont(font);
        for(int i=0; i<V_LINES; i++) {
            double lineValue = minRange + i*(scale/V_LINES);
            g.drawString(String.format("%.2f", lineValue), 5, ((V_LINES + 1) - i)*lineSpacing);
        }

        // print run
        g.drawString(String.format("Run: %d", runIndex), (int)(getSize().getWidth()) - 100, 10);
        // print best fitness
        g.drawString(String.format("Best fitness: %.2f", minRange), (int)(getSize().getWidth()) - 100, 30);
        bestResult = minRange;
    }

    public void paintComponent(Graphics g) {
        int width = (int)(getSize().getWidth());
        int height = (int)(getSize().getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        Graphics2D g2d = (Graphics2D)g;

        if(results.size() > 0) drawResults(g2d);
    }

    public Dimension getPreferredSize() {
        return new Dimension(Params.screenWidth, 100);
    }
}

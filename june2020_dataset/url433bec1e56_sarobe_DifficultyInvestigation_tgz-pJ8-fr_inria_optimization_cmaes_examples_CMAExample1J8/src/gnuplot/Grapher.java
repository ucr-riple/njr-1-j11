package gnuplot;

import common.Params;
import common.utilities.JEasyFrame;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Samuel Roberts, 2012
 */
public class Grapher {

    public static void main(String[] args) throws Exception {

        final int startingIndex = 56;
        final int graphs = 1;

        for(int i=startingIndex; i<startingIndex + graphs; i++) {
//            transformData(i, 0);
            drawGraph(i);
        }
        new JEasyFrame(new JPanel(), "Keep Graph Open!");
    }

    public static void drawGraph(int runIndex) {
        try {
            GnuPlot gp = new GnuPlot();
            gp.setAxes("Generation", "Std Devs of Improvement");
            gp.plot(Params.dataDirectory + "run-" + runIndex + "/gnuplot-best.txt", "Best of Generation");
//            gp.replot(Params.dataDirectory + "run-" + runIndex + "/gnuplot-mean.txt", "Mean of Generation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeGenData(int runIndex, int genIndex, double bestResult, double meanResult, double[] bestSolution) throws IOException {

        // Use to write gnuplot compatible data

        // make directory to write to
        String directoryName = Params.dataDirectory + "run-" + runIndex;
        new File(directoryName).mkdir();

        // write/append data to graphing files
        boolean append = true;
        if(genIndex == 1) {
            append = false;
        }
        // write the best
        PrintWriter pw = new PrintWriter(new FileWriter(directoryName + "/gnuplot-best.txt", append));
        pw.format("%f \t %f \n", (double)genIndex, bestResult);
        pw.close();

        // write the mean
        pw = new PrintWriter(new FileWriter(directoryName + "/gnuplot-mean.txt", append));
        pw.format("%f \t %f \n", (double)genIndex, meanResult);
        pw.close();

        // write the best ship
        pw = new PrintWriter(new FileWriter(directoryName + "/best-ship.txt", append));
        pw.print(genIndex + "\t" + bestResult + "\t" + Arrays.toString(bestSolution) + "\n");
        pw.close();
    }

    public static void transformData(int runIndex, double constant) throws Exception {
        String directoryName = Params.dataDirectory + "run-" + runIndex;

        // rewrite the best
        BufferedReader reader = new BufferedReader(new FileReader(directoryName + "/gnuplot-best.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter(directoryName + "/gnuplot-bestTEMP.txt", false));
        String line = "";
        while((line = reader.readLine()) != null) {
            String[] tokens = line.split(" \t ");
            double gen = Double.parseDouble(tokens[0]);
            double bestResult = constant - Double.parseDouble(tokens[1]);;
            pw.format("%f \t %f \n", gen, bestResult);
        }
        reader.close();
        pw.close();
        File fileA = new File(directoryName + "/gnuplot-best.txt");
        File fileB = new File(directoryName + "/gnuplot-bestTEMP.txt");
        fileA.delete();
        fileB.renameTo(fileA);
        fileB.delete();


        // rewrite the mean
        reader = new BufferedReader(new FileReader(directoryName + "/gnuplot-mean.txt"));
        pw = new PrintWriter(new FileWriter(directoryName + "/gnuplot-meanTEMP.txt", false));
        line = "";
        while((line = reader.readLine()) != null) {
            String[] tokens = line.split(" \t ");
            double gen = Double.parseDouble(tokens[0]);
            double bestResult = constant - Double.parseDouble(tokens[1]);;
            pw.format("%f \t %f \n", gen, bestResult);
        }
        reader.close();
        pw.close();
        fileA = new File(directoryName + "/gnuplot-mean.txt");
        fileB = new File(directoryName + "/gnuplot-meanTEMP.txt");
        fileA.delete();
        fileB.renameTo(fileA);
        fileB.delete();
    }
}
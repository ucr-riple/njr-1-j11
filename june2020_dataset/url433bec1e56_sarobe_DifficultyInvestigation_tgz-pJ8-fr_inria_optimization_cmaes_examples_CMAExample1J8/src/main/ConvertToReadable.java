package main;

import common.Params;
import common.utilities.JEasyFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class ConvertToReadable {
    public static void main(String[] args) throws Exception {

        final int startingIndex = 52;
        final int runs = 1;

        for(int i=startingIndex; i<startingIndex + runs; i++) {
            String directoryName = Params.dataDirectory + "run-" + i;

            // get best-ship.txt
            BufferedReader reader = new BufferedReader(new FileReader(directoryName + "/best-ship.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter(directoryName + "/readable-results.txt", false));

            String label = "\t\t\t\t\t[stdv]\t[thrst]\t[grav]\t[vlos]\t[seed]\t[pads]\t[size]\t[surv]\t[fuel]\t[proxW]\t[velW]\t[fuelW]\t[angW]";
            pw.println(label);
            String line = "";
            while((line = reader.readLine()) != null) {
                Scanner readLine = new Scanner(line);
                int gen = readLine.nextInt();
                BigDecimal score = readLine.nextBigDecimal();
                String array = readLine.nextLine();
                // clear out [ ] ,
                array = array.trim();
                array = array.substring(2, array.length() - 1);
                String[] tokens = array.split(", ");

                double[] x = new double[13];
                // convert the values of the array to human readable!
                for(int j = 0; j <13; j++) {
                    try {
                        x[j] = Double.valueOf(tokens[j]);
                    } catch(Exception e) {
                        System.out.println("error in run " + i);
                        System.out.println("invalid input: " + tokens[j]);
                    }
                }

                x[0] = Math.abs(x[0]) * 10;
                x[1] = Math.abs(x[1]) * 10;
                x[2] = Math.abs(x[2]) * 10;
                x[3] = Math.min(Math.abs(x[3]), 1.0);
                x[4] = (int)Math.abs(x[4]);
                x[5] = (int)Math.abs(x[5]);
                x[6] = (int)Math.abs(x[6]);
                x[7] = Math.abs(x[7]) * 10;
                x[8] = Math.abs(x[8]) * 10000;
                x[9] = Math.abs(x[9]);
                x[10] = Math.abs(x[10]);
                x[11] = Math.abs(x[11]);
                x[12] = Math.abs(x[12]);

                String arrayString = String.format("[%.3f,\t%.5f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f,\t%.2f]", x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11], x[12]);

                pw.format("%d \t\t %.3f \t%s \n", gen, score.negate(), arrayString);
            }
            pw.println(label);
            reader.close();
            pw.close();
        }
    }
}

package main;

import common.Params;
import common.utilities.JEasyFrame;
import gnuplot.Grapher;
import lunar.IProblem;
import lunar.ProblemProblem;
import strategy.CMAHandler;
import strategy.IStrategy;
import strategy.TruncatedHOOHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutoRunner {

//    static Set<Runner> activeRuns;

    public static void main(String[] args) throws Exception {
//        activeRuns = new HashSet<Runner>();

//        for(int i=0; i<1; ++i) {
            ParamEnums e = ParamEnums.ALL_PARAMS;
            System.out.println("STARTING NEW RUN, ADJUSTING " + e + " PARAMETER");
            Runner r = startNewRun((1), e);

//            activeRuns.add(r);
//
//            if(activeRuns.size() >= 4) {
//                boolean waitingForVacancy = true;
//                while(waitingForVacancy) {
//                    Set<Runner> livingRuns = new HashSet<Runner>();
//                    for(Runner a : activeRuns) {
//                        if(!a.hasEnded()) {
//                            livingRuns.add(a);
//                        }
//                    }
//                    activeRuns = livingRuns;
//                    waitingForVacancy = activeRuns.size() >= 4;
//                    Thread.sleep(10000);
//                }
//            }
//        }
    }

    private static Runner startNewRun(int runIndex, ParamEnums param) throws IOException {
        IProblem problem = new ProblemProblem(runIndex, param);
        Params.numEvals = 5000;
        IStrategy handler = new CMAHandler(problem, runIndex, 0, 1, 10);//Params.valueRange);

        // log what we're doing
        String directoryName = Params.dataDirectory + "run-" + runIndex;
        new File(directoryName).mkdir();
        PrintWriter pw = new PrintWriter(new FileWriter(directoryName + "/param.txt"));
        pw.println(param);
        pw.close();

        // go!
        Runner r = new Runner(runIndex, problem, handler);
        Thread t = new Thread(r);
        t.start();
        return r;
    }

    private static int getNextRunIndex() {
        File dataDirectory = new File(Params.dataDirectory);
        File directories[] = dataDirectory.listFiles();
        int highestRunNum = 0;
        for(File dir : directories) {
            String dirNumPart = dir.getName().substring(4);
            int dirNum = Integer.parseInt(dirNumPart);
            if(dirNum > highestRunNum) highestRunNum = dirNum;
        }
        int startingIndex = highestRunNum + 1;
        return startingIndex;
    }
}

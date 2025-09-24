package main;

import common.Params;
import common.utilities.JEasyFrame;
import lunar.IProblem;
import lunar.LunarProblem;
import lunar.ProblemProblem;
import strategy.CMAHandler;
import strategy.IStrategy;
import strategy.TruncatedHOOHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainView extends JPanel {

    public ControlPanel cp;
    public StatsVisualiser stv;
    public SpaceshipVisualiser spv;
    Runner r; // handles simulation for fitness calculation in a separate thread!
    Demonstrator d; // handles simulation of demonstration in a separate thread!

    IProblem problem;
    IStrategy handler;
    int runIndex;

    public boolean running = false;
    public boolean demoing = false;
//    public String currentStrategy = "CMA-ES";

    public static MainView mainView; // null if no view, otherwise exists

    public double[] bestSolution;

    public static void main(String[] args) {
        JEasyFrame frame = new JEasyFrame(new MainView(), "Problem Difficulty Investigation");
    }

    public MainView() {
        cp = new ControlPanel(this);
        stv = new StatsVisualiser();
        spv = new SpaceshipVisualiser();

        setLayout(new BorderLayout());
        add(cp, BorderLayout.WEST);
        add(stv, BorderLayout.NORTH);
        add(spv, BorderLayout.EAST);

        bestSolution = new double[3 * Params.maxActions];

        mainView = this;
    }

    public void startRun() {
        running = true;
        int runIndex = getNextRunIndex();
        stv.setRunIndex(runIndex);
        problem = new LunarProblem();//new ProblemProblem(runIndex, ParamEnums.ALL_PARAMS);
//        if(currentStrategy.equals("CMA-ES")) {
            handler = new CMAHandler(problem, runIndex, 0, 5, 10);//Params.valueRange);
//        } else if(currentStrategy.equals("T-HOO")) {
//            handler = new TruncatedHOOHandler(problem, runIndex);
//        } else if(currentStrategy.equals("(T-HOO)")) {
//            handler = new TruncatedHOOHandler(problem, runIndex, bestSolution, Params.bracketedRange);
//        }
        r = new Runner(runIndex, problem, handler, stv);
        Thread t = new Thread(r);
        t.start();
        cp.blockChanges();
        stv.clearResults();
    }

    public void stopRun() {
        running = false;
        if(demoing) stopDemo();
        r.end();
        cp.allowChanges();
        bestSolution = handler.getBestRecentSolution();
        System.out.println("Std devs of Improvement: " + ProblemProblem.evaluateWithCurrentParams(stv.bestResult));
    }

    public void startDemo() {
        if(demoing) stopDemo();
        demoing = true;
        d = new Demonstrator(problem, handler, spv);
        Thread t = new Thread(d);
        t.start();
    }

    public void stopDemo() {
        demoing = false;
        d.stopDemo();
        spv.stopDemo();
    }

//    public void selectedStrategy(String newStrategy) {
//        currentStrategy = newStrategy;
//    }

    public Dimension getPreferredSize() {
        return new Dimension(Params.screenWidth, Params.screenHeight);
    }

    private int getNextRunIndex() {
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



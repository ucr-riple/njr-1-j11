package ar.fiuba.tecnicas.framework.JTest;


import java.text.NumberFormat;

public class Timer {
    private long startTime;
    private long runTime;

    public Timer(long startTime) {
        this.startTime = startTime;
        this.runTime=0;
    }


    public String getTime() {
        long endTime = System.nanoTime();
        runTime = endTime - startTime;
        return NumberFormat.getInstance().format((double) runTime/1000000)+" [miliseg]";
    }
}

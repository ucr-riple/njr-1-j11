package simulation.random;

import java.util.Random;

public class ExponentialRandom {

    private Random uGen;
    private int mean;
    
    public ExponentialRandom(int mean){
        this.uGen = new Random();
        this.mean = mean;
    }
    
    public int nextInt() {
        double u = uGen.nextDouble();
        return (int) (-1 * mean * Math.log(u));
    }
}

package common.math;

import java.util.Collection;
import java.util.List;

/**
 * Created by Samuel Roberts
 * on 29/11/11
 */
public class StatsUtil {

    public static double getMean(Collection<Double> results) {
        double total = 0;
        for(Double d : results) {
            total += d;
        }
        return total / results.size();
    }

    public static double getBest(Collection<Double> results) {
        double best = -Double.MAX_VALUE;
        for(Double d : results) {
            if(d > best) best = d;
        }
        return best;
    }

    public static double getStdDev(Collection<Double> results) {
        double mean = getMean(results);
        double stdDev = 0;

        for(Double d : results) {
            stdDev += ((d - mean) * (d - mean));
        }

        stdDev /= results.size();
        stdDev = Math.sqrt(stdDev);
        return stdDev;
    }
}

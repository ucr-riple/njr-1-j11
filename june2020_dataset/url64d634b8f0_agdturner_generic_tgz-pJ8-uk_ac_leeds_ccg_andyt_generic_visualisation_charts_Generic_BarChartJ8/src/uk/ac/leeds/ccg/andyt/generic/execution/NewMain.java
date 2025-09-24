/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.execution;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author geoagdt
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TreeMap<Integer, Integer> stats = new TreeMap<Integer, Integer>();
        Random random = new Random(); // Set the seed if you like
        int n = 10000000; // Set a different n if you like
        for (long l = 0; l < 1000000; l++) {
            Integer value = random.nextInt(n);
            value /= 1000000;
            // Add to stats
            Integer count = stats.get(value);
            if (count != null) {
                count += 1;
                stats.put(value, count);
            } else {
                stats.put(value,0);
            }
        }
        
        Iterator<Entry<Integer,Integer>> ite = stats.entrySet().iterator();
        System.out.println("Value, Frequency");
        while (ite.hasNext()) {
            Entry<Integer,Integer> entry = ite.next();
            System.out.println(" " + entry.getKey() + ", " +  entry.getValue());
        }
       
    }
}
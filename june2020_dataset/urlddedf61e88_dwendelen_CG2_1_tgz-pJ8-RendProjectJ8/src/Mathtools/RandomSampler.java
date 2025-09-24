
package Mathtools;

public class RandomSampler extends Sampler {
  
  /** Creates new RandomSampler */
  public RandomSampler() {
  }
  
  public double[] generateNextRandomPair() {
    double[] result = new double[2];
    result[0] = Math.random();
    result[1] = Math.random();
    return(result);
  }
  
  public void reset() {
  }
  
  static public void main (String[] args) {
    Sampler s = new RandomSampler();
    double[] a;
    
    a = s.generateNextRandomPair();
    System.out.println(a[0] + " -- " + a[1]);
  }
}
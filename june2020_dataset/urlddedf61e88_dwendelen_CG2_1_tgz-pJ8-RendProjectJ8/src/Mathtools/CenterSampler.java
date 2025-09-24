
package Mathtools;


public class CenterSampler extends Sampler {

  /** Creates new CenterSampler */
  public CenterSampler() {
  }
  
  public double[] generateNextRandomPair() {
    double[] result = new double[2];
    result[0] = 0.5;
    result[1] = 0.5;
    return(result);
  }
  
  public void reset() {
  }
  
  static public void main (String[] args) {
    Sampler s = new CenterSampler();
    double[] a;
    
    a = s.generateNextRandomPair();
    System.out.println(a[0] + " -- " + a[1]);
  }
  
}
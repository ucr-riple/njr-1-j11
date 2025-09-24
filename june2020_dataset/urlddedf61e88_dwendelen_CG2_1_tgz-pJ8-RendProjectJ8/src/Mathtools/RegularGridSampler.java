/*
 * RegularGridSampler.java
 *
 * Created on October 4, 2000, 4:37 PM
 */

package Mathtools;

/**
 *
 * @author  phil
 * @version
 */
public class RegularGridSampler extends Sampler {

  private int gridSize;
  private int currentSample = 0;

  /** Creates new RegularGridSampler */
  public RegularGridSampler (int gridSize) {
    this.gridSize = gridSize;
    this.currentSample = 0;
  }

  public double[] generateNextRandomPair() {
    int x,y;
    double[] result = new double[2];

    if (currentSample == gridSize*gridSize) this.reset();

    x = (currentSample%this.gridSize);
    y = (currentSample/this.gridSize); //integer division
    result[0] = (0.5 + (double)x)/(double)this.gridSize;
    result[1] = (0.5 + (double)y)/(double)this.gridSize;

    this.currentSample ++;
    return(result);
  }

  public void reset() {
    this.currentSample = 0;
  }

  static public void main (String[] args) {
    Sampler s = new RegularGridSampler(3);
    double[] a;
    int i;

    for (i=0;i<10;i++) {
      a = s.generateNextRandomPair();
      System.out.println(a[0] + " -- " + a[1]);
    }
  }
}
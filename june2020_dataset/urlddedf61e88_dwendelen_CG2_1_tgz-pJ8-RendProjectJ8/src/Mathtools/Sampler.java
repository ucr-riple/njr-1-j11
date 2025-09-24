/*
 * Sampler.java
 *
 * Created on October 4, 2000, 4:18 PM
 */

package Mathtools;

public abstract class Sampler {

  final public static int CENTER = 1;
  final public static int RANDOM = 2;
  final public static int REGULAR_GRID = 3;

  public abstract double[] generateNextRandomPair();

  public abstract void reset();

  public static Sampler constructSampler(int type, int desiredNumberOfSamples) {
    Sampler sampler;
    int gridSize;

    switch (type) {
      case CENTER: {
        sampler = new CenterSampler();
        break;
      }
      case RANDOM: {
        sampler = new RandomSampler();
        break;
      }
      case REGULAR_GRID: {
        gridSize = (int)Math.sqrt(desiredNumberOfSamples);
        if (gridSize <=0) gridSize = 1;
        sampler = new RegularGridSampler(gridSize);
        break;
      }
      default: {
        sampler = new RandomSampler();
        break;
      }
    }
    return(sampler);
  }
}

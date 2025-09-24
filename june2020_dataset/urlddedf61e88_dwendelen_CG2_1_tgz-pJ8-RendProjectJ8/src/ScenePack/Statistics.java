/*
 * Statistics.java
 *
 * Created on August 23, 2000, 10:11 AM
 */

package ScenePack;

/**
 *
 * @author  phil
 * @version
 */
public class Statistics extends Object {

  private static int numberOfObjectIntersections = 0;

  public static void oneMoreIntersection(Primitive primitive) {
    numberOfObjectIntersections++;
  }
  
  public static void reset() {
    numberOfObjectIntersections = 0;
  }

  public static void printStatistics() {
    double tmp;

    System.out.println("Statistics:");
    System.out.println("-----------");
    System.out.println("Number of Object Intersections: " + numberOfObjectIntersections);
    tmp = (double)numberOfObjectIntersections/(double)(Settings.IMAGE_WIDTH*Settings.IMAGE_HEIGHT);
    System.out.println("Average # of intersections per pixel: " + tmp);
  }

}
package Vectormath;

public class Vector2 extends Object {

  public double[] data = new double[2];
  /** Creates new Vector2 */
  public Vector2() {
    data[0] = 0.0;
    data[1] = 0.0;
  }
  public Vector2(double x, double y) {
    data[0] = x;
    data[1] = y;
  }

}
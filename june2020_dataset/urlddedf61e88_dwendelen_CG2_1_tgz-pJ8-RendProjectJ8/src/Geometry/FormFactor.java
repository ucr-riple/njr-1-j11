

package Geometry;

import Vectormath.*;
import Mathtools.*;


public class FormFactor extends Object {


  /** Computes the G-function between two points and their normals.
   * The G-function is the product of the cosine factors between the
   * normals and the line connecting the points, divided by the distance^2
   * between the points.
   * @param p1 point 1
   * @param n1 normal at point 1
   * @param p2 point 2
   * @param n2 normal at point 2
   * @return G-value
   */
  public static double G(Vector3 p1,Vector3 n1,Vector3 p2,Vector3 n2) {
    double ff;
    Vector3 connect;

    connect = Vector3.subtract(p2,p1);
    ff = Vector3.cosinePositiveOnly(n1,connect) * Vector3.cosinePositiveOnly(n2,connect.scale(-1.0));
    ff /= connect.norm2();
    return(ff);
  }
  
}
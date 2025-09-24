

package Geometry;

import Vectormath.*;
import Mathtools.*;

/** GeomtricObject is an interface that all objects in the scene have
 * to implement.
 */
public interface GeometricObject {
  /** This method computes the intersection of a given ray with the geometric
   * object. Only intersection points withing the given interval are requested.
   * Any output data is recorded in the geometric record.
   * @param ray input ray that has to be intersected with the current object
   * @param interval only intersection points within this interval are valid. The interval is expressed in units along the ray, NOT in values of the t-parameter along the ray
   * (the direction of the ray might not be normalized).
   * @param record Geomrecord that records all output data
   * @return true if an intersection point is found, false otherwise
   */
  public boolean intersect(Ray ray,Interval interval,GeomRecord record);

  /** Compute a random surface point on the surface of the geomtric object,
   * uniformly distributed w.r.t. area.
   * The two random numbers can be specified to make the process repeatable,
   * to enforce stratified sampling, or to use known pseudo-random sequences.
   * @param r1 1st random number, must lie in [0,1)
   * @param r2 2nd random number, must lie in [0,1)
   * @return GeomRecord that contains necessary data for the surface point
   */
  public GeomRecord randomSurfacePoint(double r1,double r2);

  /** Compute the total surface area of the geometric object.
   * @return total surface area of the geometric object
   */
  public double getArea();


}
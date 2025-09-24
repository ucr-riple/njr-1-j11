package Geometry;

import Vectormath.*;
import Mathtools.*;
/** Class Sphere defines spheres as geometric objects.
 */
public class Sphere implements GeometricObject {
  Vector3 center;

  double radius;

  /** Creates new default sphere
   */
  public Sphere() {
    center = new Vector3(0.0,0.0,0.0);
    radius = 1.0;
  }

  /** Create a new SPhere, with given center and radius
   * @param center Centerpoint of the new sphere
   * @param radius radius of the new sphere
   */
  public Sphere(Vector3 center,double radius) {
    this.center = center.makeCopy();
    this.radius = radius;
  }

  public boolean intersect(Ray ray,Interval interval,GeomRecord record) {
    double a,b,c,discriminant,sqrtdiscriminant;
    double t=0.0;
    double distance=0.0;
    boolean hit = false;
    Vector3 ominc;

    ominc = Vector3.subtract(ray.origin,this.center);
    a = Vector3.dot(ray.direction, ray.direction);
    b = 2.0*Vector3.dot(ray.direction, ominc);
    c = Vector3.dot(ominc,ominc) - this.radius*this.radius;
    discriminant = b*b-4.0*a*c;

    if (discriminant >= 0.0) { //this should become EPSILON at some point
      sqrtdiscriminant = Math.sqrt(discriminant);
      t = (-b-sqrtdiscriminant)/(2.0*a);
      distance = t*ray.direction.norm();
      if (interval.contains(distance)) { // now we have valid intersection for closest point
        hit = true;
      }
      else {
        t = (-b+sqrtdiscriminant)/(2.0*a);
        distance = t*ray.direction.norm();
        if (interval.contains(distance)) {// valid intersection for 2nd point
          hit = true;
        }
      }
    }

    if (hit) {
      record.point = ray.pointAtParameter(t);
      record.normal = (Vector3.subtract(record.point,this.center)).scaleInverse(this.radius);
      record.distance = distance;
    }
    return(hit);
  }

  public static void main(String[] args) {

    boolean hit;

    Sphere sph = new Sphere(new Vector3(3.0,0.0,0.0),1.0);
    Ray ray = new Ray(new Vector3(0.0,0.0,0.0),new Vector3(1.0,0.0,0.0));
    GeomRecord record = new GeomRecord();
    Interval interval = new Interval(0.0,Double.POSITIVE_INFINITY);

    hit = sph.intersect(ray, interval ,record);
    record.printlnGeomRecord();
  }

  // Not yet implemented!!!!!!!!!!!!!!!
  public GeomRecord randomSurfacePoint(double r1,double r2) {
    GeomRecord tmp = new GeomRecord();
    return(tmp);
  }

  public double getArea() {
    return(4.0*this.radius*this.radius*Math.PI);
  }


}
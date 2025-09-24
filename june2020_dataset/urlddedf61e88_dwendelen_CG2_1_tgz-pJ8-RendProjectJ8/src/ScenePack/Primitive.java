
package ScenePack;

import Geometry.*;
import Vectormath.*;
import Mathtools.*;
import Material.*;
import Color.*;
import java.util.*;

/** Class Primitive defines a primitive object. A Primitive is defined by a geomtry, a material and an emission, plus any other relevant data.
 * Think of a primitive as a single geometric object that can be rendered.
 */
public class Primitive extends Object {
  /** id-number of this Primitive. Each Primitive has a unique id-number.
   */
  public int idNumber;

  /** running id-number for all primitives
   */
  static int nextIdNumber =0;

  private GeometricObject geometry;

  private Reflection reflection;

  private Emission emission;

  private boolean castsShadow;

  private boolean receivesShadow;


  /** Creates new Primitive, that consists of the given geometry, the given material,
   * and might have an emission (can be null)
   * @param geom Geometry object
   * @param reflection Reflection Object
   * @param emission Emission object
   */
  public Primitive(GeometricObject geom,Reflection reflection,Emission emission) {
    this.idNumber = nextIdNumber;
    this.nextIdNumber ++;

    this.geometry = geom;
    this.reflection = reflection;
    this.emission = emission;
    if (emission != null) {
      emission.computeRadiance(this.geometry.getArea());
    }
    this.castsShadow = true;
    this.receivesShadow = true;
   
  }

  /** Intersect the primitive with a ray. The interval indicates what valid
   * distances along the ray are considered for finding an intersection point.
   * @param ray Ray that needs to be intersected with the Primitive.
   * @param interval Interval within which distances for the intersection point are valid.
   * @return IntersectionRecord that has all information about the intersection.
   */
  public PathNode intersect(Ray ray,Interval interval) {
    boolean hit;
    GeomRecord geomRecord = new GeomRecord();
    PathNode node = new PathNode();

    hit = this.geometry.intersect(ray, interval, geomRecord);
    if (hit) {
      node.hit = true;
      node.primitive = this;
      node.point = geomRecord.point;
      node.normal = geomRecord.normal;
      node.direction = ray.direction.scale(-1.0);
      node.distance = geomRecord.distance;
    }

    Statistics.oneMoreIntersection(this);
    return(node);
  }

  /** Evaluate the BRDF for a given point on the primitive and two
   * directions. The directions should be such that they point AWAY
   * from the surface.
   * @param position location on the primitive
   * @param inDirection direction from which light is coming
   * @param outDirection direction in which light is reflected
   * @return BRDF value
   */
  public RGBColor evaluateReflection(Vector3 position,Vector3 inDirection,Vector3 normal,Vector3 outDirection) {
    RGBColor c;
    c = this.reflection.evaluate(inDirection, normal, outDirection);
    return(c);
  }

  /** Evaluate the emission characteristics of the primitive.
   * The outgoing emission is a radiance value and is expressed as
   * Watt/m^2.sr
   * @param ray origin of the ray is on the Primitive, direction points away from the primitive.
   * @return Emission color
   */
  public RGBColor evaluateEmission(Ray ray) {
    RGBColor c;
    c = this.emission.evaluate(ray);
    return(c);
  }

  /** Generate a random surface point belonging to the primitive
   * @return random surface point
   */
  public GeomRecord randomSurfacePoint() {
    GeomRecord tmp;
    tmp = this.randomSurfacePoint(Math.random(), Math.random());
    return(tmp);
  }
  
  public double getArea() {
    return(this.geometry.getArea());
  }

  /** generate a random surface point, with given random numbers.
   * This is useful for stratfied sampling or pseudo-random sequences
   * @param r1 1st random number [0,1)
   * @param r2 2nd random number [0,1)
   * @return Point on the surface
   */
  public GeomRecord randomSurfacePoint(double r1,double r2) {
    GeomRecord tmp;
    tmp = this.geometry.randomSurfacePoint(r1, r2);
    return(tmp);
  }

  /** Checks whether this Primitive is a light source.
   * @return true if Primitive is a light source, false if it is not.
   */
  public boolean isLightSource() {
    return(emission != null);
  }

  /** Define the Primitive as casting shadows.
   * @param b true if the Primitive has to cast shadows, false if not.
   */
  public void setCastsShadow(boolean b) {
    this.castsShadow = b;
  }

  /** Define whether the Primitive receives shadows
   * @param b true if the Primitive is rendered with shadows, false if not
   */
  public void setReceivesShadow(boolean b) {
    this.receivesShadow = b;
  }

  /**
   * @return true if the Primitive casts shadows, false if not
   */
  public boolean castsShadow() {
    return(castsShadow);
  }

  /**
   * @return true if the primitive receives shadows, false if not
   */
  public boolean receivesShadow() {
    return(receivesShadow);
  }

  /**
   * @return true if the Primitive casts no shadow, false if not
   */
  public boolean castsNoShadow() {
    return(!castsShadow);
  }

  /**
   * @return true if the primitive does not receives shadows, false if not
   */
  public boolean receivesNoShadow() {
    return(!receivesShadow);
  }
}
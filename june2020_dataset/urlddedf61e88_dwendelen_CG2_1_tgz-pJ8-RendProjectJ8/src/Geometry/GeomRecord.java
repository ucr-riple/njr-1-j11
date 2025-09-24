package Geometry;

import Vectormath.*;

/** GeomRecord is a collection of data gathered from methods on GeomtricObjects.
 * E.g. intersection data or the random point requests.
 */
public class GeomRecord extends Object {
  /** Point on the surface of the geometric object
   */
  public Vector3 point;

  /** Normal vector at the point
   */
  public Vector3 normal;

  /** distance between the point and some other point. For intersection, this is the distance between the start of the ray and the found intersection point.
   */
  public double distance;

  /** Probability that the above point was generated.
   */
  public double probability;

  /** Creates new IntersectionRecord */
  public GeomRecord() {
    point = new Vector3();
    normal = new Vector3();
    distance = 0.0;
    probability = 1.0;
  }

  public void printlnGeomRecord() {
    System.out.println("Geom Record:");
    System.out.print("Hit Point: ");this.point.printVector3();
    System.out.print("Hit Normal: ");this.normal.printVector3();
    System.out.print("distance-value: " + this.distance);
    System.out.println("probability-value: " + this.probability);
  }

  public GeomRecord makeCopy() {
    GeomRecord  tmpRecord = new GeomRecord();

    tmpRecord.point = this.point.makeCopy();
    tmpRecord.normal = this.normal.makeCopy();
    tmpRecord.distance = this.distance;
    tmpRecord.probability = this.probability;

    return(tmpRecord);
  }

  public void makeCopyOf(GeomRecord ir) {
    point = ir.point.makeCopy();
    normal = ir.normal.makeCopy();
    distance = ir.distance;
    probability = ir.probability;
  }


}
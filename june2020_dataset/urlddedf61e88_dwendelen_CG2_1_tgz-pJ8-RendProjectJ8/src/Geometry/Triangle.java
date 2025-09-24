package Geometry;

import Vectormath.*;
import Mathtools.*;

/** Class Triangle define polygonal triangles in 3D space.
 * Triangle implements the interface GeometricObject.
 */
public class Triangle implements GeometricObject {
  private Vector3[] vertex;

  private Vector3 edge01;

  private Vector3 edge02;

  private Vector3 normal;

  private double area;

  /** Creates new default Triangle
   */
  public Triangle() {
    this(new Vector3(1.0, 0.0, 0.0),new Vector3(0.0, 1.0, 0.0),new Vector3(0.0, 0.0, 1.0));
  }

  /** Creates a new triangle. The vertices should be given in counterclockwise
   * orientation, as seen from the 'front' side of the triangle.
   * @param a 1st vertex
   * @param b 2nd vertex
   * @param c 3rd vertex
   */
  public Triangle(Vector3 a,Vector3 b,Vector3 c) {
    vertex = new Vector3[3];
    vertex[0] = a.makeCopy();
    vertex[1] = b.makeCopy();
    vertex[2] = c.makeCopy();

    edge01 = Vector3.subtract(vertex[0],vertex[1]);
    edge02 = Vector3.subtract(vertex[0],vertex[2]);

    normal = Vector3.cross(Vector3.subtract(vertex[1],vertex[0]),Vector3.subtract(vertex[2],vertex[0]));
    normal.normalize();

    area = this.computeArea();
  }

  private double computeArea() {
    Vector3 a,b,c;
    double total_area;

    a = Vector3.cross(vertex[0],vertex[1]);
    b = Vector3.cross(vertex[1],vertex[2]);
    c = Vector3.cross(vertex[2],vertex[0]);
    total_area = (Vector3.add(a,b,c)).norm()/2.0;

    return(total_area);
  }

  /** */
  public boolean intersect(Ray ray,Interval interval,GeomRecord record) {
    Vector3 v3;
    double ei_hf, gf_di, dh_eg, ak_jb, jc_al, bl_kc;
    double distance;
    double M,beta,gamma,t;
    boolean hit = false;

    v3 = Vector3.subtract(this.vertex[0],ray.origin);

    ei_hf = edge02.data[1]*ray.direction.data[2]-ray.direction.data[1]*edge02.data[2];
    gf_di = ray.direction.data[0]*edge02.data[2]-edge02.data[0]*ray.direction.data[2];
    dh_eg = edge02.data[0]*ray.direction.data[1]-edge02.data[1]*ray.direction.data[0];
    ak_jb = edge01.data[0]*v3.data[1]-v3.data[0]*edge01.data[1];
    jc_al = v3.data[0]*edge01.data[2]-edge01.data[0]*v3.data[2];
    bl_kc = edge01.data[1]*v3.data[2]-v3.data[1]*edge01.data[2];

    M = edge01.data[0]*ei_hf + edge01.data[1]*gf_di + edge01.data[2]*dh_eg;

    if (Math.abs(M) > 0.0) {
      beta = (v3.data[0]*ei_hf + v3.data[1]*gf_di + v3.data[2]*dh_eg)/M;
      gamma = (ray.direction.data[2]*ak_jb + ray.direction.data[1]*jc_al + ray.direction.data[0]*bl_kc)/M;
      t = -(edge02.data[2]*ak_jb + edge02.data[1]*jc_al + edge02.data[0]*bl_kc)/M;
      distance = t*ray.direction.norm();

      if ((beta >= 0.0) && (gamma >= 0.0) && (beta+gamma <= 1.0) && interval.contains(distance)) {
        hit = true;
        record.point = ray.pointAtParameter(t);
        record.normal = this.normal;
        record.distance = distance;
      }
    }
    return(hit);
  }

  public static void main(String[] args) {

    boolean hit;

    Triangle tri = new Triangle();
    Ray ray = new Ray(new Vector3(0.0,0.0,0.0),new Vector3(1.0,1.0,1.0));
    GeomRecord record = new GeomRecord();
    Interval interval = new Interval(0.0,Double.POSITIVE_INFINITY);

    hit = tri.intersect(ray, interval ,record);
    record.printlnGeomRecord();
  }

  /** Generating a random point on a triangle.
   * Based on algorithm given in Graphics Gems 1, p 24
   */
  public GeomRecord randomSurfacePoint(double r1,double r2) {
    double a,b,c;
    GeomRecord record = new GeomRecord();

    a = 1.0 - Math.sqrt(r1);
    b = (1.0-r2)*Math.sqrt(r1);
    c = r2*Math.sqrt(r1);

    record.point = Vector3.linearCombination(a, this.vertex[0], b, this.vertex[1], c ,this.vertex[2]);
    record.normal = this.normal;
    record.probability = 1.0/this.area;

    return(record);
  }

  /** returns the area of the triangle
   * @return area of the triangle
   */
  public double getArea() {
    return(this.area);
  }


}
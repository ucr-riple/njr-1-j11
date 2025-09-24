
package Geometry;

import Vectormath.*;
import Mathtools.*;
import java.util.*;

/** Class Rectangle define planar rectangles
 */
public class Rectangle implements GeometricObject {
  private Vector3[] vertex;

  // edgeji is a vector from point j to i (confusing, I know!!!!)
  private Vector3 edge01;

  private Vector3 edge02;

  private Vector3 edge03;

  private Vector3 normal;

  private double area;

  /** Creates new default rectangle
   */
  public Rectangle() {
    this(new Vector3(0.0, 0.0, 0.0),new Vector3(1.0, 0.0, 0.0),new Vector3(1.0, 1.0, 0.0),new Vector3(0.0, 1.0, 0.0));
  }

  /** Creates new rectangle with given vertices.
   * Orientation should be counterclockwise as seen from the 'front'.
   * No validity check is made to see whether this is indeed a rectangle,
   * so slopless data could result in a non-planar quadrangle
   * @param a 1st vertex
   * @param b 2nd vertex
   * @param c 3rd vertex
   * @param d 4th vertex
   */
  public Rectangle(Vector3 a,Vector3 b,Vector3 c,Vector3 d) {
    vertex = new Vector3[4];
    vertex[0] = a.makeCopy();
    vertex[1] = b.makeCopy();
    vertex[2] = c.makeCopy();
    vertex[3] = d.makeCopy();

    edge01 = Vector3.subtract(vertex[0],vertex[1]);
    edge02 = Vector3.subtract(vertex[0],vertex[2]);
    edge03 = Vector3.subtract(vertex[0],vertex[3]);

    normal = Vector3.cross(Vector3.subtract(vertex[1],vertex[0]),Vector3.subtract(vertex[2],vertex[0]));
    normal.normalize();

    area = this.computeArea();
  }

  private double computeArea() {
    double total_area;
    total_area = edge01.norm() * edge03.norm() ;
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

    ei_hf = edge03.data[1]*ray.direction.data[2]-ray.direction.data[1]*edge03.data[2];
    gf_di = ray.direction.data[0]*edge03.data[2]-edge03.data[0]*ray.direction.data[2];
    dh_eg = edge03.data[0]*ray.direction.data[1]-edge03.data[1]*ray.direction.data[0];
    ak_jb = edge01.data[0]*v3.data[1]-v3.data[0]*edge01.data[1];
    jc_al = v3.data[0]*edge01.data[2]-edge01.data[0]*v3.data[2];
    bl_kc = edge01.data[1]*v3.data[2]-v3.data[1]*edge01.data[2];

    M = edge01.data[0]*ei_hf + edge01.data[1]*gf_di + edge01.data[2]*dh_eg;

    if (Math.abs(M) > 0.0) {
      beta = (v3.data[0]*ei_hf + v3.data[1]*gf_di + v3.data[2]*dh_eg)/M;
      gamma = (ray.direction.data[2]*ak_jb + ray.direction.data[1]*jc_al + ray.direction.data[0]*bl_kc)/M;
      t = -(edge03.data[2]*ak_jb + edge03.data[1]*jc_al + edge03.data[0]*bl_kc)/M;
      distance = t*ray.direction.norm();

      if ((beta >= 0.0) && (gamma >= 0.0) && (beta <= 1.0) && (gamma <= 1.0) && interval.contains(distance)) {
        hit = true;
        record.point = ray.pointAtParameter(t);
        record.normal = this.normal;
        record.distance = distance;
      }
    }
    return(hit);
  }

  public GeomRecord randomSurfacePoint(double r1,double r2) {
    GeomRecord record = new GeomRecord();

    record.point = Vector3.linearCombination(1.0, this.vertex[2], r1, this.edge01, r2, this.edge03);
    record.normal = this.normal;
    record.probability = 1.0/this.area;

    return(record);
  }

  /** returns area of rectangle
   * @return area of rectangle
   */
  public double getArea() {
    return(this.area);
  }


  public Vector splitRectangle(int a, int b) {
    Vector v = new Vector(a*b);
    int i,j;
    Rectangle r;
    Vector3 c1,c2,c3,c4,inc1,inc2;

    inc1 = this.edge01.scaleInverse(-a);
    inc2 = this.edge03.scaleInverse(-b);
    for (i=0;i<a;i++) {
      for (j=0;j<b;j++) {
        c1 = Vector3.linearCombination(this.vertex[0],i,inc1,j,inc2);
        c2 = Vector3.linearCombination(this.vertex[0],i+1,inc1,j,inc2);
        c3 = Vector3.linearCombination(this.vertex[0],i+1,inc1,j+1,inc2);
        c4 = Vector3.linearCombination(this.vertex[0],i,inc1,j+1,inc2);
        v.addElement(new Rectangle(c1,c2,c3,c4));
      }
    }
    return(v);
  }
}
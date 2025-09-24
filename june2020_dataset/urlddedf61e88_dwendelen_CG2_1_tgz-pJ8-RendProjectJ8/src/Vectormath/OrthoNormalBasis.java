package Vectormath;

/** OrthoNormalBasis describes any basis of 3 normalized and orthogonal vectors
 * in XYZ space
 */
public class OrthoNormalBasis extends Object {

  /**
   * vectors u,v,w are the basisvectors of the orthonormal basis
   * They are supposed to obey the right-hand rule.
   */
  public Vector3 u;
  public Vector3 v;
  public Vector3 w;

  /** Creates new OrthoNormalBasis, default canonical basis XYZ */
  public OrthoNormalBasis() {
    u = new Vector3(1.0, 0.0, 0.0);
    v = new Vector3(0.0, 1.0, 0.0);
    w = new Vector3(0.0, 0.0, 1.0);
  }

  /** Constructs an orthonormal basis, starting from 2 vectors a and b.
   * u is chosen to be parallel to a.
   * w is chosen to be parallel to axb
   * v is in the plane of a and b
   * Gramm-Schmidt on ab:
   * u is parallel with a
   * v is in plane of a and b
   * w is parallel to axb
   * @param a
   * @param b
   */
  public void constructUVFrom(Vector3 a,Vector3 b) {
    this.u = Vector3.normalize(a);
    this.w = Vector3.normalize(Vector3.cross(a,b));
    this.v = Vector3.cross(this.w,this.u);
  }

  /** Gramm-Schmidt on ab:
   * v is parallel with a
   * u is in plane of a and b
   * w is parallel with bxa
   * @param a
   * @param b
   */
  public void constructVUFrom(Vector3 a,Vector3 b) {
    this.v = Vector3.normalize(a);
    this.w = Vector3.normalize(Vector3.cross(b,a));
    this.u = Vector3.cross(this.v,this.w);
  }

  /** Gramm-Schmidt on ab:
   * v is parallel with a
   * w is in plane of a and b
   * u is parallel to axb
   * @param a
   * @param b
   */
  public void constructVWFrom(Vector3 a,Vector3 b) {
    this.v = Vector3.normalize(a);
    this.u = Vector3.normalize(Vector3.cross(a,b));
    this.w = Vector3.cross(this.u,this.v);
  }

  /** Gramm-Schmidt on ab:
   * w is parallel with a
   * v is in plane of a and b
   * u is parallel to bxa
   * @param a
   * @param b
   */
  public void constructWVFrom(Vector3 a,Vector3 b) {
    this.w = Vector3.normalize(a);
    this.u = Vector3.normalize(Vector3.cross(b,a));
    this.v = Vector3.cross(this.w,this.u);
  }

  /** Gramm-Schmidt on ab:
   * u is parallel with a
   * w is in plane of a and b
   * v is parallel to bxa
   * @param a
   * @param b
   */
  public void constructUWFrom(Vector3 a,Vector3 b) {
    this.u = Vector3.normalize(a);
    this.v = Vector3.normalize(Vector3.cross(b,a));
    this.w = Vector3.cross(this.u,this.v);
  }

  /** Gramm-Schmidt on ab:
   * w is parallel with a
   * u is in plane of a and b
   * v is parallel to axb
   * @param a
   * @param b
   */
  public void constructWUFrom(Vector3 a,Vector3 b) {
    this.w = Vector3.normalize(a);
    this.v = Vector3.normalize(Vector3.cross(a,b));
    this.u = Vector3.cross(this.v,this.w);
  }

  /** Construct basis by setting w parallel to given vector
   * the other 2 vectors are default choices, not specified by user
   * @param a
   */
  public void constructWFrom(Vector3 a) {
    this.w = Vector3.normalize(a);
    if ( (Math.abs(this.w.data[0]) < Math.abs(this.w.data[1])) && (Math.abs(this.w.data[0]) < Math.abs(this.w.data[2]))) {
      // x component is smallest in absolute value
      this.v.data[0] = 0.0;
      this.v.data[1] = this.w.data[2];
      this.v.data[2] = -this.w.data[1];
      v.normalize();
    }
    else if ((Math.abs(this.w.data[1]) < Math.abs(this.w.data[2]))) {
      // y component is smallest in absolute value
      this.v.data[0] = this.w.data[2];
      this.v.data[1] = 0.0;
      this.v.data[2] = -this.w.data[0];
      v.normalize();
    }
    else {
      // z component is smallest in absolute value
      this.v.data[0] = this.w.data[1];
      this.v.data[1] = -this.w.data[0];
      this.v.data[2] = 0.0;
      v.normalize();
    }
    this.u = Vector3.cross(this.v,this.w);
  }

  /** Takes vector a in canonical basis XYZ and converts to coordinates
   * in given basis.
   * @param a input vector in canonical basis XYZ
   * @return coordinates of a in new basis
   */
  public Vector3 convert(Vector3 a) {
    Vector3 tmp = new Vector3();

    tmp.data[0] = Vector3.dot(this.u,a);
    tmp.data[1] = Vector3.dot(this.v,a);
    tmp.data[2] = Vector3.dot(this.w,a);

    return(tmp);
  }

  /** Prints all components of the object
   */
  public void printOrthoNormalBasis() {
    System.out.print("Basis: ");
    System.out.print("( "+this.u.data[0]+" , "+this.u.data[1]+" , "+this.u.data[2]+" )");
    System.out.print("( "+this.v.data[0]+" , "+this.v.data[1]+" , "+this.v.data[2]+" )");
    System.out.println("( "+this.w.data[0]+" , "+this.w.data[1]+" , "+this.w.data[2]+" )");
  }

  public static void main(String[] args) {
    System.out.println("Tests for OrthoNormal Basis");

    Vector3 a = new Vector3(2.0, 0.0, 0.0);
    Vector3 b = new Vector3(1.0, 1.0, 0.0);
    OrthoNormalBasis basis = new OrthoNormalBasis();

    System.out.print("Vector a: ");a.printVector3();
    System.out.print("Vector b: ");b.printVector3();

    basis.constructUVFrom(a,b);
    System.out.print("ConstructUVFrom: ");basis.printOrthoNormalBasis();
    basis.constructVUFrom(a,b);
    System.out.print("ConstructVUFrom: ");basis.printOrthoNormalBasis();
    basis.constructVWFrom(a,b);
    System.out.print("ConstructVWFrom: ");basis.printOrthoNormalBasis();
    basis.constructWVFrom(a,b);
    System.out.print("ConstructWVFrom: ");basis.printOrthoNormalBasis();
    basis.constructUWFrom(a,b);
    System.out.print("ConstructUWFrom: ");basis.printOrthoNormalBasis();
    basis.constructWUFrom(a,b);
    System.out.print("ConstructWUFrom: ");basis.printOrthoNormalBasis();
    basis.constructWFrom(b);
    System.out.print("ConstructWFrom b: ");basis.printOrthoNormalBasis();


  }

  /** Creates a new object and copies existing object
   * @return new copy of the object
   */
  public OrthoNormalBasis makeCopy() {
    OrthoNormalBasis onb = new OrthoNormalBasis();
    onb.u = this.u.makeCopy();
    onb.v = this.v.makeCopy();
    onb.w = this.w.makeCopy();
    return(onb);
  }


}
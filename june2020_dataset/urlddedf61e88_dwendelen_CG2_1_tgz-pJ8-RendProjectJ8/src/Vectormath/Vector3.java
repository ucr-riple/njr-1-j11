package Vectormath;

/** Class Vector3 defines vectors in 3d and operators for 3d vectors
 */
public class Vector3 extends Object {
  /** Fields specifying the three components of the vector
   */
  public double[] data =new double[3];

  /** Creates a new Vector3, initialized as the origin (0, 0, 0)
   */
  public Vector3() {
    data[0] = 0.0;
    data[1] = 0.0;
    data[2] = 0.0;
  }

  /** Creates a new Vector3, with given parameters as its coordinates
   * @param x 1st coordinate of vector
   * @param y 2nd component of vector
   * @param z 3rd component of vector
   */
  public Vector3(double x,double y,double z) {
    data[0] = x;
    data[1] = y;
    data[2] = z;
  }

  /** Computes and returns the norm of vector
   * @return Cartesian norm of vector
   */
  public double norm() {
    double tmp;
    tmp = Math.sqrt(this.data[0]*this.data[0] + this.data[1]*this.data[1] + this.data[2]*this.data[2]);
    return(tmp);
  }

  /** Computes and returns the squared norm of vector
   * @return Cartesian norm^2 of vector
   */
  public double norm2() {
    double tmp;
    tmp = this.data[0]*this.data[0] + this.data[1]*this.data[1] + this.data[2]*this.data[2];
    return(tmp);
  }

  /** Add two vectors
   * @param a 1st term of sum
   * @param b 2nd term of sum
   * @return resulting sum vector (new object)
   */
  public static Vector3 add(Vector3 a,Vector3 b) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = a.data[0] + b.data[0];
    tmp.data[1] = a.data[1] + b.data[1];
    tmp.data[2] = a.data[2] + b.data[2];
    return(tmp);
  }

  /** Add three vectors
   * @param a 1st term of sum
   * @param b 2nd term of sum
   * @param c 3rd term of sum
   * @return resulting sum vector (new object)
   */
  public static Vector3 add(Vector3 a,Vector3 b,Vector3 c) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = a.data[0] + b.data[0] + c.data[0];
    tmp.data[1] = a.data[1] + b.data[1] + c.data[1];
    tmp.data[2] = a.data[2] + b.data[2] + c.data[2];
    return(tmp);
  }

  /** Subtract two vectors
   * @param a 1st term of subtraction
   * @param b 2nd term of subtraction
   * @return resulting vector (new object)
   */
  public static Vector3 subtract(Vector3 a,Vector3 b) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = a.data[0] - b.data[0];
    tmp.data[1] = a.data[1] - b.data[1];
    tmp.data[2] = a.data[2] - b.data[2];
    return(tmp);
  }

  /** Scale a vector with a scalar, and return new vector
   * @param s scalar
   * @return resulting scaled vector (new object)
   */
  public Vector3 scale(double s) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = this.data[0] * s;
    tmp.data[1] = this.data[1] * s;
    tmp.data[2] = this.data[2] * s;
    return(tmp);
  }

  /** Scale a vector with the inverse of a scalar, and return new vector
   * @param s scalar
   * @return resulting scaled vector (new object)
   */
  public Vector3 scaleInverse(double s) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = this.data[0] / s;
    tmp.data[1] = this.data[1] / s;
    tmp.data[2] = this.data[2] / s;
    return(tmp);
  }

  /** Compute the dot product of two vectors
   * @param a 1st vector
   * @param b 2nd vector
   * @return resulting dot product
   */
  public static double dot(Vector3 a,Vector3 b) {
    double tmp;
    tmp = a.data[0]*b.data[0] + a.data[1]*b.data[1] + a.data[2]*b.data[2];
    return(tmp);
  }

  /** Compute the cross product of two vectors
   * @param a 1st vector
   * @param b 2nd vector
   * @return resulting cross product (new object)
   */
  public static Vector3 cross(Vector3 a,Vector3 b) {
    Vector3 tmp = new Vector3();
    tmp.data[0] = a.data[1]*b.data[2]-a.data[2]*b.data[1];
    tmp.data[1] = a.data[2]*b.data[0]-a.data[0]*b.data[2];
    tmp.data[2] = a.data[0]*b.data[1]-a.data[1]*b.data[0];
    return(tmp);
  }

  /** Print the three components of a vector
   */
  public void printVector3() {
    System.out.print("( "+this.data[0]+" , "+this.data[1]+" , "+this.data[2]+" )");
  }

  /** Print the three components of a vector, followed by a newline
   */
  public void printlnVector3() {
    System.out.println("( "+this.data[0]+" , "+this.data[1]+" , "+this.data[2]+" )");
  }

  /** Normalizes the vector on which this method is applied, and return the resulting new vector
   * @param v vector to be normalized
   * @return normalized vector (new object)
   */
  public static Vector3 normalize(Vector3 v) {

    Vector3 tmp = new Vector3();

    double length = v.norm();

    tmp.data[0] = v.data[0]/length;
    tmp.data[1] = v.data[1]/length;
    tmp.data[2] = v.data[2]/length;

    return(tmp);
  }

  /** Normalizes the vector on which this method is applied
   */
  public void normalize() {
    double length = this.norm();

    this.data[0] /= length;
    this.data[1] /= length;
    this.data[2] /= length;
  }

  /** make a copy of vector
   * @return copy of vector on which method is apllied (new object)
   */
  public Vector3 makeCopy() {
    Vector3 tmp = new Vector3(this.data[0],this.data[1],this.data[2]);
    return(tmp);
  }

  /** Computes the cosine of the angle between two vectors. The cosine is signed.
   * @param a 1st vector
   * @param b 2nd vector
   * @return cosine of the angle between the two vectors
   */
  public static double cosine(Vector3 a,Vector3 b) {
    double tmp;
    tmp = dot(a,b)/(a.norm()*b.norm());
    return(tmp);
  }

  /** Computes the cosine of the angle between two vectors, but returns
   * only positive values. Negative cosine values are clipped to zero.
   * @param a 1st vector
   * @param b 2nd vector
   * @return cosine (positive only) of the angle between the two vectors
   */
  public static double cosinePositiveOnly(Vector3 a,Vector3 b) {
    double tmp;
    tmp = cosine(a,b);
    if (tmp < 0.0) tmp = 0.0;
    return(tmp);
  }

  /** Compute the distance between two points
   * @param a 1st point
   * @param b 2nd point
   * @return Cartesian distance between the two points
   */
  public static double distance(Vector3 a,Vector3 b) {
    double d;
    d = Math.sqrt((a.data[0]-b.data[0])*(a.data[0]-b.data[0])+(a.data[1]-b.data[1])*(a.data[1]-b.data[1])+(a.data[2]-b.data[2])*(a.data[2]-b.data[2]));
    return(d);
  }

  /** Compute linear combination of vectors and scalars
   * result = a.x + b.y, with x,y vectors and a,b scalars
   * @param a scalar for x
   * @param x vector weighted with a
   * @param b scalar for y
   * @param y vector weighted with b
   * @return resulting vector (new object)
   */
  public static Vector3 linearCombination(double a,Vector3 x,double b,Vector3 y) {
    Vector3 result = new Vector3();
    result.data[0] = a*x.data[0] + b*y.data[0];
    result.data[1] = a*x.data[1] + b*y.data[1];
    result.data[2] = a*x.data[2] + b*y.data[2];
    return(result);
  }

  /** Compute linear combination of vectors and scalars
   * result = a.x + b.y + c.z, with x,y,z vectors and a,b,c scalars
   * @param a scalar for x
   * @param x vector weighted with a
   * @param b scalar for y
   * @param y vector weighted with b
   * @param c scalar for z
   * @param z vector weighted with c
   * @return resulting vector (new object)
   */
  public static Vector3 linearCombination(double a,Vector3 x,double b,Vector3 y,double c,Vector3 z) {
    Vector3 result = new Vector3();
    result.data[0] = a*x.data[0] + b*y.data[0] + c*z.data[0];
    result.data[1] = a*x.data[1] + b*y.data[1] + c*z.data[1];
    result.data[2] = a*x.data[2] + b*y.data[2] + c*z.data[2];
    return(result);
  }

   /** Compute linear combination of vectors and scalars
   * result = x + b.y + c.z, with x,y,z vectors and a,b,c scalars
   * @param x vector
   * @param b scalar for y
   * @param y vector weighted with b
   * @param c scalar for z
   * @param z vector weighted with c
   * @return resulting vector (new object)
   */
  public static Vector3 linearCombination(Vector3 x,double b,Vector3 y,double c,Vector3 z) {
    Vector3 result = new Vector3();
    result.data[0] = x.data[0] + b*y.data[0] + c*z.data[0];
    result.data[1] = x.data[1] + b*y.data[1] + c*z.data[1];
    result.data[2] = x.data[2] + b*y.data[2] + c*z.data[2];
    return(result);
  }
  
  /** Compute linear combination of vectors and scalars
   * result = x + b.y, with x,y vectors and b scalar
   * @param x vector
   * @param b scalar for y
   * @param y vector weighted with b
   * @return resulting vector (new object)
   */
  public static Vector3 linearCombination(Vector3 x,double b,Vector3 y) {
    Vector3 result = new Vector3();
    result.data[0] = x.data[0] + b*y.data[0];
    result.data[1] = x.data[1] + b*y.data[1];
    result.data[2] = x.data[2] + b*y.data[2];
    return(result);
  }

  /** Compute linear combination of vectors and scalars
   * result = a.x + y, with x,y vectors and a scalar
   * @param a scalar for x
   * @param x vector weighted with a
   * @param y vector
   * @return resulting vector (new object)
   */
  public static Vector3 linearCombination(double a,Vector3 x,Vector3 y) {
    Vector3 result = new Vector3();
    result.data[0] = a*x.data[0] + y.data[0];
    result.data[1] = a*x.data[1] + y.data[1];
    result.data[2] = a*x.data[2] + y.data[2];
    return(result);
  }

  public static Vector3 halfVector(Vector3 a, Vector3 b)
  {
	  Vector3 h = Vector3.add(a, b);
	  h.normalize();
	  
	  return h;
  }

}
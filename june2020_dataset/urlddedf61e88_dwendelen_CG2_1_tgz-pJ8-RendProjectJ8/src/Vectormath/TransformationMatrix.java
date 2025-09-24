package Vectormath;

/** Class TransformationMatrix handles all sorts of operations on matrices and coordinate transforms
 */
public class TransformationMatrix extends Object {

  /** we store the matrix, as well as its inverse
   */
  double[][] data;
  double[][] invdata;

  /** Creates new TransformationMatrix, by default the identity matrix
   */
  public TransformationMatrix() {
    data = new double[4][4];
    invdata = new double[4][4];

    data[0][0] = 1.0; data[0][1] = 0.0; data[0][2] = 0.0; data[0][3] = 0.0;
    data[1][0] = 0.0; data[1][1] = 1.0; data[1][2] = 0.0; data[1][3] = 0.0;
    data[2][0] = 0.0; data[2][1] = 0.0; data[2][2] = 1.0; data[2][3] = 0.0;
    data[3][0] = 0.0; data[3][1] = 0.0; data[3][2] = 0.0; data[3][3] = 1.0;

    invdata[0][0] = 1.0; invdata[0][1] = 0.0; invdata[0][2] = 0.0; invdata[0][3] = 0.0;
    invdata[1][0] = 0.0; invdata[1][1] = 1.0; invdata[1][2] = 0.0; invdata[1][3] = 0.0;
    invdata[2][0] = 0.0; invdata[2][1] = 0.0; invdata[2][2] = 1.0; invdata[2][3] = 0.0;
    invdata[3][0] = 0.0; invdata[3][1] = 0.0; invdata[3][2] = 0.0; invdata[3][3] = 1.0;
  }

  /** Creates new Identity matrix
   * @return identity matrix (new object)
   */
  public static TransformationMatrix identity() {
    TransformationMatrix tmp = new TransformationMatrix();
    return(tmp);
  }

  /** Creates translation matrix
   * @param trans vector that indicates the translation operator
   * @return resulting translation matrix (new object)
   */
  public static TransformationMatrix translation(Vector3 trans) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = 1.0; tmp.data[0][1] = 0.0; tmp.data[0][2] = 0.0; tmp.data[0][3] = trans.data[0];
    tmp.data[1][0] = 0.0; tmp.data[1][1] = 1.0; tmp.data[1][2] = 0.0; tmp.data[1][3] = trans.data[1];
    tmp.data[2][0] = 0.0; tmp.data[2][1] = 0.0; tmp.data[2][2] = 1.0; tmp.data[2][3] = trans.data[2];
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = 1.0; tmp.invdata[0][1] = 0.0; tmp.invdata[0][2] = 0.0; tmp.invdata[0][3] = -trans.data[0];
    tmp.invdata[1][0] = 0.0; tmp.invdata[1][1] = 1.0; tmp.invdata[1][2] = 0.0; tmp.invdata[1][3] = -trans.data[1];
    tmp.invdata[2][0] = 0.0; tmp.invdata[2][1] = 0.0; tmp.invdata[2][2] = 1.0; tmp.invdata[2][3] = -trans.data[2];
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }

  /** Creates new scaling matrix, with given vector as scaling factors for x, y and z
   * @param scale vector that gives 3 scaling factors for x, y and z
   * @return scaling matrix (new object)
   */
  public static TransformationMatrix scale(Vector3 scale) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = scale.data[0]; tmp.data[0][1] = 0.0; tmp.data[0][2] = 0.0; tmp.data[0][3] = 0.0;
    tmp.data[1][0] = 0.0; tmp.data[1][1] = scale.data[1]; tmp.data[1][2] = 0.0; tmp.data[1][3] = 0.0;
    tmp.data[2][0] = 0.0; tmp.data[2][1] = 0.0; tmp.data[2][2] = scale.data[2]; tmp.data[2][3] = 0.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = 1.0/scale.data[0]; tmp.invdata[0][1] = 0.0; tmp.invdata[0][2] = 0.0; tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = 0.0; tmp.invdata[1][1] = 1.0/scale.data[1]; tmp.invdata[1][2] = 0.0; tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = 0.0; tmp.invdata[2][1] = 0.0; tmp.invdata[2][2] = 1.0/scale.data[2]; tmp.invdata[2][3] = 0.0;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }

  /** Creates rotation matrix around X-axis
   * @param theta rotation angle in radians. Positive angle obeys right-hand coordinate rule.
   * @return rotation matrix (new object)
   */
  public static TransformationMatrix rotateX(double theta) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = 1.0; tmp.data[0][1] = 0.0; tmp.data[0][2] = 0.0; tmp.data[0][3] = 0.0;
    tmp.data[1][0] = 0.0; tmp.data[1][1] = Math.cos(theta); tmp.data[1][2] = -Math.sin(theta); tmp.data[1][3] = 0.0;
    tmp.data[2][0] = 0.0; tmp.data[2][1] = Math.sin(theta); tmp.data[2][2] = Math.cos(theta); tmp.data[2][3] = 0.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = 1.0; tmp.invdata[0][1] = 0.0; tmp.invdata[0][2] = 0.0; tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = 0.0; tmp.invdata[1][1] = Math.cos(theta); tmp.invdata[1][2] = Math.sin(theta); tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = 0.0; tmp.invdata[2][1] = -Math.sin(theta); tmp.invdata[2][2] = Math.cos(theta); tmp.invdata[2][3] = 0.0;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }
  /** Creates rotation matrix around Y-axis
   * @param theta rotation angle in radians. Positive angle obeys right-hand coordinate rule.
   * @return rotation matrix (new object)
   */
  public static TransformationMatrix rotateY(double theta) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = Math.cos(theta); tmp.data[0][1] = 0.0; tmp.data[0][2] = Math.sin(theta); tmp.data[0][3] = 0.0;
    tmp.data[1][0] = 0.0; tmp.data[1][1] = 1.0; tmp.data[1][2] = 0.0; tmp.data[1][3] = 0.0;
    tmp.data[2][0] = -Math.sin(theta); tmp.data[2][1] = 0.0; tmp.data[2][2] = Math.cos(theta); tmp.data[2][3] = 0.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = Math.cos(theta); tmp.invdata[0][1] = 0.0; tmp.invdata[0][2] = -Math.sin(theta); tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = 0.0; tmp.invdata[1][1] = 1.0; tmp.invdata[1][2] = 0.0; tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = Math.sin(theta); tmp.invdata[2][1] = 0.0; tmp.invdata[2][2] = Math.cos(theta); tmp.invdata[2][3] = 0.0;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }
  /** Creates rotation matrix around Z-axis
   * @param theta rotation angle in radians. Positive angle obeys right-hand coordinate rule.
   * @return rotation matrix (new object)
   */
  public static TransformationMatrix rotateZ(double theta) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = Math.cos(theta); tmp.data[0][1] = -Math.sin(theta); tmp.data[0][2] = 0.0; tmp.data[0][3] = 0.0;
    tmp.data[1][0] = Math.sin(theta); tmp.data[1][1] = Math.cos(theta); tmp.data[1][2] = 0.0; tmp.data[1][3] = 0.0;
    tmp.data[2][0] = 0.0; tmp.data[2][1] = 0.0; tmp.data[2][2] = 1.0; tmp.data[2][3] = 0.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = Math.cos(theta); tmp.invdata[0][1] = Math.sin(theta); tmp.invdata[0][2] = 0.0; tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = -Math.sin(theta); tmp.invdata[1][1] = Math.cos(theta); tmp.invdata[1][2] = 0.0; tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = 0.0; tmp.invdata[2][1] = 0.0; tmp.invdata[2][2] = 1.0; tmp.invdata[2][3] = 0.0;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }


  public static TransformationMatrix perspective(double distance) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = 1.0; tmp.data[0][1] = 0.0; tmp.data[0][2] = 0.0; tmp.data[0][3] = 0.0;
    tmp.data[1][0] = 0.0; tmp.data[1][1] = 1.0; tmp.data[1][2] = 0.0; tmp.data[1][3] = 0.0;
    tmp.data[2][0] = 0.0; tmp.data[2][1] = 0.0; tmp.data[2][2] = 0.0; tmp.data[2][3] = 1.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = -1.0/distance; tmp.data[3][3] = 0.0;

    tmp.invdata[0][0] = 1.0; tmp.invdata[0][1] = 0.0; tmp.invdata[0][2] = 0.0; tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = 0.0; tmp.invdata[1][1] = 1.0; tmp.invdata[1][2] = 0.0; tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = 0.0; tmp.invdata[2][1] = 0.0; tmp.invdata[2][2] = 0.0; tmp.invdata[2][3] = -distance;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 1.0; tmp.invdata[3][3] = 0.0;

    return(tmp);
  }



  /** Transform a vector as a location
   * @param v vector to be transformed
   * @return result of transformation (new object)
   */
  public Vector3 transformAsLocation(Vector3 v) {
    Vector3 tmp = new Vector3();
    double homogenous = 1;

    tmp.data[0] = data[0][0]*v.data[0] + data[0][1]*v.data[1] + data[0][2]*v.data[2] + data[0][3];
    tmp.data[1] = data[1][0]*v.data[0] + data[1][1]*v.data[1] + data[1][2]*v.data[2] + data[1][3];
    tmp.data[2] = data[2][0]*v.data[0] + data[2][1]*v.data[1] + data[2][2]*v.data[2] + data[2][3];

    return(tmp);
  }

  public Vector4 transformAsLocation(Vector4 v) {
    Vector4 tmp = new Vector4();

    tmp.data[0] = data[0][0]*v.data[0] + data[0][1]*v.data[1] + data[0][2]*v.data[2] + data[0][3]*v.data[3];
    tmp.data[1] = data[1][0]*v.data[0] + data[1][1]*v.data[1] + data[1][2]*v.data[2] + data[1][3]*v.data[3];
    tmp.data[2] = data[2][0]*v.data[0] + data[2][1]*v.data[1] + data[2][2]*v.data[2] + data[2][3]*v.data[3];
    tmp.data[3] = data[3][0]*v.data[0] + data[3][1]*v.data[1] + data[3][2]*v.data[2] + data[3][3]*v.data[3];

    return(tmp);
  }

  /** Transform a vector as a direction
   * This means that the translation component is not taken into account
   * @param v vector to be transformed
   * @return result of transformation (new object)
   */
  public Vector3 transformAsDirection(Vector3 v) {
    Vector3 tmp = new Vector3();

    tmp.data[0] = data[0][0]*v.data[0] + data[0][1]*v.data[1] + data[0][2]*v.data[2];
    tmp.data[1] = data[1][0]*v.data[0] + data[1][1]*v.data[1] + data[1][2]*v.data[2];
    tmp.data[2] = data[2][0]*v.data[0] + data[2][1]*v.data[1] + data[2][2]*v.data[2];

    return(tmp);
  }
  /** Transform a vector as a normal vector
   * This means that the translation component is not taken into account, and we use the inverse of the rotation component
   * @param v vector to be transformed
   * @return result of transformation (new object)
   */
  public Vector3 transformAsNormal(Vector3 v) {
    Vector3 tmp = new Vector3();

    tmp.data[0] = invdata[0][0]*v.data[0] + invdata[1][0]*v.data[1] + invdata[2][0]*v.data[2];
    tmp.data[1] = invdata[0][1]*v.data[0] + invdata[1][1]*v.data[1] + invdata[2][1]*v.data[2];
    tmp.data[2] = invdata[0][2]*v.data[0] + invdata[1][2]*v.data[1] + invdata[2][2]*v.data[2];

    return(tmp);
  }
  /** This routine produces a matrix, which describes the rotation from XYZ to UVW
   * Watch out: this is not a proper coordinate transform!
   * @return resulting rotation matrix (new object)
   * @param onb orthonormalbasis, to which XYZ is rotated
   */
  public static TransformationMatrix rotateXYZtoUVW(OrthoNormalBasis onb) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = onb.u.data[0]; tmp.data[0][1] = onb.v.data[0]; tmp.data[0][2] = onb.w.data[0]; tmp.data[0][3] = 0.0;
    tmp.data[1][0] = onb.u.data[1]; tmp.data[1][1] = onb.v.data[1]; tmp.data[1][2] = onb.w.data[1]; tmp.data[1][3] = 0.0;
    tmp.data[2][0] = onb.u.data[2]; tmp.data[2][1] = onb.v.data[2]; tmp.data[2][2] = onb.w.data[2]; tmp.data[2][3] = 0.0;
    tmp.data[3][0] = 0.0; tmp.data[3][1] = 0.0; tmp.data[3][2] = 0.0; tmp.data[3][3] = 1.0;

    tmp.invdata[0][0] = onb.u.data[0]; tmp.invdata[0][1] = onb.u.data[1]; tmp.invdata[0][2] = onb.u.data[2]; tmp.invdata[0][3] = 0.0;
    tmp.invdata[1][0] = onb.v.data[0]; tmp.invdata[1][1] = onb.v.data[1]; tmp.invdata[1][2] = onb.v.data[2]; tmp.invdata[1][3] = 0.0;
    tmp.invdata[2][0] = onb.w.data[0]; tmp.invdata[2][1] = onb.w.data[1]; tmp.invdata[2][2] = onb.w.data[2]; tmp.invdata[2][3] = 0.0;
    tmp.invdata[3][0] = 0.0; tmp.invdata[3][1] = 0.0; tmp.invdata[3][2] = 0.0; tmp.invdata[3][3] = 1.0;

    return(tmp);
  }

  /** This routine produces a coordinate transformation matrix. When this matrix is applied
   * to a point, expressed in OXYZ, the result are the coordinates
   * of that point expressed in PUVW.
   * @param fr Frame of reference (target coordinate system)
   * @return resulting trnasformation matrix (new object)
   */
  public static TransformationMatrix coordinateTransformOXYZtoPUVW(FrameOfReference fr) {
    TransformationMatrix result;
    TransformationMatrix rotation;
    TransformationMatrix translation;

    rotation = rotateXYZtoUVW(fr.onb); //rotate XYZ to UVW
    translation = translation(fr.origin); //translates O to P

    result = translation.multiply(rotation); // this matrix transforms basis OXYZ to PUVW
    result.invert(); // the inverse is the coordinate transform

    return(result);

  }

  /** Matrix inverts itself
   */
  public void invert() {
    double [][] tmp;

    tmp = this.data;
    this.data = this.invdata;
    this.invdata = tmp;
  }

  /** Matrix multiplication.
   * result = m1*m2
   * m1 is this
   * m2 is argument matrix
   * @param m 2nd matrix in multiplication
   * @return resulting matrix (new object)
   */
  public TransformationMatrix multiply(TransformationMatrix m) {
    TransformationMatrix tmp = new TransformationMatrix();

    tmp.data[0][0] = this.data[0][0]*m.data[0][0] + this.data[0][1]*m.data[1][0] + this.data[0][2]*m.data[2][0] + this.data[0][3]*m.data[3][0];
    tmp.data[0][1] = this.data[0][0]*m.data[0][1] + this.data[0][1]*m.data[1][1] + this.data[0][2]*m.data[2][1] + this.data[0][3]*m.data[3][1];
    tmp.data[0][2] = this.data[0][0]*m.data[0][2] + this.data[0][1]*m.data[1][2] + this.data[0][2]*m.data[2][2] + this.data[0][3]*m.data[3][2];
    tmp.data[0][3] = this.data[0][0]*m.data[0][3] + this.data[0][1]*m.data[1][3] + this.data[0][2]*m.data[2][3] + this.data[0][3]*m.data[3][3];

    tmp.data[1][0] = this.data[1][0]*m.data[0][0] + this.data[1][1]*m.data[1][0] + this.data[1][2]*m.data[2][0] + this.data[1][3]*m.data[3][0];
    tmp.data[1][1] = this.data[1][0]*m.data[0][1] + this.data[1][1]*m.data[1][1] + this.data[1][2]*m.data[2][1] + this.data[1][3]*m.data[3][1];
    tmp.data[1][2] = this.data[1][0]*m.data[0][2] + this.data[1][1]*m.data[1][2] + this.data[1][2]*m.data[2][2] + this.data[1][3]*m.data[3][2];
    tmp.data[1][3] = this.data[1][0]*m.data[0][3] + this.data[1][1]*m.data[1][3] + this.data[1][2]*m.data[2][3] + this.data[1][3]*m.data[3][3];

    tmp.data[2][0] = this.data[2][0]*m.data[0][0] + this.data[2][1]*m.data[1][0] + this.data[2][2]*m.data[2][0] + this.data[2][3]*m.data[3][0];
    tmp.data[2][1] = this.data[2][0]*m.data[0][1] + this.data[2][1]*m.data[1][1] + this.data[2][2]*m.data[2][1] + this.data[2][3]*m.data[3][1];
    tmp.data[2][2] = this.data[2][0]*m.data[0][2] + this.data[2][1]*m.data[1][2] + this.data[2][2]*m.data[2][2] + this.data[2][3]*m.data[3][2];
    tmp.data[2][3] = this.data[2][0]*m.data[0][3] + this.data[2][1]*m.data[1][3] + this.data[2][2]*m.data[2][3] + this.data[2][3]*m.data[3][3];

    tmp.data[3][0] = this.data[3][0]*m.data[0][0] + this.data[3][1]*m.data[1][0] + this.data[3][2]*m.data[2][0] + this.data[3][3]*m.data[3][0];
    tmp.data[3][1] = this.data[3][0]*m.data[0][1] + this.data[3][1]*m.data[1][1] + this.data[3][2]*m.data[2][1] + this.data[3][3]*m.data[3][1];
    tmp.data[3][2] = this.data[3][0]*m.data[0][2] + this.data[3][1]*m.data[1][2] + this.data[3][2]*m.data[2][2] + this.data[3][3]*m.data[3][2];
    tmp.data[3][3] = this.data[3][0]*m.data[0][3] + this.data[3][1]*m.data[1][3] + this.data[3][2]*m.data[2][3] + this.data[3][3]*m.data[3][3];

    tmp.invdata[0][0] = m.invdata[0][0]*this.invdata[0][0] + m.invdata[0][1]*this.invdata[1][0] + m.invdata[0][2]*this.invdata[2][0] + m.invdata[0][3]*this.invdata[3][0];
    tmp.invdata[0][1] = m.invdata[0][0]*this.invdata[0][1] + m.invdata[0][1]*this.invdata[1][1] + m.invdata[0][2]*this.invdata[2][1] + m.invdata[0][3]*this.invdata[3][1];
    tmp.invdata[0][2] = m.invdata[0][0]*this.invdata[0][2] + m.invdata[0][1]*this.invdata[1][2] + m.invdata[0][2]*this.invdata[2][2] + m.invdata[0][3]*this.invdata[3][2];
    tmp.invdata[0][3] = m.invdata[0][0]*this.invdata[0][3] + m.invdata[0][1]*this.invdata[1][3] + m.invdata[0][2]*this.invdata[2][3] + m.invdata[0][3]*this.invdata[3][3];

    tmp.invdata[1][0] = m.invdata[1][0]*this.invdata[0][0] + m.invdata[1][1]*this.invdata[1][0] + m.invdata[1][2]*this.invdata[2][0] + m.invdata[1][3]*this.invdata[3][0];
    tmp.invdata[1][1] = m.invdata[1][0]*this.invdata[0][1] + m.invdata[1][1]*this.invdata[1][1] + m.invdata[1][2]*this.invdata[2][1] + m.invdata[1][3]*this.invdata[3][1];
    tmp.invdata[1][2] = m.invdata[1][0]*this.invdata[0][2] + m.invdata[1][1]*this.invdata[1][2] + m.invdata[1][2]*this.invdata[2][2] + m.invdata[1][3]*this.invdata[3][2];
    tmp.invdata[1][3] = m.invdata[1][0]*this.invdata[0][3] + m.invdata[1][1]*this.invdata[1][3] + m.invdata[1][2]*this.invdata[2][3] + m.invdata[1][3]*this.invdata[3][3];

    tmp.invdata[2][0] = m.invdata[2][0]*this.invdata[0][0] + m.invdata[2][1]*this.invdata[1][0] + m.invdata[2][2]*this.invdata[2][0] + m.invdata[2][3]*this.invdata[3][0];
    tmp.invdata[2][1] = m.invdata[2][0]*this.invdata[0][1] + m.invdata[2][1]*this.invdata[1][1] + m.invdata[2][2]*this.invdata[2][1] + m.invdata[2][3]*this.invdata[3][1];
    tmp.invdata[2][2] = m.invdata[2][0]*this.invdata[0][2] + m.invdata[2][1]*this.invdata[1][2] + m.invdata[2][2]*this.invdata[2][2] + m.invdata[2][3]*this.invdata[3][2];
    tmp.invdata[2][3] = m.invdata[2][0]*this.invdata[0][3] + m.invdata[2][1]*this.invdata[1][3] + m.invdata[2][2]*this.invdata[2][3] + m.invdata[2][3]*this.invdata[3][3];

    tmp.invdata[3][0] = m.invdata[3][0]*this.invdata[0][0] + m.invdata[3][1]*this.invdata[1][0] + m.invdata[3][2]*this.invdata[2][0] + m.invdata[3][3]*this.invdata[3][0];
    tmp.invdata[3][1] = m.invdata[3][0]*this.invdata[0][1] + m.invdata[3][1]*this.invdata[1][1] + m.invdata[3][2]*this.invdata[2][1] + m.invdata[3][3]*this.invdata[3][1];
    tmp.invdata[3][2] = m.invdata[3][0]*this.invdata[0][2] + m.invdata[3][1]*this.invdata[1][2] + m.invdata[3][2]*this.invdata[2][2] + m.invdata[3][3]*this.invdata[3][2];
    tmp.invdata[3][3] = m.invdata[3][0]*this.invdata[0][3] + m.invdata[3][1]*this.invdata[1][3] + m.invdata[3][2]*this.invdata[2][3] + m.invdata[3][3]*this.invdata[3][3];

    return(tmp);

  }

  static void main(String[] args) {
    Vector3 a,b;
    TransformationMatrix m;
    /*
    OrthoNormalBasis onb =  new OrthoNormalBasis();
    FrameOfReference frame = new FrameOfReference();

    frame.setOrigin(new Vector3(2.0, 3.0, 4.0));
    m = coordinateTransformOXYZtoPUVW(frame);
     */

    m = perspective(1.0);

    a = new Vector3(4.0,4.0,-4.0);
    b = m.transformAsLocation(a);
    System.out.print("Coordinate Transform: ");b.printVector3();


  }


}
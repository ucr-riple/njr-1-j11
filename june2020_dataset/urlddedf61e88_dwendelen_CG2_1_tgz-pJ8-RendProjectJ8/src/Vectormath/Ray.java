package Vectormath;

/** Class Ray describes a line in 3D space, given by one offset point and a direction vector.
 * The parametric equation of the line = origin + t.direction
 */
public class Ray extends Object {
  /** Origin of the ray
   */
  public Vector3 origin;

  /** Direction of the ray - there is no requirement that this vector should be normalized
   */
  public Vector3 direction;

  /** Creates new Ray with origin at the origin of the coordinate system
   * and direction (1, 1, 1)
   */
  public Ray() {
    origin = new Vector3(0.0,0.0,0.0);
    direction = new Vector3(1.0,1.0,1.0);
  }

  /** Cretaes a new Ray with given origin and direction
   * @param origin origin of the ray
   * @param direction direction vector of the ray
   */
  public Ray(Vector3 origin,Vector3 direction) {
    this.origin = origin.makeCopy();
    this.direction = direction.makeCopy();
  }

  /** Compute and return the point at a given t-position along the ray.
   * the resulting point = origin + t.direction
   * The distance between the origin of the ray and the resulting point equals t.|direction|
   * @param t required distance along the ray, measured from the origin, and in units measured in lengths of direction
   * @return resulting point along the ray (new object)
   */
  public Vector3 pointAtParameter(double t) {
    Vector3 tmp2;
    tmp2 = Vector3.linearCombination(this.origin, t , this.direction);
    return(tmp2);
  }

  /** Compute and return the point at a given t-position along the ray.
   * the resulting point = origin + t.direction/|direction|
   * The distance between the origin of the ray and the resulting point equals t.
   * @param t required distance along the ray, measured from the origin, and in normalized units
   * @return resulting point along the ray (new object)
   */
  public Vector3 pointAtParameterNormalizedDirection(double t) {
    Vector3 tmp2;
    tmp2 = Vector3.linearCombination(this.origin, t/this.direction.norm() , this.direction);
    return(tmp2);
  }

  /** Print the fields of the ray
   */
  public void printRay() {
    System.out.print("Ray: ");
    this.origin.printVector3();
    this.direction.printVector3();
  }

  /** Print the fields of the ray, followed by a newline
   */
  public void printlnRay() {
    System.out.print("Ray: ");
    this.origin.printVector3();
    this.direction.printlnVector3();
  }


}
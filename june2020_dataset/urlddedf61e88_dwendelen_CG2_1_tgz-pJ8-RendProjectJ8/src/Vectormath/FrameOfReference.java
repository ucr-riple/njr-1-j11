package Vectormath;

/** Class FrameOfReference describes a complete coordinate system, anchored
 * at a point in the scene.
 *
 * origin is the origin of this coordinate system, expressed in OXYZ coordinates
 * onb is the set of orthonormal basis vectors, expressed in OXYZ coordinates
 */
public class FrameOfReference extends Object {
  
  public OrthoNormalBasis onb;
  public Vector3 origin;

  /** Creates canonical new FrameOfReference
   */
  public FrameOfReference() {
    origin = new Vector3();
    onb = new OrthoNormalBasis();
  }
 /** Creates canonical new FrameOfReference with given origin and basis vectors
   * @param origin anchor point of the new Frame
   * @param onb set of orthonormal basis vectors
   */
  public FrameOfReference(Vector3 origin,OrthoNormalBasis onb) {
    this();
    this.setOrigin(origin);
    this.setOrthoNormalBasis(onb);
  }

  /** Change the origin of the frame
   * @param origin new origin
   */
  public void setOrigin(Vector3 origin) {
    // this is probably not the most clean way to do it, but I don't know of any other
    // way to do it right now
    this.origin = origin.makeCopy();
  }

  /** Change the set of basis vectors
   * @param onb new set of basis vectors
   */
  public void setOrthoNormalBasis(OrthoNormalBasis onb) {
    this.onb = onb.makeCopy();
  }


}
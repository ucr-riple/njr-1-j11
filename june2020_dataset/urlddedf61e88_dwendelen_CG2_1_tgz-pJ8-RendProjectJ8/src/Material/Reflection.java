
 
package Material;

import Vectormath.*;
import Color.*;


public interface Reflection {
  
  /** Evaluate the reflection for a given incoming and outgoing direction.
   * Both directions should be given such that they point away from the
   * surface point.
   *
   * @param inDirection direction from which light is coming
   * @param outDirection direction in which light is leaving
   * @return reflection value
   */
  public RGBColor evaluate(Vector3 inDirection,Vector3 outDirection);
  
  public RGBColor evaluate(Vector3 inDirection, Vector3 normal, Vector3 outDirection);

}

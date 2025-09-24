

package Material;

import Color.*;
import Vectormath.*;


public class DiffuseReflection implements Reflection {

  private RGBColor reflectance; // reflectance has value from 0 to 1

  /** Creates new Diffuse */
  public DiffuseReflection() {
    this.reflectance = new RGBColor(0.5,0.5,0.5);
  }

   public DiffuseReflection(double white) {
    this(white, white, white);
  }
  
  public DiffuseReflection(double red,double green,double blue) {
    this.reflectance = new RGBColor(red,green,blue);
  }

  public RGBColor evaluate(Vector3 inDirection,Vector3 outDirection) {
    RGBColor tmp;

    tmp = this.reflectance.scale(1.0/Math.PI); // we want to return the brdf values

    return(tmp);
  }
  public RGBColor evaluate(Vector3 inDirection,Vector3 normal,Vector3 outDirection) {
    return(evaluate(inDirection,outDirection));
  }
}
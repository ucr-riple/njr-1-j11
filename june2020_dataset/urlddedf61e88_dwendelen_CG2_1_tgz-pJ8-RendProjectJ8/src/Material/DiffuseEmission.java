

package Material;

import Color.*;
import Vectormath.*;

public class DiffuseEmission implements Emission {

  private RGBColor radiance;
  private RGBColor power; // power = radiance*area*PI

  /** Creates new Diffuse */
  public DiffuseEmission(double whitePower) {
    this(whitePower, whitePower, whitePower);
  }

  public DiffuseEmission(double redPower,double greenPower,double bluePower) {
    this.power = new RGBColor(redPower,greenPower,bluePower);
  }

  public void computeRadiance(double area) {
    this.radiance = this.power.makeCopy();
    this.radiance.scaleSelf(1.0/(area*Math.PI));
  }

  public RGBColor evaluate(Ray ray) {
    RGBColor tmp;

    tmp = this.radiance.makeCopy(); // we want to return the brdf values

    return(tmp);
  }

}
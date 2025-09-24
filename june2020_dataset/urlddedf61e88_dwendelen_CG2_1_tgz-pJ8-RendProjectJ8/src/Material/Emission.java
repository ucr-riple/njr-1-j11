
package Material;

import Vectormath.*;
import Color.*;


public interface Emission {
  public RGBColor evaluate (Ray ray);

  public void computeRadiance(double area);
}

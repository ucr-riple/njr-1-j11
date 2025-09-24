

package ScenePack;

import Vectormath.*;
import Geometry.*;
import Mathtools.*;
import Material.*;
import Color.*;
import ImagePack.*;
import Renderers.*;

/** Scene contains all information in order to generate an image
 */
public class Scene extends Object {
  /** PrimitiveGroup contains all the primitives, including the light sources, in the scene.
   */
  public PrimitiveGroup primitiveGroup;
  public Camera defaultCamera;

  /** Creates new Scene
   */
  public Scene() {
    primitiveGroup = new PrimitiveGroup(Settings.MAX_PRIMITIVES_IN_GROUP, Settings.MAX_LIGHTSOURCES_IN_GROUP);
    defaultCamera = null;
  }

  public void printSceneInfo() {
    System.out.println("Scene Characterstics:");
    System.out.println("---------------------");
    System.out.println("  Number of Primitives: " + this.primitiveGroup.numberOfPrimitives());
    System.out.println("  Number of Shadow Casters: " + this.primitiveGroup.numberOfShadowCasters());
    System.out.println("  Number of Shadow Receivers: " + this.primitiveGroup.numberOfShadowReceivers());
    System.out.println("  Number of Light Sources: " + this.primitiveGroup.numberOfLightSources());
  }

  /** Intersect a ray with the primitives in the scene
   * @param ray Ray to be intersected against scene
   * @return contains all information w.r.t. intersection
   */
  public PathNode intersect(Ray ray) {
    PathNode node;
    node = this.intersect(ray, new Interval(Settings.SELF_INTERSECT_EPSILON, Double.POSITIVE_INFINITY));
    return(node);
  }

  /** Intersect a ray with the primitives in the scene
   * @return contains all information w.r.t. intersection
   * @param ray Ray to be intersected against scene
   * @param interval Only points at a distance within this interval are valid
   */
  public PathNode intersect(Ray ray,Interval interval) {
    PathNode node;
    node = this.primitiveGroup.intersect(ray, interval);
    return(node);
  }

  /** Shade a point in the scene.
   * @param rayToShade
   * @param record
   * @return shaded Color
   */
  public RGBColor shade(PathNode node) {
    RGBColor finalColor;
    finalColor = node.shade(this.primitiveGroup, this);
    return (finalColor);
  }

}

package ScenePack;

import Vectormath.*;
import Mathtools.*;
import Geometry.*;
import java.util.*;
import Color.*;


public class PrimitiveGroup extends Vector {
    private Vector primitives;
    
    private int numberOfPrimitives;
    
    private int numberOfShadowCasters;
    
    private int numberOfShadowReceivers;
    
    private Vector lightSources; // this is a quick index to access the light sources in the Vector primitives
    
    private int numberOfLightSources;
    
    private double totalSurfaceArea;
    
  /** Creates new PrimitiveGroup
   * @param expectedNumberOfPrimitives Expected number of Primitives that are needed in the group.
   */
    public PrimitiveGroup(int expectedNumberOfPrimitives, int expectedNumberOfLightSources) {
        primitives = new Vector(expectedNumberOfPrimitives);
        numberOfPrimitives = 0;
        numberOfShadowCasters = 0;
        numberOfShadowReceivers = 0;
        lightSources = new Vector(expectedNumberOfLightSources);
        numberOfLightSources = 0;
        totalSurfaceArea = 0.0;
    }
    
  /**
   * @return number of Primitives in the group, including light sources
   */
    public int numberOfPrimitives() {
        return (this.numberOfPrimitives);
    }
    
  /**
   * @return number of light sources in the group.
   */
    public int numberOfLightSources() {
        return (this.numberOfLightSources);
    }
    
  /**
   * @return number of primitives casting a shadow in the group
   */
    public int numberOfShadowCasters() {
        return (this.numberOfShadowCasters);
    }
    
  /**
   * @return number of primitives receiving shadows in the group
   */
    public int numberOfShadowReceivers() {
        return (this.numberOfShadowReceivers);
    }
    
    public double totalSurfaceArea() {
        return (this.totalSurfaceArea);
    }
    
    public Primitive getPrimitive(int i) {
        return((Primitive)primitives.elementAt(i));
    }
    
    public Primitive getLightSource(int i) {
        return((Primitive)lightSources.elementAt(i));
    }
    
  /** Add a primitive to the group.
   * @param primitive Primitive object to be added
   * @param castsShadow true if primitive should cast shadow, false if not
   * @param receivesShadow true if primitive should receive shadow, false if not
   */
    
    public void addPrimitive(Primitive primitive,boolean castsShadow,boolean receivesShadow) {
        primitive.setCastsShadow(castsShadow);
        primitive.setReceivesShadow(receivesShadow);
        if (castsShadow) this.numberOfShadowCasters ++;
        if (receivesShadow) this.numberOfShadowReceivers ++;
        this.primitives.addElement(primitive);
        this.numberOfPrimitives ++;
        this.totalSurfaceArea += primitive.getArea();
    }
    
  /** Add a light source to the group
   * @param primitive Primitive defining the light source. Make sure it has some emission value.
   */
    public void addLightSource(Primitive primitive) {
        this.addPrimitive(primitive, false, false); //lightsources do not cast nor receive shadow
        this.lightSources.addElement(primitive);
        this.numberOfLightSources ++;
    }
    
  /** Intersect a ray with all primitives in the group.
   * The closest point at a positive distance from the origin of the ray is valid
   * @param ray Ray to intersect primitives with
   * @return IntersectionRecord that contains info about the intersection
   */
    public PathNode intersect(Ray ray) {
        PathNode node;
        node = this.intersect(ray, new Interval(Settings.SELF_INTERSECT_EPSILON, Double.POSITIVE_INFINITY));
        return(node);
    }
    
  /** Intersect a ray with all primitives in the group.
   * The closest point at a distance within the given interval is valid
   * @param ray Ray to intersect primitives with
   * @param Interval distance values which are valid
   * @return IntersectionRecord that contains info about the intersection
   */
    public PathNode intersect(Ray ray,Interval interval) {
        int i;
        boolean hit;
        Interval currentInterval;
        PathNode primNode, finalNode;
        
        finalNode = new PathNode();
        currentInterval = interval.makeCopy();
        
        for (i=0;i < this.numberOfPrimitives();i++) {
            primNode = ((Primitive)primitives.elementAt(i)).intersect(ray, currentInterval);
            if (primNode.hit) {
                finalNode.makeCopyOf(primNode);
                currentInterval.setUpperBound(primNode.distance);
            }
        } // end of i-loop over all primitives
        return(finalNode);
    }
    
    
    
  /** Test intersection with Shadowray.
   * Any intersection point in given interval is valid.
   * @param ray Ray to intersect primitives with
   * @param Interval distance values which are valid
   * @return IntersectionRecord that contains info about the intersection
   */
    public PathNode intersectShadowRay(Ray ray, Interval interval) {
        int                 i, selectedPrim;
        PathNode            primNode, finalNode;
      
        finalNode = new PathNode();
        
        loopPrimitives: for (i=0;i < this.numberOfPrimitives(); i++) {
            
           selectedPrim = i;
            
            if (((Primitive)primitives.elementAt(selectedPrim)).castsShadow()) { //we only test this primitive if it casts a shadow
                primNode = ((Primitive)primitives.elementAt(selectedPrim)).intersect(ray, interval);
                if (primNode.hit) {
                    finalNode.makeCopyOf(primNode);
                    break loopPrimitives; //once we have an intersection, no need to check for other ones (shadow ray)
                }
            } // end of casts shadow test
        } //end of i-loop over all primitives
        return(finalNode);
    }
    
    public PathNode intersectShadowRay(Vector3 start, Vector3 end) {
        Vector3  direction;
        PathNode node;
        double   length;
        
        direction = Vector3.subtract(end,start);
        length = direction.norm();
        node = intersectShadowRay(new Ray(start,direction), new Interval(Settings.SELF_INTERSECT_EPSILON, length-Settings.SELF_INTERSECT_EPSILON));
        return(node);
    }
    
    
    
    
    
    public GeomRecord randomSurfacePoint() {
        GeomRecord geomRecord;
        int primIndex;
        double r;
        Primitive prim;
        
        r = Math.random();
        
        primIndex = 0;
        prim = (Primitive)(this.getPrimitive(primIndex));
        while ( r > (double)prim.getArea()/(double)this.totalSurfaceArea ) {
            r -= (double)prim.getArea()/(double)this.totalSurfaceArea;
            primIndex ++;
            prim = (Primitive)(this.getPrimitive(primIndex));
        }
        geomRecord = (prim).randomSurfacePoint(Math.random(), Math.random());
        geomRecord.probability = 1.0/this.totalSurfaceArea;
        return(geomRecord);
    }
    
    
}
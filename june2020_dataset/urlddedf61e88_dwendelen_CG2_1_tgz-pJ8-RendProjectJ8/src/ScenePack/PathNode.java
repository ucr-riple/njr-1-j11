

package ScenePack;

import Geometry.*;
import Vectormath.*;
import Color.*;
import Mathtools.*;


public class PathNode extends Object {
    
    public boolean hit;
    public Primitive primitive;
    
    public Vector3 point;
    public Vector3 normal;
    public Vector3 direction;
    
    public double probability;
    public double distance;
    
    public PathNode() {
        hit = false;
        primitive = null;
        point = new Vector3();
        normal = new Vector3();
        direction = new Vector3();
        probability  = 1.0;
        distance = 1.0;
    }
    
    public void makeCopyOf(PathNode node) {
        this.hit = node.hit;
        this.primitive = node.primitive;
        this.point = node.point;
        this.normal = node.normal;
        this.direction = node.direction;
        this.probability = node.probability;
        this.distance = node.distance;
    }
    
    public RGBColor shade(PrimitiveGroup pg, Scene scene) {
        RGBColor  finalColor, directColor,indirectColor;
        int       light;
        
        finalColor = new RGBColor(0.0,0.0,0.0);
        
        // self-emission
        if (Settings.SELF_ILLUMINATION) {
            finalColor = this.shadeSelfEmission();
        }
        // direct illumination
        if (Settings.DIRECT_ILLUMINATION) {
            finalColor = RGBColor.add(finalColor,shadeDirect(pg));
        }
        // return final value
        return (finalColor);
    }
    
    
    public RGBColor shadeSelfEmission() {
        RGBColor finalColor = new RGBColor(0.0,0.0,0.0);
        if (this.primitive.isLightSource()) {
            finalColor = this.primitive.evaluateEmission(new Ray(this.point, this.direction));
        }
        return finalColor;
    }
    
    public RGBColor shadeDirect(PrimitiveGroup pg) {
        RGBColor directColor= new RGBColor(0.0,0.0,0.0);
        RGBColor lightColor;
        int light;
        for (light=0; light<pg.numberOfLightSources(); light++) {
            lightColor = this.shadeDirectLightSourceSampling((pg.getLightSource(light)), pg);
            directColor = RGBColor.add(directColor,lightColor);
        } // end loop over all light sources
        return (directColor);
    }
    
    public RGBColor shadeDirectLightSourceSampling(Primitive lightSource, PrimitiveGroup potentialBlockers) {
        GeomRecord          lightRecord;
        RGBColor            finalColor= new RGBColor(0.0,0.0,0.0), sampleColor;
        double              sourcePoint[];
        int                 i;
        Sampler             sourcePointSampler;
        
        //sanity check, return black if given Primitive is not a light source
        if (!lightSource.isLightSource()) return(finalColor);
        
        // init the sampler for generating points on the light source
        sourcePointSampler = Sampler.constructSampler(Settings.LIGHTSOURCE_SAMPLING_PATTERN, Settings.SHADOWRAYS_PER_POINT);
        
        for (i=0;i<Settings.SHADOWRAYS_PER_POINT;i++) { // loop over all shadowrays
            sourcePoint = sourcePointSampler.generateNextRandomPair();
            lightRecord = lightSource.randomSurfacePoint(sourcePoint[0], sourcePoint[1]);
            sampleColor = this.shadeFromPointOnSource(lightRecord, lightSource, potentialBlockers);
            finalColor = RGBColor.linearCombination(finalColor, 1.0/lightRecord.probability, sampleColor);
        } // end i-loop over all shadow rays
        
        finalColor.scaleSelf(1.0/Settings.SHADOWRAYS_PER_POINT); // make sure we take the average over all shadow rays
        return (finalColor); // return the final color as the result of the direct illumination
    }
    
    public RGBColor shadeFromPointOnSource(GeomRecord lightRecord, Primitive lightSource, PrimitiveGroup potentialBlockers) {
    Vector3             lightDirection;
    Ray                 shadowRay;
    PathNode            shadowNode = new PathNode();
    RGBColor            finalColor= new RGBColor(0.0,0.0,0.0), reflectionValue, radianceValue, sampleColor;
    Interval            interval;
    double              G;

    G = FormFactor.G(this.point, this.normal, lightRecord.point, lightRecord.normal);

    if (G > 0.0) {// light contribution is only computed if there is a non-zero contribution due to G-factor

      lightDirection = Vector3.subtract(lightRecord.point, this.point);

      if (this.primitive.receivesShadow()) { //only test blocked shadow ray if this primitive receives shadow
        shadowRay = new Ray(this.point, lightDirection); //construct the shadow ray
        interval = new Interval(Settings.SELF_INTERSECT_EPSILON, lightDirection.norm()-Settings.SELF_INTERSECT_EPSILON); // set the search-interval for intersection
        shadowNode = potentialBlockers.intersectShadowRay(shadowRay, interval);
        }
      // compute actual illumination if:
      //  - the primitive receives no shadow (iow, is always illuminated)
      //  - there was no hit along the shadowray
      //  - we are using false color shadows (in which case we need the shadow filter)
      if (this.primitive.receivesNoShadow() || (!shadowNode.hit)) {
        reflectionValue = this.primitive.evaluateReflection(this.point, lightDirection, this.normal ,this.direction);
        radianceValue = lightSource.evaluateEmission(new Ray(lightRecord.point, lightDirection.scale(-1.0)));
        sampleColor = RGBColor.multiply(reflectionValue, radianceValue);
        finalColor = sampleColor.scale(G);
      }
    } // end test on G factor

    return (finalColor);
  }
}

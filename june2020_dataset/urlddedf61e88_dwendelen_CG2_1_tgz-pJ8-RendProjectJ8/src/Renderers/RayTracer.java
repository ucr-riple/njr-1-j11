
 
package Renderers;

import ScenePack.*;
import ImagePack.*;
import Color.*;
import Vectormath.*;
import Mathtools.*;


public class RayTracer extends Object {

   public static Image generateImage(Scene scene,Camera camera) {

    Ray                 ray,rayToShade;
    int                 i,j,k;
    RGBColor            sampleColor, totalColor;
    PathNode            node = new PathNode();
    Sampler             pixelSampler;
    double[]            samplePair;
    Image               image;

    image = new Image(camera.getXres(), camera.getYres());

    for (i=0;i< camera.getYres();i++) {
      for (j=0;j< camera.getXres();j++) {

        pixelSampler = Sampler.constructSampler(Settings.PIXEL_SAMPLING_PATTERN, Settings.SAMPLES_PER_PIXEL);
        totalColor = new RGBColor(0.0,0.0,0.0);
        for (k=0; k< Settings.SAMPLES_PER_PIXEL;k++) {

          samplePair = pixelSampler.generateNextRandomPair();
          ray = camera.rayAtPixelLocation(j,i,samplePair[0],samplePair[1]);

          if (ray == null) {
            sampleColor = Settings.BACKGROUND_COLOR;
          }
          else {
            node = scene.intersect(ray);
            if (node.hit) {
              if (Vector3.dot(node.normal, ray.direction) < 0.0) {
                sampleColor = scene.shade(node);
              }
              else {
                sampleColor = new RGBColor(0.0,0.0,0.0);
              }
            }
            else {
              sampleColor = Settings.BACKGROUND_COLOR;
            }
          }
          totalColor = RGBColor.add(totalColor,sampleColor);
        } // end k-loop
        totalColor.scaleSelf(1.0/(double)Settings.SAMPLES_PER_PIXEL);
        // now we are ready to attribute the computed color to the pixel
        image.setColorAndDepthOfPixel(j,i,1.0/node.distance,totalColor);
      } // end of j-loop

    if (i%20 == 0) {System.out.println("Scanline "+i+" finished");}

    } // end of i-j loop

    return (image);
  }
 
  
}
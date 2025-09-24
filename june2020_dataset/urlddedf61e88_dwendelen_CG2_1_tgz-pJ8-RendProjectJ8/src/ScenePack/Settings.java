package ScenePack;

import Color.*;
import Mathtools.*;

public class Settings extends Object {

  final public static int IMAGE_WIDTH = 700;
  final public static int IMAGE_HEIGHT = 700;

  final public static RGBColor BACKGROUND_COLOR = new RGBColor(0.0,0.0,0.0); // if no object is found
 
  final public static int MAX_PRIMITIVES_IN_GROUP = 500;
  final public static int MAX_LIGHTSOURCES_IN_GROUP = 101;

  final public static double SELF_INTERSECT_EPSILON = 1e-6;

  // settings for pixel sampling
  final public static int SAMPLES_PER_PIXEL = 1;
  final public static int PIXEL_SAMPLING_PATTERN = Sampler.CENTER; // possible values: see Sampler class

  //general settings for self-emission term
  final public static boolean SELF_ILLUMINATION = true;
  
  // general settings for direct illumination
  final public static boolean DIRECT_ILLUMINATION = true;
  
  // settings for direct illumination using area light source sampling
  final public static int SHADOWRAYS_PER_POINT = 4;
  final public static int LIGHTSOURCE_SAMPLING_PATTERN = Sampler.REGULAR_GRID; // possible values: see Sampler class
 
  static public void printSettings() {
    System.out.println("Settings:");
    System.out.println("---------");
    System.out.println("Image width and size " + IMAGE_WIDTH + " x " + IMAGE_HEIGHT);
    System.out.println("Samples per pixel " + SAMPLES_PER_PIXEL);
    System.out.println("Shadow rays per point " + SHADOWRAYS_PER_POINT );
   System.out.println("---------");
  }

}
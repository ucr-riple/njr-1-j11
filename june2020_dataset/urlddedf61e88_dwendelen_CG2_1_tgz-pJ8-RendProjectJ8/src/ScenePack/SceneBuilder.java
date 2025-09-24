

package ScenePack;

import Vectormath.*;
import Geometry.*;
import Material.*;
import Mathtools.*;
import java.util.*;

public class SceneBuilder extends Object {

  public static void cornellBox(Scene scene) {
    Vector3 a,b,c,d,e,f,g,h;
    Vector3 al, bl, cl, dl;
    Reflection white, red, blue, green, purple, softred, softgreen, softblue, softyellow;
    Reflection nickel, silver;
    Primitive primitive;
    Emission whiteLight, redLight, blueLight;
    Triangle tr;
    int t;
    Interval interval;
    Random r;
    Vector3 ab, bc, cd, da, abcd;
    Rectangle rect;
    Vector collection;
    int i;

    a = new Vector3(-1.0,-1.0,0.0);
    b = new Vector3(1.0,-1.0,0.0);
    c = new Vector3(1.0,-1.0,-2.0);
    d = new Vector3(-1.0,-1.0,-2.0);
    e = new Vector3(-1.0,1.0,0.0);
    f = new Vector3(1.0,1.0,0.0);
    g = new Vector3(1.0,1.0,-2.0);
    h = new Vector3(-1.0,1.0,-2.0);

    white = new DiffuseReflection(0.5, 0.5, 0.5);
    red = new DiffuseReflection(1.0,0.0,0.0);
    green = new DiffuseReflection(0.0,1.0,0.0);
    blue = new DiffuseReflection(0.0,0.0,1.0);

    softred = new DiffuseReflection(0.5, 0.1, 0.1);
    softblue = new DiffuseReflection(0.1, 0.1, 0.5);
    softgreen = new DiffuseReflection(0.1,0.5,0.1);
    softyellow = new DiffuseReflection(.5,.5,.1);

    purple = new DiffuseReflection(0.5, 0.2,0.7);

    // floor
    rect = new Rectangle(a,b,c,d);
    collection = rect.splitRectangle(1,1);
    for (i=0;i<collection.size();i++) {
      primitive = new Primitive((GeometricObject)collection.elementAt(i), white, null);
      addPrimitive(scene, primitive, false, true);
    }

    // back wall
    primitive = new Primitive(new Rectangle(d,c,g,h), white, null);
    addPrimitive(scene, primitive, false, true);
    // ceiling
    primitive = new Primitive(new Rectangle(f,e,h,g), white, null);
    addPrimitive(scene, primitive, false, true);
    // left wall
    primitive = new Primitive(new Rectangle(a,d,h,e), softred, null);
    addPrimitive(scene, primitive, false, true);
    // right wall
    primitive = new Primitive(new Rectangle(c,b,f,g), softblue, null);
    addPrimitive(scene, primitive, false, true);

  
    // one big white light source in the center

    al = new Vector3(0.3,0.99,-0.7);
    bl = new Vector3(0.3,0.99,-1.3);
    cl = new Vector3(-0.3,0.99,-1.3);
    dl = new Vector3(-0.3,0.99,-0.7);
    whiteLight = new DiffuseEmission(80.0, 80.0, 80.0);
    primitive = new Primitive(new Rectangle(al, dl, cl, bl), white, whiteLight);
    addLightSource(scene, primitive);

    // small cube
    a = new Vector3(0.05, -1.0, -0.7);
    b = new Vector3(0.65, -1.0, -0.5);
    c = new Vector3(0.85, -1.0, -1.1);
    d = new Vector3(0.25, -1.0, -1.3);
    e = new Vector3(0.05, -0.4, -0.7);
    f = new Vector3(0.65, -0.4, -0.5 );
    g = new Vector3(0.85, -0.4, -1.1);
    h = new Vector3(0.25, -0.4, -1.3);
    primitive = new Primitive(new Rectangle(a,b,f,e),softyellow,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(b,c,g,f),softyellow,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(f,g,h,e),softyellow,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(d,h,g,c),softyellow,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(d,a,e,h),softyellow,null);
    addPrimitive(scene, primitive, true, true);

    // large cube
    a = new Vector3(-0.85, -1.0, -1.4);
    b = new Vector3(-0.65, -1.0, -0.8);
    c = new Vector3(-0.05, -1.0, -1.0);
    d = new Vector3(-0.25, -1.0, -1.6);
    e = new Vector3(-0.85, 0.2, -1.4);
    f = new Vector3(-0.65, 0.2, -0.8 );
    g = new Vector3(-0.05, 0.2, -1.0);
    h = new Vector3(-0.25, 0.2, -1.6);
    primitive = new Primitive(new Rectangle(a,b,f,e),softgreen,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(b,c,g,f),softgreen,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(f,g,h,e),softgreen,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(d,h,g,c),softgreen,null);
    addPrimitive(scene, primitive, true, true);
    primitive = new Primitive(new Rectangle(d,a,e,h),softgreen,null);
    addPrimitive(scene, primitive, true, true);


    scene.defaultCamera = new Camera(new Vector3(0.0,0.0,2.3), new Vector3(0.0,0.0,-1.0), new Vector3(0.0,1.0,0.0));
    scene.defaultCamera.setProjectionType(Camera.PERSPECTIVE);
    scene.defaultCamera.setScreen(Math.PI/4.0, Math.PI/4.0);
    scene.defaultCamera.setResolution(Settings.IMAGE_WIDTH, Settings.IMAGE_HEIGHT);
  }



  public static void addPrimitive(Scene scene, Primitive primitive, boolean castsShadow, boolean receivesShadow) {
    scene.primitiveGroup.addPrimitive(primitive,castsShadow, receivesShadow);
  }

  public static void addLightSource(Scene scene, Primitive primitive) {
    scene.primitiveGroup.addLightSource(primitive);
    // light sources don't cast shadows nor receive shadows
  }


}
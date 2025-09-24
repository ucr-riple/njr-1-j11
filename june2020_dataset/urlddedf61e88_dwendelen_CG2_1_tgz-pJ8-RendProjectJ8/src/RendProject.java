

import Geometry.*;
import Vectormath.*;
import Material.BlinnPhongReflection;
import Material.DiffuseReflection;
import Material.MaterialFactory;
import Material.ModelReflection;
import Mathtools.*;
import ScenePack.*;
import Color.*;
import ImagePack.*;
import Renderers.*;

public class RendProject extends Object {
    
    public static void main(String[] args) {
        Image               image;
        Scene               scene;
        
        Settings.printSettings();
        
        MaterialFactory mf = new MaterialFactory();
        scene = new Scene();
        
        SceneBuilder.cornellBox(scene);
        
        //======
        //======
        ModelReflection reflections[] = new ModelReflection[3];
        reflections[0] = mf.createASBrass();
        reflections[1] = mf.createASCherry235();
        reflections[2] = mf.createASOrangeBball();
        
        Vector3[] positions = new Vector3[3];
        positions[0] = new Vector3(-0.7, -0.6, -0.6);
        positions[1] = new Vector3(0, -0.8, -0);
        positions[2] = new Vector3(0.4, -0.2, -1);
		//======
        //======
        
        for(int i = 0; i < reflections.length; i++)
        {
        	Sphere sphere = new Sphere(positions[i], 0.2);
        	Primitive p = new Primitive(sphere, reflections[i], null);
        
        	SceneBuilder.addPrimitive(scene, p, true, true);
        }
        
        scene.printSceneInfo();
        
        image = new Image(Settings.IMAGE_WIDTH, Settings.IMAGE_HEIGHT);
        
        System.out.println("Start generating Image");Timer.startTime();
        image = RayTracer.generateImage(scene, scene.defaultCamera);
        System.out.println("Image Complete " + Timer.endTime() + " seconds");
        image.writeImageAsRGBE("picture.rgbe");
        
        Statistics.printStatistics();
    }
    
}
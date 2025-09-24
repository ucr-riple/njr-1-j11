

package Material;


import Color.*;
import Vectormath.*;


abstract public class ModelReflection implements Reflection {
	
	public ModelReflection(DiffuseReflection diffuseReflection, RGBColor specularColour)
	{
		this.diffuseReflection = diffuseReflection;
		this.specularColour = specularColour;
	}
	
	private DiffuseReflection diffuseReflection;
	private RGBColor specularColour;
	
	public RGBColor evaluate(Vector3 inDirection,Vector3 outDirection) {
		throw new RuntimeException("Not implemented");
	}
	
	public RGBColor evaluate(Vector3 inDirection,Vector3 normal,Vector3 outDirection) { 
		
		RGBColor d = this.diffuseReflection.evaluate(inDirection, normal, outDirection);
		RGBColor s = this.specularColour.scale(this.calculateSpecularScale(inDirection, normal, outDirection));
		
		return RGBColor.add(d, s);
	}
	
	public abstract double calculateSpecularScale(Vector3 inDirection,Vector3 normal,Vector3 outDirection);
}
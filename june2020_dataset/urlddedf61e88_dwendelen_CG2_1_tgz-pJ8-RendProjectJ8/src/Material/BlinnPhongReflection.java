

package Material;


import Color.*;
import Vectormath.*;



public class BlinnPhongReflection extends ModelReflection {
	
	public BlinnPhongReflection(
			DiffuseReflection diffuseReflection,
			RGBColor specularColour,
			double p0) {
		super(diffuseReflection, specularColour);
		
		n = p0;
	}

	private double n;
	
	public double calculateSpecularScale(Vector3 inDirection,Vector3 normal,Vector3 outDirection)
	{	
		double cosAngle = Vector3.cosine(inDirection.scale(-1), outDirection);
		double i = (n+2)/(2*Math.PI);
		i = i * Math.pow(cosAngle, this.n);	
		return i;
	}
}
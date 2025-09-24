

package Material;


import javax.management.RuntimeErrorException;

import Vectormath.Vector3;
import Color.*;

public class AshikhminShirleyReflection extends ModelReflection {
	
	public AshikhminShirleyReflection(
			DiffuseReflection diffuseReflection,
			RGBColor specularColour,
			double p0, double p1) {
		super(diffuseReflection, specularColour);
		
		F0 = p0;
		n = p1;
	}
	
	private double F0;
	private double n;
	
	public double calculateSpecularScale(Vector3 inDirection,Vector3 normal,Vector3 outDirection)
	{	
		Vector3 H = Vector3.halfVector(inDirection, outDirection);
		inDirection = inDirection.scale(-1);
		
		double VdotH = Vector3.dot(outDirection, H);
		
		double NdotH = Vector3.dot(normal, H);
		double NdotL = Vector3.dot(normal, inDirection);
		
		double NdotV = Vector3.dot(normal, outDirection);
		double i = (n+1)/(8*Math.PI);
		
		i = i*Math.pow(NdotH, n)/(VdotH * Math.max(NdotL, NdotV));
		i = i*this.fresnel(VdotH);
		return i;
	}
	
	protected double fresnel(double cosO)
	{
		//Schlick's approximation
		return this.F0 + (1-F0)*(1-cosO)*(1-cosO)*(1-cosO)*(1-cosO)*(1-cosO);
	}
}
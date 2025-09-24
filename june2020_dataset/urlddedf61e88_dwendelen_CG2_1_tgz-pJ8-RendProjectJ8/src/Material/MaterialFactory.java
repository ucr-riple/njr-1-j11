package Material;

import Color.RGBColor;

public class MaterialFactory
{
	public ModelReflection createBPBrass()
	{
		return this.createBP(
				0.0382, 0.0272, 0.0119,
				0.0367, 0.015, 0.00537,
				3.16e4);
	}
	
	public ModelReflection createASBrass()
	{
		return this.createAS(
				0.0486, 0.0317, 0.0151,
				0.0717, 0.0289, 0.00967,
				0.999, 4.58e4);
	}
	
	public ModelReflection createBPCherry235()
	{
		return createBP(
				0.0432, 0.0167, 0.00699,
				0.0092, 0.00786, 0.00539,
				82.1);

	}

	public ModelReflection createASCherry235()
	{
		return createAS(
				0.044, 0.0175, 0.00732,
				0.258, 0.224, 0.157,
				0.0558, 84.4);
	}
	
	public ModelReflection createBPOrangeBball()
	{
		return createBP(
				0.325, 0.0469, 0.00486,
				0.00125, 0.00069, 0.000432,
				1.07e+005);

	}

	public ModelReflection createASOrangeBball()
	{
		return createAS(
				0.323, 0.0457, 0.00397,
				0.0398, 0.0229, 0.0155,
				0.14, 4.26e+004);
	}
	
	protected BlinnPhongReflection createBP(double dr, double dg, double db, double sr, double sg, double sb, double p0)
	{
		DiffuseReflection diffuseReflection = new DiffuseReflection(dr, dg, db);
		RGBColor specularColour = new RGBColor(sr, sg, sb);
		BlinnPhongReflection bpr = new BlinnPhongReflection(diffuseReflection, specularColour, p0);
		return bpr;
	}
	
	protected AshikhminShirleyReflection createAS(double dr, double dg, double db, double sr, double sg, double sb, double p0, double p1)
	{
		DiffuseReflection diffuseReflection = new DiffuseReflection(dr, dg, db);
		RGBColor specularColour = new RGBColor(sr, sg, sb);
		AshikhminShirleyReflection asr = new AshikhminShirleyReflection(diffuseReflection, specularColour, p0, p1);
		return asr;
	}
}

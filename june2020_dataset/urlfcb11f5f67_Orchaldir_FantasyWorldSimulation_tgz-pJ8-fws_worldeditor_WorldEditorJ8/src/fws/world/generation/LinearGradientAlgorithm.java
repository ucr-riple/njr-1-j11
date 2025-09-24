package fws.world.generation;

import fws.utility.map.Cell;

public class LinearGradientAlgorithm<T extends Cell> implements GenerationAlgorithm<T>
{
	private float degrees_;
	private float dir_x_;
	private float dir_y_;
	private float radius_;
	
	private float value0_;
	private float value1_;
	private float delta_;
	
	public LinearGradientAlgorithm(float degrees, float radius, float value0, float value1)
	{
		degrees_ = degrees;
		radius_ = radius;
		value0_ = value0;
		value1_ = value1;
	}
	
	@Override
	public void nextSeed()
	{
		
	}

	@Override
	public void update()
	{
		double radians = Math.toRadians(degrees_);
		
		dir_x_ = (float)Math.sin(radians);
		dir_y_ = (float)Math.cos(radians);
		
		delta_ = value1_ - value0_;
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float distance = dir_x_ * x + dir_y_ * y;
		float t = Math.min(Math.max(distance / radius_, 0.0f), 1.0f);
		
		return value0_ + delta_ * t;
	}
}

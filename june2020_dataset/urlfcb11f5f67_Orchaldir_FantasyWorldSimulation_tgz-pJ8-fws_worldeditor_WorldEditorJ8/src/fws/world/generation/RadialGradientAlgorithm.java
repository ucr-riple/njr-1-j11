package fws.world.generation;

import fws.utility.map.Cell;

public class RadialGradientAlgorithm<T extends Cell> implements GenerationAlgorithm<T>
{
	private float pos_x_;
	private float pos_y_;
	private float radius_;
	
	private float value0_;
	private float value1_;
	private float delta_;
	
	public RadialGradientAlgorithm(float pos_x, float pos_y, float radius, float value0, float value1)
	{
		pos_x_ = pos_x;
		pos_y_ = pos_y;
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
		delta_ = value1_ - value0_;
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float delta_x = x - pos_x_;
		float delta_y = y - pos_y_;
		
		float distance = (float)Math.sqrt(delta_x * delta_x + delta_y * delta_y);
		float t = Math.min(Math.max(distance / radius_, 0.0f), 1.0f);
		
		return value0_ + delta_ * t;
	}
}

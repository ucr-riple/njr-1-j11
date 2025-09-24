package fws.world.generation;

import fws.utility.map.Cell;

public class SineAlgorithm<T extends Cell> implements GenerationAlgorithm<T>
{
	private float degrees_;
	private float dir_x_;
	private float dir_y_;
	private float factor_;
	private float offset_;
	
	private float min_;
	private float max_;
	private float mean_;
	private float amplitude_;
	
	public SineAlgorithm(float degrees, float period, float offset, float min, float max)
	{
		degrees_ = degrees;
		factor_ = 2.0f * (float)Math.PI / period;
		offset_ = offset;
		min_ = min;
		max_ = max;
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
		
		amplitude_ = (max_ - min_) / 2.0f;
		mean_ = min_ + amplitude_;
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float distance = dir_x_ * x + dir_y_ * y;
		float t = (float)Math.sin((distance - offset_) * factor_);
		
		return mean_ + amplitude_ * t;
	}
}

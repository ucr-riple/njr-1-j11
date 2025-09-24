package fws.world.generation;

import fws.utility.map.Cell;
import fws.world.WorldData;
import fws.world.WorldGenerationMap;

public class ModifiedByElevationAlgorithm<T extends Cell & WorldData> implements GenerationAlgorithm<T>
{
	private WorldGenerationMap map_;
	private float sea_level_;
	private float remainder_;
	
	private float min_;
	private float max_;
	private float delta_;
	
	public ModifiedByElevationAlgorithm(WorldGenerationMap map, float min, float max)
	{
		map_ = map;
		
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
		sea_level_ = map_.getSeaLevel();
		remainder_ = 1.0f - sea_level_;
		
		delta_ = max_ - min_;
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float t = Math.max((cell.getElevation() - sea_level_) / remainder_, 0.0f);
		
		return min_ + delta_ * t;
	}
}

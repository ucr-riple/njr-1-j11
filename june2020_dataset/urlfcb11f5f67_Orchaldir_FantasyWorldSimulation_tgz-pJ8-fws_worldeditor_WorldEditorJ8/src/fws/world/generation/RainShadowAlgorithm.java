package fws.world.generation;

import fws.utility.map.Cell;
import fws.utility.map.Map;
import fws.world.WorldData;
import fws.world.WorldGenerationCell;
import fws.world.WorldGenerationMap;

public class RainShadowAlgorithm<T extends Cell & WorldData> implements GenerationAlgorithm<T>
{
	private WorldGenerationMap map_;
	private float sea_level_;
	private float remainder_;
	
	private float[][] rainfall_;

	private float min_cost_;
	private float threshold_;
	private float normal_cost_;
	private float recharge_;
	
	public RainShadowAlgorithm(WorldGenerationMap map, float threshold, int distance, int recharge_distance_)
	{
		map_ = map;
		
		threshold_ = threshold;
		normal_cost_ = 1.0f / distance;
		min_cost_ = normal_cost_ / 2.0f;
		
		recharge_ = 1.0f / recharge_distance_;
	}
	
	@Override
	public void nextSeed()
	{
		
	}
	
	private float getInitRainfall(int x, int y)
	{
		return map_.getMap().getCell(x, y).getElevation() > sea_level_ ? 0.0f : 1.0f;
	}

	@Override
	public void update()
	{
		sea_level_ = map_.getSeaLevel();
		remainder_ = 1.0f - sea_level_;
		
		Map<WorldGenerationCell> map = map_.getMap();
		
		int width = map.getWidth();
		int height = map.getHeight();
		
		// init rainfall
		
		rainfall_ = new float[width][height];
		
		for(int y = 0; y < height; y++)
		{
			rainfall_[0][y] = getInitRainfall(0, y);
			rainfall_[width - 1][y] = getInitRainfall(width - 1, y);
		}
		
		for(int x = 0; x < width; x++)
		{
			rainfall_[x][0] = getInitRainfall(x, 0);
			rainfall_[x][height - 1] = getInitRainfall(x, height - 1);
		}
		
		// right & up
		
		for(int y = 1; y < height; y++)
		{
			for(int x = 1; x < width; x++)
			{
				float water_left = rainfall_[x-1][y];
				float water_down = rainfall_[x][y-1];
				float water_diagonal = rainfall_[x-1][y-1];
				float water = Math.max(water_diagonal, Math.max(water_left, water_down));
				
				updateCell(x, y, water);
			}
		}
		
		// left & down
		
		for(int y = height - 2; y >= 0; y--)
		{
			for(int x = width - 2; x >= 0; x--)
			{
				float water_right = rainfall_[x+1][y];
				float water_up = rainfall_[x][y+1];
				float water_diagonal = rainfall_[x+1][y+1];
				float water = Math.max(water_diagonal, Math.max(water_right, water_up));
				
				updateCell(x, y, water);
			}
		}
	}
	
	public void updateCell(int x, int y, float old_water)
	{
		WorldGenerationCell cell = map_.getMap().getCell(x, y);
		float elevation = cell.getElevation();
		float rainfall;

		if(elevation > sea_level_)
		{
			float t = (elevation - sea_level_) / remainder_;
			
			if(old_water > threshold_)
			{
				rainfall = Math.max(old_water - normal_cost_ * (1.0f + t), 0.0f);
			}
			else
			{
				rainfall = Math.max(old_water - min_cost_, 0.0f);
			}
		}
		else
		{
			rainfall = Math.min(old_water + recharge_, 1.0f);
		}
		
		rainfall_[x][y] = Math.max(rainfall, rainfall_[x][y]);
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		int cx = map_.getMap().getColumn(cell.getId());
		int cy = map_.getMap().getRow(cell.getId());
		
		return rainfall_[cx][cy];
	}
}

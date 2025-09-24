package fws.world.generation;

import fws.utility.map.Cell;
import fws.utility.map.Map;
import fws.world.*;

public class PlateTectonicsAlgorithm<T extends Cell & WorldData> implements GenerationAlgorithm<T>
{
	private PlateTectonicsMap tectonics_map_;
	
	public PlateTectonicsAlgorithm(PlateTectonicsMap tectonics_map)
	{
		tectonics_map_ = tectonics_map;
	}
	
	@Override
	public void nextSeed()
	{
		
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float tectonics_x = x / tectonics_map_.getCellSize();
		float tectonics_y = y / tectonics_map_.getCellSize();
		int cell_x = (int)tectonics_x;
		int cell_y = (int)tectonics_y;
		
		float rest_x = tectonics_x - (float)Math.floor(tectonics_x);
		float rest_y = tectonics_y - (float)Math.floor(tectonics_y);
		
		// get elevations
		
		float value00; // left & down
		float value10; // right & down
		float value01; // left & up
		float value11; // right & up
		float factor_x, factor_y;
		
		if(rest_x <= 0.5f && rest_y <= 0.5f)
		{
			// case left & down
			
			factor_x = rest_x + 0.5f;
			factor_y = rest_y + 0.5f;
			
			value00 = tectonics_map_.getElevation(cell_x-1, cell_y-1);
			value10 = tectonics_map_.getElevation(cell_x, cell_y-1);
			value01 = tectonics_map_.getElevation(cell_x-1, cell_y);
			value11 = tectonics_map_.getElevation(cell_x, cell_y);
		}
		else if(rest_x > 0.5f && rest_y <= 0.5f)
		{
			// case right & down
			
			factor_x = rest_x - 0.5f;
			factor_y = rest_y + 0.5f;
			
			value00 = tectonics_map_.getElevation(cell_x, cell_y-1);
			value10 = tectonics_map_.getElevation(cell_x+1, cell_y-1);
			value01 = tectonics_map_.getElevation(cell_x, cell_y);
			value11 = tectonics_map_.getElevation(cell_x+1, cell_y);
		}
		else if(rest_x <= 0.5f && rest_y > 0.5f)
		{
			// case left & up
			
			factor_x = rest_x + 0.5f;
			factor_y = rest_y - 0.5f;
			
			value00 = tectonics_map_.getElevation(cell_x-1, cell_y);
			value10 = tectonics_map_.getElevation(cell_x, cell_y);
			value01 = tectonics_map_.getElevation(cell_x-1, cell_y+1);
			value11 = tectonics_map_.getElevation(cell_x, cell_y+1);
		}
		else
		{
			// case right & up
			
			factor_x = rest_x - 0.5f;
			factor_y = rest_y - 0.5f;
			
			value00 = tectonics_map_.getElevation(cell_x, cell_y);
			value10 = tectonics_map_.getElevation(cell_x+1, cell_y);
			value01 = tectonics_map_.getElevation(cell_x, cell_y+1);
			value11 = tectonics_map_.getElevation(cell_x+1, cell_y+1);
		}
		
		// interpolation
		
		return interpolateBilinear(value00, value10, value01, value11, factor_x, factor_y);
	}
	
	public float interpolateBilinear(float v00, float v10, float v01, float v11, float x, float y)
	{
		float value0 = v00 * (1.0f - x) + v10 * x;
		float value1 = v01 * (1.0f - x) + v11 * x;
		
		return value0 * (1.0f - y) + value1 * y;
	}
	
	public float interpolateWithDistance(float v00, float v10, float v01, float v11, float x, float y)
	{
		float rest_x = 1.0f - x;
		float rest_y = 1.0f - y;
		
		float x_2 = x * x;
		float y_2 = y * y;
		float rest_x_2 = rest_x * rest_x;
		float rest_y_2 = rest_y * rest_y;
		
		// distances
		
		float distance00 = (float)Math.sqrt(x_2 + y_2);
		float distance10 = (float)Math.sqrt(rest_x_2 + y_2);
		float distance01 = (float)Math.sqrt(x_2 + rest_y_2);
		float distance11 = (float)Math.sqrt(rest_x_2 + rest_y_2);
		
		// weights
		
		float w00 = Math.max(1.0f - distance00, 0.0f);
		float w10 = Math.max(1.0f - distance10, 0.0f);
		float w01 = Math.max(1.0f - distance01, 0.0f);
		float w11 = Math.max(1.0f - distance11, 0.0f);
		
		float total_weight = w00 + w10 + w01 + w11;
		
		return (w00 * v00 + w10 * v10 + w01 * v01 + w11 * v11) / total_weight;
	}

}

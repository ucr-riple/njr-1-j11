package fws.world;

import fws.utility.map.Cell;
import fws.utility.map.ColorSelector;
import static org.lwjgl.opengl.GL11.glColor3f;

public class ColorLandAndWater<T extends Cell & WorldData> implements ColorSelector<T>
{
	private WorldGenerationMap map_;
	
	public ColorLandAndWater(WorldGenerationMap map)
	{
		map_ = map;
	}
	
	@Override
	public void selectColor(T cell)
	{
		float sea_level = map_.getSeaLevel();
		float elevation = cell.getElevation();
		
		if(elevation >= sea_level)
		{
			float value = (elevation - sea_level) / (1.0f - sea_level);
			glColor3f(value, 1.0f, value);
		}
		else
		{
			float value = elevation / sea_level;
			glColor3f(0.0f, 0.0f, value);
		}
	}
}

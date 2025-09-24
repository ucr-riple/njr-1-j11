package fws.world;

import fws.utility.map.Cell;
import fws.utility.map.ColorSelector;
import static org.lwjgl.opengl.GL11.glColor3f;

public class ColorElevation<T extends Cell & WorldData> implements ColorSelector<T>
{
	@Override
	public void selectColor(T cell)
	{
		float elevation = cell.getElevation();
		
		glColor3f(elevation, elevation, elevation);
	}
}

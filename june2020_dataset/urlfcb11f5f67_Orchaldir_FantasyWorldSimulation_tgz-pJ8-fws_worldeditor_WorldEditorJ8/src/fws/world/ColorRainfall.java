package fws.world;

import fws.utility.map.Cell;
import fws.utility.map.ColorSelector;
import static org.lwjgl.opengl.GL11.glColor3f;

public class ColorRainfall<T extends Cell & WorldData> implements ColorSelector<T>
{
	public static final float MAX_HUE = 2.0f / 3.0f;
	
	@Override
	public void selectColor(T cell)
	{
		float rainfall = cell.getRainfall();
		
		//glColor3f(1.0f - rainfall, 1.0f - rainfall, 1.0f);
		glColor3f(0.0f, 0.0f, rainfall);
	}
}

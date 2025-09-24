package fws.world;

import fws.utility.map.Cell;
import fws.utility.map.ColorSelector;
import java.awt.Color;
import static org.lwjgl.opengl.GL11.glColor3f;

public class ColorTemperature<T extends Cell & WorldData> implements ColorSelector<T>
{
	public static final float MAX_HUE = 2.0f / 3.0f;
	
	@Override
	public void selectColor(T cell)
	{
		float temperature = cell.getTemperature();
		float hue = MAX_HUE - temperature * MAX_HUE;
		float saturation = 0.9f;
		float luminance = 1.0f;
		
		Color color = Color.getHSBColor(hue, saturation, luminance);

		glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
	}
}

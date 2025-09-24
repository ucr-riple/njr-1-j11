package fws.utility.map;

import java.awt.Color;
import java.util.Random;
import static org.lwjgl.opengl.GL11.*;

public class RandomColorSelector<T extends Cell> implements ColorSelector<T>
{
	private Random random_ = new Random();

	@Override
	public void selectColor(T cell)
	{
		float hue = random_.nextFloat();
		float saturation = 0.9f;
		float luminance = 1.0f;
		
		Color color = Color.getHSBColor(hue, saturation, luminance);

		glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
	}

}

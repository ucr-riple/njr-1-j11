package fws.utility.map;

import static org.lwjgl.opengl.GL11.*;

public class ColorRenderer<T extends Cell>
{
	private Map<T> map_;
	
	private float cell_size_;
	private float cell_border_;
	
	private ColorSelector<T> selector_;
	
	public ColorRenderer(Map<T> map, float cell_size, float cell_border, ColorSelector<T> selector)
	{
		map_ = map;
		
		cell_size_ = cell_size;
		cell_border_ = cell_border;
		
		selector_ = selector;
	}
	
	public ColorRenderer(Map<T> map, float cell_size, ColorSelector<T> selector)
	{
		this(map, cell_size, 0.0f, selector);
	}
	
	public float getCellSize()
	{
		return cell_size_;
	}
	
	public void setSelector(ColorSelector<T> selector)
	{
		selector_ = selector;
	}
	
	public T getCell(int pixel_x, int pixel_y)
	{
		return map_.getCell(pixel_x / cell_size_, pixel_y / cell_size_);
	}
	
	public void render()
	{
		glScalef(cell_size_, cell_size_, cell_size_);
		
		map_.prepareRendering(cell_border_ / cell_size_);
		
		for(int index = 0; index < map_.getNumberOfCells(); index++)
		{
			selector_.selectColor(map_.getCell(index));
			map_.renderCell(index);
		}
	}
}

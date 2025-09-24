package fws.world;

import fws.utility.map.*;

public class PlateTectonicsMap
{
	private Map<PlateTectonicsCell> map_;
	private PlateType default_type_;
	private int cell_size_;
	
	public PlateTectonicsMap(int width, int height, int cell_size, PlateType type)
	{
		PlateTectonicsCell[] cells = new PlateTectonicsCell[width*height];
		
		for(int index = 0; index < cells.length; index++)
		{
			cells[index] = new PlateTectonicsCell(index, type);
		}
		
		map_ = new SquareMap(width, height, cells);
		cell_size_ = cell_size;
		default_type_ = type;
	}
	
	public void clear()
	{
		for(int index = 0; index < map_.getNumberOfCells(); index++)
		{
			map_.getCell(index).type_ = default_type_;
		}
	}
	
	public Map<PlateTectonicsCell> getMap()
	{
		return map_;
	}
	
	public PlateType getDefaultType()
	{
		return default_type_;
	}

	public int getCellSize()
	{
		return cell_size_;
	}
	
	public PlateType getType(int index)
	{
		PlateTectonicsCell cell = map_.getCell(index);
		
		if(cell == null)
			return default_type_;
		
		return cell.type_;
	}
	
	public PlateType getType(int x, int y)
	{
		return getType(map_.getIndex(x, y));
	}
	
	public float getElevation(int x, int y)
	{
		return getType(x, y).getElevation();
	}
	
	public WorldGenerationMap createWorldGenerationMap(MapType type)
	{
		int width = map_.getWidth() * cell_size_;
		int height = map_.getHeight() * cell_size_;
		
		return new WorldGenerationMap(type, width, height);
	}
}

package fws.utility.map;

public abstract class Map<T extends Cell>
{
	protected final int width_;
	protected final int height_;
	private T[] cells_;
	
	public Map(int width, int height, T[] cells)
	{
		width_ = width;
		height_ = height;
		cells_ = cells;
	}
	
	public int getWidth()
	{
		return width_;
	}
	
	public int getHeight()
	{
		return height_;
	}
	
	// index
	
	public int getColumn(int index)
	{
		return index % width_;
	}
	
	public int getRow(int index)
	{
		return index / width_;
	}
	
	public int getIndex(int column, int row)
	{
		if(column < 0 || column >= width_ || row < 0 || row >= height_)
			return -1;
		
		return column + row * width_;
	}
	
	// cells

	public int getNumberOfCells()
	{
		return cells_.length;
	}

	public T getCell(int index)
	{
		if(index < 0 || index >= cells_.length)
			return null;
		
		return cells_[index];
	}
	
	public T getCell(int column, int row)
	{
		return getCell(getIndex(column, row));
	}
	
	public abstract T getCell(float x, float y);
	
	// neighbors
	
	public abstract int getNumberOfNeighbors();
	
	public T getNeighbor(T cell, int dir)
	{
		return getNeighbor(cell.getId(), dir);
	}
	
	public abstract T getNeighbor(int index, int dir);
	
	// diagonal neighbors
	
	public abstract int getNumberOfDiagonalNeighbors();
	
	public T getDiagonalNeighbor(T cell, int dir)
	{
		return getDiagonalNeighbor(cell.getId(), dir);
	}
	
	public abstract T getDiagonalNeighbor(int index, int dir);
	
	// rendering
	
	abstract void prepareRendering(float cell_border);
	abstract void renderCell(int index);
}

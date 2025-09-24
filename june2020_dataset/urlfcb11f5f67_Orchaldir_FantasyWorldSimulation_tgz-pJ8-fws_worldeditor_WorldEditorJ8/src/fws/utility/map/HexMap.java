package fws.utility.map;

import static org.lwjgl.opengl.GL11.*;

public class HexMap<T extends Cell> extends Map<T>
{
	public static final float CELL_WIDTH = 1.0f;
	public static final float CELL_WIDTH_1_2 = CELL_WIDTH * 0.5f;
	
	public static final float CELL_HEIGHT = (float) (CELL_WIDTH * 2.0f / Math.sqrt(3.0f));
	public static final float CELL_HEIGHT_1_4 = CELL_HEIGHT * 0.25f;
	public static final float CELL_HEIGHT_3_4 = CELL_HEIGHT * 0.75f;
	
	private float cell_width_border_;
	private float cell_height_border_;
	private float cell_height_3_4_border_;
	
	public HexMap(int width, int height, T[] cells)
	{
		super(width, height, cells);
	}
	
	float getRowStart(int row)
	{
		return (row % 2) * CELL_WIDTH_1_2;
	}
	
	// cells
	
	@Override
	public T getCell(float x, float y)
	{
		int row = (int) (y * CELL_HEIGHT);
		int column = (int) (x * CELL_WIDTH - getRowStart(row));
		
		return getCell(column, row);
	}
	
	// neighbors
	
	@Override
	public int getNumberOfNeighbors()
	{
		return 6;
	}

	@Override
	public T getNeighbor(int index, int dir)
	{
		if(dir < 0 || dir >= getNumberOfNeighbors())
			return null;
		
		return null;
	}
	
	// diagonal neighbors
	
	@Override
	public int getNumberOfDiagonalNeighbors()
	{
		return 0;
	}

	@Override
	public T getDiagonalNeighbor(int index, int dir)
	{
		return null;
	}
	
	// rendering
	
	@Override
	void prepareRendering(float cell_border)
	{
		cell_width_border_ = CELL_WIDTH - cell_border;
		cell_height_border_ = CELL_HEIGHT - cell_border;
		cell_height_3_4_border_ = CELL_HEIGHT_3_4 - cell_border;
	}

	@Override
	void renderCell(int index)
	{
		int column = getColumn(index);
		int row = getRow(index);
		
		// x values
		
		float x0 = column * CELL_WIDTH + getRowStart(row);
		float x1 = x0 + CELL_WIDTH_1_2;
		float x2 = x0 + cell_width_border_;
		
		// y values
		
		float y0 = row * CELL_HEIGHT_3_4;
		float y1 = y0 + CELL_HEIGHT_1_4;
		float y2 = y0 + cell_height_3_4_border_;
		float y3 = y0 + cell_height_border_;
		
		// draw the hexagon
		
		glBegin(GL_POLYGON);
		
		glVertex2f(x1, y0);
		glVertex2f(x2, y1);
		glVertex2f(x2, y2);
		glVertex2f(x1, y3);
		glVertex2f(x0, y2);
		glVertex2f(x0, y1);
		
		glEnd();
	}
}

package fws.world.continent;

import fws.world.*;
import java.util.*;

public class GrowingAlgorithm
{
	private PlateTectonicsMap tectonics_map_;
	private PlateType type_;
	private List<Integer> continents_ = new ArrayList<Integer>();
	
	private LinkedList<Integer> free_cells_ = new LinkedList<Integer>();
	private LinkedList<Integer> border_ = new LinkedList<Integer>();
	private LinkedList<Integer> diagonal_border_ = new LinkedList<Integer>();
	
	private Random random_ = new Random(19);
	
	public GrowingAlgorithm(PlateTectonicsMap tectonics_map, PlateType type)
	{
		tectonics_map_ = tectonics_map;
		
		type_ = type;
		
		continents_.add(10);
		continents_.add(6);
	}
	
	public void clear()
	{
		free_cells_.clear();
		
		PlateType default_type = tectonics_map_.getDefaultType();
		
		for(int i = 0; i < tectonics_map_.getMap().getNumberOfCells(); i++)
		{
			if(tectonics_map_.getType(i) == default_type)
				free_cells_.add(i);
		}
	}
	
	public void update()
	{
		clear();
		
		for(int size : continents_)
		{
			growContinent(size);
		}
	}
	
	public void growContinent(int size)
	{
		border_.clear();
		diagonal_border_.clear();
		
		PlateTectonicsCell start_cell = getStartCell();
		start_cell.type_ = type_;
		addNeighbors(start_cell);
		
		for(int i = 0; i < size; i++)
		{
			if(border_.isEmpty())
				return;
			
			int list_index = random_.nextInt(border_.size());
			int index = border_.remove(list_index);
			PlateTectonicsCell cell = tectonics_map_.getMap().getCell(index);
			
			cell.type_ = type_;
			addNeighbors(cell);
		}
	}
	
	private PlateTectonicsCell getStartCell()
	{
		int list_index = random_.nextInt(free_cells_.size());
		int index = free_cells_.remove(list_index);
		
		return tectonics_map_.getMap().getCell(index);
	}
	
	private void addNeighbors(PlateTectonicsCell cell)
	{
		addNeighbor(cell, 0);
		addNeighbor(cell, 1);
		addNeighbor(cell, 2);
		addNeighbor(cell, 3);
		
		addDiagonalNeighbor(cell, 0);
		addDiagonalNeighbor(cell, 1);
		addDiagonalNeighbor(cell, 2);
		addDiagonalNeighbor(cell, 3);
	}
	
	private void addNeighbor(PlateTectonicsCell cell, int dir)
	{
		PlateTectonicsCell neighbor = tectonics_map_.getMap().getNeighbor(cell, dir);
		
		if(neighbor == null)
			return;
		
		int neighbor_index = neighbor.getId();
		
		if(!free_cells_.contains(neighbor_index))
		{
			if(diagonal_border_.removeFirstOccurrence(neighbor_index))
			{
				addToBorder(border_, neighbor_index);
			}
			
			return;
		}
		
		free_cells_.removeFirstOccurrence(neighbor_index);
		
		addToBorder(border_, neighbor_index);
	}
	
	private void addDiagonalNeighbor(PlateTectonicsCell cell, int dir)
	{
		PlateTectonicsCell neighbor = tectonics_map_.getMap().getDiagonalNeighbor(cell, dir);
		
		if(neighbor == null)
			return;
		
		int neighbor_index = neighbor.getId();
		
		if(!free_cells_.contains(neighbor_index))
			return;
		
		free_cells_.removeFirstOccurrence(neighbor_index);
		
		addToBorder(diagonal_border_, neighbor_index);
	}
	
	private void addToBorder(LinkedList<Integer> border, int cell)
	{
		int pos = 0;
		
		for(int border_index : border)
		{
			if(border_index == cell)
				return;
			else if(border_index > cell)
				break;
			
			pos++;
		}
		
		border.add(pos, cell);
	}
}

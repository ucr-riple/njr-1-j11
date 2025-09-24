package solver.structures;

import java.util.ArrayList;
import java.util.List;

import solver.exceptions.InvalidSolutionException;

public abstract class SudokuRowCol {
	protected int SIZE=9;
	protected ArrayList<Cell> cells;

	public SudokuRowCol(int size){
		cells = new ArrayList<Cell>();
		for(int i=0; i<size;i++)
			cells.add(new Cell(0,0));
		this.SIZE=size;
	}

	public boolean contains(int n) {
		for (Cell cell : cells) {
			if (cell.is(n))return true;
		}
		return false;
	}

	public void setCell(int i, Cell cell) {
		this.cells.set(i, cell);		
	}

	public Cell getCell(int r) {
		return this.cells.get(r);
	}

	public void removeCandidate(Integer value) {
		for (Cell cell : cells) {
			if (cell.getCandidates().contains(value)){
				cell.getCandidates().remove(value);				
			}
		}
	}
	public void removeCandidateExceptBlock(Integer value, int block) {
		for (Cell cell : cells) {
			if (cell.getCandidates().contains(value) && cell.getBlock()!=block){
				cell.getCandidates().remove(value);				
			}
		}
	}

	public void checkSolution() throws InvalidSolutionException  {
		//TODO:
	}

	public List<Cell> getCells() {
		return cells;
	}

	
	
}

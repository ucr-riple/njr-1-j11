package solver.structures;

import solver.Sudoku;


public class SudokuBlock extends Sudoku{
//	private int SIZE = 3;
	
	public SudokuBlock() {
		super();
		this.SIZE=3;
	}
	
	@Override
	public SudokuBlock getBlock(int i){
		return this;
	}
	
	@Override
	public SudokuBlock getBlock(int r, int c) {
		return this;
	}

	
	public void removeCandidate(Integer value) {
		for (int r=0;r<this.SIZE;r++){
			for (int c=0;c<this.SIZE;c++){
				if (getCell(r, c).getCandidates().contains(value))
					getCell(r, c).getCandidates().remove(value);
			}
		}
		
	}

	
}

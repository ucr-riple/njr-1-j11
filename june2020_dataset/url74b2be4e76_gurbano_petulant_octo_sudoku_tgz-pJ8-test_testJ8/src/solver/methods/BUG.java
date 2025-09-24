package solver.methods;

import java.util.List;

import solver.SolveStep;
import solver.Sudoku;
import solver.structures.Cell;
import solver.structures.SudokuBlock;
import solver.structures.SudokuRowCol;

public class BUG implements SolveMethod {

	@Override
	public void apply(Sudoku s, SolveStep ss) {
		s.solveCell(ss.cell,ss.value);		
	}

	@Override
	public void printInfo(SolveStep solveStep) {
		System.out.println(String.format("Cell [%d,%d] can be solved by method %s.",
				solveStep.cell.getRow(),solveStep.cell.getCol(), this.getName()));
		System.out.println(String.format("Value is %d.",solveStep.value));		
	}

	@Override
	public String getName() {
		return "BUG";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		Cell threeCell = null;
		boolean foundBug = true;
		boolean foundThree = false;
		List<Cell> cells = s.getCellList();
		for (Cell cell : cells) {
			if (!cell.isSolved()){
				if (cell.getCandidates().size()<2 || cell.getCandidates().size()>3){
					foundBug=false;
					return null;
				}
				if (cell.getCandidates().size()==3 && foundThree){
					foundBug=false;
					return null;
				}
				if (cell.getCandidates().size()==3 && !foundThree){
					foundThree = true;
					threeCell = cell;
				}
				
			}
		}
		/*There's only one cell with three candidates and the all the others contains two candidates*/
		List<Integer> candidates = threeCell.getCandidates();
		/*Look for a candidates that affects a cell in the row, a cell in the column and a cell in the box*/
		for (Integer integer : candidates) {
			boolean affectRow = false;
			boolean affectCol = false;
			boolean affectBox = false;
			SudokuRowCol row = s.getRow(threeCell.getRow());
			SudokuRowCol col = s.getCol(threeCell.getCol());
			SudokuBlock box = s.getBlock(threeCell.getBlock());
			
			for (Cell cell : row.getCells()) {
				if (cell!=threeCell && cell.getCandidates().contains(integer) && threeCell.getBlock()!=cell.getBlock())
					affectRow = true;						
			}
			for (Cell cell : col.getCells()) {
				if (cell!=threeCell && cell.getCandidates().contains(integer) && threeCell.getBlock()!=cell.getBlock())
					affectCol = true;						
			}
			for (Cell cell : box.getCellList()) {
				if (cell!=threeCell && cell.getCandidates().contains(integer))
					affectBox = true;						
			}
			
			if(affectRow&&affectCol&&affectBox){
				SolveStep ss = new SolveStep();
				ss.setMethod(this);					
				ss.setCell(threeCell);
				ss.setValue(integer);			
				return ss;
				
			}
			
		}
		
		
		return null;
	}

}

package solver.methods;

import java.util.LinkedList;
import java.util.List;

import solver.SolveStep;
import solver.Sudoku;
import solver.structures.Cell;
import solver.structures.SudokuBlock;
import solver.structures.SudokuRow;
import solver.structures.SudokuRowCol;

/**
 * 
 * @author urbangi
 *
 */
public class NakedSingle implements SolveMethod{
	
	
	
	@Override
	public String getName() {
		return "Naked Single";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		for (int r=0; r<9;r++){//rows
			for (int c=0; c<9;c++){//cols
				if (!s.getCell(r, c).isSolved()){
					if (s.getCell(r, c).getCandidates().size()==1){
						SolveStep ss = new SolveStep();
						ss.setMethod(this);					
						ss.setCell(s.getCell(r, c));
						ss.setValue(s.getCell(r, c).getCandidates().get(0));			
						return ss;
					}
				}
			}
		}	
		return null;
	}

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

	

	

}

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
 * If a cell is the only one in a col that can take a particular value, 
 * then it must have that value.
 * @author urbangi
 *
 */
public class HiddenSingleCol implements SolveMethod{
	
	private class Freq{
		public List<Cell> cells= new LinkedList<Cell>();
		public void add(Cell cell) {
			cells.add(cell);			
		}				
	}
	
	@Override
	public String getName() {
		return "Hidden single col";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		for (int x=0; x<9;x++){//SCORRO OGNI RIGA
			SudokuRowCol sb = s.getCol(x);
			Freq[] freq = new Freq[10]; 
			for (int i = 0; i < freq.length; i++) freq[i]= new Freq();
			for (int r=0; r<9;r++){				
					if (!sb.getCell(r).isSolved()){
						List<Integer> candidates =sb.getCell(r).getCandidates();
						for (Integer integer : candidates) {
							freq[integer.intValue()].add(sb.getCell(r));
						}
					}
				
			}
			for (int i = 1; i < freq.length; i++) {
				if (freq[i].cells.size()==1){
					SolveStep ss = new SolveStep();
					ss.setMethod(this);					
					ss.setCell(freq[i].cells.get(0));
					ss.setValue(i);				
					
					return ss;
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

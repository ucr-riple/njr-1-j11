package solver.methods;

import java.util.LinkedList;
import java.util.List;

import solver.SolveStep;
import solver.Sudoku;
import solver.structures.Cell;
import solver.structures.SudokuBlock;

/**
 * If a cell is the only one in a block that can take a particular value, 
 * then it must have that value.
 * @author urbangi
 *
 */
public class HiddenSingleBlock implements SolveMethod{
	
	private class Freq{
		public List<Cell> cells= new LinkedList<Cell>();

		public void add(Cell cell) {
			cells.add(cell);			
		}		
		
	}
	
	@Override
	public String getName() {
		return "Hidden single block";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		for (int x=0; x<9;x++){//SCORRO OGNI BLOCCO
			SudokuBlock sb = s.getBlock(x);
			Freq[] freq = new Freq[10]; 
			for (int i = 0; i < freq.length; i++) freq[i]= new Freq();
			for (int r=0; r<sb.getSize();r++){
				for (int c=0; c<sb.getSize();c++){
					if (!sb.getCell(r, c).isSolved()){
						List<Integer> candidates =sb.getCell(r, c).getCandidates();
						for (Integer integer : candidates) {
							freq[integer.intValue()].add(sb.getCell(r, c));
						}
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

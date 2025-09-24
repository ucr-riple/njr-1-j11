package solver.methods.naked.n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solver.SolveStep;
import solver.Sudoku;
import solver.methods.SolveMethod;
import solver.methods.naked.pair.NakedPairCol;
import solver.methods.naked.sets.Couple;
import solver.structures.Cell;

public class NakedNCol extends NakedPairCol implements SolveMethod{

	


	@Override
	public String getName() {
		return "Naked Pair (Row) (1.1)";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		/*Compute all possible couple*/
		List<Couple> allCouple = new ArrayList<Couple>();
		for (int a=1;a<=9; a++){
			for (int b=1;b<=9; b++){
				if (a!=b){
					Couple c = new Couple(a, b);
					allCouple.add(c);
				}
			}
		}

		/*FOR EACH COL*/

		for(int col=0; col<s.getSize(); col++){
			/*If a couple it's in EXACTLY two cells of the col and both candidates are not in other cells, there is a naked pair */
			for (Couple couple : allCouple) {
				List<Cell> nakedCells = new ArrayList<Cell>();
				for (int c=0; c<s.getSize();c++){/*Cicle through the col*/
					Cell tmp = s.getCell(c,col);
					if (!tmp.isSolved() && couple.isIn(tmp.getCandidates())){
						nakedCells.add(tmp);
					}
				}
				/*Check if there is a naked set:
				 * Couple must be in extactly two cells and candidates must NOT be present elsewhere*/
				if (nakedCells.size()==2){
					boolean foundNakedSet = true; //A naked set Exists
					boolean isUseful = false; //One of the candidate of the set exists outside the naked cells
					for (int c=0; c<s.getSize();c++){/*Cicle through the col*/						
						Cell tmp = s.getCell(c,col);
						if (!tmp.isSolved() && tmp!=nakedCells.get(0) && tmp!=nakedCells.get(1)){
							if (couple.atLeastOneIsIn(tmp.getCandidates()))	foundNakedSet=false;
						}						
					}
					if (foundNakedSet){
						for (int c=0; c<s.getSize();c++){/*Cicle through the col*/						
							Cell tmp = s.getCell(c,col);
							
							if (!tmp.isSolved() && !tmp.equals(nakedCells.get(0)) && !tmp.equals(nakedCells.get(1)) 
									&& couple.atLeastOneIsIn(tmp.getCandidates())){
								isUseful = true; /*ONE OF THE CELL outside the naked cells contains the naked set*/
							}
							if (!tmp.isSolved() 
									&& (tmp.equals(nakedCells.get(0))||tmp.equals(nakedCells.get(1))) 
									&& tmp.getCandidates().size()>couple.size()){
								isUseful = true; /*ONE OF THE naked cells contains other candidates*/
							}
						}
						if (isUseful){
							System.out.println( 
									String.format("Naked couple (%d,%d) found on cells [%d,%d],[%d,%d]"
											,couple.a, couple.b,
											nakedCells.get(0).getRow(),nakedCells.get(0).getCol(),
											nakedCells.get(1).getRow(),nakedCells.get(1).getCol())
							);
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("couple", couple);
							params.put("nakedPair", nakedCells);
							SolveStep ss = new SolveStep();
							ss.setMethod(this);					
							ss.setObjects(params);									
							return ss;
						}
					}
				}

			}	
		}
		return null;
	}

}

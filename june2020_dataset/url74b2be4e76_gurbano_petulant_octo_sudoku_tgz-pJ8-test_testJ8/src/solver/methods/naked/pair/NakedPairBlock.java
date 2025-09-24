package solver.methods.naked.pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import solver.SolveStep;
import solver.Sudoku;
import solver.methods.SolveMethod;
import solver.methods.naked.sets.Couple;
import solver.structures.Cell;
import solver.structures.SudokuBlock;

public class NakedPairBlock implements SolveMethod{
	
	
	 

	@Override
	public void apply(Sudoku s, SolveStep ss) {
		Couple couple = (Couple) ss.getParams().get("couple");
		List<Cell> nakedPair = (List<Cell>) ss.getParams().get("nakedPair");
		
		nakedPair.get(0).setCandidates(new ArrayList<Integer>( Arrays.asList(couple.a,couple.b) ));
		nakedPair.get(1).setCandidates(new ArrayList<Integer>( Arrays.asList(couple.a,couple.b) ));
		
		
		int block = nakedPair.get(0).getBlock();
		
		
		
		/*Remove candidates couple's candidates from the cells of the block (all but the nakedPair)*/
		SudokuBlock sb = s.getBlock(block);
		for (int r=0; r<sb.getSize();r++){
			for (int c=0; c<sb.getSize();c++){
				if ((sb.getCell(r, c).getCandidates().contains(couple.a)
						||
					sb.getCell(r, c).getCandidates().contains(couple.a))
					&&
					!nakedPair.contains(sb.getCell(r, c))
					){
						sb.getCell(r, c).removeCandidate(couple.a);
						sb.getCell(r, c).removeCandidate(couple.b);
				}
			}
		}
		
	}

	@Override
	public void printInfo(SolveStep ss) {
		Couple couple = (Couple) ss.getParams().get("couple");
		List<Cell> nakedPair = (List<Cell>) ss.getParams().get("nakedPair");
		int block = nakedPair.get(0).getBlock();
		System.out.println(String.format(getName() + ")There is a Naked pair (%s) in block %d", couple, block));
		System.out.println(String.format("Candidates %s can be removed from all cells of block %d but cells [%d,%d] and [%d,%d]",
					couple,block,
					nakedPair.get(0).getRow(),nakedPair.get(0).getCol(),
					nakedPair.get(1).getRow(),nakedPair.get(1).getCol()
				));		
	}

	@Override
	public String getName() {
		return "Naked Pair (Block)";
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

		/*FOR EACH BLOCK*/
		for(int block=0; block<9; block++){
			SudokuBlock sb = s.getBlock(block);
			/*If a couple it's in EXACTLY two cells of the block and both candidates are not in other cells, there is a naked pair */
			for (Couple couple : allCouple) {
				List<Cell> nakedCells = new ArrayList<Cell>();
				for (Cell tmp : sb.getCellList()) {//Cicle trough the block
					if (!tmp.isSolved() && couple.isIn(tmp.getCandidates())){
						nakedCells.add(tmp);
					}
				}
				/*Check if there is a naked set:
				 * Couple must be in extactly two cells and candidates must NOT be present elsewhere*/
				if (nakedCells.size()==2){
					boolean foundNakedSet = true; //A naked set Exists
					boolean isUseful = false; //One of the candidate of the set exists outside the naked cells
					for (Cell tmp : sb.getCellList()) {//Cicle trough the block
						if (!tmp.isSolved() && tmp!=nakedCells.get(0) && tmp!=nakedCells.get(1)){
							if (couple.atLeastOneIsIn(tmp.getCandidates()))	foundNakedSet=false;
						}						
					}
					if (foundNakedSet){
						for (Cell tmp : sb.getCellList()) {//Cicle trough the block
							
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
	
	
	
//
//	@Override
//	public SolveStep getStep(Sudoku s) {
//		for(int block=0; block<9; block++){
//			SudokuBlock sb = s.getBlock(block);
//			
//			Map<Couple, List<Cell>> nakedPair = new HashMap<Couple, List<Cell>>();
//			/*LOOK FOR COUPLES IN THE BLOCK*/
//			for (int r=0; r<sb.getSize();r++){
//				for (int c=0; c<sb.getSize();c++){
//					if (sb.getCell(r, c).getCandidates().size()==2){
//						Couple couple = new Couple(sb.getCell(r, c).getCandidates().get(0),sb.getCell(r, c).getCandidates().get(1));
//						if (!nakedPair.containsKey(couple))
//							nakedPair.put(couple, new LinkedList<Cell>());
//						nakedPair.get(couple).add(sb.getCell(r, c));
//					}
//				}
//			}
//			Set<Couple> couples = nakedPair.keySet();
//			for (Couple couple : couples) {
//				if (nakedPair.get(couple).size()==2){					
//					/*Look for other cells (but the couple) that contains one of the candidates*/
//					boolean candidatesInOtherCell = false;
//					for (int r=0; r<sb.getSize();r++){
//						for (int c=0; c<sb.getSize();c++){							
//							if ((sb.getCell(r, c).getCandidates().contains(couple.a)
//									||
//								sb.getCell(r, c).getCandidates().contains(couple.a))
//								&&
//								!nakedPair.get(couple).contains(sb.getCell(r, c))
//								){
//								candidatesInOtherCell=true;
//							}
//						}
//					}
//					if (candidatesInOtherCell){
//						Map<String, Object> params = new HashMap<String, Object>();
//						params.put("couple", couple);
//						params.put("nakedPair", nakedPair.get(couple));
//						SolveStep ss = new SolveStep();
//						ss.setMethod(this);					
//						ss.setObjects(params);									
//						return ss;
//						
//					}
//				}
//			}
//			
//		}
//		return null;
//	}

}

package solver.methods;

import solver.SolveStep;
import solver.Sudoku;
import solver.structures.Cell;
import solver.structures.SudokuBlock;
import solver.structures.SudokuRowCol;
/**
 * A solving technique that uses the intersections between lines and boxes.
 * This is a basic solving technique. When all candidates for a digit in a house are 
 * located inside the intersection with another house, we can eliminate the remaining candidates 
 * from the second house outside the intersection.
 * @author urbangi
 *
 */
public class LockedCandidatesPointing implements SolveMethod {

	

	@Override
	public String getName() {
		return "Locked Candidates (pointing)";
	}

	@Override
	public SolveStep getStep(Sudoku s) {
		/*FOR EACH BLOCK*/
			/*FOR EACH CANDIDATE*/
				/*IF ALL THE CANDIDATES CELLS ARE IN THE SAME ROW, 
				 * 	WE CAN ELIMINATE THE CANDIDATE FROM THE SUDOKU ROW (EXCEPT FROM THE BLOCK)*/		
		for(int block=0; block<9; block++){
			SudokuBlock sb = s.getBlock(block);
			for (Integer candidate = 1; candidate<=9; candidate++){
				int rowfreq[] = {0,0,0}; //Frequency of the candidates in the 3 row of the block
				int colfreq[] = {0,0,0}; //Frequency of the candidates in the 3 col of the block
				/*FOR EACH CELL OF THE BLOCK*/
				for (int r=0; r<sb.getSize();r++){
					for (int c=0; c<sb.getSize();c++){
						if (!sb.getCell(r, c).isSolved()){
							if (sb.getCell(r, c).getCandidates().contains(candidate)){
								rowfreq[r] = rowfreq[r]+1;
								colfreq[c] = colfreq[c]+1;
							}							
						}
					}
				}
				boolean found = false;
				int sol_block = block;
				int sol_row = -1;
				int sol_col = -1;	
				/*IF at the end of the block check two of the rowfreq are 0, then the candidates are all on the same row*/
				if (rowfreq[0]>1 && rowfreq[1]==0 && rowfreq[2]==0){ 
					found = true;
					sol_row = 0;
//					System.out.println(String.format("(Block %d) Locked candidates on row %d (value %d)", block,sb.getCell(0, 0).getRow(), candidate ));
				}
				if (rowfreq[0]==0 && rowfreq[1]>1 && rowfreq[2]==0){
					found = true;
					sol_row = 1;
//					System.out.println(String.format("(Block %d) Locked candidates on row %d (value %d)", block,sb.getCell(1, 0).getRow(), candidate ));
				}
				if (rowfreq[0]==0 && rowfreq[1]==0 && rowfreq[2]>1){
					found = true;
					sol_row = 2;
//					System.out.println(String.format("(Block %d) Locked candidates on row %d (value %d)", block,sb.getCell(2, 0).getRow(), candidate ));
				}
				
				/*IF at the end of the block check two of the colfreq are 0, then the candidates are all on the same col*/
				if (colfreq[0]>1 && colfreq[1]==0 && colfreq[2]==0){ 
					found = true;
					sol_col = 0;
//					System.out.println(String.format("(Block %d) Locked candidates on col %d (value %d)", block,sb.getCell(0, 0).getCol(), candidate ));
				}
				if (colfreq[0]==0 && colfreq[1]>1 && colfreq[2]==0){ 
					found = true;
					sol_col = 1;
//					System.out.println(String.format("(Block %d) Locked candidates on col %d (value %d)", block,sb.getCell(0, 1).getCol(), candidate ));
				}
				if (colfreq[0]==0 && colfreq[1]==0 && colfreq[2]>1){
					found = true;
					sol_col = 2;
//					System.out.println(String.format("(Block %d) Locked candidates on col %d (value %d)", block,sb.getCell(0, 2).getCol(), candidate ));
				}
				
				if (found){
					found = false;
					if (sol_row>=0){
						/*FOUND A ROW*/
						SudokuRowCol row = s.getRow(sb.getCell(sol_row, 0).getRow());
						/*Cicle trough the row check all the candidates (except the one from the block sol_block)*/
						for (Cell cell : row.getCells()) {
							if (!cell.isSolved() && cell.getCandidates().contains(candidate) && cell.getBlock()!=sol_block){
								found = true;
							}
						}
						if (found){
//							System.out.println(String.format("(Block %d) Locked candidates on row %d (value %d)", sol_block,sb.getCell(sol_row, 0).getRow(), candidate ));
							SolveStep ss = new SolveStep();
							ss.setMethod(this);					
							ss.setCell(sb.getCell(sol_row, 0));
							ss.setInfo("ROW");
							ss.setValue(candidate);			
							return ss;
						}
					}
					if (sol_col>=0){
						/*FOUND A COL*/
						SudokuRowCol col = s.getCol(sb.getCell(0, sol_col).getCol());
						/*Cicle trough the col check all the candidates (except the one from the block sol_block)*/
						for (Cell cell : col.getCells()) {
							if (!cell.isSolved() && cell.getCandidates().contains(candidate) && cell.getBlock()!=sol_block){
								found = true;
							}
						}
						if (found){
//							System.out.println(String.format("(Block %d) Locked candidates on row %d (value %d)", sol_block,sb.getCell(sol_row, 0).getRow(), candidate ));
							SolveStep ss = new SolveStep();
							ss.setMethod(this);					
							ss.setCell(sb.getCell(0, sol_col));
							ss.setInfo("COL");
							ss.setValue(candidate);			
							return ss;
						}
					}
				}
				
			}
		}
		
		return null;
	}
	
	@Override
	public void apply(Sudoku s, SolveStep ss) {
		int row = ss.cell.getRow();
		int block = ss.cell.getBlock();
		s.getRow(row).removeCandidateExceptBlock(ss.value, ss.cell.getBlock());

	}
	
	@Override
	public void printInfo(SolveStep ss) {
		String s = String.format("Candidate %d is present, in block %d, only on %s %d.\n" +
				"Then, candidate %d can be removed from %s %d (everywhere but in block %d)",
				ss.value, ss.cell.getBlock(),
				ss.info.equalsIgnoreCase("ROW") ? "row":"column",
				ss.info.equalsIgnoreCase("ROW") ? ss.cell.getRow():ss.cell.getCol(),
				ss.value,
				ss.info.equalsIgnoreCase("ROW") ? "row":"column",
				ss.info.equalsIgnoreCase("ROW") ? ss.cell.getRow():ss.cell.getCol(),
				ss.cell.getBlock());
		System.out.println(s);
	}
	
	
}

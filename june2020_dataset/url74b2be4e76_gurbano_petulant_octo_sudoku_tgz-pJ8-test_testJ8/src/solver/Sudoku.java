package solver;

import java.util.ArrayList;
import java.util.List;

import solver.exceptions.InvalidSolutionException;
import solver.structures.Cell;
import solver.structures.SudokuBlock;
import solver.structures.SudokuCol;
import solver.structures.SudokuRow;
import solver.structures.SudokuRowCol;

public class Sudoku {
	protected int SIZE = 9;
	protected int BLOCK_SIZE = 3;
	
	private List<List<Cell>> cells;	
	
	public Cell getCell(int r, int c){
		return cells.get(r).get(c);
	}
	public void setCell(int r, int c, Cell cell){
		cells.get(r).set(c, cell);		
	}

	private void initCells(){
		cells = new ArrayList<List<Cell>>(SIZE);
		for (int r=0;r<SIZE;r++){
			List<Cell> row = new ArrayList<Cell>(SIZE);
			for (int c=0;c<SIZE;c++){
				row.add(new Cell(r,c));
			}
			cells.add(row);
		}		
	}
	
	public Sudoku(){
		initCells();
	}
	
	public String toString(boolean printCandidates){
		String ret = "";
		for (int r=0;r<this.SIZE;r++){
			for (int c=0;c<this.SIZE;c++){
				if (getCell(r, c).isSolved())
					if(!printCandidates)
						ret+=" " +getCell(r, c).getValue()+" ";
					else
						ret+=tolen(""+getCell(r, c).getValue(),20);
				else
					if(!printCandidates)
						ret+=" - ";
					else
						ret+= tolen(printCandidates(r, c),20);
			}
			ret+="\n";
		}
		return ret;
	}

	private String tolen(String a, int i) {
		int padnum = i-a.length();
		for (int x=0;x<padnum/2;x++)
			a = " "+a;
		for (int x=0;x<padnum/2;x++)
			a = a+" ";
		return a;
	}
	
	
	public static Sudoku NULL(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null}
		};
		ret.init(tmp);
		return ret;		
	}
	
	public static Sudoku EASIEST(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{7,null,null,1,3,9,null,6,null},
				{null,1,null,8,2,4,null,5,3},
				{null,4,null,6,null,5,null,null,2},
				{null,null,3,9,null,null,null,7,1},
				{1,9,null,null,null,null,null,8,4},
				{5,8,null,7,4,null,9,3,null},
				{null,5,9,4,null,null,8,null,7},
				{8,3,null,null,null,null,6,null,null},
				{2,7,4,null,6,null,null,null,null}		
		};
		ret.init(tmp);
		return ret;		
	}

	public static Sudoku EASY(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{null,null,null,null,null,9,2,null,1},
				{null,null,5,3,7,2,null,null,null},
				{null,null,null,6,null,null,7,null,null},
				{null,null,1,null,8,null,3,null,2},
				{null,5,null,null,null,null,null,9,null},
				{3,null,7,null,6,null,4,null,null},
				{null,null,3,null,null,6,null,null,null},
				{null,null,null,7,2,1,5,null,null},
				{4,null,2,5,null,null,null,null,null}					
		};
		ret.init(tmp);
		return ret;		
	}
	
	public static Sudoku INTRICATE(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{9,null,null,7,null,null,null,null,1},
				{null,null,null,null,null,null,null,null,3},
				{4,null,null,3,null,1,null,6,null},
				{null,8,4,9,3,null,null,7,null},
				{null,null,null,null,null,null,null,null,null},
				{null,1,null,null,8,7,2,5,null},
				{null,5,null,2,null,6,null,null,8},
				{6,null,null,null,null,null,null,null,null},
				{8,null,null,null,null,5,null,null,7}		
		};
		ret.init(tmp);
		return ret;		
	}
	
	public static Sudoku DIFFICULT(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{8,null,null,2,null,9,null,null,null},
				{4,9,null,null,null,null,7,null,null},
				{6,null,null,3,null,5,null,null,null},
				{null,null,null,1,null,8,null,null,2},
				{null,1,null,null,2,null,null,8,null},
				{2,null,null,7,null,6,null,null,null},
				{null,null,null,8,null,7,null,null,9},
				{null,null,6,null,null,null,null,4,8},
				{null,null,null,9,null,4,null,null,1}
		};
		ret.init(tmp);
		return ret;		
	}

	
	public static Sudoku CIOSCA1(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{6,3,4,1,7,null,null,null,null},
				{5,null,1,null,null,8,7,null,3},
				{null,8,7,null,null,null,6,null,1},
				{null,1,null,null,null,7,3,null,8},
				{null,null,2,8,5,null,1,7,null},
				{8,7,null,3,null,null,null,2,null},
				{null,null,3,null,null,null,null,1,null},
				{null,null,9,2,null,null,4,null,7},
				{null,null,8,null,null,6,2,3,9}
		};
		ret.init(tmp);
		return ret;		
	}
	
	public static Sudoku CIOSCA2(){
		Sudoku ret = new Sudoku();
		ret.initCells();
		Integer[][] tmp = {
				{null,null,null,3,9,null,null,null,null},
				{null,8,null,null,null,null,4,null,9},
				{6,null,null,5,null,2,null,null,null},
				{3,4,null,null,null,null,7,5,null},
				{1,null,null,null,null,null,null,null,8},
				{null,5,8,null,null,null,null,1,4},
				{null,null,null,6,null,1,null,null,2},
				{8,null,7,null,null,null,null,4,null},
				{null,null,null,null,2,7,null,null,null}
		};
		ret.init(tmp);
		return ret;		
	}

	private void init(Integer[][] tmp) {
		initCells();
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (tmp[r][c]!=null)
					this.setCell(r, c, new Cell(r,c,tmp[r][c]));
				else
					this.setCell(r, c, new Cell(r,c));
			}
		}		
	}
	

	private void init(String tmp) throws Throwable {
		initCells();
		if (tmp.length()!=81){
			throw new Throwable();
		}else{
			for (int r = 0; r < SIZE; r++) {
				for (int c = 0; c < SIZE; c++) {
					try{
						this.setCell(r, c, new Cell(r,c, Integer.parseInt(""+tmp.charAt((r+1)+c))));
					}catch(Exception e){
						this.setCell(r, c, new Cell(r,c, null ));
					}
				}
			}
			
		}
	}
	
	public boolean contains(int n) {
		for (int r=0;r<SIZE;r++){
			for (int c=0;c<SIZE;c++){
				if (getCell(r, c).is(n))
					return true;
			}
		}
		return false;
	}

	public boolean isSolved() {
		for (int x=0; x<SIZE;x++){
			for (int y=0; y<SIZE;y++){
				if (!getCell(x, y).isSolved())
					return false;
			}
		}
		return true;
	}

	public SudokuBlock getBlock(int i){
		int row = i/BLOCK_SIZE;
		int column = i%BLOCK_SIZE;
		SudokuBlock sb = new SudokuBlock();
		for (int r=0;r<BLOCK_SIZE;r++){
			for (int c=0;c<BLOCK_SIZE;c++){
				Cell newcell = this.getCell(r+(row*BLOCK_SIZE), c+(column*BLOCK_SIZE));
				sb.setCell(r, c, newcell);
			}
		}
		return sb;
	}
	public SudokuBlock getBlock(int r, int c) {
		int blocknum = c/BLOCK_SIZE+(r/BLOCK_SIZE*BLOCK_SIZE);
		return getBlock(blocknum);
	}

	public SudokuRowCol getRow(int r) {
		SudokuRowCol row = new SudokuRow(SIZE);
		for (int i=0; i<SIZE; i++){
			row.setCell(i,this.getCell(r, i));
		}
		return row;
	}
	public SudokuCol getCol(int c) {
		SudokuCol col = new SudokuCol(SIZE);
		for (int i=0; i<SIZE; i++){
			col.setCell(i,this.getCell(i,c));
		}
		return col;
	}

	public String printCandidates(int i, int j) {
		String ret="{";
		ret += getCell(i, j).printCandidates();
		return ret+"}";
	}
	public int getSize() {
		return SIZE;
	}
	
	public void removeCandidateFromRow(Integer value, int row) {
		this.getRow(row).removeCandidate(value);		
	}
	public void removeCandidateFromCol(Integer value, int col) {
		this.getCol(col).removeCandidate(value);		
	}
	public void removeCandidateFromBlock(Integer value, int row, int col) {
		this.getBlock(row,col).removeCandidate(value);
		
	}
	public void solveCell(Cell cell, Integer value) {
		cell.setCandidates(new ArrayList<Integer>());
		cell.setValue(value);
		this.removeCandidateFromBlock(value, cell.getRow(), cell.getCol());
		this.removeCandidateFromRow(value, cell.getRow());
		this.removeCandidateFromCol(value, cell.getCol());
		
	}
	
	
	
	public void checkSolution() throws InvalidSolutionException {
		for (int i=0; i<SIZE; i++){
			//CECK ROW
			checkRowSolution(i);			
		}
		for (int i=0; i<SIZE; i++){
			//CECK COLS
			checkColsolution(i);
		}
		for (int i=0; i<8; i++){
			//CECK BLOCK
		}
		
	}
	private void checkColsolution(int i) throws InvalidSolutionException  {
		this.getRow(i).checkSolution();		
	}
	private void checkRowSolution(int i) throws InvalidSolutionException {
		this.getCol(i).checkSolution();	
		
	}
	public List<Cell> getCellList() {
		List<Cell> ret = new ArrayList<Cell>();
		for (List<Cell> row : cells) {
			ret.addAll(row);
		}
		return ret;
	}







}

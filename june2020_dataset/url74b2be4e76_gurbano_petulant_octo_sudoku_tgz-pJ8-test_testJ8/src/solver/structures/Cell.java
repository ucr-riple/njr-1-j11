package solver.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cell {
	private boolean filled;
	private Integer value;	
	private List<Integer> candidates;
	private int row, col, block; //related to the whole sudoku
	
	
	public Cell(int r, int c){
		this.row= r;
		this.col = c;
		this.block =  c/3+(r/3*3);
		filled = false;
		value = null;
		candidates = new ArrayList<Integer>();
	}	
	
	public Cell(int r, int c,Integer integer) {
		this.row= r;
		this.col = c;
		this.block =  c/3+(r/3*3);
		filled = true;
		value = integer;
		candidates = new ArrayList<Integer>();
	}

	private void sortCandidate(){
		Collections.sort(candidates, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}			
		});	
	}
	public boolean isSolved() {
		return filled;
	}
	public void setSolved(boolean filled) {
		this.filled = filled;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		setSolved(value!=null);
		this.value = value;
	}
	public List<Integer> getCandidates() {
		return candidates;
	}
	public void setCandidates(List<Integer> candidates) {
		this.candidates = candidates;
	}

	public boolean is(int n) {
		if (value!=null && value.intValue()==n)
			return true;
		else
			return false;
	}

	public String printCandidates() {
		StringBuilder ret = new StringBuilder();
		for (Integer candidat : getCandidates()) {
			ret.append("'"+candidat+"'");
		}
		return ret.toString();
	}

	public void addCandidate(Integer n) {
		if (!candidates.contains(n))
			candidates.add(n);
		sortCandidate();	
	}
	public void removeCandidate(Integer n) {
		if (candidates.contains(n))
			candidates.remove(n);		
		sortCandidate();	
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "Cell [block=" + block + ", candidates=" + candidates + ", col="
				+ col + ", filled=" + filled + ", row=" + row + ", value="
				+ value + "]";
	}

	public int getBlock() {
		return block;
	}

	


	
}

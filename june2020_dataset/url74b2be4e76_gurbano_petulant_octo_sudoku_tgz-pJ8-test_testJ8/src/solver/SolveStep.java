package solver;

import java.util.HashMap;
import java.util.Map;

import solver.methods.SolveMethod;
import solver.structures.Cell;

public class SolveStep {

	public SolveMethod sm;
	public Cell cell;
	public Integer value;
	public String info;
	Map<String, Object> params = new HashMap<String, Object>();
	
	public void debug() {
		System.out.println("---------");
		sm.printInfo(this);		
	}
	public void apply(Sudoku s){
		this.sm.apply(s, this);		
	}

	
	
	/*GETTER/SETTER*/
	public void setMethod(SolveMethod method) {
		this.sm = method;		
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public void setValue(int i) {
		this.value=i;
	}

	
	

	public void setInfo(String info) {
		this.info = info;
	}

	public void setObjects(Map<String, Object> params) {
		this.params=params;
	}

	public SolveMethod getSm() {
		return sm;
	}

	public void setSm(SolveMethod sm) {
		this.sm = sm;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Cell getCell() {
		return cell;
	}

	public String getInfo() {
		return info;
	}

	

}

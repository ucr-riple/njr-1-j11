package regexpToNFA;

import java.util.LinkedList;
/**
 * 以小括号为划分单元，有2种
 * 	1：括号里面套括号如（（a）(b)）。unit.getStart()->u.getStart()
 *  2：括号里面再没有括号如（a）。unit.getStart()->unit.getEnd()
 *  当单元后面有nextUnit时，unit.getEnd()->unit.getNextUnit().getStart()
 *  如果单元后面没有nextUnit并且不是最外层Unit时，unit.getEnd()->unit.getFather().getEnd()
 * @author Administrator
 *
 */
public class Unit {
	private int start;
	private int end;
	private Unit nextUnit;
	private Unit father;
	private LinkedList<Unit> innerFirstUnit =new LinkedList<Unit>();
	private String tranChar="";
	private int state=0; //0:正常 1:闭包 2:正闭包
	public Unit(int s,int e,Unit unit){
		this.start=s;
		this.end=e;
		this.father=unit;
	}
	public int getStart(){
		return this.start;
	}
	public int getEnd(){
		return this.end;
	}
	public Unit getNextUnit(){
		return this.nextUnit;
	}
	public void setNextUnit(Unit unit){
		this.nextUnit=unit;
	}
	public LinkedList<Unit> getInnerFirstUnit(){
		return this.innerFirstUnit;
	}
	public void addInnerFirstUnit(Unit unit){
		this.innerFirstUnit.add(unit);
	}
	public String getTranChar(){
		return this.tranChar;
	}
	public void setTranChar(char ch){
		this.tranChar+=ch;
	}
	/**
	 * @return 0：正常 1：闭包 2：正闭包
	 */
	public int getState(){
		return this.state;
	}
	/**
	 * @param s 0：正常 1：闭包 2：正闭包
	 */
	public void setState(int s){
		this.state=s;
	}
	public Unit getFather(){
		return this.father;
	}
}

package NFAToDNA;

import java.util.TreeSet;
/**
 * @author lm
 *新节点图的边
 */
public class Dtran {
	/**
	 * 转换为DNF过程中，开始新节点中包含的旧节点集
	 */
	TreeSet<Integer> nodes = new TreeSet<Integer>(); //closure(mark.value)
	/**
	 * 开始节点
	 */
	NewNode mark;
	/**
	 * 转换字符
	 */
	String tran;
	/**
	 * 结束节点
	 */
	NewNode d;
	public Dtran(TreeSet<Integer> nodes,NewNode mark,String tran,NewNode d){
		this.nodes=nodes;
		this.mark=mark;
		this.tran=tran;
		this.d=d;
	}
	public TreeSet<Integer> getNodes(){
		return this.nodes;
	}
	/**
	 * 开始节点
	 */
	public NewNode getMark(){
		return this.mark;
	}
	public String getTran(){
		return this.tran;
	}
	/**
	 * 结束节点
	 */
	public NewNode getD(){
		return this.d;
	}
}

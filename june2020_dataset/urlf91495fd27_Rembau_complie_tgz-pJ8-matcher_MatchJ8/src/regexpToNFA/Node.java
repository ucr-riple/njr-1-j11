package regexpToNFA;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * 节点，1为开始节点，2为结束节点
 * @author Administrator
 *
 */
public class Node {
	private int value;
	/**
	 * 节点能到达的节点和对应的转换字符
	 */
	private Hashtable<Integer,LinkedList<String>> enableReachValues = new Hashtable<Integer,LinkedList<String>>();
	public Node(int v){
		this.value=v;
	}
	public int getValue(){
		return this.value;
	}
	public void addEnableReachValue(int value,String ch){
		if(enableReachValues.containsKey(value)){
			enableReachValues.get(value).add(ch);
		} else{
			LinkedList<String> strs = new LinkedList<String>();
			strs.add(ch);
			this.enableReachValues.put(value,strs);
		}
	}
	/**
	 * 节点能到达的节点对应的转换字符
	 */
	public Hashtable<Integer,LinkedList<String>> getEnableReachValues(){
		return this.enableReachValues;
	}
	/**
	 * 该节点经过tran转换能到达的节点的 id
	 * @param tran 转换字符
	 * @return 能到达的节点的id
	 */
	public TreeSet<Integer> getNodeMove(String tran){
		//System.out.print("进来的="+tran+"=");
		TreeSet<Integer> nodes= new TreeSet<Integer>();
		Enumeration<Integer> e=enableReachValues.keys();
		while(e.hasMoreElements()){
			int i = e.nextElement();
			//System.out.print("里面的="+enableReachValues.get(i)+"=");
			for(String str:enableReachValues.get(i)){
				if(str.equals(tran)){
					//System.out.print(i);
					nodes.add(i);
				}
			}
		}
		//System.out.print("值："+value+" "+nodes);
		return nodes;
	}
}
package regexpToNFA;

import java.util.Hashtable;
import java.util.LinkedList;

import NFAToDNA.NewNode;

public class ManagerK {
	static int count_k=1;
	static LinkedList<K> en = new LinkedList<K>();
	public static void addUnit(K k){
		en.add(k);
		k.setK(count_k++);
	}
	public static void handle(LinkedList<NewNode> nns,Hashtable<Integer,Node> nodeList){
		for(K k:en){
			k.handle(nns,nodeList);
		}
	}
	public static void printK(){
		for(K k:en){
			System.out.println(k.getK()+":start:"+k.getStartNewNode()+":end:"+k.getEndNewNode()+":can:"+k.getCanReachNode());
		}
	}
	public static LinkedList<K> getEn(){
		printK();
		return en;
	}
}

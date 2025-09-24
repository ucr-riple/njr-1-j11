package regexpToNFA;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import NFAToDNA.NewNode;

public class K {
	private Unit unit;
	private int k;
	private LinkedList<Integer> startNewNode = new LinkedList<Integer>();
	private LinkedList<Integer> endNewNode = new LinkedList<Integer>();
	private LinkedList<Integer> canReachNode = new LinkedList<Integer>();
	public K(Unit unit){
		this.unit=unit;
	}
	public void addStartNewNode(NewNode nn){
		startNewNode.add(nn.getId());
	}
	public LinkedList<Integer> getStartNewNode(){
		return this.startNewNode;
	}
	public void addEndNewNode(NewNode nn){
		endNewNode.add(nn.getId());
	}
	public LinkedList<Integer> getEndNewNode(){
		return this.endNewNode;
	}
	public void addCanReachNode(NewNode nn){
		canReachNode.add(nn.getId());
	}
	public LinkedList<Integer> getCanReachNode(){
		return this.canReachNode;
	}
	public Unit getUnit(){
		return this.unit;
	}
	public void setK(int k){
		this.k=k;
	}
	public int getK(){
		return this.k;
	}
	public void handle(LinkedList<NewNode> nns,Hashtable<Integer,Node> nodeList){
		LinkedList<Integer> canReach = getCanReach(nodeList);
		
		for(NewNode nn:nns){
			for(int v:canReach){
				if(nn.getNodes().contains(v)){
					this.addCanReachNode(nn);
					break;
				}
			}
			if(nn.getNodes().contains(unit.getStart())){
				this.addStartNewNode(nn);
			}
			if(nn.getNodes().contains(unit.getEnd())){
				this.addEndNewNode(nn);
			}
		}
	}
	public LinkedList<Integer> getCanReach(Hashtable<Integer,Node> nodeList){
		LinkedList<Integer> canReach = new LinkedList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(unit.getStart());
		canReach.add(unit.getStart());
		canReach.add(unit.getEnd());
		while(!stack.isEmpty()){
			int v = stack.pop();
			Enumeration<Integer> keys = nodeList.get(v).getEnableReachValues().keys();
			while(keys.hasMoreElements()){
				int v_=keys.nextElement();
				if(!canReach.contains(v_)){
					canReach.add(v_);
					stack.push(v_);
				}
			}
		}
		return canReach;
	}
}

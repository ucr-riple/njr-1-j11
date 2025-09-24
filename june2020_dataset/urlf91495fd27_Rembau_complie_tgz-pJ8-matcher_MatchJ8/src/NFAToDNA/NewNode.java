package NFAToDNA;

import java.util.TreeSet;
public class NewNode {
	private TreeSet<Integer> nodes = new TreeSet<Integer>();
	private int id;
	private boolean mark=false;
	boolean start=false;
	boolean end=false;
	public NewNode(int id){
		this.id=id;
	}
	public void setStart(){
		this.start=true;
	}
	public boolean isStart(){
		return this.start;
	}
	public void setEnd(){
		this.end=true;
	}
	public boolean isEnd(){
		return this.end;
	}
	public TreeSet<Integer> getNodes(){
		return this.nodes;
	}
	public void setNodes(TreeSet<Integer> nodes){
		this.nodes=nodes;
	}
	public void addNode(Integer node){
		this.nodes.add(node);
	}
	public void setId(int i){
		this.id=i;
	}
	public int getId(){
		return this.id;
	}
	public boolean getMark(){
		return this.mark;
	}
	public void mark(){
		mark=true;
	}
	public boolean isSame(TreeSet<Integer> t){
		if(nodes.size()!=t.size()){
			return false;
		} else{
			for(int i:t){
				if(!nodes.contains(i)){
					return false;
				}
			}
		} 
		return true;
	}
}
